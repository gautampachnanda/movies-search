package uk.co.aaditech.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.aaditech.entities.Programme;
import uk.co.aaditech.repository.ProgrammeRepository;

@Service
public class ProgrammeSevice {

	@Autowired
	private ProgrammeRepository repository;

	public Programme save(Programme programme) {
		return repository.save(programme);
	}

	public void saveAll(List<Programme> entities) {
		repository.saveAll(entities);
	}

	public List<Programme> findAll() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	public void clearAll() {
		repository.deleteAll();
	}

}
