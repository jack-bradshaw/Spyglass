package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.core.Supplier;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToBooleanSupplierAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBooleanSupplier;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToBooleanSupplierAdapter extends TestDefaultAdapter<
		Boolean,
		DefaultToBooleanSupplier,
		DefaultToBooleanSupplierAdapter> {

	private static Boolean expectedDefaultValue;

	private Context context;

	private DefaultToBooleanSupplier annotation;

	private DefaultToBooleanSupplierAdapter adapter;

	@Before
	public void setup() throws IllegalAccessException, InstantiationException {
		context = mock(Context.class);
		expectedDefaultValue = Boolean.TRUE;
		adapter = new DefaultToBooleanSupplierAdapter();

		annotation = mock(DefaultToBooleanSupplier.class);
		doReturn(BooleanSupplier.class).when(annotation).value();
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
	public Boolean getExpectedDefaultValue() {
		return expectedDefaultValue;
	}

	@Override
	public DefaultToBooleanSupplierAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToBooleanSupplier getAnnotation() {
		return annotation;
	}

	public static class BooleanSupplier implements Supplier<Boolean> {
		@Override
		public Boolean get() {
			return expectedDefaultValue;
		}
	}
}