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

    for(ListIterator<Token> it = tokens.listIterator(); it.hasNext(); ) {
      Token current = it.next();
      // Get Tokens
      System.out.println("--------------");
      System.out.println("-----");
      System.out.println("Token Line: {" + current.getLineNumber() + "}");
      System.out.println("Token Indent: {" + current.getIndentation() + "}");
      System.out.println("Token Def: {" + current.getDefinition() + "}");
      System.out.println("Token Value: {" + current.getValue() + "}");
      System.out.println("-----");
      System.out.println("--------------");
      // Filter out nodes
      if (current.getDefinition().equals("yaml_comment") || current.getDefinition().equals("empty_line")) {
        comments.add(current.getValue());
        continue;
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
        if (key != null) {
          values.add(current.getValue());
          YamlNode node = new YamlNode(null, current.getLineNumber(), line.toString(), comments, key, values);
          comments = new LinkedList<>();
          key = null;
          values = new LinkedList<>();
          nodes.add(node);
          continue;
        } else {
          key = current.getValue();
          line.append(current.getValue());
          continue;
        }
      }

    }
    return nodes;
  }
}
