package com.hellyard.cuttlefish;

import com.hellyard.cuttlefish.api.definition.Definition;
import com.hellyard.cuttlefish.definitions.DefinitionGroup;
import com.hellyard.cuttlefish.util.Validation;

import java.io.File;
import java.util.HashMap;

class Parser {
  private HashMap<String, DefinitionGroup> definitionsMap;

  /**
   * Only constructable via {@link ParserBuilder}
   *
   * @param definitionsMap is a {@link HashMap} containing {@link String} as key and {@link Definition} as value
   */
  Parser(HashMap<String, DefinitionGroup> definitionsMap) {
    this.definitionsMap = definitionsMap;
  }

  public void parse(File file, String group) {
    Validation.notNull(file, "File cannot be null");
    Validation.notNull(file.getParentFile(), "Parent cannot be null");
    // TODO: Parse file.
  }
}
