package com.matthewtamlin.spyglass.processor.definitions;

import com.matthewtamlin.spyglass.processor.framework.CompileChecker;

import org.junit.Test;

public class TestCompanionDef {
	@Test
	public void testGetJavaFile_checkFileCompiles() {
		CompileChecker.checkCompiles(CompanionDef.SRC_FILE);
	}
}
