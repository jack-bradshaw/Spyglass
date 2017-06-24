package com.matthewtamlin.spyglass.integration_tests.framework;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

public class AttributeSetSupplier {
	public static AttributeSet fromXml(final Context context, final int xmlResId) {
		final XmlPullParser parser = context.getResources().getXml(xmlResId);

		try {
			for (int type = 0;
				 (type != XmlPullParser.END_DOCUMENT) && (type != XmlPullParser.START_TAG);
				 type = parser.next()) {}

		} catch (final Exception e) {
			throw new RuntimeException("Could not parse XML.", e);
		}

		return Xml.asAttributeSet(parser);
	}
}