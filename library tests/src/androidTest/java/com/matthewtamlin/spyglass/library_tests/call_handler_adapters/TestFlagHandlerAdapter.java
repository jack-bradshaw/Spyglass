package com.matthewtamlin.spyglass.library_tests.call_handler_adapters;

import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.call_handler_adapters.SpecificFlagHandlerAdapter;
import com.matthewtamlin.spyglass.library.call_handler_annotations.SpecificFlagHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResourceType")
@RunWith(AndroidJUnit4.class)
public class TestFlagHandlerAdapter {
	private static final int ATTRIBUTE_ID = 8527;

	private static final int FLAG_0 = 0b0;

	private static final int FLAG_1 = 0b1;

	private static final int FLAG_2 = 0b10;

	private static final int FLAG_3 = 0b100;

	private static final int FLAG_4 = 0b1000;

	private TypedArray missingAttribute;

	private TypedArray containingFlag0;

	private TypedArray containingFlag1;

	private TypedArray containingFlag2;

	private TypedArray containingFlags1And2;

	private TypedArray containingFlags2And3;

	private TypedArray containingFlags3And4;

	private SpecificFlagHandler handlesFlag1;

	private SpecificFlagHandler handlesFlag1And2;

	private SpecificFlagHandlerAdapter adapter;

	@Before
	public void setup() {
		missingAttribute = mock(TypedArray.class);
		when(missingAttribute.hasValue(ATTRIBUTE_ID)).thenReturn(false);
		when(missingAttribute.getInt(eq(ATTRIBUTE_ID), anyInt())).thenAnswer(new Answer<Object>() {
			@Override
			public Object answer(final InvocationOnMock invocation) throws Throwable {
				// Always return the second argument since it's the default
				return invocation.getArgumentAt(1, Integer.class);
			}
		});
		when(missingAttribute.getInteger(eq(ATTRIBUTE_ID), anyInt()))
				.thenAnswer(new Answer<Object>() {
					@Override
					public Object answer(final InvocationOnMock invocation) throws Throwable {
						// Always return the second argument since it's the default
						return invocation.getArgumentAt(1, Integer.class);
					}
				});

		containingFlag0 = mock(TypedArray.class);
		when(containingFlag0.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(containingFlag0.getInt(eq(ATTRIBUTE_ID), anyInt())).thenReturn(FLAG_0);
		when(containingFlag0.getInteger(eq(ATTRIBUTE_ID), anyInt())).thenReturn(FLAG_0);

		containingFlag1 = mock(TypedArray.class);
		when(containingFlag1.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(containingFlag1.getInt(eq(ATTRIBUTE_ID), anyInt())).thenReturn(FLAG_1);
		when(containingFlag1.getInteger(eq(ATTRIBUTE_ID), anyInt())).thenReturn(FLAG_1);

		containingFlag2 = mock(TypedArray.class);
		when(containingFlag2.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(containingFlag2.getInt(eq(ATTRIBUTE_ID), anyInt())).thenReturn(FLAG_2);
		when(containingFlag2.getInteger(eq(ATTRIBUTE_ID), anyInt())).thenReturn(FLAG_2);

		containingFlags1And2 = mock(TypedArray.class);
		when(containingFlags1And2.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(containingFlags1And2.getInt(eq(ATTRIBUTE_ID), anyInt())).thenReturn(FLAG_1 | FLAG_2);
		when(containingFlags1And2.getInteger(eq(ATTRIBUTE_ID), anyInt()))
				.thenReturn(FLAG_1 | FLAG_2);

		containingFlags2And3 = mock(TypedArray.class);
		when(containingFlags2And3.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(containingFlags2And3.getInt(eq(ATTRIBUTE_ID), anyInt())).thenReturn(FLAG_2 | FLAG_3);
		when(containingFlags2And3.getInteger(eq(ATTRIBUTE_ID), anyInt()))
				.thenReturn(FLAG_2 | FLAG_3);

		containingFlags3And4 = mock(TypedArray.class);
		when(containingFlags3And4.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(containingFlags3And4.getInt(eq(ATTRIBUTE_ID), anyInt())).thenReturn(FLAG_3 | FLAG_4);
		when(containingFlags3And4.getInteger(eq(ATTRIBUTE_ID), anyInt()))
				.thenReturn(FLAG_3 | FLAG_4);

		handlesFlag1 = mock(SpecificFlagHandler.class);
		when(handlesFlag1.attributeId()).thenReturn(ATTRIBUTE_ID);
		when(handlesFlag1.handledFlags()).thenReturn(FLAG_1);

		handlesFlag1And2 = mock(SpecificFlagHandler.class);
		when(handlesFlag1And2.attributeId()).thenReturn(ATTRIBUTE_ID);
		when(handlesFlag1And2.handledFlags()).thenReturn(FLAG_1 | FLAG_2);

		adapter = new SpecificFlagHandlerAdapter();
	}

	@Test
	public void testReflectiveInstantiation() throws Exception {
		SpecificFlagHandlerAdapter.class.newInstance();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShouldCallMethod_nullAnnotation() {
		adapter.shouldCallMethod(null, containingFlag1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShouldCallMethod_nullAttrs() {
		adapter.shouldCallMethod(handlesFlag1, null);
	}

	@Test
	public void testShouldCallMethod_handlesSingleFlag_missingAttribute() {
		final boolean result = adapter.shouldCallMethod(handlesFlag1, missingAttribute);
		assertThat(result, is(false));
	}

	@Test
	public void testShouldCallMethod_handlesSingleFlag_oneFlagNoMatch() {
		final boolean result = adapter.shouldCallMethod(handlesFlag1, containingFlag2);
		assertThat(result, is(false));
	}

	@Test
	public void testShouldCallMethod_handlesSingleFlag_oneFlagOneMatch() {
		final boolean result = adapter.shouldCallMethod(handlesFlag1, containingFlag1);
		assertThat(result, is(true));
	}

	@Test
	public void testShouldCallMethod_handlesSingleFlag_twoFlagsNoMatch() {
		final boolean result = adapter.shouldCallMethod(handlesFlag1And2, containingFlags3And4);
		assertThat(result, is(false));
	}

	@Test
	public void testShouldCallMethod_handlesSingleFlag_twoFlagsOneMatch() {
		final boolean result = adapter.shouldCallMethod(handlesFlag1, containingFlags1And2);
		assertThat(result, is(true));
	}

	@Test
	public void testShouldCallMethod_handlesMultipleFlags_missingAttribute() {
		final boolean result = adapter.shouldCallMethod(handlesFlag1And2, missingAttribute);
		assertThat(result, is(false));
	}

	@Test
	public void testShouldCallMethod_handlesMultipleFlags_oneFlagNoMatch() {
		final boolean result = adapter.shouldCallMethod(handlesFlag1And2, containingFlag0);
		assertThat(result, is(false));
	}

	@Test
	public void testShouldCallMethod_handlesMultipleFlags_oneFlagOneMatch() {
		final boolean result = adapter.shouldCallMethod(handlesFlag1And2, containingFlag2);
		assertThat(result, is(true));
	}

	@Test
	public void testShouldCallMethod_handlesMultipleFlags_twoFlagsNoMatch() {
		final boolean result = adapter.shouldCallMethod(handlesFlag1And2, containingFlags3And4);
		assertThat(result, is(false));
	}

	@Test
	public void testShouldCallMethod_handlesMultipleFlags_twoFlagsOneMatch() {
		final boolean result = adapter.shouldCallMethod(handlesFlag1And2, containingFlags2And3);
		assertThat(result, is(true));
	}

	@Test
	public void testShouldCallMethod_handlesMultipleFlags_twoFlagsTwoMatches() {
		final boolean result = adapter.shouldCallMethod(handlesFlag1And2, containingFlags1And2);
		assertThat(result, is(true));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAttributeId_nullSupplied() {
		adapter.getAttributeId(null);
	}

	@Test
	public void testGetAttributeId_nonNullSupplied() {
		final int result = adapter.getAttributeId(handlesFlag1);
		assertThat(result, is(ATTRIBUTE_ID));
	}
}