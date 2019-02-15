package uk.co.aaditech.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import uk.co.aaditech.entities.Genre;
import uk.co.aaditech.entities.Programme;
import uk.co.aaditech.entities.ProgrammeType;

@RunWith(SpringRunner.class)
@DataJpaTest

@AutoConfigureTestDatabase(replace = Replace.NONE)
@ComponentScan("uk.co.aaditech.services")
public class ProgrammeRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ProgrammeRepository programmeRepository;

	@Test
	public void whenFindById_thenReturnProgramme() {
		programmeRepository.deleteAll();
		List<Genre> genres = new ArrayList<>();
		genres.add(new Genre("s"));
		Programme toSave = new Programme("alex", "imdb1", "primary", "original", false, 2001, null, null, genres,
				ProgrammeType.MOVIE);
		entityManager.persist(toSave);
		entityManager.flush();
		Programme found = programmeRepository.findByImdbId("imdb1");
		assertThat(found.getTitle()).isEqualTo(toSave.getTitle());
	}

}
