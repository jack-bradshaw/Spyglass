# Spyglass
Spyglass is an Android library which makes attribute handling in custom views much simpler.

## Advantages of the Spyglass framework
The Spyglass framework has several advantages over other means of handling custom view attributes:
- Entirely compile-time: The Spyglass framework generates code at compile-time, performs compile-time validation, and makes absolutely no reflective calls at runtime. This eliminates several classes of bugs and makes runtime performance no diffrent from code written by hand.
- Better missing-attribute control: Traditionally a default value must be provided when accessing view attributes via the TypedArray class, which makes it difficult to ignore missing attributes without significant boilerplate overhead. The spyglass framework provides annotations which allow developers to easily handle/ignore missing-attributes on a case-by-case basis.
- High level abstractions: The Spyglass uses descriptive annotations with sane defaults to allow developers to work at a high level. All the low level implementation code is generated automatically and can be ignored.
- Readable code: As well as making the consumer more readable, the Spyglass framework generates code which can be easily read and understood. Since there's no reflection, debugging tools and stacktraces can easily follow your program flow through the generated code.
- Friendlier API: The API of the Spyglass framework is easier to use than the TypedArray class. Intent is declared using annotations, and the API uses builders where appropriate to avoid confusing variable order.
