package com.matthewtamlin.spyglass.processor.annotation_utils.use_annotation_util;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseChar;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseString;
import com.matthewtamlin.spyglass.processor.annotation_utils.UseAnnoUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.net.MalformedURLException;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(JUnit4.class)
public class TestUseAnnotationUtil {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor" +
			"/annotation_utils/use_annotation_util/Data.java");

	private IdBasedElementSupplier elementSupplier;

	@Before
	public void setup() throws MalformedURLException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));

		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());

		elementSupplier = new IdBasedElementSupplier(dataFileObject);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUseAnnotationMirror_nullSupplied() {
		UseAnnoUtil.getAnnotation(null);
	}

	@Test
	public void testGetUseAnnotationMirror_useBooleanAnnotationPresent() {
		final VariableElement element = getVariableElementWithId("boolean");

		final AnnotationMirror mirror = UseAnnoUtil.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseBoolean.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_useByteAnnotationPresent() {
		final VariableElement element = getVariableElementWithId("byte");

		final AnnotationMirror mirror = UseAnnoUtil.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseByte.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_useCharAnnotationPresent() {
		final VariableElement element = getVariableElementWithId("char");

		final AnnotationMirror mirror = UseAnnoUtil.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseChar.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_useDoubleAnnotationPresent() {
		final VariableElement element = getVariableElementWithId("double");

		final AnnotationMirror mirror = UseAnnoUtil.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseDouble.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_useFloatAnnotationPresent() {
		final VariableElement element = getVariableElementWithId("float");

		final AnnotationMirror mirror = UseAnnoUtil.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseFloat.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_useIntAnnotationPresent() {
		final VariableElement element = getVariableElementWithId("int");

		final AnnotationMirror mirror = UseAnnoUtil.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseInt.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_useLongAnnotationPresent() {
		final VariableElement element = getVariableElementWithId("long");

		final AnnotationMirror mirror = UseAnnoUtil.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseLong.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_useNullAnnotationPresent() {
		final VariableElement element = getVariableElementWithId("null");

		final AnnotationMirror mirror = UseAnnoUtil.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseNull.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_useShortAnnotationPresent() {
		final VariableElement element = getVariableElementWithId("short");

		final AnnotationMirror mirror = UseAnnoUtil.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseShort.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_useStringAnnotationPresent() {
		final VariableElement element = getVariableElementWithId("string");

		final AnnotationMirror mirror = UseAnnoUtil.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseString.class.getName()));
	}

	@Test
	public void testGetUseAnnotationMirror_noUseAnnotationPresent() {
		final VariableElement element = getVariableElementWithId("no use annotation");

		final AnnotationMirror mirror = UseAnnoUtil.getAnnotation(element);

		assertThat(mirror, is(nullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHasUseAnnotation_nullSupplied() {
		UseAnnoUtil.hasAnnotation(null);
	}

	@Test
	public void testHasUseAnnotation_useBooleanAnnotationPresent() {
		doHasAnnotationTestForElementWithId("boolean", true);
	}

	@Test
	public void testHasUseAnnotation_useByteAnnotationPresent() {
		doHasAnnotationTestForElementWithId("byte", true);
	}

	@Test
	public void testHasUseAnnotation_useCharAnnotationPresent() {
		doHasAnnotationTestForElementWithId("char", true);
	}

	@Test
	public void testHasUseAnnotation_useDoubleAnnotationPresent() {
		doHasAnnotationTestForElementWithId("double", true);
	}

	@Test
	public void testHasUseAnnotation_useFloatAnnotationPresent() {
		doHasAnnotationTestForElementWithId("float", true);
	}

	@Test
	public void testHasUseAnnotation_useIntAnnotationPresent() {
		doHasAnnotationTestForElementWithId("int", true);
	}

	@Test
	public void testHasUseAnnotation_useLongAnnotationPresent() {
		doHasAnnotationTestForElementWithId("long", true);
	}

	@Test
	public void testHasUseAnnotation_useNullAnnotationPresent() {
		doHasAnnotationTestForElementWithId("null", true);
	}

	@Test
	public void testHasUseAnnotation_useShortAnnotationPresent() {
		doHasAnnotationTestForElementWithId("short", true);
	}

	@Test
	public void testHasUseAnnotation_useStringAnnotationPresent() {
		doHasAnnotationTestForElementWithId("string", true);
	}

	@Test
	public void testHasUseAnnotation_noUseAnnotationPresent() {
		doHasAnnotationTestForElementWithId("no use annotation", false);
	}

	private VariableElement getVariableElementWithId(final String id) {
		try {
			return (VariableElement) elementSupplier.getUniqueElementWithId(id);
		} catch (final ClassCastException e) {
			throw new RuntimeException("Found element with ID " + id + ", but it wasn't a VariableElement.");
		}
	}

	private void doHasAnnotationTestForElementWithId(final String id, final boolean shouldHaveAnnotation) {
		final VariableElement element = getVariableElementWithId(id);

		final boolean hasAnnotation = UseAnnoUtil.hasAnnotation(element);

		assertThat(hasAnnotation, is(shouldHaveAnnotation));
	}

	private enum PlaceholderEnum {}
}