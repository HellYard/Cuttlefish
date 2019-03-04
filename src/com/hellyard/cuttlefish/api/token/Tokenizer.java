package com.hellyard.cuttlefish.api.token;

import com.hellyard.cuttlefish.api.definition.Definition;

import java.io.Reader;
import java.util.LinkedList;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 * License: http://creativecommons.org/licenses/by-nc-nd/4.0/
 */
public interface Tokenizer {

  String name();

  LinkedList<Token> tokenize(Reader reader, LinkedList<Definition> definitions);
}