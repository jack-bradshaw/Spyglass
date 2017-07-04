package com.matthewtamlin.spyglass.consumer_tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.matthewtamlin.spyglass.consumer.IllegalThreadException;
import com.matthewtamlin.spyglass.consumer.InvalidBuilderStateException;
import com.matthewtamlin.spyglass.consumer.InvalidSpyglassCompanionException;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.consumer.SpyglassInvocationException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class TestSpyglass {
	@Rule
	public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

	private Context context;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
	}

	@Test(expected = InvalidBuilderStateException.class)
	@UiThreadTest
	public void testInstantiateUsingBuilder_noTargetEverSupplied() {
		Spyglass.builder()
				.withAnnotationSource(View.class)
				.withContext(context)
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	@UiThreadTest
	public void testInstantiateUsingBuilder_nullTargetSupplied() {
		Spyglass.builder()
				.withTarget(null)
				.withAnnotationSource(View.class)
				.withContext(context)
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	@UiThreadTest
	public void testInstantiateUsingBuilder_targetHasNoCompanionClass() {
		final View targetView = new ViewWithoutCompanion(context);

		Spyglass.builder()
				.withTarget(targetView)
				.withAnnotationSource(ViewWithoutCompanion.class)
				.withContext(context)
				.withStyleableResource(new int[0])
				.build();
	}

	@Test
	@UiThreadTest
	public void testInstantiateUsingBuilder_targetHasNormalCompanionClass() {
		final View targetView = new ViewWithNormalCompanion(context);

		Spyglass.builder()
				.withTarget(targetView)
				.withAnnotationSource(ViewWithNormalCompanion.class)
				.withContext(context)
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	@UiThreadTest
	public void testInstantiateUsingBuilder_noContextEverSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withAnnotationSource(View.class)
				.withAnnotationSource(View.class)
				.withStyleableResource(new int[0])
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	@UiThreadTest
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
	@UiThreadTest
	public void testInstantiateUsingBuilder_noStyleableResourceEverSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withAnnotationSource(View.class)
				.withAnnotationSource(View.class)
				.withContext(context)
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	@UiThreadTest
	public void testInstantiateUsingBuilder_nullStyleableResourceSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withAnnotationSource(View.class)
				.withContext(context)
				.withStyleableResource(null)
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	@UiThreadTest
	public void testInstantiateUsingBuilder_noAnnotationSourceEverSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withContext(context)
				.withStyleableResource(new int[]{})
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	@UiThreadTest
	public void testInstantiateUsingBuilder_nullAnnotationSourceSupplied() {
		Spyglass.builder()
				.withTarget(mock(View.class))
				.withAnnotationSource(null)
				.withContext(context)
				.withStyleableResource(new int[]{})
				.build();
	}

	@Test(expected = InvalidBuilderStateException.class)
	@UiThreadTest
	public void testInstantiateUsingBuilder_annotationSourceIsNotSuperclassOfTarget() {
		final View targetView = new ViewWithNormalCompanion(context);

		Spyglass.builder()
				.withTarget(targetView)
				.withAnnotationSource(ViewWithoutCompanion.class)
				.withContext(context)
				.withStyleableResource(new int[]{})
				.build();
	}

	@Test
	@UiThreadTest
	public void testInstantiateUsingBuilder_annotationSourceIsSuperclassOfTarget() {
		final View targetView = new SubclassOfViewWithNormalCompanion(context);

		Spyglass.builder()
				.withTarget(targetView)
				.withAnnotationSource(ViewWithNormalCompanion.class)
				.withContext(context)
				.withStyleableResource(new int[]{})
				.build();
	}

	@Test
	@UiThreadTest
	public void testInstantiateUsingBuilder_annotationSourceIsExactClassOfTarget() {
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
	@UiThreadTest
	public void testPassDataToMethods_usingViewWithIncompleteCompanion() {
		final View targetView = new ViewWithIncompleteCompanion(context);

		final Spyglass spyglass = Spyglass
				.builder()
				.withTarget(targetView)
				.withAnnotationSource(ViewWithIncompleteCompanion.class)
				.withContext(context)
				.withStyleableResource(new int[0])
				.build();

		spyglass.passDataToMethods();
	}

	@Test(expected = SpyglassInvocationException.class)
	@UiThreadTest
	public void testPassDataToMethods_usingViewWithExceptionThrowingCompanion_notInternalException() {
		final View targetView = new ViewWithExceptionThrowingCompanion(context);

		final Spyglass spyglass = Spyglass
				.builder()
				.withTarget(targetView)
				.withAnnotationSource(ViewWithExceptionThrowingCompanion.class)
				.withContext(context)
				.withStyleableResource(new int[0])
				.build();

		spyglass.passDataToMethods();
	}

	@Test
	@UiThreadTest
	public void testPassDataToMethods_usingViewWithNormalCompanion() throws NoSuchMethodException {
		final View targetView = new ViewWithNormalCompanion(context);

		final Spyglass spyglass = Spyglass
				.builder()
				.withTarget(targetView)
				.withAnnotationSource(ViewWithNormalCompanion.class)
				.withContext(context)
				.withStyleableResource(new int[0])
				.build();

		spyglass.passDataToMethods();

		assertThat(ViewWithNormalCompanion_SpyglassCompanion.activateCallersWasInvoked(), is(true));
	}
}