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

import java.util.*;

import org.antlr.v4.runtime.Token;

import dijkstra.lexparse.DijkstraVisitor;
import dijkstra.utility.DijkstraType;

/**
 * Base class for the Dijkstra AST nodes
 * @version Feb 9, 2015
 */
public abstract class ASTNode
{
	public enum ASTNodeType {
		PROGRAM, DECL, ASSIGN, INPUT, OUTPUT, ITERATIVE, COMPOUND, ALTERNATIVE,
		GUARD, ID, CONSTANT, UNARYEXPR, BINARYEXPR, VARLIST, EXPRLIST, VAR,
		CLASSATTIACCESSOR, ARRARYACCESSOR, FUNCCALL, CONSTRUCTORCALL, METHODCALL,
		VARIABLEDECL, ARRARYDECL, PROCEDUREDECL, FUNCTIONDECL, PARALIST, PARAM, RETURN, RETURNLIST,
		PROCEDURECALL, CLASSDECL, PROPERTY,PROPERTYLIST, DIJKSTRA, CONSTRUCTOR
	};
	
	public ASTNodeType nodeType;
	public ASTNode parent;
	public List<ASTNode> children;
	public Token token;
	public Token token_Classname;//for class attribute accessor
	public DijkstraType type;
	public ArrayList<DijkstraType> arrayList;
	public ArrayList<Integer> funEntryLabelList = new ArrayList<Integer>();
	int currentAddress = 0;
	
	public ASTNode()
	{
		this(null);
	}
	
	public ASTNode(ASTNode parent) 
	{
		children = new ArrayList<ASTNode>();
		this.parent = parent;
		token = null;
		type = DijkstraType.UNDEFINED;
	}
	
	public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
	
	/**
	 * @param i
	 * @return the ith child
	 * @throws ArrayOutOfBoundsException if i is invalid
	 */
	public ASTNode getChild(int i)
	{
		return children.get(i);
	}
	
	/**
	 * Add a child to this node. By default, this is placed at the rightmost child
	 * @param child
	 */
	public void addChild(ASTNode child)
	{
		children.add(child);
		child.parent = this;
	}
	
	/**
	 * Insert the child as the ith child of this node. Pushes all nodes to the right.
	 * @param i
	 * @param child
	 * @throws IndesOutOfBoundsException if i isout of bounds
	 */
	public void addChild(int i, ASTNode child)
	{
		children.add(i, child);
		child.parent = this;
	}
	
	/**
	 * Remove the ith child from this node and shift all of the other children to the left.
	 * @param i
	 * @return the removed node
	 * @throws IndesOutOfBoundsException if i is out of bounds
	 */
	public ASTNode removeChild(int i)
	{
		ASTNode child = children.remove(i);
		child.parent = null;
		return child;
	}
	
	/**
	 * @return the number of children
	 */
	public int getChildCount()
	{
		return children.size();
	}

	/**
	 * Print the complete tree rooted at this node.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "(" + nodeType + nodeInfo() + ")";
	}
	
	/**
	 * @return common node information
	 */
	protected String nodeInfo()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(" type=" + type);
		builder.append(token == null ? "" : ", token=" + token.getText());
		builder.append(extraInfo());
		for (ASTNode n : children) {
			builder.append(" " + n);
		}
		return builder.toString();
	}
	
	/**
	 * This can be overwritten by the specific ASTNode subclasses.
	 * @return any extra information about the node
	 */
	protected String extraInfo()
	{
		return "";
	}

	/**
	 * This can be overwritten by the specific ASTNode subclasses.
	 * @return any extra information about the node
	 */
	protected int setFunAddress()
	{
		return currentAddress++;
	}
	
	
	
	/**
	 * This can be overwritten by the specific ASTNode subclasses.
	 * 
	 */
	public void setType(DijkstraType dt)
	{
		
	}
	
	/**
	 * This method is used for printing the bare minimum of just this node.
	 * @return a short string for a node
	 */
	public String shortString()
	{
		return nodeType.toString();
	}
}


