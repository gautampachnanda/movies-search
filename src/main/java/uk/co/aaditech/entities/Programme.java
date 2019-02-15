package uk.co.aaditech.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.logging.log4j.util.Strings;

/**
 * 
 * tconst (string) - alphanumeric unique identifier of the title titleType
 * (string) – the type/format of the title (e.g. movie, short, tvseries,
 * tvepisode, video, etc) primaryTitle (string) – the more popular title / the
 * title used by the filmmakers on promotional materials at the point of release
 * originalTitle (string) - original title, in the original language isAdult
 * (boolean) - 0: non-adult title; 1: adult title startYear (YYYY) – represents
 * the release year of a title. In the case of TV Series, it is the series start
 * year endYear (YYYY) – TV Series end year. ‘\N’ for all other title types
 * runtimeMinutes – primary runtime of the title, in minutes genres (string
 * array) – includes up to three genres associated with the title
 * 
 * 
 * @author gautampachnanda
 *
 */

@Entity
@Table(name = "PROGRAMME")
public class Programme implements Serializable {

	private static final long serialVersionUID = -5340066906802932204L;

	private static final Object IS_ADULT_STRING_VALUE = "1";

	@Id
	@GeneratedValue(generator = "PROGRAMMES_SEQ")
	@SequenceGenerator(name = "PROGRAMMES_SEQ", sequenceName = "PROGRAMMES_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "TITLE")
	private String title;
	@Column(name = "IMDB_ID")
	private String imdbId;
	@Column(name = "PRIMARY_TITLE")
	private String primaryTitle;
	@Column(name = "ORIGINAL_TITLE")
	private String originalTitle;
	@Column(name = "ADULT")
	private boolean adult;
	@Column(name = "START_YEAR")
	private Integer startYear;
	@Column(name = "END_YEAR")
	private Integer endYear;
	@Column(name = "RUNTIME_MINUTES")
	private Integer runtime;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "programme")
	private List<Genre> genres;

	@Enumerated(EnumType.STRING)
	@Column(name = "TITLE_TYPE")
	private ProgrammeType programmeType;

	public Programme() {

	}

	public Programme(String title, String imdbId, String primaryTitle, String originalTitle, boolean adult,
			Integer startYear, Integer endYear, Integer runtime, List<Genre> genres, ProgrammeType programmeType) {
		super();
		this.title = title;
		this.imdbId = imdbId;
		this.primaryTitle = primaryTitle;
		this.originalTitle = originalTitle;
		this.adult = adult;
		this.startYear = startYear;
		this.endYear = endYear;
		this.runtime = runtime;
		this.genres = genres;
		this.programmeType = programmeType;
	}

	public Programme(String tsvString) {
		System.out.println(tsvString);
		String[] lineValues = tsvString.split("\\t");
		fromValues(lineValues);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrimaryTitle() {
		return primaryTitle;
	}

	public void setPrimaryTitle(String primaryTitle) {
		this.primaryTitle = primaryTitle;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public boolean isAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public Integer getStartYear() {
		return startYear;
	}

	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	public Integer getEndYear() {
		return endYear;
	}

	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	public Integer getRuntime() {
		return runtime;
	}

	public void setRuntime(Integer runtime) {
		this.runtime = runtime;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	public ProgrammeType getType() {
		return programmeType;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdb_id) {
		this.imdbId = imdb_id;
	}

	private void fromValues(String[] values) {
		for (String value : values) {
			System.out.println(value);
		}
		title = values[2];
		imdbId = values[0];
		primaryTitle = values[2];
		originalTitle = values[3];
		adult = IS_ADULT_STRING_VALUE.equals(values[4]);

		startYear = getIntegerValue(values[5]);
		endYear = getIntegerValue(values[6]);
		runtime = getIntegerValue(values[7]);
		genres = buildGenres(values[0]);
		programmeType = ProgrammeType.fromName(values[1]);
		for (Genre genre : genres) {
			genre.setProgramme(this);
		}
	}

	private List<Genre> buildGenres(String value) {
		List<Genre> genres = new ArrayList<>();
		if (Strings.isNotEmpty(value)) {
			String[] genreStrings = value.split(",");
			for (String genreString : genreStrings) {
				genres.add(new Genre(genreString));
			}
		}
		return genres;
	}

	private Integer getIntegerValue(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

	@Override
	public String toString() {
		return "Programme [id=" + id + ", title=" + title + ", imdbId=" + imdbId + ", primaryTitle=" + primaryTitle
				+ ", originalTitle=" + originalTitle + ", adult=" + adult + ", startYear=" + startYear + ", endYear="
				+ endYear + ", runtime=" + runtime + ", genres=" + genres + ", programmeType=" + programmeType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (adult ? 1231 : 1237);
		result = prime * result + ((endYear == null) ? 0 : endYear.hashCode());
		result = prime * result + ((genres == null) ? 0 : genres.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imdbId == null) ? 0 : imdbId.hashCode());
		result = prime * result + ((originalTitle == null) ? 0 : originalTitle.hashCode());
		result = prime * result + ((primaryTitle == null) ? 0 : primaryTitle.hashCode());
		result = prime * result + ((programmeType == null) ? 0 : programmeType.hashCode());
		result = prime * result + ((runtime == null) ? 0 : runtime.hashCode());
		result = prime * result + ((startYear == null) ? 0 : startYear.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Programme other = (Programme) obj;
		if (adult != other.adult)
			return false;
		if (endYear == null) {
			if (other.endYear != null)
				return false;
		} else if (!endYear.equals(other.endYear))
			return false;
		if (genres == null) {
			if (other.genres != null)
				return false;
		} else if (!genres.equals(other.genres))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imdbId == null) {
			if (other.imdbId != null)
				return false;
		} else if (!imdbId.equals(other.imdbId))
			return false;
		if (originalTitle == null) {
			if (other.originalTitle != null)
				return false;
		} else if (!originalTitle.equals(other.originalTitle))
			return false;
		if (primaryTitle == null) {
			if (other.primaryTitle != null)
				return false;
		} else if (!primaryTitle.equals(other.primaryTitle))
			return false;
		if (programmeType != other.programmeType)
			return false;
		if (runtime == null) {
			if (other.runtime != null)
				return false;
		} else if (!runtime.equals(other.runtime))
			return false;
		if (startYear == null) {
			if (other.startYear != null)
				return false;
		} else if (!startYear.equals(other.startYear))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
