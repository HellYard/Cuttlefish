package com.hellyard.cuttlefish;

import com.hellyard.cuttlefish.api.definition.Definition;

import java.io.Reader;
import java.util.LinkedHashMap;

public class CuttlefishBuilder {
  LinkedHashMap<String, Definition> definitionsMap = new LinkedHashMap<>();
  private Reader reader;
  private String configurationType;

  public CuttlefishBuilder(Reader reader, String configuriatonType) {
    if (reader == null) {
      throw new NullPointerException("Reader cannot be null");
    }
    this.reader = reader;
    if (configuriatonType == null) {
      throw new NullPointerException("Configuration type cannot be null");
    }
    this.configurationType = configuriatonType;
  }

  public Cuttlefish build() {
    return new Cuttlefish(reader, configurationType);
  }
}
