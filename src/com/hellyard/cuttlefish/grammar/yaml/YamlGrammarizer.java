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
    Token key = null;
    LinkedList<String> values = new LinkedList<>();
    StringBuilder line = new StringBuilder();
    StringBuilder quotedValue = new StringBuilder();
    StringBuilder shortValue = new StringBuilder();
    boolean inQuote = false;
    boolean inShort = false;
    final String shortChars = "[]{}";
    String shortChar = "";
    String quoteChar = "";
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

      if(current.getDefinition().equalsIgnoreCase("yaml_quote")) {
        if(inQuote && current.getValue().trim().equalsIgnoreCase(quoteChar.trim())) {
          if(inShort) {
            shortValue.append(quotedValue.toString());
          } else {
            values.add(quotedValue.toString());
          }
          inQuote = false;
          quoteChar = "";
          quotedValue.setLength(0);
          line.setLength(0);
          sequence = false;
        } else if(!inQuote) {
          inQuote = true;
          quoteChar = current.getValue();
        }
        continue;
      }

      if(inQuote) {
        line.append(current.getValue());
        quotedValue.append(current.getValue());
        continue;
      }

      if(current.getDefinition().equalsIgnoreCase("yaml_shorthand_start")) {
        if(!inShort) {
          inShort = true;
          shortChar = current.getValue().trim();
          line.append(current.getValue());
          continue;
        }
      }

      if(current.getDefinition().equalsIgnoreCase("yaml_shorthand_separator")) {
        if(inShort) {
          values.add(shortValue.toString());
          shortValue.setLength(0);
          line.append(current.getValue());
          continue;
        }
      }

      if(current.getDefinition().equalsIgnoreCase("yaml_shorthand_end")) {
        if(inShort) {
          if(shortChars.indexOf(current.getValue().trim()) == (shortChars.indexOf(shortChar) + 1)) {
            inShort = false;
            line.append(current.getValue());
            values.add(shortValue.toString());
            final YamlNode parent = getParent(nodes, key);
            final String nodeStr = (parent == null)? key.getValue() : parent.getNode() + "." + key.getValue();
            YamlNode node = new YamlNode(parent, key.getIndentation(), current.getLineNumber(), line.toString(), comments, key.getValue(), nodeStr, values);
            node.setShortCharacters(shortChar + shortChars.charAt(shortChars.indexOf(shortChar) + 1));
            node.setShorthand(true);
            nodes.add(node);
            System.out.println("Added node: " + node.toString());
            System.out.println("Parent: " + ((parent == null)? "None" : parent.toString()));
            comments = new LinkedList<>();
            key = null;
            line.setLength(0);
            shortValue.setLength(0);
            values = new LinkedList<>();
            continue;
          }
          shortValue.append(current.getValue());
          line.append(current.getValue());
          continue;
        }
      }

      if(inShort) {
        shortValue.append(current.getValue());
        continue;
      }

      if (current.getDefinition().equals("yaml_comment") || current.getDefinition().equals("empty_line")) {
        if(previous != null && key != null) {
          final YamlNode parent = getParent(nodes, key);
          final String nodeStr = (parent == null)? key.getValue() : parent.getNode() + "." + key.getValue();
          YamlNode node = new YamlNode(parent, key.getIndentation(), current.getLineNumber(), line.toString(), comments, key.getValue(), nodeStr, values);
          nodes.add(node);
          System.out.println("Added node: " + node.toString());
          System.out.println("Parent: " + ((parent == null)? "None" : parent.toString()));
          comments = new LinkedList<>();
          key = null;
          line.setLength(0);
          values = new LinkedList<>();
        }

        if(current.getDefinition().equalsIgnoreCase("empty_line")) {
          comments.add(System.lineSeparator());
        } else {
          comments.add(current.getValue());
        }
        continue;
      }

      if(current.getDefinition().equalsIgnoreCase("yaml_sequence")) {
        if(previous == null || sequence || next == null || !next.getDefinition().equalsIgnoreCase("yaml_literal") && !next.getDefinition().equalsIgnoreCase("yaml_quote")) {
          throw new GrammarException(current.getLineNumber(), line.toString() + current.getValue());
        } else {
          sequence = true;
        }
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
          continue;
        } else if (key != null) {
          YamlNode parent = getParent(nodes, key);
          final String nodeStr = (parent == null)? key.getValue() : parent.getNode() + "." + key.getValue();

          if(previous != null && previous.getLineNumber() < current.getLineNumber()) {

            //System.out.println("In new if clause fucker");
            YamlNode node = new YamlNode(parent, key.getIndentation(), previous.getLineNumber(), line.toString(), comments, key.getValue(), nodeStr, values);
            nodes.add(node);
            //System.out.println("Added node: " + node.toString());
            comments = new LinkedList<>();
            values = new LinkedList<>();
            line.setLength(0);

            key = current;
            line.append(current.getValue());
            continue;
          }
          if(next != null && next.getLineNumber() == current.getLineNumber() && next.getDefinition().equalsIgnoreCase("yaml_sequence")) {
            values.add(current.getValue() + "-");
            skip = true;
          } else {
            values.add(current.getValue());
          }
          YamlNode node = new YamlNode(parent, key.getIndentation(), current.getLineNumber(), line.toString(), comments, key.getValue(), nodeStr, values);
          nodes.add(node);
          //System.out.println("Added node: " + node.toString());
          comments = new LinkedList<>();
          key = null;
          line.setLength(0);
          values = new LinkedList<>();
        } else {
          key = current;
          line.append(current.getValue());
        }
      }

    }
    return nodes;
  }

  private YamlNode getParent(LinkedList<YamlNode> nodes, Token key) {
    YamlNode parent = null;
    if(nodes.size() > 0) {
      if(nodes.getLast().getIndentation() == key.getIndentation()) {
        System.out.println("Indent == ");
        parent = nodes.getLast().getParent();
      } else if(nodes.getLast().getIndentation() < key.getIndentation()) {
        parent = nodes.getLast();
      } else {
        YamlNode node = nodes.getLast();
        while((node = node.getParent()) != null) {
          if(node.getIndentation() < key.getIndentation()) {
            parent = node;
            break;
          }
        }
      }
    }
    return parent;
  }
}
