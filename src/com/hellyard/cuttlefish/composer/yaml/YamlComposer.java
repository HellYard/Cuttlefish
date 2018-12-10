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
        writer.write(indent + String.join(System.lineSeparator() + indent, node.getComments()));
        writer.newLine();
        writer.write(indent + node.getKey() + ":");

        if(node.getValues().size() > 0) {
          if(node.getValues().size() > 1) {
            writer.newLine();
            writer.write(indent + "-" + String.join(System.lineSeparator() + indent + "-", node.getValues()));
          } else {
            writer.write(node.getValues().getFirst());
          }
        }
      }
    } catch(IOException e) {
      return false;
    }
    return true;
  }
}