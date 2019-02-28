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
public abstract class Definition {

  private final String name;
  private final Pattern regex;
  protected boolean once = false;

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

  public boolean isOnce() {
    return once;
  }

  //public abstract boolean handle(Token current, Token next, Token last, List<String> commentBlock, List<String> values);
}