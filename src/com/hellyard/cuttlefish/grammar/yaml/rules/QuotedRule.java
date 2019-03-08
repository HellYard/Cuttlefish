package com.hellyard.cuttlefish.grammar.yaml.rules;

import com.hellyard.cuttlefish.api.token.Token;
import com.hellyard.cuttlefish.exception.GrammarException;
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
public class QuotedRule extends GrammarizerRule {
  public QuotedRule() {
    super("*");
  }

  @Override
  public Boolean handle(Token key, Token current, Token next, Token previous, Map<String, String> variables, LinkedList<String> nodeComments, LinkedList<String> commentBlock, List<YamlValue> values) throws GrammarException {
    if(variables.containsKey("quoteChar") && !variables.get("quoteChar").equalsIgnoreCase("")) {
      System.out.println("Quoted Value: " + variables.get("quotedValue"));
      variables.put("quotedValue", variables.get("quotedValue") + current.getValue());
      variables.put("line", variables.get("line") + current.getValue());
      this.skip = true;
    }
    return false;
  }
}
