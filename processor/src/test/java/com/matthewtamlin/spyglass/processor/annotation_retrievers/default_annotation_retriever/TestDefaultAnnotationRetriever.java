package com.matthewtamlin.spyglass.processor.annotation_retrievers.default_annotation_retriever;

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
import com.matthewtamlin.spyglass.processor.annotation_retrievers.DefaultAnnoRetriever;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.net.MalformedURLException;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.tools.JavaFileObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(JUnit4.class)
public class TestDefaultAnnotationRetriever {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor" +
			"/annotation_retrievers/default_annotation_retriever/Data.java");

	private IdBasedElementSupplier elementSupplier;

	@BeforeClass
	public static void setupClass() {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));
	}

	@Before
	public void setup() throws MalformedURLException {
		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());
		elementSupplier = new IdBasedElementSupplier(dataFileObject);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAnnotation_nullSupplied() {
		DefaultAnnoRetriever.getAnnotation(null);
	}

	@Test
	public void testGetAnnotation_defaultToBooleanAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("boolean");

		final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToBoolean.class.getName()));
	}

	@Test
	public void testGetAnnotation_defaultToBooleanResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("boolean resource");

		final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToBooleanResource.class.getName()));
	}

	@Test
	public void testGetAnnotation_defaultToColorResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("color resource");

		final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToColorResource.class.getName()));
	}

	@Test
	public void testGetAnnotation_defaultToColorStateListResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("color state list resource");

		final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToColorStateListResource.class.getName()));
	}

	@Test
	public void testGetAnnotation_defaultToDimensionAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("dimension");

		final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToDimension.class.getName()));
	}

	@Test
	public void testGetAnnotation_defaultToDimensionResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("dimension resource");

		final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToDimensionResource.class.getName()));
	}

	@Test
	public void testGetAnnotation_defaultToDrawableResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("drawable resource");

		final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToDrawableResource.class.getName()));
	}

	@Test
	public void testGetAnnotation_defaultToEnumConstantAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("enum constant");

		final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToEnumConstant.class.getName()));
	}

	@Test
	public void testGetAnnotation_defaultToFloatAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("float");

		final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToFloat.class.getName()));
	}

	@Test
	public void testGetAnnotation_defaultToFractionResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("fraction resource");

		final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToFractionResource.class.getName()));
	}

	@Test
	public void testGetAnnotation_defaultToIntegerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("integer");

		final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToInteger.class.getName()));
	}

	@Test
	public void testGetAnnotation_defaultToIntegerResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("integer resource");

		final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToIntegerResource.class.getName()));
	}

	@Test
	public void testGetAnnotation_defaultToNullAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("null");

		final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToNull.class.getName()));
	}

	@Test
	public void testGetAnnotation_defaultToStringAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("string");

		final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToString.class.getName()));
	}

	@Test
	public void testGetAnnotation_defaultToStringResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("string resource");

		final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToStringResource.class.getName()));
	}

	@Test
	public void testGetAnnotation_defaultToTextArrayResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("text array resource");

		final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToTextArrayResource.class.getName()));
	}

	@Test
	public void testGetAnnotation_defaultToTextResourceAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("text resource");

		final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DefaultToTextResource.class.getName()));
	}

	@Test
	public void testGetAnnotation_noDefaultAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("no default annotation");

		final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(nullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHasAnnotation_nullSupplied() {
		DefaultAnnoRetriever.hasAnnotation(null);
	}

	@Test
	public void testHasAnnotation_defaultToBooleanAnnotationPresent() {
		doHasAnnotationTestForElementWithId("boolean", true);
	}

	@Test
	public void testHasAnnotation_defaultToBooleanResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("boolean resource", true);
	}

	@Test
	public void testHasAnnotation_defaultToColorResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("color resource", true);
	}

	@Test
	public void testHasAnnotation_defaultToColorStateListResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("color state list resource", true);
	}

	@Test
	public void testHasAnnotation_defaultToDimensionAnnotationPresent() {
		doHasAnnotationTestForElementWithId("dimension", true);
	}

	@Test
	public void testHasAnnotation_defaultToDimensionResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("dimension resource", true);
	}

	@Test
	public void testHasAnnotation_defaultToDrawableResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("drawable resource", true);
	}

	@Test
	public void testHasAnnotation_defaultToEnumConstantAnnotationPresent() {
		doHasAnnotationTestForElementWithId("enum constant", true);
	}

	@Test
	public void testHasAnnotation_defaultToFloatAnnotationPresent() {
		doHasAnnotationTestForElementWithId("float", true);
	}

	@Test
	public void testHasAnnotation_defaultToFractionResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("fraction resource", true);
	}

	@Test
	public void testHasAnnotation_defaultToIntegerAnnotationPresent() {
		doHasAnnotationTestForElementWithId("integer", true);
	}

	@Test
	public void testHasAnnotation_defaultToIntegerResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("integer resource", true);
	}

	@Test
	public void testHasAnnotation_defaultToNullAnnotationPresent() {
		doHasAnnotationTestForElementWithId("null", true);
	}

	@Test
	public void testHasAnnotation_defaultToStringAnnotationPresent() {
		doHasAnnotationTestForElementWithId("string", true);
	}

	@Test
	public void testHasAnnotation_defaultToStringResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("string resource", true);
	}

	@Test
	public void testHasAnnotation_defaultToTextArrayResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("text array resource", true);
	}

	@Test
	public void testHasAnnotation_defaultToTextResourceAnnotationPresent() {
		doHasAnnotationTestForElementWithId("text resource", true);
	}

	@Test
	public void testHasAnnotation_noDefaultAnnotationPresent() {
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

		final boolean hasAnnotation = DefaultAnnoRetriever.hasAnnotation(element);

		assertThat(hasAnnotation, is(shouldHaveAnnotation));
	}

	private enum PlaceholderEnum {}
}