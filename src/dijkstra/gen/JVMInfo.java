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

package dijkstra.gen;

/**
 * Description
 * @version Feb 22, 2015
 */
public class JVMInfo
{
	private static int nextAddress = 1;
	private static int nextFunAddress = 0;
	public static int getNextAddress()
	{
		return nextAddress++;
	}
	
	
	public static int setAddressOffset(int AddressOffset)
	{
		return nextAddress = nextAddress + AddressOffset;
	}
	
	public static int getNextFunAddress()
	{
		return nextFunAddress++;
	}
	
	public static void reset()
	{
		nextAddress = 1;
		nextFunAddress = 0;
	}
}
