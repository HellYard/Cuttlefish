package com.hellyard.cuttlefish.grammar.yaml;

import com.hellyard.cuttlefish.api.grammar.GrammarObject;
import com.hellyard.cuttlefish.api.grammar.Grammarizer;
import com.hellyard.cuttlefish.api.token.Token;
import com.hellyard.cuttlefish.exception.GrammarException;
import com.hellyard.cuttlefish.iterator.TokenIterator;
import com.hellyard.cuttlefish.iterator.TokenList;

import java.util.LinkedList;

public class YamlGrammarizer implements Grammarizer {
  @Override
  public String name() {
    return "YAML Ain't Markup Language";
  }

  @Override
  public LinkedList<? extends GrammarObject> grammarize(TokenList tokens) throws GrammarException {
    LinkedList<YamlNode> nodes = new LinkedList<>();
    LinkedList<String> comments = new LinkedList<>();
    StringBuilder parts = new StringBuilder();
    boolean firstLiteral = false;

    for (TokenIterator it = tokens.iterator(); it.hasNext();) {
      Token current = it.next();
      Token previous = null;
      boolean topElement = false;
      boolean elementDone = false;
      if (it.hasPrevious()) {
        previous = it.previous();
      }

      if (previous == null) {
        topElement = true;
      }

      switch (current.getDefinition()) {
        // Yaml comment gets stored into list and next element.
        case "yaml_comment": {
          comments.add(current.getValue());
          continue;
        }

        // Separator comes after the firstLiteral so it just adds itself to the parts.
        case "yaml_separator": {
          if(previous == null || !previous.getDefinition().equalsIgnoreCase("yaml_literal")
             || previous.getDefinition().equalsIgnoreCase("yaml_literal")
                && previous.getLineNumber() != current.getLineNumber()) {

            throw new GrammarException(current.getLineNumber(), current.getValue());
          }
          parts.append(current.getValue());
        }
        case "yaml_literal": {
          if (topElement && !firstLiteral) {
            firstLiteral = true;
            parts.append(current.getValue());
            continue;
          }

          if(it.peek().getLineNumber() > current.getLineNumber() &&
              !it.peek().getDefinition().equalsIgnoreCase("yaml_sequence") ) {
            elementDone = true;
          }
          break;
        }
      }

      if(elementDone) {
        YamlNode parent = null;
        if(previous != null) {
          if(previous.getIndentation() > current.getIndentation()) {

          } else if(previous.getIndentation() == current.getIndentation()) {
            parent = nodes.getLast().getParent();
          }
        }

        YamlNode node = new YamlNode(parent, current.getLineNumber(), parts.toString());
        node.setComments(comments);
        nodes.add(node);
        parts.setLength(0);
        comments.clear();
      }
    }
    return nodes;
  }
}
