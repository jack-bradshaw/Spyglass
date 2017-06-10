package com.matthewtamlin.spyglass.consumer;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import com.matthewtamlin.java_utilities.testing.Tested;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

/**
 * Translates view attributes into method calls via annotations. This class interacts with the UI, so all method
 * calls must be made on the main thread. This class uses the builder pattern for instantiation
 * (see {@link #builder()}).
 */
@Tested(testMethod = "automated")
public class Spyglass {
	/**
	 * The target to pass data to via reflective method calls.
	 */
	private final View target;


	/**
	 * A context which provides access to system resources.
	 */
	private final Context context;

	/**
	 * Supplies the data to pass to the target.
	 */
	private final TypedArray attrSource;

	/**
	 * Class containing the logic for invoking methods in the target class.
	 */
	private Class<?> companionClass;

	/**
	 * Constructs a new Spyglass from a builder.
	 *
	 * @param builder
	 * 		the builder to use as the base for the spyglass
	 */
	private Spyglass(final Builder builder) {
		this.target = builder.target;
		this.context = builder.context;

		this.attrSource = context.obtainStyledAttributes(
				builder.attributeSet,
				builder.styleableRes,
				builder.defStyleAttr,
				builder.defStyleRes);

		final String targetClassName = builder.targetClass.getCanonicalName();

		try {
			companionClass = Class.forName(targetClassName + "_SpyglassCompanion");

		} catch (final ClassNotFoundException e) {
			final String unformattedExceptionMessage =
					"No companion class was found for class \'%1$s\'.\n" +
							"Check the following:\n" +
							"- Does the class have any Spyglass handler annotations?\n" +
							"- Was the Spyglass annotation processor enabled at compile time?\n" +
							"- Were the generated files deleted manually or by a trimming tool?";

			throw new MissingCompanionClassException(String.format(unformattedExceptionMessage, targetClassName));
		}
	}

	/**
	 * Passes data to the target view using its method annotations. Methods are validated prior to use, to ensure
	 * that annotations have been applied correctly. This method will fail if called on a non-UI thread.
	 *
	 * @throws IllegalThreadException
	 * 		if this method is called on a non-UI thread
	 * @throws SpyglassInvocationException
	 * 		if a target method throws an exception when invoked, with the cause set to the thrown exception
	 */
	public void passDataToMethods() {
		if (Looper.myLooper() != Looper.getMainLooper()) {
			throw new IllegalThreadException("Spyglass methods must be called on the UI thread.");
		}

		try {
			final Method activateCallers = companionClass.getMethod(
					"activateCallers",
					target.getClass(),
					Context.class,
					TypedArray.class);

			activateCallers.invoke(null, target, context, attrSource);

		} catch (final NoSuchMethodException e) {
			throw new InvalidSpyglassCompanionException(
					"Spyglass found an invalid companion class. Were the generated files modified?", e);

		} catch (final InvocationTargetException e) {
			throw new SpyglassInvocationException(
					"Spyglass encountered an exception when invoking a method in a target class.", e);

		} catch (final IllegalAccessException e) {
			throw new RuntimeException("Spyglass cannot access a method in the target class.", e);
		}
	}

	/**
	 * @return a new spyglass builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builds new instances of the {@link Spyglass} tool. Attempting to call {@link #build()} without first setting
	 * the target, annotation source, context and styleable-resource properties will result in an exception being
	 * thrown. An exception will also be thrown if the target is not an instance of the annotation source class.
	 */
	public static class Builder {
		/**
		 * The target to pass data to. This property is mandatory and must be non-null prior to calling
		 * {@link #build()}.
		 */
		private View target;

		/**
		 * The class to look for annotations in. This property is mandatory and must be non-null prior to calling
		 * {@link #build()}. The target must be an instance of this class.
		 */
		private Class<? extends View> annotationSource;

		/**
		 * The context to use when accessing system resources. This property is mandatory and must be non-null prior
		 * to calling {@link #build()}.
		 */
		private Context context;

		/**
		 * The styleable resource to use when interpreting data. This property is mandatory and must be non-null
		 * prior to calling {@link #build()}.
		 */
		private int styleableRes[];

		/**
		 * The attribute set to source data from. This property is optional and does not need to be changed prior to
		 * calling {@link #build()}.
		 */
		private AttributeSet attributeSet;

		/**
		 * An attribute in the current theme which references the style to source defaults from. This property is
		 * optional and does not need to be changed prior to calling {@link #build()}.
		 */
		private int defStyleAttr;

		/**
		 * The resource ID of the style to source defaults from. This property is optional and does not need to be
		 * changed prior to calling {@link #build()}.
		 */
		private int defStyleRes;

		/**
		 * Constructs a new builder without setting any values. The new builder cannot be used to create a spyglass
		 * without setting the mandatory properties first.
		 */
		private Builder() {}

		/**
		 * Sets the target to pass data to. If this method is called more than once, only the most recent values are
		 * used. This method must be called with non-null values prior to calling {@link #build()}.
		 *
		 * @param view
		 * 		the target to pass data to
		 *
		 * @return this builder
		 */
		public <T extends View> Builder withTarget(final T view) {
			this.target = view;

			return this;
		}

		/**
		 * Sets the class to use as the annotation source. Do not pass the runtime class of the target to this method,
		 * instead pass a static class reference. This is important because the Spyglass framework uses this class to
		 * lookup compiled data at runtime. If this method is called more than once, only the most recent value is
		 * used. This method must be called with a non-null value prior to calling {@link #build()}, and the target
		 * supplied to {@link #withTarget(View)} must be an instance of the annotation source class.
		 *
		 * @param annotationSource
		 * 		the class containing the annotations to use
		 *
		 * @return this builder
		 */
		public Builder withAnnotationSource(final Class<? extends View> annotationSource) {
			this.annotationSource = annotationSource;
			return this;
		}

		/**
		 * Sets the context to use when accessing system resources. If this method is called more than once, only the
		 * most recent value is used. This method must be called with a non-null value prior to calling {@link
		 * #build()}.
		 *
		 * @param context
		 * 		the context to use when accessing system resources
		 *
		 * @return this builder
		 */
		public Builder withContext(final Context context) {
			this.context = context;
			return this;
		}

		/**
		 * Sets the styleable resource to use when interpreting attribute data. The behaviour of the spyglass is
		 * undefined if the styleable resource is not applicable to the target view. If this method is called more than
		 * once, only the most recent value is used. This method must be called with a non-null value prior to calling
		 * {@link #build()}.
		 *
		 * @param styleableRes
		 * 		the styleable resource to use when interpreting attribute data
		 *
		 * @return this builder
		 */
		public Builder withStyleableResource(final int[] styleableRes) {
			this.styleableRes = styleableRes;
			return this;
		}

		/**
		 * Sets the attribute set to source data from. If this method is called more than once, only the most recent
		 * value is used. An attribute set is not mandatory, and {@link #build()} can safely be called without ever
		 * calling this method.
		 *
		 * @param attributeSet
		 * 		the attribute set to source data from, may be null
		 *
		 * @return this builder
		 */
		public Builder withAttributeSet(final AttributeSet attributeSet) {
			this.attributeSet = attributeSet;
			return this;
		}

		/**
		 * Sets the style to source defaults from if the attribute set is missing data. The data in the attribute set
		 * takes precedence, so the data in the default style is only used if the attribute set is missing data for a
		 * particular attribute. If this method is called more than once, only the most recent value is used. This value
		 * is not mandatory, and {@link #build()} can safely be called without ever calling this method.
		 *
		 * @param defStyleAttr
		 * 		an attribute in the current theme which references the default style, 0 to use no default style
		 *
		 * @return this builder
		 */
		public Builder withDefStyleAttr(final int defStyleAttr) {
			this.defStyleAttr = defStyleAttr;
			return this;
		}

		/**
		 * Sets the style to source defaults from if the attribute set is missing data. The data in the attribute set
		 * takes precedence, so the data in the default style is only used if the attribute set is missing data for a
		 * particular attribute. If this method is called more than once, only the most recent value is used. This
		 * value is not mandatory, and {@link #build()} can safely be called without ever calling this method.
		 *
		 * @param defStyleRes
		 * 		the resource ID of the default style, 0 to use no default style
		 *
		 * @return this builder
		 */
		public Builder withDefStyleRes(final int defStyleRes) {
			this.defStyleRes = defStyleRes;
			return this;
		}

		/**
		 * Constructs a new spyglass using this builder. Attempting to call this method without first setting the
		 * target, the context and the styleable resource will result in an exception being thrown.
		 *
		 * @return the new spyglass
		 *
		 * @throws InvalidBuilderStateException
		 * 		if no target has been set
		 * @throws InvalidBuilderStateException
		 * 		if no annotation source has been set
		 * @throws InvalidBuilderStateException
		 * 		if no context has been set
		 * @throws InvalidBuilderStateException
		 * 		if no styleable resource has been set
		 * @throws InvalidBuilderStateException
		 * 		if the target is not an instance of the annotation source
		 */
		public Spyglass build() {
			checkNotNull(target, new InvalidBuilderStateException("Unable to build a Spyglass without a target. Call " +
					"method withTarget(View) before calling build()."));

			checkNotNull(annotationSource, new InvalidBuilderStateException("Unable to build a Spyglass without an " +
					"annotation source. Use method withAnnotationSource(Class) before calling build()."));

			checkNotNull(context, new InvalidBuilderStateException("Unable to build a Spyglass without a context. " +
					"Call method withContext(Context) before calling build()."));

			checkNotNull(styleableRes, new InvalidBuilderStateException("Unable to build a Spyglass without a " +
					"styleable resource. Call method withStyleableRes(int[]) before calling build()."));

			if (!annotationSource.isAssignableFrom(target.getClass())) {
				throw new InvalidBuilderStateException(
						"Unable to build a Spyglass. The target must be an instance of the annotation source.");
			}

			return new Spyglass(this);
		}
	}
}