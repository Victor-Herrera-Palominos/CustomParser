/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Victor H
 * Simple exception in case of faulty syntax. Stores line.
 */

@SuppressWarnings("serial")
public class SyntaxError extends Exception 
{
	int line;
        
	public SyntaxError(int line) {
		this.line = line;
	}
	
	/**
         * Returns Syntax Error
         * 
         * @return the line where the syntax has an error
         */
	public int getLine() {
		return line;
	}
}
