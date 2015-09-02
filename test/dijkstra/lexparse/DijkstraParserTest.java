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

package dijkstra.lexparse;

import static org.junit.Assert.assertTrue;

import java.io.*;
import java.util.*;

import javax.swing.JFrame;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.gui.TreeViewer;
import org.junit.Test;

import dijkstra.runtime.DijkstraRuntime;
import dijkstra.utility.DijkstraFactory;

/**
 * Description
 * @version Jan 27, 2015
 */
public class DijkstraParserTest
{
	private DijkstraParser parser;
	private ParserRuleContext tree;
	private boolean stopViewer;
	
	@Test
	public void minimalprogram()
	{
		doParse("int i");
	}
	
	@Test
	public void idlist()
	{
		doParse("int aaa,bbb,ccc");
	}
	
	@Test
	public void twoDeclarationProgram()
	{
		doParse("int i boolean b");
	}
	
	@Test
	public void complexassign()
	{
		doParse("a <- b + 3 * c < 5 + d");
//		showTree();
	}
	
	@Test
	public void singleAssignmentProgram()
	{
		doParse("i <- 1");
	}
	
	@Test
	public void declarationFollowedBySemicolon()
	{
		doParse("int i;");
	}
	

	
	@Test
	public void assignStatement()
	{
		doParse("a <- 42");
	}
	
	@Test
	public void assignmentStatementFollowedBySemicolon()
	{
		doParse("a <- 42;");
	}
	
	

	
	@Test
	public void inputStatementFollowedBySemicolon()
	{
		doParse("input x;");
	}
	
	@Test
	public void outputStatement()
	{
		doParse("print 1");
	}
	
	@Test
	public void outputStatementFollowedBySemicolon()
	{
		doParse("print 2;");
	}
/*	
	@Test
	public void iterativeStatement()
	{
		doParse("loop 5 a <- 1");
	}
*/	
	@Test
	public void compoundStatement()
	{
		doParse("{ print 3; }");
	}
	
	@Test
	public void compoundStatementWithDeclaration()
	{
		doParse("{ int i i <- 3 }");
	}
	
	@Test
	public void modanddiv()
	{
		doParse("print 4 mod 5 + a * b div c ");
	}
	
	@Test
	public void singleGuard()
	{
		doParse("if b :: print 1 fi");
	}
	
	@Test
	public void testEqualityExpression()
	{
		doParse("mybool <- a = b = c");
	}
	
	@Test
	public void arithmeticPrecedence1()
	{
		doParse("a <- a + b * c");

	}
	
	@Test
	public void arithmeticPrecedence2()
	{
		doParse("a <- d - a * b + c");

	}
	
	@Test
	public void floatconstant()
	{
		doParse("float a "
				+ "a <- 2.5");
	}
	
	@Test
	public void arrarydecl()
	{
		doParse("int [10] a");
	}

	@Test
	public void logicalexpression()
	{
		doParse("a <- x = 1 & y = 2 | z =4");
		showTree();
	}
	
	
	
	@Test
	public void logica()
	{
		doParse("print true | false & false");
		showTree();
	}
	
	@Test
	public void funttestn()
	{
		doParse("fun myfun() : int, float, boolean { int a \n"
				+ "b <- c \n"
				+ "return a, b,c }");
//		showTree();
	}
	@Test
	public void multipleGuardsInAlternative() throws Exception
	{
		String s = "bollean i,j,k\n"
				+ "int x;"
				+ "i <- true; j <- false; k <- true\n"
				+ "x <- 0\n"
				+ "if\n"
				+ "  i :: x <- 42\n"
				+ "  j :: x <- 1\n"
				+ "  k :: x <- 2\n"
				+ "fi\n"
				+ "print x";
		doParse(s);
//		showTree();
	}
	
	@Test
	public void printLOGICAL() throws Exception
	{
		String s ="print true | false & false";
		
		doParse(s);
		showTree();
	}
	
	
	
	@Test
	public void expressions()
	{
		doParse("int x, y, z\n"
				+ "x, y, z <- 5, 10, 17\n"
				+ "float a\n"
				+ "a <- 2\n"
				+ "if \n"
				+ "   x < 10 & y < 10  :: a <- 25.5\n"
				+ "   x < 10 | y ~= 10 :: a <- a mod 5\n"
				+ "fi\n"
				+ "do\n"
				+ "   x = 5 | y = a div 4 :: z <- a\n"
				+ "   x = 10 & y = a div 8 :: z <- z mod a\n"
				+ "od\n");
//		showTree();

	}
	
	@Test
	public void functioncallpression()
	{	
		doParse("x, y, z <- 1, 2, 3\n"
			+ "fun sum(x, y) : int {\n"
			+ "z <- x + y # outer z\n"
			+ "return z\n"
			+ "}\n"
			+ "print sum(40, 2)\n");
//		showTree();
	}	
	
	@Test
	public void classdeclaration()
	{	
		doObjParse("class myclass \n"
				+ "int a;\n "
				+ "fun b(int a) : int,float, boolean { a <- 1 }\n "
				+ "float c;\n");

	}
	
	public void classfundeclaration()
	{	
		doObjParse("class myclass "
				+ "fun myfun(): int { a <- 1 }\n");

	}
	
	@Test
	public void classarraydeclaration()
	{	
		doObjParse("class myclass "
				+ "( int a [ RW ], x, int y, z [W]) int [10] a, b; \n");
//		showTree();
	}
	
	@Test
	public void ClassFunMuntiReturnDeclaration()
	{	
		doObjParse("class myclass\n "
				+ "fun mutireturn(a, int b, myclass c): int, int { \n"
				+ "a <- 1 b <- 2\n"
				+ "return a, b}\n");


	}
	
	@Test
	public void ClassFunMuntiReturnDeclarationandProgram()
	{	
		doObjParse("class myclass\n "
				+ "fun mutireturn(): int, int { \n"
				+ "a <- 1 b <- 2\n"
				+ "return a, b}\n"
				+ "program myprogramtest \n"
				+ "c, d <- mutireturn()");
//		showTree();s
	}
	
	
	@Test
	public void smalltestProgram()
	{	
		doParse("boolean a; input a");
//		showTree();
	}
	
	
	@Test
	public void ClassAttibuteAccessProgram()
	{	
		doObjParse("class myclass\n "
				+ "int x,y\n"
				+ "fun mutireturn(): int, int { \n"
				+ "a <- 1 b <- 2\n"
				+ "return a, b}\n"
				+ "program myprogramtest \n"
				+ "c, d <- mutireturn();\n"
				+ "myclass.y, array[2], a <- 1, 2, 3");
		showTree();
	}
	
	@Test
	public void ClassConstructorMathodcallProgram()
	{	
		doObjParse("class myclass\n "
				+ "fun methodcall(): int, int { \n"
				+ "a <- 1 b <- 2\n"
				+ "return a, b}"
				+ "fun myclass(): int {int a,b}\n"
				+ "program myprogramtest \n"
				+ "myclas x "
				+ "c, d <- x.methodcall()");

//		showTree();
	}
	@Test
	public void exprtest()
	{	
		doObjParse("program Expr1 "
				+"  i1, i2, i3 <- 2, -5, 42                      "  
	+"	f1, f2, f3 <- -6.8, 820.75, 2387.92          "  
	+"	t, f <- true, false                          "  
	+"	                                             "  
	+"	print i3 div 7 + 3 * 8 mod 5 = 10            "  
	+"	print (i3 div 7 + 3 * 8 mod 5 = 10) = t      "  
	+"	                                             "  
	+"	print f3 < 2387.921                          "  
	+"	print f3 > 2387.919                          "  
	+"	                                             "  
	+"	print f3 + -f3 = 0.0                         "  
	+"	                                             "  
	+"	print i1 + -i1 = 0                           " ) 
	;
		showTree();
	}
	
	
	@Test
	public void Arrayaccess()
	{	
		doObjParse("class myclass\n "
				+ "int [10] a,b;\n"
				+ "class yourclass\n"
				+ "float c,d;\n"				
				+ "program myprogramtest \n"
				+ "myclass x\n"
				+ "b[1] <- 6");
//		showTree();

	}
	
	
	
	
	@Test
	public void squ()
	{	
		doObjParse("program myprogramtest"
				+ "  float inVal                                                                                        "
		+	"		input inVal                                                                                        "
		+	"		root <- compute(inVal, inVal / 2)                                                                  "
		+	"		print root                                                                                         "
		+ "                                                                                                      "
		+	"		fun compute(float inVal, float guess) : float {                                                    "
		+	"			float computedRoot                                                                               "
		+	"			if                                                                                               "
		+	"				check(inVal / guess, guess) :: computedRoot <- guess                                           "
		+	"				~check(inVal / guess, guess) :: computedRoot <- compute(inVal, betterGuess(inVal, guess))      "
		+	"			fi                                                                                               "
		+	"			return computedRoot                                                                              "
		+	"		}                                                                                                  "
		+ "                                                                                                      "
		+	"		fun abs(float val) : float {                                                                       "
		+	"			float other                                                                                      "
		+	"			if                                                                                               "
		+	"				val >= 0.0 :: other <- val                                                                     "
		+	"				val < 0.0 :: other <- -val                                                                     "
		+	"			fi                                                                                               "
		+	"			return other                                                                                     "
		+	"		}                                                                                                  "
		+ "                                                                                                      "
		+	"		fun check(float a, float b) : boolean {                                                            "
		+	"			boolean isGood                                                                                   "
		+	"			if                                                                                               "
		+	"				abs(a - b) < 0.00001 :: isGood <- true                                                         "
		+	"				abs(a - b) >= 0.00001 :: isGood <- false                                                       "
		+	"			fi                                                                                               "
		+	"			return isGood                                                                                    "
		+	"		}                                                                                                  "
		+ "                                                                                                      "
		+	"		fun betterGuess(float a, float b) : float {                                                        "
		+	"				return ((b + (a / b)) / 2)                                                                     "
		+	"			}                                                                                                ");
//		showTree();

	}
	
	@Test
	public void localtest() throws Exception
	{
		String text = 
				"	i <- 1                                    "    
						+"	j <- 2                                    "    
						+"	b <- false                                "    
						+"	                                          "    
						+"	print i <= j | b			# expect true      \n "    
						+"	print i <= j & b			# expect false      \n"    
						+"	print 42 div j = 21 | b		# expect true   \n"    
				;
		doParse(text);
//		showTree();
	}
	
	@Test
	public void iterativeStatement()
	{
		String text = "input a "
				+ "do "
				+ "   a <= 5 :: print 1"
				+ "   a > 5 :: { b <- 3+a; print b }"
				+ "od";
		doParse(text);
//		showTree();
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
	private void doParse(String inputText)
	{
		makeParser("program test " + inputText);
		tree = parser.dijkstraText();
		assertTrue(true);
	}

	private void doObjParse(String inputText)
	{
		makeParser(inputText);
		tree = parser.dijkstraText();
		assertTrue(true);
	}	
	/**
	 * Call this routine to display the parse tree. Hit ENTER on the console to continue.
	 */
	private void showTree()
	{
		System.out.println(tree.toStringTree());
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
