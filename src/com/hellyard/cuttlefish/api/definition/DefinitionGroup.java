package com.hellyard.cuttlefish.api.definition;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by creatorfromhell.
 * <p>
 * Cuttlefish YAML Parser
 * <p>
 * This work is licensed under the GNU Affero General Public License Version 3. To view a copy of
 * this license, visit https://www.gnu.org/licenses/agpl-3.0.html.
 */
public class DefinitionGroup {

  private final String name;
  private LinkedHashMap<String, Definition> definitions = new LinkedHashMap<>();

  public DefinitionGroup(String name) {
    this.name = name;
  }

  public LinkedHashMap<String, Definition> getDefinitions() {
    return definitions;
  }

  public LinkedList<Definition> getDefinitionsValues() {
    return new LinkedList<>(definitions.values());
  }

  public void addDefinition(Definition... definitions) {
    for(Definition definition : definitions) {
      this.definitions.put(definition.getName(), definition);
    }
  }

  public String getName() {
    return name;
  }
}