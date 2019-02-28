package com.hellyard.cuttlefish.grammar.yaml;

import java.util.List;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 *
 * This work is licensed under the GNU Affero General Public License Version 3. To view a copy of
 * this license, visit https://www.gnu.org/licenses/agpl-3.0.html.
 */
public class YamlValue {
  private List<String> comments;
  private String value;

  private String type;

  public YamlValue(List<String> comments, String value, String type) {
    this.comments = comments;
    this.value = value;
    this.type = type;
  }

  public List<String> getComments() {
    return comments;
  }

  public void setComments(List<String> comments) {
    this.comments = comments;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}