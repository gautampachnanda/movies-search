package uk.co.aaditech.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uk.co.aaditech.entities.Programme;
import uk.co.aaditech.entities.ProgrammeType;
import uk.co.aaditech.services.ProgrammeService;

@RestController
public class ProgrammeController {
	private Logger logger = LoggerFactory.getLogger(ProgrammeController.class);

	@Autowired
	private ProgrammeService programmeService;

	@RequestMapping(value = "/programme", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Programme> allProgrammes(@RequestParam(value = "title", required = false) Optional<String> title,
			@RequestParam(value = "type", required = false) Optional<String> type,
			@RequestParam(value = "startyear", required = false) Optional<Integer> startYear,
			@RequestParam(value = "maxresults", required = false) Optional<Integer> maxResults) {
		logger.info("Lookup programmes");
		List<Programme> results;
		if (title.isPresent() || type.isPresent()) {

			Programme probe = new Programme();
			if (title.isPresent()) {
				probe.setTitle(title.get());
			}
			if (type.isPresent()) {
				probe.setProgrammeType(ProgrammeType.fromName(type.get()));
			}

			if (startYear.isPresent()) {
				probe.setStartYear(startYear.get());
			}
			logger.info("Filtering using {} maxResults {}", probe, maxResults);
			results = programmeService.findAll(probe, maxResults, Optional.ofNullable(1));
		} else {
			logger.info("Fetching all");
			results = programmeService.findAll();
		}
		logger.info("Found {} results", results.size());
		return results;
	}

	@RequestMapping(value = "/programme/imdbid/{imdbId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Programme byImdbId(@PathVariable String imdbId) {
		return programmeService.findByImdbId(imdbId);
	}

	@RequestMapping(value = "/programme/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Programme> byType(@PathVariable String type) {
		return programmeService.findByType(type);
	}

}
