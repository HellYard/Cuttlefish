package com.hellyard.cuttlefish.definitions.yaml;

import com.hellyard.cuttlefish.api.definition.Definition;

import java.util.regex.Pattern;

public class ListSeparatorDefinition extends Definition {
  public ListSeparatorDefinition() {
    super("yaml_list_separator", Pattern.compile(Pattern.quote(",")));
  }
}
