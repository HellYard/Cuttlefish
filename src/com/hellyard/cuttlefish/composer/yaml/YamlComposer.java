package com.hellyard.cuttlefish.composer.yaml;

import com.hellyard.cuttlefish.api.composer.Composer;
import com.hellyard.cuttlefish.api.grammar.GrammarObject;
import com.hellyard.cuttlefish.grammar.yaml.YamlNode;
import com.hellyard.cuttlefish.grammar.yaml.YamlValue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.LinkedList;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 * License: http://creativecommons.org/licenses/by-nc-nd/4.0/
 */
public class YamlComposer implements Composer {
  @Override
  public boolean compose(File file, LinkedList<? extends GrammarObject> objects) {

    LinkedList<YamlNode> nodes = (LinkedList<YamlNode>)objects;
    if(!file.exists()) {
      try {
        file.createNewFile();
      } catch(IOException e) {
        return false;
      }
    }

    try(FileOutputStream out = new FileOutputStream(file);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {

      for(YamlNode node : nodes) {
        final String indent = (node.getIndentation() > 0)? String.format("%1$" + node.getIndentation() + "s", "") : "";
        for(int i = 0; i < node.getComments().size(); i++) {

          final String comment = node.getComments().get(i);
          if(comment.trim().equalsIgnoreCase("")) {
            writer.newLine();
          } else {
            if(i > 0) writer.newLine();
            writer.write(indent);
            if(!comment.startsWith("#")) {
              writer.write("#");
            }
            writer.write(comment);
          }
        }
        writer.newLine();
        writer.write(indent + node.getKey() + ":");
        writerValues(node, indent, writer);
      }
    } catch(IOException e) {
      return false;
    }
    return true;
  }

  private void writerValues(YamlNode node, String indent, BufferedWriter writer) {
    try {
      if(node.getValues().size() > 0) {
        if(node.isSequence()) {
          writer.newLine();
          for(int i = 0; i < node.getValues().size(); i++) {

            YamlValue value = node.getValues().get(i);
            
            for(int j = 0; j < value.getComments().size(); j++) {

              final String comment = value.getComments().get(j);
              if(comment.trim().equalsIgnoreCase("")) {
                writer.newLine();
              } else {
                if(i > 0) writer.newLine();
                writer.write(indent);
                if(!comment.startsWith("#")) {
                  writer.write("#");
                }
                writer.write(comment);
              }
            }

            if(value.getComments().size() > 0) {
              writer.newLine();
            }

            final boolean literal = isLiteral(value.getValue());
            writer.write(indent + "-");
            writer.write(" ");
            if(literal) writer.write("\"");
            writer.write(value.getValue().replaceAll("\"", "\\\\\""));
            if(literal) writer.write("\"");
          }
        } else if(node.isShorthand()) {
          writer.write(node.getShortCharacters().charAt(0));
          for(int i = 0; i < node.getValues().size(); i++) {
            if(i > 0) writer.write(", ");

            String strValue = node.getValues().get(i).getValue();
            final boolean literal = isLiteral(strValue);
            if(literal) writer.write("\"");
            writer.write(strValue.replaceAll("\"", "\\\\\""));
            if(literal) writer.write("\"");
          }
          writer.write(node.getShortCharacters().charAt(1));
        } else {
          String value = node.getValues().get(0).getValue();
          if(!value.startsWith(" ")) writer.write(" ");
          final boolean literal = isLiteral(value);
          if(literal) writer.write("\"");
          writer.write(value.replaceAll("\"", "\\\\\""));
          if(literal) writer.write("\"");
        }
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  private boolean isLiteral(String str) {
    if(str.trim().equalsIgnoreCase("false") || str.trim().equalsIgnoreCase("true")) return false;
    try {
      new BigDecimal(str.trim());
      return false;
    } catch(NumberFormatException ignore) {
      return true;
    }
  }
}