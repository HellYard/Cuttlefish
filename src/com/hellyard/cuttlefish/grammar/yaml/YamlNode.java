package com.hellyard.cuttlefish.grammar.yaml;

import com.hellyard.cuttlefish.api.grammar.GrammarObject;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 * License: http://creativecommons.org/licenses/by-nc-nd/4.0/
 */
public class YamlNode implements GrammarObject {

  private final YamlNode parent;
  private final int indentation;
  private final int lineNumber;
  private final String line;
  private LinkedList<String> comments;
  private String key;
  private final String node;
  private List<YamlValue> newValues;

  private boolean sequence = false;
  private boolean shorthand = false;
  private String shortCharacters = "[]";

  public YamlNode(YamlNode parent, int indentation, int lineNumber, String line, final LinkedList<String> comments, String key, final String node) {
    this.parent = parent;
    this.indentation = indentation;
    this.lineNumber = lineNumber;
    this.line = line;
    this.comments = comments;
    this.key = key;
    this.node = node;
  }

  public YamlNode(YamlNode parent, int indentation, int lineNumber, String line, final LinkedList<String> comments, String key, final String node, List<YamlValue> newValues) {
    this.parent = parent;
    this.indentation = indentation;
    this.lineNumber = lineNumber;
    this.line = line;
    this.comments = comments;
    this.key = key;
    this.node = node;
    this.newValues = newValues;
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

  public String getNode() {
    return node;
  }

  public boolean isSequence() {
    return sequence;
  }

  public void setSequence(boolean sequence) {
    this.sequence = sequence;
  }

  public boolean isShorthand() {
    return shorthand;
  }

  public void setShorthand(boolean shorthand) {
    this.shorthand = shorthand;
  }

  public String getShortCharacters() {
    return shortCharacters;
  }

  public void setShortCharacters(String shortCharacters) {
    if(shortCharacters.length() > 1) {
      this.shortCharacters = shortCharacters;
    }
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
    return key;
  }

  public List<YamlValue> getValues() {
    return newValues;
  }

  public void setValues(List<YamlValue> newValues) {
    this.newValues = newValues;
  }

  @Override
  public boolean equals(Object obj) {
    if(obj == this) return true;
    if(!(obj instanceof YamlNode)) return false;

    return node.equalsIgnoreCase(((YamlNode)obj).node);
  }

  @Override
  public int hashCode() {
    return node.hashCode();
  }
}
