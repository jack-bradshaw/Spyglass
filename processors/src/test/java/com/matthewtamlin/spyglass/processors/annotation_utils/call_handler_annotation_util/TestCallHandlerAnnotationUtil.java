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
import java.net.MalformedURLException;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.tools.JavaFileObject;

import static com.matthewtamlin.spyglass.processors.annotation_utils.CallHandlerAnnotationUtil.getCallHandlerAnnotationMirror;
import static com.matthewtamlin.spyglass.processors.annotation_utils.CallHandlerAnnotationUtil.hasCallHandlerAnnotation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(JUnit4.class)
public class TestCallHandlerAnnotationUtil {
	private static final File DATA_FILE = new File("processors/src/test/java/com/matthewtamlin/spyglass/processors" +
			"/annotation_utils/call_handler_annotation_util/Data.java");

	private IdBasedElementSupplier elementSupplier;

	@Before
	public void setup() throws MalformedURLException, CompilerMissingException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));

		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());

		elementSupplier = new IdBasedElementSupplier(dataFileObject);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCallHandlerAnnotationMirror_nullSupplied() {
		getCallHandlerAnnotationMirror(null);
	}

	@Test
	public void testGetCallHandlerAnnotationMirror_specificEnumHandlerAnnotationPresent()
			throws CompilerMissingException {

		final ExecutableElement element = getExecutableElementWithId("specific enum");

		final AnnotationMirror mirror = getCallHandlerAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(SpecificEnumHandler.class.getName()));
	}

	@Test
	public void testGetCallHandlerAnnotationMirror_specificFlagHandlerAnnotationPresent()
			throws CompilerMissingException {

		final ExecutableElement element = getExecutableElementWithId("specific flag");

		final AnnotationMirror mirror = getCallHandlerAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(SpecificFlagHandler.class.getName()));
	}

	@Test
	public void testGetCallHandlerAnnotationMirror_noCallHandlerAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("no call handler annotation");

		final AnnotationMirror mirror = getCallHandlerAnnotationMirror(element);

		assertThat(mirror, is(nullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHasCallHandlerAnnotation_nullSupplied() {
		hasCallHandlerAnnotation(null);
	}

	@Test
	public void testHasCallHandlerAnnotation_specificEnumHandlerAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("specific enum");

		final boolean hasAnnotation = hasCallHandlerAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasCallHandlerAnnotation_specificFlagHandlerAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("specific flag");

		final boolean hasAnnotation = hasCallHandlerAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasCallHandlerAnnotation_noCallHandlerAnnotationPresent() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("no call handler annotation");

		final boolean hasAnnotation = hasCallHandlerAnnotation(element);

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