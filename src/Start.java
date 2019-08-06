/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author Victor H
 * Main method, accepts user input for the filename
 */

public class Start 
{
    public static void main(String args[]) throws IOException 
    {
            try 
            {
                System.out.println("Filename: ");
                Scanner scanner = new Scanner(System.in);
                Lexer lexer = new Lexer(scanner.nextLine());
                if(lexer.hasMoreTokens())
                {
                    Parser parser = new Parser(lexer);
                    ArrayList<Command> output = parser.startParse();
                    Draw pen = new Draw(output);
                }
                else
                    System.exit(0);
            } catch (SyntaxError se) {
                    System.out.println("Syntax Error on Line #" + se.getLine());
            }

    }
}