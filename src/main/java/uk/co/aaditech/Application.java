package uk.co.aaditech;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uk.co.aaditech.entities.Programme;
import uk.co.aaditech.repository.ProgrammeRepository;
import uk.co.aaditech.services.ProgrammeService;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class },scanBasePackages= {"uk.co.aaditech.services"})
@RestController
@EnableJpaRepositories(basePackageClasses= {ProgrammeRepository.class,ProgrammeService.class})
public class Application {

	@Autowired
	private ProgrammeService programmeService;

	@RequestMapping("/")
	public String home() throws Exception {
		programmeService.loadAndSaveEntitiesFromFile();
		return "Hello Docker World";
	}
	
	@RequestMapping(value="/programme", 
			method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Programme> allProgrammes() {
		return programmeService.findAll();
	}

	
	@RequestMapping(value="/programme/imdbid/{imdbId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Programme byImdbId(@PathVariable String imdbId) {
		return programmeService.findByImdbId(imdbId);
	}
	
	@RequestMapping(value="/programme/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Programme> byType(@PathVariable String type){
		
		return programmeService.findByType(type);
		
	}


	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }
	

}
