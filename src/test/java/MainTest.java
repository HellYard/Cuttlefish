import com.hellyard.cuttlefish.CuttlefishBuilder;

import java.nio.file.Paths;

public class MainTest {
  public static void main(String[] args) {
    System.out.println(new CuttlefishBuilder(Paths.get("D://output/output.yml").toFile(), "yaml").build().getNodes().toString());
  }
}
