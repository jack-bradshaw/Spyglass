package com.matthewtamlin.spyglass.consumer_tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.matthewtamlin.spyglass.consumer.IllegalThreadException;
import com.matthewtamlin.spyglass.consumer.InvalidBuilderStateException;
import com.matthewtamlin.spyglass.consumer.InvalidSpyglassCompanionException;
import com.matthewtamlin.spyglass.consumer.MissingCompanionClassException;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.consumer.SpyglassInvocationException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class TestSpyglass {
	@Rule
	public final ActivityTestRule<EmptyActivity> activityRule = new ActivityTestRule<>(EmptyActivity.class);

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiateUsingBuilder_noTargetEverSupplied() {
		Spyglass.builder()
				.withAnnotationSource(View.class)
				.withContext(mock(Context.class))
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiateUsingBuilder_nullTargetSupplied() {
		Spyglass.builder()
				.withTarget(null)
				.withAnnotationSource(View.class)
				.withContext(mock(Context.class))
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiateUsingBuilder_noContextEverSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withAnnotationSource(View.class)
				.withAnnotationSource(View.class)
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiateUsingBuilder_nullContextSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withAnnotationSource(View.class)
				.withAnnotationSource(View.class)
				.withContext(null)
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiateUsingBuilder_noStyleableResourceEverSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withAnnotationSource(View.class)
				.withAnnotationSource(View.class)
				.withContext(mock(Context.class))
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiateUsingBuilder_nullStyleableResourceSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withAnnotationSource(View.class)
				.withContext(mock(Context.class))
				.withStyleableResource(null)
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiateUsingBuilder_noAnnotationSourceEverSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withContext(mock(Context.class))
				.withStyleableResource(new int[]{})
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiateUsingBuilder_nullAnnotationSourceSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withAnnotationSource(null)
				.withContext(mock(Context.class))
				.withStyleableResource(new int[]{})
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	public void testInstantiateUsingBuilder_annotationSourceIsNotSuperclassOfTarget() {
		final Context context = InstrumentationRegistry.getContext();
		final View targetView = new ViewWithNormalCompanion(context);

		Spyglass.builder()
				.withTarget(targetView)
				.withAnnotationSource(ViewWithoutCompanion.class)
				.withContext(context)
				.withStyleableResource(new int[]{})
				.build();
	}

	@Test
	public void testInstantiateUsingBuilder_annotationSourceIsSuperclassOfTarget() {
		final Context context = InstrumentationRegistry.getContext();
		final View targetView = new ViewWithNormalCompanion(context);

		Spyglass.builder()
				.withTarget(targetView)
				.withAnnotationSource(View.class)
				.withContext(context)
				.withStyleableResource(new int[]{})
				.build();
	}

	@Test
	public void testInstantiateUsingBuilder_annotationSourceIsExactClassOfTarget() {
		final Context context = InstrumentationRegistry.getContext();
		final View targetView = new ViewWithNormalCompanion(context);

		Spyglass.builder()
				.withTarget(targetView)
				.withAnnotationSource(ViewWithNormalCompanion.class)
				.withContext(context)
				.withStyleableResource(new int[]{})
				.build();
	}

	@Test(expected = IllegalThreadException.class)
	public void testPassDataToMethods_calledOnNonUiThread() {
		final Context context = InstrumentationRegistry.getContext();
		final View targetView = new ViewWithNormalCompanion(context);

		final Spyglass spyglass = Spyglass
				.builder()
				.withTarget(targetView)
				.withAnnotationSource(ViewWithNormalCompanion.class)
				.withContext(context)
				.withStyleableResource(new int[0])
				.build();

		spyglass.passDataToMethods();
	}

	@Test(expected = InvalidSpyglassCompanionException.class)
	public void testPassDataToMethods_usingViewWithIncompleteCompanion() {
		final Context context = InstrumentationRegistry.getContext();
		final View targetView = new ViewWithIncompleteCompanion(context);

		final Spyglass spyglass = Spyglass
				.builder()
				.withTarget(targetView)
				.withAnnotationSource(ViewWithIncompleteCompanion.class)
				.withContext(context)
				.withStyleableResource(new int[0])
				.build();

		callPassDataToMethodsSynchronously(spyglass);
	}

	@Test(expected = SpyglassInvocationException.class)
	public void testPassDataToMethods_usingViewWithExceptionThrowingCompanion() {
		final Context context = InstrumentationRegistry.getContext();
		final View targetView = new ViewWithExceptionThrowingCompanion(context);

		final Spyglass spyglass = Spyglass
				.builder()
				.withTarget(targetView)
				.withAnnotationSource(ViewWithExceptionThrowingCompanion.class)
				.withContext(context)
				.withStyleableResource(new int[0])
				.build();

		callPassDataToMethodsSynchronously(spyglass);
	}

	@Test
	public void testPassDataToMethods_usingViewWithNormalCompanion() throws NoSuchMethodException {
		final Context context = InstrumentationRegistry.getContext();
		final View targetView = new ViewWithNormalCompanion(context);

		final Spyglass spyglass = Spyglass
				.builder()
				.withTarget(targetView)
				.withAnnotationSource(ViewWithNormalCompanion.class)
				.withContext(context)
				.withStyleableResource(new int[0])
				.build();

		callPassDataToMethodsSynchronously(spyglass);

		assertThat(ViewWithNormalCompanion_SpyglassCompanion.activateCallersWasInvoked(), is(true));
	}

	private void callPassDataToMethodsSynchronously(final Spyglass spyglass) {
		final RuntimeException[] exceptionHolder = new RuntimeException[1];

		activityRule.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					spyglass.passDataToMethods();
				} catch (final RuntimeException e) {
					exceptionHolder[0] = e;
				}
			}
		});

		getInstrumentation().waitForIdleSync();

		if (exceptionHolder[0] != null) {
			throw exceptionHolder[0];
		}
	}
}