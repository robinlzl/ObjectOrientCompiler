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

import dijkstra.ast.ASTVisitor;
import dijkstra.lexparse.DijkstraParserException;
import dijkstra.symbol.Symbol.symbolTypeEnum;
import dijkstra.utility.DijkstraType;

/**
 * A Dijkstra compiler symbol table
 * @version Feb 6, 2015
 */
public class SymbolTable
{
	private final SymbolTable parent;
	private final Map<String,  Symbol> symbols;
	private int idAddressOffset = 0;
	static private ArrayList<String> globallist;
	public String glaobal;
	/**
	 * Sole constructor. Creates the symbol table with the specified parent. The parent
	 * can be null for the global lexical level symbol table.
	 * @param parent the symbol table at the enclosing lexical level.
	 */
	public SymbolTable(SymbolTable parent)
	{
		this.parent = parent;
		symbols = new HashMap<String, Symbol>();
		globallist = new ArrayList<String>();
	}
	
	public ArrayList<String> getGloballist()
	{
		return this.globallist;
	}
	
	public void setIdAddressOffest(int offset)
	{
		idAddressOffset = offset;
	}
	
	public int getIdAddressOffest()
	{
		return idAddressOffset;
	}
	
	/**
	 * Add the specified Symbol to the current symbol table.
	 * @param symbol the symbol to add to the table
	 * @return the symbol that was added
	 * @throws DijkstraSymbolException if the symbol already exists in this table
	 */
	public Symbol add(Symbol symbol) 
	{
		final Symbol s = symbols.put(symbol.getId(), symbol);
		if (s != null) {	// Symbol was already in the table
			throw new DijkstraSymbolException(
					"Attempting to add a duplicate symbol to a symbol table" + s.getId());
		}
		return symbol;
	}
	

	/**
	 * Add the symbol to the symbol table if there is no symbol for the same name
	 * visible from the current scope.
	 * @param symbol the symbol to add if not new
	 * @return the symbol that is visible from this scope, either the argument given or the one
	 * 		that can be seen from this scope.
	 */
	public Symbol addIfNew(Symbol symbol)
	{
//		if(symboltype == symbolTypeEnum.FUNC)
//		{
//			symbol.setId("FUNC");
//		}
//		else
//		{
//			symbol.setId("NORMAL");
//		}
		
		Symbol s = getSymbol(symbol.getId());
		
		if (s == null ) {
			symbols.put(symbol.getId(), symbol);
			s = symbol;
		}
		return s;
	}
	
	/**
	 * Get the symbol with the specified key in the current scope.
	 * @param id the desired symbol's ID
	 * @return the symbol referenced or null if it does not exist.
	 */
	public Symbol getSymbol(String id)
	{
//		Debug("[SymbolTable] symbols = " + symbols);
		Symbol symbol = symbols.get(id);
		SymbolTable st = this;
		while (symbol == null && st.parent != null) {
			st = st.parent;
			symbol = st.getSymbol(id);
			if (symbol != null && symbol.getPostfix() == "NORMAL")
			{
//				Debug("[SymbolTable] symbol = " + symbol.getId() + " table = " + symbol.talbleNumber);

				symbol.globalFlag = true;
				addGlobalIfNew(symbol.getId());
			}
		}
		
//		int i = 0;
//		if(symbol.globalFlag == true)
//		{
//			for(i = 0; i < globallist.globallist.size(); i++)
//			{
//				if (globallist.get(i) == symbol.getId())
//				{
//					break;
//				}
//			}
//			if(i == globallist.size())
//			{
//				globallist.add(symbol.getId());
//			}
//		}
		return symbol;
	}
	
	private void Debug(String string) {
		// TODO Auto-generated method stub
		if(ASTVisitor.DebugFlag = true)
		{
			System.out.println(string);
		}
	}

	private void addGlobalIfNew(String id) {
		// TODO Auto-generated method stub
		int i = 0;
		for(i = globallist.size() - 1; i >= 0 ; i--)
		{
			if(globallist.get(i) == id)
			{
				break;
			}
		}
		if(i >= 0)
		{
			return;
		}
		else
		{
			globallist.add(id);
		}
	}

	public int getNumberOfSymbols()
	{
		return symbols.size();
	}
	
	public Symbol getoneSymbol(int i)
	{
		return symbols.get(i);
	}
	
	/**
	 * @return the parent of this symbol table
	 */
	public SymbolTable getParent()
	{
		return parent;
	}

	/**
	 * @return the symbols
	 */
	public Collection<Symbol> getSymbols()
	{
		return symbols.values();
	}

	/*
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((symbols == null) ? 0 : symbols.hashCode());
		return result;
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SymbolTable)) {
			return false;
		}
		SymbolTable other = (SymbolTable) obj;
		if (parent == null) {
			if (other.parent != null) {
				return false;
			}
		} else if (!parent.equals(other.parent)) {
			return false;
		}
		if (symbols == null) {
			if (other.symbols != null) {
				return false;
			}
		} else if (!symbols.equals(other.symbols)) {
			return false;
		}
		return true;
	}
}
