package com.hellyard.cuttlefish.token.yaml;

import com.hellyard.cuttlefish.api.definition.Definition;
import com.hellyard.cuttlefish.api.token.Token;
import com.hellyard.cuttlefish.api.token.Tokenizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;

/**
 * Created by creatorfromhell.
 * <p>
 * Cuttlefish YAML Parser
 * <p>
 * This work is licensed under the GNU Affero General Public License Version 3. To view a copy of
 * this license, visit https://www.gnu.org/licenses/agpl-3.0.html.
 */
public class YamlTokenizer implements Tokenizer {

  @Override
  public String name() {
    return "yaml";
  }

  public LinkedList<Token> tokenize(File file, LinkedList<Definition> definitions) {
    LinkedList<Token> tokens = new LinkedList<>();

    try (FileInputStream stream = new FileInputStream(file);
         Scanner scanner = new Scanner(stream)) {

      int lineCount = 1;
      while (scanner.hasNextLine()) {
        boolean start = true;
        String line = scanner.nextLine();
        line = line.replaceAll("\\t", "    ");
        final int indentation = line.indexOf(line.trim());

        line = line.trim();
        if (line.length() == 0) {
          tokens.add(new Token(lineCount, indentation, "empty_line", ""));
        }
        while (line.length() > 0) {
          for(Definition definition : definitions) {
            if(definition.isOnce() && !start) continue;

            Matcher matcher = definition.getRegex().matcher(line);
            if (matcher.find()) {

              final String tokenValue = (start) ? matcher.group().trim() : matcher.group();
              tokens.add(new Token(lineCount, indentation, definition.getName(), tokenValue));
              line = matcher.replaceFirst("");
              start = false;
            }
          }
        }

        lineCount++;
      }
    } catch(IOException ignore) {
      return tokens;
    }
    return tokens;
  }
}