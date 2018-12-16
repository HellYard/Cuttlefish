package com.hellyard.cuttlefish;

import com.hellyard.cuttlefish.api.definition.Definition;

import java.io.File;
import java.util.LinkedHashMap;

public class CuttlefishBuilder {
  LinkedHashMap<String, Definition> definitionsMap = new LinkedHashMap<>();
  private File file;
  private String configurationType;

  public CuttlefishBuilder(File file, String configuriatonType) {
    if (file == null) {
      throw new NullPointerException("File cannot be null");
    }
    this.file = file;
    if (configuriatonType == null) {
      throw new NullPointerException("Configuration type cannot be null");
    }
    this.configurationType = configuriatonType;
  }

  public Cuttlefish build() {
    return new Cuttlefish(file, configurationType);
  }
}
