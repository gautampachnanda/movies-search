package uk.co.aaditech.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "GENRE")
public class Genre implements Serializable {

	private static final long serialVersionUID = -6485072452672272638L;

	@Id
	@GeneratedValue(generator = "GENRES_SEQ")
	@SequenceGenerator(name = "GENRES_SEQ", sequenceName = "GENRES_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String genre;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "PROGRAMME_ID", nullable = false, referencedColumnName = "ID")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Programme programme;

	public Genre() {

	}

	public Genre(String genre) {
		this.setGenre(genre);
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setProgramme(Programme programme) {
		this.programme = programme;

	}

	public Programme getProgramme() {
		return programme;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((programme == null) ? 0 : programme.hashCode());
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
		Genre other = (Genre) obj;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (programme == null) {
			if (other.programme != null)
				return false;
		} else if (!programme.equals(other.programme))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Genre [id=" + id + ", genre=" + genre+"]";
	}

}
