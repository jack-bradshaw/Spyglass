package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.AttributeSet;

import com.matthewtamlin.android_utilities.library.helpers.DimensionHelper;
import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.dimension_handler.DimensionHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integration_tests.dimension_handler.WithDefaultToDpDimension;
import com.matthewtamlin.spyglass.integration_tests.dimension_handler.WithDefaultToDpDimensionResource;
import com.matthewtamlin.spyglass.integration_tests.dimension_handler.WithDefaultToInDimension;
import com.matthewtamlin.spyglass.integration_tests.dimension_handler.WithDefaultToInDimensionResource;
import com.matthewtamlin.spyglass.integration_tests.dimension_handler.WithDefaultToMmDimension;
import com.matthewtamlin.spyglass.integration_tests.dimension_handler.WithDefaultToMmDimensionResource;
import com.matthewtamlin.spyglass.integration_tests.dimension_handler.WithDefaultToNull;
import com.matthewtamlin.spyglass.integration_tests.dimension_handler.WithDefaultToPtDimension;
import com.matthewtamlin.spyglass.integration_tests.dimension_handler.WithDefaultToPtDimensionResource;
import com.matthewtamlin.spyglass.integration_tests.dimension_handler.WithDefaultToPxDimension;
import com.matthewtamlin.spyglass.integration_tests.dimension_handler.WithDefaultToPxDimensionResource;
import com.matthewtamlin.spyglass.integration_tests.dimension_handler.WithDefaultToSpDimension;
import com.matthewtamlin.spyglass.integration_tests.dimension_handler.WithDefaultToSpDimensionResource;
import com.matthewtamlin.spyglass.integration_tests.dimension_handler.WithoutDefault;
import com.matthewtamlin.spyglass.integration_tests.framework.AttributeSetSupplier;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.matthewtamlin.spyglass.integration_tests.framework.AttributeSetSupplier.fromXml;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestDimensionHandler {
	@Rule
	public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

	private Context context;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributePresent_attributeEquals10dp() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_with_attr_equals_10dp);

		final DimensionHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		final int expectedValue = (int) DimensionHelper.dpToPx(context, 10);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributePresent_attributeEquals10in() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_with_attr_equals_10in);

		final DimensionHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		final int expectedValue = (int) DimensionHelper.inToPx(context, 10);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributePresent_attributeEquals10mm() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_with_attr_equals_10mm);

		final DimensionHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		final int expectedValue = (int) DimensionHelper.mmToPx(context, 10);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributePresent_attributeEquals10pt() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_with_attr_equals_10pt);

		final DimensionHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		final int expectedValue = (int) DimensionHelper.ptToPx(context, 10);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributePresent_attributeEquals10px() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_with_attr_equals_10px);

		final DimensionHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.of(10)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributePresent_attributeEquals10sp() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_with_attr_equals_10sp);

		final DimensionHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		final int expectedValue = (int) DimensionHelper.spToPx(context, 10);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassNeverCallsMethod_attributeMissing_noDefaultPresent() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_without_attr);

		final DimensionHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.<Integer>none()));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToDimensionPresent_usingDpUnits() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_without_attr);

		final DimensionHandlerTestTargetBase target = new WithDefaultToDpDimension(context, attrs);

		final int expectedValue = (int) DimensionHelper.dpToPx(context, WithDefaultToDpDimension.DEFAULT_VALUE_DP);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToDimensionPresent_usingInUnits() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_without_attr);

		final DimensionHandlerTestTargetBase target = new WithDefaultToInDimension(context, attrs);

		final int expectedValue = (int) DimensionHelper.inToPx(context, WithDefaultToInDimension.DEFAULT_VALUE_IN);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToDimensionPresent_usingMmUnits() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_without_attr);

		final DimensionHandlerTestTargetBase target = new WithDefaultToMmDimension(context, attrs);

		final int expectedValue = (int) DimensionHelper.mmToPx(context, WithDefaultToMmDimension.DEFAULT_VALUE_MM);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToDimensionPresent_usingPtUnits() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_without_attr);

		final DimensionHandlerTestTargetBase target = new WithDefaultToPtDimension(context, attrs);

		final int expectedValue = (int) DimensionHelper.ptToPx(context, WithDefaultToPtDimension.DEFAULT_VALUE_PT);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToDimensionPresent_usingPxUnits() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_without_attr);

		final DimensionHandlerTestTargetBase target = new WithDefaultToPxDimension(context, attrs);

		final int expectedValue = WithDefaultToPxDimension.DEFAULT_VALUE_PX;
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToDimensionPresent_usingSpUnits() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_without_attr);

		final DimensionHandlerTestTargetBase target = new WithDefaultToSpDimension(context, attrs);

		final int expectedValue = (int) DimensionHelper.spToPx(context, WithDefaultToSpDimension.DEFAULT_VALUE_SP);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToDimensionResourcePresent_usingDpUnits() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_without_attr);

		final DimensionHandlerTestTargetBase target = new WithDefaultToDpDimensionResource(context, attrs);

		final int expectedValue = (int) context.getResources().getDimension(R.dimen.DimensionForTestingDp);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToDimensionResourcePresent_usingInUnits() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_without_attr);

		final DimensionHandlerTestTargetBase target = new WithDefaultToInDimensionResource(context, attrs);

		final int expectedValue = (int) context.getResources().getDimension(R.dimen.DimensionForTestingIn);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToDimensionResourcePresent_usingMmUnits() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_without_attr);

		final DimensionHandlerTestTargetBase target = new WithDefaultToMmDimensionResource(context, attrs);

		final int expectedValue = (int) context.getResources().getDimension(R.dimen.DimensionForTestingMm);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToDimensionResourcePresent_usingPtUnits() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_without_attr);

		final DimensionHandlerTestTargetBase target = new WithDefaultToPtDimensionResource(context, attrs);

		final int expectedValue = (int) context.getResources().getDimension(R.dimen.DimensionForTestingPt);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToDimensionResourcePresent_usingPxUnits() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_without_attr);

		final DimensionHandlerTestTargetBase target = new WithDefaultToPxDimensionResource(context, attrs);

		final int expectedValue = (int) context.getResources().getDimension(R.dimen.DimensionForTestingPx);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToDimensionResourcePresent_usingSpUnits() {
		final AttributeSet attrs = fromXml(context, R.xml.dimension_handler_without_attr);

		final DimensionHandlerTestTargetBase target = new WithDefaultToSpDimensionResource(context, attrs);

		final int expectedValue = (int) context.getResources().getDimension(R.dimen.DimensionForTestingSp);
		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassCallsMethod_attributeMissing_defaultToNullPresent() {
		final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.dimension_handler_without_attr);

		final DimensionHandlerTestTargetBase target = new WithDefaultToNull(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.<Integer>of(null)));
	}
}