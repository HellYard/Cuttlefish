package com.hellyard.cuttlefish;

import com.hellyard.cuttlefish.api.composer.Composer;
import com.hellyard.cuttlefish.api.configurationtype.ConfigurationType;
import com.hellyard.cuttlefish.api.definition.Definition;
import com.hellyard.cuttlefish.api.grammar.GrammarObject;
import com.hellyard.cuttlefish.api.grammar.Grammarizer;
import com.hellyard.cuttlefish.api.token.Token;
import com.hellyard.cuttlefish.api.token.Tokenizer;
import com.hellyard.cuttlefish.configurationTypes.properties.PropertiesConfigurationType;
import com.hellyard.cuttlefish.configurationTypes.yaml.YamlConfigurationType;
import com.hellyard.cuttlefish.exception.GrammarException;

import java.io.File;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Cuttlefish {

  private static Map<String, ConfigurationType> configurations = new HashMap<>();

  static {
    addConfiguration(new YamlConfigurationType());
    addConfiguration(new PropertiesConfigurationType());
  }

  private HashMap<String, Definition> definitionHashMap;
  private Reader reader;
  private Grammarizer grammarizer;
  private Tokenizer tokenizer;
  private Composer composer;
  private LinkedList<Token> tokenList;
  private LinkedList<? extends GrammarObject> nodes;

  Cuttlefish(Reader reader, String configurationType) {
    ConfigurationType type = null;
    if (configurations.containsKey(configurationType)) {
      type = configurations.get(configurationType);
    }
    if (type == null) {
      throw new IllegalArgumentException(configurationType + " is not a valid configuration type!");
    }
    this.reader = reader;
    tokenizer = type.getTokenizer();
    grammarizer = type.getGrammarizer();
    composer = type.getComposer();
    this.definitionHashMap = type.getDefinitions();
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



  public static void addConfiguration(ConfigurationType... types) {
    for(ConfigurationType configurationType : types) {
      configurations.put(configurationType.name(), configurationType);
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

  public void compose(LinkedList<? extends GrammarObject> objects, File file) {
    composer.compose(file, objects);
  }
}
