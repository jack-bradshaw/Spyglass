package com.matthewtamlin.spyglass.library_tests.handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.handler_adapters.EnumConstantHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_annotations.EnumConstantHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResourceType")
public class TestEnumConstantHandlerAdapter {
	private static final int ATTRIBUTE_ID = 5927;

	private static int CORRECT_ORDINAL = 2;

	private TypedArray containingAttributeWithCorrectOrdinal;

	private TypedArray containingAttributeWithWrongOrdinal;

	private TypedArray missingAttribute;

	private EnumConstantHandler annotation;

	private EnumConstantHandlerAdapter adapter;

	@Before
	public void setup() {
		containingAttributeWithCorrectOrdinal = mock(TypedArray.class);
		when(containingAttributeWithCorrectOrdinal.hasValue(ATTRIBUTE_ID))
				.thenReturn(true);
		when(containingAttributeWithCorrectOrdinal.getInt(eq(ATTRIBUTE_ID), anyInt()))
				.thenReturn(CORRECT_ORDINAL);
		when(containingAttributeWithCorrectOrdinal.getInteger(eq(ATTRIBUTE_ID), anyInt()))
				.thenReturn(CORRECT_ORDINAL);

		containingAttributeWithWrongOrdinal = mock(TypedArray.class);
		when(containingAttributeWithWrongOrdinal.hasValue(ATTRIBUTE_ID))
				.thenReturn(true);
		when(containingAttributeWithWrongOrdinal.getInt(eq(ATTRIBUTE_ID), anyInt()))
				.thenReturn(CORRECT_ORDINAL - 1);
		when(containingAttributeWithWrongOrdinal.getInteger(eq(ATTRIBUTE_ID), anyInt()))
				.thenReturn(CORRECT_ORDINAL - 1);

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

		annotation = mock(EnumConstantHandler.class);
		when(annotation.attributeId()).thenReturn(ATTRIBUTE_ID);
		doReturn(TestEnum.class).when(annotation).enumClass();
		when(annotation.ordinal()).thenReturn(CORRECT_ORDINAL);

		adapter = new EnumConstantHandlerAdapter();
	}

	@Test
	public void testReflectiveInstantiation() throws Exception {
		EnumConstantHandlerAdapter.class.newInstance();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_nullAnnotation() {
		adapter.getAccessor(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callValueExistsInArray_nullSupplied() {
		adapter.getAccessor(annotation).valueExistsInArray(null);
	}

	@Test
	public void testGetAccessor_callValueExistsInArray_valueAvailableAndCorrectOrdinal() {
		final boolean result = adapter.getAccessor(annotation)
				.valueExistsInArray(containingAttributeWithCorrectOrdinal);

		assertThat(result, is(true));
	}

	@Test
	public void testGetAccessor_callValueExistsInArray_valueAvailableAndIncorrectOrdinal() {
		final boolean result = adapter.getAccessor(annotation)
				.valueExistsInArray(containingAttributeWithWrongOrdinal);

		assertThat(result, is(false));
	}

	@Test
	public void testGetAccessor_callValueExistsInArray_valueMissing() {
		final boolean result = adapter.getAccessor(annotation).valueExistsInArray(missingAttribute);

		assertThat(result, is(false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callGetValueFromArray_nullSupplied() {
		adapter.getAccessor(annotation).getValueFromArray(null);
	}

	@Test
	public void testGetAccessor_callGetValueFromArray_valueAvailableAndCorrectOrdinal() {
		final Void value = adapter.getAccessor(annotation)
				.getValueFromArray(containingAttributeWithCorrectOrdinal);

		assertThat(value, is(nullValue()));
	}

	@Test(expected = RuntimeException.class)
	public void testGetAccessor_callGetValueFromArray_valueAvailableAndIncorrectOrdinal() {
		adapter.getAccessor(annotation).getValueFromArray(containingAttributeWithWrongOrdinal);
	}

	@Test(expected = RuntimeException.class)
	public void testGetAccessor_callGetValueFromArray_valueMissing() {
		adapter.getAccessor(annotation).getValueFromArray(missingAttribute);
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

	@Test(expected = IllegalArgumentException.class)
	public void testIsMandatory_nullAnnotation() {
		adapter.isMandatory(null);
	}

	@Test
	public void testIsMandatory_nonNullSupplied() {
		final boolean mandatory = adapter.isMandatory(annotation);

		assertThat(mandatory, is(false));
	}

	public enum TestEnum {
		ITEM_1,
		ITEM_2,
		ITEM_3,
		ITEM_4
	}
}