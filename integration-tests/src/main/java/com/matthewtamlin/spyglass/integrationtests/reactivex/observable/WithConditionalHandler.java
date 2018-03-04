package com.matthewtamlin.spyglass.integrationtests.reactivex.observable;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import com.matthewtamlin.spyglass.integrationtests.R;
import com.matthewtamlin.spyglass.integrationtests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificBooleanHandler;
import com.matthewtamlin.spyglass.markers.annotations.placeholders.UseBoolean;
import io.reactivex.Observable;

public class WithConditionalHandler extends ObservableTestTargetBase {
  public WithConditionalHandler(final Context context) {
    super(context);
    init(null, 0, 0);
  }
  
  public WithConditionalHandler(final Context context, @Nullable final AttributeSet attrs) {
    super(context, attrs);
    init(attrs, 0, 0);
  }
  
  public WithConditionalHandler(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs, defStyleAttr, 0);
  }
  
  @RequiresApi(21)
  @TargetApi(21)
  public WithConditionalHandler(
      final Context context,
      @Nullable final AttributeSet attrs,
      final int defStyleAttr,
      final int defStyleRes) {
    
    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs, defStyleAttr, defStyleRes);
  }
  
  @SpecificBooleanHandler(attributeId = R.styleable.ObservableTestTargetBase_observableAttr, handledBoolean = true)
  public Observable handlerMethod(@UseBoolean(true) final boolean value) {
    return Observable.fromCallable(() -> {
      setReceivedValue(ReceivedValue.of(value));
      
      return 0;
    });
  }
  
  private void init(final AttributeSet attributeSet, final int defStyleAttr, final int defStyleRes) {
    WithConditionalHandler_SpyglassCompanion
        .builder()
        .withTarget(this)
        .withContext(getContext())
        .withStyleableResource(R.styleable.ObservableTestTargetBase)
        .withAttributeSet(attributeSet)
        .withDefaultStyleAttribute(defStyleAttr)
        .withDefaultStyleResource(defStyleRes)
        .build()
        .callTargetMethodsNow();
  }
}