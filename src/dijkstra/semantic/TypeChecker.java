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

package dijkstra.semantic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import dijkstra.ast.*;
import dijkstra.ast.ASTNode.ASTNodeType;
import dijkstra.ast.ASTNodeFactory.*;
import dijkstra.lexparse.DijkstraParser;
import dijkstra.symbol.*;
import dijkstra.utility.DijkstraType;
import static dijkstra.utility.DijkstraType.*;

/**BinaryExpressionNode1
 * Description
 * @version Feb 17, 2015
 */
public class TypeChecker extends ASTVisitor<DijkstraType>
{
	private final Stack<DijkstraType> typeNeeded;
	private boolean continueChecking;		// true if something changed
	private SymbolTableManager stm = SymbolTableManager.getInstance();
	
	private boolean debug = false;
	
	public TypeChecker()
	{
		typeNeeded = new Stack<DijkstraType>();
		continueChecking = false;
	}

	public DijkstraType visit(DijkstraTextNode dijkstratestnode)
	{
		continueChecking = false;
		visitChildren(dijkstratestnode);			// return value doesn't matter
		if (!checkAgain()) {
			validateSymbols();
		}
		return null;
	}
	
	public DijkstraType visit(DeclarationNode decl)
	{
		return decl.type;
	}
	
	public DijkstraType visit(AssignNode assign)
	{
		DijkstraType expectedExprType;
		final VarlistNode varlistnode	= assign.getVarlist();
		final ExprListNode exprlistnode = assign.getExpressionlist();
		ArrayList<DijkstraType> vartypelist = new ArrayList<DijkstraType>();
		ArrayList<DijkstraType> exprtypelist = new ArrayList<DijkstraType>();
		DijkstraType tmpexprtype ;

		for(int i = 0; i < exprlistnode.getChildCount(); i++)
		{
			ASTNode exprnode = exprlistnode.getChild(i);
			
			if(exprnode.type == FUNC)
			{
				if(exprnode.nodeType == ASTNodeType.FUNCCALL || exprnode.nodeType == ASTNodeType.PROCEDURECALL)	
				{
					typeNeeded.push(UNDEFINED);
					exprnode.accept(this);
					typeNeeded.pop();
					
					FuntioncallNode funccallnode = (FuntioncallNode)exprnode;
//					Symbol funcsymbol = stm.getSymbol(funccallnode.getId().getName());
					Symbol funcsymbol = funccallnode.symbol;
					ArrayList<DijkstraType> typeList = funcsymbol.getTypeList();
					Debug("[typechecker]" +typeList +"  name =   " + funcsymbol.getId()) ;
					for(int n = 0; n < typeList.size(); n++)
					{
						exprtypelist.add(i, typeList.get(i));
						i++;
					}
				}
				else if(exprnode.nodeType == ASTNodeType.METHODCALL)
				{
					MethodcallNode methodCallNode = (MethodcallNode)exprnode;
					int currenttable = stm.getcurrentTableNumber();
					Symbol classsymbol = stm.getSymbol(methodCallNode.classname);
					Debug("[TypeChecker] classname =  " + methodCallNode.classname + " methodname =  " + methodCallNode.methodcallName + " classsymbol.talbleNumber =   " + classsymbol.talbleNumber);
				
					stm.enterScope(classsymbol.talbleNumber);
					Symbol methodsymbol = stm.getSymbol(methodCallNode.methodcallName);
					stm.enterScope(currenttable);
					
					Debug("[TypeChecker] methodsymbo = " + methodsymbol);
					
					ArrayList<DijkstraType> typeList = methodsymbol.getTypeList();
					Debug("[typechecker]" +typeList) ;
					for(int n = 0; n < typeList.size(); n++)
					{
						exprtypelist.add(i, typeList.get(i));
						i++;
					}
					typeNeeded.push(UNDEFINED);
					exprnode.accept(this);
					typeNeeded.pop();
				}
			}
			else
			{
				//check the access level
				if(exprnode.nodeType == ASTNodeType.CLASSATTIACCESSOR)
				{
					ClassAttributeAccessorNode attrAccessNode = (ClassAttributeAccessorNode)exprnode;
					Symbol objectsymbol = stm.getSymbol(attrAccessNode.getObjectName().getName());
					int currentTableNum = stm.getcurrentTableNumber();
					stm.enterScope(objectsymbol.talbleNumber);
					Symbol attibuteSymbol = stm.getSymbol(attrAccessNode.getattributeName().getName());
					if(attibuteSymbol.accessSpec == 1)
					{
						throw new DijkstraSymbolException("Trying to access write only attribute '"
								+ attrAccessNode.getObjectName().token.getText() + "." + attrAccessNode.getattributeName().token.getText() + "'");
					}
					stm.enterScope(currentTableNum);
				}
				
				typeNeeded.push(exprnode.type);
				tmpexprtype = exprnode.accept(this);
				exprtypelist.add(i, tmpexprtype);
				typeNeeded.pop();
			}
			
		}
		
		for(int i = 0; i < varlistnode.getChildCount(); i++)
		{
			if(varlistnode.getChild(i).nodeType == ASTNodeType.CLASSATTIACCESSOR)
			{
				ClassAttributeAccessorNode attrAccessNode = (ClassAttributeAccessorNode)varlistnode.getChild(i);
				Symbol objectsymbol = stm.getSymbol(attrAccessNode.getObjectName().getName());
				int currentTableNum = stm.getcurrentTableNumber();
				stm.enterScope(objectsymbol.talbleNumber);
				Symbol attibuteSymbol = stm.getSymbol(attrAccessNode.getattributeName().getName());
				if(attibuteSymbol.accessSpec == 0)
				{
					throw new DijkstraSymbolException("Trying to write a read only attribute '"
							+ attrAccessNode.getObjectName().token.getText() + "." + attrAccessNode.getattributeName().token.getText() + "'");
				}
				stm.enterScope(currentTableNum);
			}
			
			
			typeNeeded.push(varlistnode.type);
			vartypelist.add(i, varlistnode.getChild(i).accept(this));
			typeNeeded.pop();
			
		}	
		
		if(exprtypelist.size() != vartypelist.size())
		{
			int exprlistsize = exprtypelist.size();
			int varlistsize = vartypelist.size();
			throw new DijkstraSemanticException("Needed( ASSIGN) expr numb: " +exprlistsize + " and got var num: " + varlistsize);
		}
		
		for(int n= 0; n < exprtypelist.size(); n++)
		{
			DijkstraType vartype = vartypelist.get(n);
			DijkstraType exprType = exprtypelist.get(n);
			
			if(vartype == INT && exprType == FLOAT)
			{
//				varlistnode.getId(n).setType(exprType);
				Debug("assign set FLOAT to INT");
			}
			else if(vartype == FLOAT && exprType == INT)
			{
//				varlistnode.getId(n).setType(exprType);
				Debug("warning: assigning" + exprType + " to " + vartype);
			}
			else if(vartype == NUMERIC && exprType != UNDEFINED && exprType != BOOLEAN)
			{
				varlistnode.getChild(n).setType(exprType);
				continueChecking = true;
				Debug("assign set expr type to the NUMERIC cartype");
			}
			else if (exprType != vartype && vartype != UNDEFINED && vartype != NUMERIC)
			{
				throw new DijkstraSemanticException("Needed( ASSIGN) " + vartype + " and got " + exprType);
			}

			if (exprType != vartype && vartype == UNDEFINED) 
			{
				if(varlistnode.getChild(n).nodeType == ASTNodeType.CLASSATTIACCESSOR)
				{
					
				}
				else if(exprType == OBJECT)
				{
					throw new DijkstraSemanticException(" Using construcor but the OBJECT is not declared ");
				}
				else if(varlistnode.getChild(n).nodeType == ASTNodeType.ID) 
				{
					IDNode id = (IDNode)varlistnode.getChild(n);
					id.setType(exprType);
					Debug("[TypeChecker] assign type "  + id );
					
				}
				continueChecking = true;
				Debug("[TypeChecker] continuecheck assign type ");
			}
				
		}
		
		return DijkstraType.UNDEFINED;
		
	}
	
//	public DijkstraType visit(AlternativeNode input)
//	{
//		return DijkstraType.UNDEFINED;
//	}
	
	public DijkstraType visit(ArrayaccessorNode arraynode)
	{
		typeNeeded.push(arraynode.type);
		arraynode.type = arraynode.getId().accept(this);
		typeNeeded.pop();
		
		return arraynode.type;
	}
	
	public DijkstraType visit(ArrayDeclarationNode arrardecl)
	{
		for(int i = 1; i < arrardecl.getChildCount(); i++)
		{
			typeNeeded.push(arrardecl.type);
			arrardecl.type = arrardecl.getID(i).accept(this);
			typeNeeded.pop();
		}
		return DijkstraType.UNDEFINED;
	}
	
	public DijkstraType visit(ClassAttributeAccessorNode classaccessnode)
	{
		typeNeeded.push(classaccessnode.type);
		DijkstraType type = classaccessnode.getattributeName().accept(this);
		typeNeeded.pop();
		
		typeNeeded.push(OBJECT);
		classaccessnode.getObjectName().accept(this);
		typeNeeded.pop();
		
		return type;
	}
	
	public DijkstraType visit(ClassdeClarationNode classdeclarationnode)
	{
		Symbol classsymbol = stm.getSymbol(classdeclarationnode.getId().getName());
		ArrayList<DijkstraType> propertylist = classsymbol.getParaTypeList();
		typeNeeded.push(classdeclarationnode.type);
		classdeclarationnode.getId();
		typeNeeded.pop();
		for(int i = 1; i < classdeclarationnode.getChildCount(); i++)
		{
			classdeclarationnode.getChild(i).accept(this);
		}
		
		if(classdeclarationnode.getChild(classdeclarationnode.getChildCount()-1).nodeType == ASTNodeType.PROPERTYLIST)
		{
			PropertyListNode Propertylistnode = (PropertyListNode)classdeclarationnode.getChild(classdeclarationnode.getChildCount()-1);
			for(int n = 0; n < Propertylistnode.getChildCount(); n++)
			{
				DijkstraType propertyType = Propertylistnode.getChild(n).type;
				propertylist.set(n,propertyType);
			}
			classsymbol.setParaTypelist(propertylist);
		}
		
		return classdeclarationnode.type;
	}

	public DijkstraType visit(ConstructorNode node)
	{
		return node.type;
	}
	
	public DijkstraType visit(CompoundNode compound)
	{
		for(int i =0; i < compound.getChildCount(); i++)
		{
			typeNeeded.push(compound.type);
			compound.getChild(i).accept(this);
			typeNeeded.pop();
		}
		return compound.type;
	}
//	public DijkstraType visit(ExprListNode node) 
//	{ 
//		
//		return DijkstraType.UNDEFINED;
//	}
	public DijkstraType visit(FunctionDeclarationNode node) 
	{ 
		final IDNode funcname = node.getID();
		int currentTableNum = stm.getcurrentTableNumber();
		stm.enterScope(node.getID().talbleNumber);
		Symbol funcsymbol = stm.getSymbol(node.getID().getName());
		stm.enterScope(currentTableNum);
		Debug("[typechecker] fundecl name = " + node.getID().getName()+ " symbol = " + funcsymbol);
		
		DijkstraType type;
		ArrayList<DijkstraType> paratypelist = funcsymbol.getParaTypeList();
		if(node.ParameterlistFlag == true)
		{
			ParameterlistNode paralistnode = (ParameterlistNode)node.getChild(node.getChildCount()-1);
	
			for(int i = 0; i < paralistnode.getChildCount(); i++)
			{
				typeNeeded.push(paratypelist.get(i));
				type = paralistnode.getChild(i).accept(this);
				if(type != paratypelist.get(i))
				{
					paratypelist.set(i, type);
				}
				typeNeeded.pop();
			}
		}
		Debug(" typechecker : fundecl node's paratypelist : "+ paratypelist);

		if(node.getChildCount() == 0)
		{
			throw new DijkstraSemanticException("function  '"
					+ funcname.getName() + "' no return values ");

		}
		
		CompoundNode compoundnode = node.getcompoundbody();
		stm.enterScope(compoundnode.symbolTabolNumber);
		visitChildren(compoundnode);
		stm.enterScope(currentTableNum);
		return DijkstraType.UNDEFINED;
	}
	
	public DijkstraType visit(FuntioncallNode functionnode) 
	{ 
		DijkstraType expected = typeNeeded.peek();
		Symbol symbol;
		ArrayList<DijkstraType> paratypelist = new ArrayList<DijkstraType>();

		symbol = stm.getSymbol(functionnode.getId().getName());
		functionnode.symbol = symbol;
		paratypelist = symbol.getParaTypeList();
		ASTNode exprlist = functionnode.getExprListNode();
		Debug("[TypeChecker] functioncallnode name = " + functionnode.getId().getName() + "  exprlist : "+exprlist + " size:  " + paratypelist.size());
		Debug("[typechecker]"+paratypelist);
		
		
		int exprlistsize;
		if(exprlist == null)
		{
			exprlistsize = 0;
		}
		else
		{
			exprlistsize = exprlist.getChildCount();
		}
		
		if(paratypelist.size() != exprlistsize)
		{
			throw new DijkstraSemanticException(" Using Function   '"
					+ functionnode.getId().token.getText() + "' with wrong parameters  ");
		}
		
		for(int i = 0; i < paratypelist.size(); i++)
		{
			typeNeeded.push(paratypelist.get(i));
			Debug("typechecker functioncallnode :paratype check  "+paratypelist.get(i));
			exprlist.getChild(i).accept(this);
			typeNeeded.pop();
		}
		DijkstraType type = symbol.getTypeList().get(0);
		return type;
	}


	public DijkstraType visit(MethodcallNode methodCallNode) 
	{ 
		Symbol symbol;
		ArrayList<DijkstraType> paratypelist = new ArrayList<DijkstraType>();

		int currenttable = stm.getcurrentTableNumber();
		Symbol classsymbol = stm.getSymbol(methodCallNode.classname);
		Debug("[TypeChecker] classname =  " + methodCallNode.classname + " methodname =  " + methodCallNode.methodcallName + " classsymbol.talbleNumber =   " + classsymbol.talbleNumber);
	
		stm.enterScope(classsymbol.talbleNumber);
		Symbol methodsymbol = stm.getSymbol(methodCallNode.methodcallName);
		paratypelist = methodsymbol.getParaTypeList();
		stm.enterScope(currenttable);
		
		Debug("[TypeChecker] method symbol = " + methodsymbol);
		
		ASTNode exprlist = methodCallNode.getExprListNode();
		Debug("[TypeChecker] method name = " + methodCallNode.methodcallName + ":exprlist : "+exprlist + " size:  " + paratypelist.size());
		Debug("[typechecker]  method paratype list "+ paratypelist);
		
		int argumentNum;
		if(exprlist == null)
		{
			argumentNum = 0;
		}
		else
		{
			argumentNum = exprlist.getChildCount();
		}
		
		
		if(paratypelist.size() != argumentNum)
		{
			throw new DijkstraSemanticException(" Using method   '"
					+ methodCallNode.getObjectIdNode().token.getText()+ "."+ methodCallNode.getMethodIdNode().token.getText()
					+ "' with wrong parameters  ");
		}
		
		for(int i = 0; i < paratypelist.size(); i++)
		{
			typeNeeded.push(paratypelist.get(i));
			Debug("[typechecker] method :paratype check  "+paratypelist.get(i));
			exprlist.getChild(i).accept(this);
			typeNeeded.pop();
		}
		DijkstraType type = methodsymbol.getTypeList().get(0);
		return type;
	}

	
	public DijkstraType visit(ParameterlistNode paralistnode) 
	{ 
//		for(int i=0; i < paralistnode.getChildCount(); i++)
//		{
//			typeNeeded.push(paralistnode.type);
//			paralistnode.getChild(i).accept(this);
//			typeNeeded.pop();
//		}
		visitChildren(paralistnode);			// return value doesn't matter
		return DijkstraType.UNDEFINED;
	}
	
	public DijkstraType visit(ParameterNode paranode) 
	{ 
//		typeNeeded.push(paranode.type);
//		paranode.type = 
		paranode.type = paranode.getID().accept(this);
//		typeNeeded.pop();
		return paranode.type;
	}
	
	public DijkstraType visit(InputNode inputnode)
	{
		for(int i = 0; i < inputnode.getChildCount(); i++)
		{
			if(inputnode.getChild(i).nodeType == ASTNodeType.CLASSATTIACCESSOR)
			{
				ClassAttributeAccessorNode attrAccessNode = (ClassAttributeAccessorNode)inputnode.getChild(i);
				Symbol attibuteSymbol = stm.getSymbol(attrAccessNode.getattributeName().getName());
				if(attibuteSymbol.accessSpec == 0)
				{
					throw new DijkstraSymbolException("Trying to write a read only attribute '"
							+ attrAccessNode.getObjectName().token.getText() + "." + attrAccessNode.getattributeName().token.getText() + "'");
				}
			}
			
			typeNeeded.push(inputnode.type);
			inputnode.getChild(i).accept(this);
			typeNeeded.pop();
		}
		
		return inputnode.type;
	}
	
	public DijkstraType visit(ProgramNode program) 
	{ 
		continueChecking = false;
		visitChildren(program);			// return value doesn't matter
		if (!checkAgain()) {
			validateSymbols();
		}
		return null;
	}
	
	public DijkstraType visit(ProcedurecallNode procedurenode) 
	{ 
		Symbol symbol;
		ArrayList<DijkstraType> paratypelist = new ArrayList<DijkstraType>();

		symbol = stm.getSymbol(procedurenode.getId().getName());
		paratypelist = symbol.getParaTypeList();
		ASTNode exprlist = procedurenode.getExprListNode();
		Debug("[typechecker] procedurecallnode :exprlist : "+exprlist + " size:  " + paratypelist.size());
		Debug("[typechecker]" + paratypelist);
		for(int i = 0; i < paratypelist.size(); i++)
		{
			typeNeeded.push(paratypelist.get(i));
			Debug("[typechecker] procedurecallnode :paratype check "+paratypelist.get(i));
			exprlist.getChild(i).accept(this);
			typeNeeded.pop();
		}

		return procedurenode.type;
	}
	
	public DijkstraType visit(ProcedureDeclarationNode node) 
	{ 
		final IDNode funcname = node.getID();
		Symbol funcsymbol = stm.getSymbol(node.getID().getName());
		DijkstraType type;
		ArrayList<DijkstraType> paratypelist = funcsymbol.getParaTypeList();
		if(node.ParameterlistFlag == true)
		{
			ParameterlistNode paralistnode = (ParameterlistNode)node.getChild(node.getChildCount()-1);
	
			for(int i = 0; i < paralistnode.getChildCount(); i++)
			{
				typeNeeded.push(paratypelist.get(i));
				type = paralistnode.getChild(i).accept(this);
				if(type != paratypelist.get(i))
				{
					paratypelist.set(i, type);
				}
				typeNeeded.pop();
			}
		}
		Debug("[TypeChecker] : proceduredecl node's paratypelist : "+ paratypelist);

		if(node.getChildCount() == 0)
		{
			throw new DijkstraSemanticException("procedure  '"
					+ funcname.getName() + "' no return ");

		}
		
		CompoundNode compoundnode = node.getcompoundbody();
		int currentTableNum = stm.getcurrentTableNumber();
		stm.enterScope(compoundnode.symbolTabolNumber);
		visitChildren(compoundnode);
		stm.enterScope(currentTableNum);
		return DijkstraType.UNDEFINED;
	}
	

	
	public DijkstraType visit(PropertyListNode node) 
	{ 
		visitChildren(node);
		return DijkstraType.UNDEFINED;
	}
	
	public DijkstraType visit(PropertyNode node) 
	{ 
		typeNeeded.push(node.type);
		node.type = node.getId().accept(this);
		typeNeeded.pop();
		return DijkstraType.UNDEFINED;
	}
	
	public DijkstraType visit(ReturnStatementNode returnnode) 
	{ 
		for(int i =0; i < returnnode.getChildCount(); i++)
		{
			typeNeeded.push(returnnode.type);
			returnnode.getChild(i).accept(this);
			typeNeeded.pop();
		}
		return returnnode.type;

	}
	

	public DijkstraType visit(variableDeclarationNode variabledecl) 
	{ 
		DijkstraType vardecltype = variabledecl.type;
		DijkstraType childtype;
		for(int i = 0; i < variabledecl.getChildCount(); i++)
		{
			typeNeeded.push(vardecltype);
			childtype = variabledecl.getChild(i).accept(this);
			typeNeeded.pop();
			
		}
		return variabledecl.type;
	}
	
//	public DijkstraType visit(VarlistNode node) 
//	{ 
//
//		return DijkstraType.UNDEFINED;
//	}
	
//	public DijkstraType visit(VarNode node) 
//	{ 
//
//		return DijkstraType.UNDEFINED;
//	}
	
	public DijkstraType visit(OutputNode output)
	{
		Debug("[TypeChecker]  output type = "+output.type);
		ASTNode exprnode = output.getExpression();
		if(exprnode.nodeType == ASTNodeType.CLASSATTIACCESSOR)
		{
			ClassAttributeAccessorNode attrAccessNode = (ClassAttributeAccessorNode)exprnode;
			Symbol objectsymbol = stm.getSymbol(attrAccessNode.getObjectName().getName());
			int currentTableNum = stm.getcurrentTableNumber();
			stm.enterScope(objectsymbol.talbleNumber);
			Symbol attibuteSymbol = stm.getSymbol(attrAccessNode.getattributeName().getName());
			
			if(attibuteSymbol.accessSpec == 3)
			{
				throw new DijkstraSymbolException("Local ttribute cannot be accessed outside the class '"
						+ attrAccessNode.getObjectName().token.getText() + "." + attrAccessNode.getattributeName().token.getText() + "'");
			}
			
			if(attibuteSymbol.accessSpec == 1)
			{
				throw new DijkstraSymbolException("Trying to access write only attribute '"
						+ attrAccessNode.getObjectName().token.getText() + "." + attrAccessNode.getattributeName().token.getText() + "'");
			}
			stm.enterScope(currentTableNum);
		}
		typeNeeded.push(output.type);
		output.type = output.getExpression().accept(this);
		typeNeeded.pop();
		return output.type;
	}
	
//	public DijkstraType visit(IterativeNode iterative)
//	{
//		typeNeeded.push(BOOLEAN);
//		DijkstraType exprType = iterative.getExpression().accept(this);
//		typeNeeded.pop();
//
//		
//		
//		if (exprType == UNDEFINED) { continueChecking = true; Debug("iter");}
//		return iterative.getStatement().accept(this);
//	}
	
	public DijkstraType visit(GuardNode guard)
	{
		typeNeeded.push(BOOLEAN);
		DijkstraType exprType = guard.getExpression().accept(this);
		typeNeeded.pop();
		if (exprType == INT || exprType == FLOAT) {
			throw new DijkstraSemanticException(
					"Guard needs a BOOLEAN expression, but its type is INT");
		}
		
		if(guard.getExpression().nodeType == ASTNodeType.FUNCCALL)
		{
			FuntioncallNode funccallNode = (FuntioncallNode)guard.getExpression();
			Symbol funcSymbol = stm.getSymbol(funccallNode.getId().getName());
			DijkstraType returnType = funcSymbol.getTypeList().get(0);
			if(returnType != BOOLEAN)
			{
				throw new DijkstraSemanticException(
						"Guard needs a BOOLEAN expression, but its type is " + returnType);
			}
			Debug("[Typechecker] returnType = " + returnType + " exprType =  " + exprType); 
			exprType = returnType; 
		}
		
		if (exprType == UNDEFINED) 
		{ 
			continueChecking = true;
			Debug("[Typechecker]continueChecking gaurd"); 
		}
		return guard.getStatement().accept(this);
	}
	
	public DijkstraType visit(UnaryExpressionNode unary)
	{
		// The UnaryExpressionNode type is defined in the constructor
		DijkstraType needed = typeNeeded.peek();
		
		Debug("[Typechecker] unary : "+ unary + " needed:  "+needed.toString() + " unary type " + unary.type.toString());
		if(unary.type == NUMERIC && (needed == INT || needed == FLOAT))
		{
			unary.type = needed;
		}
		if (needed != UNDEFINED && needed != unary.type && needed != NUMERIC) {
			throw new DijkstraSemanticException("Unary expression should be "
					+ needed + " but is " + unary.type);
		}
		
//		typeNeeded.push(unary.type);
		typeNeeded.push(needed);
		DijkstraType exprType = unary.getChild(0).accept(this);
		typeNeeded.pop();
		
		Debug(needed.toString() + " unary type: " + unary.type.toString());
		
		if(unary.type == NUMERIC)
		{
			unary.type = exprType;
		}
		Debug("[Typechecker] needed : " + needed.toString() + " unary type: " + unary.type.toString() + " exprtype : " + exprType);
		
		if(unary.getExpression().nodeType == ASTNodeType.FUNCCALL)
		{
			FuntioncallNode funccallNode = (FuntioncallNode)unary.getExpression();
			Symbol funSymbol = stm.getSymbol(funccallNode.getId().getName());
			Debug("[Typechecker]func name "+ funccallNode.getId().getName()+ "  funSymbol returnlist : " + funSymbol.getTypeList());

			DijkstraType returnType = funSymbol.getTypeList().get(0);
			if(returnType != needed)
			{
				throw new DijkstraSemanticException("Unary function should be "
						+ needed + " but is " + unary.type);			
			}
		}
		else if (needed != NUMERIC && exprType != unary.type) { 
			continueChecking = true; 
			Debug("[Typechecker]continueChecking Unary");
		}
		return unary.type;
	}
	
	public DijkstraType visit(BinaryExpressionNode binary)
	{
		// The BinaryExpressionNode type is defined in the constructor
		DijkstraType expected = typeNeeded.peek();
		DijkstraType need = UNDEFINED;
		Debug("[typechecker]binary  " + binary.type + " expected  " + expected);
		if(((expected == INT || expected == FLOAT ) && (binary.type == NUMERIC)) 
				|| ((binary.type == INT || binary.type == FLOAT ) && (expected == NUMERIC))
						||((expected == INT || expected == FLOAT) && (binary.type == INT || binary.type == FLOAT)))
		{
			
		}
		else if (expected != UNDEFINED && binary.type != NUMERIC && expected != binary.type) {
			Debug("[typechecker]" + binary);
			throw new DijkstraSemanticException("Binary expression should be "
					+ expected + " but is " + binary.type);
		}

		if (binary.type == BOOLEAN) 
		{
			if (binary.getOp() == DijkstraParser.EQ || binary.getOp() == DijkstraParser.NEQ)
			{
				need = UNDEFINED;
			}
			else if( binary.getOp() == DijkstraParser.GT || binary.getOp() == DijkstraParser.GTE
					|| binary.getOp() == DijkstraParser.LT || binary.getOp() == DijkstraParser.LTE
					|| binary.getOp() == DijkstraParser.GT) 
			{
				need = NUMERIC;		// Any type will do
//				need = UNDEFINED;
			}
			if (binary.getOp() == DijkstraParser.OR || binary.getOp() == DijkstraParser.AND)
					 {
				need = BOOLEAN;		// Any type will do
			}
		}
		else if (binary.type == NUMERIC)
		{
			if(binary.getOp() == DijkstraParser.MOD || binary.getOp() == DijkstraParser.DIV)
			{
				need = INT;
			}
			else
			{
				need = NUMERIC;
			}
			
		}
		else if(binary.type == INT || binary.type == FLOAT)
		{
			need = binary.type;
		}
		
		Debug("[typechecker] binary need  " + need);
		typeNeeded.push(need);
		DijkstraType expr1Type = binary.getExpr1().accept(this);
		DijkstraType expr2Type = binary.getExpr2().accept(this);
		typeNeeded.pop();
		Debug("[typechecker] binary expr1Type  " + expr1Type + " expr2Type " + expr2Type);
		
		if(binary.getOp() == DijkstraParser.MOD || binary.getOp() == DijkstraParser.DIV)
		{
			if(expr1Type == FLOAT || expr2Type == FLOAT)
			{
				throw new DijkstraSemanticException("mod and div operation only allows INT " + "BUT got" +expr1Type + " and " + expr2Type);
			}
		}
		
		if(binary.type == NUMERIC)
		{
			if(expr1Type == FLOAT || expr2Type == FLOAT )//waiting for implementing
			{
				binary.type = FLOAT;
				continueChecking = true;
				Debug("[Typechecker]continueChecking BinaryTYPE = FLOAT " + binary.type);
			}
			else if(binary.getOp() == DijkstraParser.SLASH)
			{
				binary.type = FLOAT;
				continueChecking = true;
				Debug("[Typechecker]continueChecking because SLASH BinaryTYPE = FLOAT " + binary.type);
			}
			else
			{
				binary.type = INT;
				continueChecking = true;
				Debug("[Typechecker]continueChecking BinaryTYPE = INT " + binary.type);
			}
		}
		else if(binary.type == BOOLEAN )
		{
			if((expr1Type != expr2Type) && (expr1Type == BOOLEAN || expr2Type == BOOLEAN))
			{
				throw new DijkstraSemanticException("Needed( BOOLEAN) " + "and got" +expr1Type + " + " + expr2Type);

			}
		}
		else if(binary.type == FLOAT)
		{
			
		}
		else if (need != UNDEFINED) {	// We know what we need
			if (expr1Type != need || expr2Type != need) {
				continueChecking = true;
				Debug("[Typechecker]continueChecking BinaryExpressionNode1");
			}
		} 
		else 
		{
			// We would accept anything, but they need to be the same type
			if (expr1Type == expr2Type && expr1Type == UNDEFINED) {
				continueChecking = true;
				Debug("[Typechecker]continueChecking BinaryExpressionNode2");
			}
		}
		return binary.type;
	}
	
	public DijkstraType visit(IDNode id)
	{
		DijkstraType needed = typeNeeded.peek();
		DijkstraType idType = id.getType();
		Debug("[Typechecker] idname: " +id.getName() + "  needed    " + needed + "  idType " + idType);
		if((needed != idType) && ((needed == INT && idType == FLOAT) || (idType == INT && needed == FLOAT)))
		{
			Debug("warning : " + id.getName()+ " is  " + idType + " but needed " + needed);
		}
		else if((idType == NUMERIC && needed == INT) || (idType == NUMERIC && needed == FLOAT))
		{
			id.setType(needed); 
			continueChecking = true;
			Debug( "[Typechecker]continueChecking IDNode change numeric to in or float = " +id.getName()+ " id.type = " + id.type + " needed = " + needed);

		}
		else if(needed == CLASS && idType == OBJECT)
		{
			
		}
		else if ((idType != needed) && (needed != UNDEFINED) 
				&& (idType != UNDEFINED) && (needed != NUMERIC)) 
		{
			throw new DijkstraSemanticException("Identifier " + id.getName() + " should be "
					+ needed + " but is " + idType);
		}
		
		if (idType == UNDEFINED) 
		{ 
			id.setType(needed); 
			continueChecking = true;
			Debug( "[Typechecker]continueChecking IDNode1 = " +id.getName()+ " id.type = " + id.type + " needed = " + needed);
		}
		
		if((id.getType() == UNDEFINED || id.getType() == NUMERIC) && (needed != UNDEFINED && needed != NUMERIC))
		{
			id.setType(needed); 
			continueChecking = true;
			Debug( "[Typechecker]continueChecking IDNode2 = " +id.getName()+ " id.type = " + id.type + " needed = " + needed);
		}
		
		if(id.getType() != id.type)
		{
			id.type = id.getType();
			Debug("[Typechecker]  "+id.getType()+ " id type" + " idnode type  " + id.type);
		}
		return id.getType();
	}
	
	public DijkstraType visit(ConstantNode c)
	{
		DijkstraType needed = typeNeeded.peek();
		if(needed == FLOAT && c.type == INT)
		{
			c.type = FLOAT;
		}
		if (c.type != needed && needed != UNDEFINED && needed != NUMERIC) {
			throw new DijkstraSemanticException("Needed " + needed + " and got " + c );
			
		}
		Debug("[Typechecker] constant type = "+c.type + "needed = " + needed);
		return c.type;
	}
	
	/**
	 * @return the changed field's value
	 */
	public boolean checkAgain()
	{
		return continueChecking;
	}
	
	/**
	 * Make sure that there are no symbols that are undefined.
	 */
	private void validateSymbols()
	{
		for (SymbolTable st : stm.getTables()) {
			for (Symbol symbol : st.getSymbols()) {
				if (symbol.getType() == UNDEFINED) {
					throw new DijkstraSemanticException(
							"Cannot define type of '" + symbol.getId() + "' ");
				}
			}
		}
	}
	
	/**
	 * Print out a debugging message. If you're not debugging, just set the debugging
	 * variable.
	 * @param msg
	 */
	private void debug(String msg) {
		if (debug) {
			Debug("DEBUG: " + msg);
		}
	}
}
