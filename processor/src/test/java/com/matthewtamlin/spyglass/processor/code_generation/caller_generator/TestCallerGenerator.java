package com.matthewtamlin.spyglass.processor.code_generation.caller_generator;

import com.matthewtamlin.avatar.rules.AvatarRule;
import com.matthewtamlin.spyglass.common.class_definitions.AndroidClassNames;
import com.matthewtamlin.spyglass.processor.code_generation.CallerDef;
import com.matthewtamlin.spyglass.processor.code_generation.CallerGenerator;
import com.matthewtamlin.spyglass.processor.core.CoreHelpers;
import com.matthewtamlin.spyglass.processor.framework.CompileChecker;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;

public class TestCallerGenerator {
	@Rule
	public final AvatarRule avatarRule = AvatarRule
			.builder()
			.withSourcesAt("processor/src/test/java/com/matthewtamlin/spyglass/processor/code_generation/" +
					"caller_generator/Data.java")
			.build();

	private CallerGenerator callerGenerator;

	@Before
	public void setup() {
		final CoreHelpers coreHelpers = new CoreHelpers(avatarRule.getElementUtils(), avatarRule.getTypeUtils());

		callerGenerator = new CallerGenerator(coreHelpers);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullCoreHelpers() {
		new CallerGenerator(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateFor_nullMethodSupplied() {
		callerGenerator.generateFor(null, CodeBlock.of(""), CodeBlock.of(""), CodeBlock.of(""));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateFor_nullContextParameterSupplied() {
		callerGenerator.generateFor(mock(ExecutableElement.class), null, CodeBlock.of(""), CodeBlock.of(""));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateFor_nullTargetParameterSupplied() {
		callerGenerator.generateFor(mock(ExecutableElement.class), CodeBlock.of(""), null, CodeBlock.of(""));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateFor_nullAttrsParameterSupplied() {
		callerGenerator.generateFor(mock(ExecutableElement.class), CodeBlock.of(""), CodeBlock.of(""), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateFor_elementWithNoHandlerAnnotation() {
		final ExecutableElement element = avatarRule.getElementWithUniqueId("no handler");

		callerGenerator.generateFor(
				element,
				CodeBlock.of("target"),
				CodeBlock.of("context"),
				CodeBlock.of("attrs"));
	}

	@Test
	public void testGenerateFor_elementWithCallHandler() {
		final ExecutableElement element = avatarRule.getElementWithUniqueId("call handler");

		final TypeSpec result = callerGenerator.generateFor(
				element,
				CodeBlock.of("target"),
				CodeBlock.of("context"),
				CodeBlock.of("attrs"));

		assertThat(result, is(notNullValue()));
		checkCompiles(result);
	}

	@Test
	public void testGenerateFor_elementWithValueHandlerButNoDefault() {
		final ExecutableElement element = avatarRule.getElementWithUniqueId("value handler no default");

		final TypeSpec result = callerGenerator.generateFor(
				element,
				CodeBlock.of("target"),
				CodeBlock.of("context"),
				CodeBlock.of("attrs"));

		assertThat(result, is(notNullValue()));
		checkCompiles(result);
	}

	@Test
	public void testGenerateFor_elementWithValueHandlerAndDefault() {
		final ExecutableElement element = avatarRule.getElementWithUniqueId("value handler with default");

		final TypeSpec result = callerGenerator.generateFor(
				element,
				CodeBlock.of("target"),
				CodeBlock.of("context"),
				CodeBlock.of("attrs"));

		assertThat(result, is(notNullValue()));
		checkCompiles(result);
	}

	private void checkCompiles(final TypeSpec anonymousTypeSpec) {
		// Anonymous class cannot be top level class, so nest the anonymous class as a field
		final TypeSpec wrapperTypeSpec = TypeSpec
				.classBuilder("Wrapper")
				.addField(FieldSpec.builder(ClassName.get(Data.class), "target").build())
				.addField(FieldSpec.builder(AndroidClassNames.CONTEXT, "context").build())
				.addField(FieldSpec.builder(AndroidClassNames.TYPED_ARRAY, "attrs").build())
				.addField(FieldSpec
						.builder(TypeName.OBJECT, "o")
						.initializer("$L", anonymousTypeSpec)
						.build())
				.build();

		final JavaFile wrapperJavaFile = JavaFile
				.builder("", wrapperTypeSpec)
				.build();

		final Set<JavaFile> filesToCompile = new HashSet<>();

		filesToCompile.add(wrapperJavaFile);
		filesToCompile.add(CallerDef.SRC_FILE);

		CompileChecker.checkCompiles(filesToCompile);
	}
}