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

import java.io.*;
import java.util.*;
import javax.swing.JFrame;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.gui.*;
import dijkstra.lexparse.DijkstraParser;

/**
 * Display a parse tree.
 * @version Feb 9, 2015
 */
public class DijkstraTreeDisplayer
{
	/**
	 * Call this routine to display the parse tree. Hit ENTER on the console to continue.
	 */
	public static void showTree(DijkstraParser parser, ParserRuleContext tree)
	{
		{
			System.out.println(tree.toStringTree(parser));
			List<String> ruleNames = Arrays.asList(parser.getRuleNames());
			TreeViewer tv = new TreeViewer(ruleNames, tree);
			JFrame frame = new JFrame("Parse Tree");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.add(tv);
	        
	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	        BufferedReader br = 
	                new BufferedReader(new InputStreamReader(System.in));
	        try {
				br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
