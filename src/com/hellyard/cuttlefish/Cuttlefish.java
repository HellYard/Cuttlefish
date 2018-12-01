package com.hellyard.cuttlefish;

import com.hellyard.cuttlefish.api.definition.Definition;
import com.hellyard.cuttlefish.api.grammar.GrammarObject;
import com.hellyard.cuttlefish.api.grammar.Grammarizer;
import com.hellyard.cuttlefish.api.token.Tokenizer;
import com.hellyard.cuttlefish.exception.GrammarException;
import com.hellyard.cuttlefish.iterator.TokenList;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

class Cuttlefish {
  private HashMap<String, Definition> definitionHashMap;
  private File file;
  private Grammarizer grammarizer;
  private Tokenizer tokenizer;
  private TokenList tokenList;
  private LinkedList<? extends GrammarObject> nodes;

  Cuttlefish(
          File file,
          Tokenizer tokenizer,
          Grammarizer grammarizer,
          HashMap<String, Definition> definitionHashMap
  ) {
    this.file = file;
    this.tokenizer = tokenizer;
    this.grammarizer = grammarizer;
    this.definitionHashMap = definitionHashMap;
    parseNodes();
  }

  private void parseNodes() {
    tokenList = tokenizer.tokenize(file, new LinkedList<>(definitionHashMap.values()));
    try {
      nodes = grammarizer.grammarize(tokenList);
    } catch (GrammarException e) {
      e.printStackTrace();
    }
  }

  public File getFile() {
    return file;
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

  public TokenList getTokenList() {
    return tokenList;
  }

  public LinkedList<? extends GrammarObject> getNodes() {
    return nodes;
  }
}
