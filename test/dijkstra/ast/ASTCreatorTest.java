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

package dijkstra.ast;

import static org.junit.Assert.*;

import org.antlr.v4.runtime.*;
import org.junit.*;

import dijkstra.lexparse.DijkstraParser;
import dijkstra.utility.*;
import static dijkstra.ast.ASTNodeFactory.*;
import static dijkstra.lexparse.DijkstraParser.*;
import static dijkstra.utility.DijkstraType.*;

/**
 * Test cases for the ASTNode and ASTCreator classes.
 * @version Feb 14, 2015
 */
public class ASTCreatorTest
{
	private DijkstraParser parser;
	private ParserRuleContext tree;
	private ASTCreator creator;
	private ASTNode root;
	
	@Test
	public void declProgram()
	{
		doAST("int i");
		System.out.println(root.toString());
		String s = "(DIJKSTRA type=UNDEFINED (PROGRAM type=UNDEFINED, name=test (VARIABLEDECL type=INT (ID type=UNDEFINED, token=i))))";
//		String s = "(PROGRAM type=UNDEFINED, name=test (DECL type=INT (ID type=UNDEFINED, token=i)))";
		assertEquals(s, root.toString());
	}
	
	@Test
	public void assignId()
	{
		doAST("i <- a");
		System.out.println(root.toString());
		String s = "(DIJKSTRA type=UNDEFINED (PROGRAM type=UNDEFINED, name=test (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (VAR type=UNDEFINED (ID type=UNDEFINED, token=i))) (EXPRLIST type=UNDEFINED (ID type=UNDEFINED, token=a)))))";
//		String s = "(PROGRAM type=UNDEFINED, name=test (ASSIGN type=UNDEFINED "
//				+ "(ID type=UNDEFINED, token=i) (ID type=UNDEFINED, token=a)))";
//		assertEquals(s, root.toString());
	}
	
	@Test
	public void assignBooleanConstant()
	{
		doAST("b <- true");
		System.out.println(root.toString());
		String s = "(DIJKSTRA type=UNDEFINED (PROGRAM type=UNDEFINED, name=test (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=b)) (EXPRLIST type=UNDEFINED (CONSTANT type=BOOLEAN, token=true)))))";

//		String s = "(PROGRAM type=UNDEFINED, name=test "
//				+ "(ASSIGN type=UNDEFINED (ID type=UNDEFINED, token=b) (CONSTANT type=BOOLEAN, token=true)))";
		assertEquals(s, root.toString());
	}
	

	@Test
	public void assignNegative()
	{
		doAST("i <- -2;");
		System.out.println(root.toString());
		String s = "(DIJKSTRA type=UNDEFINED (PROGRAM type=UNDEFINED, name=test (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=i)) (EXPRLIST type=UNDEFINED (UNARYEXPR type=INT, token=- (CONSTANT type=INT, token=2))))))";

//		String s = "(PROGRAM type=UNDEFINED, name=test (ASSIGN type=UNDEFINED (ID type=UNDEFINED, token=i) "
//				+ "(UNARYEXPR type=INT, token=- (CONSTANT type=INT, token=2))))";
		assertEquals(s, root.toString());
	}
	
	@Test
	public void assignNot()
	{
		doAST("b <- ~false");
		System.out.println(root.toString());
		String s = "(DIJKSTRA type=UNDEFINED (PROGRAM type=UNDEFINED, name=test (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=b)) (EXPRLIST type=UNDEFINED (UNARYEXPR type=BOOLEAN, token=~ (CONSTANT type=BOOLEAN, token=false))))))";
		assertEquals(s, root.toString());
	}

	
	@Test
	public void Multiassign()
	{
		doAST("a, b, c, d <- ~false, 2, x[3], myclass.var");
		System.out.println(root.toString());
		String s = "(DIJKSTRA type=UNDEFINED (PROGRAM type=UNDEFINED, name=test (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=a) (ID type=UNDEFINED, token=b) (ID type=UNDEFINED, token=c) (ID type=UNDEFINED, token=d)) (EXPRLIST type=UNDEFINED (UNARYEXPR type=BOOLEAN, token=~ (CONSTANT type=BOOLEAN, token=false)) (CONSTANT type=INT, token=2) (ARRARYACCESSOR type=UNDEFINED (ID type=UNDEFINED, token=x) (CONSTANT type=INT, token=3)) (CLASSATTIACCESSOR type=UNDEFINED (ID type=UNDEFINED, token=myclass) (ID type=UNDEFINED, token=var))))))";
		assertEquals(s, root.toString());
	}
	
	@Test
	public void assignMultiplyExpression()
	{
		doAST("b <- 7 * 4");
		ASTDisplayer displayer = new ASTDisplayer();
		System.out.println(root.accept(displayer));
		System.out.println(root.toString());
		String s = "(DIJKSTRA type=UNDEFINED (PROGRAM type=UNDEFINED, name=test (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=b)) (EXPRLIST type=UNDEFINED (BINARYEXPR type=NUMERIC, token=* (CONSTANT type=INT, token=7) (CONSTANT type=INT, token=4))))))";

//		String s = "(PROGRAM type=UNDEFINED, name=test (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (VAR type=UNDEFINED (ID type=UNDEFINED, token=b))) "
//				+ "(BINARYEXPR type=INT, token=* (CONSTANT type=INT, token=7) (CONSTANT type=INT, token=4))))";

		assertEquals(s, root.toString());

	}
	
	@Test
	public void complexAssign()
	{
		doAST("a <- b + 3 * c < 5 + d");
		ASTDisplayer displayer = new ASTDisplayer();
		System.out.println(root.accept(displayer));
		System.out.println(root.toString());
		String s = "(DIJKSTRA type=UNDEFINED (PROGRAM type=UNDEFINED, name=test (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=a)) (EXPRLIST type=UNDEFINED (BINARYEXPR type=BOOLEAN, token=< (BINARYEXPR type=INT, token=+ (ID type=UNDEFINED, token=b) (BINARYEXPR type=INT, token=* (CONSTANT type=INT, token=3) (ID type=UNDEFINED, token=c))) (BINARYEXPR type=INT, token=+ (CONSTANT type=INT, token=5) (ID type=UNDEFINED, token=d)))))))";

//		String s = "(PROGRAM type=UNDEFINED, name=test "
//				+ "(ASSIGN type=UNDEFINED "
//					+ "(ID type=UNDEFINED, token=a) "
//					+ "(BINARYEXPR type=BOOLEAN, token=< "
//						+ "(BINARYEXPR type=INT, token=+ "
//							+ "(ID type=UNDEFINED, token=b) "
//							+ "(BINARYEXPR type=INT, token=* "
//								+ "(CONSTANT type=INT, token=3) "
//								+ "(ID type=UNDEFINED, token=c))) "
//						+ "(BINARYEXPR type=INT, token=+ "
//							+ "(CONSTANT type=INT, token=5) "
//							+ "(ID type=UNDEFINED, token=d)))))";
		assertEquals(s, root.toString());
	}
	
	@Test
	public void inputStatement()
	{
		doAST("int a; input a");

		System.out.println(root.toString());
		ASTDisplayer displayer = new ASTDisplayer();
		System.out.println(root.accept(displayer));
		System.out.println(root.toString());
		String s = "(DIJKSTRA type=UNDEFINED (PROGRAM type=UNDEFINED, name=test (VARIABLEDECL type=INT (ID type=UNDEFINED, token=a)) (INPUT type=UNDEFINED (ID type=UNDEFINED, token=a))))";
		assertEquals(s, root.toString());

	}
	
	@Test
	public void outputStatement()
	{
		doAST("print 0");
		ASTDisplayer displayer = new ASTDisplayer();
		System.out.println(root.accept(displayer));
		System.out.println(root.toString());
		String s = "(DIJKSTRA type=UNDEFINED (PROGRAM type=UNDEFINED, name=test (OUTPUT type=UNDEFINED (CONSTANT type=INT, token=0))))";
		assertEquals(s, root.toString());
	}
	
	@Test
	public void ifWithOneGuard()
	{
		doAST("if a < 1 :: print 0 fi");
		ASTDisplayer displayer = new ASTDisplayer();
		System.out.println(root.accept(displayer));
		System.out.println(root.toString());
		String s = "(DIJKSTRA type=UNDEFINED (PROGRAM type=UNDEFINED, name=test (ALTERNATIVE type=UNDEFINED (GUARD type=UNDEFINED (BINARYEXPR type=BOOLEAN, token=< (ID type=UNDEFINED, token=a) (CONSTANT type=INT, token=1)) (OUTPUT type=UNDEFINED (CONSTANT type=INT, token=0))))))";

		assertEquals(s, root.toString());
	}
	
	
	@Test
	public void alternativeWithCompoundStatement()
	{
		doAST("input i; a <- 0; do "
				+ "i :: a <- a + 1; od print a ");
		ASTDisplayer displayer = new ASTDisplayer();
		System.out.println(root.accept(displayer));
		System.out.println(root.toString());
		String s = "(DIJKSTRA type=UNDEFINED (PROGRAM type=UNDEFINED, name=test (INPUT type=UNDEFINED (ID type=UNDEFINED, token=i)) (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=a)) (EXPRLIST type=UNDEFINED (CONSTANT type=INT, token=0))) (ITERATIVE type=UNDEFINED (GUARD type=UNDEFINED (ID type=UNDEFINED, token=i) (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=a)) (EXPRLIST type=UNDEFINED (BINARYEXPR type=INT, token=+ (ID type=UNDEFINED, token=a) (CONSTANT type=INT, token=1)))))) (OUTPUT type=UNDEFINED (ID type=UNDEFINED, token=a))))";
//		String s = "(PROGRAM type=UNDEFINED, name=test "
//				+ "(INPUT type=UNDEFINED "
//					+ "(ID type=UNDEFINED, token=i)) "
//				+ "(ASSIGN type=UNDEFINED "
//					+ "(ID type=UNDEFINED, token=a) "
//					+ "(CONSTANT type=INT, token=0)) "
//				+ "(ITERATIVE type=UNDEFINED "
//					+ "(ID type=UNDEFINED, token=i) "
//					+ "(COMPOUND type=UNDEFINED "
//						+ "(ASSIGN type=UNDEFINED "
//							+ "(ID type=UNDEFINED, token=a) "
//							+ "(BINARYEXPR type=INT, token=+ "
//								+ "(ID type=UNDEFINED, token=a) "
//								+ "(CONSTANT type=INT, token=1))) "
//						+ "(OUTPUT type=UNDEFINED "
//							+ "(ID type=UNDEFINED, token=a)))))";
		assertEquals(s, root.toString());
	}
	
	@Test
	public void fibonacci()
	{

		String text =
				"\n"
				+ "  int f1\n"
				+ "  int f2\n"
				+ "  input n\n"
				+ "  f1 <- 1 f2 <- 1 "
				+ "  if\n"
				+ "    n < 3 :: print 1\n"
				+ "    n > 2 :: n <- n - 2\n"
				+ "  fi\n"
				+ "  do \n"
				+ "    a >2 :: t <- f1\n"
				+ "    a >5 :: f1 <- f2\n"
				+ "    a >8 :: f2 <- t + f1\n"
				+ "  od"
				+ "  \n"
				+ "  print f2";
		doAST(text);
		ASTDisplayer displayer = new ASTDisplayer();
		System.out.println(root.accept(displayer));
		System.out.println(root.toString());
		String s = "(DIJKSTRA type=UNDEFINED (PROGRAM type=UNDEFINED, name=test (VARIABLEDECL type=INT (ID type=UNDEFINED, token=f1)) (VARIABLEDECL type=INT (ID type=UNDEFINED, token=f2)) (INPUT type=UNDEFINED (ID type=UNDEFINED, token=n)) (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=f1)) (EXPRLIST type=UNDEFINED (CONSTANT type=INT, token=1))) (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=f2)) (EXPRLIST type=UNDEFINED (CONSTANT type=INT, token=1))) (ALTERNATIVE type=UNDEFINED (GUARD type=UNDEFINED (BINARYEXPR type=BOOLEAN, token=< (ID type=UNDEFINED, token=n) (CONSTANT type=INT, token=3)) (OUTPUT type=UNDEFINED (CONSTANT type=INT, token=1))) (GUARD type=UNDEFINED (BINARYEXPR type=BOOLEAN, token=> (ID type=UNDEFINED, token=n) (CONSTANT type=INT, token=2)) (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=n)) (EXPRLIST type=UNDEFINED (BINARYEXPR type=INT, token=- (ID type=UNDEFINED, token=n) (CONSTANT type=INT, token=2)))))) (ITERATIVE type=UNDEFINED (GUARD type=UNDEFINED (BINARYEXPR type=BOOLEAN, token=> (ID type=UNDEFINED, token=a) (CONSTANT type=INT, token=2)) (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=t)) (EXPRLIST type=UNDEFINED (ID type=UNDEFINED, token=f1)))) (GUARD type=UNDEFINED (BINARYEXPR type=BOOLEAN, token=> (ID type=UNDEFINED, token=a) (CONSTANT type=INT, token=5)) (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=f1)) (EXPRLIST type=UNDEFINED (ID type=UNDEFINED, token=f2)))) (GUARD type=UNDEFINED (BINARYEXPR type=BOOLEAN, token=> (ID type=UNDEFINED, token=a) (CONSTANT type=INT, token=8)) (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=f2)) (EXPRLIST type=UNDEFINED (BINARYEXPR type=INT, token=+ (ID type=UNDEFINED, token=t) (ID type=UNDEFINED, token=f1)))))) (OUTPUT type=UNDEFINED (ID type=UNDEFINED, token=f2))))";
		assertEquals(s, root.toString());
	}

	@Test
	public void functioncallpression()
	{	
		String text =("x, y, z <- 1, 2, 3\n"
			+ "fun sum(x, y) : int {\n"
			+ "z <- x + y \n"
			+ "return z\n"
			+ "}\n"
			+ "print sum(40, 2)\n");
		doAST(text);
		ASTDisplayer displayer = new ASTDisplayer();
		System.out.println(root.accept(displayer));
		System.out.println(root.toString());
		String s = "(DIJKSTRA type=UNDEFINED (PROGRAM type=UNDEFINED, name=test (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=x) (ID type=UNDEFINED, token=y) (ID type=UNDEFINED, token=z)) (EXPRLIST type=UNDEFINED (CONSTANT type=INT, token=1) (CONSTANT type=INT, token=2) (CONSTANT type=INT, token=3))) (FUNCTIONDECL type=FUNC (ID type=UNDEFINED, token=sum) (PARALIST type=UNDEFINED (PARAM type=UNDEFINED (ID type=UNDEFINED, token=x)) (PARAM type=UNDEFINED (ID type=UNDEFINED, token=y))) (COMPOUND type=UNDEFINED (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=z)) (EXPRLIST type=UNDEFINED (BINARYEXPR type=INT, token=+ (ID type=UNDEFINED, token=x) (ID type=UNDEFINED, token=y)))) (RETURN type=UNDEFINED, token=return (EXPRLIST type=UNDEFINED (ID type=UNDEFINED, token=z))))) (OUTPUT type=UNDEFINED (FUNCCALL type=UNDEFINED (ID type=UNDEFINED, token=sum) (EXPRLIST type=UNDEFINED (CONSTANT type=INT, token=40) (CONSTANT type=INT, token=2)))))))";}	
	
	@Test
	public void Classtest()
	{
		String text ="class myclass\n "
				+ "fun methodcall(): int, int { \n"
				+ "a <- 1 b <- 2\n"
				+ "return a, b}"
				+ "fun myclass(): int {int a,b}\n"
				+ "program myprogramtest \n"
				+ "myclas x "
				+ "c, d <- x.methodcall()\n";
		
		doclassAST(text);
		ASTDisplayer displayer = new ASTDisplayer();
		System.out.println(root.accept(displayer));
		System.out.println(root.toString());
		String s = "(DIJKSTRA type=UNDEFINED (CLASSDECL type=CLASS (ID type=UNDEFINED, token=myclass) (FUNCTIONDECL type=FUNC (ID type=UNDEFINED, token=methodcall) (COMPOUND type=UNDEFINED (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=a)) (EXPRLIST type=UNDEFINED (CONSTANT type=INT, token=1))) (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=b)) (EXPRLIST type=UNDEFINED (CONSTANT type=INT, token=2))) (RETURN type=UNDEFINED, token=return (EXPRLIST type=UNDEFINED (ID type=UNDEFINED, token=a) (ID type=UNDEFINED, token=b))))) (FUNCTIONDECL type=FUNC (ID type=UNDEFINED, token=myclass) (COMPOUND type=UNDEFINED (VARIABLEDECL type=INT (ID type=UNDEFINED, token=a) (ID type=UNDEFINED, token=b))))) (PROGRAM type=UNDEFINED, name=myprogramtest (VARIABLEDECL type=CLASS (ID type=UNDEFINED, token=myclas) (ID type=UNDEFINED, token=x)) (ASSIGN type=UNDEFINED (VARLIST type=UNDEFINED (ID type=UNDEFINED, token=c) (ID type=UNDEFINED, token=d)) (EXPRLIST type=UNDEFINED (METHODCALL type=UNDEFINED (ID type=UNDEFINED, token=methodcall) (ID type=UNDEFINED, token=x))))))";
		assertEquals(s, root.toString());
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
	private String doParse(String inputText)
	{
		makeParser("program test " + inputText);
		tree = parser.dijkstraText();
		assertTrue(true);
		return tree.toStringTree(parser);
	}

	private void doAST(String inputText)
	{
		doParse(inputText);
		System.out.println("I am here");
		creator =  new ASTCreator();
		System.out.println("ASTCreator success");
		root = tree.accept(creator);
		System.out.println("tree.accept success");
	}
	
	/**
	 * This method performs the parse. If you want to see what the tree looks like, use
	 * 		<br><code>System.out.println(tree.toStringTree());<code></br>
	 * after calling this method.
	 * @param inputText the text to parse
	 */
	private String doclassParse(String inputText)
	{
		makeParser(inputText);
		tree = parser.dijkstraText();
		assertTrue(true);
		return tree.toStringTree(parser);
	}	
	private void doclassAST(String inputText)
	{
		doclassParse(inputText);
		System.out.println("2parse success");
		creator =  new ASTCreator();
		System.out.println("2creater success");
		root = tree.accept(creator);
		System.out.println("2accept tree success");
	}
}
