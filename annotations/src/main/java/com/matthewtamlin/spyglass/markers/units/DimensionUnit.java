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

package com.matthewtamlin.spyglass.markers.units;

/**
 * A unit of measurement which can quantify a screen dimension.
 */
public enum DimensionUnit {
  /**
   * Pixels. The actual pixel count on the screen.
   */
  PX,
  
  /**
   * Display independent pixels. Automatically scaled by the system based on screen density.
   */
  DP,
  
  /**
   * Points. Equal to 1/72 of an inch.
   */
  PT,
  
  /**
   * Inches. Actual distance on the screen.
   */
  IN,
  
  /**
   * Scale independent pixels. Automatically scaled by the system based on the user's font size preference.
   */
  SP,
  
  /**
   * Millimetres. Actual distance on the screen.
   */
  MM
}