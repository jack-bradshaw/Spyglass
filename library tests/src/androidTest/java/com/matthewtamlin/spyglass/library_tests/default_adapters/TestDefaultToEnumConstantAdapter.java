package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToEnumConstantAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToEnumConstant;

import org.junit.Before;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getContext;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestDefaultToEnumConstantAdapter {
	private Context context;

	private TestEnum expectedDefaultValue;

	private DefaultToEnumConstantAdapter adapter;

	private DefaultToEnumConstant annotation;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
		expectedDefaultValue = TestEnum.ITEM_2;
		adapter = new DefaultToEnumConstantAdapter();

		annotation = mock(DefaultToEnumConstant.class);
		doReturn(TestEnum.class).when(annotation).enumClass();
		when(annotation.ordinal()).thenReturn(1);
	}

	@Test
	public void testReflectiveInstantiation() throws Exception {
		adapter.getClass().newInstance();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefault_nullAnnotation() {
		adapter.getDefault(null, getContext());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefault_nullContext() {
		adapter.getDefault(annotation, null);
	}

	@Test
	public void testGetDefault_validArguments() {
		final Object returnedDefault = adapter.getDefault(annotation, getContext());

		assertThat(returnedDefault, is((Object) expectedDefaultValue));
	}

	public enum TestEnum {
		ITEM_1,
		ITEM_2,
		ITEM_3,
		ITEM_4
	}
}