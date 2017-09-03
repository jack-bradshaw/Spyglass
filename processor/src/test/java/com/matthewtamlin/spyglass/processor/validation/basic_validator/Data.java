package com.matthewtamlin.spyglass.processor.validation.basic_validator;

import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToStringResource;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseChar;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseString;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.StringHandler;

abstract class Data {
	@Target(isValid = true)
	public void method1() {}

	@Target(isValid = true)
	@StringHandler(attributeId = 1)
	protected void method2(Object o) {}

	@Target(isValid = false)
	@StringHandler(attributeId = 1)
	@BooleanHandler(attributeId = 1)
	void method3(Object o) {}

	@Target(isValid = false)
	@StringHandler(attributeId = 1)
	@BooleanHandler(attributeId = 1)
	@FloatHandler(attributeId = 1)
	void method4(Object o) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	void method5() {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	@SpecificEnumHandler(attributeId = 1, ordinal = 1)
	void method6() {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	@StringHandler(attributeId = 1)
	void method7() {}

	@Target(isValid = true)
	@BooleanHandler(attributeId = 1)
	@DefaultToBoolean(false)
	void method8(Object o) {}

	@Target(isValid = false)
	@StringHandler(attributeId = 1)
	@DefaultToString("something")
	@DefaultToStringResource(resId = 1)
	void method9(Object o) {}

	@Target(isValid = false)
	@StringHandler(attributeId = 1)
	@DefaultToString("something")
	@DefaultToStringResource(resId = 1)
	@DefaultToBoolean(false)
	void method10(Object o) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	@DefaultToBoolean(false)
	void method11() {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	@DefaultToString("something")
	@DefaultToStringResource(resId = 1)
	void method12() {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	@DefaultToString("something")
	@DefaultToStringResource(resId = 1)
	@DefaultToBoolean(false)
	void method13() {}

	@Target(isValid = false)
	@DefaultToBoolean(true)
	void method14() {}

	@Target(isValid = false)
	@DefaultToString("something")
	@DefaultToNull
	void method15() {}

	@Target(isValid = false)
	@DefaultToString("something")
	@DefaultToNull
	@DefaultToEnumConstant(enumClass = Enum.class, ordinal = 1)
	void method16() {}

	@Target(isValid = false)
	@DimensionHandler(attributeId = 1)
	void method17() {}

	@Target(isValid = true)
	@FloatHandler(attributeId = 1)
	void method18(Object o) {}

	@Target(isValid = false)
	@BooleanHandler(attributeId = 1)
	void method19(@UseBoolean(false) Object o) {}

	@Target(isValid = false)
	@BooleanHandler(attributeId = 1)
	void method20(Object o1, Object o2) {}

	@Target(isValid = true)
	@BooleanHandler(attributeId = 1)
	void method21(@UseByte(1) Object o1, Object o2) {}

	@Target(isValid = false)
	@BooleanHandler(attributeId = 1)
	void method22(@UseByte(1) Object o1, @UseLong(1) Object o2) {}

	@Target(isValid = false)
	@BooleanHandler(attributeId = 1)
	void method23(Object o1, @UseDouble(1) Object o2, Object o3) {}

	@Target(isValid = true)
	@BooleanHandler(attributeId = 1)
	void method24(Object o1, @UseFloat(1F) Object o2, @UseChar('A') Object o3) {}

	@Target(isValid = false)
	@BooleanHandler(attributeId = 1)
	void method25(@UseNull Object o1, @UseChar('A') Object o2, @UseInt(1) Object o3) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	void method26() {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	void method27(Object o) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	void method28(@UseBoolean(false) Object o) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	void method29(Object o1, Object o2) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	void method30(@UseByte(1) Object o1, Object o2) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	void method31(@UseByte(1) Object o1, @UseLong(1) Object o2) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	void method32(Object o1, @UseDouble(2.0) Object o2, Object o3) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	void method33(Object o1, @UseFloat(2F) Object o2, @UseChar('A') Object o3) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	void method34(@UseNull Object o1, @UseChar('A') Object o2, @UseInt(1) Object o3) {}

	@Target(isValid = false)
	@BooleanHandler(attributeId = 1)
	void method35(@UseInt(1) @UseBoolean(true) Object o1, Object o2) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	void method36(@UseInt(1) @UseBoolean(true) @UseString("something") Object o1) {}

	@Target(isValid = false)
	@BooleanHandler(attributeId = 1)
	private void method37(Object o1) {}

	@Target(isValid = true)
	@StringHandler(attributeId = 1)
	abstract void method38(Object o2);

	public class NonStaticInnerClass {
		@Target(isValid = false)
		@BooleanHandler(attributeId = 1)
		public void method39(Object o1) {}
	}

	public static class StaticInnerClass {
		@Target(isValid = true)
		@BooleanHandler(attributeId = 1)
		public void method41(Object o1) {}

		public class NonStaticClassWithinStaticClass {
			@Target(isValid = false)
			@BooleanHandler(attributeId = 1)
			public void method43(Object o1) {}

			public void methodContainingAnonymousClass() {
				new Object() {
					@Target(isValid = false)
					@StringHandler(attributeId = 1)
					public void method45(Object o1) {}
				};
			}

			public void methodContainingLocalClass() {
				class LocalClass {
					@Target(isValid = false)
					@StringHandler(attributeId = 1)
					public void method46(Object o1) {}
				}
			}
		}

		public static class StaticClassWithinStaticClass {
			@Target(isValid = true)
			@BooleanHandler(attributeId = 1)
			public void method47(Object o1) {}
		}
	}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method48(@UseBoolean(true) boolean b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method49(@UseBoolean(true) Boolean b) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method50(@UseBoolean(true) byte b) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method51(@UseBoolean(true) Byte b) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method52(@UseBoolean(true) char c) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method53(@UseBoolean(true) Character c) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method54(@UseBoolean(true) double d) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method55(@UseBoolean(true) Double d) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method56(@UseBoolean(true) float f) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method57(@UseBoolean(true) Float f) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method58(@UseBoolean(true) int i) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method59(@UseBoolean(true) Integer i) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method60(@UseBoolean(true) long l) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method61(@UseBoolean(true) Long l) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method62(@UseBoolean(true) Number n) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method63(@UseBoolean(true) Object o) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method64(@UseBoolean(true) short s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method65(@UseBoolean(true) Short s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method66(@UseBoolean(true) String s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method67(@UseByte(0) boolean b) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method68(@UseByte(0) Boolean b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method69(@UseByte(0) byte b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method70(@UseByte(0) Byte b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method71(@UseByte(0) char c) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method72(@UseByte(0) Character c) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method73(@UseByte(0) double d) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method74(@UseByte(0) Double d) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method75(@UseByte(0) float f) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method76(@UseByte(0) Float f) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method77(@UseByte(0) int i) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method78(@UseByte(0) Integer i) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method79(@UseByte(0) long l) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method80(@UseByte(0) Long l) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method81(@UseByte(0) Number n) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method82(@UseByte(0) Object o) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method83(@UseByte(0) short s) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method84(@UseByte(0) Short s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method85(@UseByte(0) String s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method86(@UseChar(0) boolean b) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method87(@UseChar(0) Boolean b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method88(@UseChar(0) byte b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method89(@UseChar(0) Byte b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method90(@UseChar(0) char c) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method91(@UseChar(0) Character c) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method92(@UseChar(0) double d) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method93(@UseChar(0) Double d) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method94(@UseChar(0) float f) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method95(@UseChar(0) Float f) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method96(@UseChar(0) int i) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method97(@UseChar(0) Integer i) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method98(@UseChar(0) long l) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method99(@UseChar(0) Long l) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method100(@UseChar(0) Number n) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method101(@UseChar(0) Object o) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method102(@UseChar(0) short s) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method103(@UseChar(0) Short s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method104(@UseChar(0) String s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method105(@UseDouble(0) boolean b) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method106(@UseDouble(0) Boolean b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method107(@UseDouble(0) byte b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method108(@UseDouble(0) Byte b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method109(@UseDouble(0) char c) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method110(@UseDouble(0) Character c) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method111(@UseDouble(0) double d) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method112(@UseDouble(0) Double d) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method113(@UseDouble(0) float f) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method114(@UseDouble(0) Float f) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method115(@UseDouble(0) int i) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method116(@UseDouble(0) Integer i) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method117(@UseDouble(0) long l) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method118(@UseDouble(0) Long l) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method119(@UseDouble(0) Number n) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method120(@UseDouble(0) Object o) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method121(@UseDouble(0) short s) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method122(@UseDouble(0) Short s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method123(@UseDouble(0) String s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method124(@UseFloat(0) boolean b) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method125(@UseFloat(0) Boolean b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method126(@UseFloat(0) byte b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method127(@UseFloat(0) Byte b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method128(@UseFloat(0) char c) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method129(@UseFloat(0) Character c) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method130(@UseFloat(0) double d) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method131(@UseFloat(0) Double d) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method132(@UseFloat(0) float f) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method133(@UseFloat(0) Float f) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method134(@UseFloat(0) int i) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method135(@UseFloat(0) Integer i) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method136(@UseFloat(0) long l) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method137(@UseFloat(0) Long l) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method138(@UseFloat(0) Number n) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method139(@UseFloat(0) Object o) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method140(@UseFloat(0) short s) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method141(@UseFloat(0) Short s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method142(@UseFloat(0) String s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method143(@UseInt(0) boolean b) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method144(@UseInt(0) Boolean b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method145(@UseInt(0) byte b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method146(@UseInt(0) Byte b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method147(@UseInt(0) char c) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method148(@UseInt(0) Character c) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method149(@UseInt(0) double d) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method150(@UseInt(0) Double d) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method151(@UseInt(0) float f) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method152(@UseInt(0) Float f) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method153(@UseInt(0) int i) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method154(@UseInt(0) Integer i) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method155(@UseInt(0) long l) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method156(@UseInt(0) Long l) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method157(@UseInt(0) Number n) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method158(@UseInt(0) Object o) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method159(@UseInt(0) short s) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method160(@UseInt(0) Short s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method161(@UseInt(0) String s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method162(@UseLong(0) boolean b) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method163(@UseLong(0) Boolean b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method164(@UseLong(0) byte b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method165(@UseLong(0) Byte b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method166(@UseLong(0) char c) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method167(@UseLong(0) Character c) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method168(@UseLong(0) double d) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method169(@UseLong(0) Double d) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method170(@UseLong(0) float f) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method171(@UseLong(0) Float f) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method172(@UseLong(0) int i) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method173(@UseLong(0) Integer i) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method174(@UseLong(0) long l) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method175(@UseLong(0) Long l) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method176(@UseLong(0) Number n) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method177(@UseLong(0) Object o) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method178(@UseLong(0) short s) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method179(@UseLong(0) Short s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method180(@UseLong(0) String s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method181(@UseNull boolean b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method182(@UseNull Boolean b) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method183(@UseNull byte b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method184(@UseNull Byte b) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method185(@UseNull char c) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method186(@UseNull Character c) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method187(@UseNull double d) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method188(@UseNull Double d) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method189(@UseNull float f) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method190(@UseNull Float f) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method191(@UseNull int i) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method192(@UseNull Integer i) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method193(@UseNull long l) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method194(@UseNull Long l) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method195(@UseNull Number n) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method196(@UseNull Object o) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method197(@UseNull short s) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method198(@UseNull Short s) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method199(@UseNull String s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method200(@UseShort(0) boolean b) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method201(@UseShort(0) Boolean b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method202(@UseShort(0) byte b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method203(@UseShort(0) Byte b) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method204(@UseShort(0) char c) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method206(@UseShort(0) Character c) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method207(@UseShort(0) double d) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method208(@UseShort(0) Double d) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method209(@UseShort(0) float f) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method210(@UseShort(0) Float f) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method211(@UseShort(0) int i) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method212(@UseShort(0) Integer i) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method213(@UseShort(0) long l) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method214(@UseShort(0) Long l) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method215(@UseShort(0) Number n) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method216(@UseShort(0) Object o) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method217(@UseShort(0) short s) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method218(@UseShort(0) Short s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method219(@UseShort(0) String s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method220(@UseString("") boolean b) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method221(@UseString("") Boolean b) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method222(@UseString("") byte b) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method223(@UseString("") Byte b) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method224(@UseString("") char c) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method225(@UseString("") Character c) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method226(@UseString("") double d) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method227(@UseString("") Double d) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method228(@UseString("") float f) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method229(@UseString("") Float f) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method230(@UseString("") int i) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method231(@UseString("") Integer i) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method232(@UseString("") long l) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method233(@UseString("") Long l) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method234(@UseString("") Number n) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method235(@UseString("") Object o) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method236(@UseString("") short s) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method237(@UseString("") Short s) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	public void method238(@UseString("") String s) {}
}