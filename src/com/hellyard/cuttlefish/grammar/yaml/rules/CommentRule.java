package com.hellyard.cuttlefish.grammar.yaml.rules;

import com.hellyard.cuttlefish.exception.GrammarException;
import com.hellyard.cuttlefish.api.token.Token;
import com.hellyard.cuttlefish.grammar.yaml.YamlValue;
import com.hellyard.cuttlefish.token.yaml.BlockToken;

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
public class CommentRule extends GrammarizerRule {
  public CommentRule() {
    super("yaml_comment");
  }

  @Override
  public Boolean handle(Token key, Token current, Token next, Token previous, Map<String, String> variables, LinkedList<String> nodeComments, LinkedList<String> commentBlock, List<YamlValue> values) throws GrammarException {
    if(previous != null && key != null) {
      if(next == null || !next.getDefinition().equalsIgnoreCase("yaml_sequence")) {
        return true;
      }
    }
    commentBlock.addAll(((BlockToken)current).getValues());

    if(next != null && !next.getDefinition().equalsIgnoreCase("empty_line") && !next.getDefinition().equalsIgnoreCase("yaml_comment") && !variables.containsKey("sequence") && !next.getDefinition().equalsIgnoreCase("yaml_sequence")) {
      nodeComments.addAll(commentBlock);
      commentBlock.clear();
    }
    return false;
  }
}
