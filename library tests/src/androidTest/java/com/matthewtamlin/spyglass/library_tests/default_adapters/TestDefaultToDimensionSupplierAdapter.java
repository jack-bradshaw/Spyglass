package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.core.DimensionUnit;
import com.matthewtamlin.spyglass.library.core.Supplier;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToDimensionSupplierAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimensionSupplier;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToDimensionSupplierAdapter extends TestDefaultAdapter<
		Float,
		DefaultToDimensionSupplier,
		DefaultToDimensionSupplierAdapter> {

	private static Float suppliedDimensionMm;

	private static DimensionUnit suppliedUnit;

	private Float expectedDefaultValue;

	private Context context;

	private DefaultToDimensionSupplier annotation;

	private DefaultToDimensionSupplierAdapter adapter;

	@Before
	public void setup() throws IllegalAccessException, InstantiationException {
		context = InstrumentationRegistry.getTargetContext();

		suppliedDimensionMm = 1000f;
		suppliedUnit = DimensionUnit.MM;

		expectedDefaultValue = suppliedUnit.convertToPx(context, suppliedDimensionMm);

		adapter = new DefaultToDimensionSupplierAdapter();

		annotation = mock(DefaultToDimensionSupplier.class);
		doReturn(FloatSupplier.class).when(annotation).valueSupplier();
		doReturn(UnitSupplier.class).when(annotation).unitSupplier();
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
	public Float getExpectedDefaultValue() {
		return expectedDefaultValue;
	}

	@Override
	public DefaultToDimensionSupplierAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToDimensionSupplier getAnnotation() {
		return annotation;
	}

	public static class FloatSupplier implements Supplier<Float> {
		@Override
		public Float get() {
			return suppliedDimensionMm;
		}
	}

	public static class UnitSupplier implements Supplier<DimensionUnit> {
		@Override
		public DimensionUnit get() {
			return suppliedUnit;
		}
	}
}