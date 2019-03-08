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
public class SequenceRule extends GrammarizerRule {
  public SequenceRule() {
    super("yaml_sequence");
  }

  @Override
  public Boolean handle(Token key, Token current, Token next, Token previous, Map<String, String> variables, LinkedList<String> nodeComments, LinkedList<String> commentBlock, List<YamlValue> values) throws GrammarException {
    if(previous == null || next == null || !next.getDefinition().equalsIgnoreCase("yaml_literal") && !next.getDefinition().equalsIgnoreCase("yaml_quote")) {
      throw new GrammarException(current.getLineNumber(), variables.get("line") + current.getValue());
    }
    variables.put("containsSequence", "");
    variables.put("sequence", "");
    return false;
  }
}