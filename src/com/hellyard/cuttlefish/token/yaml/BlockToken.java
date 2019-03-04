package com.hellyard.cuttlefish.token.yaml;

import com.hellyard.cuttlefish.api.token.Token;

import java.util.LinkedList;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 * License: http://creativecommons.org/licenses/by-nc-nd/4.0/
 */
public class BlockToken extends Token {

  private LinkedList<String> values;

  public BlockToken(int lineNumber, int indentation, String definition, LinkedList<String> values) {
    super(lineNumber, indentation, definition, "");
    this.values = values;
  }

  public LinkedList<String> getValues() {
    return values;
  }

  public void setValues(LinkedList<String> values) {
    this.values = values;
  }
}