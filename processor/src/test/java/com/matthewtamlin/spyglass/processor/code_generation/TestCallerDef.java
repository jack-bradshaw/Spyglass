package com.matthewtamlin.spyglass.processor.code_generation;

import com.matthewtamlin.spyglass.processor.framework.CompileChecker;

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