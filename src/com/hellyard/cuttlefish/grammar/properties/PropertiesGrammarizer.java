package com.hellyard.cuttlefish.grammar.properties;

import com.hellyard.cuttlefish.api.grammar.GrammarObject;
import com.hellyard.cuttlefish.api.grammar.Grammarizer;
import com.hellyard.cuttlefish.api.token.Token;
import com.hellyard.cuttlefish.exception.GrammarException;

import java.util.LinkedList;

public class PropertiesGrammarizer implements Grammarizer {
  @Override
  public String name() {
    return "properties";
  }

  @Override
  public LinkedList<? extends GrammarObject> grammarize(LinkedList<Token> tokens) throws GrammarException {
    return null;
  }
}
