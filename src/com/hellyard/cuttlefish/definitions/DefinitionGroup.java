package com.hellyard.cuttlefish.definitions;

import com.hellyard.cuttlefish.api.definition.Definition;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 *
 * This work is licensed under the GNU Affero General Public License Version 3. To view a copy of
 * this license, visit https://www.gnu.org/licenses/agpl-3.0.html.
 */
public class DefinitionGroup {

  private LinkedHashMap<String, Definition> definitions = new LinkedHashMap<>();
  private final String name;

  public DefinitionGroup(String name) {
    this.name = name;
  }

  public LinkedHashMap<String, Definition> getDefinitions() {
    return definitions;
  }

  public LinkedList<Definition> getDefinitionsValues() {
    return new LinkedList<>(definitions.values());
  }

  public void addDefinition(Definition definition) {
    definitions.put(definition.getName(), definition);
  }

  public String getName() {
    return name;
  }
}