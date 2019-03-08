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
public class ShortSeparatorRule extends GrammarizerRule {
  public ShortSeparatorRule() {
    super("yaml_shorthand_separator");
    this.skip = true;
  }

  @Override
  public Boolean handle(Token key, Token current, Token next, Token previous, Map<String, String> variables, LinkedList<String> nodeComments, LinkedList<String> commentBlock, List<YamlValue> values) throws GrammarException {
    if(variables.containsKey("inShort")) {
      values.add(new YamlValue(commentBlock, variables.get("shortValue"), "string_list"));
      commentBlock.clear();

      variables.put("line", variables.get("line") + current.getValue());

      variables.remove("shortValue");
      return false;
    }

    StringBuilder shortValue = new StringBuilder(variables.get("shortValue"));

    shortValue.append(current.getValue());
    variables.put("shortValue", shortValue.toString());
    return false;
  }
}