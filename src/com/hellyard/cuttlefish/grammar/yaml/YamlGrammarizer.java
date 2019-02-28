package com.hellyard.cuttlefish.grammar.yaml;

import com.hellyard.cuttlefish.api.grammar.GrammarObject;
import com.hellyard.cuttlefish.api.grammar.Grammarizer;
import com.hellyard.cuttlefish.api.token.Token;
import com.hellyard.cuttlefish.exception.GrammarException;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class YamlGrammarizer implements Grammarizer {
  LinkedList<YamlNode> nodes = new LinkedList<>();

  @Override
  public String name() {
    return "YAML";
  }

  @Override
  public LinkedList<? extends GrammarObject> grammarize(final LinkedList<Token> tokens) throws GrammarException {
    LinkedList<String> nodeComments = new LinkedList<>();
    List<String> commentBlock = new LinkedList<>();
    Token key = null;

    LinkedList<YamlValue> newValues = new LinkedList<>();

    //LinkedList<String> values = new LinkedList<>();
    StringBuilder line = new StringBuilder();
    StringBuilder quotedValue = new StringBuilder();
    StringBuilder shortValue = new StringBuilder();
    boolean inQuote = false;
    boolean inShort = false;
    final String shortChars = "[]{}";
    String shortChar = "";
    String quoteChar = "";


    boolean containsSequence = false;
    boolean sequence = false;
    boolean skip = false;

    for(ListIterator<Token> it = tokens.listIterator(); it.hasNext();) {
      Token previous = null;
      if(it.hasPrevious()) {
        previous = tokens.get(it.previousIndex());
        ////System.out.println(previous.getDefinition());
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

      if(key == null) inShort = false;

      ////System.out.println("Type: " + current.getDefinition());
      ////System.out.println("Type: " + current.getValue());
      ////System.out.println("Quote: " + inQuote);
      ////System.out.println("Short: " + inShort);

      // Get Tokens
      ////System.out.println("--------------");
      ////System.out.println("-----");
      ////System.out.println("Token Line: {" + current.getLineNumber() + "}");
      ////System.out.println("Token Indent: {" + current.getIndentation() + "}");
      ////System.out.println("Token Def: {" + current.getDefinition() + "}");
      ////System.out.println("Token Value: {" + current.getValue() + "}");
      ////System.out.println("Current Line: {" + line.toString() + "}");
      ////System.out.println("-----");
      ////System.out.println("--------------");
      // Filter out nodes

      if(current.getDefinition().equalsIgnoreCase("yaml_quote")) {
        if(inQuote) {
          if(current.getValue().trim().equalsIgnoreCase(quoteChar.trim()) && !quotedValue.toString().endsWith("\\")) {
            ////System.out.println("Turning quotes off.");
            if(inShort) {
              shortValue.append(quotedValue.toString());
            } else {
              newValues.add(new YamlValue(commentBlock, quotedValue.toString(), "string"));
              commentBlock = new LinkedList<>();
            }

            ////System.out.println("Shortened: " + inShort);
            inQuote = false;
            quoteChar = "";
            quotedValue.setLength(0);
            line.setLength(0);
            sequence = false;

            if(!it.hasNext()) {
              final YamlNode parent = getParent(nodes, key);
              final String nodeStr = (parent == null)? key.getValue() : parent.getNode() + "." + key.getValue();
              YamlNode node = new YamlNode(parent, key.getIndentation(), current.getLineNumber(), line.toString(), nodeComments, key.getValue(), nodeStr, newValues);
              if(inShort) node.setShortCharacters(shortChar + shortChars.charAt(shortChars.indexOf(shortChar) + 1));
              node.setShorthand(inShort);
              node.setSequence(containsSequence);
              nodes.add(node);
              ////System.out.println("Added node: " + node.toString());
              ////System.out.println("Parent: " + ((parent == null)? "None" : parent.toString()));
              commentBlock = new LinkedList<>();
              nodeComments = new LinkedList<>();
              key = null;
              line.setLength(0);
              shortValue.setLength(0);
              newValues = new LinkedList<>();
            }
          } else {
            if(quotedValue.toString().endsWith("\\")) quotedValue.deleteCharAt(quotedValue.length() - 1);
            quotedValue.append(current.getValue());
          }
          continue;
        } else {
          ////System.out.println("Turning quotes on.");
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
          newValues.add(new YamlValue(commentBlock, shortValue.toString(), "string_list"));
          commentBlock = new LinkedList<>();
          shortValue.setLength(0);
          line.append(current.getValue());
          continue;
        } else {
          shortValue.append(current.getValue());
          continue;
        }
      }

      if(current.getDefinition().equalsIgnoreCase("yaml_shorthand_end")) {
        if(inShort) {
          if(shortChars.indexOf(current.getValue().trim()) == (shortChars.indexOf(shortChar) + 1)) {
            inShort = false;
            line.append(current.getValue());
            newValues.add(new YamlValue(commentBlock, shortValue.toString(), "string_list"));
            final YamlNode parent = getParent(nodes, key);
            final String nodeStr = (parent == null)? key.getValue() : parent.getNode() + "." + key.getValue();
            YamlNode node = new YamlNode(parent, key.getIndentation(), current.getLineNumber(), line.toString(), nodeComments, key.getValue(), nodeStr, newValues);
            node.setShortCharacters(shortChar + shortChars.charAt(shortChars.indexOf(shortChar) + 1));
            node.setShorthand(true);
            nodes.add(node);
            ////System.out.println("Added node: " + node.toString());
            ////System.out.println("Parent: " + ((parent == null)? "None" : parent.toString()));
            nodeComments = new LinkedList<>();
            commentBlock = new LinkedList<>();
            key = null;
            line.setLength(0);
            shortValue.setLength(0);
            newValues = new LinkedList<>();
            continue;
          }
          shortValue.append(current.getValue());
          line.append(current.getValue());
          continue;
        }
      }

      if(inShort) {
        if(!current.getValue().trim().equalsIgnoreCase("")) {
          shortValue.append(current.getValue());
        }
        continue;
      }

      if (current.getDefinition().equals("yaml_comment") || current.getDefinition().equals("empty_line")) {
        if(previous != null && key != null && !sequence) {
          final YamlNode parent = getParent(nodes, key);
          final String nodeStr = (parent == null)? key.getValue() : parent.getNode() + "." + key.getValue();
          YamlNode node = new YamlNode(parent, key.getIndentation(), current.getLineNumber(), line.toString(), nodeComments, key.getValue(), nodeStr, newValues);
          node.setSequence(containsSequence);
          nodes.add(node);
          ////System.out.println("Added node: " + node.toString());
          ////System.out.println("Parent: " + ((parent == null)? "None" : parent.toString()));
          nodeComments = new LinkedList<>();
          commentBlock = new LinkedList<>();
          key = null;
          containsSequence = false;
          line.setLength(0);
          newValues = new LinkedList<>();
        }

        if(current.getDefinition().equalsIgnoreCase("empty_line")) {
          commentBlock.add(System.lineSeparator());
        } else {
          commentBlock.add(current.getValue());
        }

        if(next != null && !next.getDefinition().equalsIgnoreCase("empty_line") && !next.getDefinition().equalsIgnoreCase("yaml_comment") && !sequence) {
          nodeComments.addAll(commentBlock);
          commentBlock = new LinkedList<>();
        }
        continue;
      }

      if(current.getDefinition().equalsIgnoreCase("yaml_sequence")) {
        if(previous == null || sequence || next == null || !next.getDefinition().equalsIgnoreCase("yaml_literal") && !next.getDefinition().equalsIgnoreCase("yaml_quote")) {
          throw new GrammarException(current.getLineNumber(), line.toString() + current.getValue());
        } else {
          containsSequence = true;
          sequence = true;
        }
      }

      if (current.getDefinition().equals("yaml_separator")) {
        ////System.out.println("Quote:" + inQuote);
        ////System.out.println("Key:" + key);
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
          newValues.add(new YamlValue(commentBlock, current.getValue().trim(), "string"));
          commentBlock = new LinkedList<>();
          continue;
        } else if (key != null) {
          YamlNode parent = getParent(nodes, key);
          final String nodeStr = (parent == null)? key.getValue() : parent.getNode() + "." + key.getValue();

          if(previous != null && previous.getLineNumber() < current.getLineNumber()) {

            //////System.out.println("In new if clause fucker");
            YamlNode node = new YamlNode(parent, key.getIndentation(), previous.getLineNumber(), line.toString(), nodeComments, key.getValue(), nodeStr, newValues);
            node.setSequence(containsSequence);
            nodes.add(node);
            //////System.out.println("Added node: " + node.toString());
            nodeComments = new LinkedList<>();
            commentBlock = new LinkedList<>();
            newValues = new LinkedList<>();
            line.setLength(0);
            containsSequence = false;
            key = current;
            line.append(current.getValue());
            continue;
          }
          if(next != null && next.getLineNumber() == current.getLineNumber() && next.getDefinition().equalsIgnoreCase("yaml_sequence")) {
            newValues.add(new YamlValue(commentBlock, current.getValue() + "-", "string"));
            commentBlock = new LinkedList<>();
            skip = true;
          } else {
            newValues.add(new YamlValue(commentBlock, current.getValue().trim(), "string"));
            commentBlock = new LinkedList<>();
          }
          YamlNode node = new YamlNode(parent, key.getIndentation(), current.getLineNumber(), line.toString(), nodeComments, key.getValue(), nodeStr, newValues);
          node.setSequence(containsSequence);
          nodes.add(node);
          //////System.out.println("Added node: " + node.toString());
          nodeComments = new LinkedList<>();
          commentBlock = new LinkedList<>();
          containsSequence = false;
          sequence = false;
          key = null;
          line.setLength(0);
          newValues = new LinkedList<>();
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
        ////System.out.println("Indent == ");
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
