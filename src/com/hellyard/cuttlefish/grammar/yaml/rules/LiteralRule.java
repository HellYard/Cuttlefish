package com.hellyard.cuttlefish.grammar.yaml.rules;

import com.hellyard.cuttlefish.api.token.Token;
import com.hellyard.cuttlefish.exception.GrammarException;
import com.hellyard.cuttlefish.grammar.yaml.RuleResult;
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
public class LiteralRule extends GrammarizerRule {
  public LiteralRule() {
    super("yaml_literal");
  }

  @Override
  public RuleResult handle(Token key, Token current, Token next, Token previous, Map<String, String> variables, LinkedList<String> nodeComments, LinkedList<String> commentBlock, List<YamlValue> values) throws GrammarException {

    System.out.println("Keys: " + String.join(", ", variables.keySet()));

    if(variables.containsKey("sequence")) {
      variables.remove("sequence");
      values.add(new YamlValue(commentBlock, current.getValue().trim(), "string"));
      commentBlock.clear();
      return new RuleResult(false, key);
    } else if (key != null) {
      if(previous != null && previous.getLineNumber() < current.getLineNumber()) {
        return new RuleResult(true, key);
      }
      if(next != null && next.getLineNumber() == current.getLineNumber() && next.getDefinition().equalsIgnoreCase("yaml_sequence")) {
        values.add(new YamlValue(commentBlock, current.getValue() + "-", "string"));
        commentBlock.clear();
        skip = true;
      } else {
        values.add(new YamlValue(commentBlock, current.getValue().trim(), "string"));
        commentBlock.clear();
      }
      return new RuleResult(true, key);
    } else {
      variables.put("line", variables.get("line") + current.getValue());
      System.out.println("Current: " + current.toString());
      return new RuleResult(false, current);
    }
  }
}