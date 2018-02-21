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

package com.matthewtamlin.spyglass.processor.annotation_retrievers.use_annotation_retriever;

import com.matthewtamlin.avatar.rules.AvatarRule;
import com.matthewtamlin.spyglass.markers.annotations.placeholder_annotations.*;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.PlaceholderRetriever;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

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
    PlaceholderRetriever.getAnnotation(null);
  }

  @Test
  public void testGetAnnotation_useBooleanAnnotationPresent() {
    final VariableElement element = avatarRule.getElementWithUniqueId("boolean");

    final AnnotationMirror mirror = PlaceholderRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(UseBoolean.class.getName()));
  }

  @Test
  public void testGetAnnotation_useByteAnnotationPresent() {
    final VariableElement element = avatarRule.getElementWithUniqueId("byte");

    final AnnotationMirror mirror = PlaceholderRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(UseByte.class.getName()));
  }

  @Test
  public void testGetAnnotation_useCharAnnotationPresent() {
    final VariableElement element = avatarRule.getElementWithUniqueId("char");

    final AnnotationMirror mirror = PlaceholderRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(UseChar.class.getName()));
  }

  @Test
  public void testGetAnnotation_useDoubleAnnotationPresent() {
    final VariableElement element = avatarRule.getElementWithUniqueId("double");

    final AnnotationMirror mirror = PlaceholderRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(UseDouble.class.getName()));
  }

  @Test
  public void testGetAnnotation_useFloatAnnotationPresent() {
    final VariableElement element = avatarRule.getElementWithUniqueId("float");

    final AnnotationMirror mirror = PlaceholderRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(UseFloat.class.getName()));
  }

  @Test
  public void testGetAnnotation_useIntAnnotationPresent() {
    final VariableElement element = avatarRule.getElementWithUniqueId("int");

    final AnnotationMirror mirror = PlaceholderRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(UseInt.class.getName()));
  }

  @Test
  public void testGetAnnotation_useLongAnnotationPresent() {
    final VariableElement element = avatarRule.getElementWithUniqueId("long");

    final AnnotationMirror mirror = PlaceholderRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(UseLong.class.getName()));
  }

  @Test
  public void testGetAnnotation_useNullAnnotationPresent() {
    final VariableElement element = avatarRule.getElementWithUniqueId("null");

    final AnnotationMirror mirror = PlaceholderRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(UseNull.class.getName()));
  }

  @Test
  public void testGetAnnotation_useShortAnnotationPresent() {
    final VariableElement element = avatarRule.getElementWithUniqueId("short");

    final AnnotationMirror mirror = PlaceholderRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(UseShort.class.getName()));
  }

  @Test
  public void testGetAnnotation_useStringAnnotationPresent() {
    final VariableElement element = avatarRule.getElementWithUniqueId("string");

    final AnnotationMirror mirror = PlaceholderRetriever.getAnnotation(element);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(UseString.class.getName()));
  }

  @Test
  public void testGetAnnotation_noUseAnnotationPresent() {
    final VariableElement element = avatarRule.getElementWithUniqueId("no use-annotation");

    final AnnotationMirror mirror = PlaceholderRetriever.getAnnotation(element);

    assertThat(mirror, is(nullValue()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHasAnnotation_nullSupplied() {
    PlaceholderRetriever.hasAnnotation(null);
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
    doHasAnnotationTestForElementWithId("no use-annotation", false);
  }

  private void doHasAnnotationTestForElementWithId(final String id, final boolean shouldHaveAnnotation) {
    final VariableElement element = avatarRule.getElementWithUniqueId(id);

    final boolean hasAnnotation = PlaceholderRetriever.hasAnnotation(element);

    assertThat(hasAnnotation, is(shouldHaveAnnotation));
  }
}