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

package dijkstra.semantic;

/**
 * Description
 * @version Feb 17, 2015
 */
public class DijkstraSemanticException extends RuntimeException
{
	public DijkstraSemanticException(String msg)
	{
		super(msg);
		System.out.println(msg);
	}
}
