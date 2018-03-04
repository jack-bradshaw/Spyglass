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


import com.google.common.collect.ImmutableList;
import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.processor.annotationretrievers.ConditionalHandlerRetriever;
import com.matthewtamlin.spyglass.processor.annotationretrievers.DefaultRetriever;
import com.matthewtamlin.spyglass.processor.annotationretrievers.UnconditionalHandlerRetriever;
import com.matthewtamlin.spyglass.processor.definitions.AnnotationRegistry;

import javax.inject.Inject;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.util.*;

import static javax.lang.model.element.Modifier.PRIVATE;

@Tested(testMethod = "automated")
public class BasicValidator implements Validator {
  private final List<Rule> rules = ImmutableList.of(
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          if (countValueHandlerAnnotations(element) + countCallHandlerAnnotations(element) > 1) {
            return Result.createFailure("Methods must not have multiple handler annotations.");
          }
          
          return Result.createSuccessful();
        }
      },
      
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          if (countDefaultAnnotations(element) > 1) {
            return Result.createFailure("Methods must not have multiple default annotations.");
          }
          
          return Result.createSuccessful();
        }
      },
      
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          if (DefaultRetriever.hasAnnotation(element) &&
              !UnconditionalHandlerRetriever.hasAnnotation(element)) {
            return Result.createFailure(
                "Methods without handler annotations must not have default annotations.");
          }
          
          return Result.createSuccessful();
        }
      },
      
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          if (DefaultRetriever.hasAnnotation(element) &&
              ConditionalHandlerRetriever.hasAnnotation(element)) {
            
            return Result.createFailure(
                "Methods with conditional handler annotations must not have default annotations.");
          }
          
          return Result.createSuccessful();
        }
      },
      
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          final int parameterCount = ((ExecutableElement) element).getParameters().size();
          
          if (UnconditionalHandlerRetriever.hasAnnotation(element) && parameterCount < 1) {
            return Result.createFailure("Methods with simple handler annotations must have at least one parameter.");
          }
          
          return Result.createSuccessful();
        }
      },
      
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          final Map<Integer, Set<Annotation>> placeholderAnnotations = getPlaceholderAnnotations(element);
          
          for (final Integer paramIndex : placeholderAnnotations.keySet()) {
            if (placeholderAnnotations.get(paramIndex).size() > 1) {
              return Result.createFailure("Parameters must not have multiple placeholder annotations.");
            }
          }
          
          return Result.createSuccessful();
        }
      },
      
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          final int paramCount = ((ExecutableElement) element).getParameters().size();
          final int annotatedParamCount = countNonEmptySets(getPlaceholderAnnotations(element).values());
          
          if (UnconditionalHandlerRetriever.hasAnnotation(element) &&
              annotatedParamCount != paramCount - 1) {
            
            return Result.createFailure(
                "Methods with simple handler annotations must have placeholder annotations on all but one parameter.");
          }
          
          return Result.createSuccessful();
        }
      },
      
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          final int paramCount = ((ExecutableElement) element).getParameters().size();
          final int annotatedParamCount = countNonEmptySets(getPlaceholderAnnotations(element).values());
          
          if (ConditionalHandlerRetriever.hasAnnotation(element) && annotatedParamCount != paramCount) {
            return Result.createFailure(
                "Methods with conditional handler annotations must have placeholder annotations on all parameters.");
          }
          
          return Result.createSuccessful();
        }
      },
      
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          if (element.getModifiers().contains(PRIVATE)) {
            return Result.createFailure(
                "Methods with handler annotations must have public, protected, or default access. " +
                    "Private methods are not compatible with the Spyglass Framework.");
          }
          
          return Result.createSuccessful();
        }
      },
      
      new Rule() {
        @Override
        public Result checkElement(final ExecutableElement element) {
          if (!hasStaticRoot(element)) {
            return Result.createFailure("Anonymous and local classes are not compatible with the Spyglass Framework.");
          }
          
          return Result.createSuccessful();
        }
        
        private boolean hasStaticRoot(final Element element) {
          final TypeElement parent = (TypeElement) element.getEnclosingElement();
          
          if (parent == null) {
            return true;
          }
          
          switch (parent.getNestingKind()) {
            case TOP_LEVEL:
              return true;
            case MEMBER:
              return true;
            case LOCAL:
              return false;
            case ANONYMOUS:
              return false;
          }
          
          throw new RuntimeException("Should never get here.");
        }
      });
  
  @Inject
  public BasicValidator() {}
  
  public Result validate(final ExecutableElement element) {
    for (final Rule rule : rules) {
      final Result result = rule.checkElement(element);
      
      if (!result.isSuccessful()) {
        return result;
      }
    }
    
    return Result.createSuccessful();
  }
  
  private static int countCallHandlerAnnotations(final ExecutableElement method) {
    int count = 0;
    
    for (final Class<? extends Annotation> annotationClass : AnnotationRegistry.CONDITIONAL_HANDLERS) {
      if (method.getAnnotation(annotationClass) != null) {
        count++;
      }
    }
    
    return count;
  }
  
  private static int countValueHandlerAnnotations(final ExecutableElement method) {
    int count = 0;
    
    for (final Class<? extends Annotation> annotationClass : AnnotationRegistry.UNCONDITIONAL_HANDLERS) {
      if (method.getAnnotation(annotationClass) != null) {
        count++;
      }
    }
    
    return count;
  }
  
  private static int countDefaultAnnotations(final ExecutableElement method) {
    int count = 0;
    
    for (final Class<? extends Annotation> annotationClass : AnnotationRegistry.DEFAULTS) {
      if (method.getAnnotation(annotationClass) != null) {
        count++;
      }
    }
    
    return count;
  }
  
  private static Map<Integer, Set<Annotation>> getPlaceholderAnnotations(final ExecutableElement method) {
    final Map<Integer, Set<Annotation>> placeholderAnnotations = new HashMap<>();
    
    final List<? extends VariableElement> params = method.getParameters();
    
    for (int i = 0; i < params.size(); i++) {
      placeholderAnnotations.put(i, new HashSet<>());
      
      for (final Class<? extends Annotation> annotationClass : AnnotationRegistry.PLACEHOLDERS) {
        final Annotation foundAnnotation = params.get(i).getAnnotation(annotationClass);
        
        if (foundAnnotation != null) {
          placeholderAnnotations.get(i).add(foundAnnotation);
        }
      }
    }
    
    return placeholderAnnotations;
  }
  
  private static int countNonEmptySets(final Collection<? extends Set> collection) {
    int count = 0;
    
    for (final Set<?> s : collection) {
      if (!s.isEmpty()) {
        count++;
      }
    }
    
    return count;
  }
  
  private interface Rule {
    public Result checkElement(ExecutableElement element);
  }
}