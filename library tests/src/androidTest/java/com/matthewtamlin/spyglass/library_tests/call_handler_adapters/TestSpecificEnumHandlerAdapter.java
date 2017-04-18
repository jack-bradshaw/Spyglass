package com.matthewtamlin.spyglass.library_tests.call_handler_adapters;

import android.content.res.TypedArray;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.call_handler_adapters.SpecificEnumHandlerAdapter;
import com.matthewtamlin.spyglass.library.call_handler_annotations.SpecificEnumHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResourceType")
@RunWith(AndroidJUnit4.class)
public class TestSpecificEnumHandlerAdapter {
	private static final int ATTRIBUTE_ID = 5927;

	private TypedArray withCorrectOrdinal;

	private TypedArray withWrongOrdinal;

	private TypedArray missingAttribute;

	private SpecificEnumHandler annotation;

	private SpecificEnumHandlerAdapter adapter;

	@Before
	public void setup() {
		final int correctOrdinal = 3;

		withCorrectOrdinal = mock(TypedArray.class);
		when(withCorrectOrdinal.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(withCorrectOrdinal.getInt(eq(ATTRIBUTE_ID), anyInt())).thenReturn(correctOrdinal);
		when(withCorrectOrdinal.getInteger(eq(ATTRIBUTE_ID), anyInt())).thenReturn(correctOrdinal);

		withWrongOrdinal = mock(TypedArray.class);
		when(withWrongOrdinal.hasValue(ATTRIBUTE_ID)).thenReturn(true);
		when(withWrongOrdinal.getInt(eq(ATTRIBUTE_ID), anyInt())).thenReturn(correctOrdinal - 1);
		when(withWrongOrdinal.getInteger(eq(ATTRIBUTE_ID), anyInt()))
				.thenReturn(correctOrdinal - 1);

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

		annotation = mock(SpecificEnumHandler.class);
		when(annotation.attributeId()).thenReturn(ATTRIBUTE_ID);
		when(annotation.ordinal()).thenReturn(correctOrdinal);

		adapter = new SpecificEnumHandlerAdapter();
	}

	@Test
	public void testReflectiveInstantiation() throws Exception {
		SpecificEnumHandlerAdapter.class.newInstance();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShouldCallMethod_nullAnnotation() {
		adapter.shouldCallMethod(null, missingAttribute);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShouldCallMethod_nullAttrs() {
		adapter.shouldCallMethod(annotation, null);
	}

	@Test
	public void testShouldCallMethod_attrsMissingValue() {
		final boolean result = adapter.shouldCallMethod(annotation, missingAttribute);
		assertThat(result, is(false));
	}

	@Test
	public void testShouldCallMethod_attrsHaveWrongOrdinal() {
		final boolean result = adapter.shouldCallMethod(annotation, withWrongOrdinal);
		assertThat(result, is(false));
	}

	@Test
	public void testShouldCallMethod_attrsHaveCorrectOrdinal() {
		final boolean result = adapter.shouldCallMethod(annotation, withCorrectOrdinal);
		assertThat(result, is(true));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAttributeId_nullSupplied() {
		adapter.getAttributeId(null);
	}

	@Test
	public void testGetAttributeId_nonNullSupplied() {
		final int attributeId = adapter.getAttributeId(annotation);

		assertThat(attributeId, is(ATTRIBUTE_ID));
	}

	public enum TestEnum {}
}