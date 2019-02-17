package uk.co.aaditech.entities;

import org.junit.Test;

public class ProgrammeTest {

	
	@Test
	public void createObjectfromTSV() {
		Programme p=new Programme("tt0000001	short	Carmencita	Carmencita	0	1894	\\N	1	Documentary,Short");
		System.out.println( p.getGenres());
	}
}
