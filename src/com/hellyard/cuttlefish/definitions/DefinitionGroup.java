package com.hellyard.cuttlefish.definitions;

import com.hellyard.cuttlefish.api.Definition;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 *
 * This work is licensed under the GNU Affero General Public License Version 3. To view a copy of
 * this license, visit https://www.gnu.org/licenses/agpl-3.0.html.
 */
public class DefinitionGroup {

  private Map<String, Definition> definitions = new HashMap<>();
  private final String name;

  public DefinitionGroup(String name) {
    this.name = name;
  }

  public Map<String, Definition> getDefinitions() {
    return definitions;
  }

  public String getName() {
    return name;
  }
}