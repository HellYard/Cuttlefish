package com.hellyard.cuttlefish.api.configurationtype;

import com.hellyard.cuttlefish.api.composer.Composer;
import com.hellyard.cuttlefish.api.definition.Definition;
import com.hellyard.cuttlefish.api.grammar.Grammarizer;
import com.hellyard.cuttlefish.api.token.Tokenizer;

import java.util.LinkedHashMap;

public interface ConfigurationType {
  String name();

  LinkedHashMap<String, Definition> getDefinitions();

  Tokenizer getTokenizer();

  Grammarizer getGrammarizer();

  Composer getComposer();
}
