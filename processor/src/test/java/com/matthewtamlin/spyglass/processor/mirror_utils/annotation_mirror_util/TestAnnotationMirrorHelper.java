package com.matthewtamlin.spyglass.processor.mirror_utils.annotation_mirror_util;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.processor.mirror_utils.AnnotationMirrorHelper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class TestAnnotationMirrorHelper {
	@Rule
	public final CompilationRule compilationRule = new CompilationRule();

	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor" +
			"/annotation_utils/annotation_mirror_util/Data.java");

	private IdBasedElementSupplier elementSupplier;

	private AnnotationMirrorHelper helper;

	@Before
	public void setup() throws MalformedURLException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));
		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());
		elementSupplier = new IdBasedElementSupplier(dataFileObject);

		helper = new AnnotationMirrorHelper(compilationRule.getElements());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAnnotationMirror_nullElement() {
		AnnotationMirrorHelper.getAnnotationMirror(null, Annotation.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAnnotationMirror_nullAnnotationClass() {
		AnnotationMirrorHelper.getAnnotationMirror(mock(Element.class), null);
	}

	@Test
	public void testGetAnnotationMirror_annotationMissing() {
		final Element element = elementSupplier.getUniqueElementWithId("get annotation mirror: without annotation");

		final AnnotationMirror mirror =
				AnnotationMirrorHelper.getAnnotationMirror(element, SomeAnnotationWithValue.class);

		assertThat(mirror, is(nullValue()));
	}

	@Test
	public void testGetAnnotationMirror_annotationPresent() {
		final Element element = elementSupplier.getUniqueElementWithId("get annotation mirror: with annotation");

		final AnnotationMirror mirror = AnnotationMirrorHelper
				.getAnnotationMirror(element, SomeAnnotationWithValue.class);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(SomeAnnotationWithValue.class.getName()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetValueUsingDefaults_nullAnnotationMirrorSupplied() {
		helper.getValueUsingDefaults(null, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetValueUsingDefaults_nullValueKeySupplied() {
		helper.getValueUsingDefaults(mock(AnnotationMirror.class), null);
	}

	@Test
	public void testGetValueIgnoringDefaults_invalidKey() {
		final Element e = elementSupplier.getUniqueElementWithId("get annotation value ignoring defaults: with value");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(e, SomeAnnotationWithValue.class);

		final AnnotationValue value = helper.getValueIgnoringDefaults(mirror, "invalidKey");

		assertThat(value, is(nullValue()));
	}

	@Test
	public void testGetValueIgnoreDefaults_noValueProvided() {
		final Element e = elementSupplier.getUniqueElementWithId("get annotation value ignoring defaults: no value");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(e, SomeAnnotationWithValue.class);

		final AnnotationValue value = helper.getValueIgnoringDefaults(mirror, "value");

		assertThat(value, is(nullValue()));
	}

	@Test
	public void testGetValueIgnoringDefaults_valueProvided() {
		final Element e = elementSupplier.getUniqueElementWithId("get annotation value ignoring defaults: with value");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(e, SomeAnnotationWithValue.class);

		final AnnotationValue value = helper.getValueIgnoringDefaults(mirror, "value");

		assertThat(value, is(notNullValue()));
		assertThat((String) value.getValue(), is(Data.SPECIFIED_VALUE));
	}

	@Test
	public void testGetValueUsingDefaults_invalidKey() {
		final Element e = elementSupplier.getUniqueElementWithId("get annotation value with defaults: with value");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(e, SomeAnnotationWithValue.class);

		final AnnotationValue value = helper.getValueUsingDefaults(mirror, "invalidKey");

		assertThat(value, is(nullValue()));
	}

	@Test
	public void testGetValueUsingDefaults_noValueProvided() {
		final Element e = elementSupplier.getUniqueElementWithId("get annotation value with defaults: no value");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(e, SomeAnnotationWithValue.class);

		final AnnotationValue value = helper.getValueUsingDefaults(mirror, "value");

		assertThat(value, is(notNullValue()));
		assertThat((String) value.getValue(), is(SomeAnnotationWithValue.DEFAULT_VALUE));
	}

	@Test
	public void testGetValueUsingDefaults_valueProvided() {
		final Element e = elementSupplier.getUniqueElementWithId("get annotation value with defaults: with value");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(e, SomeAnnotationWithValue.class);

		final AnnotationValue value = helper.getValueUsingDefaults(mirror, "value");

		assertThat(value, is(notNullValue()));
		assertThat((String) value.getValue(), is(Data.SPECIFIED_VALUE));
	}
}