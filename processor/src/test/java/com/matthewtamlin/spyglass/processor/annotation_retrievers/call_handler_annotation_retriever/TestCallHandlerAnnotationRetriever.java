package com.matthewtamlin.spyglass.processor.annotation_retrievers.call_handler_annotation_retriever;

import com.matthewtamlin.avatar.rules.AvatarRule;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.CallHandlerAnnoRetriever;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(JUnit4.class)
public class TestCallHandlerAnnotationRetriever {
	@Rule
	public final AvatarRule avatarRule = AvatarRule
			.builder()
			.withSourcesAt("processor/src/test/java/com/matthewtamlin/spyglass/processor/annotation_retrievers/" +
					"call_handler_annotation_retriever/Data.java")
			.build();

	@Test(expected = IllegalArgumentException.class)
	public void testGetAnnotation_nullSupplied() {
		CallHandlerAnnoRetriever.getAnnotation(null);
	}

	@Test
	public void testGetAnnotation_specificEnumHandlerAnnotationPresent() {
		final ExecutableElement element = avatarRule.getElementWithUniqueId("specific enum");

		final AnnotationMirror mirror = CallHandlerAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(SpecificEnumHandler.class.getName()));
	}

	@Test
	public void testGetAnnotation_specificFlagHandlerAnnotationPresent() {
		final ExecutableElement element = avatarRule.getElementWithUniqueId("specific flag");

		final AnnotationMirror mirror = CallHandlerAnnoRetriever.getAnnotation(element);

		assertThat(mirror, is(notNullValue()));
		assertThat(mirror.getAnnotationType().toString(), is(SpecificFlagHandler.class.getName()));
	}

	@Test
	public void testGetAnnotation_noCallHandlerAnnotationPresent() {
		final ExecutableElement element = avatarRule.getElementWithUniqueId("no call handler annotation");

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

	private void doHasAnnotationTestForElementWithId(final String id, final boolean shouldHaveAnnotation) {
		final ExecutableElement element = avatarRule.getElementWithUniqueId(id);

		final boolean hasAnnotation = CallHandlerAnnoRetriever.hasAnnotation(element);

		assertThat(hasAnnotation, is(shouldHaveAnnotation));
	}

	private enum PlaceholderEnum {}
}