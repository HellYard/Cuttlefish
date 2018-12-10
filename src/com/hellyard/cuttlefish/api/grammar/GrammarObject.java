package com.hellyard.cuttlefish.api.grammar;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 *
 * This work is licensed under the GNU Affero General Public License Version 3. To view a copy of
 * this license, visit https://www.gnu.org/licenses/agpl-3.0.html.
 */
public interface GrammarObject {
  GrammarObject getParent();
  int getLineNumber();
  String getLine();
}