package com.matthewtamlin.spyglass.processor.util;

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
	public void testUnmodifiableSetOf_checkSetContainsExpectedValues() {
		final Set<Object> set = SetUtil.<Object>unmodifiableSetOf(null, null, "hello", "world", "world", "world", 1);

		assertThat(set, hasSize(4));
		assertThat(set.contains(null), is(true));
		assertThat(set.contains("hello"), is(true));
		assertThat(set.contains("world"), is(true));
		assertThat(set.contains(1), is(true));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUnmodifiableSetOf_checkSetCannotBeModified() {
		final Set<Object> set = SetUtil.unmodifiableSetOf();

		set.add(new Object());
	}
}