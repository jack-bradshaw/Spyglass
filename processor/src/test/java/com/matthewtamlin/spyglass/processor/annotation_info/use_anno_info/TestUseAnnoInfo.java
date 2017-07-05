package com.matthewtamlin.spyglass.processor.annotation_info.use_anno_info;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.processor.annotation_info.UseAnnoInfo;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.UseAnnoRetriever;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.net.MalformedURLException;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JUnit4.class)
public class TestUseAnnoInfo {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor" +
			"/annotation_info/use_anno_info/Data.java");

	@Rule
	public final CompilationRule compilationRule = new CompilationRule();

	private IdBasedElementSupplier elementSupplier;

	private Elements elementUtil;

	private UseAnnoInfo info;

	@Before
	public void setup() throws MalformedURLException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));
		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());
		elementSupplier = new IdBasedElementSupplier(dataFileObject);

		elementUtil = compilationRule.getElements();

		info = new UseAnnoInfo(elementUtil);
	}

	@Test
	public void testGetDefinedTypeFor_useBooleanAnnotation() {
		final TypeMirror expectedResult = elementUtil.getTypeElement(Boolean.class.getCanonicalName()).asType();

		doTest("with use boolean", expectedResult);
	}

	@Test
	public void testGetDefinedTypeFor_useByteAnnotation() {
		final TypeMirror expectedResult = elementUtil.getTypeElement(Byte.class.getCanonicalName()).asType();

		doTest("with use byte", expectedResult);
	}

	@Test
	public void testGetDefinedTypeFor_useCharAnnotation() {
		final TypeMirror expectedResult = elementUtil.getTypeElement(Character.class.getCanonicalName()).asType();

		doTest("with use char", expectedResult);
	}

	@Test
	public void testGetDefinedTypeFor_useDoubleAnnotation() {
		final TypeMirror expectedResult = elementUtil.getTypeElement(Double.class.getCanonicalName()).asType();

		doTest("with use double", expectedResult);
	}

	@Test
	public void testGetDefinedTypeFor_useFloatAnnotation() {
		final TypeMirror expectedResult = elementUtil.getTypeElement(Float.class.getCanonicalName()).asType();

		doTest("with use float", expectedResult);
	}

	@Test
	public void testGetDefinedTypeFor_useIntAnnotation() {
		final TypeMirror expectedResult = elementUtil.getTypeElement(Integer.class.getCanonicalName()).asType();

		doTest("with use int", expectedResult);
	}

	@Test
	public void testGetDefinedTypeFor_useLongAnnotation() {
		final TypeMirror expectedResult = elementUtil.getTypeElement(Long.class.getCanonicalName()).asType();

		doTest("with use long", expectedResult);
	}

	@Test
	public void testGetDefinedTypeFor_useNullAnnotation() {
		final ExecutableElement method = (ExecutableElement) elementSupplier.getUniqueElementWithId("with use null");
		final VariableElement parameter = method.getParameters().get(0);
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		assertThat(info.getTypeSuppliedBy(useAnnotation), is(nullValue()));
	}

	@Test
	public void testGetDefinedTypeFor_useShortAnnotation() {
		final TypeMirror expectedResult = elementUtil.getTypeElement(Short.class.getCanonicalName()).asType();

		doTest("with use short", expectedResult);
	}

	@Test
	public void testGetDefinedTypeFor_useStringAnnotation() {
		final TypeMirror expectedResult = elementUtil.getTypeElement(String.class.getCanonicalName()).asType();

		doTest("with use string", expectedResult);
	}

	private void doTest(final String id, final TypeMirror expectedResult) {
		final ExecutableElement method = (ExecutableElement) elementSupplier.getUniqueElementWithId(id);
		final VariableElement parameter = method.getParameters().get(0);
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		assertThat(info.getTypeSuppliedBy(useAnnotation), is(expectedResult));
	}
}