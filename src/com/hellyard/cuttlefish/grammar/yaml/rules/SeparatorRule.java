package com.hellyard.cuttlefish.grammar.yaml.rules;

import com.hellyard.cuttlefish.exception.GrammarException;
import com.hellyard.cuttlefish.api.token.Token;
import com.hellyard.cuttlefish.grammar.yaml.YamlValue;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 *
 * License: http://creativecommons.org/licenses/by-nc-nd/4.0/
 */
public class SeparatorRule extends GrammarizerRule {
  public SeparatorRule() {
    super("yaml_separator");
    this.skip = true;
  }

  public SeparatorRule(String definition) {
    super(definition);
  }

  @Override
  public Boolean handle(Token key, Token current, Token next, Token previous, Map<String, String> variables, LinkedList<String> nodeComments, LinkedList<String> commentBlock, List<YamlValue> values) throws GrammarException {
    if(key != null) {
      StringBuilder line = new StringBuilder(variables.get("line"));

      line.append(current.getValue());
      variables.put("line", line.toString());
      return false;
    }
    throw new GrammarException(current.getLineNumber(), variables.get("line") + current.getValue());
  }
}
