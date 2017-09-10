package com.matthewtamlin.spyglass.processor.core.companion_namer;

import com.matthewtamlin.avatar.rules.AvatarRule;
import com.matthewtamlin.spyglass.processor.core.CompanionNamer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.lang.model.element.TypeElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JUnit4.class)
public class TestCompanionNamer {
	@Rule
	public final AvatarRule avatarRule = AvatarRule
			.builder()
			.withSourcesAt(
					"processor/src/test/java/com/matthewtamlin/spyglass/processor/core/companion_namer/Data.java")
			.build();

	@Test
	public void testGetCompanionNameFor_topLevelClass() {
		final TypeElement element = avatarRule.getElementWithUniqueId("top level");

		final String companionName = CompanionNamer.getCompanionNameFor(element);
		final String expectedName = "Data_SpyglassCompanion";

		assertThat(companionName, is(expectedName));
	}

	@Test
	public void testGetCompanionNameFor_classNestedByOneLevel() {
		final TypeElement element = avatarRule.getElementWithUniqueId("nested one level");

		final String companionName = CompanionNamer.getCompanionNameFor(element);
		final String expectedName = "Data_ClassA_SpyglassCompanion";

		assertThat(companionName, is(expectedName));
	}

	@Test
	public void testGetCompanionNameFor_classNestedByMultipleLevels() {
		final TypeElement element = avatarRule.getElementWithUniqueId("nested multiple levels");

		final String companionName = CompanionNamer.getCompanionNameFor(element);
		final String expectedName = "Data_ClassA_ClassB_ClassC_ClassD_SpyglassCompanion";

		assertThat(companionName, is(expectedName));
	}
}