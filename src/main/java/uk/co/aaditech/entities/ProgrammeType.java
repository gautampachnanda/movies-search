package uk.co.aaditech.entities;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ProgrammeType {

	MOVIE("movie"), TV_SERIES("tvSeries"), TV_EPISODE("tvEpisode"), VIDEO("video"), SHORT("short"), TV_SHORT(
			"tvShort"), TV_MOVIE("tvMovie"), TV_MINI_SERIES(
					"tvMiniSeries"), TV_SPECIAL("tvSpecial"), VIDEO_GAME("videoGame"), UNKNOWN("unknown");
	private static final Logger logger = LoggerFactory.getLogger(ProgrammeType.class);
	public final Map<String, ProgrammeType> lookup = new HashMap<String, ProgrammeType>();

	private String name;

	ProgrammeType(String name) {
		lookup.put(name, this);
		this.setName(name);
	}

	public static ProgrammeType fromName(String name) {
		for (ProgrammeType v : values()) {
			if (name.equals(v.getName())) {
				return v;
			}
		}
		logger.warn("new name {} not found in mappings", name);
		return UNKNOWN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
