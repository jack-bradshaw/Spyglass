package com.matthewtamlin.spyglass.integrationtests.reactivex;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.AttributeSet;
import com.matthewtamlin.spyglass.integrationtests.R;
import com.matthewtamlin.spyglass.integrationtests.framework.AttributeSetSupplier;
import com.matthewtamlin.spyglass.integrationtests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.integrationtests.reactivex.maybe.WithConditionalHandler;
import com.matthewtamlin.spyglass.integrationtests.reactivex.maybe.WithUnconditionalHandlerAndDefault;
import com.matthewtamlin.spyglass.integrationtests.reactivex.maybe.WithUnconditionalHandlerAndNoDefault;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestMaybe {
  @Rule
  public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();
  
  private Context context;
  
  @Before
  public void setup() {
    context = InstrumentationRegistry.getTargetContext();
  }
  
  @Test
  public void testSubscriptionOccurs_conditionalHandler_attributeMissing() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.maybe_without_attr);
    
    final WithConditionalHandler target = new WithConditionalHandler(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.none()));
  }
  
  @Test
  public void testSubscriptionOccurs_conditionalHandler_attributePresent() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.maybe_with_attr_equals_true);
    
    final WithConditionalHandler target = new WithConditionalHandler(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(true)));
  }
  
  @Test
  public void testSubscriptionOccurs_unconditionalHandlerWithoutDefault_attributeMissing() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.maybe_without_attr);
    
    final WithUnconditionalHandlerAndNoDefault target = new WithUnconditionalHandlerAndNoDefault(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.none()));
  }
  
  @Test
  public void testSubscriptionOccurs_unconditionalHandlerWithoutDefault_attributePresent() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.maybe_with_attr_equals_true);
    
    final WithUnconditionalHandlerAndNoDefault target = new WithUnconditionalHandlerAndNoDefault(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(true)));
  }
  
  @Test
  public void testSubscriptionOccurs_unconditionalHandlerWithDefault_attributeMissing() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.maybe_without_attr);
    
    final WithUnconditionalHandlerAndDefault target = new WithUnconditionalHandlerAndDefault(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(false)));
  }
  
  @Test
  public void testSubscriptionOccurs_unconditionalHandlerWithDefault_attributePresent() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.maybe_with_attr_equals_true);
    
    final WithUnconditionalHandlerAndDefault target = new WithUnconditionalHandlerAndDefault(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(true)));
  }
}