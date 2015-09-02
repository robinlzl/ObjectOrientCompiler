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

import java.util.*;

import dijkstra.utility.DijkstraType;

/**
 * Singleton manager class that manages all symbol tables in the compilation.
 * 
 * @version Feb 7, 2015
 */
public class SymbolTableManager
{
	private static SymbolTableManager instance =  null;
	private SymbolTable currentSymbolTable;
	private final ArrayList<SymbolTable> tables;
	private static int talbleNumber = 0;
	private int currentSymboltTableNum = 0;
	private Stack<Integer> parentTableNumStack;//only used when enter a new scope and exit to original table 
	public ArrayList<String> globallist;
	/**
	 * Constructor that sets up the initial (global) symbol table.
	 */
	private SymbolTableManager()
	{
		tables = new ArrayList<SymbolTable>();
		currentSymbolTable = new SymbolTable(null);
		tables.add(currentSymbolTable);
		currentSymboltTableNum = 0;
		parentTableNumStack = new Stack<Integer>();
	}
	
	/**
	 * Enter a new scope. This adds a new symbol table to the lexical scope.
	 */
	public void enterScope()
	{
		currentSymbolTable = new SymbolTable(currentSymbolTable);
		tables.add(currentSymbolTable);
		parentTableNumStack.push(currentSymboltTableNum);
		currentSymboltTableNum = tables.size() - 1;

	}
	
	public void enterScope(int i)
	{
		currentSymbolTable = this.getSymbolTable(i);
		currentSymboltTableNum = i;
	}
	/**
	 * Exit a scope.
	 */
	public void exitScope()
	{
		currentSymbolTable = currentSymbolTable.getParent();
		currentSymboltTableNum = parentTableNumStack.peek();
		parentTableNumStack.pop();
	}

	/**
	 * @return the instance
	 */
	public static SymbolTableManager getInstance()
	{
		if (instance == null) {
			instance = new SymbolTableManager();
		}
		talbleNumber = 0;
		return instance;
	}
	
	// The next methods are pass through methods to the current symbol table, but the
	// symbol table manager takes care of creating the appropriate symbols.
	
	/**
	 * Add a symbol to the current symbol table.
	 * @param symbol the symbol to add
	 * @return the added symbol
	 * @throws DijkstraSymbolException if the symbol already exists in this table
	 * @see SymbolTable#add(Symbol)
	 */
	public Symbol add(Symbol symbol)
	{
		return currentSymbolTable.add(symbol);
	}
	
	/**
	 * Add a symbol to the current symbol table with a type of UNDEFINED.
	 * @param id the symbol name 
	 * @return the added symbol
	 * @throws DijkstraSymbolException if the symbol already exists in this table
	 * @see SymbolTable#add(Symbol)
	 * @see Symbol#Symbol(String)
	 */
	public Symbol add(String id)
	{
		return currentSymbolTable.add(new Symbol(id));
	}
	
	/**
	 * Add a symbol to the current symbol table with the type specified.
	 * @param id the symbol name 
	 * @param symbolType the symbol's type
	 * @return the added symbol
	 * @throws DijkstraSymbolException if the symbol already exists in this table
	 * @see SymbolTable#add(Symbol)
	 * @see Symbol#Symbol(String, dijkstra.utility.DijkstraType)
	 */
	public Symbol add(String id, DijkstraType symbolType)
	{
		return currentSymbolTable.add(new Symbol(id, symbolType));
	}
	
	public Symbol add(String id, ArrayList<DijkstraType> symbolTypelist)
	{
		return currentSymbolTable.add(new Symbol(id, symbolTypelist));
	}
	/**
	 * Add a symbol to the current symbol table with a type of UNDEFINED.
	 * @param symbol symbol to add 
	 * @return the symbol (whether added or one that was visible)
	 * @see SymbolTable#addIfNew(Symbol)
	 * @see Symbol#Symbol(String)
	 */
	public Symbol addIfNew(Symbol symbol)
	{
		return currentSymbolTable.addIfNew(symbol);
	}
	
	public Symbol addtoMainIfNew(String id)
	{
		return this.getSymbolTable(0).addIfNew(new Symbol(id));
	}
	
	
	
	/**
	 * Add a symbol to the current symbol table with a type of UNDEFINED.
	 * @param id the symbol name 
	 * @return the symbol (whether added or one that was visible)
	 * @see SymbolTable#addIfNew(Symbol)
	 * @see Symbol#Symbol(String)
	 */
	public Symbol addIfNew(String id)
	{
		return currentSymbolTable.addIfNew(new Symbol(id));
	}
	
	/**
	 * Add a symbol to the current symbol table with a type of UNDEFINED.
	 * @param id the symbol name 
	 * @param type the type associated with the id
	 * @return the symbol (whether added or one that was visible)
	 * @see SymbolTable#addIfNew(Symbol)
	 * @see Symbol#Symbol(String)
	 */
	public Symbol addIfNew(String id, DijkstraType type)
	{
		return currentSymbolTable.addIfNew(new Symbol(id, type));
	}
	
	/**
	 * Get the symbol with the specified key in the current scope.
	 * @param id the desired symbol's ID
	 * @return the symbol referenced or null if it does not exist.
	 * @see SymbolTable#getSymbol(String)
	 */
	public Symbol getSymbol(String id)
	{
		return currentSymbolTable.getSymbol(id);
	}
	
	
	/**
	 * @return the current symbol table
	 */
	public SymbolTable getCurrentSymbolTable()
	{
		return currentSymbolTable;
	}

	public SymbolTable enterNextSymbolTable()
	{
		talbleNumber++;
		currentSymbolTable = tables.get(talbleNumber);
		return currentSymbolTable;
	}
	
	public SymbolTable backtoParentSymbolTable()
	{
		currentSymbolTable = tables.get(talbleNumber);
		return currentSymbolTable;
	}

	public SymbolTable backtoMainymbolTable()
	{
		talbleNumber = 0;
		currentSymboltTableNum = 0;
		currentSymbolTable = tables.get(0);
		return currentSymbolTable;
	}
	
	public ArrayList<String> getGlaobalList()
	{
		 globallist = currentSymbolTable.getGloballist();
		 return globallist;
	}
	
//	public Symbol findSymbol(String id)
//	{
//		Symbol symbol = symbols.get(id); 
//		enterScope(0);
//		for(int i = 0; i < tables.size(); i++)
//		{
//			symbol = currentSymbolTable.getSymbol(id);
//			if(symbol != null)
//		}
//		
//		do{
//			symbol = currentSymbolTable.getSymbol(id);
//			enterNextSymbolTable();
//		}while (((tables.size() - 1) != talbleNumber) && symbol == null);
//		backtoMainymbolTable();
//		return symbol;
//	}
	
	
	
	/**
	 * @return the tables
	 */
	public ArrayList<SymbolTable> getTables()
	{
		return tables;
	}
	
	/**
	 * @return the tables
	 */
	public int getcurrentTableNumber()
	{
		return currentSymboltTableNum;
	}
	
	// Next methods added for testing and debugging
	/**
	 * @param i
	 * @return the symbol table at index i in the symbol table array
	 */
	public SymbolTable getSymbolTable(int i)
	{
		return tables.get(i);
	}
	
	public void reset()
	{
		tables.clear();
		currentSymbolTable = new SymbolTable(null);
		tables.add(currentSymbolTable);
		currentSymboltTableNum = 0;
	}
}
