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

import static org.junit.Assert.*;

import org.antlr.v4.runtime.*;
import org.junit.*;

import dijkstra.ast.*;
import dijkstra.ast.ASTNodeFactory.IDNode;
import dijkstra.lexparse.DijkstraParser;
import dijkstra.utility.ASTDisplayer;
import dijkstra.utility.DijkstraFactory;
import static dijkstra.ast.ASTNodeFactory.*;
import static dijkstra.utility.DijkstraType.*;

/**
 * Description
 * @version Feb 15, 2015
 */
public class SymbolCreatorTest
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
	public void singleDeclaration()
	{
		doSymbol("int i");

		String s = "(DIJKSTRA type=UNDEFINED (PROGRAM type=UNDEFINED, name=test (VARIABLEDECL type=INT (ID type=UNDEFINED, token=i))))";
		System.out.println("1");
//		assertEquals(s, ast.toString());
		System.out.println("1");
		assertEquals(1, stm.getCurrentSymbolTable().getNumberOfSymbols());
		System.out.println("2");
		assertNotNull(stm.getSymbol("i"));
		System.out.println("3");
	}
	
	@Test
	public void nestedDeclaration()
	{
		doSymbol("int i { boolean i }");
		Symbol s = stm.getSymbol("i");	// the int i
		assertEquals(INT, s.getType());
		s = stm.getSymbolTable(1).getSymbol("i");
		assertEquals(1, stm.getCurrentSymbolTable().getNumberOfSymbols());
		assertEquals(BOOLEAN, s.getType());
	}
	
	@Test(expected=DijkstraSymbolException.class)
	public void funnoreturn()
	{
		doSymbol("fun myfucn(int a,boolean b): int \n"
				+ " { int c; \n"
				+ " c <- 5;\n"
				+ " }");
	}
	
	@Test(expected=DijkstraSymbolException.class)
	public void funnoreturnvalueerror()
	{
		doSymbol("fun myfucn(int a,boolean b): int, int\n"
				+ " { int c; \n"
				+ " c <- 5;\n"
				+ " return c}");
	}
	
	@Test(expected=DijkstraSymbolException.class)
	public void proreturn()
	{
		doSymbol("proc myproc(int a,boolean b)\n"
				+ "{ int c; \n"
				+ " c <- 5;\n"
				+ " return c}");
	}
	
	@Test(expected=DijkstraSymbolException.class)
	public void twoDeclarationsOfSameVariable()
	{
		doSymbol("int i; int i;");
	}
	
	@Test(expected=DijkstraSymbolException.class)
	public void useBeforeDefInPrint()
	{
		doSymbol("print a;");
	}
	
	@Test(expected=DijkstraSymbolException.class)
	public void useBeforeDefInAssigment()
	{
		doSymbol("a <- b");
	}
	@Test
	public void Assigment()
	{
		doSymbol("int a, b; a <- b");
	}
	@Test
	public void defineInInput()
	{
		doSymbol("input a");
		Symbol s = stm.getSymbol("a");
		assertEquals(UNDEFINED, s.getType());
	}
	
	@Test
	public void itetative()
	{
		doSymbol("int a, b; \n"
				+ "do\n"
				+ " a > b :: print true\n"
				+ " a < b :: print false\n"
				+ "od");

		Symbol s = stm.getSymbol("b");
		assertEquals(INT, s.getType());
	}
	
	@Test
	public void defineThenInput()
	{
		doSymbol("boolean a; input a");
		Symbol s = stm.getSymbol("a");
		assertEquals(BOOLEAN, s.getType());
		IDNode id = (IDNode) ast.getChild(0).getChild(1).getChild(0);
		assertTrue(s == id.symbol);
	}
	
	@Test
	public void arraytest()
	{
		doSymbol("int [10] a");
		Symbol s = stm.getSymbol("a");
		assertEquals(INT, s.getType());
	}
	
	@Test
	public void arrayaccesstest()
	{
		doSymbol("int [10] a\n"
				+ "a[0] <- 1");
		Symbol s = stm.getSymbol("a");
		assertEquals(INT, s.getType());
	}
	
	
	@Test
	public void funInput()
	{
		doSymbol("fun myfucn(int a,boolean b): int \n"
				+ " { int c; \n"
				+ " c <- 5;\n"
				+ " return c}");
		Symbol s1 = stm.getSymbol("myfucn");
		System.out.println(s1.getType());
		assertEquals(FUNC, s1.getType());

		Symbol s2 = stm.getSymbol("b");
		System.out.println(s2.getType());
		assertEquals(BOOLEAN, s2.getType());
		

		stm.enterScope(1);
		Symbol s3 = stm.getSymbol("c");
		System.out.println(s3.getType());
		assertEquals(INT, s3.getType());
		stm.exitScope();
	}
	
	@Test
	public void funcall()
	{
		doSymbol("fun myfucn(int a,boolean b): int \n"
				+ " { int c; \n"
				+ " c <- 5;\n"
				+ " return c}\n"
				+ " int x,y "
				+ "boolean z "
				+ "x <- myfucn(y,z)");
		Symbol s1 = stm.getSymbol("myfucn");
		System.out.println(s1.getType());
		assertEquals(FUNC, s1.getType());

		Symbol s2 = stm.getSymbol("b");
		System.out.println(s2.getType());
		assertEquals(BOOLEAN, s2.getType());

		Symbol s4 = stm.getSymbol("x");
		System.out.println(s4.getType());
		assertEquals(INT, s4.getType());
		

		stm.enterScope(1);
		Symbol s3 = stm.getSymbol("c");
		System.out.println(s3.getType());
		assertEquals(INT, s3.getType());
		stm.exitScope();
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
		creator = new ASTCreator();
		ast = tree.accept(creator);
		ASTDisplayer displayer = new ASTDisplayer();
		System.out.println(ast.accept(displayer));
		System.out.println(ast.toString());
	}
	
	private void doSymbol(String inputText)
	{
		doAST(inputText);
		symbolCreator = new SymbolCreator();
		ast.accept(symbolCreator);
		ASTDisplayer displayer = new ASTDisplayer();
		System.out.println(ast.accept(displayer));
		System.out.println(ast.toString());
	}
}
