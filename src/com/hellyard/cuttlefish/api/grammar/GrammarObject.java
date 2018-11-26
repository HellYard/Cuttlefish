package com.hellyard.cuttlefish.api.grammar;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 *
 * This work is licensed under the GNU Affero General Public License Version 3. To view a copy of
 * this license, visit https://www.gnu.org/licenses/agpl-3.0.html.
 */
public class GrammarObject {

  private final GrammarObject parent;
  private final int line;
  private final String literal;

  public GrammarObject(GrammarObject parent, int line, String literal) {
    this.parent = parent;
    this.line = line;
    this.literal = literal;
  }

  public GrammarObject getParent() {
    return parent;
  }

  public int getLine() {
    return line;
  }

  public String getLiteral() {
    return literal;
  }
}