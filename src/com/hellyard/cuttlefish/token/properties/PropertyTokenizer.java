package com.hellyard.cuttlefish.token.properties;

import com.hellyard.cuttlefish.api.definition.Definition;
import com.hellyard.cuttlefish.api.token.Token;
import com.hellyard.cuttlefish.api.token.Tokenizer;

import java.io.File;
import java.util.LinkedList;

public class PropertyTokenizer implements Tokenizer {
  @Override
  public String name() {
    return "properties";
  }

  @Override
  public LinkedList<Token> tokenize(File file, LinkedList<Definition> definitions) {
    return null;
  }
}
