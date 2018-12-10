package com.hellyard.cuttlefish;

import com.hellyard.cuttlefish.api.definition.Definition;
import com.hellyard.cuttlefish.api.definition.DefinitionGroup;
import com.hellyard.cuttlefish.api.grammar.Grammarizer;
import com.hellyard.cuttlefish.api.token.Tokenizer;
import com.hellyard.cuttlefish.definitions.yaml.*;
import com.hellyard.cuttlefish.grammar.yaml.YamlGrammarizer;
import com.hellyard.cuttlefish.token.yaml.YamlTokenizer;

import java.io.File;
import java.util.LinkedHashMap;

public class CuttlefishBuilder {
  LinkedHashMap<String, Definition> definitionsMap = new LinkedHashMap<>();
  private File file;
  private Tokenizer tokenizer;
  private Grammarizer grammarizer;

  public CuttlefishBuilder(File file, String presetType) {
    if (file == null) {
      throw new NullPointerException("File cannot be null");
    }
    this.file = file;
    if (presetType != null && presetType.equalsIgnoreCase("yaml")) {
      this.tokenizer = new YamlTokenizer();
      this.grammarizer = new YamlGrammarizer();
      DefinitionGroup definitionGroup = new DefinitionGroup("yaml");
      definitionGroup.addDefinition(
              new CommentDefinition(),
              new LiteralDefinition(),
              new MapDefinition(),
              new QuoteDefinition(),
              new SeparatorDefinition(),
              new SequenceDefinition());
      this.definitionsMap.putAll(definitionGroup.getDefinitions());
    }
  }

  public Cuttlefish build() {
    return new Cuttlefish(file, tokenizer, grammarizer, definitionsMap);
  }
}
