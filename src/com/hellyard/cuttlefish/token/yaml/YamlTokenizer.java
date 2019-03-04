package com.hellyard.cuttlefish.token.yaml;

import com.hellyard.cuttlefish.api.definition.Definition;
import com.hellyard.cuttlefish.api.token.Token;
import com.hellyard.cuttlefish.api.token.Tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;

/**
 * Created by creatorfromhell.
 * <p>
 * Cuttlefish YAML Parser
 */
public class YamlTokenizer implements Tokenizer {

  @Override
  public String name() {
    return "yaml";
  }

  public LinkedList<Token> tokenize(Reader reader, LinkedList<Definition> definitions) {
    LinkedList<Token> tokens = new LinkedList<>();

    try (Scanner scanner = new Scanner(reader)) {

      int lineCount = 1;
      LinkedList<String> commentBlock = new LinkedList<>();
      int lastComment = -1;
      int lastIndent = -1;

      while (scanner.hasNextLine()) {
        boolean start = true;
        String line = scanner.nextLine();
        //System.out.println("line - " + line);
        line = line.replaceAll("\\t", "    ");
        final int indentation = line.indexOf(line.trim());

        line = line.trim();
        if (line.length() == 0) {
          //tokens.add(new Token(lineCount, indentation, "empty_line", ""));
          commentBlock.add(" ");
          lastComment = lineCount;
          lastIndent = indentation;
        }
        while (line.length() > 0) {
          //System.out.println("Loop Line: " + line);
          for(Definition definition : definitions) {
            if(definition.isOnce() && !start) continue;

            Matcher matcher = definition.getRegex().matcher(line);
            if (matcher.find()) {
              final String tokenValue = (start) ? matcher.group().trim() : matcher.group();

              if(!definition.getName().equalsIgnoreCase("yaml_comment")) {
                if(commentBlock.size() > 0) {
                  System.out.println("Add new block comment");
                  tokens.add(new BlockToken(lastComment, lastIndent, "yaml_comment", commentBlock));
                  commentBlock = new LinkedList<>();
                }
                System.out.println("Line: " + line);

                tokens.add(new Token(lineCount, indentation, definition.getName(), tokenValue));
                line = matcher.replaceFirst("");
                //System.out.println("Line: " + line);
                System.out.println("Def: " + definition.getName());
                start = false;
              } else {
                if(commentBlock.size()  < 1) {
                  System.out.println("New Comment Block");
                  System.out.println("Current Block: " + String.join(" - ", commentBlock));
                }
                commentBlock.add(tokenValue);
                System.out.println("Line: " + line);
                line = matcher.replaceFirst("");
                start = false;

                System.out.println("Line: " + line);
                lastComment = lineCount;
                lastIndent = indentation;
              }
            }
          }
        }

        lineCount++;
      }
    } finally {
      try {
        reader.close();
      } catch(IOException ignored) {
      }
    }
    return tokens;
  }
}