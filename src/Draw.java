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
 * Draw class is what executes commands and prints the result
 */
public class Draw {
    
    public boolean mark;   // Pen state(true = down, false = up)
    public String color;   // Pen color
    public double xPos;    // Current x-value
    public double yPos;    // Current y-value
    public int v;          // Current angle for forward movement

    /**
     * Creates the Draw object with defaults
     *
     * @param commands - Commands to be executed
     */
    public Draw(ArrayList<Command> commands) 
    {    
        // Default values for Draw
        mark = false;
        color = "#0000FF";
        xPos = 0.0000;
        yPos = 0.0000;
        v = 0;              // Facing (1,0), the positive x-axis

        //Executes every command in ArrayList
        for (Command c : commands) {
            execute(c);
        }
    }

    /**
     * Checks the type of command it is and runs the necessary method
     * 
     * @param c - Currently executing command
     */
    public void execute(Command c) 
    {
        // Command is type casted into the correct Command subclass
        switch (c.toString()) 
        {
            default: break;
            case "Pen": pen((Pen) c); break;
            case "Movement": movement((Movement) c); break;
            case "Color": color((Color) c); break;
            case "Rep": rep((Rep) c); break;
            case "MultiRep": multiRep((MultiRep) c); break;
        }
    }

    /**
     * Sets the state of the pen
     * 
     * @param c - The Pen command
     */
    public void pen(Pen c) 
    {
        if (c.getData().equals("Down"))
            mark = true;    
        else if (c.getData().equals("Up"))
            mark = false;
    }
    
    /**
     * Moves and rotates the pen
     * 
     * @param c - The Movement command
     */
    public void movement(Movement c) 
    {
        // Starting coordinates of the pen
        double xPrev = xPos;
        double yPrev = yPos;
        // Left adds to the angle, Right subtracts from the angle
        if (c.getData().equals("Left"))
            v = v + c.getNum();
        else if (c.getData().equals("Right"))
            v = v - c.getNum();
        else if (c.getData().equals("Forw")) 
        {
            // Pen's calculated destination coordinates
            xPos = xPos + c.getNum() * Math.cos(Math.toRadians(v));
            yPos = yPos + c.getNum() * Math.sin(Math.toRadians(v));
            
            if (mark)
                System.out.format("Line with the Color %s drawn from %.4f %.4f to %.4f %.4f%n", color, xPrev, 
                        yPrev, xPos, yPos);
        } 
        else if (c.getData().equals("Back")) 
        {
            // Pen's calculated destination coordinates
            xPos = xPos - c.getNum() * Math.cos(Math.toRadians(v));
            yPos = yPos - c.getNum() * Math.sin(Math.toRadians(v));
            
            if (mark)
                System.out.format("Line with the Color %s drawn from %.4f %.4f to %.4f %.4f%n", color, xPrev, 
                        yPrev, xPos, yPos);
        }
    }
    
    /**
     * Gets the color of the pen in hex
     * 
     * @param c - The Color command
     */
    public void color(Color c) 
    {
        color = c.getHexColor();
    }
    
    /**
     * Executes repetitions with one parameter
     * 
     * @param c - The Rep command
     */
    public void rep(Rep c) 
    {
        // Amount of repetitions
        int amount = c.getNum();
        // Gets the parameter(command) from the rep command
        Command cmd = c.getCmd();
        // Loops and executes the command 'amount' of times
        for (int i = 0; i < amount; i++)
            execute(cmd);
    }
    
    /**
     * Executes repetitions with multiple parameters
     * 
     * @param c - The MultiRep command
     */
    public void multiRep(MultiRep c) 
    {
        // Amount of repetitions
        int amount = c.getNum();
        // Gets the parameters(commands) from the multirep command
        ArrayList<Command> cmds = c.getCmds();
        // Loops and executes each command 'amount' of times
        for (int i = 0; i < amount; i++) 
        {
            for (Command cmd : cmds) {
                execute(cmd);
            }
        }
    }
}