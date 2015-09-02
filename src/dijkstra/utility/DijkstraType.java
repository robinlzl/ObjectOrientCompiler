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

package dijkstra.utility;

/**
 * Type values that are used in symbols and for attributes on nodes.
 * @version Feb 6, 2015
 */
public enum DijkstraType
{
	UNDEFINED, INT, BOOLEAN, FLOAT, CLASS, FUNC, MULTITYPE, NUMERIC, OBJECT, CONSTRUCTOR; 
	
	public static DijkstraType getType(String s)
	{
		switch (s) {
			case "int": return INT;
			case "boolean" : return BOOLEAN;
			case "float" : return FLOAT;
			case "fun" : return FUNC;
			case "class" : return CLASS;
		}
		return UNDEFINED;
	}
}
