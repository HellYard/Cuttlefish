package com.hellyard.cuttlefish.composer.properties;

import com.hellyard.cuttlefish.api.composer.Composer;
import com.hellyard.cuttlefish.api.grammar.GrammarObject;

import java.io.File;
import java.util.LinkedList;

public class PropertiesComposer implements Composer {
  @Override
  public boolean compose(File file, LinkedList<? extends GrammarObject> objects) {
    return false;
  }
}
