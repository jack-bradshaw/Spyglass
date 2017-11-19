# Spyglass
Spyglass is an Android library that makes attribute handling in custom views much simpler. The framework is based on one central assertion: Every custom annotation that can be used to configure a view via XML can be mapped to an equivalent method call in the view class. The Spyglass framework uses compile-time code generation to implement these mapping relationships without impacting performance or readability.

## Advantages of the Spyglass framework
Traditionally, attributes are handled using the TypedArray class. The Spyglass framework has several advantages over this approach:
- Entirely compile-time: The Spyglass framework generates code at compile-time, performs compile-time validation, and makes absolutely no reflective calls at runtime. This eliminates several classes of bugs and makes runtime performance no different from code written by hand.
- Better missing-attribute control: Traditionally a default value must always be provided when accessing an attribute. This makes it difficult to ignore missing attributes without significant boilerplate overhead. The Spyglass framework was designed to give developers full control of the default behaviour with minimal overhead.
- High level abstractions: The Spyglass framework uses descriptive annotations with sane defaults to allow developers to work at a high level. All the low level implementation code is generated automatically and can be forgotten about.
- Readable code: As well as making the view must more readable, the Spyglass framework generates code which can be easily read and understood. Since there's no reflection, debugging tools and stacktraces can easily follow your program flow through the generated code.
- Friendlier API: The API of the Spyglass framework is easier to use than the TypedArray class. Intent is declared using annotations, and the API uses builders where appropriate to avoid confusing variable order.

## Dependency
To use the framework, add the following to your gradle build file:
```groovy
repositories {
	jcenter()
}

dependencies {
	// For Android Gradle plugin 3.0.0+ projects:
	compileOnly 'com.matthew-tamlin:spyglass-annotations:1.0.0'
	annotationProcessor 'com.matthew-tamlin:spyglass-processor:1.0.0'

	// For older projects:
	provided 'com.matthew-tamlin:spyglass-annotations:1.0.0'
	annotationProcessor 'com.matthew-tamlin:spyglass-processor:1.0.0'
}
```

Always make sure the annotations dependency and the processor dependency have the same version number.

## Usage
Use of the Spyglass framework is divided into four tasks:
- Defining mapping relationships.
- Defining default values.
- Defining placeholder values.
- Defining conditional mapping relationships.

### Defining mapping relationships
A mapping relationship tells the Spyglass framework how to route attributes when instantiating a view from an XML layout. It's as simple as adding `@Handler` annotations to the view and calling a framework method. 

Consider the following class.
```java
public class ExampleView extends FrameLayout {
	public MyView(Context context) {
		super(context);
		init(null);
	}

	public ExampleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public void setTitle(String title) {
		TextView tv = findViewById(R.id.example_title_view);
		tv.setText(title);
	}

	private void init(AttributeSet attrs) {
		// Inflate some things, get some view references, other initialisation etc.
	}
}
```

The Spyglass framework can be used to enable configuration of the view via XML attributes.

First declare the view in the `attrs` resource file as a styleable-resource and declare a custom attribute called `title`.
```XML
<resources>
	<declare-styleable name="ExampleView">
		<attr name="title" format="string"/>
	</declare-styleable>
</resources>
```

Next modify the class to tell the Spyglass framework that the `setTitle` method should receive the value passed to the `title` attribute. This is done by adding an `@StringHandler` annotation.
```java
@StringHandler(R.styleable.ExampleView_title)
public void setTitle(String title) {
	TextView tv = findViewById(R.id.example_title_view);
	tv.setText(title);
}
```

Rebuilding the project causes the `ExampleView_SpyglassCompanion` class to be automatically generated. It's important to make sure that generated classes are included in the build (this should be the case by default in Android Studio).

Finally modify the `init` method to use the generated class. All Spyglass companions expose a builder for instantiation.
```java
private void init(AttributeSet attrs) {
	// Inflate some things, get some view references, other initialisation etc.

	ExampleView_SpyglassCompanion
			.builder()
			.withTarget(this)
			.withContext(getContext())
			.withStyleableResource(R.styleable.ExampleView)
			.withAttributeSet(attrs)
			.build()
			.passDataToMethods();
}
```

That's it! Now when the class is instantiated from an XML layout, whatever value is passed to the `title` attribute will be routed to the `setTitle` method. For example, when the following layout is inflated and the ExampleView class is instantiated, the `setTitle` method is invoked with `"Hello, World!"` as the argument.
```XML
<FrameLayout
	android:width="match_parent"
	android:height="match_parent">

	<com.example.ExampleView
		android:width="match_parent"
		android:height="match_parent"
		app:title="Hello, World!"/>

</FrameLayout>
```

You can add as many `@Handler` annotations to a view as you like, and the Spyglass framework will automatically adjust the generated code to accomodate.

The Android resource system isn't limited to string resources, and neither is the Spyglass framework. The Spyglass framework offers the following annotations for handling different attribute types:
- `@BooleanHandler`
- `@ColorHandler`
- `@ColorStateListHandler`
- `@DimensionHandler`
- `@DrawableHandler`
- `@EnumConstantHandler`
- `@EnumOrdinalHandler`
- `@FloatHandler`
- `@FractionHandler`
- `@IntegerHandler`

Check the Javadoc of these annotations for minutiae regarding their application. 

All of these annotations are analagous to the `@StringHandler` annotation, except for the `@EnumConstantHandler` which is a little bit more involved. This annotation is very useful, because it handles all the conversion between XML enums and Java enums.

To handle an enum attribute, start by defining the attribute in the `attrs` resource file.
```XML
<resources>
	<declare-styleable name="ExampleView">
		<attr name="fruit" format="enum">
			<enum name="apple" value="0"/>
			<enum name="pear" value="1"/>
			<enum name="orange" value="2"/>
	</declare-styleable>
</resources>
```

Then define an equivalent enum in a Java file:
```java
public enum Fruit {
	APPLE,
	PEAR,
	ORANGE
}
```

Finally apply the `@EnumConstantHandler` annotation to a method in the view class.
```java
@EnumConstantHandler(attributeId = R.styleable.ExampleView_fruit, enumClass = Fruit.class)
public void setFruit(Fruit fruit) {
	// Do something
}
```

That's it! Now when the class is instantiated from an XML layout, whatever value is passed to the `fruit` attribute will be converted to the appropriate `Fruit` constant and routed to the `setFruit` method. For example, when the following layout is inflated and the ExampleView class is instantiated, the `setFruit` method is invoked with `Fruit.ORANGE` as the argument.
```XML
<FrameLayout
	android:width="match_parent"
	android:height="match_parent">

	<com.example.ExampleView
		android:width="match_parent"
		android:height="match_parent"
		app:fruit="orange"/>

</FrameLayout>
```

There's one small caveat to keep in mind regarding enum conversion. The Spyglass framework expects an equal number of XML enum constants and Java enum constants, and matches constants based on their values/ordinals. So in the fruit example, `<enum name="apple" value="0"/>` maps to `Fruit.APPLE` because the value of the XML constant matches the ordinal of the Java constant.

## Defining default values
Normally if a view is inflated from an XML layout and no value is provided for an attribute, then any associated `@Handler` methods are simply not called. This is sometimes the desired behaviour, but on other occasions it is preferable to still call the method and supply a default value. The Spyglass framework provides three approaches for defining defaults.

### Default annotations
The simplest way to define defaults is using the `@DefaultTo` annotations.

The previous example can be modified to set the default title to "Untitied document" by adding the `@DefaultToString` annotation to the `setTitle` method.
```java
@HandlesString(R.styleable.ExampleView_title)
@DefaultToString("Untitled document")
public void setTitle(String title) {
	TextView tv = findViewById(R.id.example_title_view);
	tv.setText(title);
}
```

Now if the view is instantiated from XML without an `title` attribute, the `setTitle` method will called with `"Untitled document"` as the argument. Simple!

The following `@DefaultTo` annotations are provided by the Spyglass framework:
- `@DefaultToBoolean`
- `@DefaultToBooleanResource`
- `@DefaultToColorResource`
- `@DefaultToColorStateListResource`
- `@DefaultToDimension`
- `@DefaultToDrawable`
- `@DefaultToEnumConstant`
- `@DefaultToFloat`
- `@DefaultToFractionResource`
- `@DefaultToInteger`
- `@DefaultToIntegerResource`
- `@DefaultToNull`
- `@DefaultToStringResource`
- `@DefaultToTextArrayResource`
- `@DefaultToTextResource`

Check the Javadoc of these annotations for minutiae regarding their application.

### Default style attribute
Every Spyglass companion builder exposes the `withDefaultStyleAttribute` method which accepts a resource ID that refers to an attribute in the current theme, which in turn refers to a style to source default values from. That's quite a mouth full, so here's a more useful demo.

First add an attribute to the `attrs` resource file.
```XML
<resources>
	<attr name="exampleViewDefaultStyle">
</resources>
```

Then define a style in the `styles` resource file that contains the default values, and refer to the new style in the current theme using the new attribute.
```XML
<resources>
	<!-- This is the current theme. -->
	<style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
		<item name="exampleViewDefaultStyle">@style/ExampleViewDefaults</item>
	</style>

	<!-- This is the style to source defaults from. -->
	<style name="ExampleViewDefaults">
		<item name="title">Untitled document</item>
	</style>
<resources>
```

Finally pass the default style attribute to the Spyglass companion builder.
```java
private void init(AttributeSet attrs) {
	// Inflate some things, get some view references, other initialisation etc.

	ExampleView_SpyglassCompanion
			.builder()
			.withTarget(this)
			.withContext(getContext())
			.withStyleableResource(R.styleable.ExampleView)
			.withAttributeSet(attrs)
			.withDefaultStyleAttribute(R.attr.exampleViewDefaultStyle)
			.build()
			.passDataToMethods();
}
```

Now if the view is instantiated from XML without a `title` attribute, the `setTitle` method will called with `"Untitled document"` as the argument.

It's convention for view classes to define a constructor with a signature similar to `public ExampleView(Context context, AttributeSet attrs, int defStyleAttr)`. The third parameter should be propagated to the `withDefaultStyleAttribute` method of the Spyglass companion builder.

### Default style resources
Every Spyglass companion builder also exposes the `withDefaultStyleResource` method which is very similar to the `withDefaultStyleAttribute` method, except the supplied resource ID directly referes to a style resource instead of going through the current theme.

It's convention for view classes to define a constructor with a signature similar to `public ExampleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)`. The fourth parameter should be propagated to the `withDefaultStyleResoure` method of the Spyglass companion builder.

### All together
What happens if you supply a default style attribute, a default style resource and an `@DefaultTo` annotation? When multilple default sources are available, the Spyglass framework searches through them until a value is found. If the attribute is not found in the default style attribute, then the Spyglass framework moves onto the default style resource. If a value is still not found, it moves onto the default annotation. If no default annotation is found, then the method is not called at all.

## Defining placeholder values
All of the previous examples involve methods which have only one parameter, but what happens when the Spyglass framework encounters a method with multiple parameters? To call a multi-parameter method, placeholder values must be defined for all but one parameter. Placeholder values are defined by applying `@Use` annotations to the parameters.

Here's a common example:
```java
@HandlesString(R.styleable.ExampleView_title)
public void setTitle(String title, boolean animate) {
	// Some implementaiton
}
```

Normally when a view is being first initialised there is no need to show animations. As such, the method should be changed to:
```java
@HandlesString(R.styleable.ExampleView_title)
public void setTitle(String title, @UseBoolean(false) boolean animate) {
	// Some implementaiton
}
```

Now when the Spyglass companion passes in the title, it will also pass `false` to `animate`.

The Spyglass framework provides the following `@Use` annotations:
- `@UseBoolean`
- `@UseByte`
- `@UseChar`
- `@UseDouble`
- `@UseFloat`
- `@UseInt`
- `@UseLong`
- `@UseNull`
- `@UseShort`
- `@UseString`

Check the Javadoc of these annotations for minutiae regarding their application.

## Defining conditional mapping relationships
In some cases a 1:1 mapping between attributes and methods isn't sufficient, instead a conditional mapping between specific values and specific method calls is needed. The Spyglass framework currently supports conditional mapping for enums and flags.

### Enum constants
Consider the case of an enum attribute which defines the direction a view faces towards. In XML it makes sense to set the value using a single enum attribute, but in Java it makes more sense to design the API with four distinct methods. Using the `@SpecificEnumHandler` annotation, the Spyglass framework can route specific enum constants to specific methods.

Start by defining an enum attribute in the `attrs` resource file as:
```XML
<resources>
	<declare-styleable name="ExampleView">
		<attr name="directionFacing" format="enum">
			<enum name="north" value="0"/>
			<enum name="south" value="1"/>
			<enum name="east" value="2"/>
			<enum name="west" value="3"/>
		</attr>
	</declare-styleable>
</resources>
```

Then define an equivalent enum in a Java file:
```java
public enum Direction {
	NORTH,
	SOUTH,
	EAST,
	WEST
}
```

Finally apply the `@SpecificEnumHandler` annotation to the methods in the view class.
```java
@SpecificEnumHandler(attributeId = R.styleable.directionFacing, ordinal = 0)
public void faceNorth() {
	// Some implementation
}

@SpecificEnumHandler(attributeId = R.styleable.directionFacing, ordinal = 1)
public void faceSouth() {
	// Some implementation
}

@SpecificEnumHandler(attributeId = R.styleable.directionFacing, ordinal = 2)
public void faceEast() {
	// Some implementation
}

@SpecificEnumHandler(attributeId = R.styleable.directionFacing, ordinal = 3)
public void faceWest() {
	// Some implementation
}
```

Just as with the `@EnumConstantHandler`, the Spyglass framework automatically handles conversion between the XML enum and the Java enum. The difference is that in this case the framework doesn't pass in the value, instead it uses the value's ordinal to decide which method to call. For example, when the following layout is inflated and the ExampleView class is instantiated, only the `faceWest` method is invoked.
```XML
<FrameLayout
	android:width="match_parent"
	android:height="match_parent">
	
	<com.example.ExampleView
		android:width="match_parent"
		android:height="match_parent"
		app:directionFacing="west"/>

</FrameLayout>
```

### Flags
Consider the case of a flag attribute which defines some properties to apply to some text. In XML it makes sense to set the value using a single flag attribute, but in Java it makes more sense to design the API with distinct methods. Using the `@SpecificFlagHandler` annotation, the Spyglass framework can route specific flags to specific methods.

Start by defining a flag attribute in the `attrs` resource file as:
```XML
<resources>
	<declare-styleable name="ExampleView">
		<attr name="textProperties">
			<flag name="bold" value="1"/>
			<flag name="italic" value="2"/>
			<flag name="underline" value="4"/>
			<flag name="regular" value="8"/>
		</attr>
	</declare-styleable>
</resources>
```

Next apply the annotation to the methods in the view class.
```java
@SpecificFlagHandler(attributeId = R.styleable.textProperties, handledFlags = 1)
public void useBoldText() {
	// Some implementation
}

@SpecificFlagHandler(attributeId = R.styleable.textProperties, handledFlags = 2)
public void useItalicText() {
	// Some implementation
}

@SpecificFlagHandler(attributeId = R.styleable.textProperties, handledFlags = 4)
public void useUnderlinedText() {
	// Some implementation
}

@SpecificFlagHandler(attributeId = R.styleable.textProperties, handledFlags = 8)
public void useRegularText() {
	// Some implementation
}
```

When the Spyglass framework find a value for the `textProperties` attribute, it will do a bitwise-OR comparison between the value and the handled flags. If the comparison resolves to true, then the method is called. For example, when the following layout is inflated and the ExampleView class is instantiated, only the `useBoldText` and `useUnderlinedText` methods are called.
```XML
<FrameLayout
	android:width="match_parent"
	android:height="match_parent">

	<com.example.ExampleView
		android:width="match_parent"
		android:height="match_parent"
		app:textProperties="bold|underline"/>

</FrameLayout>
```

## Compatibility
The Spyglass framework is compatible with all versions of Android.

## Licensing
This library is licenced under the Apache v2.0 licence. Have a look at [the license](LICENSE) for details.

## Issues
Before reporting an issue, please ensure the issue has not already been resolved and ensure the issue directly relates to the framework.
