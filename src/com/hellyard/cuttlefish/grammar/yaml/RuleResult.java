package com.hellyard.cuttlefish.grammar.yaml;

import com.hellyard.cuttlefish.api.token.Token;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 *
 * License: http://creativecommons.org/licenses/by-nc-nd/4.0/
 */
public class RuleResult {

  public boolean complete;
  public Token key;

  public RuleResult(boolean complete, Token key) {
    this.complete = complete;
    this.key = key;
  }
}