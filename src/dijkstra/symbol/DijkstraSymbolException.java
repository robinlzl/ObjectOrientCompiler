/*******************************************************************************
 * Copyright (c) 2015 Gary F. Pollice
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Used in CS4533/CS544 at Worcester Polytechnic Institute
 *******************************************************************************/

package dijkstra.symbol;

/**
 * This class is used for any exceptions thrown from the Dijkstra symbol table
 * operations.
 * 
 * @version Feb 7, 2015
 */
public class DijkstraSymbolException extends RuntimeException
{
	/**
	 * Sole constructor
	 * @param msg the message describing the error
	 */
	public DijkstraSymbolException(String msg)
	{
		super(msg);
		System.out.println(msg);
	}
}
