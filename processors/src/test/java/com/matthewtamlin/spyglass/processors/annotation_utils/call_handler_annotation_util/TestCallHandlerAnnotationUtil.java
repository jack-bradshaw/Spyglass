package com.matthewtamlin.spyglass.processors.annotation_utils.call_handler_annotation_util;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.java_compiler_utilities.element_supplier.CompilerMissingException;
import com.matthewtamlin.java_compiler_utilities.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.annotations.call_handler_annotations.SpecificFlagHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;

import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;

import static com.matthewtamlin.spyglass.annotations.units.DimensionUnit.DP;
import static com.matthewtamlin.spyglass.processors.annotation_utils.CallHandlerAnnotationUtil.getCallHandlerAnnotation;
import static com.matthewtamlin.spyglass.processors.annotation_utils.CallHandlerAnnotationUtil.hasCallHandlerAnnotation;
import static com.matthewtamlin.spyglass.processors.annotation_utils.CallHandlerAnnotationUtil.isCallHandlerAnnotation;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockBooleanHandler;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockColorHandler;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockColorStateListHandler;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDefaultToBoolean;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDefaultToBooleanResource;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDefaultToColorResource;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDefaultToColorStateListResource;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDefaultToDimension;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDefaultToDimensionResource;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDefaultToDrawableResource;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDefaultToEnumConstant;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDefaultToFloat;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDefaultToFractionResource;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDefaultToInteger;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDefaultToIntegerResource;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDefaultToNull;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDefaultToString;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDefaultToStringResource;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDefaultToTextArrayResource;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDefaultToTextResource;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDimensionHandler;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockDrawableHandler;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockEnumConstantHandler;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockEnumOrdinalHandler;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockFloatHandler;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockFractionHandler;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockIntegerHandler;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockSpecificEnumHandler;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockSpecificFlagHandler;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockStringHandler;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockTextArrayHandler;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockTextHandler;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockUseBoolean;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockUseByte;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockUseChar;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockUseDouble;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockUseFloat;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockUseInt;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockUseLong;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockUseNull;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockUseShort;
import static com.matthewtamlin.spyglass.processors.testing_utils.MockAnnotationsSupplier.createMockUseString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(JUnit4.class)
public class TestCallHandlerAnnotationUtil {
	private static final File DATA_FILE = new File("processors/src/test/java/com/matthewtamlin/spyglass/processors" +
			"/annotation_utils/call_handler_annotation_util/Data.java");

	private Element withSpecificEnumHandler;

	private Element withSpecificFlagHandler;

	private Element withNoCallHandler;

	@Before
	public void setup() throws MalformedURLException, CompilerMissingException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));

		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());
		final IdBasedElementSupplier elementSupplier = new IdBasedElementSupplier(dataFileObject);

		withSpecificEnumHandler = elementSupplier.getUniqueElementWithId("has specific enum handler");
		withSpecificFlagHandler = elementSupplier.getUniqueElementWithId("has specific flag handler");
		withNoCallHandler = elementSupplier.getUniqueElementWithId("has no call handler");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCallHandlerAnnotation_nullSupplied() {
		getCallHandlerAnnotation(null);
	}

	@Test
	public void testGetCallHandlerAnnotation_specificEnumHandlerAnnotationPresent() {
		final Annotation anno = getCallHandlerAnnotation(withSpecificEnumHandler);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetCallHandlerAnnotation_specificFlagHandlerAnnotationPresent() {
		final Annotation anno = getCallHandlerAnnotation(withSpecificFlagHandler);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetCallHandlerAnnotation_noCallHandlerAnnotationPresent() {
		final Annotation anno = getCallHandlerAnnotation(withNoCallHandler);

		assertThat(anno, is(nullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsCallHandlerAnnotation_nullSupplied() {
		isCallHandlerAnnotation(null);
	}

	@Test
	public void testIsCallHandlerAnnotation_specificEnumHandlerAnnotationSupplied() {
		final Annotation a = createMockSpecificEnumHandler(0, 0);

		assertThat(isCallHandlerAnnotation(a), is(true));
	}

	@Test
	public void testIsCallHandlerAnnotation_specificFlagHandlerAnnotationSupplied() {
		final Annotation a = createMockSpecificFlagHandler(0, 0);

		assertThat(isCallHandlerAnnotation(a), is(true));
	}

	@Test
	public void testIsCallHandlerAnnotation_defaultToBooleanAnnotationSupplied() {
		final Annotation a = createMockDefaultToBoolean(true);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_defaultToBooleanResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToBooleanResource(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_defaultToColorResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToColorResource(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_defaultToColorStateListResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToColorStateListResource(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_defaultToDimensionAnnotationSupplied() {
		final Annotation a = createMockDefaultToDimension(0, DP);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_defaultToDimensionResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToDimensionResource(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_defaultToDrawableResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToDrawableResource(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_defaultToEnumConstantAnnotationSupplied() {
		final Annotation a = createMockDefaultToEnumConstant(PlaceholderEnum.class, 0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_defaultToFloatAnnotationSupplied() {
		final Annotation a = createMockDefaultToFloat(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_defaultToFractionResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToFractionResource(0, 0, 0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_defaultToIntegerAnnotationSupplied() {
		final Annotation a = createMockDefaultToInteger(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_defaultToIntegerResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToIntegerResource(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_defaultToNullAnnotationSupplied() {
		final Annotation a = createMockDefaultToNull();

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_defaultToStringAnnotationSupplied() {
		final Annotation a = createMockDefaultToString("hello world");

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_defaultToStringResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToStringResource(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_defaultToTextArrayResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToTextArrayResource(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_defaultToTextResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToTextResource(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_useBooleanAnnotationSupplied() {
		final Annotation a = createMockUseBoolean(true);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_useByteAnnotationSupplied() {
		final Annotation a = createMockUseByte((byte) 0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_useCharAnnotationSupplied() {
		final Annotation a = createMockUseChar((char) 0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_useDoubleAnnotationSupplied() {
		final Annotation a = createMockUseDouble(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_useFloatAnnotationSupplied() {
		final Annotation a = createMockUseFloat(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_useIntAnnotationSupplied() {
		final Annotation a = createMockUseInt(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_useLongAnnotationSupplied() {
		final Annotation a = createMockUseLong(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_useNullAnnotationSupplied() {
		final Annotation a = createMockUseNull();

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_useShortAnnotationSupplied() {
		final Annotation a = createMockUseShort((short) 0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_useStringAnnotationSupplied() {
		final Annotation a = createMockUseString("hello world");

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_booleanHandlerAnnotationSupplied() {
		final Annotation a = createMockBooleanHandler(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_colorHandlerAnnotationSupplied() {
		final Annotation a = createMockColorHandler(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_colorStateListHandlerAnnotationSupplied() {
		final Annotation a = createMockColorStateListHandler(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_dimensionHandlerAnnotationSupplied() {
		final Annotation a = createMockDimensionHandler(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_drawableHandlerAnnotationSupplied() {
		final Annotation a = createMockDrawableHandler(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_enumConstantHandlerAnnotationSupplied() {
		final Annotation a = createMockEnumConstantHandler(0, PlaceholderEnum.class);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_enumOrdinalHandlerAnnotationSupplied() {
		final Annotation a = createMockEnumOrdinalHandler(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_floatHandlerAnnotationSupplied() {
		final Annotation a = createMockFloatHandler(0);
		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_fractionHandlerAnnotationSupplied() {
		final Annotation a = createMockFractionHandler(0, 0, 0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_integerHandlerAnnotationSupplied() {
		final Annotation a = createMockIntegerHandler(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_stringHandlerAnnotationSupplied() {
		final Annotation a = createMockStringHandler(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_textArrayHandlerAnnotationSupplied() {
		final Annotation a = createMockTextArrayHandler(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test
	public void testIsCallHandlerAnnotation_textHandlerAnnotationSupplied() {
		final Annotation a = createMockTextHandler(0);

		assertThat(isCallHandlerAnnotation(a), is(false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHasCallHandlerAnnotation_nullSupplied() {
		hasCallHandlerAnnotation(null);
	}

	@Test
	public void testHasCallHandlerAnnotation_specificEnumHandlerAnnotationPresent() {
		final boolean hasHandler = hasCallHandlerAnnotation(withSpecificEnumHandler);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasCallHandlerAnnotation_specificFlagHandlerAnnotationPresent() {
		final boolean hasHandler = hasCallHandlerAnnotation(withSpecificFlagHandler);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasCallHandlerAnnotation_noCallHandlerAnnotationPresent() {
		final boolean hasHandler = hasCallHandlerAnnotation(withNoCallHandler);

		assertThat(hasHandler, is(false));
	}

	private enum PlaceholderEnum {}
}