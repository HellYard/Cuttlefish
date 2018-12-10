package com.hellyard.cuttlefish.grammar.yaml;

import com.hellyard.cuttlefish.api.grammar.GrammarObject;

import java.util.LinkedList;

public class YamlNode implements GrammarObject {

  private final YamlNode parent;
  private final int indentation;
  private final int lineNumber;
  private final String line;
  private LinkedList<String> comments;
  private String key;
  private LinkedList<String> values;

  public YamlNode(YamlNode parent, int indentation, int lineNumber, String line, LinkedList<String> comments, String key, LinkedList<String> values) {
    this.parent = parent;
    this.indentation = indentation;
    this.lineNumber = lineNumber;
    this.line = line;
    this.comments = comments;
    this.key = key;
    this.values = values;
  }

  public int getIndentation() {
    return indentation;
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

  public LinkedList<String> getValues() {
    return values;
  }

  public void setValues(LinkedList<String> values) {
    this.values = values;
  }

  @Override
  public YamlNode getParent() {
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
    System.out.println(key);
    return key + values.toString();
  }
}
