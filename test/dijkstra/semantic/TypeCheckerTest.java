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

import static org.junit.Assert.*;

import org.antlr.v4.runtime.*;
import org.junit.*;

import dijkstra.ast.*;
import dijkstra.semantic.DijkstraSemanticException;
import dijkstra.lexparse.DijkstraParser;
import dijkstra.symbol.*;
import dijkstra.utility.*;
import static dijkstra.utility.DijkstraType.*;

/**
 * Description
 * @version Feb 17, 2015
 */
public class TypeCheckerTest
{
	private DijkstraParser parser;
	private ParserRuleContext tree;
	private ASTCreator creator;
	private ASTNode ast;
	private SymbolCreator symbolCreator;
	private SymbolTableManager stm;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		stm = SymbolTableManager.getInstance();
		stm.reset();
	}

	@Test
	public void declaration()
	{
		doTypeCheck("int i");
		assertEquals(INT, stm.getSymbol("i").getType());
	}
	
	@Test
	public void singleAssign()
	{
		doTypeCheck("int i\n"
				+ "i <- 1");
		assertEquals(INT, ast.getChild(0).getChild(0).type);		// assign should be INT
		assertEquals(INT, stm.getSymbol("i").getType());
	}
	
	@Test(expected=DijkstraSemanticException.class)
	public void badTypeAssign()
	{
		doTypeCheck("int i; i <- false");
	}
	
	@Test
	public void outputBoolean()
	{
		doTypeCheck("print true");
		assertEquals(BOOLEAN, ast.getChild(0).getChild(0).type);	// print node should be BOOLEAN
	}
	
	@Test
	public void booleanUnaryExpression()
	{
		doTypeCheck("print ~true");
		assertEquals(BOOLEAN, ast.getChild(0).getChild(0).type);
	}
	
//	@Test
//	public void integerUnaryExpression()
//	{
//		doTypeCheck("int b\n"
//				+ " input a;\n"
//				+ " b <- -a");
//		assertEquals(INT, ast.getChild(0).getChild(1).getChild(1).getChild(0).getChild(0).type);	// input should now be int
//	}
	
	@Test(expected=DijkstraSemanticException.class)
	public void badInferredType()
	{
		doTypeCheck("a <- 1 + 2; print ~a");
	}
	
//	@Test
//	public void complexRelationalExpression()
//	{
//		doTypeCheck("int a \n"
//				+ "a <- 5 + 7 < 6 * 3");
//		assertEquals(BOOLEAN, stm.getSymbol("a").getType());
//	}
	
	@Test
	public void complexRelationalExpression1()
	{
		doTypeCheck("boolean a \n"
				+ "a <- 5 + 7 < 6 * 3");
		assertEquals(BOOLEAN, stm.getSymbol("a").getType());
	}
	@Test
	public void complexEqualityExpressionOfInt()
	{
		doTypeCheck("boolean a \n"
				+ "a <- 6 * 7 = 84 / 2");
		assertEquals(BOOLEAN, stm.getSymbol("a").getType());
	}
	@Test
	public void floattest()
	{
		doTypeCheck("float a , d\n"
				+ "int b,c \n"
				+ " input b; "
				+ "a <- b "
				+ "c <- a \n"
				+ "d <- a");
		assertEquals(FLOAT, stm.getSymbol("a").getType());
		assertEquals(FLOAT, stm.getSymbol("c").getType());
		assertEquals(FLOAT, stm.getSymbol("d").getType());
	}
	
	
	@Test
	public void floatassigntest()
	{
		doTypeCheck("float a , d\n"
				+ "int b,c \n"
				+ " input b; "
				+ "a <- b "
				+ "c <- a \n"
				+ "d <- a "
				+ "a, b ,c <- 1, 2 ,3");
		assertEquals(FLOAT, stm.getSymbol("a").getType());
		assertEquals(FLOAT, stm.getSymbol("c").getType());
		assertEquals(FLOAT, stm.getSymbol("d").getType());
	}
	
	@Test
	public void arraytest()
	{
		doTypeCheck("float a , d\n"
				+ "int b,c \n"
				+ " input b; "
				+ "a <- b "
				+ "c <- a \n"
				+ "d <- a");
		assertEquals(FLOAT, stm.getSymbol("a").getType());
		assertEquals(FLOAT, stm.getSymbol("c").getType());
		assertEquals(FLOAT, stm.getSymbol("d").getType());
	}
	
	@Test(expected=DijkstraSemanticException.class)
	public void funtest()
	{
		doTypeCheck("fun myfucn(int a,boolean b): boolean \n"
				+ " { int c; \n"
				+ " c <- 5;\n"
				+ " return c}");
	}
	
	@Test(expected=DijkstraSemanticException.class)
	public void modanddivUsingFloat() throws Exception
	{
		doTypeCheck("a <- 10.2; print a "
				+ "b <- 3.7; print b "
				+ "print a mod b "
				+ "print a div b ");
	} 
	
	@Test
	public void funrurntest()
	{
		doTypeCheck("fun myfucn(int a,boolean b): int \n"
				+ " { int c; \n"
				+ " c <- 5;\n"
				+ " return c}\n"
				+ " int x,y "
				+ "boolean z "
				+ "x <- myfucn(y,z)");
	}
	
	@Test
	public void iterativeWithTwoGuards()
	{
		String s ="do"
				+ "  5 < 13 :: print true"
				+ "  7 > 3 :: { print 2; print true }"
				+ "od";
		doTypeCheck(s);
		assertEquals(BOOLEAN, ast.getChild(0).getChild(0).getChild(1).getChild(0).type);	// 2d guard expr type
	}
		
	
//	@Test
//	public void iterativeStatment()
//	{
//		doTypeCheck("loop 5 / 3  print true");
//		assertEquals(INT, ast.getChild(0).getChild(0).getChild(0).type);	// expression type
//	}
//
//	@Test(expected=DijkstraSemanticException.class)
//	public void iterativeStatementWithBooleanExpression()
//	{
//		doTypeCheck("loop 5 < 3 print false");
//	}
	
	@Test
	public void alternativeWithOneGuard()
	{
		doTypeCheck("if true :: print 1 fi");
		assertEquals(BOOLEAN, ast.getChild(0).getChild(0).getChild(0).getChild(0).type);	// guard expr. type
	}
	
	@Test
	public void alternativeWithTwoGuards()
	{
		String s = "if"
				+ "  5 < 13 :: print true"
				+ "  7 > 3 :: { print 2; print true }"
				+ "fi";
		doTypeCheck(s);
		assertEquals(BOOLEAN, ast.getChild(0).getChild(0).getChild(1).getChild(0).type);	// 2d guard expr type
	}
	
	

	@Test
	public void compoundStatement()
	{
		doTypeCheck("{{{ print true }}}");
	}
	
	@Test
	public void fibonacci()
	{
		String text =
				"\n"
				+ "  int f1\n"
				+ "  int f2\n"
				+ "  input n"
				+ "  int a "
				+ "  int t\n"
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
		doTypeCheck(text);
		assertEquals(INT, stm.getSymbol("n").getType());
//		assertNull(stm.getSymbol("t"));
//		assertEquals(INT, stm.getSymbolTable(1).getSymbol("t").getType());
//		assertEquals(BOOLEAN, stm.getSymbolTable(1).getSymbol("unnecessary_boolean").getType());
	}
	
	// Helper methods
	private void makeParser(String inputText)
	{
		parser = DijkstraFactory.makeParser(new ANTLRInputStream(inputText));
	}
	
	/**
	 * This method performs the parse. If you want to see what the tree looks like, use <br>
	 * <code>System.out.println(tree.toStringTree());<code></br>
	 * after calling this method.
	 * 
	 * @param inputText
	 *            the text to parse
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
		System.out.println(tree.toStringTree());
		creator = new ASTCreator();
		ast = tree.accept(creator);
		System.out.println(ast.toString());

	}
	
	private void doTypeCheck(String inputText)
	{
		doAST(inputText);
		symbolCreator = new SymbolCreator();
		ast.accept(symbolCreator);
//		System.out.println("here");
//		Symbol s = stm.getSymbol("i");	// the int i
//		assertEquals(INT, s.getType());
//		System.out.println("here");
		System.out.println(ast.toString());
		TypeChecker checker = new TypeChecker();
		do {
			ast.accept(checker);
		} while (checker.checkAgain());
//		System.out.println(ast.toString());
	}
}
