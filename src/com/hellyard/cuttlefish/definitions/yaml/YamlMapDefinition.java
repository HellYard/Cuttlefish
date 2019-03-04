package com.hellyard.cuttlefish.definitions.yaml;

import com.hellyard.cuttlefish.api.definition.Definition;

import java.util.regex.Pattern;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 * License: http://creativecommons.org/licenses/by-nc-nd/4.0/
 */
public class YamlMapDefinition extends Definition {
  public YamlMapDefinition() {
    super("yaml_map", Pattern.compile(Pattern.quote("^\\s*?")));
  }
}