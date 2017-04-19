package com.matthewtamlin.spyglass.library_tests.value_handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.value_handler_adapters.ValueHandlerAdapter;

import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public abstract class TestValueHandlerAdapter<V,
		A extends Annotation,
		H extends ValueHandlerAdapter<V, A>> {

	public abstract V getExpectedValue();

	public abstract TypedArray getTypedArrayContainingAttribute();

	public abstract TypedArray getTypedArrayMissingAttribute();

	public abstract A getAnnotation();

	public abstract H getAdapter();

	public abstract int getAttributeId();

	@Test
	public void testReflectiveInstantiation() throws Exception {
		getAdapter().getClass().newInstance();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_nullAnnotation() {
		getAdapter().getAccessor(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callValueExistsInArray_nullSupplied() {
		getAdapter().getAccessor(getAnnotation()).valueExistsInArray(null);
	}

	@Test
	public void testGetAccessor_callValueExistsInArray_valueAvailable() {
		final boolean result = getAdapter().getAccessor(getAnnotation())
				.valueExistsInArray(getTypedArrayContainingAttribute());

		assertThat("Accessor should report value as available.", result, is(true));
	}

	@Test
	public void testGetAccessor_callValueExistsInArray_valueMissing() {
		final boolean result = getAdapter().getAccessor(getAnnotation())
				.valueExistsInArray(getTypedArrayMissingAttribute());

		assertThat("Accessor should report value as missing.", result, is(false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callGetValueFromArray_nullSupplied() {
		getAdapter().getAccessor(getAnnotation()).getValueFromArray(null);
	}

	@Test
	public void testGetAccessor_callGetValueFromArray_valueAvailable() {
		final V value = getAdapter().getAccessor(getAnnotation())
				.getValueFromArray(getTypedArrayContainingAttribute());

		assertThat("Incorrect value returned by accessor.", value, is(getExpectedValue()));
	}

	@Test(expected = RuntimeException.class)
	public void testGetAccessor_callGetValueFromArray_valueMissing() {
		getAdapter().getAccessor(getAnnotation())
				.getValueFromArray(getTypedArrayMissingAttribute());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAttributeId_nullSupplied() {
		getAdapter().getAttributeId(null);
	}

	@Test
	public void testGetAttributeId_nonNullSupplied() {
		final int id = getAdapter().getAttributeId(getAnnotation());
		assertThat("Incorrect attribute ID returned.", id, is(getAttributeId()));
	}
}