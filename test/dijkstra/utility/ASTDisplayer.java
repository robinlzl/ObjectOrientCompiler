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

package dijkstra.utility;

import dijkstra.ast.*;
import static dijkstra.ast.ASTNodeFactory.*;

/**
 * Print out a simple representation of an AST.
 * @version Feb 14, 2015
 */
public class ASTDisplayer extends ASTVisitor<String>
{
	private final String indentString;
	private int indentLevel;
	private final StringBuilder sb;
	
	public ASTDisplayer()
	{
		sb = new StringBuilder();
		indentString = "  ";
		indentLevel = 0;
	}
	
	public String visit(ProgramNode node) 
	{ 
		sb.append("program " + node.programName);
		indentLevel++;
		visitChildren(node); 
		return sb.toString();
	}
	
	public String visit(DeclarationNode node) 
	{
		indent();
		sb.append(node.type + " " + node.getId().getName());
		return null;
	}
	
	public String visit(AssignNode node) 
	{ 
		indent();
		sb.append("<-");
		indentLevel++;
		visitChildren(node);
		indentLevel--;
		return null;
	}
	
	public String visit(InputNode node) 
	{ 
		indent();
		for(int i = 0; i < node.getChildCount(); i++)
		{
			sb.append("input " + node.getID(i).getName());
		}
		return null;
	}
	
	public String visit(OutputNode node) 
	{
		indent();
		sb.append("print");
		indentLevel++;
		visitChildren(node);
		indentLevel--;
		return null;
	}
	
	public String visit(IterativeNode node) 
	{
		indent();
		sb.append("loop");
		indentLevel++;
		visitChildren(node);
		indentLevel--;
		return null;
	}
	
	public String visit(AlternativeNode node) 
	{ 
		indent();
		sb.append("if");
		indentLevel++;
		visitChildren(node);
		indentLevel--;
		indent();
		sb.append("fi");
		return null;
	}
	
	public String visit(CompoundNode node)
	{
		indent();
		sb.append("{");
		indentLevel++;
		visitChildren(node);
		indentLevel--;
		indent();
		sb.append("}");
		return null;
	}
	
	public String visit(GuardNode node) 
	{ 
		indent();
		sb.append("::");
		indentLevel++;
		visitChildren(node);
		indentLevel--;
		return null;
	}
	
	public String visit(UnaryExpressionNode node) 
	{ 
		indent();
		sb.append(node.token.getText());
		indentLevel++;
		visitChildren(node);
		indentLevel--;
		return null;
	}
	
	public String visit(BinaryExpressionNode node) 
	{ 
		indent();
		sb.append(node.token.getText());
		indentLevel++;
		visitChildren(node);
		indentLevel--;
		return null;
	}
	
	public String visit(IDNode node) 
	{ 
		indent();
		sb.append(node.shortString() + " " + node.getName());
		return null;
	}
	
	public String visit(ConstantNode node) 
	{ 
		indent();
		sb.append(node.shortString() + " " + node.token.getText());
		return null;
	}

	public String visit(VarlistNode node) 
	{ 
		indent();
		sb.append("VarlistNode");
		indentLevel++;
		visitChildren(node);
		indentLevel--;
		return null;
	}
	
	public String visit(VarNode node) 
	{ 
		indent();
		sb.append("VarNode");
		indentLevel++;
		visitChildren(node);
		indentLevel--;
		return null;
	}
	
	private void indent()
	{
		sb.append("\n");
		for (int i=0; i < indentLevel; i++) {
			sb.append(indentString);
		}
	}
}
