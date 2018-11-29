package com.hellyard.cuttlefish.iterator;

import com.hellyard.cuttlefish.api.token.Token;

import java.util.LinkedList;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 *
 * This work is licensed under the GNU Affero General Public License Version 3. To view a copy of
 * this license, visit https://www.gnu.org/licenses/agpl-3.0.html.
 */
public class TokenList extends LinkedList<Token> {

  public int getMod() {
    return modCount;
  }

  public TokenIterator iterator() {
    return new TokenIterator(this);
  }
}