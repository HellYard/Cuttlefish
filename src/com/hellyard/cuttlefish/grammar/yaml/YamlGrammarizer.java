package com.hellyard.cuttlefish.grammar.yaml;

import com.hellyard.cuttlefish.api.grammar.GrammarObject;
import com.hellyard.cuttlefish.api.grammar.Grammarizer;
import com.hellyard.cuttlefish.api.token.Token;
import com.hellyard.cuttlefish.exception.GrammarException;

import java.util.LinkedList;
import java.util.ListIterator;

public class YamlGrammarizer implements Grammarizer {
  @Override
  public String name() {
    return "YAML Ain't Markup Language";
  }

  @Override
  public LinkedList<? extends GrammarObject> grammarize(LinkedList<Token> tokens) throws GrammarException {
    LinkedList<YamlNode> nodes = new LinkedList<>();
    LinkedList<String> comments = new LinkedList<>();
    StringBuilder parts = new StringBuilder();
    boolean firstLiteral = false;

    for (ListIterator<Token> it = tokens.listIterator(); it.hasNext(); ) {
      Token current = it.next();
      Token previous = null;
      boolean topElement = false;
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
          parts.append(current.getValue());
        }
        case "yaml_literal": {
          if (topElement && !firstLiteral) {
            firstLiteral = true;
            parts.append(current.getValue());
            continue;
          }
          if (topElement) {
            YamlNode node = new YamlNode(null, current.getLineNumber(), parts.toString());
            node.setComments(comments);
            nodes.add(node);
            parts = new StringBuilder();
            comments = new LinkedList<>();
            continue;
          }
          // So the identation is
          if (current.getIndentation() > previous.getIndentation()) {

          }
          break;
        }
      }
    }
    return nodes;
  }
}
