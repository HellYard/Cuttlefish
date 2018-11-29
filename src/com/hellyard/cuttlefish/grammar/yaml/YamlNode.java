package com.hellyard.cuttlefish.grammar.yaml;

import com.hellyard.cuttlefish.api.grammar.GrammarObject;

import java.util.LinkedList;

public class YamlNode implements GrammarObject {

  private final YamlNode parent;
  private final int line;
  private final String literal;
  private LinkedList<String> comments;
  private String key;
  private LinkedList<String> values;

  public YamlNode(YamlNode parent, int line, String literal) {
    this.parent = parent;
    this.line = line;
    this.literal = literal;
  }

  public LinkedList<String> getComments() {
    return comments;
  }

  public String getKey() {
    return key;
  }

  public LinkedList<String> getValues() {
    return values;
  }

  public void setComments(LinkedList<String> comments) {
    this.comments = comments;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setValues(LinkedList<String> values) {
    this.values = values;
  }

    @Override
    public YamlNode getParent() {
        return parent;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public String getLiteral() {
        return literal;
    }
}
