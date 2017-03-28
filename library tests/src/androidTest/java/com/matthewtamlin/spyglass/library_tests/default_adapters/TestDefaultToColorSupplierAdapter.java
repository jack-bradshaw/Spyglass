package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.graphics.Color;

import com.matthewtamlin.spyglass.library.core.Supplier;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToColorSupplierAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorSupplier;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class TestDefaultToColorSupplierAdapter extends TestDefaultAdapter<
		Integer,
		DefaultToColorSupplier,
		DefaultToColorSupplierAdapter> {

	private Context context;

	private static Integer expectedDefaultValue;

	private DefaultToColorSupplierAdapter adapter;

	private DefaultToColorSupplier annotation;

	@Before
	public void setup() throws IllegalAccessException, InstantiationException {
		expectedDefaultValue = Color.RED;
		annotation = mock(DefaultToColorSupplier.class);
		adapter = new DefaultToColorSupplierAdapter();
		context = mock(Context.class);

		doReturn(ColorSupplier.class).when(annotation).value();
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
	public DefaultToColorSupplierAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToColorSupplier getAnnotation() {
		return annotation;
	}

	public static class ColorSupplier implements Supplier<Integer> {
		@Override
		public Integer get() {
			return expectedDefaultValue;
		}
	}
}