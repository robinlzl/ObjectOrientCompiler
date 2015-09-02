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
import static dijkstra.ast.ASTNodeFactory.*;

/**
 * The base Dijkstra AST visitor.
 * @version Feb 9, 2015
 */
public class ASTVisitor<T>
{
	public static boolean DebugFlag = false; 
//	public static boolean DebugFlag = true;
	public T visit(ASTNode node) { return visitChildren(node); }
	public T visit(ProgramNode node) { return visitChildren(node); }
	public T visit(DeclarationNode node) { return visitChildren(node); }
	public T visit(AssignNode node) { return visitChildren(node); }
	public T visit(InputNode node) { return visitChildren(node); }
	public T visit(OutputNode node) { return visitChildren(node); }
	public T visit(DijkstraTextNode node) { return visitChildren(node); }
	public T visit(IterativeNode node) { return visitChildren(node); }
	public T visit(AlternativeNode node) { return visitChildren(node); }
	public T visit(CompoundNode node) { return visitChildren(node); }
	public T visit(GuardNode node) { return visitChildren(node); }
	public T visit(UnaryExpressionNode node) { return visitChildren(node); }
	public T visit(BinaryExpressionNode node) { return visitChildren(node); }
	public T visit(VarlistNode node) {return visitChildren(node);}
	public T visit(VarNode node) {return visitChildren(node);}	
	public T visit(ExprListNode node) {return visitChildren(node);}	
	public T visit(ArrayaccessorNode node) {return visitChildren(node);}
	public T visit(variableDeclarationNode node) {return visitChildren(node);}
	public T visit(ParameterlistNode node) {return visitChildren(node);}	
	public T visit(ParameterNode node) {return visitChildren(node);}	
	public T visit(FunctionDeclarationNode node) {return visitChildren(node);}	
	public T visit(ClassAttributeAccessorNode node) {return visitChildren(node);}	
	public T visit(ClassdeClarationNode node) {return visitChildren(node);}	
	public T visit(ConstructorNode node) {return visitChildren(node);}	
	public T visit(FuntioncallNode node) {return visitChildren(node);}	
	public T visit(MethodcallNode node) {return visitChildren(node);}	
	public T visit(ProcedurecallNode node) {return visitChildren(node);}	
	public T visit(ProcedureDeclarationNode node) {return visitChildren(node);}	
	public T visit(PropertyListNode node) {return visitChildren(node);}	
	public T visit(PropertyNode node) {return visitChildren(node);}	
	public T visit(ArrayDeclarationNode node) {return visitChildren(node);}	
	public T visit(ReturnStatementlistNode node) {return visitChildren(node);}
	public T visit(ReturnStatementNode node) {return visitChildren(node);}
	
	public T visit(IDNode node) { return null; }
	public T visit(ConstantNode node) { return null; }
	
	/**
	 * Visit all of the children without getting any results
	 * @param node the node whose children must be visited
	 */
	public T visitChildren(ASTNode node) 
	{
		if(node != null)
		{
			for (ASTNode child : node.children) {
				child.accept(this);
			}
		}
		return null;
	}
	
	public void OpenDebug()
	{
		DebugFlag = true;
	}
	
	public void CloseDebug()
	{
		DebugFlag = false;
	}
	
	public void Debug(String msg)
	{
		if(DebugFlag == true)
		{
			System.out.println(msg);
		}
	}
}
