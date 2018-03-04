package com.matthewtamlin.spyglass.integrationtests.reactivex.maybe;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import com.matthewtamlin.spyglass.integrationtests.R;
import com.matthewtamlin.spyglass.integrationtests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.markers.annotations.unconditionalhandlers.BooleanHandler;
import io.reactivex.Maybe;

public class WithUnconditionalHandlerAndNoDefault extends MaybeTestTargetBase {
  public WithUnconditionalHandlerAndNoDefault(final Context context) {
    super(context);
    init(null, 0, 0);
  }
  
  public WithUnconditionalHandlerAndNoDefault(final Context context, @Nullable final AttributeSet attrs) {
    super(context, attrs);
    init(attrs, 0, 0);
  }
  
  public WithUnconditionalHandlerAndNoDefault(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs, defStyleAttr, 0);
  }
  
  @RequiresApi(21)
  @TargetApi(21)
  public WithUnconditionalHandlerAndNoDefault(
      final Context context,
      @Nullable final AttributeSet attrs,
      final int defStyleAttr,
      final int defStyleRes) {
    
    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs, defStyleAttr, defStyleRes);
  }
  
  @BooleanHandler(attributeId = R.styleable.MaybeTestTargetBase_maybeAttr)
  public Maybe handlerMethod(final boolean value) {
    return Maybe.fromCallable(() -> {
      setReceivedValue(ReceivedValue.of(value));
      
      return 0;
    });
  }
  
  private void init(final AttributeSet attributeSet, final int defStyleAttr, final int defStyleRes) {
    WithUnconditionalHandlerAndNoDefault_SpyglassCompanion
        .builder()
        .withTarget(this)
        .withContext(getContext())
        .withStyleableResource(R.styleable.MaybeTestTargetBase)
        .withAttributeSet(attributeSet)
        .withDefaultStyleAttribute(defStyleAttr)
        .withDefaultStyleResource(defStyleRes)
        .build()
        .passDataToMethods()
        .blockingAwait();
  }
}