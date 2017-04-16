package com.matthewtamlin.spyglass.library_tests.core;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.matthewtamlin.spyglass.library.core.IllegalThreadException;
import com.matthewtamlin.spyglass.library.core.InvalidBuilderStateException;
import com.matthewtamlin.spyglass.library.core.MandatoryAttributeMissingException;
import com.matthewtamlin.spyglass.library.core.Spyglass;
import com.matthewtamlin.spyglass.library.core.SpyglassFieldBindException;
import com.matthewtamlin.spyglass.library.core.SpyglassMethodCallException;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static android.support.test.InstrumentationRegistry.getContext;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class TestSpyglass {
	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiationViaBuilder_noViewEverSupplied() {
		Spyglass.builder()
				.withContext(mock(Context.class))
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiationViaBuilder_nullViewSupplied() {
		Spyglass.builder()
				.forView(null)
				.withContext(mock(Context.class))
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiationViaBuilder_noContextEverSupplied() {
		Spyglass.builder()
				.forView(mock(View.class))
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiationViaBuilder_nullContextSupplied() {
		Spyglass.builder()
				.forView(mock(View.class))
				.withContext(null)
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiationViaBuilder_noStyleableResourceEverSupplied() {
		Spyglass.builder()
				.forView(mock(View.class))
				.withContext(mock(Context.class))
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiationViaBuilder_nullStyleableResourceSupplied() {
		Spyglass.builder()
				.forView(mock(View.class))
				.withContext(mock(Context.class))
				.withStyleableResource(null)
				.build();
	}

	@Test
	public void testInstantiationViaBuilder_allMandatoryValuesSupplied() {
		Spyglass.builder()
				.forView(mock(View.class))
				.withContext(getContext())
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = IllegalThreadException.class)
	public void testBindDataToFields_calledOnNonUiThread() {
		final Spyglass spyglass = Spyglass.builder()
				.forView(mock(View.class))
				.withContext(getContext())
				.withStyleableResource(new int[0])
				.build();

		final Future<Void> f = Executors.newSingleThreadExecutor().submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				// This should fail
				spyglass.bindDataToFields();
				return null;
			}
		});

		try {
			// Test must not complete before the background task is done
			f.get();

		} catch (final InterruptedException e) {
			// This is not expected
			throw new RuntimeException("Test failed. Background task interrupted.");

		} catch (final ExecutionException e) {
			// This is expected
			throw (IllegalThreadException) e.getCause();
		}
	}

	@Test
	public void testBindDataToFields_noHandlerAnnotations() {

	}

	@Test
	public void testBindDataToFields_attrSupplied() {

	}

	@Test
	public void testBindDataToFields_attrMissing_noDefault_notMandatory() {

	}

	@Test(expected = MandatoryAttributeMissingException.class)
	public void testBindDataToFields_attrMissing_noDefault_isMandatory() {

	}

	@Test
	public void testBindDataToFields_attrMissing_hasDefault_notMandatory() {

	}

	@Test
	public void testBindDataToFields_attrMissing_hasDefault_isMandatory() {

	}

	@Test(expected = SpyglassFieldBindException.class)
	public void testBindDataToFields_dataTypeMismatch() {

	}

	@Test(expected = IllegalThreadException.class)
	public void testPassDataToMethods_calledOnNonUiThread() {
		final Spyglass spyglass = Spyglass.builder()
				.forView(mock(View.class))
				.withContext(getContext())
				.withStyleableResource(new int[0])
				.build();

		final Future<Void> f = Executors.newSingleThreadExecutor().submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				// This should fail
				spyglass.passDataToMethods();
				return null;
			}
		});

		try {
			// Test must not complete before the background task is done
			f.get();

		} catch (final InterruptedException e) {
			// This is not expected
			throw new RuntimeException("Test failed. Background task interrupted.");

		} catch (final ExecutionException e) {
			// This is expected
			throw (IllegalThreadException) e.getCause();
		}
	}

	@Test
	public void testPassDataToMethods_noHandlerAnnotations() {

	}

	@Test
	public void testPassDataToMethods_attrSupplied() {

	}

	@Test
	public void testPassDataToMethods_attrMissing_noDefault_notMandatory() {

	}

	@Test(expected = MandatoryAttributeMissingException.class)
	public void testPassDataToMethods_attrMissing_noDefault_isMandatory() {

	}

	@Test
	public void testPassDataToMethods_attrMissing_hasDefault_notMandatory() {

	}

	@Test
	public void testPassDataToMethods_attrMissing_hasDefault_isMandatory() {

	}

	@Test(expected = SpyglassMethodCallException.class)
	public void testPassDataToMethods_dataTypeMismatch() {

	}

}