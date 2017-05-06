package com.matthewtamlin.spyglass.processors.validation;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.spyglass.processors.validation.resources.Processor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.net.MalformedURLException;

import static com.google.common.truth.Truth.ASSERT;
import static com.google.common.truth.Truth.assertWithMessage;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

@RunWith(JUnit4.class)
public class TestValidator {
	private static final File DATA_FILE = new File("processors/src/test/java/com/matthewtamlin/spyglass/processors/" +
			"validation/resources/Data.java");

	@Before
	public void setup() {
		assertWithMessage("Data file does not exist.").that(DATA_FILE.exists()).isTrue();
	}

	@Test
	public void doTest() throws MalformedURLException {
		ASSERT.about(javaSource())
				.that(JavaFileObjects.forResource(DATA_FILE.toURI().toURL()))
				.processedWith(new Processor())
				.compilesWithoutError();
	}
}