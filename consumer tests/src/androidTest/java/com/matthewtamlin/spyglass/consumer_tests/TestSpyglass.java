package com.matthewtamlin.spyglass.consumer_tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.matthewtamlin.spyglass.consumer.InvalidBuilderStateException;
import com.matthewtamlin.spyglass.consumer.MissingCompanionClassException;
import com.matthewtamlin.spyglass.consumer.Spyglass;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class TestSpyglass {
	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiateUsingBuilder_noTargetEverSupplied() {
		Spyglass.builder()
				.withContext(mock(Context.class))
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiateUsingBuilder_nullTargetSupplied() {
		Spyglass.builder()
				.withTarget(null)
				.withContext(mock(Context.class))
				.withStyleableResource(new int[0])
				.build();
	}

	@Test
	public void testInstantiateUsingBuilder_targetWithCompanionSupplied() {
		final Context context = InstrumentationRegistry.getContext();
		final View targetView = new ViewWithCompanion(context);

		Spyglass.builder()
				.withTarget(targetView)
				.withContext(context)
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = MissingCompanionClassException.class)
	public void testInstantiateUsingBuilder_targetWithoutCompanionSupplied() {
		final Context context = InstrumentationRegistry.getContext();
		final View targetView = new ViewWithoutCompanion(context);

		Spyglass.builder()
				.withTarget(targetView)
				.withContext(context)
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiateUsingBuilder_noContextEverSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiateUsingBuilder_nullContextSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withContext(null)
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiateUsingBuilder_noStyleableResourceEverSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withContext(mock(Context.class))
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiateUsingBuilder_nullStyleableResourceSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withContext(mock(Context.class))
				.withStyleableResource(null)
				.build();
	}
}