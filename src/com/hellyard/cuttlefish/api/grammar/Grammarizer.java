package com.hellyard.cuttlefish.api.grammar;

import com.hellyard.cuttlefish.exception.GrammarException;
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
public interface Grammarizer {

  String name();

  LinkedList<GrammarObject> grammarize(LinkedList<Token> tokens) throws GrammarException;
}