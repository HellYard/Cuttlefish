package com.hellyard.cuttlefish.composer.yaml;

import com.hellyard.cuttlefish.api.composer.Composer;
import com.hellyard.cuttlefish.api.grammar.GrammarObject;
import com.hellyard.cuttlefish.grammar.yaml.YamlNode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

        if(node.getValues().size() > 0) {
          if(node.getValues().size() > 1) {
            writer.newLine();
            for(int i = 0; i < node.getValues().size(); i++) {
              if(i > 0) writer.newLine();

              final String value = node.getValues().get(i);
              writer.write(indent + "-");
              if(!value.startsWith(" ")) writer.write(" ");
              writer.write(value);
            }
          } else {
            final String value = node.getValues().getFirst();
            if(!value.startsWith(" ")) writer.write(" ");
            writer.write(value);
          }
        }
      }
    } catch(IOException e) {
      return false;
    }
    return true;
  }
}