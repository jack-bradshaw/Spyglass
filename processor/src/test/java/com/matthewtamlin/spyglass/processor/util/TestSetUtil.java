package com.matthewtamlin.spyglass.processor.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashSet;
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

	@Test(expected = IllegalArgumentException.class)
	public void testAllToString_nullSupplied() {
		SetUtil.allToString(null);
	}

	@Test
	public void testAllToString_emptySetSupplied() {
		final Set<Object> items = new HashSet<>();

		final Set<String> stringSet = SetUtil.allToString(items);

		assertThat(stringSet, is((Set) new HashSet<>()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAllToString_setContainsNull() {
		final Set<Object> items = new HashSet<>();

		items.add(null);

		SetUtil.allToString(items);
	}

	@Test
	public void testAllToString_anElementReturnsNullString() {
		final Set<Object> items = new HashSet<>();

		items.add(new Object() {
			@Override
			public String toString() {
				return null;
			}
		});

		final Set<String> stringSet = SetUtil.allToString(items);

		final Set<String> expectedStrings = new HashSet<>();
		expectedStrings.add(null);

		assertThat(stringSet, is(expectedStrings));
	}

	@Test
	public void testAllToString_oneElementOnly() {
		final Set<Object> items = new HashSet<>();

		items.add(new Object() {
			@Override
			public String toString() {
				return "item0";
			}
		});

		final Set<String> stringSet = SetUtil.allToString(items);

		final Set<String> expectedStrings = new HashSet<>();
		expectedStrings.add("item0");

		assertThat(stringSet, is(expectedStrings));
	}

	@Test
	public void testAllToString_multipleElements_uniqueStrings() {
		final Set<Object> items = new HashSet<>();

		items.add(new Object() {
			@Override
			public String toString() {
				return "item0";
			}
		});

		items.add(new Object() {
			@Override
			public String toString() {
				return "item1";
			}
		});

		final Set<String> stringSet = SetUtil.allToString(items);

		final Set<String> expectedStrings = new HashSet<>();
		expectedStrings.add("item0");
		expectedStrings.add("item1");

		assertThat(stringSet, is(expectedStrings));
	}

	@Test
	public void testAllToString_multipleElements_duplicateStrings() {
		final Set<Object> items = new HashSet<>();

		items.add(new Object() {
			@Override
			public String toString() {
				return "item0";
			}
		});

		items.add(new Object() {
			@Override
			public String toString() {
				return "item0";
			}
		});

		final Set<String> stringSet = SetUtil.allToString(items);

		final Set<String> expectedStrings = new HashSet<>();
		expectedStrings.add("item0");

		assertThat(stringSet, is(expectedStrings));
	}
}