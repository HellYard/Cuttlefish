package com.hellyard.cuttlefish.grammar.properties;

import com.hellyard.cuttlefish.api.grammar.GrammarObject;

import java.util.LinkedList;

public class PropertyNode implements GrammarObject {
  private final PropertyNode parent;
  private final int lineNumber;
  private final String line;
  private LinkedList<String> comments;
  private String key;
  private String value;

  public PropertyNode(PropertyNode parent, int lineNumber, String line, final LinkedList<String> comments, String key, String value) {
    this.parent = parent;
    this.lineNumber = lineNumber;
    this.line = line;
    this.comments = comments;
    this.key = key;
    this.value = value;
  }

  public LinkedList<String> getComments() {
    return comments;
  }

  public void setComments(LinkedList<String> comments) {
    this.comments = comments;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public GrammarObject getParent() {
    return parent;
  }

  @Override
  public int getLineNumber() {
    return lineNumber;
  }

  @Override
  public String getLine() {
    return line;
  }

  @Override
  public String toString() {
    return key + value;
  }
}
