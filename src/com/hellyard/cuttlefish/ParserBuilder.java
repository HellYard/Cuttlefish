package com.hellyard.cuttlefish;

import com.hellyard.cuttlefish.api.definition.Definition;
import com.hellyard.cuttlefish.definitions.DefinitionGroup;

import java.util.HashMap;

public class ParserBuilder {
  private HashMap<String, DefinitionGroup> definitionsMap = new HashMap<>();

  /**
   * Adds a definition to the parser.
   *
   * @param group The name of the {@link DefinitionGroup} this definition should be part of.
   * @param definition is a {@link Definition}
   * @return {@link ParserBuilder}
   */
  public ParserBuilder addDefinition(String group, Definition definition) {
    DefinitionGroup defGroup = (definitionsMap.containsKey(group))? definitionsMap.get(group) : new DefinitionGroup(group);
    defGroup.getDefinitions().put(definition.getName(), definition);

    definitionsMap.put(group, defGroup);
    return this;
  }

  /**
   * Adds a definition group to the parser.
   * 
   * @param group The {@link DefinitionGroup} to add to the parser.
   * @return {@link ParserBuilder}
   */
  public ParserBuilder addDefinitionGroup(DefinitionGroup group) {
    if(definitionsMap.containsKey(group.getName())) {
      group.getDefinitions().putAll(definitionsMap.get(group.getName()).getDefinitions());
    }
    definitionsMap.put(group.getName(), group);
    return this;
  }

  /**
   * Builds the parser
   *
   * @return {@link Parser}
   */
  public Parser build() {
    return new Parser(definitionsMap);
  }
}
