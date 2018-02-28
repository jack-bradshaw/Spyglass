/*
 * Copyright 2017-2018 Matthew David Tamlin
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

package com.matthewtamlin.spyglass.processor.validation;

/**
 * Exception to indicate that the Spyglass framework detected an illegal use of Spyglass components.
 */
public class ValidationException extends Exception {
  /**
   * Constructs a new ValidationException with no cause or message. The
   */
  public ValidationException() {
    super();
  }

  /**
   * Constructs a new ValidationException with a message but no cause.
   */
  public ValidationException(final String message) {
    super(message);
  }

  /**
   * Constructs a new ValidationException with a message and a cause.
   */
  public ValidationException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new ValidationException with a cause but no method.
   */
  public ValidationException(final Throwable cause) {
    super(cause);
  }
}