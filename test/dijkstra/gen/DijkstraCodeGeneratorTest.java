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

import static org.junit.Assert.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.antlr.v4.runtime.*;
import org.junit.*;

import dijkstra.ast.*;
import dijkstra.ast.ASTNode.ASTNodeType;
import dijkstra.ast.ASTNodeFactory.ClassdeClarationNode;
import dijkstra.ast.ASTNodeFactory.ProgramNode;
import dijkstra.lexparse.DijkstraParser;
import dijkstra.runtime.DijkstraRuntime;
import dijkstra.semantic.TypeChecker;
import dijkstra.symbol.*;
import dijkstra.utility.DijkstraFactory;

/**
 * Code generation tests.
 * @version Feb 22, 2015
 */
public class DijkstraCodeGeneratorTest extends ClassLoader
{
	private DijkstraParser parser;
	private ParserRuleContext tree;
	private ASTCreator creator;
	private ASTNode ast;
	private SymbolCreator symbolCreator;
	private SymbolTableManager stm;
	private byte[] code;
	private ArrayList<byte[]> objectcodelist = new ArrayList<byte[]>();
	private byte[] oneObjectCode;
	private static String programName = new String();
	private static ArrayList<String> subclassname = new ArrayList<String>();
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		stm = SymbolTableManager.getInstance();
		stm.reset();
		JVMInfo.reset();
		DijkstraRuntime.setInputs(null);
	}
	
	@Test
	public void printIntConstant() throws Exception
	{
		runCode("print 1");
	}
	
	@Test
	public void printTrue() throws Exception
	{
		runCode("print true");
	}
	
	@Test
	public void printFalse() throws Exception
	{
		runCode("print false");
	}
	@Test
	public void printdiv() throws Exception
	{
		runCode("print 42 div 7");
	}
	
	
	
	@Test
	public void printLOGICAL() throws Exception
	{
		runCode("print true | false & false");
	}
	
	@Test
	public void simplearray() throws Exception
	{
		String s =("int [10] a\n"
				+ "a[0] <- 3;\n"
				+ " print a[0] \n"
				);
//		writeCode(s);
		runCode(s);
	}
	
	@Test
	public void arraytest() throws Exception
	{
		String s ="int [10] a\n"
				+ " a[0] <- 3;\n"
				+ " a[1] <- 4 \n"
				+ " a[2] <- 5 \n"
				+ " a[3] <- 6 \n"
				+ " a[4] <- 7 \n"
				+ " print a[0] \n"
				+ " print a[3] \n"
				+ " print a[2] \n"
				+ " print a[7] \n"
				;
//		writeCode(s);
		runCode(s);
	} 
	
	@Test
	public void firsttest() throws Exception
	{
		String s ="int a, b\n"
				+ " a, b <- 1, 2;\n"
				+ " print a \n"
				+ " print b";
//		writeCode(s);
		runCode(s);
	} 
	
	@Test
	public void arraycmp() throws Exception
	{
		String s ="float[3] a \n"
				+ " a[0], a[1] <- 1.0, 2.0;\n"
				+ " print a[0] "
				+ " print a[1] "
				+ "print a[0] <= a[1] \n"
				;
//		writeCode(s);
		runCode(s);
	} 
	
	@Test
	public void simpleAssign() throws Exception
	{
		runCode("a <- 1; print a");
	} 
	
	@Test
	public void orandtest() throws Exception
	{
		runCode("a , b <- true, false ; print a&b; print a|b");
	} 
	
	@Test
	public void orandtest1() throws Exception
	{
		runCode("a , b <- true, true ; print a&b; print a|b");
	} 
	
	@Test
	public void orandtest2() throws Exception
	{
		runCode("a , b <- false, false ; print a&b; print a|b");
	} 
	@Test
	public void floatAssign() throws Exception
	{
		String s =""
				+ "a <- 1.5; print a "
				+ "a <- 1.5 + 3.6; print a "
				+ "a <- 2.3 b <- 4.2 print a*b "
				+ "a <- 9.4 b <- 1.3 print a/b "
				+ "a <- 10 b <- 2.0 print a/b "
				+ "a <- 7.4234 b <- 4.124323 print a-b "
				+ "a <- 10 b <- 5 print a/b "
				;
		runCode(s);
	} 
	@Test
	public void floatandintAssign() throws Exception
	{
		String s ="a <- 10 b <- 2.0 print a/b ";
//		writeCode(s);
		runCode(s);
	} 
	@Test
	public void modanddiv() throws Exception
	{
		String s ="a <- 10; print a "
				+ "b <- 3; print b "
				+ "print a mod b "
				+ "print a div b "
				;
		runCode(s);
	} 
	
	
	@Test
	public void Rational() throws Exception
	{
		String s ="	x, y, z <- 3, -3, 7              "
	+"	f, g, h, i <- 3.4, 42.0, 7.0, 3.0"
	+"	                                 "
	+"	print x < y                      "
	+"	print y >= y                     "
	+"	print z > y * y                  "
	+"	print f < g                      "
	+"	print z * h < g                  "
	+"	print g < 2 * f * h              "
	+"	print g ~= g                     "
	+"	print x <= i                     "
	+"	print x < y = f < g              "
	+"	print x < y = f < g = h < i      "
	;
		runCode(s);
} 
	
	
	@Test
	public void proc() throws Exception
	{
		String s =
				" int i                                   "
						+" 	proc incr(int x) "
						+ "{ "
						+ "    i <- i + x"
						+ "}        "
						+" 	                                       "
						+" 	i <- 0                                 "
						+" 	incr(5)                                "
						+" 	print i                                "
						+" 	incr(32)                               "
						+" 	print i                                "
	;
//		writeCode(s);
		runCode(s);
	} 
	
	@Test
	public void outside() throws Exception
	{
		String s="x, y, z <- 1, 2, 3\n"
			+ "fun sum(x, y) : int {\n"
			+ "z <- x + y # outer z\n"
			+ "return z\n"
			+ "}\n"
			+ "print sum(40, 2)\n";
//			writeCode(s);
			runCode(s);
		} 
	
	
	@Test
	public void Functemp() throws Exception
	{
		String s ="fun convFtoC(float temp) : float {       "
	+"	return (temp - 32) * (5 / 9)           "
	+"}                                        "
	+"                                         "
	+"fun convCtoF(float temp) : float {       "
	+"	return temp * (9 / 5) + 32             "
	+"}                                        "
	+"                                         "
	+"outputTemp <- convFtoC(68.0)             "
	+"print outputTemp                         "
	+"outputTemp <- convCtoF(20.0)             "
	+"print outputTemp                         "
	;
//		writeCode(s);
		runCode(s);
	} 	
	
	
	@Test
	public void tEMP() throws Exception
	{
		
		String s ="	isFahrenheit <- true                                       "
	+"	inDegrees <- 68                                            "
	+"	float outTemp                                              "
	+"	                                                           "
	+"	if                                                         "
	+"		isFahrenheit :: outTemp <- (inDegrees - 32) * (5 / 9)    "
	+"		~isFahrenheit :: outTemp <- inDegrees * (9 / 5) + 32     "
	+"	fi                                                         "
	+"	                                                           "
	+"	print 20.0                                                 "
	+"                                                             "
	;
//	writeCode(s);
	runCode(s);
} 
	
	
	@Test(expected=Exception.class)
	public void TYPE() throws Exception
	{
		
		String s ="	input i          "
	+"	f <- 5 / 3       "
	+"	i <- f           "
	+"	i1 <- i div 2    "
	;
//		writeCode(s);
		runCode(s);
	}
	
	
	@Test
	public void SORT6() throws Exception
	{
		
		String s ="	 i <- 0   "
				+ " float[2] array  "
				+ " 	array[0], array[1] <- 5, 3;  "
	+"	array[i],array[i+1] <- array[i+1], array[i]          "
	+"print array[0];                                                                                                                                                               "
	+"print array[1];  "		
	;
//		writeCode(s);
		runCode(s);
	}
	
	
	@Test
	public void Functemp1() throws Exception
	{
		String s ="	fun convFtoC(float temp) : float {   "
	+"		return (temp - 32) * (5 / 9)       "
	+"	}                                    "
	+"	                                     "
	+"	fun convCtoF(float temp) : float {   "
	+"		return temp * (9 / 5) + 32         "
	+"	}                                    "
	+"	                                     "
	+"	outputTemp <- convFtoC(68)           "
	+"	print outputTemp                     "
	+"	outputTemp <- convCtoF(20.0)         "
	+"	print outputTemp                     "
	;
//		writeCode(s);
		runCode(s);
	} 
	
	
	@Test
	public void FuncSort4() throws Exception
	{
		String s ="                                                                                                                                                                 "
	+"fun isDone(int i):int"
	+ "{                                                                                                                                                          "
	+ " if                                                                                                                                                                           "
	+"	  i > 10  ::  return 5;    "
	+"	  i < 10  ::  return 6;                                                                                                                                                         "
	+"	fi "
	+ " return 7                                                                                                                                                                          "
	+"}                                                                                                                                                                              "
	+"print isDone(9)                                                                                                                                                         "
	;
//		writeCode(s);
		runCode(s);
	}
	
	
	@Test
	public void FuncSort5() throws Exception
	{
		String s =" float[2] array  "
				+ " 	array[0], array[1] <- 5, 3;  "
				+ "  int i"
				+ "  i <- 0                                                                     "
				+ "if                                                                                                                                                                             "
	+"						array[i] > array[i+1] :: {array[i],array[i+1] <- array[i+1], array[i]; i <- i+1;}                                                                                              "
//	+"						true:: i<- i+1;                                                                                                                                                                "
	+"					fi "
	+"print array[0];                                                                                                                                                               "
	+"print array[1];  "
	;
//		writeCode(s);
		runCode(s);
	}
	
	@Test
	public void FuncSort() throws Exception
	{
		String s =" float[8] array;                                                                                                                                                                "
	+"                                                                                                                                                                               "
	+"fun isDone():boolean{                                                                                                                                                          "
	+"	if                                                                                                                                                                           "
	+"	  (array[0]<= array[1]) & (array[1]<= array[2]) &(array[2]<= array[3]) &(array[3]<= array[4]) &(array[4]<= array[5]) &(array[5]<= array[6]) & (array[6]<= array[7]) ::       "
	+"		return true;                                                                                                                                                               "
	+"		true:: return false;                                                                                                                                                         "
	+"	fi                                                                                                                                                                           "
	+"                                                                                                                                                                               "
	+"	return false;                                                                                                                                                                "
	+"}                                                                                                                                                                              "
	+"array[0], array[1], array[2], array[3], array[4], array[5], array[6], array[7] <- 5, 3, 2, 1, 6, 9, 4, 19;                                                                     "
	+"boolean done;                                                                                                                                                                  "
	+"done <- false;                                                                                                                                                                 "
	+"do                                                                                                                                                                             "
	+"~done ::                                                                                                                                                                       "
	+"                                                                                                                                                                               "
	+"{"
	+ "int i;                                                                                                                                                                        "
	+"	i <- 0;                                                                                                                                                                        "
	+"		do                                                                                                                                                                             "
	+"			i<7 ::                                                                                                                                                                         "
	+"				{                                                                                                                                                                              "
	+"					if                                                                                                                                                                             "
	+"						array[i] > array[i+1] :: {array[i],array[i+1] <- array[i+1], array[i]; i <- i+1;}                                                                                              "
	+"						true:: i<- i+1;                                                                                                                                                                "
	+"					fi                                                                                                                                                                             "
	+"				}                                                                                                                                                                              "
	+"		od                                                                                                                                                                             "
	+"                                                                                                                                                                               "
	+"	done <- isDone();                                                                                                                                                              "
	+"}                                                                                                                                                                              "
	+"od                                                                                                                                                                             "
	+"                                                                                                                                                                               "
	+"print array[0];                                                                                                                                                                "
	+"print array[1];                                                                                                                                                                "
	+"print array[2];                                                                                                                                                                "
	+"print array[3];                                                                                                                                                                "
	+"print array[4];                                                                                                                                                                "
	+"print array[5];                                                                                                                                                                "
	+"print array[6];                                                                                                                                                                "
	+"print array[7];                                                                                                                                                                "
	;
//		writeCode(s);
		runCode(s);
	} 
	
	@Test
	public void FuncSort1() throws Exception
	{
		String s =" float[8] array;"
				;
//				writeCode(s);
				runCode(s);
			} 
	@Test
	public void FuncSort2() throws Exception
	{
		String s =" int[8] array;"
				;
//				writeCode(s);
				runCode(s);
			}
	@Test
	public void basicAlternative() throws Exception
	{
		String s =" boolean b int a "
				+ " b <- true a <- 1\n"
				+ "if\n"
				+ "  b :: a <- -5\n"
				+ "fi\n"
				+ "print a";
//		writeCode(s);
		runCode(s);
	}
	
	@Test
	public void logicalNegation() throws Exception
	{
		runCode("print ~true");
	}
	
	@Test
	public void nestedUnaryMinus() throws Exception
	{
		runCode("print ----42");
	}
	
	@Test
	public void floatcmpl() throws Exception
	{
//		writeCode("a, b <- 3.0, 4.5 "
//				+ " print a<b "
//				+ "a, b <- 3.0, 4.5 "
//				+ " print a>b");
		runCode("a, b <- 3.0, 4.5 "
				+ " print a < b "
				+ "a, b <- 3.0, 4.5 "
				+ " print a>b");
	}
	
	@Test
	public void floatcmpl1() throws Exception
	{
		runCode("a, b <- 3.0, 4.5 "
				+ " print a<=b ");
	}
	
	@Test
	public void floatcmpl2() throws Exception
	{
		runCode("a, b <- 3.0, 4.5 "
				+ " print a>b ");
	}
	
	@Test
	public void floatcmpl3() throws Exception
	{
		runCode("a, b <- 3.0, 4.5 "
				+ " print a>=b ");
	}
	
	@Test
	public void floatcmpl4() throws Exception
	{
		runCode("a, b <- 3.0, 3.0 "
				+ " print a>=b ");
	}
	
	@Test
	public void multipleGuardsInAlternative() throws Exception
	{
		String s = " "
//				+ "boolean i,j,k\n"
//				+ "int x;"
				+ "i <- true; j <- false; k <- true\n"
				+ "x <- 0\n"
				+ "if\n"
				+ "  i :: x <- 42\n"
				+ "  j :: x <- 1\n"
				+ "  k :: x <- 2\n"
				+ "fi\n"
				+ "print x";
//		writeCode(s);
		runCode(s);
	}
	
	@Test
	public void funcalltest1() throws Exception
	{
		String text = "  x <- true "
//				+ "  continue <- 2 "
				+ "if                                                                                "
	+"					x  ::   continue <- 1                         "
	+"			fi "
	+ "   print continue "
	;
		writeCode(text);
//		runCode(text);
	}
	
	
	
	
	@Test
	public void iterative() throws Exception
	{
		String s = ""
//				+ "int x, y;"
				+ "x, y <- 9, 20\n"
				+ "print x\n"
				+ "print y\n"
				+ "do\n"
				+ "  x < 10 :: x <- x + 1\n"
				+ "  y > 10 :: y <- y - 7\n"
				+ "od\n"
				+ "print x\n"
				+ "print y\n";
//		writeCode(s);
		runCode(s);
	}
	
	@Test
	public void lessThanFalse() throws Exception
	{
		runCode("print 5 < 2");
	}
	
	@Test public void lessThanTrue() throws Exception
	{
		runCode("print 2 < 5");
	}
	
	@Test
	public void greaterThanFalse() throws Exception
	{
//		writeCode("print 2 > 5");
		runCode("print 2 > 5");
	}
	
	@Test
	public void greaterThanTrue() throws Exception
	{
		runCode("print 5 > 2");
	}
	
	@Test
	public void trueEquals() throws Exception
	{
		runCode("print 2 = 2", "trueEquals");
	}
	
	@Test
	public void falseEquals() throws Exception
	{
		runCode("print 1 = 2", "falseEquals");
	}
	
	@Test
	public void trueNotEquals() throws Exception
	{
		runCode("print 42 ~= 41", "trueNotEquals");
	}
	
	@Test
	public void falseNotEquals() throws Exception
	{
		runCode("print 42 ~= 42", "falseNotEquals");
	}
	
	@Test
	public void simpleAdd() throws Exception
	{
		runCode("print 5+37", "simpleAdd-expect 42");
//		writeCode("print 5+37");
		assertEquals("i=42", DijkstraRuntime.getLastMessage());
	}
	
	@Test
	public void subtractByAddition() throws Exception
	{
		runCode("print 47+-5", "subtractByAddition");
		assertEquals("i=42", DijkstraRuntime.getLastMessage());
	}
	
	@Test
	public void simpleSubtraction() throws Exception
	{
		runCode("print 47 - 5", "simpleSubtraction");
		assertEquals("i=42", DijkstraRuntime.getLastMessage());
	}
	
	@Test 
	public void additionBySubtraction() throws Exception
	{
		runCode("print 37 --5", "additionBySubtraction");
		assertEquals("i=42", DijkstraRuntime.getLastMessage());
	}
	
	@Test 
	public void mathtest() throws Exception
	{
		runCode("a <- 12.5 b<-4.8 print (b + (a / b)) / 2", "additionBySubtraction");
		assertEquals("f=3.7020833", DijkstraRuntime.getLastMessage());
	}
	
	
	
	@Test
	public void simpleMultiplication() throws Exception
	{
		runCode("print 7*6", "simpleMultiplication");
		assertEquals("i=42", DijkstraRuntime.getLastMessage());
	}
	
	@Test 
	public void simpleDivision() throws Exception
	{
		runCode(" print 84 / 2 "
				, "simpleDivision");
		assertEquals("f=42.0", DijkstraRuntime.getLastMessage());
	}
	
//	@Test
//	public void simpleLoop() throws Exception
//	{
//		runCode("i <- 0; loop 42 i <- i + 1; print i", "simpleLoop");
//		assertEquals("i=42", DijkstraRuntime.getLastMessage());
//	}
	
//	@Test
//	public void loopZeroTimes() throws Exception
//	{
//		runCode("i <- 42; loop 0 i <- i + 1; print i", "simpleLoop");
//		assertEquals("i=42", DijkstraRuntime.getLastMessage());
//	}
	
//	@Test
//	public void nestedLoop() throws Exception
//	{
//		runCode("i <- 0 loop 7 loop 6 i <- i + 1 print i", "nestedLoop");
//		assertEquals("i=42", DijkstraRuntime.getLastMessage());
//	}
	
	@Test
	public void ifInsideLoop() throws Exception
	{
		String s = "int i\n"
				+ "i <- 0\n"
				+ "\n"
				+ "  do\n"
				+ "    i < 26 ::"
				+ "			if\n	"
				+ "				i < 10 :: i <- i + 1\n"
				+ "    			i > 9 :: i <- i + 2\n"
				+ "			fi\n"
				+ "  od\n"
				+ "\n"
				+ "print i";
//		writeCode(s);
		runCode(s, "ifInsideLoop");
//		assertEquals("i=42", DijkstraRuntime.getLastMessage());
	}
	
	
	@Test
	public void  Euclid() throws Exception
	{
		String s = "int x, y \n"
				+ " x, y <- 9, 18\n"
				+ "do\n"
				+ "	x ~= y :: \n"
				+ "		if\n"
				+ "			x > y :: x <- x - y\n"
				+ "			x < y :: y <- y - x\n"
				+ "		fi\n"
				+ "od\n"
				+ "print x ";
//		writeCode(s);
//		DijkstraRuntime.setInputs(new String[] {"9", "18"});
		runCode(s, "ifInsideLoop ");
//		assertEquals("i=42", DijkstraRuntime.getLastMessage());
	}
	

	@Test
	public void  ARTERTEST() throws Exception
	{
		String s = "howMany? <- 5 \n"
				+"			if                                                 "
				+"				howMany? < 2 :: print 1                          "
				+"				howMany? >= 2 :: {print 1; print 1}              "
				+"			fi                                                 "
				+ "print howMany? ";
//		writeCode(s);
		runCode(s);
//		assertEquals("i=42", DijkstraRuntime.getLastMessage());
	}
	
	@Test
	public void  Euclidinput() throws Exception
	{
		String s = "input x, y \n"
				+ "do\n"
				+ "	x ~= y :: \n"
				+ "		if\n"
				+ "			x > y :: x <- x - y\n"
				+ "			x < y :: y <- y - x\n"
				+ "		fi\n"
				+ "od\n"
				+ "print x ";
//		writeCode(s);
		DijkstraRuntime.setInputs(new String[] {"48", "64"});
		runCode(s, "ifInsideLoop ");
//		assertEquals("i=42", DijkstraRuntime.getLastMessage());
	}	
	
	
	
	@Test
	public void simpleIntInput() throws Exception
	{
		DijkstraRuntime.setInputs(new String[] {"41"});
//		writeCode("input i; i <- i+1 ;print i; print i+1");
		runCode("input i; i <- i+1 ; print i; print i+1", "simpleIntInput");
		assertEquals("i=43", DijkstraRuntime.getLastMessage());
	}
	
	@Test
	public void simpleIntInput2() throws Exception
	{
		DijkstraRuntime.setInputs(new String[] {"41"});
//		writeCode("input i;print i; print i+1");
		runCode("input i ; print i; print i+1", "simpleIntInput");
		System.out.println(DijkstraRuntime.getLastMessage());
		assertEquals("i=42", DijkstraRuntime.getLastMessage());
	}
	
	@Test
	public void floatIntInput2() throws Exception
	{
		DijkstraRuntime.setInputs(new String[] {"2.8"});
		runCode("input i ; print i; print i+1.2", "simpleIntInput");
//		writeCode("input i ; print i; print i+1.2");
		assertEquals("f=4.0", DijkstraRuntime.getLastMessage());
	}
	
	
	@Test
	public void simpleBooleanInput() throws Exception
	{
		DijkstraRuntime.setInputs(new String[] {"true"});
		runCode("input i; print ~i", "simpleBooleanInput");
		assertEquals("b=false", DijkstraRuntime.getLastMessage());
	}
	
	@Test(expected=Exception.class)
	public void programAbort() throws Exception
	{
		String text = "i <- 0\n"
				+ "if\n"
				+ "  i < 0 :: print -1\n"
				+ "  i > 0 :: print 1\n"
				+ "fi";
		runCode(text);
	}
	
	
	@Test
	public void funcdecrtest() throws Exception
	{
		String text =
				"fun myfun(): int \n"
				+ " {  "
				+ " int c \n"
				+ " c <- 5;\n"
				+ " return c"
				+ "}\n"
				+ "x <- 1\n"
				+ " print x ";
//		writeCode(text);
		runCode(text);
	}
	
	@Test
	public void funcalltest() throws Exception
	{
		String text = "fun myfun(): int \n"
				+ " { int c; \n"
				+ " c <- 5;\n"
				+ " return c}\n"
				+ " x <- 1 "
				+ "x <- myfun()\n"
				+ "print x";
//		writeCode(text);
		runCode(text);
	}
	
	
	@Test
	public void completetest() throws Exception
	{
		String text = "fun myfun(int x, int y): int\n"
				+ " {\n"
				+ "do\n"
				+ "	x ~= y :: \n"
				+ "		if\n"
				+ "			x > y :: x <- x - y\n"
				+ "			x < y :: y <- y - x\n"
				+ "		fi\n"
				+ "od\n"
				+ "return x }"
				+ " int a "
				+ "a <- myfun(5, 6)\n"
				+ "print a\n";
//		writeCode(text);
		runCode(text);
	}
	
	@Test
	public void funmutireturntest() throws Exception
	{
		String text = "fun myfun(): int, int\n"
				+ " {\n"
				+ " c,d <- 5,6;\n"
				+ " return c, d}\n"
				+ " int x,y "
				+ "boolean z "
				+ "x,y <- myfun()\n"
				+ "print x\n"
				+ "print y\n";
//		writeCode(text);
		runCode(text);
	}
	
	@Test(expected=Exception.class)
	public void funParametertest() throws Exception
	{
		String text = "fun myfucn(int a,boolean b): int \n"
				+ " { int c; \n"
				+ " c <- a + b;\n"
				+ " return c}\n"
				+ " int x,y "
				+ "y , z <- 1, 2 "
				+ "x <- myfucn(y,z)\n"
				+ "print x";
//		writeCode(text);
		runCode(text);
	}

	@Test
	public void funParametertest2() throws Exception
	{
		String text = "fun myfucn(int a,boolean b): int \n"
				+ " { int c; \n"
				+ " c <- a + 6;\n"
				+ " return c}\n"
				+ " int x,y "
				+ "y <- 1 "
				+ " z <- true "
				+ "x <- myfucn(y,z)\n"
				+ "print x";
//		writeCode(text);
		runCode(text);
	}

	@Test
	public void inFunAndOutsideVariableTest() throws Exception
	{
		String text = "	 "
				+ "fun sum(x, y) : int { "
				+ " z <- x + y "
				+ "return z }"
				+ "x, y, z <- 12, 5, 8 "
				+ "print x\n"
				+ "print y "
				+ "print z "
				+ "z <- sum(40, 3) "
				+ "print z \n"
				+ "print sum(x, y) \n";
//		writeCode(text);
		runCode(text);
	}
	
	@Test
	public void functioninfuntion() throws Exception
	{
		String text = "	 "
				+ "fun sum1(int x, int y) : int { "
				+ " z <- x + y "
				+ "return z }"
				+ "  "
				+ " fun sum2(int x, int y) : int"
				+ " { "
				+ " 	m <- 8 + sum1(x , 8) "
				+ "      "
				+ "	     return m  "
				+ " } "
				+ "z <- sum2(40, 3) "
				+ "print z \n"
				;
//		writeCode(text);
		runCode(text);
	}
	
	
	
	@Test
	public void proceduretest() throws Exception
	{
		String text = "proc sum(x, y) { "
				+ " int z "
				+ " z <- x + y "
				+ " print z "
				+ " return "
				+ " }"
				+ " a <- true "
				+ " if "
				+ "		a :: sum(40, 3) "
				+ " fi"
				;
//		writeCode(text);
		runCode(text);
	}
	
	@Test
	public void procandfuncwithsamenametest() throws Exception
	{
		String text = "proc sum(x, y) { "
				+ " int z "
				+ " z <- x + y "
				+ " print z "
				+ " return "
				+ " }"
				+ "  fun sum(m, n) : int"
				+ " { "
				+ "    b <- 1"
				+ "    "
				+ "    return b "
				+ " } "
				+ " a <- true "
				+ " if "
				+ "		a :: sum(40, 3) "
				+ " fi "
				+ " i <- sum(1, 2)"
				+ "  print i  "
				;
//		writeCode(text);
		runCode(text);
	}
	
	@Test
	public void fibonacciandcompoundtest() throws Exception
	{
		String text = "n <- 5"
				+ "  f1 <- 1 f2 <- 1 "
				+ "do\n"
				+ "  	n ~= 0 ::\n"
				+ "          {\n"
				+ "               t <- f1  \n "
				+ "  	          f1 <- f2 \n"
				+ "  	          f2 <- t + f1\n"
				+ "               unnecessary_boolean <- f1 < f2 * t + 3\n"
				+ "               n <- n - 1 \n"
				+ "          }\n"
				+ "  od\n"
				+ "  print f2\n"
				+ "print f1\n";
//		writeCode(text);
		runCode(text);
	}
	
	@Test
	public void fibonacci() throws Exception
	{
		String text =
				"\n"
				+ "  input n\n"
				+ "  f1 <- 1 f2 <- 1 "
				+ "  if\n"
				+ "    n < 3 :: print 1\n"
				+ "    n > 2 :: n <- n - 2\n"
				+ "  fi\n"
				+ "  do\n"
				+ "  	n ~= 0 ::\n"
				+ "          {\n"
				+ "               t <- f1  \n "
				+ "  	          f1 <- f2 \n"
				+ "  	          f2 <- t + f1\n"
				+ "               unnecessary_boolean <- f1 < f2 * t + 3\n"
				+ "               n <- n - 1 \n"
				+ "          }\n"
				+ "  od\n"
				+ "  print f2\n"
				+ "print f1\n";
		DijkstraRuntime.setInputs(new String[] {"5"});
//		writeCode(text);
		runCode(text, "fibonacci");
		assertEquals("i=3", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void classdecltest() throws Exception
	{
		String text = 
				"class myclass (int a, int b)"
				+ " int private "
				+ " int b"
				+ " program myClassProgram "
				+ " myclass classVar "
				+ " classVar <- myclass(4, 5) "
				+ " e <- 1000 "
				+ " print e "
				+ " print classVar.a "
				+ " x <- classVar.b "
				+ " print x "
				;
//		writeCode(text);
		runCode(text);
	}
	
	@Test
	public void classMethodtest() throws Exception
	{
		String text = 
				"class myclass (int a, int b)"
				+ " int private "
				+ " fun sum(int x, int y) : int"
				+ " {"
				+ "     int z"
				+ "      private <- 6;"
				+ "     z <- x + y + private"
				+ "     return z    "
				+ " }"
				+ " program myClassProgram "
				+ " myclass classVar "
				+ " classVar <- myclass(4, 5) "
				+ " e <- 1000 "
				+ " print e "
				+ " m <- classVar.sum(7, 8) "
				;
//		writeCode(text);
		runCode(text);
	}
	
	@Test(expected=Exception.class)
	public void accesslocalExceptiontest() throws Exception
	{
		String text = 
				"class myclass (int a, int b)"
				+ " int private "
				+ " fun sum(int x, int y) : int"
				+ " {"
				+ "     int z"
				+ "      private <- 6;"
				+ "     z <- x + y + private"
				+ "     return z    "
				+ " }"
				+ " program myClassProgram "
				+ " myclass classVar "
				+ " classVar <- myclass(4, 5) "
				+ " e <- 1000 "
				+ " print e "
				+ " m <- classVar.sum(7, 8) "
				+ " print m "
				+ " print classVar.private"
				;
//		writeCode(text);
		runCode(text);
	}
	
	@Test(expected=DijkstraSymbolException.class)
	public void classMethodandAccesstest() throws Exception
	{
		String text = 
				"class myclass (int a [W], int b [RW])"
				+ " int privatevar "
				+ "fun sum(int x, int y) : int"
				+ " {"
				+ "     int z   "
				+ "		privatevar <- 6"
				+ "     z <- x + y + privatevar"
				+ "     return z    "
				+ " }"
				+ " program myClassProgram "
				+ " myclass classVar "
				+ " classVar <- myclass(4, 5) "
				+ " e <- 1000 "
				+ " print e "
				+ " m <- classVar.sum(7, 8) "
				+ " print m "
				+ " n <- classVar.b "
				+ " print n"
				+ " print classVar.b"
				+ " l <- classVar.a "
				+ " print l "
				;
//		writeCode(text);
		runCode(text);
	}	
	
	@Test
	public void classandFunctiontest() throws Exception
	{
		String text = 
				"class myclass (int a [W], int b [RW])"
				+ " int localvar "
				+ " fun sum(int x, int y) : int"
				+ " {"
				+ "     int z"
				+ "     localvar <- 6"
				+ "     z <- x + y + localvar"
				+ "     return z    "
				+ " }"
				+ " program myClassProgram "
				+ " myclass classVar "
				+ " classVar <- myclass(4, 5) "
				+ " e <- 1000 "
				+ " print e "
				+ " fun sum(int x, int y) : int"
				+ " {"
				+ "     int z"
				+ "     z <- x + y "
				+ "     return z    "
				+ " } "
				+ " m <- classVar.sum(7, 8) "
				+ " print m "
				+ " classVar.b <- 25 "
				+ " result <- sum(m,classVar.b) "
				+ " print result"
				;
//		writeCode(text);
		runCode(text);
	}
	
	
	@Test
	public void classwriteattributetest() throws Exception
	{
		String text = 
				"class myclass (int a [W], int b [RW])"
				+ " int localvar "
				+ " fun sum(int x, int y) : int"
				+ " {"
				+ "     int z"
				+ "     localvar <- 6"
				+ "     z <- x + y + localvar"
				+ "     return z    "
				+ " }"
				+ " program myClassProgram "
				+ " myclass classVar "
				+ " classVar <- myclass(4, 5) "
				+ " e <- 1000 "
				+ " print e "
				+ " m <- classVar.sum(7, 8) "
				+ " print m "
				+ " classVar.b <- 25 "
				+ " print classVar.b"
				+ " classVar.a <- 25 "
//				+ " print classVar.a"
				;
//		writeCode(text);
		runCode(text);
	}
	
	//two class test
	@Test
	public void classtwoclasstest() throws Exception
	{
		String text = 
				  "class myclass1 (int a [W], int b [RW])"
				+ " fun sum(int x, int y) : int"
				+ " {"
				+ "     int z"
				+ "     z <- x + y"
				+ "     return z    "
				+ " }   "
				+ " class myclass2 (float c [W], float d [RW])"
				+ " float c "
				+ " float d "
				+ " program myClassProgram "
				+ " myclass1 object1 "
				+ " object1 <- myclass1(4, 5) "
				+ " m <- object1.sum(7, 8) "
				+ " print m "
				+ " print object1.b "
				+ " myclass2 object2 "
				+ " object2 <- myclass2(3.0, 4.0)"
				+ " object2.d <- 8.0  "
				+ " print object2.d "
				;
//		writeCode(text);
		runCode(text);
	}
	
	
	@Test
	public void localtest() throws Exception
	{
		String text = 
				"	i <- 1                                    "    
						+"	j <- 2                                    "    
						+"	b <- false                                "    
						+"	                                          "    
						+"	print i <= j | b			# expect true    \n   "    
						+"	print i <= j & b			# expect false    \n  "    
						+"	print 42 div j = 21 | b		# expect true \n  "    
				;
//		writeCode(text);
		runCode(text);
	}
	
	@Test
	public void isprime() throws Exception
	{
		String text = "input candidateNumber "
				+ "	if                                                                    " 
						+"		candidateNumber = 2 | candidateNumber = 3 :: print true             " 
						+"		candidateNumber >= 4 & candidateNumber mod 2 = 0 :: print false     " 
						+"		candidateNumber >= 4 & candidateNumber mod 2 = 1 :: {               " 
						+"		    isPrime <- false  "
						+"			i <- 3  "
						+"			do                                                                " 
						+"			  ~isPrime & (i <= candidateNumber div 2) ::                      " 
						+"			  	{                                                             " 
						+"			  		isPrime <- candidateNumber mod i ~= 0                       " 
						+"					i <- i + 2    "
						+"				}                                                               " 
						+"			od                                                                " 
						+"			print isPrime                                                     " 
						+"		}                                                                   " 
						+"	fi                                                                    " ;
//		writeCode(text);
		DijkstraRuntime.setInputs(new String[] {"7"});
		runCode(text);
	}
	
	
	@Test
	public void isprime1() throws Exception
	{
		String text = "	fun isPrime(candidateNumber) : boolean                                                      "
	+"	{                                                                                       "
	+"		isPrime <- false "
	+ "		      continue <- false                                                               "
	+"		if                                                                                    "
	+"			candidateNumber < 2 :: continue <- false                                            "
	+"			candidateNumber = 2 :: return true                                                  "
	+"			candidateNumber = 3 :: return true                                                  "
	+"			candidateNumber > 3 :: "
	+ "				{  "
	+"				if                                                                                "
	+ "						candidateNumber mod 2 = 0 :: "
	+ "						   continue <- false                                  "
	+"					candidateNumber mod 2 ~= 0 :: "
	+ "                         continue <- true                               "
	+"				fi                                                                                "
//	+"			                                                                                    "
	+"				i, isPrime <- 3, false   "
	+ "					 "
	+"				do                                                                                "
	+"					continue & (i <= candidateNumber div 2) :: {                                    "
	+"						if                                                                            "
	+"							candidateNumber mod i = 0 :: continue <- false                              "
	+"							candidateNumber mod i > 0 :: i <- i + 2                                     "
	+"						fi                                                                            "
	+"					}                                                                               "
	+"					continue & (i > candidateNumber div 2) :: isPrime, continue <- true, false      "
	+"				od                                                                                "
	+"				return isPrime                                                                    "
	+"			}                                                                                   "
	+"		fi                                                                                    "
	+"		return false                                                                          "
	+"	}                                                                                       "
	+"	print isPrime(37)                                                                       "
	+"	print isPrime(225)                                                                      "
	;
//		writeCode(text);
//		DijkstraRuntime.setInputs(new String[] {"7"});
		runCode(text);
	}
	
	
	@Test
	public void fibonacciexample() throws Exception
	{
		String text = "	input howMany?                                         "
						+"	                                                       "
						+"	if                                                     "
						+"		howMany? <= 46 :: {                                  "
						+"			f1, f2 <- 1, 1                                     "
						+"	            print f1  "
						+ "				print f2 "
						+ "  print howMany?                                          "
						+"			if                                                 "
						+"				howMany? < 2 :: print 1                          "
						+"				howMany? >= 2 :: {print 1; print 1}              "
						+"			fi                                                 "
						+"	                                                       "
						+"			counter <- 2                                       "
						+"			do                                                 "
						+"				counter < howMany? :: f1, f2 <- f2, f1 + f2      "
						+"				counter < howMany? :: print f2                   "
						+"				counter < howMany? :: counter <- counter + 1    "
						+"			od                                                 "
						+"	            print f1  "
						+ "				print f2                                           "
						+"		}                                                    "
						+"		howMany? >= 46 :: {                                  "
						+"			a1, a2 <- 1.0, 1.0                                 "
						+"			print a1                                           "
						+"			print a2                                           "
						+"			                                                   "
						+"			counter <- 2                                       "
						+"			do                                                 "
						+"				counter < howMany? :: a1, a2 <- a2, a1 + a2      "
						+"				counter < howMany? :: print a2                   "
						+"				counter < howMany? :: counter <- counter + 1     "
						+"			od                                                 "
						+"		}                                                    "
						+"	fi                                                     "
				;
//		writeCode(text);
		DijkstraRuntime.setInputs(new String[] {"58"});
		runCode(text);
	}
	
	
	@Test
	public void Objectfibonacci() throws Exception
	{
		String text = 	
						"class Less(howMany? [RW])	                                                       "
						+" fun	lessthan46	() : boolean {                                  "
						+"			f1, f2 <- 1, 1                                     "
						+"	            print f1  "
						+ "				print f2 "
						+ "             howMany?  <- 5   "
						+"			if                                                 "
						+"				howMany? < 2 :: print 1                          "
						+"				howMany? >= 2 :: {print 1; print 1}              "
						+"			fi                                                 "
						+"			counter <- 2                                       "
						+"			do                                                 "
						+"				counter < howMany? ::{ f1, f2 <- f2, f1 + f2      "
						+"				 					print f2                   "
						+"									counter <- counter + 1 }   "
						+"			od                                                 "
						+"	            print f1  "
						+ "				print f2                                           "
						+"		    return true "
						+ "    }                                                    "
						+"class More(int howMany? [RW])  "
						+ " fun morethan46	() : boolean		"
						+ " {                                  "
						+"			a1, a2 <- 1.0, 1.0                                 "
						+"			print a1                                           "
						+"			print a2                                           "
						+"			                                                   "
						+"			counter <- 2                                       "
						+"			do                                                 "
						+"				counter < howMany? ::{ a1, a2 <- a2, a1 + a2      "
						+"				 						print a2                   "
						+"				 						counter <- counter + 1  }   "
						+"			od                                                 "
						+"		   return true "
						+ "     }                                                    "
						+ "program objectfibonacci "
						+ "	int howMany? "
						+ " input howMany?"
						+ " Less less"
						+ " More more "
						+ " int globalvar"
						+ " globalvar <- 6"
						+ " fun sum(int x, int y) : int"
						+ " {"
						+ "     int z"
						+ "     z <- x + y + globalvar"
						+ "     return z    "
						+ " }   "
						+ " if  "
						+ "    howMany? >= 46 :: {  more <- More(howMany?)                              " 
						+ "                         print   more.morethan46() "
						+ "							}                  "
						+ "    howMany? <  46 :: {  "
						+ "							less <- Less(howMany?)                              " 
						+ "                         print   less.lessthan46( )  "
						+ "						 }  "
						+ " fi              "
						+ " print sum(4, 5) "
				;
//		writeCode(text);
		DijkstraRuntime.setInputs(new String[] {"80"});
		runCode(text);
	}

	@Test
	public void squrtest() throws Exception
	{
		String text = 
							"       float inVal              "
						+	"		input inVal                                                                                        "
						+	"		root <- compute(inVal, inVal / 2)                                                                  "
						+	"		print root                                                                                         "
						+ "                                                                                                      "
						+	"		fun compute(float inVal, float guess) : float {                                                    "
						+	"			float computedRoot "
						+ "				computedRoot <- 0.0                                                                              "
						+	"			if                                                                                               "
						+	"				check(inVal / guess, guess) :: computedRoot <- guess                                           "
						+	"				~check(inVal / guess, guess) :: computedRoot <- compute(inVal, betterGuess(inVal, guess))      "
						+	"			fi                                                                                               "
						+	"			return computedRoot                                                                              "
						+	"		}                                                                                                  "
						+ "                                                                                                      "
						+	"		fun abs(float val) : float {                                                                       "
						+	"			float other      "
						+ "				other <- 0 "
						+	"			if                                                                                               "
						+	"				val >= 0.0 :: other <- val                                                                     "
						+	"				val < 0.0 :: other <- -val                                                                     "
						+	"			fi                                                                                               "
						+	"			return other                                                                                     "
						+	"		}                                                                                                  "
						+ "                                                                                                      "
						+	"		fun check(float a, float b) : boolean {                                                            "
						+	"			boolean isGood"
						+ "				isGood <- false                                                                                   "
						+	"			if                                                                                               "
						+	"				abs(a - b) < 0.00001 :: isGood <- true                                                         "
						+	"				abs(a - b) >= 0.00001 :: isGood <- false                                                       "
						+	"			fi                                                                                               "
						+	"			return isGood                                                                                    "
						+	"		}                                                                                                  "
						+ "                                                                                                      "
						+	"		fun betterGuess(float a, float b) : float {                                                        "
						+	"				return ((b + (a / b)) / 2)                                                                     "
						+	"			}    "
						;
//		writeCode(text);
		DijkstraRuntime.setInputs(new String[] {"40.0"});
		runCode(text);
	}
	
	
	@Test
	public void objectSqurtest() throws Exception
	{
		String text = 
						    "class Squr(float inVal [W], float guess [R])                                                                                               "
						+ "        fun compute() : float {                                                    "
						+ "            float computedRoot "
						+   "				computedRoot <- 0.0                                                                              "
						+	"			if                                                                                               "
						+	"				check(inVal / guess, guess) :: computedRoot <- guess                                           "
						+	"				~check(inVal / guess, guess) :: computedRoot <- computenext(inVal, betterGuess(inVal, guess))      "
						+	"			fi                                                                                               "
						+	"			return computedRoot                                                                              "
						+	"		}                                                                                                  "
						+ "        fun computenext(float inVal, float guess) : float {                                                    "
						+ "            float computedRoot "
						+   "				computedRoot <- 0.0                                                                              "
						+	"			if                                                                                               "
						+	"				check(inVal / guess, guess) :: computedRoot <- guess                                           "
						+	"				~check(inVal / guess, guess) :: computedRoot <- computenext(inVal, betterGuess(inVal, guess))      "
						+	"			fi                                                                                               "
						+	"			return computedRoot                                                                              "
						+	"		}"
						+   "                                                                                                      "
						+	"		fun abs(float val) : float {                                                                       "
						+	"			float other      "
						+ "				other <- 0 "
						+	"			if                                                                                               "
						+	"				val >= 0.0 :: other <- val                                                                     "
						+	"				val < 0.0 :: other <- -val                                                                     "
						+	"			fi                                                                                               "
						+	"			return other                                                                                     "
						+	"		}                                                                                                  "
						+ "                                                                                                      "
						+	"		fun check(float a, float b) : boolean {                                                            "
						+	"			boolean isGood"
						+ "				isGood <- false                                                                                   "
						+	"			if                                                                                               "
						+	"				abs(a - b) < 0.00001 :: isGood <- true                                                         "
						+	"				abs(a - b) >= 0.00001 :: isGood <- false                                                       "
						+	"			fi                                                                                               "
						+	"			return isGood                                                                                    "
						+	"		}                                                                                                  "
						+ "                                                                                                      "
						+	"		fun betterGuess(float a, float b) : float {                                                        "
						+	"				return ((b + (a / b)) / 2)                                                                      "
						+	"			}    "
						+   " program  objectSqurtest      "
						+ "         float inVal              "
						+	"		input inVal"
						+ "         Squr  squr"
						+ "	 		squr <- Squr(inVal, inVal / 2)                                                                            "
						+	"		root <- squr.compute()                                                                  "
						+	"		print root    "
						;
//		writeCode(text);
		DijkstraRuntime.setInputs(new String[] {"40.0"});
		runCode(text);
	}
	
	@Test
	public void squrtest1() throws Exception
	{
		String text = 
	"	float inVal                                                 "
	+"	input inVal                                                 "
	+"                                                              "
	+"	float guess, currentDiff;                                   "
	+"	boolean continue;                                           "
	+"                                                              "
	+"	continue, guess, currentDiff <- true, inVal / 2, 0;         "
	+"                                                              "
	+"	do                                                          "
	+"		continue :: {                                             "
	+"			currentDiff <- (guess * guess) - inVal;                 "
	+"			if                                                      "
	+"			  currentDiff < 0 :: currentDiff <- -currentDiff;       "
	+"			  true :: nop <- 0;                              "
	+"			fi                                                      "
	+"		}                                                         "
	+"                                                              "
	+"		continue & (currentDiff < 0.00001) :: {                   "
	+"			continue <- false;                                      "
	+"		}                                                         "
	+"                                                              "
	+"		continue & (currentDiff >= 0.00001) ::                    "
	+"			guess <- ((guess + (inVal / guess)) / 2)                "
	+"	od                                                          "
	+"                                                              "
	+"	print guess                                                 "
	;
//		writeCode(text);
		DijkstraRuntime.setInputs(new String[] {"40.0"});
		runCode(text);
	}	
	
	@Test
	public void exprtest() throws Exception
	{
		String text = 
	"	i1, i2, i3 <- 2, -5, 42                      "  
	+"	f1, f2, f3 <- -6.8, 820.75, 2387.92          "  
	+"	t, f <- true, false                  "
	+ " print i3        "  
	+"	print i3 div 7 + 3 * 8 mod 5 = 10            "  
	+"	print (i3 div 7 + 3 * 8 mod 5 = 10) = t      "  
	+"	                                             "  
	+"	print f3 < 2387.921                          "  
	+"	print f3 > 2387.919                          "  
	+"	                                             "  
	+"	print f3 + -f3 = 0.0                         "  
	+"	                                             "  
	+"	print i1 + -i1 = 0                           "  
	;
//		writeCode(text);
		runCode(text);
	}
	
	
	
	@Test
	public void FuncInc() throws Exception
	{
		String text =
	"                                         "
	+" 	fun incr(int i) : int {                "
	+" 		return i + 1                         "
	+" 	}                                      "
	+" 	                                       "
	+" 	x <- incr(1)                           "
	+" 	print x                                "
	+" 	x <- incr(-1)                          "
	+" 	print x                                "
	+" 	print incr(41)                         "
	;
//		writeCode(text);
		runCode(text);
	}
	
	
	// Helper methods
	private void makeParser(String inputText)
	{
		parser = DijkstraFactory.makeParser(new ANTLRInputStream(inputText));
	}

	/**
	 * This method performs the parse. If you want to see what the tree looks like, use <br>
	 * <code>Debug(tree.toStringTree());<code></br>
	 * after calling this method.
	 * 
	 * @param inputText
	 *            the text to parse
	 */
	private String doParse(String inputText)
	{
		if(inputText.substring(0, 5).equals("class"))
		{
			makeParser(inputText);
		}
		else
		{
			makeParser("program Test " + inputText);
		}
		tree = parser.dijkstraText();
		assertTrue(true);
		return tree.toStringTree(parser);
	}

	private void doAST(String inputText)
	{
		doParse(inputText);
		creator = new ASTCreator();
		ast = tree.accept(creator);
		System.out.println("ASTCreator ; " + ast.toString());
	}

	private void doTypeCheck(String inputText)
	{
		doAST(inputText);
		bytecodefortest();
		symbolCreator = new SymbolCreator();
		ast.accept(symbolCreator);
		System.out.println("Symbolcreator : " + ast.toString());
		TypeChecker checker = new TypeChecker();
		do {
			ast.accept(checker);
		} while (checker.checkAgain());
		System.out.println("TypeChecker ; " + ast.toString());
	}

	private void doCodeGen(String inputText)
	{
		doTypeCheck(inputText);
		subclassname.clear();
		for(int i = 0; i < ast.getChildCount(); i++)
		{
			if(ast.getChild(i).nodeType == ASTNodeType.PROGRAM)
			{
				programName = ((ProgramNode)ast.getChild(i)).programName;
			}
			else if(ast.getChild(i).nodeType == ASTNodeType.CLASSDECL)
			{
				subclassname.add(((ClassdeClarationNode)ast.getChild(i)).className);
			}
		}
		
		for(int i =0; i < ast.getChildCount(); i++)
		{
			if(ast.getChild(i).nodeType == ASTNodeType.PROGRAM)
			{
				DijkstraCodeGenerator cg = new DijkstraCodeGenerator();
				code = ast.accept(cg);
			}
			else
			{
				continue;
			}
		}
		
		for(int i = 0; i < ast.getChildCount(); i++)
		{
			
			if(ast.getChild(i).nodeType == ASTNodeType.CLASSDECL)
			{
				DijkstraCodeGenerator cg = new DijkstraCodeGenerator();
				cg.programName = programName;
				oneObjectCode = ast.getChild(i).accept(cg);
				objectcodelist.add(oneObjectCode);
			}
			else
			{
				continue;
			}
		}
	}

	private void runCode(String inputText) throws Exception
	{
		System.out.println("--------------------starttest-------------------");
		runCode(inputText, null);
	}
	
	private int bytecodefortest( )
	{
		int a = 5;
		for(int i=0; i < 5; i++)
		{
			a = i;
		}
		
		return a;
	}
	
	private void runCode(String inputText, String msg) throws Exception
	{
		doCodeGen(inputText);
		if (msg != null) {
			System.out.print(msg + "> ");
		}
		DijkstraCodeGeneratorTest loader = new DijkstraCodeGeneratorTest();
//		Class<?> testClass = loader.defineClass("djkcode.Test", code, 0, code.length);
		Class<?> testClass = loader.defineClass("djkcode." + programName, code, 0, code.length);
		if(subclassname.isEmpty() == false)
		{
//			DijkstraCodeGeneratorTest subclassloader = new DijkstraCodeGeneratorTest();
//			System.out.println(subclassname.get(0) + "    "+ objectcodelist.get(0).length);
			for(int n = 0; n < subclassname.size(); n++)
			{
				Class<?> subClass = loader.defineClass("djkcode." + subclassname.get(n), objectcodelist.get(n), 0, objectcodelist.get(n).length);
			}
		}
		Method m[] = testClass.getMethods();
		for(int i = 0; i < m.length; i++)
		{
//			System.out.println(m[i].getName()+"   "+m[i].getDeclaringClass());
			if(m[i].getName() == "main")
			{
				m[i].invoke(null,  new Object[] { null });
				break;
			}
			else
			{
				continue;
			}
		}
	}
	
	private void writeCode(String inputText)
	{
		doCodeGen(inputText);
		FileOutputStream fos;
		try {
//			fos = new FileOutputStream("classes/djkcode/Test.class");
			fos = new FileOutputStream("classes/djkcode/" + programName + ".class");
			fos.write(code);
			fos.close();
			
			if(subclassname.isEmpty() == false)
			{
				for(int i = 0; i < objectcodelist.size(); i++)
				{
					fos = new FileOutputStream("classes/djkcode/" + subclassname.get(i) + ".class");
					fos.write(objectcodelist.get(i));
					fos.close();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
