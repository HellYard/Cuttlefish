package com.hellyard.cuttlefish.api.grammar;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 * License: http://creativecommons.org/licenses/by-nc-nd/4.0/
 */
public interface GrammarObject {
  GrammarObject getParent();
  int getLineNumber();
  String getLine();
}