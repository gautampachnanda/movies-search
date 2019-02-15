package uk.co.aaditech.entities;

import org.junit.Test;

public class ProgrammeTypeTest {
	
	
	@Test
	public void test() {
		ProgrammeType a = ProgrammeType.fromName("short");
		System.out.println(a);
	}

}
