package com.hellyard.cuttlefish.api.definition;

import java.util.regex.Pattern;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 *
 * This work is licensed under the GNU Affero General Public License Version 3.
 * To view a copy of this license, visit https://www.gnu.org/licenses/agpl-3.0.html.
 */
public class Definition {

  private final String name;
  private final Pattern regex;

  public Definition(final String name, final Pattern regex) {
    this.name = name;
    this.regex = regex;
  }

  public String getName() {
    return name;
  }

  public Pattern getRegex() {
    return regex;
  }
}