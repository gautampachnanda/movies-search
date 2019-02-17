package uk.co.aaditech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import uk.co.aaditech.entities.Programme;
import uk.co.aaditech.entities.ProgrammeType;

/**
 * @author gautampachnanda
 *
 */

public interface ProgrammeRepository extends JpaRepository<Programme, Long> {
	
	@Query("from Programme p where p.imdbId = :imdbId")
    public Programme findByImdbId(@Param("imdbId") String imdbId);
	
	
	@Query("from Programme p where p.programmeType = :type")
    public List<Programme> findByType(@Param("type") ProgrammeType type);

}