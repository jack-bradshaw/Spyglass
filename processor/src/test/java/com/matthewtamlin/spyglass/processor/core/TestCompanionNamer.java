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

package com.matthewtamlin.spyglass.processor.core;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.rules.AvatarRule;
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
      .withSourceFileObjects(
          JavaFileObjects.forResource(getClass().getResource("TestCompanionNamerData.java")))
      .build();
  
  @Test
  public void testGetCompanionNameFor_topLevelClass() {
    final TypeElement element = avatarRule.getElementWithUniqueId("top level");
    
    final String companionName = CompanionNamer.getCompanionNameFor(element);
    final String expectedName = "TestCompanionNamerData_SpyglassCompanion";
    
    assertThat(companionName, is(expectedName));
  }
  
  @Test
  public void testGetCompanionNameFor_classNestedByOneLevel() {
    final TypeElement element = avatarRule.getElementWithUniqueId("nested one level");
    
    final String companionName = CompanionNamer.getCompanionNameFor(element);
    final String expectedName = "TestCompanionNamerData_ClassA_SpyglassCompanion";
    
    assertThat(companionName, is(expectedName));
  }
  
  @Test
  public void testGetCompanionNameFor_classNestedByMultipleLevels() {
    final TypeElement element = avatarRule.getElementWithUniqueId("nested multiple levels");
    
    final String companionName = CompanionNamer.getCompanionNameFor(element);
    final String expectedName = "TestCompanionNamerData_ClassA_ClassB_ClassC_ClassD_SpyglassCompanion";
    
    assertThat(companionName, is(expectedName));
  }
}