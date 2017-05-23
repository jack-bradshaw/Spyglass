package com.matthewtamlin.spyglass.processors.annotation_utils.default_annotation_util;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.java_compiler_utilities.element_supplier.CompilerMissingException;
import com.matthewtamlin.java_compiler_utilities.element_supplier.IdBasedElementSupplier;

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
import static com.matthewtamlin.spyglass.processors.annotation_utils.DefaultAnnotationUtil.getDefaultAnnotation;
import static com.matthewtamlin.spyglass.processors.annotation_utils.DefaultAnnotationUtil.hasDefaultAnnotation;
import static com.matthewtamlin.spyglass.processors.annotation_utils.DefaultAnnotationUtil.isDefaultAnnotation;
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
public class TestDefaultAnnotationUtil {
	private static final File DATA_FILE = new File("processors/src/test/java/com/matthewtamlin/spyglass/processors" +
			"/annotation_utils/default_annotation_util/Data.java");

	private Element withDefaultToBoolean;

	private Element withDefaultToBooleanResource;

	private Element withDefaultToColorResource;

	private Element withDefaultToColorStateListResource;

	private Element withDefaultToDimension;

	private Element withDefaultToDimensionResource;

	private Element withDefaultToDrawableResource;

	private Element withDefaultToEnumConstant;

	private Element withDefaultToFloat;

	private Element withDefaultToFractionResource;

	private Element withDefaultToInteger;

	private Element withDefaultToIntegerResource;

	private Element withDefaultToNull;

	private Element withDefaultToString;

	private Element withDefaultToStringResource;

	private Element withDefaultToTextArrayResource;

	private Element withDefaultToTextResource;

	private Element withNoDefaultAnnotation;

	@Before
	public void setup() throws MalformedURLException, CompilerMissingException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));

		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());
		final IdBasedElementSupplier elementSupplier = new IdBasedElementSupplier(dataFileObject);

		withDefaultToBoolean = elementSupplier.getUniqueElementWithId("boolean");
		withDefaultToBooleanResource = elementSupplier.getUniqueElementWithId("boolean resource");
		withDefaultToColorResource = elementSupplier.getUniqueElementWithId("color resource");
		withDefaultToColorStateListResource = elementSupplier.getUniqueElementWithId("color state list resource");
		withDefaultToDimension = elementSupplier.getUniqueElementWithId("dimension");
		withDefaultToDimensionResource = elementSupplier.getUniqueElementWithId("dimension resource");
		withDefaultToDrawableResource = elementSupplier.getUniqueElementWithId("drawable resource");
		withDefaultToEnumConstant = elementSupplier.getUniqueElementWithId("enum constant");
		withDefaultToFloat = elementSupplier.getUniqueElementWithId("float");
		withDefaultToFractionResource = elementSupplier.getUniqueElementWithId("fraction resource");
		withDefaultToInteger = elementSupplier.getUniqueElementWithId("integer");
		withDefaultToIntegerResource = elementSupplier.getUniqueElementWithId("integer resource");
		withDefaultToNull = elementSupplier.getUniqueElementWithId("null");
		withDefaultToString = elementSupplier.getUniqueElementWithId("string");
		withDefaultToStringResource = elementSupplier.getUniqueElementWithId("string resource");
		withDefaultToTextArrayResource = elementSupplier.getUniqueElementWithId("text array resource");
		withDefaultToTextResource = elementSupplier.getUniqueElementWithId("text resource");
		withNoDefaultAnnotation = elementSupplier.getUniqueElementWithId("no default annotation");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAnnotation_nullSupplied() {
		getDefaultAnnotation(null);
	}

	@Test
	public void testGetDefaultAnnotation_defaultToBooleanAnnotationPresent() {
		final Annotation anno = getDefaultAnnotation(withDefaultToBoolean);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_defaultToBooleanResourceAnnotationPresent() {
		final Annotation anno = getDefaultAnnotation(withDefaultToBooleanResource);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_defaultToColorResourceAnnotationPresent() {
		final Annotation anno = getDefaultAnnotation(withDefaultToColorResource);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_defaultToColorStateListResourceAnnotationPresent() {
		final Annotation anno = getDefaultAnnotation(withDefaultToColorStateListResource);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_defaultToDimensionAnnotationPresent() {
		final Annotation anno = getDefaultAnnotation(withDefaultToDimension);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_defaultToDimensionResourceAnnotationPresent() {
		final Annotation anno = getDefaultAnnotation(withDefaultToDimensionResource);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_defaultToDrawableResourceAnnotationPresent() {
		final Annotation anno = getDefaultAnnotation(withDefaultToDrawableResource);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_defaultToEnumConstantAnnotationPresent() {
		final Annotation anno = getDefaultAnnotation(withDefaultToEnumConstant);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_defaultToFloatAnnotationPresent() {
		final Annotation anno = getDefaultAnnotation(withDefaultToFloat);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_defaultToFractionResourceAnnotationPresent() {
		final Annotation anno = getDefaultAnnotation(withDefaultToFractionResource);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_defaultToIntegerAnnotationPresent() {
		final Annotation anno = getDefaultAnnotation(withDefaultToInteger);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_defaultToIntegerResourceAnnotationPresent() {
		final Annotation anno = getDefaultAnnotation(withDefaultToIntegerResource);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_defaultToNullAnnotationPresent() {
		final Annotation anno = getDefaultAnnotation(withDefaultToNull);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_defaultToStringAnnotationPresent() {
		final Annotation anno = getDefaultAnnotation(withDefaultToString);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_defaultToStringResourceAnnotationPresent() {
		final Annotation anno = getDefaultAnnotation(withDefaultToStringResource);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_defaultToTextArrayResourceAnnotationPresent() {
		final Annotation anno = getDefaultAnnotation(withDefaultToTextArrayResource);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_defaultToTextResourceAnnotationPresent() {
		final Annotation anno = getDefaultAnnotation(withDefaultToTextResource);

		assertThat(anno, is(notNullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_noDefaultAnnotationPresent() {
		final Annotation anno = getDefaultAnnotation(withNoDefaultAnnotation);

		assertThat(anno, is(nullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsDefaultAnnotation_nullSupplied() {
		isDefaultAnnotation(null);
	}

	@Test
	public void testIsDefaultAnnotation_specificEnumHandlerAnnotationSupplied() {
		final Annotation a = createMockSpecificEnumHandler(0, 0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_specificFlagHandlerAnnotationSupplied() {
		final Annotation a = createMockSpecificFlagHandler(0, 0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_defaultToBooleanAnnotationSupplied() {
		final Annotation a = createMockDefaultToBoolean(true);
		assertThat(isDefaultAnnotation(a), is(true));
	}

	@Test
	public void testIsDefaultAnnotation_defaultToBooleanResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToBooleanResource(0);
		assertThat(isDefaultAnnotation(a), is(true));
	}

	@Test
	public void testIsDefaultAnnotation_defaultToColorResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToColorResource(0);
		assertThat(isDefaultAnnotation(a), is(true));
	}

	@Test
	public void testIsDefaultAnnotation_defaultToColorStateListResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToColorStateListResource(0);
		assertThat(isDefaultAnnotation(a), is(true));
	}

	@Test
	public void testIsDefaultAnnotation_defaultToDimensionAnnotationSupplied() {
		final Annotation a = createMockDefaultToDimension(0, DP);
		assertThat(isDefaultAnnotation(a), is(true));
	}

	@Test
	public void testIsDefaultAnnotation_defaultToDimensionResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToDimensionResource(0);
		assertThat(isDefaultAnnotation(a), is(true));
	}

	@Test
	public void testIsDefaultAnnotation_defaultToDrawableResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToDrawableResource(0);
		assertThat(isDefaultAnnotation(a), is(true));
	}

	@Test
	public void testIsDefaultAnnotation_defaultToEnumConstantAnnotationSupplied() {
		final Annotation a = createMockDefaultToEnumConstant(PlaceholderEnum.class, 0);
		assertThat(isDefaultAnnotation(a), is(true));
	}

	@Test
	public void testIsDefaultAnnotation_defaultToFloatAnnotationSupplied() {
		final Annotation a = createMockDefaultToFloat(0);
		assertThat(isDefaultAnnotation(a), is(true));
	}

	@Test
	public void testIsDefaultAnnotation_defaultToFractionResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToFractionResource(0, 0, 0);
		assertThat(isDefaultAnnotation(a), is(true));
	}

	@Test
	public void testIsDefaultAnnotation_defaultToIntegerAnnotationSupplied() {
		final Annotation a = createMockDefaultToInteger(0);
		assertThat(isDefaultAnnotation(a), is(true));
	}

	@Test
	public void testIsDefaultAnnotation_defaultToIntegerResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToIntegerResource(0);
		assertThat(isDefaultAnnotation(a), is(true));
	}

	@Test
	public void testIsDefaultAnnotation_defaultToNullAnnotationSupplied() {
		final Annotation a = createMockDefaultToNull();
		assertThat(isDefaultAnnotation(a), is(true));
	}

	@Test
	public void testIsDefaultAnnotation_defaultToStringAnnotationSupplied() {
		final Annotation a = createMockDefaultToString("hello world");
		assertThat(isDefaultAnnotation(a), is(true));
	}

	@Test
	public void testIsDefaultAnnotation_defaultToStringResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToStringResource(0);
		assertThat(isDefaultAnnotation(a), is(true));
	}

	@Test
	public void testIsDefaultAnnotation_defaultToTextArrayResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToTextArrayResource(0);
		assertThat(isDefaultAnnotation(a), is(true));
	}

	@Test
	public void testIsDefaultAnnotation_defaultToTextResourceAnnotationSupplied() {
		final Annotation a = createMockDefaultToTextResource(0);
		assertThat(isDefaultAnnotation(a), is(true));
	}

	@Test
	public void testIsDefaultAnnotation_useBooleanAnnotationSupplied() {
		final Annotation a = createMockUseBoolean(true);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_useByteAnnotationSupplied() {
		final Annotation a = createMockUseByte((byte) 0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_useCharAnnotationSupplied() {
		final Annotation a = createMockUseChar((char) 0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_useDoubleAnnotationSupplied() {
		final Annotation a = createMockUseDouble(0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_useFloatAnnotationSupplied() {
		final Annotation a = createMockUseFloat(0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_useIntAnnotationSupplied() {
		final Annotation a = createMockUseInt(0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_useLongAnnotationSupplied() {
		final Annotation a = createMockUseLong(0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_useNullAnnotationSupplied() {
		final Annotation a = createMockUseNull();
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_useShortAnnotationSupplied() {
		final Annotation a = createMockUseShort((short) 0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_useStringAnnotationSupplied() {
		final Annotation a = createMockUseString("hello world");
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_booleanHandlerAnnotationSupplied() {
		final Annotation a = createMockBooleanHandler(0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_colorHandlerAnnotationSupplied() {
		final Annotation a = createMockColorHandler(0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_colorStateListHandlerAnnotationSupplied() {
		final Annotation a = createMockColorStateListHandler(0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_dimensionHandlerAnnotationSupplied() {
		final Annotation a = createMockDimensionHandler(0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_drawableHandlerAnnotationSupplied() {
		final Annotation a = createMockDrawableHandler(0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_enumConstantHandlerAnnotationSupplied() {
		final Annotation a = createMockEnumConstantHandler(0, PlaceholderEnum.class);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_enumOrdinalHandlerAnnotationSupplied() {
		final Annotation a = createMockEnumOrdinalHandler(0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_floatHandlerAnnotationSupplied() {
		final Annotation a = createMockFloatHandler(0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_fractionHandlerAnnotationSupplied() {
		final Annotation a = createMockFractionHandler(0, 0, 0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_integerHandlerAnnotationSupplied() {
		final Annotation a = createMockIntegerHandler(0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_stringHandlerAnnotationSupplied() {
		final Annotation a = createMockStringHandler(0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_textArrayHandlerAnnotationSupplied() {
		final Annotation a = createMockTextArrayHandler(0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test
	public void testIsDefaultAnnotation_textHandlerAnnotationSupplied() {
		final Annotation a = createMockTextHandler(0);
		assertThat(isDefaultAnnotation(a), is(false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHasDefaultAnnotation_nullSupplied() {
		hasDefaultAnnotation(null);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToBooleanAnnotationPresent() {
		final boolean hasHandler = hasDefaultAnnotation(withDefaultToBoolean);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToBooleanResourceAnnotationPresent() {
		final boolean hasHandler = hasDefaultAnnotation(withDefaultToBooleanResource);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToColorResourceAnnotationPresent() {
		final boolean hasHandler = hasDefaultAnnotation(withDefaultToColorResource);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToColorStateListResourceAnnotationPresent() {
		final boolean hasHandler = hasDefaultAnnotation(withDefaultToColorStateListResource);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToDimensionAnnotationPresent() {
		final boolean hasHandler = hasDefaultAnnotation(withDefaultToDimension);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToDimensionResourceAnnotationPresent() {
		final boolean hasHandler = hasDefaultAnnotation(withDefaultToDimensionResource);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToDrawableResourceAnnotationPresent() {
		final boolean hasHandler = hasDefaultAnnotation(withDefaultToDrawableResource);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToEnumConstantAnnotationPresent() {
		final boolean hasHandler = hasDefaultAnnotation(withDefaultToEnumConstant);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToFloatAnnotationPresent() {
		final boolean hasHandler = hasDefaultAnnotation(withDefaultToFloat);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToFractionResourceAnnotationPresent() {
		final boolean hasHandler = hasDefaultAnnotation(withDefaultToFractionResource);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToIntegerAnnotationPresent() {
		final boolean hasHandler = hasDefaultAnnotation(withDefaultToInteger);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToIntegerResourceAnnotationPresent() {
		final boolean hasHandler = hasDefaultAnnotation(withDefaultToIntegerResource);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToNullAnnotationPresent() {
		final boolean hasHandler = hasDefaultAnnotation(withDefaultToNull);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToStringAnnotationPresent() {
		final boolean hasHandler = hasDefaultAnnotation(withDefaultToString);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToStringResourceAnnotationPresent() {
		final boolean hasHandler = hasDefaultAnnotation(withDefaultToStringResource);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToTextArrayResourceAnnotationPresent() {
		final boolean hasHandler = hasDefaultAnnotation(withDefaultToTextArrayResource);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToTextResourceAnnotationPresent() {
		final boolean hasHandler = hasDefaultAnnotation(withDefaultToTextResource);

		assertThat(hasHandler, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_noDefaultAnnotationPresent() {
		final boolean hasHandler = hasDefaultAnnotation(withNoDefaultAnnotation);

		assertThat(hasHandler, is(false));
	}


	private enum PlaceholderEnum {}
}