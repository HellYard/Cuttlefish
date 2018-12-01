package com.hellyard.cuttlefish.token.yaml;

import com.hellyard.cuttlefish.api.definition.Definition;
import com.hellyard.cuttlefish.api.token.Token;
import com.hellyard.cuttlefish.api.token.Tokenizer;
import com.hellyard.cuttlefish.iterator.TokenList;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;

/**
 * Created by creatorfromhell.
 *
 * Cuttlefish YAML Parser
 *
 * This work is licensed under the GNU Affero General Public License Version 3. To view a copy of
 * this license, visit https://www.gnu.org/licenses/agpl-3.0.html.
 */
public class YamlTokenizer implements Tokenizer {

  @Override
  public String name() {
    return "YAML";
  }

  public TokenList tokenize(File file, LinkedList<Definition> definitions) {
    TokenList tokens = new TokenList();

    try(Scanner scanner = new Scanner(file)) {

      int lineCount = 1;
      while(scanner.hasNextLine()) {
        boolean start = true;
        String line = scanner.nextLine();
        final int indentation = line.indexOf(line.trim());

        while(line.length() > 0) {
          for(Definition definition : definitions) {

            Matcher matcher = definition.getRegex().matcher(line);
            if(matcher.find()) {

              final String tokenValue = (start)? matcher.group().trim() : matcher.group();
              tokens.add(new Token(lineCount, indentation, definition.getName(), tokenValue));
              line = matcher.replaceFirst("");
              start = false;
            }
          }
        }

        lineCount++;
      }
    } catch(IOException ignore) {
    }
    return tokens;
  }
}