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

import static dijkstra.utility.DijkstraType.*;

import java.util.ArrayList;

import dijkstra.utility.DijkstraType;

/**
 * This class defines a Symbol object that gets stored in a symbol table and is
 * bound to identifiers and other entities. This is a simple entity data structure.
 * @version Feb 4, 2015
 */
public class Symbol
{
//	private final String id;
	private String id;
	private DijkstraType type;
	private String value;
	private ArrayList<DijkstraType> typelist;//this is the returntype list of a function symbol
	private ArrayList<DijkstraType> paratypelist = new ArrayList<DijkstraType>();
	private int address;
	private int funaddress;
	public static final int NO_ADDRESS = Integer.MIN_VALUE	;
	public String functionDiscriptor = null;
	public String functionSigniture = null;
	public int functionSymbolOffset = 0;
	public ArrayList<String> classInheritanceList = new ArrayList<String>();
	public ArrayList<DijkstraType> classPropertyList = new ArrayList<DijkstraType>();
	private Symbol classSymbol = null;// This is used to store the class which an object belongs to
	private String classname = null;//this is used to store the name of the class which an object belongs to 
	public boolean globalFlag = false;
	public symbolTypeEnum symbolType = symbolTypeEnum.NORMAL;
	public int talbleNumber = 0;
	private String postFix;
	public ArrayList<Integer> accesslist = new ArrayList<Integer>();
	public Integer accessSpec = 3;//0 = readonly 1 = writeonly 2 = readandwrite 3=local
	static public enum symbolTypeEnum
	{
		NORMAL, ARRAY, FUNC, CLASS, OBJECT, PROPERTY
	}
	/**
	 * Constructor that creates the Symbol with the name, and an UNDEFINED type.
	 * @param id the symbol name
	 */
	public Symbol(String id)
	{
		this(id, UNDEFINED);
		this.address = NO_ADDRESS;
	}
	
	/**
	 * Constructor that creates the Symbol with the specified name and type.
	 * @param id the symbol name
	 * @param type the type assigned to the symbol
	 */
	public Symbol(String id, DijkstraType type)
	{
		this.id = id;
		this.type = type;
		this.address = NO_ADDRESS;
		this.value = null;
	}
	
	public Symbol(String id, ArrayList<DijkstraType> symbolTypelist)
	{
		this.id = id;
		this.typelist = symbolTypelist;
		this.address = NO_ADDRESS;
		this.value = null;
	}
	
	
	/**
	 * @return the type
	 */
	public DijkstraType getType()
	{
		return type;
	}
	/**
	 * @return the type
	 */
	public ArrayList<DijkstraType> getTypeList()
	{
		return typelist;
	}
	/**
	 * @return the type
	 */
	public ArrayList<DijkstraType> getParaTypeList()
	{
		return paratypelist;
	}
	
	/**
	 * @param type the type to set
	 */
	public void setType(DijkstraType type)
	{
		this.type = type;
	}

	public void setClassSymbol(Symbol classSymbol)
	{
		this.classSymbol = classSymbol;
	}
	
	
	public void setClassname(String className)
	{
		this.classname = className;
	}
	
	public String getClassname()
	{
		return this.classname;
	}
	
	public Symbol getClassSymbol()
	{
		return classSymbol;
	}
	
	/**
	 * @param type the type to set
	 */
	public void setfuntionDisc(String fundisc)
	{
		this.functionDiscriptor = fundisc;
	}
	
	public void setTypelist(ArrayList<DijkstraType> typelist)
	{
		this.typelist = typelist;
	}
	

	public void setParaTypelist(ArrayList<DijkstraType> paratypelist)
	{
		this.paratypelist = paratypelist;
	}
	/**
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	
	/**
	 * @return the name
	 */
	public String setId(String postfix)
	{
		return id = id + postfix;
	}
	
	/**
	 * @return the name
	 */
	public String getPostfix()
	{
		return this.postFix;
	}
	
	
	public void setPostfix(String symbolpostfix)
	{
		this.postFix = symbolpostfix;
	}
	
	/**
	 * @return the name
	 */
	public String getId()
	{
		return id;
	}
	
	/**
	 * @return the address
	 */
	public int getAddress()
	{
		return address;
	}

	public int getFunAddress()
	{
		return funaddress;
	}
	
	/**
	 * @param address the address to set
	 */
	public void setAddress(int address)
	{
		this.address = address;
	}
	/**
	 * @param address the address to set
	 */
	public void setFunAddress(int address)
	{
		this.funaddress = address;
	}
	/*
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		if (!(obj instanceof Symbol)) {
			return false;
		}
		Symbol other = (Symbol) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("name=");
		builder.append(id);
		builder.append(", DijkstraSymbol [type=");
		builder.append(type);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}
	
}
