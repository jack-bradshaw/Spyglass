package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToIntegerAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToInteger;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToIntegerAdapter extends TestDefaultAdapter<
		Integer,
		DefaultToInteger,
		DefaultToIntegerAdapter> {

	private Context context;

	private Integer expectedDefaultValue;

	private DefaultToIntegerAdapter adapter;

	private DefaultToInteger annotation;

	@Before
	public void setup() {
		context = mock(Context.class);
		expectedDefaultValue = 10284073;
		adapter = new DefaultToIntegerAdapter();

		annotation = mock(DefaultToInteger.class);
		when(annotation.value()).thenReturn(expectedDefaultValue);

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
	public DefaultToIntegerAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToInteger getAnnotation() {
		return annotation;
	}
}