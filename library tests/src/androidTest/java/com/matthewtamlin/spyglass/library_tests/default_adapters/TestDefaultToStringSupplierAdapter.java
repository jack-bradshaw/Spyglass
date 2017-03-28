package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.core.Supplier;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToStringSupplierAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToStringSupplier;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToStringSupplierAdapter extends TestDefaultAdapter<
		String,
		DefaultToStringSupplier,
		DefaultToStringSupplierAdapter> {

	private static String expectedDefaultValue;

	private Context context;

	private DefaultToStringSupplier annotation;

	private DefaultToStringSupplierAdapter adapter;

	@Before
	public void setup() throws IllegalAccessException, InstantiationException {
		context = mock(Context.class);
		expectedDefaultValue = "There are no Strings on me...";
		adapter = new DefaultToStringSupplierAdapter();

		annotation = mock(DefaultToStringSupplier.class);
		doReturn(StringSupplier.class).when(annotation).value();
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
	public String getExpectedDefaultValue() {
		return expectedDefaultValue;
	}

	@Override
	public DefaultToStringSupplierAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToStringSupplier getAnnotation() {
		return annotation;
	}

	public static class StringSupplier implements Supplier<String> {
		@Override
		public String get() {
			return expectedDefaultValue;
		}
	}
}