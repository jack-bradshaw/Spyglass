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

package com.matthewtamlin.spyglass.processor.validation;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.rules.AvatarRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@RunWith(JUnit4.class)
public class TestBasicValidator {
  @Rule
  public final AvatarRule avatarRule = AvatarRule
      .builder()
      .withSourceFileObjects(JavaFileObjects.forResource(getClass().getResource("TestBasicValidatorData.java")))
      .build();

  private Set<Element> elements;

  private BasicValidator validator;

  @Before
  public void setup() {
    elements = avatarRule.getElementsWithAnnotation(Target.class);

    validator = new BasicValidator();
  }

  @Test
  public void testValidateElement_usingDataFileElements() {
    for (final Element element : elements) {
      if (element.getKind() != ElementKind.METHOD) {
        throw new RuntimeException("All test elements must be executable elements (e.g. methods).");
      }

      final Target targetAnnotation = element.getAnnotation(Target.class);
      final boolean shouldPassValidation = targetAnnotation.isValid();
      final Result validationResult = validator.validate((ExecutableElement) element);

      assertThat(validationResult.isSuccessful(), is(shouldPassValidation));
    }
  }
}