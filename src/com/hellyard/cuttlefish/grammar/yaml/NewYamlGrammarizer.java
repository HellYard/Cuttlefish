package com.hellyard.cuttlefish.grammar.yaml;

import com.hellyard.cuttlefish.api.grammar.GrammarObject;
import com.hellyard.cuttlefish.api.grammar.Grammarizer;
import com.hellyard.cuttlefish.api.token.Token;
import com.hellyard.cuttlefish.exception.GrammarException;
import com.hellyard.cuttlefish.grammar.yaml.rules.CommentRule;
import com.hellyard.cuttlefish.grammar.yaml.rules.GrammarizerRule;
import com.hellyard.cuttlefish.grammar.yaml.rules.LiteralRule;
import com.hellyard.cuttlefish.grammar.yaml.rules.QuoteRule;
import com.hellyard.cuttlefish.grammar.yaml.rules.QuotedRule;
import com.hellyard.cuttlefish.grammar.yaml.rules.SeparatorRule;
import com.hellyard.cuttlefish.grammar.yaml.rules.SequenceRule;
import com.hellyard.cuttlefish.grammar.yaml.rules.ShortEndRule;
import com.hellyard.cuttlefish.grammar.yaml.rules.ShortSeparatorRule;
import com.hellyard.cuttlefish.grammar.yaml.rules.ShortStartRule;
import com.hellyard.cuttlefish.grammar.yaml.rules.ShortedRule;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;


/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 * License: http://creativecommons.org/licenses/by-nc-nd/4.0/
 */
public class NewYamlGrammarizer implements Grammarizer {
  LinkedList<YamlNode> nodes = new LinkedList<>();

  @Override
  public String name() {
    return "YAML";
  }

  @Override
  public LinkedList<? extends GrammarObject> grammarize(final LinkedList<Token> tokens) throws GrammarException {

    LinkedList<GrammarizerRule> rules = new LinkedList<>();
    rules.add(new QuoteRule());
    rules.add(new QuotedRule());
    rules.add(new ShortStartRule());
    rules.add(new ShortSeparatorRule());
    rules.add(new ShortEndRule());
    rules.add(new ShortedRule());
    rules.add(new CommentRule());
    rules.add(new SequenceRule());
    rules.add(new SeparatorRule());
    rules.add(new LiteralRule());

    LinkedList<String> nodeComments = new LinkedList<>();
    LinkedList<String> commentBlock = new LinkedList<>();
    Token key = null;

    LinkedList<YamlValue> values = new LinkedList<>();

    Map<String, String> variables = new HashMap<>();
    initVariables(variables);
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

      if(key == null) variables.remove("inShort");

      if(previous == null || previous.getLineNumber() < current.getLineNumber()) variables.put("line", "");

      // Get Tokens
      System.out.println("--------------");
      System.out.println("-----");
      System.out.println("Token Line: {" + current.getLineNumber() + "}");
      System.out.println("Token Indent: {" + current.getIndentation() + "}");
      System.out.println("Token Def: {" + current.getDefinition() + "}");
      System.out.println("Token Value: {" + current.getValue() + "}");
      System.out.println("Current Line: {" + variables.get("line") + "}");
      System.out.println("-----");
      System.out.println("--------------");
      // Filter out nodes

      for(GrammarizerRule rule : rules) {
        System.out.println("rule: " + rule.getDefinition());

        if(rule.getDefinition().equalsIgnoreCase("*") || current.getDefinition().equalsIgnoreCase(rule.getDefinition())) {
          Object result = rule.handle(key, current, next, previous, variables, nodeComments, commentBlock, values);

          if(result instanceof RuleResult && ((RuleResult)result).key != null) key = ((RuleResult)result).key;

          System.out.println("Key == null?" + (key == null));

          boolean complete = (result instanceof RuleResult)? ((RuleResult)result).complete : (Boolean)result;
          if(complete) {
            final YamlNode parent = getParent(nodes, key);
            final String nodeStr = (parent == null)?
                key.getValue()
                : parent.getNode() + "." + key.getValue();
            YamlNode node = new YamlNode(parent, key.getIndentation(), current.getLineNumber(), variables.get("line"), nodeComments, key.getValue(), nodeStr, values);
            System.out.println("Added Node: " + node.toString());

            node.setSequence(variables.containsKey("containsSequence"));
            nodes.add(node);
            nodeComments.clear();
            commentBlock.clear();
            values.clear();
            key = null;
            initVariables(variables);
          }

          if(rule.isSkip()) break;
        }
      }

    }
    return nodes;
  }

  private void initVariables(Map<String, String> variables) {
    variables.clear();
    variables.put("line", "");
    variables.put("quotedValue", "");
    variables.put("shortValue", "");
    variables.put("shortChars", "[]{}");
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
