package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.test.InstrumentationRegistry;
import android.support.v4.content.ContextCompat;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToColorStateListResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorStateListResource;

import org.junit.Before;

import static com.matthewtamlin.spyglass.library_tests.R.color.color_state_list;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestDefaultToColorStateListResourceAdapter extends TestDefaultAdapter<
		ColorStateList,
		DefaultToColorStateListResource,
		DefaultToColorStateListResourceAdapter> {

	private Context context;

	private ColorStateList expectedDefaultValue;

	private DefaultToColorStateListResourceAdapter adapter;

	private DefaultToColorStateListResource annotation;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
		expectedDefaultValue = ContextCompat.getColorStateList(context, color_state_list);
		adapter = new DefaultToColorStateListResourceAdapter();

		annotation = mock(DefaultToColorStateListResource.class);
		when(annotation.value()).thenReturn(color_state_list);
	}

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public ColorStateList getExpectedDefaultValue() {
		return expectedDefaultValue;
	}

	@Override
	public DefaultToColorStateListResourceAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToColorStateListResource getAnnotation() {
		return annotation;
	}
}