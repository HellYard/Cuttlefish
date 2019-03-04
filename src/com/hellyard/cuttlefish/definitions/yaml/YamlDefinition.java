package com.hellyard.cuttlefish.definitions.yaml;

import com.hellyard.cuttlefish.api.definition.Definition;
import com.hellyard.cuttlefish.api.token.Token;
import com.hellyard.cuttlefish.grammar.yaml.YamlValue;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 * License: http://creativecommons.org/licenses/by-nc-nd/4.0/
 */
public abstract class YamlDefinition extends Definition {

  public YamlDefinition(String name, Pattern regex) {
    super(name, regex);
  }

  public abstract boolean handle(Token key, Token current, Token next, Token last, Map<String, String> variables, List<String> commentBlock, List<YamlValue> values);
}
