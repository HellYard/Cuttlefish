package com.hellyard.cuttlefish.grammar.yaml;

import com.hellyard.cuttlefish.api.grammar.GrammarObject;

import java.util.LinkedList;

public class YamlNode extends GrammarObject {
  private LinkedList<String> comments;
  private String key;
  private LinkedList<String> values;

  public YamlNode(GrammarObject parent, int line, String literal) {
    super(parent, line, literal);
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
}
