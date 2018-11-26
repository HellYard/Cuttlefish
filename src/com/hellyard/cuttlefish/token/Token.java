package com.hellyard.cuttlefish.token;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 *
 * This work is licensed under the GNU Affero General Public License Version 3.
 * To view a copy of this license, visit https://www.gnu.org/licenses/agpl-3.0.html.
 */
public class Token {

  private final int lineNumber;
  private final int indentation;
  private final String definition;
  private final String value;

  public Token(int lineNumber, int indentation, String definition, String value) {
    this.lineNumber = lineNumber;
    this.indentation = indentation;
    this.definition = definition;
    this.value = value;
  }

  public int getIndentation() {
    return indentation;
  }

  public String getDefinition() {
    return definition;
  }

  public String getValue() {
    return value;
  }
}