package com.hellyard.cuttlefish.grammar.yaml;

import com.hellyard.cuttlefish.api.grammar.GrammarObject;
import com.hellyard.cuttlefish.api.grammar.Grammarizer;
import com.hellyard.cuttlefish.api.token.Token;
import com.hellyard.cuttlefish.exception.GrammarException;

import java.util.LinkedList;
import java.util.ListIterator;

public class YamlGrammarizer implements Grammarizer {
  LinkedList<YamlNode> nodes = new LinkedList<>();

  @Override
  public String name() {
    return "YAML";
  }

  @Override
  public LinkedList<? extends GrammarObject> grammarize(final LinkedList<Token> tokens) throws GrammarException {
    LinkedList<String> comments = new LinkedList<>();
    String key = null;
    LinkedList<String> values = new LinkedList<>();
    StringBuilder line = new StringBuilder();
    boolean inList = false;
    boolean sequence = false;
    boolean skip = false;

    for(ListIterator<Token> it = tokens.listIterator(); it.hasNext();) {
      Token previous = null;
      if(it.hasPrevious()) {
        previous = tokens.get(it.previousIndex());
        System.out.println(previous.getDefinition());
      }

      Token current = it.next();
      Token next = null;
      if(it.hasNext()) {
        next = tokens.get(it.nextIndex());
      }

      if(skip) {
        current = it.next();
        skip = false;
      }

      // Get Tokens
      System.out.println("--------------");
      System.out.println("-----");
      System.out.println("Token Line: {" + current.getLineNumber() + "}");
      System.out.println("Token Indent: {" + current.getIndentation() + "}");
      System.out.println("Token Def: {" + current.getDefinition() + "}");
      System.out.println("Token Value: {" + current.getValue() + "}");
      System.out.println("Current Line: {" + line.toString() + "}");
      System.out.println("-----");
      System.out.println("--------------");
      // Filter out nodes
      if (current.getDefinition().equals("yaml_comment") || current.getDefinition().equals("empty_line")) {
        comments.add(current.getValue());
        continue;
      }

      if(current.getDefinition().equalsIgnoreCase("yaml_sequence")) {
        if(previous == null || sequence || next == null || !next.getDefinition().equalsIgnoreCase("yaml_literal"))
          throw new GrammarException(current.getLineNumber(), line.toString() + current.getValue());
        else if(previous.getLineNumber() == current.getLineNumber()) {
          final int size = values.size();
          System.out.println("Value: " + values.get(size));
          values.add(size - 1, values.getLast() + "-");
        } else sequence = true;
      }

      if (current.getDefinition().equals("yaml_separator")) {
        if (key != null) {
          line.append(current.getValue());
          continue;
        } else {
          throw new GrammarException(current.getLineNumber(), line.toString() + current.getValue());
        }
      }

      if (current.getDefinition().equals("yaml_literal")) {
        if(sequence) {
          sequence = false;
          values.add(current.getValue());
        } else if (key != null) {
          System.out.println("Previous line: " + previous.getLineNumber());
          System.out.println("Current line: " + current.getLineNumber());
          if(previous != null && previous.getLineNumber() < current.getLineNumber()) {
            System.out.println("In new if clause fucker");
            YamlNode node = new YamlNode(null, previous.getLineNumber(), line.toString(), comments, key, values);
            nodes.add(node);
            System.out.println("Added node: " + node.toString());
            comments = new LinkedList<>();
            values = new LinkedList<>();
            line.setLength(0);

            key = current.getValue();
            line.append(current.getValue());
            continue;
          }
          if(next != null && next.getLineNumber() == current.getLineNumber() && next.getDefinition().equalsIgnoreCase("yaml_sequence")) {
            values.add(current.getValue() + "-");
            skip = true;
          } else {
            values.add(current.getValue());
          }
          YamlNode node = new YamlNode(null, current.getLineNumber(), line.toString(), comments, key, values);
          nodes.add(node);
          System.out.println("Added node: " + node.toString());
          comments = new LinkedList<>();
          key = null;
          line.setLength(0);
          values = new LinkedList<>();
        } else {
          key = current.getValue();
          line.append(current.getValue());
        }
      }

    }
    return nodes;
  }
}
