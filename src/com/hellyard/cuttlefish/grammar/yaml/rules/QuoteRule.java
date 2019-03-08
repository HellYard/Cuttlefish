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
public class QuoteRule extends GrammarizerRule {
  public QuoteRule() {
    super("yaml_quote");
  }

  @Override
  public Boolean handle(Token key, Token current, Token next, Token previous, Map<String, String> variables, LinkedList<String> nodeComments, LinkedList<String> commentBlock, List<YamlValue> values) throws GrammarException {

    System.out.println("Keys: " + String.join(", ", variables.keySet()));
    System.out.println("Current: " + current.toString());
    System.out.println("Line: " + variables.get("line"));
    if(variables.containsKey("quoteChar")) {
      StringBuilder quotedValue = new StringBuilder(variables.get("quotedValue"));
      StringBuilder shortValue = new StringBuilder(variables.get("shortValue"));
      if(current.getValue().trim().equalsIgnoreCase(variables.get("quoteChar").trim()) && !quotedValue.toString().endsWith("\\")) {
        System.out.println("Turning quotes off.");
        if(variables.containsKey("shortValue")) {
          shortValue.append(quotedValue.toString().replaceAll("null ", ""));
          variables.put("shortValue", shortValue.toString());
        } else {
          values.add(new YamlValue(commentBlock, quotedValue.toString().replaceAll("null ", ""), "string"));
          commentBlock.clear();
        }

        variables.put("quoteChar", "");
        variables.put("sequence", "");

        if(next == null) {
          System.out.println("No more lines");
          return true;
        }
      } else {
        if(quotedValue.toString().endsWith("\\")) quotedValue.deleteCharAt(quotedValue.length() - 1);
        quotedValue.append(current.getValue());
        variables.put("quotedValue", quotedValue.toString());
      }
    } else {
      variables.put("quoteChar", current.getValue());
    }
    return false;
  }
}