package com.matthewtamlin.spyglass.processors.annotation_utils.annotation_mirror_util;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.java_compiler_utilities.element_supplier.CompilerMissingException;
import com.matthewtamlin.java_compiler_utilities.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.processors.annotation_utils.AnnotationMirrorUtil;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;

@SuppressWarnings("unchecked") // Not relevant when mocking
public class TestAnnotationMirrorUtil {
	private static final File DATA_FILE = new File("processors/src/test/java/com/matthewtamlin/spyglass/processors" +
			"/annotation_utils/annotation_mirror_util/Data.java");

	private IdBasedElementSupplier elementSupplier;

	@Before
	public void setup() throws MalformedURLException, CompilerMissingException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));

		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());

		elementSupplier = new IdBasedElementSupplier(dataFileObject);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAnnotationMirror_nullElement() {
		AnnotationMirrorUtil.getAnnotationMirror(null, Annotation.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAnnotationMirror_nullAnnotationClass() {
		AnnotationMirrorUtil.getAnnotationMirror(mock(Element.class), null);
	}

	@Test
	public void testGetAnnotationMirror_annotationMissing() throws CompilerMissingException {
		final Element element = elementSupplier.getUniqueElementWithId("with annotation");

		final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(element, SomeAnnotation.class);

		assertThat(mirror, is(nullValue()));
	}

	@Test
	public void testGetAnnotationMirror_annotationPresent() throws CompilerMissingException {
		final Element element = elementSupplier.getUniqueElementWithId("without annotation");

		final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(element, SomeAnnotation.class);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(SomeAnnotation.class.getName()));
	}
}