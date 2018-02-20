/*
 * Copyright 2017 Matthew David Tamlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.matthewtamlin.spyglass.processor.annotation_retrievers.default_annotation_retriever;

import com.matthewtamlin.avatar.rules.AvatarRule;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.*;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.DefaultAnnoRetriever;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;


@RunWith(JUnit4.class)
public class TestDefaultAnnotationRetriever {
  @Rule
  public final AvatarRule avatarRule = AvatarRule
      .builder()
      .withSourcesAt("processor/src/test/java/com/matthewtamlin/spyglass/processor/annotation_retrievers/" +
          "default_annotation_retriever/Data.java")
      .build();

  @Test(expected = IllegalArgumentException.class)
  public void testGetAnnotation_nullSupplied() {
    DefaultAnnoRetriever.getAnnotation(null);
  }

  @Test
  public void testGetAnnotation_defaultToBooleanAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("boolean");

    final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DefaultToBoolean.class.getName()));
  }

  @Test
  public void testGetAnnotation_defaultToBooleanResourceAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("boolean resource");

    final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DefaultToBooleanResource.class.getName()));
  }

  @Test
  public void testGetAnnotation_defaultToColorResourceAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("color resource");

    final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DefaultToColorResource.class.getName()));
  }

  @Test
  public void testGetAnnotation_defaultToColorStateListResourceAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("color state list resource");

    final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DefaultToColorStateListResource.class.getName()));
  }

  @Test
  public void testGetAnnotation_defaultToDimensionAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("dimension");

    final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DefaultToDimension.class.getName()));
  }

  @Test
  public void testGetAnnotation_defaultToDimensionResourceAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("dimension resource");

    final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DefaultToDimensionResource.class.getName()));
  }

  @Test
  public void testGetAnnotation_defaultToDrawableResourceAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("drawable resource");

    final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DefaultToDrawableResource.class.getName()));
  }

  @Test
  public void testGetAnnotation_defaultToEnumConstantAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("enum constant");

    final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DefaultToEnumConstant.class.getName()));
  }

  @Test
  public void testGetAnnotation_defaultToFloatAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("float");

    final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DefaultToFloat.class.getName()));
  }

  @Test
  public void testGetAnnotation_defaultToFractionResourceAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("fraction resource");

    final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DefaultToFractionResource.class.getName()));
  }

  @Test
  public void testGetAnnotation_defaultToIntegerAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("integer");

    final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DefaultToInteger.class.getName()));
  }

  @Test
  public void testGetAnnotation_defaultToIntegerResourceAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("integer resource");

    final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DefaultToIntegerResource.class.getName()));
  }

  @Test
  public void testGetAnnotation_defaultToNullAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("null");

    final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DefaultToNull.class.getName()));
  }

  @Test
  public void testGetAnnotation_defaultToStringAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("string");

    final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DefaultToString.class.getName()));
  }

  @Test
  public void testGetAnnotation_defaultToStringResourceAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("string resource");

    final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DefaultToStringResource.class.getName()));
  }

  @Test
  public void testGetAnnotation_defaultToTextArrayResourceAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("text array resource");

    final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DefaultToTextArrayResource.class.getName()));
  }

  @Test
  public void testGetAnnotation_defaultToTextResourceAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("text resource");

    final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DefaultToTextResource.class.getName()));
  }

  @Test
  public void testGetAnnotation_noDefaultAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("no default annotation");

    final AnnotationMirror mirror = DefaultAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(nullValue()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHasAnnotation_nullSupplied() {
    DefaultAnnoRetriever.hasAnnotation(null);
  }

  @Test
  public void testHasAnnotation_defaultToBooleanAnnotationPresent() {
    doHasAnnotationTestForElementWithId("boolean", true);
  }

  @Test
  public void testHasAnnotation_defaultToBooleanResourceAnnotationPresent() {
    doHasAnnotationTestForElementWithId("boolean resource", true);
  }

  @Test
  public void testHasAnnotation_defaultToColorResourceAnnotationPresent() {
    doHasAnnotationTestForElementWithId("color resource", true);
  }

  @Test
  public void testHasAnnotation_defaultToColorStateListResourceAnnotationPresent() {
    doHasAnnotationTestForElementWithId("color state list resource", true);
  }

  @Test
  public void testHasAnnotation_defaultToDimensionAnnotationPresent() {
    doHasAnnotationTestForElementWithId("dimension", true);
  }

  @Test
  public void testHasAnnotation_defaultToDimensionResourceAnnotationPresent() {
    doHasAnnotationTestForElementWithId("dimension resource", true);
  }

  @Test
  public void testHasAnnotation_defaultToDrawableResourceAnnotationPresent() {
    doHasAnnotationTestForElementWithId("drawable resource", true);
  }

  @Test
  public void testHasAnnotation_defaultToEnumConstantAnnotationPresent() {
    doHasAnnotationTestForElementWithId("enum constant", true);
  }

  @Test
  public void testHasAnnotation_defaultToFloatAnnotationPresent() {
    doHasAnnotationTestForElementWithId("float", true);
  }

  @Test
  public void testHasAnnotation_defaultToFractionResourceAnnotationPresent() {
    doHasAnnotationTestForElementWithId("fraction resource", true);
  }

  @Test
  public void testHasAnnotation_defaultToIntegerAnnotationPresent() {
    doHasAnnotationTestForElementWithId("integer", true);
  }

  @Test
  public void testHasAnnotation_defaultToIntegerResourceAnnotationPresent() {
    doHasAnnotationTestForElementWithId("integer resource", true);
  }

  @Test
  public void testHasAnnotation_defaultToNullAnnotationPresent() {
    doHasAnnotationTestForElementWithId("null", true);
  }

  @Test
  public void testHasAnnotation_defaultToStringAnnotationPresent() {
    doHasAnnotationTestForElementWithId("string", true);
  }

  @Test
  public void testHasAnnotation_defaultToStringResourceAnnotationPresent() {
    doHasAnnotationTestForElementWithId("string resource", true);
  }

  @Test
  public void testHasAnnotation_defaultToTextArrayResourceAnnotationPresent() {
    doHasAnnotationTestForElementWithId("text array resource", true);
  }

  @Test
  public void testHasAnnotation_defaultToTextResourceAnnotationPresent() {
    doHasAnnotationTestForElementWithId("text resource", true);
  }

  @Test
  public void testHasAnnotation_noDefaultAnnotationPresent() {
    doHasAnnotationTestForElementWithId("no default annotation", false);
  }

  private void doHasAnnotationTestForElementWithId(final String id, final boolean shouldHaveAnnotation) {
    final ExecutableElement element = avatarRule.getElementWithUniqueId(id);

    final boolean hasAnnotation = DefaultAnnoRetriever.hasAnnotation(element);

    assertThat(hasAnnotation, is(shouldHaveAnnotation));
  }
}