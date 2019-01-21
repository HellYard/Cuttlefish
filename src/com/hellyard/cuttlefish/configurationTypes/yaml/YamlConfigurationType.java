package com.hellyard.cuttlefish.configurationTypes.yaml;

import com.hellyard.cuttlefish.api.composer.Composer;
import com.hellyard.cuttlefish.api.configurationtype.ConfigurationType;
import com.hellyard.cuttlefish.api.definition.Definition;
import com.hellyard.cuttlefish.api.definition.DefinitionGroup;
import com.hellyard.cuttlefish.api.grammar.Grammarizer;
import com.hellyard.cuttlefish.api.token.Tokenizer;
import com.hellyard.cuttlefish.composer.yaml.YamlComposer;
import com.hellyard.cuttlefish.definitions.yaml.*;
import com.hellyard.cuttlefish.grammar.yaml.YamlGrammarizer;
import com.hellyard.cuttlefish.token.yaml.YamlTokenizer;

import java.util.LinkedHashMap;

public class YamlConfigurationType implements ConfigurationType {

  @Override
  public String name() {
    return "yaml";
  }

  @Override
  public LinkedHashMap<String, Definition> getDefinitions() {
    DefinitionGroup definitionGroup = new DefinitionGroup("yaml");
    definitionGroup.addDefinition(
            new YamlCommentDefinition(),
            new YamlSequenceDefinition(),
            new YamlQuoteDefinition(),
            new YamlShorthandStartDefinition(),
            new YamlShorthandSeparatorDefinition(),
            new YamlShorthandEndDefinition(),
            new YamlLiteralDefinition(),
            new YamlMapDefinition(),
            new YamlSeparatorDefinition());
    return definitionGroup.getDefinitions();
  }

  @Override
  public Tokenizer getTokenizer() {
    return new YamlTokenizer();
  }

  @Override
  public Grammarizer getGrammarizer() {
    return new YamlGrammarizer();
  }

  @Override
  public Composer getComposer() {
    return new YamlComposer();
  }
}
