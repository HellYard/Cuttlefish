package com.hellyard.cuttlefish;

import com.hellyard.cuttlefish.api.definition.Definition;
import com.hellyard.cuttlefish.api.grammar.GrammarObject;
import com.hellyard.cuttlefish.api.grammar.Grammarizer;
import com.hellyard.cuttlefish.api.token.Token;
import com.hellyard.cuttlefish.api.token.Tokenizer;
import com.hellyard.cuttlefish.exception.GrammarException;

import java.io.File;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedList;

public class Cuttlefish {
  private HashMap<String, Definition> definitionHashMap;
  private Reader reader;
  private Grammarizer grammarizer;
  private Tokenizer tokenizer;
  private LinkedList<Token> tokenList;
  private LinkedList<? extends GrammarObject> nodes;

  Cuttlefish(
          Reader reader,
          Tokenizer tokenizer,
          Grammarizer grammarizer,
          HashMap<String, Definition> definitionHashMap
  ){
    this.reader = reader;
    this.tokenizer = tokenizer;
    this.grammarizer = grammarizer;
    this.definitionHashMap = definitionHashMap;
    parseNodes();
  }

  private void parseNodes() {
    tokenList = tokenizer.tokenize(reader, new LinkedList<>(definitionHashMap.values()));
    try {
      nodes = grammarizer.grammarize(tokenList);
    } catch (GrammarException e) {
      e.printStackTrace();
    }
  }

  public Reader getReader() {
    return reader;
  }

  public Tokenizer getTokenizer() {
    return tokenizer;
  }

  public Grammarizer getGrammarizer() {
    return grammarizer;
  }

  public HashMap<String, Definition> getDefinitionHashMap() {
    return definitionHashMap;
  }

  public LinkedList<Token> getTokenList() {
    return tokenList;
  }

  public LinkedList<? extends GrammarObject> getNodes() {
    return nodes;
  }
}
