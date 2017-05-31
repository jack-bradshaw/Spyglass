package com.matthewtamlin.spyglass.processors.annotation_utils.use_annotation_util;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.CompilerMissingException;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseChar;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.net.MalformedURLException;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

import static com.matthewtamlin.spyglass.processors.annotation_utils.UseAnnotationUtil.getUseAnnotationMirror;
import static com.matthewtamlin.spyglass.processors.annotation_utils.UseAnnotationUtil.hasUseAnnotation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(JUnit4.class)
public class TestUseAnnotationUtil {
	private static final File DATA_FILE = new File("processors/src/test/java/com/matthewtamlin/spyglass/processors" +
			"/annotation_utils/use_annotation_util/Data.java");

	private IdBasedElementSupplier elementSupplier;

	@Before
	public void setup() throws MalformedURLException, CompilerMissingException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));

		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());

		elementSupplier = new IdBasedElementSupplier(dataFileObject);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUseAnnotationMirror_nullSupplied() {
		getUseAnnotationMirror(null);
	}

	@Test
	public void testGetUseAnnotationMirror_useBooleanAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("boolean");

		final AnnotationMirror mirror = getUseAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseBoolean.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_useByteAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("byte");

		final AnnotationMirror mirror = getUseAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseByte.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_useCharAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("char");

		final AnnotationMirror mirror = getUseAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseChar.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_useDoubleAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("double");

		final AnnotationMirror mirror = getUseAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseDouble.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_useFloatAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("float");

		final AnnotationMirror mirror = getUseAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseFloat.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_useIntAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("int");

		final AnnotationMirror mirror = getUseAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseInt.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_useLongAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("long");

		final AnnotationMirror mirror = getUseAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseLong.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_useNullAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("null");

		final AnnotationMirror mirror = getUseAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseNull.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_useShortAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("short");

		final AnnotationMirror mirror = getUseAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseShort.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_useStringAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("string");

		final AnnotationMirror mirror = getUseAnnotationMirror(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseString.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_noUseAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("no use annotation");

		final AnnotationMirror mirror = getUseAnnotationMirror(element);

		assertThat(mirror, is(nullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHasUseAnnotation_nullSupplied() {
		hasUseAnnotation(null);
	}

	@Test
	public void testHasUseAnnotation_useBooleanAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("boolean");

		final boolean hasAnnotation = hasUseAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasUseAnnotation_useByteAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("byte");

		final boolean hasAnnotation = hasUseAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasUseAnnotation_useCharAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("char");

		final boolean hasAnnotation = hasUseAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasUseAnnotation_useDoubleAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("double");

		final boolean hasAnnotation = hasUseAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasUseAnnotation_useFloatAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("float");

		final boolean hasAnnotation = hasUseAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasUseAnnotation_useIntAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("int");

		final boolean hasAnnotation = hasUseAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasUseAnnotation_useLongAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("long");

		final boolean hasAnnotation = hasUseAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasUseAnnotation_useNullAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("null");

		final boolean hasAnnotation = hasUseAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasUseAnnotation_useShortAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("short");

		final boolean hasAnnotation = hasUseAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasUseAnnotation_useStringAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("string");

		final boolean hasAnnotation = hasUseAnnotation(element);

		assertThat(hasAnnotation, is(true));
	}

	@Test
	public void testHasUseAnnotation_noUseAnnotationPresent() throws CompilerMissingException {
		final VariableElement element = getVariableElementWithId("no use annotation");

		final boolean hasAnnotation = hasUseAnnotation(element);

		assertThat(hasAnnotation, is(false));
	}

	private VariableElement getVariableElementWithId(final String id) throws CompilerMissingException {
		try {
			return (VariableElement) elementSupplier.getUniqueElementWithId(id);
		} catch (final ClassCastException e) {
			throw new RuntimeException("Found element with ID " + id + ", but it wasn't a VariableElement.");
		}
	}

	private enum PlaceholderEnum {}
}