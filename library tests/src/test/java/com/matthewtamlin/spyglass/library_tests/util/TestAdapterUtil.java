package com.matthewtamlin.spyglass.library_tests.util;

import com.matthewtamlin.spyglass.library.util.AdapterUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@RunWith(JUnit4.class)
public class TestAdapterUtil {
	@Test(expected = IllegalArgumentException.class)
	public void testGetHandlerAdapter_fieldVariant_nullField() {
		AdapterUtil.getHandlerAdapter((Field) null);
	}

	@Test
	public void testGetHandlerAdapter_fieldVariant_noHandlerAnnotations() {

	}

	@Test
	public void testGetHandlerAdapter_fieldVariant_oneHandlerAnnotation() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetHandlerAdapter_methodVariant_nullMethod() {
		AdapterUtil.getHandlerAdapter((Method) null);
	}

	@Test
	public void testGetHandlerAdapter_methodVariant_noHandlerAnnotations() {

	}

	@Test
	public void testGetHandlerAdapter_methodVariant_oneHandlerAnnotation() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAdapter_fieldVariant_nullField() {
		AdapterUtil.getDefaultAdapter((Field) null);
	}

	@Test
	public void testGetDefaultAdapter_fieldVariant_noDefaultAnnotations() {

	}

	@Test
	public void testGetDefaultAdapter_fieldVariant_oneDefaultAnnotation() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAdapter_methodVariant_nullMethod() {
		AdapterUtil.getDefaultAdapter((Method) null);
	}

	@Test
	public void testGetDefaultAdapter_methodVariant_noDefaultAnnotations() {

	}

	@Test
	public void testGetHandlerAdapter_methodVariant_oneDefaultAnnotation() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUseAdapters_nullMethod() {
		AdapterUtil.getUseAdapters(null);
	}

	@Test
	public void testGetUseAdapters_noArguments() {

	}

	@Test
	public void testGetUseAdapters_oneArgument_noUseAnnotations() {

	}

	@Test
	public void testGetUseAdapters_oneArgument_oneUseAnnotation() {

	}

	@Test
	public void testGetUseAdapters_threeArguments_twoUseAnnotations() {

	}

	@Test
	public void testGetUseAdapters_threeArguments_threeUseAnnotations() {

	}
}