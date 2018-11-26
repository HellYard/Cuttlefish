package com.hellyard.cuttlefish.token;

import com.hellyard.cuttlefish.api.definition.Definition;
import com.hellyard.cuttlefish.api.token.Tokenizer;
import com.hellyard.cuttlefish.api.token.Token;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
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
public class BaseTokenizer implements Tokenizer {

  public LinkedList<Token> tokenize(File file, List<Definition> definitions) {
    LinkedList<Token> tokens = new LinkedList<>();

    try(Scanner scanner = new Scanner(file)) {

      int lineCount = 1;
      while(scanner.hasNextLine()) {
        String line = scanner.nextLine();
        final int indentation = line.indexOf(line.trim());

        while(line.length() > 0) {
          for(Definition definition : definitions) {

            Matcher matcher = definition.getRegex().matcher(line);
            if(matcher.find()) {

              tokens.add(new Token(lineCount, indentation, definition.getName(), matcher.group().trim()));
              line = matcher.replaceFirst("");
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