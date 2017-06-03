package com.matthewtamlin.spyglass.processors.grouper.grouper;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.processors.grouper.TypeElementWrapper;
import com.matthewtamlin.spyglass.processors.util.SetUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import static com.matthewtamlin.spyglass.processors.grouper.Grouper.groupByEnclosingClass;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(JUnit4.class)
public class TestGrouper {
	private static final File DATA_FILE = new File("processors/src/test/java/com/matthewtamlin/spyglass/processors" +
			"/core/grouper/Data.java");

	private IdBasedElementSupplier elementSupplier;

	private TypeElement primaryClass;

	private TypeElement secondaryClass;

	private TypeElement innerClass;

	private TypeElement veryNestedClass;

	private Set<Element> primaryClassChildren;

	private Set<Element> secondaryClassChildren;

	private Set<Element> innerClassChildren;

	private Set<Element> veryNestedClassChildren;

	@Before
	public void setup() throws MalformedURLException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));

		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());
		final IdBasedElementSupplier elementSupplier = new IdBasedElementSupplier(dataFileObject);

		primaryClass = (TypeElement) elementSupplier.getUniqueElementWithId("primary class");
		secondaryClass = (TypeElement) elementSupplier.getUniqueElementWithId("secondary class");
		innerClass = (TypeElement) elementSupplier.getUniqueElementWithId("inner class");
		veryNestedClass = (TypeElement) elementSupplier.getUniqueElementWithId("very nested class");

		primaryClassChildren = new HashSet<>();
		primaryClassChildren.add(elementSupplier.getUniqueElementWithId("primary class field"));
		primaryClassChildren.add(elementSupplier.getUniqueElementWithId("primary class method"));
		primaryClassChildren.add(elementSupplier.getUniqueElementWithId("inner class"));

		secondaryClassChildren = new HashSet<>();
		secondaryClassChildren.add(elementSupplier.getUniqueElementWithId("secondary class field"));
		secondaryClassChildren.add(elementSupplier.getUniqueElementWithId("secondary class method"));

		innerClassChildren = new HashSet<>();
		innerClassChildren.add(elementSupplier.getUniqueElementWithId("inner class field"));
		innerClassChildren.add(elementSupplier.getUniqueElementWithId("inner class method"));

		veryNestedClassChildren = new HashSet<>();
		veryNestedClassChildren.add(elementSupplier.getUniqueElementWithId("very nested class field"));
		veryNestedClassChildren.add(elementSupplier.getUniqueElementWithId("very nested class method"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGroupByEnclosingClass_nullSupplied() {
		groupByEnclosingClass(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGroupByEnclosingClass_collectionContainsNull() {
		final Set<Element> set = new HashSet<>();
		set.add(null);

		groupByEnclosingClass(set);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGroupByEnclosingClass_elementNotDirectChildOfTypeElement() {
		final Element element = elementSupplier.getUniqueElementWithId("element within method");

		groupByEnclosingClass(SetUtil.immutableSetOf(element));
	}

	@Test
	public void testGroupByEnclosingClass_primaryClassComponents() {
		final Map<TypeElementWrapper, Set<Element>> groupedByClass = groupByEnclosingClass(primaryClassChildren);

		assertThat("There should only be one group.", groupedByClass.size(), is(1));

		assertThat(
				"Wrong children found.",
				groupedByClass.get(new TypeElementWrapper(primaryClass)),
				is(primaryClassChildren));
	}

	@Test
	public void testGroupByEnclosingClass_secondaryClassComponents() {
		final Map<TypeElementWrapper, Set<Element>> groupedByClass = groupByEnclosingClass(secondaryClassChildren);

		assertThat("There should only be one group.", groupedByClass.size(), is(1));

		assertThat(
				"Wrong children found.",
				groupedByClass.get(new TypeElementWrapper(secondaryClass)),
				is(secondaryClassChildren));
	}

	@Test
	public void testGroupByEnclosingClass_innerClassComponents() {
		final Map<TypeElementWrapper, Set<Element>> groupedByClass = groupByEnclosingClass(innerClassChildren);

		assertThat("There should only be one group.", groupedByClass.size(), is(1));

		assertThat(
				"Wrong children found.",
				groupedByClass.get(new TypeElementWrapper(innerClass)),
				is(innerClassChildren));
	}

	@Test
	public void testGroupByEnclosingClass_veryNestedClassComponents() {
		final Map<TypeElementWrapper, Set<Element>> groupedByClass = groupByEnclosingClass(veryNestedClassChildren);

		assertThat("There should only be one group.", groupedByClass.size(), is(1));

		assertThat(
				"Wrong children found.",
				groupedByClass.get(new TypeElementWrapper(veryNestedClass)),
				is(veryNestedClassChildren));
	}

	@Test
	public void testGroupByEnclosingClass_allComponents() {
		final Set<Element> allChildren = new HashSet<>();
		allChildren.addAll(primaryClassChildren);
		allChildren.addAll(secondaryClassChildren);
		allChildren.addAll(innerClassChildren);
		allChildren.addAll(veryNestedClassChildren);

		final Map<TypeElementWrapper, Set<Element>> groupedByClass = groupByEnclosingClass(allChildren);

		assertThat("There should only be four groups.", groupedByClass.size(), is(4));

		assertThat("Wrong children found for primary class.",
				groupedByClass.get(new TypeElementWrapper(primaryClass)),
				is(primaryClassChildren));

		assertThat("Wrong children found for secondary class.",
				groupedByClass.get(new TypeElementWrapper(secondaryClass)),
				is(secondaryClassChildren));

		assertThat("Wrong children found for inner class.",
				groupedByClass.get(new TypeElementWrapper(innerClass)),
				is(innerClassChildren));

		assertThat("Wrong children found for very nested class.",
				groupedByClass.get(new TypeElementWrapper(veryNestedClass)),
				is(veryNestedClassChildren));
	}
}