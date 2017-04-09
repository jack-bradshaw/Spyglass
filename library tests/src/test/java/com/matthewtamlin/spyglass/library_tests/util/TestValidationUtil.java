package com.matthewtamlin.spyglass.library_tests.util;

import com.matthewtamlin.spyglass.library.core.DimensionUnit;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBooleanResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorStateListResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDrawableResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToInteger;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToStringResource;
import com.matthewtamlin.spyglass.library.handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.IntegerHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.library.util.SpyglassValidationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestValidationUtil {
	@Test
	public void testValidateField_noHandlerAnnotation() {

	}

	@Test
	public void testValidateField_oneHandlerAnnotation() {

	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateField_twoHandlerAnnotations() {

	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateField_threeHandlerAnnotations() {

	}

	@Test
	public void testValidateField_oneDefaultAnnotation() {

	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateField_twoDefaultAnnotations() {

	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateField_threeDefaultAnnotations() {

	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateField_defaultAnnotationWithoutHandlerAnnotation() {

	}

	@Test
	public void testValidateMethod_noHandlerAnnotation() {

	}

	@Test
	public void testValidateMethod_oneHandlerAnnotation() {

	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_twoHandlerAnnotations() {

	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_threeHandlerAnnotations() {

	}

	@Test
	public void testValidateMethod_oneDefaultAnnotation() {

	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_twoDefaultAnnotations() {

	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_threeDefaultAnnotations() {

	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_defaultAnnotationWithoutHandlerAnnotation() {

	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_standardHandler_noArguments() {

	}

	@Test
	public void testValidateMethod_standardHandler_oneArgumentNoUseAnnotations() {

	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_standardHandler_oneArgumentOneUseAnnotation() {

	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_standardHandler_threeArgumentsNoUseAnnotations() {

	}

	@Test
	public void testValidateMethod_standardHandler_threeArgumentsTwoUseAnnotations() {

	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_standardHandler_threeArgumentsThreeUseAnnotations() {

	}

	@Test
	public void testValidateMethod_enumConstantHandler_noArguments() {

	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_enumConstantHandler_oneArgumentNoUseAnnotations() {

	}

	@Test
	public void testValidateMethod_enumConstantHandler_oneArgumentOneUseAnnotation() {

	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_enumConstantHandler_threeArgumentsNoUseAnnotations() {

	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_enumConstantHandler_threeArgumentsTwoUseAnnotations() {

	}

	@Test
	public void testValidateMethod_enumConstantHandler_threeArgumentsThreeUseAnnotations() {

	}

	@Test(expected = SpyglassValidationException.class)
	public void testValidateMethod_multipleUseAnnotationsOnOneParameter() {

	}

	@SuppressWarnings("unused")
	public class TestClass {
		private Object field1;

		@BooleanHandler(attributeId = 2)
		private Object field2;

		@FloatHandler(attributeId = 3)
		@DimensionHandler(attributeId = 3)
		private Object field3;

		@StringHandler(attributeId = 4)
		@IntegerHandler(attributeId = 4)
		@FractionHandler(attributeId = 4)
		private Object field4;

		@BooleanHandler(attributeId = 5)
		@DefaultToBoolean(true)
		private Object field5;

		@DimensionHandler(attributeId = 6)
		@DefaultToInteger(6)
		@DefaultToDrawableResource(6)
		private Object field6;

		@StringHandler(attributeId = 7)
		@DefaultToEnumConstant(enumClass = DimensionUnit.class, ordinal = 0)
		@DefaultToBooleanResource(7)
		@DefaultToStringResource(7)
		private Object field7;

		@DefaultToColorStateListResource(1)
		private Object field8;
	}
}