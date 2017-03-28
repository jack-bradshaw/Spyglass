package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.core.Supplier;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToBooleanSupplierAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBooleanSupplier;

import org.junit.After;
import org.junit.Before;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class TestDefaultToBooleanSupplierAdapter extends TestDefaultAdapter<
		Boolean,
		DefaultToBooleanSupplier,
		DefaultToBooleanSupplierAdapter> {

	private Context context;

	private static Boolean expectedDefaultValue;

	private DefaultToBooleanSupplier annotation;

	private DefaultToBooleanSupplierAdapter adapter;

	@Before
	public void setup() throws IllegalAccessException, InstantiationException {
		expectedDefaultValue = Boolean.TRUE;
		annotation = mock(DefaultToBooleanSupplier.class);
		adapter = new DefaultToBooleanSupplierAdapter();
		context = mock(Context.class);

		doReturn(BooleanSupplier.class).when(annotation).value();
	}

	@After
	public void tearDown() {
		expectedDefaultValue = null;
	}

	@Override
	public Boolean getExpectedDefaultValue() {
		return expectedDefaultValue;
	}

	@Override
	public DefaultToBooleanSupplier getAnnotation() {
		return annotation;
	}

	@Override
	public DefaultToBooleanSupplierAdapter getAdapter() {
		return adapter;
	}

	@Override
	public Context getContext() {
		return context;
	}

	public static class BooleanSupplier implements Supplier<Boolean> {
		@Override
		public Boolean get() {
			return expectedDefaultValue;
		}
	}
}