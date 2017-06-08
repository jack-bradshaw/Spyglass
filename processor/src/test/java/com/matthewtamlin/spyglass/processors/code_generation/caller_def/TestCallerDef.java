package com.matthewtamlin.spyglass.processors.code_generation.caller_def;

import com.matthewtamlin.spyglass.processors.code_generation.CallerDef;
import com.matthewtamlin.spyglass.processors.testing_utils.CompileChecker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestCallerDef {
	@Test
	public void testGetJavaFile_checkFileCompiles() {
		CompileChecker.checkCompiles(CallerDef.getJavaFile());
	}
}