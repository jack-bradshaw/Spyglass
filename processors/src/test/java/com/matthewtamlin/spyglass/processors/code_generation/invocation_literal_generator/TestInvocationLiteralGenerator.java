package com.matthewtamlin.spyglass.processors.code_generation.invocation_literal_generator;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.java_compiler_utilities.element_supplier.CompilerMissingException;
import com.matthewtamlin.java_compiler_utilities.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.processors.code_generation.InvocationLiteralGenerator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.net.MalformedURLException;

import javax.lang.model.element.ExecutableElement;
import javax.tools.JavaFileObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.either;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class TestInvocationLiteralGenerator {
	
}