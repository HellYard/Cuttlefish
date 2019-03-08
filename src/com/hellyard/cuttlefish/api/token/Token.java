package com.hellyard.cuttlefish.api.token;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 * License: http://creativecommons.org/licenses/by-nc-nd/4.0/
 *
 */
public class Token {

  private final int lineNumber;
  private final int indentation;
  private final String definition;
  private final String value;

  public Token(int lineNumber, int indentation, String definition, String value) {
    this.lineNumber = lineNumber;
    this.indentation = indentation;
    this.definition = definition;
    this.value = value;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public int getIndentation() {
    return indentation;
  }

  public String getDefinition() {
    return definition;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "Token{" +
        "lineNumber=" + lineNumber +
        ", indentation=" + indentation +
        ", definition='" + definition + '\'' +
        ", value='" + value + '\'' +
        '}';
  }
}