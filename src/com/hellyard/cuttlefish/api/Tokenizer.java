package com.hellyard.cuttlefish.api;

import com.hellyard.cuttlefish.token.Token;

import java.io.File;
import java.util.List;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 *
 * This work is licensed under the GNU Affero General Public License Version 3. To view a copy of
 * this license, visit https://www.gnu.org/licenses/agpl-3.0.html.
 */
public interface Tokenizer {

  List<Token> tokenize(File file, List<Definition> definitions);
}