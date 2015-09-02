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

import java.awt.List;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.*;
import dijkstra.ast.ASTNode.ASTNodeType;
import dijkstra.ast.ASTNodeFactory.AssignNode;
import dijkstra.ast.ASTNodeFactory.ClassAttributeAccessorNode;
import dijkstra.ast.ASTNodeFactory.IDNode;
import dijkstra.lexparse.*;
import dijkstra.lexparse.DijkstraParser.AccessSpecContext;
import dijkstra.utility.DijkstraType;
import static dijkstra.ast.ASTNodeFactory.*;
import static dijkstra.lexparse.DijkstraParser.*;

import java.util.ArrayList;
/**
 * Description
 * @version Feb 10, 2015
 */
public class ASTCreator extends DijkstraBaseVisitor<ASTNode>
{
	/**
	 * Return the result of visiting the program node.
	 * @see dijkstra.lexparse.DijkstraBaseVisitor#visitDijkstraText(dijkstra.lexparse.DijkstraParser.DijkstraTextContext)
	 */
	public ASTNode visitDijkstraText(DijkstraParser.DijkstraTextContext ctx)
	{
		ASTNode result = null;
		final DijkstraTextNode dijstratextnode = DijkstraTextNode.makeDijkstraTextNode();
		if (null != ctx.classdeClaration())
		{
			for(int i = 0; i < ctx.classdeClaration().size(); i++)
			{
				dijstratextnode.addChild(ctx.classdeClaration(i).accept(this));				
			}			
		}
		
		if (null != ctx.program())
		{		
			dijstratextnode.addChild(ctx.program().accept(this));
		}
		
		return dijstratextnode;
	}
	
	/**
	 * Return the program node after visiting and linking the appropriate children nodes.
	 * @see dijkstra.lexparse.DijkstraBaseVisitor#visitProgram(dijkstra.lexparse.DijkstraParser.ProgramContext)
	 */
	public ASTNode visitProgram(DijkstraParser.ProgramContext ctx)
	{
		String programName = ctx.ID().getText();
		ArrayList<ASTNode> functionNumList = new ArrayList<ASTNode>();
		final ProgramNode program = ProgramNode.makeProgramNode(programName);
		for (int i = 2; i < ctx.getChildCount(); i++) {
			ASTNode node = ctx.getChild(i).accept(this);
			if(node.nodeType == ASTNodeType.FUNCTIONDECL || node.nodeType == ASTNodeType.PROCEDUREDECL)
			{
				functionNumList.add(node);
				continue;
			}
			else
			{
				program.addChild(node);
			}			
		}
		
		for(int i = 0; i < functionNumList.size(); i++)
		{
			program.addChild(functionNumList.get(i));
		}
		return program;
	}
	
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#declaration}.
	 * @param ctx the parse tree
	 * @return the declaration linked up to the parent
	 */
	public ASTNode visitVariableDeclaration(DijkstraParser.VariableDeclarationContext ctx)
	{
		variableDeclarationNode variabledeclarationnode = null;

		if (null != ctx.classname())
		{
			variabledeclarationnode = variableDeclarationNode.makevariableDeclarationNode(IDNode.makeIDNode(ctx.classname().ID().getSymbol(), "CLASS"), DijkstraType.CLASS);
			for(int i = 0; i < ctx.idlist().ID().size(); i++)
			{
				variabledeclarationnode.addChild(IDNode.makeIDNode(ctx.idlist().ID(i).getSymbol(), "OBJECT"));
			}
		}
		else if (null != ctx.type())
		{
			final DijkstraType type = DijkstraType.getType(ctx.type().getText());
			variabledeclarationnode = variableDeclarationNode.makevariableDeclarationNode(type);

			for(int i = 0; i < ctx.idlist().ID().size(); i++)
			{
				IDNode id = IDNode.makeIDNode(ctx.idlist().ID(i).getSymbol());
				id.setType(type);
				variabledeclarationnode.addChild(id);
			}
		}
		
		
//		if (null != ctx.classname())
//		{
//			variabledeclarationnode = variableDeclarationNode.makevariableDeclarationNode(IDNode.makeIDNode(ctx.classname().ID().getSymbol(), "CLASS"), DijkstraType.CLASS);
//		}
//		else if (null != ctx.type())
//		{
//			final DijkstraType type = DijkstraType.getType(ctx.type().getText());
//			variabledeclarationnode = variableDeclarationNode.makevariableDeclarationNode(type);
//
//		}
//		
//		for(int i = 0; i < ctx.idlist().ID().size(); i++)
//		{
//			variabledeclarationnode.addChild(IDNode.makeIDNode(ctx.idlist().ID(i).getSymbol()));
//		}
		
		return variabledeclarationnode;
	}

	
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#declaration}.
	 * @param ctx the parse tree
	 * @return the declaration linked up to the parent
	 * child 0 is ID, child 1 is decl ,child 2 is classbody
	 */
	public ASTNode visitClassdeClaration(DijkstraParser.ClassdeClarationContext ctx)
	{
		final ClassdeClarationNode classdeclararionnode;
		ClassBodyContext classbody;
		final String classname = ctx.ID().getSymbol().getText();
		classdeclararionnode = ClassdeClarationNode.makeClassdeClarationNode(classname);
		classdeclararionnode.addChild(IDNode.makeIDNode(ctx.ID().getSymbol(), "CLASS"));
		classbody = ctx.classBody();
		
		for(int i = 0; i < classbody.getChildCount(); i++)
		{
			classdeclararionnode.addChild(classbody.declaration(i).accept(this));
		}
		
		if(ctx.getChildCount() > 3)//there is property
		{
			classdeclararionnode.addChild(ctx.propertylist().accept(this));
		}
		
		return classdeclararionnode;
	}
	
	
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#declaration}.
	 * @param ctx the parse tree
	 * @return the declaration linked up to the parent
	 */
	public ASTNode visitProperty(DijkstraParser.PropertyContext ctx)
	{
		IDNode idnode;
		int accessSpec = 0;
		PropertyNode propertynode = null;
		DijkstraType type;
		switch (ctx.getChildCount())
		{
			case 1://only an ID, type = undefined accessspec = readonly(00)
				idnode = IDNode.makeIDNode(ctx.ID().getSymbol());
				accessSpec = 0;
				propertynode = PropertyNode.makePropertyNode(DijkstraType.UNDEFINED, accessSpec);
				propertynode.addChild(idnode);
				break;
			case 2://a type and an ID, type = type access = readonly(00)
				idnode = IDNode.makeIDNode(ctx.ID().getSymbol());
				accessSpec = 0;
				type = DijkstraType.getType(ctx.type().getText());
				propertynode = PropertyNode.makePropertyNode(type, accessSpec);
				propertynode.addChild(idnode);
				break;
			case 4:// an Id and access, type = undefined, access = access
				idnode = IDNode.makeIDNode(ctx.ID().getSymbol());
				accessSpec = getAccessSpec(ctx.accessSpec());
//				type = DijkstraType.getType(ctx.type().getText());
				propertynode = PropertyNode.makePropertyNode(DijkstraType.UNDEFINED, accessSpec);
				propertynode.addChild(idnode);
				break;
			case 5://a type, an ID and a access, type = type, access = access
				idnode = IDNode.makeIDNode(ctx.ID().getSymbol());
				type = DijkstraType.getType(ctx.type().getText());
				accessSpec = getAccessSpec(ctx.accessSpec());
				propertynode = PropertyNode.makePropertyNode(type, accessSpec);
				propertynode.addChild(idnode);
				break;
			default:
					break;
		}
		
		return propertynode;
	}
	
	private int getAccessSpec(AccessSpecContext accessSpec) {
		// TODO Auto-generated method stub
		int accessSpecResult = 0;
		if(accessSpec.R() != null)
		{
			accessSpecResult = 0;
		}
		else if(accessSpec.W() != null)
		{
			accessSpecResult = 1;
		}
		else if(accessSpec.RW() != null)
		{
			accessSpecResult = 2;
		}
		return accessSpecResult;
	}

	/**
	 * Visit a parse tree produced by {@link DijkstraParser#declaration}.
	 * @param ctx the parse tree
	 * @return the declaration linked up to the parent
	 */
	public ASTNode visitPropertylist(DijkstraParser.PropertylistContext ctx)
	{
		PropertyListNode propertylistnode = PropertyListNode.makePropertyNode();
		int n = 0;
		for(int i = 0; i < ctx.getChildCount(); i = i + 2)
		{
			PropertyNode propertynode = (PropertyNode)ctx.property(n).accept(this);
			propertylistnode.addChild(propertynode);
			propertylistnode.accessSpecList.add(propertynode.accessSpec);
			propertylistnode.propertyTypeList.add(propertynode.type);
			n++;
		}
		return propertylistnode;
	}
	
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#declaration}.
	 * @param ctx the parse tree
	 * @return the declaration linked up to the parent
	 */
	public ASTNode visitArrayDeclaration(DijkstraParser.ArrayDeclarationContext ctx)
	{
		final DijkstraType type = DijkstraType.getType(ctx.type().getText());
		int n = 0;
		final ArrayDeclarationNode arraydeclarationnode = ArrayDeclarationNode.makeArrayDeclarationNode(type);
		arraydeclarationnode.addChild(ctx.expression().accept(this));
		for(int i = 0; i < ctx.idlist().getChildCount(); i = i + 2)
		{
			arraydeclarationnode.addChild(IDNode.makeIDNode(ctx.idlist().ID(n++).getSymbol()));
		}
		
		return arraydeclarationnode;
	}

//	/**
//	 * Visit a parse tree produced by {@link DijkstraParser#declaration}.
//	 * @param ctx the parse tree
//	 * @return the declaration linked up to the parent
//	 */
//	public ASTNode visitClassdeClaration(DijkstraParser.ClassdeClarationContext ctx)
//	{
//		final String classname = ctx.ID().getSymbol().getText();
//		final DijkstraType dijkstratype = DijkstraType.CLASS;
//		if (3 == ctx.getChildCount())//there is no property
//		{
//			ctx.classBody()
//		}
//		
//		final ClassdeClarationNode classdeclarationnode = ClassdeClarationNode.makeClassdeClarationNode(classname, type);
//		classdeclarationnode.addChild(ctx.expression().accept(this));
//		for(int i = 0; i < ctx.idlist().getChildCount(); i++)
//		{
//			arraydeclarationnode.addChild(IDNode.makeIDNode(ctx.idlist().ID(i).getSymbol()));
//		}
//		
//		return arraydeclarationnode;
//	}
	
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#declaration}.
	 * @param ctx the parse tree
	 * @return the declaration linked up to the parent
	 */
	public ASTNode visitPrecedureDeclaration(DijkstraParser.PrecedureDeclarationContext ctx)
	{
		final IDNode id  = IDNode.makeIDNode(ctx.ID().getSymbol(), "PROC");		// get variable
		int i = 0;
		final ProcedureDeclarationNode proceduredeclarationnode = ProcedureDeclarationNode.makeProcedureDeclarationNode(id);

		if(null != ctx.compoundStatement())
		{
			proceduredeclarationnode.addChild(ctx.compoundStatement().accept(this));
		}			
			
		if(null != ctx.parameterlist())
		{
			ParameterlistNode ParameterlistNode = (ParameterlistNode)ctx.parameterlist().accept(this);
			proceduredeclarationnode.addChild(ParameterlistNode);
			proceduredeclarationnode.setParameterlistFlag(true);
			for(int i1 = 0; i1 < ParameterlistNode.getChildCount(); i1++)
			{
				proceduredeclarationnode.paratypeList.add(i1, ParameterlistNode.getChild(i1).type); 
			}
		}
		
		return proceduredeclarationnode;
	}
	
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#declaration}.
	 * @param ctx the parse tree
	 * @return the declaration linked up to the parent
	 */
	public ASTNode visitProcedurecall(DijkstraParser.ProcedurecallContext ctx)
	{
		final IDNode procedurecallid  = IDNode.makeIDNode(ctx.ID().getSymbol(),"PROC");	
		final ProcedurecallNode procedurecallnode = ProcedurecallNode.makeProcedurecallNode(procedurecallid);
		if(ctx.expressionlist() != null)
		{
			procedurecallnode.addChild(ctx.expressionlist().accept(this));
		}
		return procedurecallnode;
	}

	/**
	 * Visit a parse tree produced by {@link DijkstraParser#declaration}.
	 * @param ctx the parse tree
	 * @return the declaration linked up to the parent
	 */
	public ASTNode visitMethodcall(DijkstraParser.MethodcallContext ctx)
	{
		final IDNode methodcallid  = IDNode.makeIDNode(ctx.ID(1).getSymbol(),"FUNC");	
		final IDNode classid  = IDNode.makeIDNode(ctx.ID(0).getSymbol(), "OBJECT");	
		
		final MethodcallNode methodcallnode = MethodcallNode.makeMethodcallNode(methodcallid, classid);
		if(ctx.expressionlist() != null)
		{
			methodcallnode.addChild(ctx.expressionlist().accept(this));
		}
		return methodcallnode;
	}
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#declaration}.
	 * @param ctx the parse tree
	 * @return the declaration linked up to the parent
	 */
	public ASTNode visitParameterlist(DijkstraParser.ParameterlistContext ctx)
	{ 

		final ParameterlistNode parameterlistnode = ParameterlistNode.makeParameterlistNode();
		for(int i = 0; i < ctx.getChildCount(); i = i+2)
		{
			parameterlistnode.addChild(ctx.getChild(i).accept(this));
		}
		return parameterlistnode;		

	}

	/**
	 * Visit a parse tree produced by {@link DijkstraParser#declaration}.
	 * @param ctx the parse tree
	 * @return the declaration linked up to the parent
	 */
	public ASTNode visitParameter(DijkstraParser.ParameterContext ctx)
	{
		final ParameterNode parameternode;
			
		if (ctx.type() != null)
		{
			parameternode = ParameterNode.makeParameterNode(IDNode.makeIDNode(ctx.ID().getSymbol()),DijkstraType.getType(ctx.type().getText()));
		}
		else if (ctx.classname() != null)
		{
			parameternode = ParameterNode.makeParameterNode(IDNode.makeIDNode(ctx.ID().getSymbol()), DijkstraType.CLASS);
		}
		else
		{
			parameternode = ParameterNode.makeParameterNode(IDNode.makeIDNode(ctx.ID().getSymbol()));
		}

		return parameternode;		
	}	
	
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#declaration}.
	 * @param ctx the parse tree
	 * @return the declaration linked up to the parent
	 */
	public ASTNode visitFunctionDeclaration(DijkstraParser.FunctionDeclarationContext ctx)
	{
		final IDNode id  = IDNode.makeIDNode(ctx.ID().getSymbol(), "FUNC");		// get variable
	
		TypelistContext typelist;
		int i = 0;
		final FunctionDeclarationNode functiondeclarationnode = FunctionDeclarationNode.makeFunctionDeclarationNode(id);

		typelist = ctx.typelist();
		do
		{
			functiondeclarationnode.settypelist(i, DijkstraType.getType(typelist.type().getText()));
			i++;
			typelist = typelist.typelist();
		}while(typelist != null);// store all the types in the typlist
		
		if(null != ctx.compoundStatement())
		{
			functiondeclarationnode.addChild(ctx.compoundStatement().accept(this));
		}			
			
		if(null != ctx.parameterlist())
		{
			ParameterlistNode ParameterlistNode = (ParameterlistNode)ctx.parameterlist().accept(this);
			functiondeclarationnode.addChild(ParameterlistNode);
			functiondeclarationnode.setParameterlistFlag(true);
			for(int i1 = 0; i1 < ParameterlistNode.getChildCount(); i1++)
			{
				functiondeclarationnode.paratypeList.add(i1, ParameterlistNode.getChild(i1).type); 
			}
		}
		
		return functiondeclarationnode;
	}
	/**
	 * Visit an assignment statment. 
	 * @see dijkstra.lexparse.DijkstraBaseVisitor#visitAssignStatement(dijkstra.lexparse.DijkstraParser.AssignStatementContext)
	 */
	public ASTNode visitAssignStatement(DijkstraParser.AssignStatementContext ctx)
	{
//		final IDNode id  = IDNode.makeIDNode(ctx.varlist().getSymbol());		// get variable
		final ASTNode varlist = ctx.varlist().accept(this);
		final ASTNode exprlist = ctx.expressionlist().accept(this);				// get the expression
		final AssignNode assign = AssignNode.makeAssignNode(varlist, exprlist);
		return assign;
	}
//***********************toydijkstra's assign************************	
//	public ASTNode visitAssignStatement(DijkstraParser.AssignStatementContext ctx)
//	{
//		final IDNode id  = IDNode.makeIDNode(ctx.ID().getSymbol());		// get variable
//		final ASTNode expr = ctx.expression().accept(this);				// get the expression
//		final AssignNode assign = AssignNode.makeAssignNode(id, expr);
//		return assign;
//	}	
//***********************toydijkstra's assing************************	

	/**
	 * Visit an input statement.
	 * @see dijkstra.lexparse.DijkstraBaseVisitor#visitInputStatement(dijkstra.lexparse.DijkstraParser.InputStatementContext)
	 */
	public ASTNode visitInputStatement(DijkstraParser.InputStatementContext ctx)
	{
		InputNode inputnode = InputNode.makeInputNode();
		int n = 0;
		for(int i = 0; i < ctx.idlist().getChildCount(); i = i + 2)
		{
			inputnode.addChild(IDNode.makeIDNode(ctx.idlist().ID(n++).getSymbol()));
		}
		return inputnode;
	}
	
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#outputStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public ASTNode visitOutputStatement(DijkstraParser.OutputStatementContext ctx)
	{
		return OutputNode.makeOutputNode(ctx.expression().accept(this));
	}
	
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#iterativeStatement}.
	 * @see dijkstra.lexparse.DijkstraBaseVisitor#visitIterativeStatement(dijkstra.lexparse.DijkstraParser.IterativeStatementContext)
	 */
	public ASTNode visitIterativeStatement(DijkstraParser.IterativeStatementContext ctx)
	{
		IterativeNode iterative = IterativeNode.makeIterativeNode();
		for (GuardContext guard : ctx.guard()) {
			iterative.addChild(guard.accept(this));
		}
		return iterative;
	}
	
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#compoundStatement}.
	 * @see dijkstra.lexparse.DijkstraBaseVisitor#visitCompoundStatement(dijkstra.lexparse.DijkstraParser.CompoundStatementContext)
	 */
	public ASTNode visitCompoundStatement(DijkstraParser.CompoundStatementContext ctx)
	{
		CompoundNode compound = CompoundNode.makeCompoundNode();
		ReturnStatementlistNode returnstatementlistnode = ReturnStatementlistNode.makeReturnStatementlistNode();
		ASTNode tmpnode;

		for (int i = 1; i < ctx.getChildCount() - 1; i++) 
		{		// Visit all nodes except the ends {}
			tmpnode = ctx.getChild(i).accept(this);
			if(tmpnode.shortString() == "RETURN")
			{
				returnstatementlistnode.addChild(tmpnode);
			}
			else
			{
				compound.addChild(tmpnode);
			}
			
		}

		//return statements are always at last
		if(returnstatementlistnode.getChildCount() != 0)
		{
			compound.addChild(returnstatementlistnode);
		}
	
		return compound;
	}
	
	/**
	 * Visit an alternative. This simply links all of the Guard subtrees to this one.
	 * @see dijkstra.lexparse.DijkstraBaseVisitor#visitAlternativeStatement(dijkstra.lexparse.DijkstraParser.AlternativeStatementContext)
	 */
	public ASTNode visitAlternativeStatement(DijkstraParser.AlternativeStatementContext ctx)
	{
		AlternativeNode alternative = AlternativeNode.makeAlternativeNode(ctx.getStart().getLine());
		for (GuardContext guard : ctx.guard()) {
			alternative.addChild(guard.accept(this));
		}
		return alternative;
	}
	
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#guard}.
	 * @see dijkstra.lexparse.DijkstraBaseVisitor#visitGuard(dijkstra.lexparse.DijkstraParser.GuardContext)
	 */
	public ASTNode visitGuard(DijkstraParser.GuardContext ctx)
	{
		return GuardNode.makeGuardNode(ctx.expression().accept(this), 
				ctx.statement().accept(this));
	}
	
	/**
	 * There are different types of expressions. For the AST, there are three types, depending upon the
	 * number of operators.
	 * @see dijkstra.lexparse.DijkstraBaseVisitor#visitExpression(dijkstra.lexparse.DijkstraParser.ExpressionContext)
	 */
	public ASTNode visitExpression(DijkstraParser.ExpressionContext ctx)
	{
		ASTNode result = null;
		TerminalNode tn;
		Token t;
		Token firstt;
		Token secondt;
		ASTNode expr0 = null, expr1 = null;
		switch (ctx.getChildCount()) {
			case 1:  // Terminal node AND others
				//if the child of the expression has no child, it is a TerminalNode
				if(null == ctx.getChild(0).getChild(0))
				{
					tn = (TerminalNode) ctx.getChild(0);
					t = tn.getSymbol();
	
					switch(t.getType())
					{
						case ID:
							result = IDNode.makeIDNode(t);
							break;
						case INTEGER:
						case FLOATCONSTANT:
						case TRUE:
						case FALSE:
							result = ConstantNode.makeConstantNode(t, t.getType());
							break;
						default:
							
					}
				}
				else
				{
					result = ctx.getChild(0).accept(this);
				}
				break;
			case 2:	// Unary expression
				tn = (TerminalNode) ctx.getChild(0);	// the operator
				t = tn.getSymbol();
				result = UnaryExpressionNode.makeUnaryExpressionNode(t, ctx.getChild(1).accept(this));
				
				break;
			case 3:	// Binary expression
				if(ctx.getChild(0).getText().equals("(") == true)
				{
					result = ctx.getChild(1).accept(this);
				}
				else
				{
					tn = (TerminalNode) ctx.getChild(1);	// the operator
					if(DijkstraParser.DOT == tn.getSymbol().getType())
					{
						firstt = ctx.ID(0).getSymbol();
						secondt = ctx.ID(1).getSymbol();
						result = ClassAttributeAccessorNode.makeClassAttributeAccessorNode(IDNode.makeIDNode(firstt, "OBJECT"), IDNode.makeIDNode(secondt) );
					}
					else
					{
						result = BinaryExpressionNode.makeBinaryExpressionNode(tn.getSymbol(), 
							ctx.expression(0).accept(this), ctx.expression(1).accept(this));
					}
				}
				break;
		}
		return result;
	}

	
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#guard}.
	 * @see dijkstra.lexparse.DijkstraBaseVisitor#visitGuard(dijkstra.lexparse.DijkstraParser.GuardContext)
	 * child 0 is name
	 */
	public ASTNode visitFunctioncall(DijkstraParser.FunctioncallContext ctx)
	{
		final IDNode id  = IDNode.makeIDNode(ctx.ID().getSymbol(), "FUNC");		// get variable
		final FuntioncallNode functioncallnode;
		if(ctx.expressionlist() != null)
		{
			ASTNode exprlist = ctx.expressionlist().accept(this);
			functioncallnode = FuntioncallNode.makeFuntioncallNode(id, exprlist);
		}
		else
		{
			functioncallnode = FuntioncallNode.makeFuntioncallNode(id);
		}
		return functioncallnode;
	}
	
	
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#guard}.
	 * @see dijkstra.lexparse.DijkstraBaseVisitor#visitGuard(dijkstra.lexparse.DijkstraParser.GuardContext)
	 */
	public ASTNode visitReturnStatement(DijkstraParser.ReturnStatementContext ctx)
	{
		Token t = ctx.RETURN().getSymbol();
		final ReturnStatementNode returnstatementnode = ReturnStatementNode.makeReturnStatementNode(t);
		if(ctx.expressionlist() != null)
		{
			returnstatementnode.addChild(ctx.expressionlist().accept(this));
		}
		return returnstatementnode;
	}
	
	
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#guard}.
	 * @see dijkstra.lexparse.DijkstraBaseVisitor#visitGuard(dijkstra.lexparse.DijkstraParser.GuardContext)
	 * child 0 is name, child is exprlist
	 */
	public ASTNode visitConstructorNode(DijkstraParser.ConstructorContext ctx)
	{

		final IDNode id  = IDNode.makeIDNode(ctx.classname().ID().getSymbol());		// get variable
		ASTNode exprlist = ctx.expressionlist().accept(this);
		final ConstructorNode constructornode = ConstructorNode.makeConstructorNode(id, exprlist);
		return constructornode;
	}

	/**
	 * Visit an varlist. 
	 * @see dijkstra.lexparse.DijkstraBaseVisitor#visitAssignStatement(dijkstra.lexparse.DijkstraParser.AssignStatementContext)
	 */
	public ASTNode visitVarlist(DijkstraParser.VarlistContext ctx)
	{
		final VarlistNode varlist = VarlistNode.makeVarlistNode();
		for(int i = 0; i < ctx.getChildCount(); i= i+2)
		{
			varlist.addChild(ctx.getChild(i).accept(this));
		}
		return varlist;
	}

	public ASTNode visitExpressionlist(DijkstraParser.ExpressionlistContext ctx)
	{
		final ExprListNode exprlist = ExprListNode.makeExprListNode();
		for(int i = 0; i < ctx.getChildCount(); i = i + 2)
		{
			exprlist.addChild(ctx.getChild(i).accept(this));
		}
		return exprlist;
	}

	/**
	 * Visit an varlist. 
	 * @see dijkstra.lexparse.DijkstraBaseVisitor#visitAssignStatement(dijkstra.lexparse.DijkstraParser.AssignStatementContext)
	 */
	public ASTNode visitVar(DijkstraParser.VarContext ctx)
	{
		ASTNode result = null;
		final ArrayaccessorNode arrayaccessornode;
		//if there is only one child, it must be an ID
		if(null != ctx.arrayaccessor())
		{
			result = ctx.arrayaccessor().accept(this);
		} 
		//more than one child, if there is a DOT token, it is ClassAttributeAccessor
		else if(null != ctx.DOT())		
		{
			result = ClassAttributeAccessorNode.makeClassAttributeAccessorNode(IDNode.makeIDNode(ctx.ID(0).getSymbol(), "OBJECT"), IDNode.makeIDNode(ctx.ID(1).getSymbol()));
		}
		//if this var is not a ID and not a ClassAttributeAccessor, then it must be a arrayaccessor. Otherwise, error.
		else if(1 == ctx.getChildCount())		
		{
			result = IDNode.makeIDNode(ctx.ID(0).getSymbol());
			result.type = DijkstraType.UNDEFINED;
		}
		else
		{
			System.out.println("\n error varlist\n");
		}
		return result;
	}
	
	/**
	 * Visit an varlist. 
	 * @see dijkstra.lexparse.DijkstraBaseVisitor#visitAssignStatement(dijkstra.lexparse.DijkstraParser.AssignStatementContext)
	 */
	public ASTNode visitArrayaccessor(DijkstraParser.ArrayaccessorContext ctx)
	{
		final IDNode id  = IDNode.makeIDNode(ctx.ID().getSymbol());		// get variable
		final ASTNode expr = ctx.expression().accept(this);				// get the expression
		final ArrayaccessorNode Arraryaccessor = ArrayaccessorNode.makeArrayaccessorNode(id,expr);

		return Arraryaccessor;
	}
	
	
	/**
	 * Return null for a type rule. The type can be found by the parent
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	public ASTNode visitType(DijkstraParser.TypeContext ctx)
	{
		return null;
	}
}
