package com.matthewtamlin.spyglass.processor.annotation_retrievers.use_annotation_retriever;

import com.matthewtamlin.avatar.rules.AvatarRule;
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
import com.matthewtamlin.spyglass.processor.annotation_retrievers.UseAnnoRetriever;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(JUnit4.class)
public class TestUseAnnotationRetriever {
	@Rule
	public final AvatarRule avatarRule = AvatarRule
			.builder()
			.withSourcesAt("processor/src/test/java/com/matthewtamlin/spyglass/processor/annotation_retrievers/" +
					"use_annotation_retriever/Data.java")
			.build();

	@Test(expected = IllegalArgumentException.class)
	public void testGetAnnotation_nullSupplied() {
		UseAnnoRetriever.getAnnotation(null);
	}

	@Test
	public void testGetAnnotation_useBooleanAnnotationPresent() {
		final VariableElement element = avatarRule.getElementWithUniqueId("boolean");

		final AnnotationMirror mirror = UseAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseBoolean.class.getName()));
	}

	@Test
	public void testGetAnnotation_useByteAnnotationPresent() {
		final VariableElement element = avatarRule.getElementWithUniqueId("byte");

		final AnnotationMirror mirror = UseAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseByte.class.getName()));
	}

	@Test
	public void testGetAnnotation_useCharAnnotationPresent() {
		final VariableElement element = avatarRule.getElementWithUniqueId("char");

		final AnnotationMirror mirror = UseAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseChar.class.getName()));
	}

	@Test
	public void testGetAnnotation_useDoubleAnnotationPresent() {
		final VariableElement element = avatarRule.getElementWithUniqueId("double");

		final AnnotationMirror mirror = UseAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseDouble.class.getName()));
	}

	@Test
	public void testGetAnnotation_useFloatAnnotationPresent() {
		final VariableElement element = avatarRule.getElementWithUniqueId("float");

		final AnnotationMirror mirror = UseAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseFloat.class.getName()));
	}

	@Test
	public void testGetAnnotation_useIntAnnotationPresent() {
		final VariableElement element = avatarRule.getElementWithUniqueId("int");

		final AnnotationMirror mirror = UseAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseInt.class.getName()));
	}

	@Test
	public void testGetAnnotation_useLongAnnotationPresent() {
		final VariableElement element = avatarRule.getElementWithUniqueId("long");

		final AnnotationMirror mirror = UseAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseLong.class.getName()));
	}

	@Test
	public void testGetAnnotation_useNullAnnotationPresent() {
		final VariableElement element = avatarRule.getElementWithUniqueId("null");

		final AnnotationMirror mirror = UseAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseNull.class.getName()));
	}

	@Test
	public void testGetAnnotation_useShortAnnotationPresent() {
		final VariableElement element = avatarRule.getElementWithUniqueId("short");

		final AnnotationMirror mirror = UseAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseShort.class.getName()));
	}

	@Test
	public void testGetAnnotation_useStringAnnotationPresent() {
		final VariableElement element = avatarRule.getElementWithUniqueId("string");

		final AnnotationMirror mirror = UseAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(UseString.class.getName()));
	}

	@Test
	public void testGetAnnotation_noUseAnnotationPresent() {
		final VariableElement element = avatarRule.getElementWithUniqueId("no use annotation");

		final AnnotationMirror mirror = UseAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(nullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHasAnnotation_nullSupplied() {
		UseAnnoRetriever.hasAnnotation(null);
	}

	@Test
	public void testHasAnnotation_useBooleanAnnotationPresent() {
		doHasAnnotationTestForElementWithId("boolean", true);
	}

	@Test
	public void testHasAnnotation_useByteAnnotationPresent() {
		doHasAnnotationTestForElementWithId("byte", true);
	}

	@Test
	public void testHasAnnotation_useCharAnnotationPresent() {
		doHasAnnotationTestForElementWithId("char", true);
	}

	@Test
	public void testHasAnnotation_useDoubleAnnotationPresent() {
		doHasAnnotationTestForElementWithId("double", true);
	}

	@Test
	public void testHasAnnotation_useFloatAnnotationPresent() {
		doHasAnnotationTestForElementWithId("float", true);
	}

	@Test
	public void testHasAnnotation_useIntAnnotationPresent() {
		doHasAnnotationTestForElementWithId("int", true);
	}

	@Test
	public void testHasAnnotation_useLongAnnotationPresent() {
		doHasAnnotationTestForElementWithId("long", true);
	}

	@Test
	public void testHasAnnotation_useNullAnnotationPresent() {
		doHasAnnotationTestForElementWithId("null", true);
	}

	@Test
	public void testHasAnnotation_useShortAnnotationPresent() {
		doHasAnnotationTestForElementWithId("short", true);
	}

	@Test
	public void testHasAnnotation_useStringAnnotationPresent() {
		doHasAnnotationTestForElementWithId("string", true);
	}

	@Test
	public void testHasAnnotation_noUseAnnotationPresent() {
		doHasAnnotationTestForElementWithId("no use annotation", false);
	}

	private void doHasAnnotationTestForElementWithId(final String id, final boolean shouldHaveAnnotation) {
		final VariableElement element = avatarRule.getElementWithUniqueId(id);

		final boolean hasAnnotation = UseAnnoRetriever.hasAnnotation(element);

		assertThat(hasAnnotation, is(shouldHaveAnnotation));
	}

	private enum PlaceholderEnum {}
}