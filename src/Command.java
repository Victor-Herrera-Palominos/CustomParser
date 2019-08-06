/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author Victor H
 * Custom Command class, stores its type, its data (number/hex) and in the case
 * of a Repeat command, it also stores other Command object(s)
 */

// Extends to different types of command classes
abstract class Command 
{
    public Token token;
    
    public Command(Token t) {
            token = t;
    }

    public TokenType getType() {
            return token.getType();
    }

    public Object getData() {
            return token.getData();
    }

    @Override
    public abstract String toString();
}

// Down & Up commands for the pen
class Pen extends Command 
{
    public Pen(Token t) {
        super(t);
    }

    @Override
    public String toString() {
        return "Pen";
    }
}

// Forw and Back, Left and Right commands for Leo
// Has number for distance/degree
class Movement extends Command 
{
    int number;

    public Movement(Token t, int number) {
        super(t);
        this.number = number;
    }

    public int getNum() {
        return this.number;
    }

    @Override
    public String toString() {
        return "Movement";
    }
}

// Color command
// Has Hex value
class Color extends Command 
{
    String color;

    public Color(Token t, String color) {
        super(t);
        this.color = color;
    }

    public String getHexColor() {
        return this.color.toUpperCase();
    }

    @Override
    public String toString() {
        return "Color";
    }
}

// Rep command with one parameter(no quotes)
// Has number for the amount of repetitions, and a Command object for which command
// is to be repeated
class Rep extends Command 
{
    int number;
    Command cmd;

    public Rep(Token t, int number, Command cmd) {
        super(t);
        this.number = number;
        this.cmd = cmd;
    }

    public Command getCmd() {
        return cmd;
    }

    public int getNum() {
        return this.number;
    }

    @Override
    public String toString() {
        return "Rep";
    }
}

// Rep command with multiple parameters/commands(uses quotes)
// Has number for the amount of repetitions, and an ArrayList of Command objects
// which are to be repeated
class MultiRep extends Command 
{
    int number;
    ArrayList<Command> cmds;

    public MultiRep(Token t, int number, ArrayList<Command> cmds) {
        super(t);
        this.number = number;
        this.cmds = cmds;
    }

    public ArrayList<Command> getCmds() {
        return cmds;
    }

    public int getNum() {
        return this.number;
    }

    @Override
    public String toString() {
        return "MultiRep";
    }
}