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

package com.matthewtamlin.spyglass.processor.annotation_retrievers.value_handler_annotation_retriever;

import com.matthewtamlin.avatar.rules.AvatarRule;
import com.matthewtamlin.spyglass.markers.annotations.unconditional_handler_annotations.*;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.ValueHandlerAnnoRetriever;
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
public class TestValueHandlerAnnotationRetriever {
  @Rule
  public final AvatarRule avatarRule = AvatarRule
      .builder()
      .withSourcesAt("processor/src/test/java/com/matthewtamlin/spyglass/processor/annotation_retrievers/" +
          "value_handler_annotation_retriever/Data.java")
      .build();

  @Test(expected = IllegalArgumentException.class)
  public void testGetAnnotation_nullSupplied() {
    ValueHandlerAnnoRetriever.getAnnotation(null);
  }

  @Test
  public void testGetAnnotation_booleanHandlerAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("boolean");

    final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(BooleanHandler.class.getName()));
  }

  @Test
  public void testGetAnnotation_colorHandlerAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("color");

    final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(ColorHandler.class.getName()));
  }

  @Test
  public void testGetAnnotation_colorStateListHandlerAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("color state list");

    final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(ColorStateListHandler.class.getName()));
  }

  @Test
  public void testGetAnnotation_dimensionHandlerAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("dimension");

    final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DimensionHandler.class.getName()));
  }

  @Test
  public void testGetAnnotation_drawableHandlerAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("drawable");

    final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(DrawableHandler.class.getName()));
  }

  @Test
  public void testGetAnnotation_enumConstantHandlerAnnotationPresent() {

    final ExecutableElement element = avatarRule.getElementWithUniqueId("enum constant");

    final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(EnumConstantHandler.class.getName()));
  }

  @Test
  public void testGetAnnotation_enumOrdinalHandlerAnnotationPresent() {

    final ExecutableElement element = avatarRule.getElementWithUniqueId("enum ordinal");

    final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(EnumOrdinalHandler.class.getName()));
  }

  @Test
  public void testGetAnnotation_floatHandlerAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("float");

    final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(FloatHandler.class.getName()));
  }

  @Test
  public void testGetAnnotation_fractionHandlerAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("fraction");

    final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(FractionHandler.class.getName()));
  }

  @Test
  public void testGetAnnotation_integerHandlerAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("integer");

    final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(IntegerHandler.class.getName()));
  }

  @Test
  public void testGetAnnotation_stringHandlerAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("string");

    final AnnotationMirror mirror = ValueHandlerAnnoRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(StringHandler.class.getName()));
  }

  @Test
  public void testGetAnnotation_noValueHandlerAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("no value handler annotation");

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

  private void doHasAnnotationTestForElementWithId(final String id, final boolean shouldHaveAnnotation) {
    final ExecutableElement element = avatarRule.getElementWithUniqueId(id);

    final boolean hasAnnotation = ValueHandlerAnnoRetriever.hasAnnotation(element);

    assertThat(hasAnnotation, is(shouldHaveAnnotation));
  }
}