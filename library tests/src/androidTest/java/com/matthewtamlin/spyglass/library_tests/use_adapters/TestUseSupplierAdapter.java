package com.matthewtamlin.spyglass.library_tests.use_adapters;

import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.core.supplier.Supplier;
import com.matthewtamlin.spyglass.library.use_adapters.UseAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseSupplierAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseSuppliedValue;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class TestUseSupplierAdapter extends TestUseAdapter<Object, UseSuppliedValue> {
	private static Object expectedValue;

	private UseSuppliedValue annotation;

	private UseAdapter<Object, UseSuppliedValue> adapter;

	@Override
	public Object getExpectedValue() {
		return expectedValue;
	}

	@Override
	public UseSuppliedValue getAnnotation() {
		return annotation;
	}

	@Override
	public UseAdapter<Object, UseSuppliedValue> getAdapter() {
		return adapter;
	}

	@Before
	public void setup() {
		expectedValue = "Here is a String, which through polymorphism is also an Object.";

		annotation = mock(UseSuppliedValue.class);
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