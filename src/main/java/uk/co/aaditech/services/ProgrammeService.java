package uk.co.aaditech.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.aaditech.entities.Programme;
import uk.co.aaditech.entities.ProgrammeType;
import uk.co.aaditech.repository.ProgrammeRepository;

@Service
public class ProgrammeService {

	private Logger logger = LoggerFactory.getLogger(ProgrammeService.class);

	@Autowired
	private ProgrammeRepository repository;

	public ProgrammeService() {
	}

	public List<Programme> getAllProgrammes() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
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
	 * @throws Exception 
	 * 
	 */
	@PostConstruct
	public void loadAndSaveEntitiesFromFile() throws Exception {

		buildAndSave();

	}

	private void buildAndSave() throws Exception {
		if (repository.count() == 0) {
			try {
				Path path = Paths.get(getClass().getClassLoader().getResource("title.basics.tsv").toURI());
				logger.info(String.format("Loading %s", path.getFileName()));
				Stream<String> lines = Files.lines(path);
				lines.filter(line -> !line.startsWith("tconst")).forEach(line -> {
					logger.info("Loaded  line {}", line);
					Programme p = new Programme(line);
					logger.info("Saving {} using line {}", p, line);
					repository.save(p);

				});

				logger.info("Saved {}", repository.count());
				lines.close();
			} catch (IOException | URISyntaxException e) {
				throw e;
			}
		}
	}

	public List<Programme> findAll() {
		return getAllProgrammes();
	}

	public Programme findByImdbId(String imdbId) {

		return repository.findByImdbId(imdbId);
	}

	public List<Programme> findByType(String type) {
		ProgrammeType programmeType=ProgrammeType.fromName(type);
		logger.info("Fetching {}", programmeType);
		return repository.findByType(programmeType);
	}

}
