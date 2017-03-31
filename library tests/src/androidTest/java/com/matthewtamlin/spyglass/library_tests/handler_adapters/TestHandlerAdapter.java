package com.matthewtamlin.spyglass.library_tests.handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.handler_adapters.HandlerAdapter;

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

	@Test
	public void testReflectiveInstantiation() throws Exception {
		getAdapter().getClass().newInstance();
	}

	@Test
	public void testAttributeValueIsAvailable_valueAvailable() {
		final boolean available = getAdapter().attributeValueIsAvailable(
				getTypedArrayContainingAttribute(),
				getAnnotationWithMandatoryFlag());

		assertThat(available, is(true));
	}

	@Test
	public void testAttributeValueIsAvailable_valueMissing() {
		final boolean available = getAdapter().attributeValueIsAvailable(
				getTypedArrayMissingAttribute(),
				getAnnotationWithMandatoryFlag());

		assertThat(available, is(false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAttributeValueIsAvailable_nullAttrs() {
		getAdapter().attributeValueIsAvailable(null, getAnnotationMissingMandatoryFlag());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAttributeValueIsAvailable_nullAnnotation() {
		getAdapter().attributeValueIsAvailable(getTypedArrayContainingAttribute(), null);
	}

	@Test
	public void testGetAttributeValue_valueAvailable() {
		final V value = getAdapter().getAttributeValue(
				getTypedArrayContainingAttribute(),
				getAnnotationWithMandatoryFlag());

		assertThat(value, is(getExpectedValue()));
	}

	@Test(expected = RuntimeException.class)
	public void testGetAttributeValue_valueMissing() {
		getAdapter().getAttributeValue(
				getTypedArrayMissingAttribute(),
				getAnnotationWithMandatoryFlag());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAttributeValue_nullAttrs() {
		getAdapter().getAttributeValue(null, getAnnotationMissingMandatoryFlag());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAttributeValue_nullAnnotation() {
		getAdapter().getAttributeValue(getTypedArrayContainingAttribute(), null);
	}

	@Test
	public void testAttributeIsMandatory_mandatoryFlagPresent() {
		final boolean mandatory = getAdapter().attributeIsMandatory(
				getAnnotationWithMandatoryFlag());

		assertThat(mandatory, is(true));
	}

	@Test
	public void testAttributeIsMandatory_mandatoryFlagMissing() {
		final boolean mandatory = getAdapter().attributeIsMandatory(
				getAnnotationMissingMandatoryFlag());

		assertThat(mandatory, is(false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAttributeIsMandatory_nullAnnotation() {
		getAdapter().attributeIsMandatory(null);
	}
}