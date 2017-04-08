package com.matthewtamlin.spyglass.library_tests.util;

import com.matthewtamlin.spyglass.library.util.EnumUtil;

import org.junit.Test;

public class TestEnumUtil {
	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumConstant_nullClassSupplied() {
		EnumUtil.getEnumConstant(null, 0);
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