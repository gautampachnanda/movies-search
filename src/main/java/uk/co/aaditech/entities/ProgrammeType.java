package uk.co.aaditech.entities;

import java.util.HashMap;
import java.util.Map;

public enum ProgrammeType {

	MOVIE("movie"), TV_SERIES("tvSeries"), TV_EPISODE("tvEpisode"), VIDEO("video"), SHORT("short"), TV_SHORT("tvShort"), TV_MOVIE("tvMovie"),TV_MINI_SERIES("tvMiniSeries");

	public final Map<String, ProgrammeType> lookup = new HashMap<String, ProgrammeType>();

	private String name;

	ProgrammeType(String name) {
		lookup.put(name, this);
		this.setName(name);
	}

	public static ProgrammeType fromName(String name) {
		for (ProgrammeType v : values()) {
			if(name.equals(v.getName())) {
				return v;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
