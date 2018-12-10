package test.java;

import com.hellyard.cuttlefish.CuttlefishBuilder;

import java.nio.file.Paths;

public class MainTest {
  public static void main(String[] args) {
    System.out.println(new CuttlefishBuilder(Paths.get("C:\\Users\\Daniel\\Desktop\\Minecraft\\spigot2\\plugins\\TheNewEconomy\\config.yml").toFile(), "yaml").build().getNodes().toString());
  }
}
