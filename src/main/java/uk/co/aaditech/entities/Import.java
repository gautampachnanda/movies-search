package uk.co.aaditech.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "IMPORT")
public class Import implements Serializable{

	private static final long serialVersionUID = -1976148582230975970L;

	@Id
	@GeneratedValue(generator = "IMPORT_SEQ")
	@SequenceGenerator(name = "IMPORT_SEQ", sequenceName = "IMPORT_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "CHECKSUM")
	private String checksum;
	
	public Import() {
		
	}
	
	public Import(String checksum) {
		this.checksum=checksum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checksum == null) ? 0 : checksum.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Import other = (Import) obj;
		if (checksum == null) {
			if (other.checksum != null)
				return false;
		} else if (!checksum.equals(other.checksum))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Import [id=" + id + ", checksum=" + checksum + "]";
	}
	
	
	
}
