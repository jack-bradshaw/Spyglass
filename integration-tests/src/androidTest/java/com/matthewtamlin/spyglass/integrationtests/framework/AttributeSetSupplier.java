/*
 * Copyright 2017 Matthew David Tamlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
           type = parser.next()) {
      }
      
    } catch (final Exception e) {
      throw new RuntimeException("Could not parse XML.", e);
    }
    
    return Xml.asAttributeSet(parser);
  }
}