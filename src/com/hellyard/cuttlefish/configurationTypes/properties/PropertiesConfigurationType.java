package com.hellyard.cuttlefish.configurationTypes.properties;

import com.hellyard.cuttlefish.api.composer.Composer;
import com.hellyard.cuttlefish.api.configurationtype.ConfigurationType;
import com.hellyard.cuttlefish.api.definition.Definition;
import com.hellyard.cuttlefish.api.definition.DefinitionGroup;
import com.hellyard.cuttlefish.api.grammar.Grammarizer;
import com.hellyard.cuttlefish.api.token.Tokenizer;
import com.hellyard.cuttlefish.composer.properties.PropertiesComposer;
import com.hellyard.cuttlefish.definitions.yaml.*;
import com.hellyard.cuttlefish.grammar.properties.PropertiesGrammarizer;
import com.hellyard.cuttlefish.token.properties.PropertyTokenizer;

import java.util.LinkedHashMap;

public class PropertiesConfigurationType implements ConfigurationType {
  @Override
  public String name() {
    return "properties";
  }

  @Override
  public LinkedHashMap<String, Definition> getDefinitions() {
    DefinitionGroup definitionGroup = new DefinitionGroup("properties");
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
    return new PropertyTokenizer();
  }

  @Override
  public Grammarizer getGrammarizer() {
    return new PropertiesGrammarizer();
  }

  @Override
  public Composer getComposer() {
    return new PropertiesComposer();
  }
}
