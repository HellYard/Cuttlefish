package com.hellyard.cuttlefish.definitions.yaml;

import com.hellyard.cuttlefish.api.definition.Definition;

import java.util.regex.Pattern;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 * License: http://creativecommons.org/licenses/by-nc-nd/4.0/
 */
public class YamlShorthandEndDefinition extends Definition {
  public YamlShorthandEndDefinition() {
    super("yaml_shorthand_end", Pattern.compile("^\\s*[}\\]]"));
  }
}