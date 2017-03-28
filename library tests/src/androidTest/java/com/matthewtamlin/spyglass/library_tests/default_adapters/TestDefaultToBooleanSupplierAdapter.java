package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.core.Supplier;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToBooleanSupplierAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBooleanSupplier;

import org.junit.Before;
import org.junit.BeforeClass;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class TestDefaultToBooleanSupplierAdapter extends TestDefaultAdapter<
		Boolean,
		DefaultToBooleanSupplier,
		DefaultToBooleanSupplierAdapter> {

	private static Boolean expectedDefaultValue;

	private DefaultToBooleanSupplier annotation;

	private DefaultToBooleanSupplierAdapter adapter;

	private Context context;

	@BeforeClass
	public static void setupClass() {
		expectedDefaultValue = Boolean.TRUE;
	}

	@Before
	public void setup() throws IllegalAccessException, InstantiationException {
		annotation = mock(DefaultToBooleanSupplier.class);
		adapter = new DefaultToBooleanSupplierAdapter();
		context = mock(Context.class);

		doReturn(BooleanSupplier.class).when(annotation).value();
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