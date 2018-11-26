package com.hellyard.cuttlefish;

import com.hellyard.cuttlefish.api.Definition;

import java.util.HashMap;

public class ParserBuilder {
    private HashMap<String, Definition> definitionsMap = new HashMap<>();

    /**
     * Adds a definition to the parser.
     *
     * @param definition is a {@link Definition}
     * @return {@link ParserBuilder}
     */
    public ParserBuilder addDefinition(Definition definition) {
        definitionsMap.put(definition.getName(), definition);
        return this;
    }

    /**
     * Builds the parser
     *
     * @return {@link Parser}
     */
    public Parser build() {
        return new Parser(definitionsMap);
    }
}
