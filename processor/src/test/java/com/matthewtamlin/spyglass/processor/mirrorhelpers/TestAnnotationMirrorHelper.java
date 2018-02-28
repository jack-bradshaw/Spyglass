/*
 * Copyright 2017-2018 Matthew David Tamlin
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

package com.matthewtamlin.spyglass.processor.mirrorhelpers;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.rules.AvatarRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class TestAnnotationMirrorHelper {
  @Rule
  public final AvatarRule avatarRule = AvatarRule
      .builder()
      .withSourceFileObjects(JavaFileObjects.forResource(getClass().getResource("TestAnnotationMirrorHelperData.java")))
      .build();

  private AnnotationMirrorHelper helper;

  @Before
  public void setup() {
    helper = new AnnotationMirrorHelper(avatarRule.getElementUtils());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_nullElementUtil() {
    new AnnotationMirrorHelper(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetAnnotationMirror_nullElement() {
    AnnotationMirrorHelper.getAnnotationMirror(null, Annotation.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetAnnotationMirror_nullAnnotationClass() {
    AnnotationMirrorHelper.getAnnotationMirror(mock(Element.class), null);
  }

  @Test
  public void testGetAnnotationMirror_annotationMissing() {
    final Element element = avatarRule.getElementWithUniqueId("get annotation mirror: without annotation");

    final AnnotationMirror mirror =
        AnnotationMirrorHelper.getAnnotationMirror(element, SomeAnnotationWithValue.class);

    assertThat(mirror, is(nullValue()));
  }

  @Test
  public void testGetAnnotationMirror_annotationPresent() {
    final Element element = avatarRule.getElementWithUniqueId("get annotation mirror: with annotation");

    final AnnotationMirror mirror = AnnotationMirrorHelper
        .getAnnotationMirror(element, SomeAnnotationWithValue.class);

    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(SomeAnnotationWithValue.class.getName()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueUsingDefaults_nullAnnotationMirrorSupplied() {
    helper.getValueUsingDefaults(null, "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetValueUsingDefaults_nullValueKeySupplied() {
    helper.getValueUsingDefaults(mock(AnnotationMirror.class), null);
  }

  @Test
  public void testGetValueIgnoringDefaults_invalidKey() {
    final Element e = avatarRule.getElementWithUniqueId("get annotation value ignoring defaults: with value");
    final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(e, SomeAnnotationWithValue.class);

    final AnnotationValue value = helper.getValueIgnoringDefaults(mirror, "invalidKey");

    assertThat(value, is(nullValue()));
  }

  @Test
  public void testGetValueIgnoreDefaults_noValueProvided() {
    final Element e = avatarRule.getElementWithUniqueId("get annotation value ignoring defaults: no value");
    final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(e, SomeAnnotationWithValue.class);

    final AnnotationValue value = helper.getValueIgnoringDefaults(mirror, "value");

    assertThat(value, is(nullValue()));
  }

  @Test
  public void testGetValueIgnoringDefaults_valueProvided() {
    final Element e = avatarRule.getElementWithUniqueId("get annotation value ignoring defaults: with value");
    final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(e, SomeAnnotationWithValue.class);

    final AnnotationValue value = helper.getValueIgnoringDefaults(mirror, "value");

    assertThat(value, is(notNullValue()));
    assertThat((String) value.getValue(), is("some value"));
  }

  @Test
  public void testGetValueUsingDefaults_invalidKey() {
    final Element e = avatarRule.getElementWithUniqueId("get annotation value with defaults: with value");
    final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(e, SomeAnnotationWithValue.class);

    final AnnotationValue value = helper.getValueUsingDefaults(mirror, "invalidKey");

    assertThat(value, is(nullValue()));
  }

  @Test
  public void testGetValueUsingDefaults_noValueProvided() {
    final Element e = avatarRule.getElementWithUniqueId("get annotation value with defaults: no value");
    final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(e, SomeAnnotationWithValue.class);

    final AnnotationValue value = helper.getValueUsingDefaults(mirror, "value");

    assertThat(value, is(notNullValue()));
    assertThat((String) value.getValue(), is(SomeAnnotationWithValue.DEFAULT_VALUE));
  }

  @Test
  public void testGetValueUsingDefaults_valueProvided() {
    final Element e = avatarRule.getElementWithUniqueId("get annotation value with defaults: with value");
    final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(e, SomeAnnotationWithValue.class);

    final AnnotationValue value = helper.getValueUsingDefaults(mirror, "value");

    assertThat(value, is(notNullValue()));
    assertThat((String) value.getValue(), is("some value"));
  }
}