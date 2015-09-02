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

import static org.junit.Assert.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;
import dijkstra.lexparse.DijkstraParser;

/**
 * Test (in some sense) the DijkstraTraceListener.
 * @version Feb 5, 2015
 */
public class TraceListenerTest
{
	private DijkstraParser parser;
	
//	@Test
	public void guardWithCompoundStatement()
	{
		final String text = "int a input a "
				+ "if"
				+ "  a < -1 :: { b <- 6; print b} "
				+ "  ~(a < -1) :: { b <- 5; print a * b}"
				+ "fi";
		doTrace(text);
	}
	
	// Helper methods
	private void makeParser(String inputText)
	{
		parser = DijkstraFactory.makeParser(new ANTLRInputStream(inputText));
	}

	/**
	 * This method performs the parse. If you want to see what the tree looks like, use
	 * 		<br><code>System.out.println(tree.toStringTree());<code></br>
	 * after calling this method.
	 * @param inputText the text to parse
	 */
	private void doTrace(String inputText)
	{
		makeParser("program test " + inputText);
		ParserRuleContext tree = parser.dijkstraText();
		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(new DijkstraTraceListener(parser), tree);
	}

}
