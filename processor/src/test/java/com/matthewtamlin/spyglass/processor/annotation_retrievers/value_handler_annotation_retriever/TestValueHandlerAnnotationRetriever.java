package com.matthewtamlin.spyglass.processor.annotation_retrievers.value_handler_annotation_retriever;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.ColorHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.ColorStateListHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.DrawableHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.EnumConstantHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.EnumOrdinalHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.IntegerHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.ValueHandlerAnnoRetriever;

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
public class TestValueHandlerAnnotationRetriever {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor" +
			"/annotation_retrievers/value_handler_annotation_retriever/Data.java");

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
		ValueHandlerAnnoRetriever.getAnnotation(null);
	}

	@Test
	public void testGetAnnotation_booleanHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("boolean");

		final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(BooleanHandler.class.getName()));
	}

	@Test
	public void testGetAnnotation_colorHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("color");

		final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(ColorHandler.class.getName()));
	}

	@Test
	public void testGetAnnotation_colorStateListHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("color state list");

		final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(ColorStateListHandler.class.getName()));
	}

	@Test
	public void testGetAnnotation_dimensionHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("dimension");

		final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DimensionHandler.class.getName()));
	}

	@Test
	public void testGetAnnotation_drawableHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("drawable");

		final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DrawableHandler.class.getName()));
	}

	@Test
	public void testGetAnnotation_enumConstantHandlerAnnotationPresent() {

		final ExecutableElement element = getExecutableElementWithId("enum constant");

		final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(EnumConstantHandler.class.getName()));
	}

	@Test
	public void testGetAnnotation_enumOrdinalHandlerAnnotationPresent() {

		final ExecutableElement element = getExecutableElementWithId("enum ordinal");

		final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(EnumOrdinalHandler.class.getName()));
	}

	@Test
	public void testGetAnnotation_floatHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("float");

		final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(FloatHandler.class.getName()));
	}

	@Test
	public void testGetAnnotation_fractionHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("fraction");

		final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(FractionHandler.class.getName()));
	}

	@Test
	public void testGetAnnotation_integerHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("integer");

		final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(IntegerHandler.class.getName()));
	}

	@Test
	public void testGetAnnotation_stringHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("string");

		final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(StringHandler.class.getName()));
	}

	@Test
	public void testGetAnnotation_noValueHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("no value handler annotation");

		final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(nullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHasAnnotation_nullSupplied() {
		ValueHandlerAnnoRetriever.hasAnnotation(null);
	}

	@Test
	public void testHasAnnotation_booleanHandlerAnnotationPresent() {
		doHasAnnotationTestForElementWithId("boolean", true);
	}

	@Test
	public void testHasAnnotation_colorHandlerAnnotationPresent() {
		doHasAnnotationTestForElementWithId("color", true);
	}

	@Test
	public void testHasAnnotation_colorStateListHandlerAnnotationPresent() {
		doHasAnnotationTestForElementWithId("color state list", true);
	}

	@Test
	public void testHasAnnotation_dimensionHandlerAnnotationPresent() {
		doHasAnnotationTestForElementWithId("dimension", true);
	}

	@Test
	public void testHasAnnotation_drawableHandlerAnnotationPresent() {
		doHasAnnotationTestForElementWithId("drawable", true);
	}

	@Test
	public void testHasAnnotation_enumConstantHandlerAnnotationPresent() {
		doHasAnnotationTestForElementWithId("enum constant", true);
	}

	@Test
	public void testHasAnnotation_enumOrdinalHandlerAnnotationPresent() {
		doHasAnnotationTestForElementWithId("enum ordinal", true);
	}

	@Test
	public void testHasAnnotation_floatHandlerAnnotationPresent() {
		doHasAnnotationTestForElementWithId("float", true);
	}

	@Test
	public void testHasAnnotation_fractionHandlerAnnotationPresent() {
		doHasAnnotationTestForElementWithId("fraction", true);
	}

	@Test
	public void testHasAnnotation_integerHandlerAnnotationPresent() {
		doHasAnnotationTestForElementWithId("integer", true);
	}

	@Test
	public void testHasAnnotation_stringHandlerAnnotationPresent() {
		doHasAnnotationTestForElementWithId("string", true);
	}

	@Test
	public void testHasAnnotation_noValueHandlerAnnotationPresent() {
		doHasAnnotationTestForElementWithId("no value handler annotation", false);
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

		final boolean hasAnnotation = ValueHandlerAnnoRetriever.hasAnnotation(element);

		assertThat(hasAnnotation, is(shouldHaveAnnotation));
	}

	private enum PlaceholderEnum {}
}