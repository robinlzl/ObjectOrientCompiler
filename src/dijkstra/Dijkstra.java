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
package dijkstra;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import org.antlr.v4.runtime.*;

import dijkstra.ast.*;
import dijkstra.ast.ASTNode.ASTNodeType;
import dijkstra.ast.ASTNodeFactory.ClassdeClarationNode;
import dijkstra.ast.ASTNodeFactory.ProgramNode;
import dijkstra.gen.DijkstraCodeGenerator;
import dijkstra.lexparse.DijkstraParser;
import dijkstra.semantic.TypeChecker;
import dijkstra.symbol.SymbolCreator;
import dijkstra.utility.DijkstraFactory;

/**
 * This is the driver for the Dijkstra compiler.
 * @version October 22, 2012
 */
public class Dijkstra
{
	private static String programName = new String();
	private static String customPackage = "djkcode";	// default
	private static String outputDirectory = ".";		// default
	private static String fileContents = null;
	public static boolean DebugFlag = false;
	private static ArrayList<String> subclassname = new ArrayList<String>();
	private static ArrayList<byte[]> objectcodelist = new ArrayList<byte[]>();
	private static byte[] code;
	private static byte[] oneObjectCode;
	/**
	 * Main program to run compiler.
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		String fileName = parseArgs(args);
		//read the input file containing the actual Dijkstra code
		try
		{
			fileContents = new Scanner(new File(fileName)).useDelimiter("\\A").next();
		}
		catch(Exception e)
		{
			System.out.println("Either no input file was specified or it could not be read.");
			showHelp();
			System.exit(1);
		}
		
		doCompile();
	}

	
	/**
	 * Parser for arguments to the main function
	 * @param args Array of strings
	 * @return name of the source file to compile from
	 */
	static private String parseArgs(String[] args)
	{
		int i = 0;
		String s = null;
		while (i < args.length) {
			s = args[i++];
			if (s.equals("-h")) {
				showHelp();
				System.exit(0);
			}
			else if (s.equals("-o")) {
				outputDirectory = args[i++];
			}
			else if (s.equals("-p")) {
				customPackage = args[i++];
			}
		}
		return s;
	}
	
	/**
	 * Prints help information
	 */
	static private void showHelp()
	{
		System.out.println("Arguments: [-h] [-cp CLASSPATH] [-p PACKAGE_NAME] sourceFile");
		System.out.println("Takes in Base Dijkstra code written in sourceFile and prints compiled code to the screen.\n" +
				"\t-h Show this text\n" +
				"\t-o<directory> compiles to the specified directory\n" +
				"\t-p<package> Sets the package to <package>\n");
	}
	
	static private void doCompile()
	{
		DijkstraParser parser = DijkstraFactory.makeParser(new ANTLRInputStream(fileContents));
		ParserRuleContext tree = parser.dijkstraText();
		ASTNode ast = tree.accept(new ASTCreator());
		ast.accept(new SymbolCreator());
		TypeChecker checker = new TypeChecker();
		do {
			ast.accept(checker);
		} while (checker.checkAgain());
		DijkstraCodeGenerator generator = new DijkstraCodeGenerator();
		if (customPackage != null) {
			generator.setClassPackage(customPackage);
		}
		// get the class name
//		programName = ((ProgramNode)ast.getChild(0)).programName;
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
		
		
//		byte[] code = ast.accept(new DijkstraCodeGenerator());
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
		
		
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(outputDirectory + "/" 
					+ customPackage + "/" + programName + ".class");
			fos.write(code);
			fos.close();
			
			if(subclassname.isEmpty() == false)
			{
				for(int i = 0; i < objectcodelist.size(); i++)
				{
					fos = new FileOutputStream(outputDirectory + "/" 
							+ customPackage + "/" + subclassname.get(i) + ".class");
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
