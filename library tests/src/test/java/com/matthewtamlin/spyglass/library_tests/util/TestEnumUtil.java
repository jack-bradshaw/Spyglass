package com.matthewtamlin.spyglass.library_tests.util;

import com.matthewtamlin.spyglass.library.util.EnumUtil;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TestEnumUtil {
	private static final int MAX_ORDINAL = TestEnum.values().length - 1;

	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumConstant_nullClassSupplied() {
		EnumUtil.getEnumConstant(null, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumConstant_ordinalIsNegativeOne() {
		EnumUtil.getEnumConstant(TestEnum.class, -1);
	}

	@Test
	public void testGetEnumConstant_ordinalIsZero() {
		final TestEnum returnedValue = EnumUtil.getEnumConstant(TestEnum.class, 0);

		assertThat(returnedValue, is(TestEnum.values()[0]));
	}

	@Test
	public void testGetEnumConstant_ordinalIsMax() {
		final TestEnum returnedValue = EnumUtil.getEnumConstant(TestEnum.class, MAX_ORDINAL);

		assertThat(returnedValue, is(TestEnum.values()[MAX_ORDINAL]));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetEnumConstant_ordinalExceedsMax() {
		EnumUtil.getEnumConstant(TestEnum.class, MAX_ORDINAL + 1);
	}

	public enum TestEnum {
		CONST1,
		CONST2,
		CONST3,
		CONST4
	}
}