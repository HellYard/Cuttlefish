package test.java;

import com.hellyard.cuttlefish.CuttlefishBuilder;
import com.hellyard.cuttlefish.composer.yaml.YamlComposer;
import com.hellyard.cuttlefish.grammar.yaml.YamlNode;

import java.io.File;
import java.nio.file.Paths;
import java.util.LinkedList;

public class MainTest {
  public static void main(String[] args) {
    LinkedList<YamlNode> nodes = (LinkedList<YamlNode>)new CuttlefishBuilder(Paths.get("C:\\Users\\Daniel\\Desktop\\Minecraft\\spigot2\\plugins\\TheNewEconomy\\config.yml").toFile(), "yaml").build().getNodes();
    for(YamlNode node : nodes) {
      System.out.println("-------------------------");
      System.out.println("-------------------------");
      System.out.println("Node String: " + node.getNode());
      System.out.println("Key: " + node.getKey());
      System.out.println("Line #: " + node.getLineNumber());
      System.out.println("Values: " + String.join(",", node.getValues()));
      System.out.println("Comments: " + String.join(",", node.getComments()));

      if(node.getParent() == null) {
        System.out.println("Parent: None");
      } else {
        System.out.println("Parent: " + node.getParent().toString());
      }
      System.out.println("-------------------------");
      System.out.println("-------------------------");
    }

    File file = new File("C:\\Users\\Daniel\\Desktop\\Minecraft\\spigot2\\plugins\\TheNewEconomy\\testwrite.yml");
    new YamlComposer().compose(file, nodes);
  }
}
