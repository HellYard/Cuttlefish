package com.hellyard.cuttlefish.exception;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 * License: http://creativecommons.org/licenses/by-nc-nd/4.0/
 */
public class GrammarException extends Exception {

  /**
   * Constructs a new exception with {@code null} as its detail message. The cause is not
   * initialized, and may subsequently be initialized by a call to {@link #initCause}.
   */
  public GrammarException(final int line, final String sequence) {
    super("Invalid grammar definition at line " + line + ": " + sequence);
  }
}