/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Victor H
 * 
 * Custom Token class. Stores its type, its data and which line it's from
 */

public class Token 
{
    public final TokenType type;
    public final Object data;
    public final int codeLine;

    /**
     * Creates a Token Object
     * 
     * @param type - What type of Token it is
     * @param data - The data included in the Token, 
     * such as the specific number in a Number type token
     * @param codeLine - The line in the code where Token was found/created
     */
    public Token(TokenType type, Object data, int codeLine) 
    {
        this.type = type;
        this.data = data;
        this.codeLine = codeLine;
    }

    /**
     *
     * @return Type of token
     */
    public TokenType getType() 
    {
        return type;
    }

    public Object getData() 
    {
        return data;
    }

    public int getCodeLine() 
    {
        return codeLine;
    }
}

//All the types of tokens
enum TokenType 
{
    Pen, Movement, Number, Color, Hex, Rep, Quote, Dot, NLWS, Comment, Error
}