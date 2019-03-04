package com.hellyard.cuttlefish.api.composer;

import com.hellyard.cuttlefish.api.grammar.GrammarObject;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 * License: http://creativecommons.org/licenses/by-nc-nd/4.0/
 */
public interface Composer {

  boolean compose(File file, LinkedList<? extends GrammarObject> objects);
}