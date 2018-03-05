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

package com.matthewtamlin.spyglass.processor.framework;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

public class CompileChecker {
  private static final JavaFileObject dummyFileObject;
  
  static {
    final TypeSpec dummyType = TypeSpec.classBuilder("__DummyClass__").build();
    final JavaFile dummyFile = JavaFile.builder("__avoid_collisions__", dummyType).build();
    
    dummyFileObject = JavaFileObjects.forSourceString(dummyFile.packageName, dummyFile.toString());
  }
  
  public static void checkCompiles(final JavaFile javaFile) {
    final Set<JavaFile> files = new HashSet<>();
    files.add(javaFile);
    
    checkCompiles(files);
  }
  
  public static void checkCompiles(final Set<JavaFile> javaFile) {
    final Compilation compilation = javac()
        .withProcessors(new WorkerProcessor(javaFile))
        .compile(dummyFileObject);
    
    assertThat(compilation).succeeded();
  }
  
  /**
   * An AbstractProcessor which writes files to the filer in the init method. No annotation processing is done.
   */
  public static class WorkerProcessor extends AbstractProcessor {
    private final Set<JavaFile> files;
    
    public WorkerProcessor(final Set<JavaFile> files) {
      this.files = new HashSet<>(files);
    }
    
    @Override
    public synchronized void init(final ProcessingEnvironment processingEnvironment) {
      super.init(processingEnvironment);
      
      final Filer filer = processingEnvironment.getFiler();
      
      for (final JavaFile file : files) {
        try {
          file.writeTo(filer);
        } catch (final IOException e) {
          final String message = String.format("Could not write file \'%1$s\'", file.typeSpec.name);
          throw new RuntimeException(message, e);
        }
      }
    }
    
    @Override
    public boolean process(final Set<? extends TypeElement> set, final RoundEnvironment roundEnvironment) {
      return false;
    }
  }
}