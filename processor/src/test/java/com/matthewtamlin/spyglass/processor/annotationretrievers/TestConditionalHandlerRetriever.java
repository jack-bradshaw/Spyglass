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

package com.matthewtamlin.spyglass.processor.annotationretrievers;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.rules.AvatarRule;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificBooleanHandler;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificEnumHandler;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificFlagHandler;
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
public class TestConditionalHandlerRetriever {
  @Rule
  public final AvatarRule avatarRule = AvatarRule
      .builder()
      .withSourceFileObjects(
          JavaFileObjects.forResource(getClass().getResource("TestConditionalHandlerRetrieverData.java")))
      .build();
  
  @Test(expected = IllegalArgumentException.class)
  public void testGetAnnotation_nullSupplied() {
    ConditionalHandlerRetriever.getAnnotation(null);
  }
  
  @Test
  public void testGetAnnotation_specificBooleanHandlerAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("specific boolean");
    
    final AnnotationMirror mirror = ConditionalHandlerRetriever.getAnnotation(element);
    
    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(SpecificBooleanHandler.class.getName()));
  }
  
  @Test
  public void testGetAnnotation_specificEnumHandlerAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("specific enum");
    
    final AnnotationMirror mirror = ConditionalHandlerRetriever.getAnnotation(element);
    
    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(SpecificEnumHandler.class.getName()));
  }
  
  @Test
  public void testGetAnnotation_specificFlagHandlerAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("specific flag");
    
    final AnnotationMirror mirror = ConditionalHandlerRetriever.getAnnotation(element);
    
    assertThat(mirror, is(notNullValue()));
    assertThat(mirror.getAnnotationType().toString(), is(SpecificFlagHandler.class.getName()));
  }
  
  @Test
  public void testGetAnnotation_noCallHandlerAnnotationPresent() {
    final ExecutableElement element = avatarRule.getElementWithUniqueId("no call handler annotation");
    
    final AnnotationMirror mirror = ConditionalHandlerRetriever.getAnnotation(element);
    
    assertThat(mirror, is(nullValue()));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testHasAnnotation_nullSupplied() {
    ConditionalHandlerRetriever.hasAnnotation(null);
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
    
    final boolean hasAnnotation = ConditionalHandlerRetriever.hasAnnotation(element);
    
    assertThat(hasAnnotation, is(shouldHaveAnnotation));
  }
}