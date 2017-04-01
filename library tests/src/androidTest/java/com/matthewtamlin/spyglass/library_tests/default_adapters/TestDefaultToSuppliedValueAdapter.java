package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.core.supplier.Supplier;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToSuppliedValueAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToSuppliedValue;

import org.junit.After;
import org.junit.Before;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class TestDefaultToSuppliedValueAdapter extends TestDefaultAdapter<
		Object,
		DefaultToSuppliedValue,
		DefaultToSuppliedValueAdapter> {

	private static Object expectedDefaultValue;

	private Context context;

	private DefaultToSuppliedValue annotation;

	private DefaultToSuppliedValueAdapter adapter;

	@Before
	public void setup() throws IllegalAccessException, InstantiationException {
		context = mock(Context.class);
		expectedDefaultValue = mock(Object.class);
		adapter = new DefaultToSuppliedValueAdapter();

		annotation = mock(DefaultToSuppliedValue.class);
		doReturn(TestSupplier.class).when(annotation).value();
	}

	@After
	public void tearDown() {
		expectedDefaultValue = null;
	}

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public Object getExpectedDefaultValue() {
		return expectedDefaultValue;
	}

	@Override
	public DefaultToSuppliedValueAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToSuppliedValue getAnnotation() {
		return annotation;
	}

	public static class TestSupplier implements Supplier<Object> {
		@Override
		public Object get() {
			return expectedDefaultValue;
		}
	}
}