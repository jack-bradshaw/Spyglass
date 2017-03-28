package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.core.Supplier;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToColorSupplierAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorSupplier;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToColorSupplierAdapter extends TestDefaultAdapter<
		Integer,
		DefaultToColorSupplier,
		DefaultToColorSupplierAdapter> {

	private static Integer expectedDefaultValue;

	private Context context;

	private DefaultToColorSupplierAdapter adapter;

	private DefaultToColorSupplier annotation;

	@Before
	public void setup() throws IllegalAccessException, InstantiationException {
		context = mock(Context.class);
		expectedDefaultValue = Color.RED;
		adapter = new DefaultToColorSupplierAdapter();

		annotation = mock(DefaultToColorSupplier.class);
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