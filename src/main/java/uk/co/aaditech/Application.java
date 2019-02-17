package uk.co.aaditech;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.co.aaditech.repository.ProgrammeRepository;
import uk.co.aaditech.services.ProgrammeService;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }, scanBasePackages = { "uk.co.aaditech.services",
		"uk.co.aaditech.config", "uk.co.aaditech.controllers" })
@RestController
@EnableJpaRepositories(basePackageClasses = { ProgrammeRepository.class })
public class Application {

	@Autowired
	private ProgrammeService programmeService;

	@RequestMapping("/")
	public String home() {
		return "Hello World";
	}

	@PostConstruct
	public void init() throws Exception {
		programmeService.loadAndSaveEntitiesFromFile();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

}
