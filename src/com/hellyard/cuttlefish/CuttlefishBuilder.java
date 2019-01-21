package com.hellyard.cuttlefish;

import com.hellyard.cuttlefish.api.definition.Definition;
import com.hellyard.cuttlefish.api.definition.DefinitionGroup;
import com.hellyard.cuttlefish.api.grammar.Grammarizer;
import com.hellyard.cuttlefish.api.token.Tokenizer;
import com.hellyard.cuttlefish.definitions.yaml.CommentDefinition;
import com.hellyard.cuttlefish.definitions.yaml.LiteralDefinition;
import com.hellyard.cuttlefish.definitions.yaml.MapDefinition;
import com.hellyard.cuttlefish.definitions.yaml.QuoteDefinition;
import com.hellyard.cuttlefish.definitions.yaml.SeparatorDefinition;
import com.hellyard.cuttlefish.definitions.yaml.SequenceDefinition;
import com.hellyard.cuttlefish.definitions.yaml.ShorthandEndDefinition;
import com.hellyard.cuttlefish.definitions.yaml.ShorthandSeparatorDefinition;
import com.hellyard.cuttlefish.definitions.yaml.ShorthandStartDefinition;
import com.hellyard.cuttlefish.grammar.yaml.YamlGrammarizer;
import com.hellyard.cuttlefish.token.yaml.YamlTokenizer;

import java.io.Reader;
import java.util.LinkedHashMap;

public class CuttlefishBuilder {
  LinkedHashMap<String, Definition> definitionsMap = new LinkedHashMap<>();
  private Reader reader;
  private Tokenizer tokenizer;
  private Grammarizer grammarizer;

  public CuttlefishBuilder(Reader reader, String presetType) {
    if (reader == null) {
      throw new NullPointerException("File Reader cannot be null");
    }
    this.reader = reader;
    if (presetType != null && presetType.equalsIgnoreCase("yaml")) {
      this.tokenizer = new YamlTokenizer();
      this.grammarizer = new YamlGrammarizer();
      DefinitionGroup definitionGroup = new DefinitionGroup("yaml");
      definitionGroup.addDefinition(
              new CommentDefinition(),
              new SequenceDefinition(),
              new QuoteDefinition(),
              new ShorthandStartDefinition(),
              new ShorthandSeparatorDefinition(),
              new ShorthandEndDefinition(),
              new LiteralDefinition(),
              //new LiteralSeparatorDefinition(),
              new MapDefinition(),
              new SeparatorDefinition());
      this.definitionsMap.putAll(definitionGroup.getDefinitions());
    }
  }

  public Cuttlefish build() {
    return new Cuttlefish(reader, tokenizer, grammarizer, definitionsMap);
  }
}
