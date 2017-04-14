package com.matthewtamlin.spyglass.library_tests.util;

import com.matthewtamlin.spyglass.library.call_handler_annotations.FlagHandler;
import com.matthewtamlin.spyglass.library.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBooleanResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorStateListResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimension;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimensionResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDrawableResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFloat;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFractionResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToInteger;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToStringResource;
import com.matthewtamlin.spyglass.library.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.library.use_annotations.UseByte;
import com.matthewtamlin.spyglass.library.use_annotations.UseChar;
import com.matthewtamlin.spyglass.library.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.library.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.library.use_annotations.UseInt;
import com.matthewtamlin.spyglass.library.use_annotations.UseLong;
import com.matthewtamlin.spyglass.library.use_annotations.UseNull;
import com.matthewtamlin.spyglass.library.use_annotations.UseString;
import com.matthewtamlin.spyglass.library.util.SpyglassValidationException;
import com.matthewtamlin.spyglass.library.util.ValidationUtil;
import com.matthewtamlin.spyglass.library.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.library.value_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.library.value_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.library.value_handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.library.value_handler_annotations.IntegerHandler;
import com.matthewtamlin.spyglass.library.value_handler_annotations.StringHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.matthewtamlin.spyglass.library.core.DimensionUnit.DP;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JUnit4.class)
public class TestValidationUtil {
	private static final String FIELD_MESSAGE = "Test for field %1$s failed with reason %2$s.";

	private static final String METHOD_MESSAGE = "Test for method %1$s failed with reason %2$s.";

	@Test
	public void testValidateField_usingFieldsOfTestClass() {
		for (final Field f : TestClass.class.getDeclaredFields()) {
			final ValidationTestTarget annotation = f.getAnnotation(ValidationTestTarget.class);

			final boolean shouldPass = annotation.isValid();
			final boolean doesPass = passesValidation(f);

			final String message = String.format(FIELD_MESSAGE, f, annotation.failureMessage());
			assertThat(message, shouldPass, is(doesPass));
		}
	}

	@Test
	public void testValidateMethod_usingMethodsOfTestClass() {
		for (final Method m : TestClass.class.getDeclaredMethods()) {
			final ValidationTestTarget annotation = m.getAnnotation(ValidationTestTarget.class);

			final boolean shouldPass = annotation.isValid();
			final boolean doesPass = passesValidation(m);

			final String message = String.format(METHOD_MESSAGE, m, annotation.failureMessage());
			assertThat(message, shouldPass, is(doesPass));
		}
	}

	public boolean passesValidation(final Field field) {
		try {
			ValidationUtil.validateField(field);
			return true;
		} catch (final SpyglassValidationException e) {
			return false;
		}
	}

	public boolean passesValidation(final Method method) {
		try {
			ValidationUtil.validateMethod(method);
			return true;
		} catch (final SpyglassValidationException e) {
			return false;
		}
	}

	@SuppressWarnings("unused")
	public class TestClass {

	}

	private enum TestEnum {}

	@Target({METHOD, FIELD})
	@Retention(RUNTIME)
	public @interface ValidationTestTarget {
		boolean isValid();

		String failureMessage();
	}
}