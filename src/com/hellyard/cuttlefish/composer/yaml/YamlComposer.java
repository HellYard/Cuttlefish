package com.hellyard.cuttlefish.composer.yaml;

import com.hellyard.cuttlefish.api.composer.Composer;
import com.hellyard.cuttlefish.api.grammar.GrammarObject;
import com.hellyard.cuttlefish.grammar.yaml.YamlNode;

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
 *
 * This work is licensed under the GNU Affero General Public License Version 3. To view a copy of
 * this license, visit https://www.gnu.org/licenses/agpl-3.0.html.
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
          if(i > 0) writer.newLine();

          final String comment = node.getComments().get(i);
          if(comment.trim().equalsIgnoreCase("")) {
            writer.newLine();
          } else {
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

  public void writerValues(YamlNode node, String indent, BufferedWriter writer) {
    try {
      if(node.getValues().size() > 0) {
        if(node.isSequence()) {
          writer.newLine();
          for(int i = 0; i < node.getValues().size(); i++) {
            if(i > 0) writer.newLine();

            final String value = node.getValues().get(i);
            final boolean literal = isLiteral(value);
            writer.write(indent + "-");
            writer.write(" ");
            if(literal) writer.write("\"");
            writer.write(value);
            if(literal) writer.write("\"");
          }
        } else if(node.isShorthand()) {
          writer.write(node.getShortCharacters().charAt(0));
          for(int i = 0; i < node.getValues().size(); i++) {
            if(i > 0) writer.write(", ");

            final String value = node.getValues().get(i);
            final boolean literal = isLiteral(value);
            if(literal) writer.write("\"");
            writer.write(value);
            if(literal) writer.write("\"");
          }
          writer.write(node.getShortCharacters().charAt(1));
        } else {
          final String value = node.getValues().getFirst();
          if(!value.startsWith(" ")) writer.write(" ");
          final boolean literal = isLiteral(value);
          if(literal) writer.write("\"");
          writer.write(value);
          if(literal) writer.write("\"");
        }
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  public boolean isLiteral(String str) {
    if(str.trim().equalsIgnoreCase("false") || str.trim().equalsIgnoreCase("true")) return false;
    try {
      new BigDecimal(str.trim());
      return false;
    } catch(NumberFormatException ignore) {
      return true;
    }
  }
}