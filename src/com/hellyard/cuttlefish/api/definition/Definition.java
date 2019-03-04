package com.hellyard.cuttlefish.api.definition;

import java.util.regex.Pattern;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 * License: http://creativecommons.org/licenses/by-nc-nd/4.0/
 *
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
}