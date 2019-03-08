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
public class ShortEndRule extends GrammarizerRule {
  public ShortEndRule() {
    super("yaml_shorthand_end");
    this.skip = true;
  }

  @Override
  public Boolean handle(Token key, Token current, Token next, Token previous, Map<String, String> variables, LinkedList<String> nodeComments, LinkedList<String> commentBlock, List<YamlValue> values) throws GrammarException {
    if(variables.containsKey("inShort")) {
      final String shortChars = variables.get("shortChars");
      values.add(new YamlValue(commentBlock, variables.get("shortValue"), "string_list"));
      if(shortChars.indexOf(current.getValue().trim()) == (shortChars.indexOf(variables.get("shortChar")) + 1)) {
        return true;
      }
      variables.put("shortValue", variables.get("shortValue") + current.getValue());
      variables.put("line", variables.get("line") + current.getValue());
      return false;
    }
    return false;
  }
}