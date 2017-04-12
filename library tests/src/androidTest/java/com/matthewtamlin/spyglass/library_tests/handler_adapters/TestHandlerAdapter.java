package com.matthewtamlin.spyglass.library_tests.handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.value_handler_adapters.HandlerAdapter;

import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public abstract class TestHandlerAdapter<V,
		A extends Annotation,
		H extends HandlerAdapter<V, A>> {

	public abstract V getExpectedValue();

	public abstract TypedArray getTypedArrayContainingAttribute();

	public abstract TypedArray getTypedArrayMissingAttribute();

	public abstract A getAnnotationWithMandatoryFlag();

	public abstract A getAnnotationMissingMandatoryFlag();

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
		getAdapter().getAccessor(getAnnotationWithMandatoryFlag()).valueExistsInArray(null);
	}

	@Test
	public void testGetAccessor_callValueExistsInArray_valueAvailable() {
		final boolean result = getAdapter().getAccessor(getAnnotationWithMandatoryFlag())
				.valueExistsInArray(getTypedArrayContainingAttribute());

		assertThat(result, is(true));
	}

	@Test
	public void testGetAccessor_callValueExistsInArray_valueMissing() {
		final boolean result = getAdapter().getAccessor(getAnnotationWithMandatoryFlag())
				.valueExistsInArray(getTypedArrayMissingAttribute());

		assertThat(result, is(false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAccessor_callGetValueFromArray_nullSupplied() {
		getAdapter().getAccessor(getAnnotationWithMandatoryFlag()).getValueFromArray(null);
	}

	@Test
	public void testGetAccessor_callGetValueFromArray_valueAvailable() {
		final V value = getAdapter().getAccessor(getAnnotationWithMandatoryFlag())
				.getValueFromArray(getTypedArrayContainingAttribute());

		assertThat(value, is(getExpectedValue()));
	}

	@Test(expected = RuntimeException.class)
	public void testGetAccessor_callGetValueFromArray_valueMissing() {
		getAdapter().getAccessor(getAnnotationWithMandatoryFlag())
				.getValueFromArray(getTypedArrayMissingAttribute());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAttributeId_nullSupplied() {
		getAdapter().getAttributeId(null);
	}

	@Test
	public void testGetAttributeId_nonNullSupplied() {
		//TODO
	}

	@Test
	public void testIsMandatory_mandatoryFlagPresent() {
		final boolean mandatory = getAdapter().isMandatory(
				getAnnotationWithMandatoryFlag());

		assertThat(mandatory, is(true));
	}

	@Test
	public void testIsMandatory_mandatoryFlagMissing() {
		final boolean mandatory = getAdapter().isMandatory(
				getAnnotationMissingMandatoryFlag());

		assertThat(mandatory, is(false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsMandatory_nullAnnotation() {
		getAdapter().isMandatory(null);
	}
}