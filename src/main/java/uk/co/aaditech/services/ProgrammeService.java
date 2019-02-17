package uk.co.aaditech.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import uk.co.aaditech.entities.Import;
import uk.co.aaditech.entities.Programme;
import uk.co.aaditech.entities.ProgrammeType;
import uk.co.aaditech.repository.ImportRepository;
import uk.co.aaditech.repository.ProgrammeRepository;

@Service
public class ProgrammeService {

	private Logger logger = LoggerFactory.getLogger(ProgrammeService.class);

	@Autowired
	private ProgrammeRepository programmeRespository;

	@Autowired
	private ImportRepository importRepository;

	public ProgrammeService() {
	}

	public List<Programme> getAllProgrammes() {
		return StreamSupport.stream(programmeRespository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	/**
	 * The format of each line in title.basics.tsv is
	 * 
	 * tconst (string) - alphanumeric unique identifier of the title
	 * 
	 * titleType (string) – the type/format of the title (e.g. movie, short,
	 * tvseries, tvepisode, video, etc)
	 * 
	 * primaryTitle (string) – the more popular title / the title used by the
	 * filmmakers on promotional materials at the point of release
	 * 
	 * originalTitle (string) - original title, in the original language
	 * 
	 * isAdult (boolean) - 0: non-adult title; 1: adult title
	 * 
	 * startYear (YYYY) – represents the release year of a title. In the case of TV
	 * Series, it is the series start year endYear (YYYY) – TV Series end year. ‘\N’
	 * for all other title types
	 * 
	 * runtimeMinutes – primary runtime of the title, in minutes
	 * 
	 * genres (string array) – includes up to three genres associated with the title
	 * 
	 * @throws Exception
	 * 
	 */
	@Async("asyncExecutor")
	@PostConstruct
	public void loadAndSaveEntitiesFromFile() throws Exception {
		buildAndSave();
	}

	private void buildAndSave() throws Exception {

		try {
			Path path = Paths.get(getClass().getClassLoader().getResource("title.basics.tsv").toURI());
			String checksum = buildChecksum(path);
			Import lastImport = getLastImport();
			if (shouldRebuild(lastImport, checksum)) {

				logger.info(String.format("Loading %s", path.getFileName()));
				Stream<String> lines = Files.lines(path);
				lines.filter(line -> !line.startsWith("tconst")).forEach(line -> {
					logger.debug("Loaded  line {}", line);
					Programme p = new Programme(line);
					logger.info("Saving :{}", p.getImdbId());
					programmeRespository.save(p);
					logger.debug("Saved {} using line {}", p, line);
				});

				logger.info("Saved {}", programmeRespository.count());
				lines.close();
				saveOrMergeImport(checksum, lastImport);
			}
		} catch (IOException | URISyntaxException e) {
			throw e;
		}

	}

	private boolean shouldRebuild(Import lastImport, String checksum) {
		return lastImport == null || !lastImport.getChecksum().equals(checksum);
	}

	private void saveOrMergeImport(String checksum, Import lastImport) {
		if (lastImport == null) {
			importRepository.save(new Import(checksum));
		} else {
			lastImport.setChecksum(checksum);
			importRepository.save(lastImport);
		}
	}

	private Import getLastImport() {

		if (importRepository.count() == 0) {
			return null;
		}

		return importRepository.findAll().iterator().next();

	}

	public List<Programme> findAll() {
		return getAllProgrammes();
	}

	public List<Programme> findAll(Programme probe, Optional<Integer> maxResults, Optional<Integer> pageNumber) {

		List<Programme> results = Lists.newArrayList();
		Sort sort = Sort.by("title");
		if (maxResults.isPresent()) {

			Page<Programme> pageResults = programmeRespository.findAll(Example.of(probe),
					gotoPage(pageNumber.isPresent() ? pageNumber.get() : 1, maxResults.get(), sort));
			results = pageResults.stream().collect(Collectors.toList());
		} else {
			results = programmeRespository.findAll(Example.of(probe), sort);
		}
		logger.info("Fetched {} for {}", results.size(), probe);
		return results;
	}

	private PageRequest gotoPage(int page, int size, Sort sort) {
		PageRequest request = PageRequest.of(page, size, sort);
		return request;
	}

	public Programme findByImdbId(String imdbId) {

		return programmeRespository.findByImdbId(imdbId);
	}

	public List<Programme> findByType(String type) {
		ProgrammeType programmeType = ProgrammeType.fromName(type);
		logger.debug("Fetching {}", programmeType);
		return programmeRespository.findByType(programmeType);
	}

	private String buildChecksum(Path path) throws IOException {
		try (InputStream is = Files.newInputStream(path)) {
			return org.apache.commons.codec.digest.DigestUtils.md5Hex(is);
		}
	}

}
