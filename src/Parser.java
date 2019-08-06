/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author Victor H
 * 
 * Parser reads the tokens created from the lexer and converts them into commands
 */

public class Parser 
{

    public Lexer lexer;
    public ArrayList<Command> cmds;

    public Parser(Lexer lexer) 
    {
        this.lexer = lexer;
        cmds = new ArrayList<Command>();
    }

    /**
     * Starts the parser
     * 
     * @return ArrayList containing all commands that are to be executed in Draw
     * @throws SyntaxError 
     */
    public ArrayList<Command> startParse() throws SyntaxError 
    {
        parse();
        return cmds;
    }

    /**
     * Calls method to turn Token into a Command, adds Command into ArrayList
     * 
     * @throws SyntaxError 
     */
    public void parse() throws SyntaxError 
    {
        Token t = lexer.nextToken();
        cmds.add(intoCommand(t));
        // If we have any more tokens, recursive parse call
        if (lexer.hasMoreTokens())
            parse();
    }

    /**
     * Transforms objects of Token class into an object of the Command class, 
     * 
     * @param t - Token being turned into a command
     * @return the created command
     * @throws SyntaxError 
     */
    public Command intoCommand(Token t) throws SyntaxError 
    {
        // Ignores excess NLWS tokens
        t = ignoreNLWS(t);
        // Command being created, to be added to "cmds" upon return
        Command cmd;
        // Switch for which case/type of Token it is, runs necessary method
        // to create the command
        switch (t.getType()) 
        {
            case Movement:
                cmd = movement(t);
                break;
            case Pen:
                cmd = pen(t);
                break;
            case Color:
                cmd = color(t);
                break;
            case Rep:
                cmd = rep(t);
                break;
            default:
                throw new SyntaxError(t.getCodeLine());
        }
        return cmd;
    }
    
    /**
     * Does nothing with NewLine/WhiteSpace tokens, moves on to the next token
     *
     * @param t - Current token
     * @return Token that comes after the NLWS token(s)
     * @throws SyntaxError
     */
    public Token ignoreNLWS(Token t) throws SyntaxError 
    {
        while (t.getType() == TokenType.NLWS)
            t = lexer.nextToken();
        return t;
    }

    /**
     * Creates a Movement(FORW, BACK, LEFT, RIGHT) command
     * 
     * @param t - The Movement token
     * @return the created Movement command
     * @throws SyntaxError 
     */
    public Command movement(Token t) throws SyntaxError 
    {
        Token t2 = lexer.nextToken();
        // After Movement token, there needs to be a NLWS token between it and the Number token
        if (t2.getType() == TokenType.NLWS) 
        {
            // Ignores excess NLWS tokens
            t2 = ignoreNLWS(t2);
            // Has to find a Number token after a Movement and NLWS token
            if (t2.getType() != TokenType.Number)
                throw new SyntaxError(t2.getCodeLine());
            // Saves value(data) of Number token to create the command later
            int num = (int) t2.getData(); 
            if (num == 0)
                throw new SyntaxError(t2.getCodeLine());
            t2 = lexer.nextToken();
            // Ignores excess NLWS tokens
            t2 = ignoreNLWS(t2);
            // Has to find a Dot token after a Number token
            if (t2.getType() != TokenType.Dot)
                throw new SyntaxError(t2.getCodeLine());
            return new Movement(t, num);
        } else {
            throw new SyntaxError(t2.getCodeLine());
        }
    }

    /**
     * Creates a Pen(DOWN, UP) command
     * 
     * @param t - The Pen token
     * @return the created Pen command
     * @throws SyntaxError 
     */
    public Command pen(Token t) throws SyntaxError {
        Token t2 = lexer.nextToken();
        // Ignores excess NLWS tokens
        t2 = ignoreNLWS(t2);
        // Has to find a Dot token after a Pen token
        if (t2.getType() != TokenType.Dot)
            throw new SyntaxError(t2.getCodeLine());
        return new Pen(t);
    }

    /**
     * Creates a Color command
     * 
     * @param t - The Color token
     * @return the created Color command
     * @throws SyntaxError 
     */
    public Command color(Token t) throws SyntaxError {
        Token t2 = lexer.nextToken();
        // After Color token, there needs to be a NLWS token between it and the Hex token
        if (t2.getType() == TokenType.NLWS) {
            // Ignores excess NLWS tokens
            t2 = ignoreNLWS(t2);
            // Has to find a Hex token after a Color and NLWS token
            if (t2.getType() != TokenType.Hex)
                throw new SyntaxError(t2.getCodeLine());
            // Saves value/data) of Hex token to create the command later
            String hex = (String) t2.getData(); 
            t2 = lexer.nextToken();
            // Ignores excess NLWS tokens
            t2 = ignoreNLWS(t2);
            // Has to find a Dot token after a Hex token
            if (t2.getType() != TokenType.Dot)
                throw new SyntaxError(t2.getCodeLine());
            return new Color(t, hex);
        } else {
            throw new SyntaxError(t2.getCodeLine());
        }
    }

    /**
     * Creates a Rep (Repeat) command
     * 
     * @param t - The Rep token
     * @return the created Rep command, either with or without quotes
     * @throws SyntaxError 
     */
    public Command rep(Token t) throws SyntaxError {
        Token t2 = lexer.nextToken();
        // After Rep token, there needs to be a NLWS token between it and the Number token
        if (t2.getType() == TokenType.NLWS) 
        {
            // Ignores excess NLWS tokens
            t2 = ignoreNLWS(t2);
            // Has to be a Number token after a Rep and NLWS token
            if (t2.getType() != TokenType.Number)
                throw new SyntaxError(t2.getCodeLine());
            // Saves value(data) of Number token to create the command later
            int num = (int) t2.getData();
            if (num == 0)
                throw new SyntaxError(t2.getCodeLine());
            t2 = lexer.nextToken();
            // After Number token, there needs to be a NLWS token
            if (t2.getType() == TokenType.NLWS) {
                t2 = lexer.nextToken();
                // Ignores excess NLWS tokens
                t2 = ignoreNLWS(t2);
		// If Number token is followed by a Quote token, it repeats several 
                // commands. Else it repeats only one command
                if (t2.getType() == TokenType.Quote) 
                {
                    t2 = lexer.nextToken();
                    ArrayList<Command> cmd = new ArrayList<Command>();
                    // It loops until it can find an ending Quote token
                    // Throws Syntax Error if no tokens remain
                    while (t2.getType() != TokenType.Quote) 
                    {
                        cmd.add(intoCommand(t2));
                        t2 = lexer.nextToken();
                        t2 = ignoreNLWS(t2);
                    }
                    return new MultiRep(t, num, cmd);
                } else {
                    return new Rep(t, num, intoCommand(t2));
                }
            } else {
                throw new SyntaxError(t2.getCodeLine());
            }
        } else {
            throw new SyntaxError(t2.getCodeLine());
        }
    }
}