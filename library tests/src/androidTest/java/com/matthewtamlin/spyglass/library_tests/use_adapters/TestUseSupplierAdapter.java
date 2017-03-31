package com.matthewtamlin.spyglass.library_tests.use_adapters;

import com.matthewtamlin.spyglass.library.core.supplier.Supplier;
import com.matthewtamlin.spyglass.library.use_adapters.UseAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseSupplierAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseSupplier;

import org.junit.Before;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class TestUseSupplierAdapter extends TestUseAdapter<Object, UseSupplier> {
	private static Object expectedValue;

	private UseSupplier annotation;

	private UseAdapter<Object, UseSupplier> adapter;

	@Override
	public Object getExpectedValue() {
		return expectedValue;
	}

	@Override
	public UseSupplier getAnnotation() {
		return annotation;
	}

	@Override
	public UseAdapter<Object, UseSupplier> getAdapter() {
		return adapter;
	}

	@Before
	public void setup() {
		expectedValue = "Here is a String, which through polymorphism is also an Object.";

		annotation = mock(UseSupplier.class);
		doReturn(TestSupplier.class).when(annotation).value();

		adapter = new UseSupplierAdapter();
	}

	public static class TestSupplier implements Supplier<Object> {
		@Override
		public Object get() {
			return expectedValue;
		}
	}
}