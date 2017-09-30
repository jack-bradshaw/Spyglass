package com.matthewtamlin.spyglass.processor.definitions;

import com.matthewtamlin.spyglass.processor.framework.CompileChecker;

import org.junit.Test;

public class TestTargetExceptionDef {
	@Test
	public void testGetJavaFile_checkFileCompiles() {
		CompileChecker.checkCompiles(TargetExceptionDef.SRC_FILE);
	}
}