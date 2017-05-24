package com.matthewtamlin.spyglass.processors.annotation_utils.default_annotation_util;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.java_compiler_utilities.element_supplier.CompilerMissingException;
import com.matthewtamlin.java_compiler_utilities.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToBooleanResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToColorResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToColorStateListResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToDimension;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToDimensionResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToDrawableResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToFloat;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToFractionResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToInteger;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToIntegerResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToStringResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToTextArrayResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToTextResource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.net.MalformedURLException;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.tools.JavaFileObject;

import static com.matthewtamlin.spyglass.processors.annotation_utils.DefaultAnnotationUtil.getDefaultAnnotationMirror;
import static com.matthewtamlin.spyglass.processors.annotation_utils.DefaultAnnotationUtil.hasDefaultAnnotation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(JUnit4.class)
public class TestDefaultAnnotationUtil {
	private static final File DATA_FILE = new File("processors/src/test/java/com/matthewtamlin/spyglass/processors" +
			"/annotation_utils/default_annotation_util/Data.java");

	private IdBasedElementSupplier elementSupplier;

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

		elementSupplier = new IdBasedElementSupplier(dataFileObject);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAnnotationMirror_nullSupplied() {
		getDefaultAnnotationMirror(null);
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToBooleanAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("boolean");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToBoolean.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToBooleanResourceAnnotationPresent()
			throws CompilerMissingException {

		final ExecutableElement element = getExecutableElementWithId("boolean resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToBooleanResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToColorResourceAnnotationPresent()
			throws CompilerMissingException {

		final ExecutableElement element = getExecutableElementWithId("color resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToColorResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToColorStateListResourceAnnotationPresent()
			throws CompilerMissingException {

		final ExecutableElement element = getExecutableElementWithId("color state list resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToColorStateListResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToDimensionAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("dimension");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToDimension.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToDimensionResourceAnnotationPresent()
			throws CompilerMissingException {

		final ExecutableElement element = getExecutableElementWithId("dimension resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToDimensionResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToDrawableResourceAnnotationPresent()
			throws CompilerMissingException {

		final ExecutableElement element = getExecutableElementWithId("drawable resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToDrawableResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToEnumConstantAnnotationPresent()
			throws CompilerMissingException {

		final ExecutableElement element = getExecutableElementWithId("enum constant");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToEnumConstant.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToFloatAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("float");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToFloat.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToFractionResourceAnnotationPresent()
			throws CompilerMissingException {

		final ExecutableElement element = getExecutableElementWithId("fraction resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToFractionResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToIntegerAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("integer");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToInteger.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToIntegerResourceAnnotationPresent()
			throws CompilerMissingException {

		final ExecutableElement element = getExecutableElementWithId("integer resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToIntegerResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToNullAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("null");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToNull.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToStringAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("string");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToString.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToStringResourceAnnotationPresent()
			throws CompilerMissingException {

		final ExecutableElement element = getExecutableElementWithId("string resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToStringResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToTextArrayResourceAnnotationPresent()
			throws CompilerMissingException {

		final ExecutableElement element = getExecutableElementWithId("text array resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToTextArrayResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToTextResourceAnnotationPresent()
			throws CompilerMissingException {

		final ExecutableElement element = getExecutableElementWithId("text resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToTextResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_noDefaultAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("no default annotation");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(nullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHasDefaultAnnotation_nullSupplied() {
		hasDefaultAnnotation(null);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToBooleanAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("boolean");

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToBooleanResourceAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("boolean resource");

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToColorResourceAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("color resource");

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToColorStateListResourceAnnotationPresent()
			throws CompilerMissingException {

		final ExecutableElement element = getExecutableElementWithId("color state list resource");

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToDimensionAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("dimension");

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToDimensionResourceAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("dimension resource");

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToDrawableResourceAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("drawable");

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToEnumConstantAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("enum constant");

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToFloatAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("float");

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToFractionResourceAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("fraction resource");

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToIntegerAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("integer");

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToIntegerResourceAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("integer resource");

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToNullAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("null");

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToStringAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("string");

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToStringResourceAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("string resource");

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToTextArrayResourceAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("text array resource");

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_defaultToTextResourceAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("text resource");

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasDefaultAnnotation_noDefaultAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("no default annotation");

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(false));
	}

	private ExecutableElement getExecutableElementWithId(final String id) throws CompilerMissingException {
		try {
			return (ExecutableElement) elementSupplier.getUniqueElementWithId(id);
		} catch (final ClassCastException e) {
			throw new RuntimeException("Found element with ID " + id + ", but it wasn't an ExecutableElement.");
		}
	}

	private enum PlaceholderEnum {}
}