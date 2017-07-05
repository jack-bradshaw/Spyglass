package com.matthewtamlin.spyglass.processor.annotation_utils.default_annotation_util;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToBooleanResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToColorResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToColorStateListResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToDimension;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToDimensionResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToDrawableResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToFloat;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToFractionResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToInteger;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToIntegerResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToStringResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToTextArrayResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToTextResource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.net.MalformedURLException;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.tools.JavaFileObject;

import static com.matthewtamlin.spyglass.processor.annotation_utils.DefaultAnnoUtil.getDefaultAnnotationMirror;
import static com.matthewtamlin.spyglass.processor.annotation_utils.DefaultAnnoUtil.hasDefaultAnnotation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(JUnit4.class)
public class TestDefaultAnnotationUtil {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor" +
			"/annotation_utils/default_annotation_util/Data.java");

	private IdBasedElementSupplier elementSupplier;

	@Before
	public void setup() throws MalformedURLException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));

		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());

		elementSupplier = new IdBasedElementSupplier(dataFileObject);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAnnotationMirror_nullSupplied() {
		getDefaultAnnotationMirror(null);
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToBooleanAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("boolean");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToBoolean.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToBooleanResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("boolean resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToBooleanResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToColorResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("color resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToColorResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToColorStateListResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("color state list resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToColorStateListResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToDimensionAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("dimension");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToDimension.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToDimensionResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("dimension resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToDimensionResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToDrawableResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("drawable resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToDrawableResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToEnumConstantAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("enum constant");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToEnumConstant.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToFloatAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("float");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToFloat.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToFractionResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("fraction resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToFractionResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToIntegerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("integer");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToInteger.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToIntegerResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("integer resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToIntegerResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToNullAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("null");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToNull.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToStringAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("string");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToString.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToStringResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("string resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToStringResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToTextArrayResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("text array resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToTextArrayResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_defaultToTextResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("text resource");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToTextResource.class.getName()));
	}

	@Test
	public void testGetDefaultAnnotationMirror_noDefaultAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("no default annotation");

		final AnnotationMirror mirror = getDefaultAnnotationMirror(element);

		assertThat(mirror, is(nullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHasDefaultAnnotation_nullSupplied() {
		hasDefaultAnnotation(null);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToBooleanAnnotationPresent() {
		doHasAnnotationTestForElementWithId("boolean", true);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToBooleanResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("boolean resource", true);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToColorResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("color resource", true);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToColorStateListResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("color state list resource", true);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToDimensionAnnotationPresent() {
		doHasAnnotationTestForElementWithId("dimension", true);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToDimensionResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("dimension resource", true);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToDrawableResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("drawable resource", true);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToEnumConstantAnnotationPresent() {
		doHasAnnotationTestForElementWithId("enum constant", true);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToFloatAnnotationPresent() {
		doHasAnnotationTestForElementWithId("float", true);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToFractionResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("fraction resource", true);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToIntegerAnnotationPresent() {
		doHasAnnotationTestForElementWithId("integer", true);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToIntegerResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("integer resource", true);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToNullAnnotationPresent() {
		doHasAnnotationTestForElementWithId("null", true);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToStringAnnotationPresent() {
		doHasAnnotationTestForElementWithId("string", true);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToStringResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("string resource", true);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToTextArrayResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("text array resource", true);
	}

	@Test
	public void testHasDefaultAnnotation_defaultToTextResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("text resource", true);
	}

	@Test
	public void testHasDefaultAnnotation_noDefaultAnnotationPresent() {
		doHasAnnotationTestForElementWithId("no default annotation", false);
	}

	private ExecutableElement getExecutableElementWithId(final String id) {
		try {
			return (ExecutableElement) elementSupplier.getUniqueElementWithId(id);
		} catch (final ClassCastException e) {
			throw new RuntimeException("Found element with ID " + id + ", but it wasn't an ExecutableElement.");
		}
	}

	private void doHasAnnotationTestForElementWithId(final String id, final boolean shouldHaveAnnotation) {
		final ExecutableElement element = getExecutableElementWithId(id);

		final boolean hasAnnotation = hasDefaultAnnotation(element);

		assertThat(hasAnnotation, is(shouldHaveAnnotation));
	}

	private enum PlaceholderEnum {}
}