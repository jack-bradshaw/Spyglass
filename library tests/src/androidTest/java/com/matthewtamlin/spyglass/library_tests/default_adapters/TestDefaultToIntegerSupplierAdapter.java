package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.core.supplier.Supplier;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToIntegerSupplierAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToIntegerSupplier;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToIntegerSupplierAdapter extends TestDefaultAdapter<
		Integer,
		DefaultToIntegerSupplier,
		DefaultToIntegerSupplierAdapter> {

	private static Integer expectedDefaultValue;

	private Context context;

	private DefaultToIntegerSupplier annotation;

	private DefaultToIntegerSupplierAdapter adapter;

	@Before
	public void setup() throws IllegalAccessException, InstantiationException {
		context = mock(Context.class);
		expectedDefaultValue = -22846286;
		adapter = new DefaultToIntegerSupplierAdapter();

		annotation = mock(DefaultToIntegerSupplier.class);
		doReturn(IntegerSupplier.class).when(annotation).value();
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
	public Integer getExpectedDefaultValue() {
		return expectedDefaultValue;
	}

	@Override
	public DefaultToIntegerSupplierAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToIntegerSupplier getAnnotation() {
		return annotation;
	}

	public static class IntegerSupplier implements Supplier<Integer> {
		@Override
		public Integer get() {
			return expectedDefaultValue;
		}
	}
}