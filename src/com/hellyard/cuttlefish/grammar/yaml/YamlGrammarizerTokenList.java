package com.hellyard.cuttlefish.grammar.yaml;

import com.hellyard.cuttlefish.api.grammar.GrammarObject;
import com.hellyard.cuttlefish.api.grammar.Grammarizer;
import com.hellyard.cuttlefish.api.token.Token;
import com.hellyard.cuttlefish.exception.GrammarException;
import com.hellyard.cuttlefish.iterator.TokenIterator;
import com.hellyard.cuttlefish.iterator.TokenList;

import java.util.LinkedList;

public class YamlGrammarizerTokenList implements Grammarizer {
  @Override
  public String name() {
    return "YAML";
  }

  @Override
  public LinkedList<? extends GrammarObject> grammarize(LinkedList<Token> tokens) throws GrammarException {
    return null;
  }

  public LinkedList<? extends GrammarObject> grammarize(TokenList tokens) throws GrammarException {
    LinkedList<YamlNode> nodes = new LinkedList<>();
    LinkedList<String> comments = new LinkedList<>();
    LinkedList<String> values = new LinkedList<>();
    StringBuilder parts = new StringBuilder();
    boolean firstLiteral = false;
    boolean elementDone = false;

    TokenIterator it = tokens.iterator();
    while(it.hasNext()) {
      Token current = it.next();
      Token previous = null;
      boolean topElement = false;

      if (it.hasPrevious()) {
        previous = it.previous();
      }

      System.out.println("Previous: " + (previous == null));
      if (previous == null) {
        topElement = true;
      }

      System.out.println("Token Line: " + current.getLineNumber());
      System.out.println("Token Indent: " + current.getIndentation());
      System.out.println("Token Def: " + current.getDefinition());
      System.out.println("Token Value: " + current.getValue());

      if(current.getLineNumber() > previous.getLineNumber()) {
        if(previous.getDefinition().equalsIgnoreCase("yaml_sequence")) {

          throw new GrammarException(current.getLineNumber(), current.getValue());
        }
      }

      switch (current.getDefinition()) {

        // Yaml comment gets stored into list and next element.
        case "yaml_comment": {
          comments.add(current.getValue());
          System.out.println("Comments.size: " + comments.size());
          break;
        }


        // Separator comes after the firstLiteral so it just adds itself to the parts.
        case "yaml_separator": {
          if(previous == null || !previous.getDefinition().equalsIgnoreCase("yaml_literal")
             || previous.getDefinition().equalsIgnoreCase("yaml_literal")
                && previous.getLineNumber() != current.getLineNumber()) {

            throw new GrammarException(current.getLineNumber(), current.getValue());
          }
          parts.append(current.getValue());
          break;
        }
        case "yaml_literal": {
          if (!firstLiteral) {
            firstLiteral = true;
            parts.append(current.getValue());
            continue;
          }

          if(!topElement) {
            if(previous.getDefinition().equalsIgnoreCase("yaml_separator")
                && previous.getLineNumber() == current.getLineNumber() || previous.getLineNumber() == current.getLineNumber()
                && previous.getDefinition().equalsIgnoreCase("yaml_sequence")) {
              values.add(current.getValue());
            }
          }

          if(!it.hasNext() || it.peek().getLineNumber() > current.getLineNumber() &&
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
        node.setValues(values);
        nodes.add(node);
        parts.setLength(0);
        comments.clear();
        values.clear();
        elementDone = false;
      }
    }
    return nodes;
  }
}
