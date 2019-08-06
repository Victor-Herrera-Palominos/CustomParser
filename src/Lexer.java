/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Victor H
 * 
 * Lexer analyzes text from file and creates tokens
 */

public class Lexer 
{
    public final ArrayList<Token> tokenList;
    public int currentToken;

    /**
     * Constructor of the Lexer
     *
     * @param filename - The filename inputted by the user
     * @throws java.io.IOException
     * @throws SyntaxError
     *
     * Converts String into tokens, which are placed in the ArrayList tokenList
     */
    public Lexer(String filename) throws java.io.IOException, SyntaxError 
    {
        // Converts file text into a String
        String instructions = Lexer.readFile(filename);
        
        // Regular expression for numbers, hex(the color) and comments
        String num = "[0-9]+";
        String hex = "[#][A-Fa-f0-9]{6}";
        String cmnt = "[%].*";

        // Create the Pattern and Matcher
        Pattern tokenPattern = Pattern.compile("\r\n|\n|\t|\"|\\.| |" + cmnt + 
                "|" + hex + "|" + num + "|FORW|BACK|LEFT|RIGHT|DOWN|UP|COLOR|REP",
                Pattern.CASE_INSENSITIVE);
        Matcher m = tokenPattern.matcher(instructions);
        
        int inPos = 0;
        tokenList = new ArrayList<Token>();
        currentToken = 0;
        int codeLine = 1;
        
        // Loops while it can still find token patterns in the instructions string
        while (m.find()) 
        {
            //Match has to start where the previous Token ended
            if (m.start() != inPos)
                tokenList.add(new Token(TokenType.Error, "", codeLine));
            //Matches a new line, "\r\n" is for Windows ext editors which use carriage return
            if (m.group().matches("\n") || m.group().matches("\r\n")) 
            {
                tokenList.add(new Token(TokenType.NLWS, "", codeLine));
                codeLine++;
            } 
            //Matches names of instructions
            else if (m.group().equalsIgnoreCase("FORW")) 
                tokenList.add(new Token(TokenType.Movement, "Forw", codeLine));
            else if (m.group().equalsIgnoreCase("BACK")) 
                tokenList.add(new Token(TokenType.Movement, "Back", codeLine));
            else if (m.group().equalsIgnoreCase("LEFT"))
                tokenList.add(new Token(TokenType.Movement, "Left", codeLine));
            else if (m.group().equalsIgnoreCase("RIGHT"))
                tokenList.add(new Token(TokenType.Movement, "Right", codeLine));
            else if (m.group().equalsIgnoreCase("UP"))
                tokenList.add(new Token(TokenType.Pen, "Up", codeLine));
            else if (m.group().equalsIgnoreCase("DOWN"))
                tokenList.add(new Token(TokenType.Pen, "Down", codeLine));
            else if (m.group().equalsIgnoreCase("COLOR"))
                tokenList.add(new Token(TokenType.Color, "Color", codeLine));
            else if (m.group().equalsIgnoreCase("REP"))
                tokenList.add(new Token(TokenType.Rep, "Rep", codeLine));
            //Matches special symbols, such as quotation marks, dots and whitespace
            else if (m.group().matches("\""))
                tokenList.add(new Token(TokenType.Quote, "\"", codeLine));
            else if (m.group().matches("\\."))
                tokenList.add(new Token(TokenType.Dot, ".", codeLine));
            else if (m.group().matches("\t"))
                tokenList.add(new Token(TokenType.NLWS, "", codeLine));
            else if (m.group().matches(" "))
                tokenList.add(new Token(TokenType.NLWS, "", codeLine));
            //Matches number values and the hex values
            else if (Character.isDigit(m.group().charAt(0)))
                tokenList.add(new Token(TokenType.Number, Integer.parseInt(m.group()), codeLine));
            else if (m.group().matches(hex))
                tokenList.add(new Token(TokenType.Hex, m.group(), codeLine));
            else if (m.group().matches(cmnt)) 
            {
                // Matched a comment. Does nothing because comments are ignored
            }
            inPos = m.end();
        }
        
        // Any still remaining data in the String becomes an Error Token
        if (inPos != instructions.length())
            tokenList.add(new Token(TokenType.Error, "", codeLine));
        
        // NewLine/WhiteSpace Tokens at the end of the ArrayList are removed
        if (!(tokenList.isEmpty())) 
        {
            Token t = tokenList.get(tokenList.size() - 1);
            while (t.getType() == TokenType.NLWS && !(tokenList.isEmpty())) 
            {
                tokenList.remove(tokenList.size() - 1);
                if(tokenList.isEmpty())
                    System.exit(0);
                else
                    t = tokenList.get(tokenList.size() - 1);
            }
        }
    }

    /**
     * Reads file and converts its text into a single stringbuilder/string
     * 
     * @param file - The file being read
     * @return String containing the text from the file
     * @throws java.io.IOException 
     */
    private static String readFile(String file) throws java.io.IOException 
    {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        char input[] = new char[1024];
        int read = 0;
        while ((read = bf.read(input,0,1024)) != -1)
        {
            sb.append(input, 0, read);
        }
        return sb.toString();
    }

    /**
     * Returns Token from ArrayList, iterates to the next one
     * 
     * @return Token from ArrayList
     * @throws SyntaxError 
     */
    public Token nextToken() throws SyntaxError 
    {
        if (!hasMoreTokens())
            throw new SyntaxError(tokenList.get((currentToken - 1)).getCodeLine());
        Token t = tokenList.get(currentToken);
        ++currentToken;
        return t;
    }

    /**
     * Checks if there are more Tokens remaining
     * 
     * @return true if current token index is less than ArrayList size
     */
    public boolean hasMoreTokens() 
    {
        return currentToken < tokenList.size();
    }
}