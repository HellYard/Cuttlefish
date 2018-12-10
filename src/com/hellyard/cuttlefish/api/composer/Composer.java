package com.hellyard.cuttlefish.api.composer;

import com.hellyard.cuttlefish.api.grammar.GrammarObject;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 *
 * This work is licensed under the GNU Affero General Public License Version 3. To view a copy of
 * this license, visit https://www.gnu.org/licenses/agpl-3.0.html.
 */
public interface Composer {

  boolean compose(File file, LinkedList<? extends GrammarObject> objects);
}