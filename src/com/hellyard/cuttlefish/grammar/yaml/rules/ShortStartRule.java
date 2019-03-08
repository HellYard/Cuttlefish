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
public class ShortStartRule extends GrammarizerRule {
  public ShortStartRule() {
    super("yaml_shorthand_start");
    this.skip = true;
  }

  @Override
  public Boolean handle(Token key, Token current, Token next, Token previous, Map<String, String> variables, LinkedList<String> nodeComments, LinkedList<String> commentBlock, List<YamlValue> values) throws GrammarException {
    if(!variables.containsKey("inShort")) {
      variables.put("inShort", "true");
      variables.put("shortChar", current.getValue().trim());

      variables.put("line", variables.get("line") + current.getValue());
    }
    return false;
  }
}