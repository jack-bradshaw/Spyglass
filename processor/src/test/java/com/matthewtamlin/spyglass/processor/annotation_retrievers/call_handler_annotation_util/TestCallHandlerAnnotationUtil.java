package com.matthewtamlin.spyglass.processor.annotation_retrievers.call_handler_annotation_util;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.CallHandlerAnnoRetriever;

import org.junit.Before;
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
public class TestCallHandlerAnnotationUtil {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor" +
			"/annotation_retrievers/call_handler_annotation_util/Data.java");

	private IdBasedElementSupplier elementSupplier;

	@Before
	public void setup() throws MalformedURLException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));

		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());

		elementSupplier = new IdBasedElementSupplier(dataFileObject);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAnnotation_nullSupplied() {
		CallHandlerAnnoRetriever.getAnnotation(null);
	}

	@Test
	public void testGetAnnotation_specificEnumHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("specific enum");

		final AnnotationMirror mirror = CallHandlerAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(SpecificEnumHandler.class.getName()));
	}

	@Test
	public void testGetAnnotation_specificFlagHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("specific flag");

		final AnnotationMirror mirror = CallHandlerAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(SpecificFlagHandler.class.getName()));
	}

	@Test
	public void testGetAnnotation_noCallHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("no call handler annotation");

		final AnnotationMirror mirror = CallHandlerAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(nullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHasAnnotation_nullSupplied() {
		CallHandlerAnnoRetriever.hasAnnotation(null);
	}

	@Test
	public void testHasAnnotation_specificEnumHandlerAnnotationPresent() {
		doHasAnnotationTestForElementWithId("specific enum", true);
	}

	@Test
	public void testHasAnnotation_specificFlagHandlerAnnotationPresent() {
		doHasAnnotationTestForElementWithId("specific flag", true);
	}

	@Test
	public void testHasAnnotation_noCallHandlerAnnotationPresent() {
		doHasAnnotationTestForElementWithId("no call handler annotation", false);
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

		final boolean hasAnnotation = CallHandlerAnnoRetriever.hasAnnotation(element);

		assertThat(hasAnnotation, is(shouldHaveAnnotation));
	}

	private enum PlaceholderEnum {}
}