package com.matthewtamlin.spyglass.processor.grouper.type_element_wrapper;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.processor.grouper.TypeElementWrapper;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;

import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class TestTypeElementWrapper {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processors" +
			"/grouper/type_element_wrapper/Data.java");

	private IdBasedElementSupplier elementSupplier;

	private TypeElement class1;

	private TypeElement class2;

	@Before
	public void setup() throws MalformedURLException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));

		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());
		final IdBasedElementSupplier elementSupplier = new IdBasedElementSupplier(dataFileObject);

		class1 = (TypeElement) elementSupplier.getUniqueElementWithId("class 1");
		class2 = (TypeElement) elementSupplier.getUniqueElementWithId("class 2");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullSupplied() {
		new TypeElementWrapper(null);
	}

	@Test
	public void testEquals_checkContractIsSatisfied() {
		final TypeElementWrapper wrapper1A = new TypeElementWrapper(class1);
		final TypeElementWrapper wrapper1B = new TypeElementWrapper(class1);
		final TypeElementWrapper wrapper1C = new TypeElementWrapper(class1);
		final TypeElementWrapper wrapper2 = new TypeElementWrapper(class2);

		// Check reflexive condition
		assertThat(wrapper1A, equalTo(wrapper1A));

		// Check symmetric condition
		assertThat(wrapper1A, equalTo(wrapper1B));
		assertThat(wrapper1B, equalTo(wrapper1A));
		assertThat(wrapper1A, not(equalTo(wrapper2)));
		assertThat(wrapper2, not(equalTo(wrapper1A)));

		// Check transitive condition
		assertThat(wrapper1A, equalTo(wrapper1B));
		assertThat(wrapper1B, equalTo(wrapper1C));
		assertThat(wrapper1A, equalTo(wrapper1C));

		// Check consistency (difficult to prove)
		for (int i = 0; i < 1000; i++) {
			assertThat(wrapper1A, equalTo(wrapper1B));
		}

		// Check null case
		assertThat(wrapper1A, not(equalTo(null)));
	}

	@Test
	public void testHashcode_checkContractIsSatisfied() {
		final TypeElementWrapper wrapper1A = new TypeElementWrapper(class1);
		final TypeElementWrapper wrapper1B = new TypeElementWrapper(class1);

		for (int i = 0; i < 1000; i++) {
			assertThat(wrapper1A.hashCode(), is(wrapper1B.hashCode()));
		}
	}

	@Test
	public void testUnwrap() {
		final TypeElementWrapper wrapper = new TypeElementWrapper(class1);

		// Expect returned value to be reference equal to passed in value
		assertThat(wrapper.unwrap() == class1, is(true));
	}
}