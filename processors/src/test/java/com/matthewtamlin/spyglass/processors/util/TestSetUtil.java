package com.matthewtamlin.spyglass.processors.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(JUnit4.class)
public class TestSetUtil {
	@Test
	public void testImmutableSetOf_checkSetContainsExpectedValues() {
		final Set<Object> set = SetUtil.<Object>immutableSetOf(null, null, "hello", "world", "world", "world", 1);

		assertThat(set, hasSize(4));
		assertThat(set.contains(null), is(true));
		assertThat(set.contains("hello"), is(true));
		assertThat(set.contains("world"), is(true));
		assertThat(set.contains(1), is(true));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testImmutableSetOf_checkImmutability() {
		final Set<Object> set = SetUtil.immutableSetOf();

		set.add(new Object());
	}
}