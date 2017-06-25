package com.matthewtamlin.spyglass.processor.annotation_utils.value_handler_annotation_util;

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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.net.MalformedURLException;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.tools.JavaFileObject;

import static com.matthewtamlin.spyglass.processor.annotation_utils.ValueHandlerAnnotationUtil.getValueHandlerAnnotationMirror;
import static com.matthewtamlin.spyglass.processor.annotation_utils.ValueHandlerAnnotationUtil.hasValueHandlerAnnotation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(JUnit4.class)
public class TestValueHandlerAnnotationUtil {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor" +
			"/annotation_utils/value_handler_annotation_util/Data.java");

	private IdBasedElementSupplier elementSupplier;

	@Before
	public void setup() throws MalformedURLException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));

		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());

		elementSupplier = new IdBasedElementSupplier(dataFileObject);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetValueHandlerAnnotationMirror_nullSupplied() {
		getValueHandlerAnnotationMirror(null);
	}

	@Test
	public void testGetValueHandlerAnnotationMirror_booleanHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("boolean");

		final AnnotationMirror mirror = getValueHandlerAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(BooleanHandler.class.getName()));
	}

	@Test
	public void testGetValueHandlerAnnotationMirror_colorHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("color");

		final AnnotationMirror mirror = getValueHandlerAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(ColorHandler.class.getName()));
	}

	@Test
	public void testGetValueHandlerAnnotationMirror_colorStateListHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("color state list");

		final AnnotationMirror mirror = getValueHandlerAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(ColorStateListHandler.class.getName()));
	}

	@Test
	public void testGetValueHandlerAnnotationMirror_dimensionHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("dimension");

		final AnnotationMirror mirror = getValueHandlerAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DimensionHandler.class.getName()));
	}

	@Test
	public void testGetValueHandlerAnnotationMirror_drawableHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("drawable");

		final AnnotationMirror mirror = getValueHandlerAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(DrawableHandler.class.getName()));
	}

	@Test
	public void testGetValueHandlerAnnotationMirror_enumConstantHandlerAnnotationPresent() {

		final ExecutableElement element = getExecutableElementWithId("enum constant");

		final AnnotationMirror mirror = getValueHandlerAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(EnumConstantHandler.class.getName()));
	}

	@Test
	public void testGetValueHandlerAnnotationMirror_enumOrdinalHandlerAnnotationPresent() {

		final ExecutableElement element = getExecutableElementWithId("enum ordinal");

		final AnnotationMirror mirror = getValueHandlerAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(EnumOrdinalHandler.class.getName()));
	}

	@Test
	public void testGetValueHandlerAnnotationMirror_floatHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("float");

		final AnnotationMirror mirror = getValueHandlerAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(FloatHandler.class.getName()));
	}

	@Test
	public void testGetValueHandlerAnnotationMirror_fractionHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("fraction");

		final AnnotationMirror mirror = getValueHandlerAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(FractionHandler.class.getName()));
	}

	@Test
	public void testGetValueHandlerAnnotationMirror_integerHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("integer");

		final AnnotationMirror mirror = getValueHandlerAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(IntegerHandler.class.getName()));
	}

	@Test
	public void testGetValueHandlerAnnotationMirror_stringHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("string");

		final AnnotationMirror mirror = getValueHandlerAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(StringHandler.class.getName()));
	}

	@Test
	public void testGetValueHandlerAnnotationMirror_noValueHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("no value handler annotation");

		final AnnotationMirror mirror = getValueHandlerAnnotationMirror(element);

		assertThat(mirror, is(nullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHasValueHandlerAnnotation_nullSupplied() {
		hasValueHandlerAnnotation(null);
	}

	@Test
	public void testHasValueHandlerAnnotation_booleanHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("boolean");

		final boolean hasAnnotation = hasValueHandlerAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasValueHandlerAnnotation_colorHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("color");

		final boolean hasAnnotation = hasValueHandlerAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testGetValueHandlerAnnotation_colorStateListHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("color state list");

		final boolean hasAnnotation = hasValueHandlerAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testGetValueHandlerAnnotation_dimensionHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("dimension");

		final boolean hasAnnotation = hasValueHandlerAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testGetValueHandlerAnnotation_drawableHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("drawable");

		final boolean hasAnnotation = hasValueHandlerAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testGetValueHandlerAnnotation_enumConstantHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("enum constant");

		final boolean hasAnnotation = hasValueHandlerAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testGetValueHandlerAnnotation_enumOrdinalHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("enum ordinal");

		final boolean hasAnnotation = hasValueHandlerAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testGetValueHandlerAnnotation_floatHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("float");

		final boolean hasAnnotation = hasValueHandlerAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testGetValueHandlerAnnotation_fractionHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("fraction");

		final boolean hasAnnotation = hasValueHandlerAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testGetValueHandlerAnnotation_integerHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("integer");

		final boolean hasAnnotation = hasValueHandlerAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testGetValueHandlerAnnotation_stringHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("string");

		final boolean hasAnnotation = hasValueHandlerAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testGetValueHandlerAnnotation_noValueHandlerAnnotationPresent() {
		final ExecutableElement element = getExecutableElementWithId("no value handler annotation");

		final boolean hasAnnotation = hasValueHandlerAnnotation(element);

		assertThat(hasAnnotation, is(false));
	}

	private ExecutableElement getExecutableElementWithId(final String id) {
		try {
			return (ExecutableElement) elementSupplier.getUniqueElementWithId(id);
		} catch (final ClassCastException e) {
			throw new RuntimeException("Found element with ID " + id + ", but it wasn't an ExecutableElement.");
		}
	}

	private enum PlaceholderEnum {}
}