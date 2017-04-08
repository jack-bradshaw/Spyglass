package com.matthewtamlin.spyglass.library_tests.util;

import org.junit.Test;

public class TestEnumUtil {
	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumConstant_nullClassSupplied() {
		
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumConstant_nonEnumClassSupplied() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumConstant_ordinalIsNegativeOne() {

	}

	@Test
	public void testGetEnumConstant_ordinalIsZero() {

	}

	@Test
	public void testGetEnumConstant_ordinalIsMax() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumConstant_ordinalExceedsMax() {

	}

	private enum TestEnum {
		CONST1,
		CONST2,
		CONST3,
		CONST4
	}
}