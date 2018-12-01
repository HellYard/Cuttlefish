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
    return "YAML";
  }

  @Override
  public LinkedList<? extends GrammarObject> grammarize(final LinkedList<Token> tokens) throws GrammarException {
    /*System.out.println("Tokens: " + tokens.size());
    for(int i = 0; i < tokens.size(); i++) {
      System.out.println(tokens.get(i).getDefinition());
      System.out.println(tokens.get(i).getValue());
    }*/
    LinkedList<YamlNode> nodes = new LinkedList<>();
    LinkedList<String> comments = new LinkedList<>();
    LinkedList<String> values = new LinkedList<>();
    StringBuilder parts = new StringBuilder();
    boolean firstLiteral = false;
    boolean elementDone = false;
    boolean topElement = false;

    for(ListIterator<Token> it = tokens.listIterator(); it.hasNext();) {
      Token previous = null;

      if (it.hasPrevious()) {
        previous = tokens.get(it.previousIndex());
      }

      Token current = it.next();
      Token next = null;

      if(it.hasNext()) {
        next = tokens.get(it.nextIndex());
      }

      //System.out.println("Previous: " + (previous == null));
      if (previous == null) {
        topElement = true;
      }

      System.out.println("Tokens: " + tokens.size());
      System.out.println("Token Line: " + current.getLineNumber());
      System.out.println("Token Indent: " + current.getIndentation());
      System.out.println("Token Def: " + current.getDefinition());
      System.out.println("Token Value: " + current.getValue());

      if(previous != null) {
        System.out.println("Tokens: " + tokens.size());
        System.out.println("Previous Line: " + previous.getLineNumber());
        System.out.println("Previous Indent: " + previous.getIndentation());
        System.out.println("Previous Def: " + previous.getDefinition());
        System.out.println("Previous Value: " + previous.getValue());
      }

      if(previous != null && current.getLineNumber() > previous.getLineNumber()) {
        if(previous.getDefinition().equalsIgnoreCase("yaml_sequence")) {

          if(next == null || !next.getDefinition().equalsIgnoreCase("yaml_separator")) {
            throw new GrammarException(current.getLineNumber(), current.getValue());
          }
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

          if(next != null) {
            //System.out.println("Token Line: " + next.getLineNumber());
            //System.out.println("Token Indent: " + next.getIndentation());
            //System.out.println("Token Def: " + next.getDefinition());
            //System.out.println("Token Value: " + next.getValue());
            if(next.getLineNumber() > current.getLineNumber() &&
                !next.getDefinition().equalsIgnoreCase("yaml_sequence") ) {
              elementDone = true;
            }
          } else {
            elementDone = true;
          }
          break;
        }
      }

      if(elementDone) {
        YamlNode parent = null;
        if(nodes.size() > 0 && previous != null) {
          if(previous.getIndentation() > current.getIndentation()) {
            parent = nodes.getLast().getParent();
          } else if(previous.getIndentation() == current.getIndentation()) {
            parent = nodes.getLast().getParent();
          } else {
            parent = nodes.getLast();
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
