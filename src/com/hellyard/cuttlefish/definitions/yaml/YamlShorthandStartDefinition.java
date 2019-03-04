package com.hellyard.cuttlefish.definitions.yaml;

import com.hellyard.cuttlefish.api.definition.Definition;

import java.util.regex.Pattern;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 * License: http://creativecommons.org/licenses/by-nc-nd/4.0/
 */
public class YamlShorthandStartDefinition extends Definition {
  public YamlShorthandStartDefinition() {
    super("yaml_shorthand_start", Pattern.compile("^\\s*[{\\[]"));
  }
}