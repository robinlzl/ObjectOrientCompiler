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

import java.util.ArrayList;

import javax.xml.ws.handler.MessageContext.Scope;

import org.antlr.v4.runtime.Token;

import dijkstra.ast.ASTNode;
import dijkstra.ast.ASTNode.ASTNodeType;
import dijkstra.ast.ASTVisitor;
import dijkstra.ast.ASTNodeFactory.*;
import dijkstra.gen.JVMInfo;
import dijkstra.symbol.Symbol.symbolTypeEnum;
import dijkstra.utility.DijkstraType;
import static dijkstra.lexparse.DijkstraParser.RETURN;
import static dijkstra.utility.DijkstraType.*;
import static dijkstra.ast.ASTNode.ASTNodeType.*;
/**
 * Create the symbol table entries by visiting the AST that was
 * created by ASTCreator.
 * 
 * @version Feb 15, 2015
 */
public class SymbolCreator extends ASTVisitor<DijkstraType>
{
	private final SymbolTableManager stm;
	
	/**
	 * Default constructor.
	 */
	public SymbolCreator()
	{
		stm = SymbolTableManager.getInstance();
		stm.reset();
		JVMInfo.reset();
	}
	
//	public DijkstraType visit(DeclarationNode decl)
//	{
//		IDNode id = decl.getId();
//		id.type = decl.type;
//		id.symbol = stm.add(/*id.token.getText()*/id.getName(), decl.type);
//		return decl.type;
//	}
	
	public DijkstraType visit(AssignNode node) 
	{ 
		DijkstraType exprtype;
		IDNode id;
		if(node.getVarlist().getChildCount() < node.getExpressionlist().getChildCount())
		{
			throw new DijkstraSymbolException(" Assigned too many values : only need "
					+ node.getVarlist().getChildCount() + " but got " + node.getExpressionlist().getChildCount());
		}
		
		for(int i = 0; i < node.getExpressionlist().getChildCount(); i++)
		{
			exprtype = node.getExpressionlist().getChild(i).accept(this);
		}	
		node.getVarlist().accept(this);
		return UNDEFINED;
	}

	public DijkstraType visit(AlternativeNode node) 
	{ 
		visitChildren(node);
		return UNDEFINED;
	}
	
	public DijkstraType visit(ArrayaccessorNode arraryaccessnode) 
	{ 
		IDNode id = arraryaccessnode.getId();
//		Symbol s = stm.addIfNew(/*id.token.getText()*/id.getName());
		Symbol s = stm.addIfNew(id.getName());
		id.symbol = s;
		id.type = s.getType();
		DijkstraType exprtype = arraryaccessnode.getExpression().accept(this);
		if(exprtype != INT && exprtype != NUMERIC && exprtype != UNDEFINED)
		{
			throw new DijkstraSymbolException("array index must be int");
		}
		return arraryaccessnode.type;
	}
	
	public DijkstraType visit(ArrayDeclarationNode node) 
	{ 
		DijkstraType exprtype = node.getExpression().accept(this);
		DijkstraType arrarytype = node.getType();
		if(exprtype != INT)
		{
			throw new DijkstraSymbolException("array index must be int");
		}
		//get id from idlist
		for (int i = 1; i < node.getChildCount(); i++) 
		{
			IDNode id = node.getID(i);
			id.type = node.type;
			id.symbol = stm.add(/*id.token.getText()*/id.getName(), node.type);
			id.symbol.symbolType = symbolTypeEnum.ARRAY;
			id.accept(this);
		}

		return node.getType();
	}
	
	public DijkstraType visit(BinaryExpressionNode node) 
	{ 
		DijkstraType type = node.type;
		visitChildren(node);
		return type;
	}

	
//	public DijkstraType visit(DijkstraTextNode node) 
//	{ 
//		if(node.getChildCount() > 1)
//		{
//			for()
//		}
//		return DijkstraType.UNDEFINED;
//	}
	
	
	public DijkstraType visit(ClassAttributeAccessorNode node) 
	{ 
		IDNode attribute  = node.getattributeName();
		IDNode classObject = node.getObjectName();
		int currentsymboltalbenum;
		Symbol classObjectSymbol = stm.getSymbol(classObject.getName());
		if(classObjectSymbol == null)
		{
			throw new DijkstraSymbolException("Using an object '"
					+ classObject.getName() + "' before it is declared");
		}
		
		
		currentsymboltalbenum = stm.getcurrentTableNumber();
		stm.enterScope(classObjectSymbol.talbleNumber);
		Debug("[SymbolCreator] attribute talbe Number = " +classObjectSymbol.talbleNumber + " name=  " + attribute.getName());
		Symbol attibuteSymbol = stm.getSymbol(attribute.getName());
		
		attribute.accept(this);
		classObject.accept(this);
		node.type = attibuteSymbol.getType();
		
		stm.enterScope(currentsymboltalbenum);
		return attibuteSymbol.getType();
	}
	
	public DijkstraType visit(ClassdeClarationNode node) 
	{ 

		IDNode id = node.getId();
		Symbol s = stm.addIfNew(id.getName(),node.type);
		ArrayList<DijkstraType> paratypelist = node.getProperty().propertyTypeList;
		s.setParaTypelist(paratypelist);
		id.symbol = s;
		id.symbol.symbolType = symbolTypeEnum.CLASS;
		id.symbol.accesslist = node.getProperty().accessSpecList;
		stm.enterScope();
		id.symbol.talbleNumber = stm.getcurrentTableNumber();
		
		for(int i = 0; i < node.getProperty().getChildCount(); i++)
		{
			PropertyNode propertynode = (PropertyNode)node.getProperty().getChild(i);
			propertynode.accept(this);
			Symbol propertySymbol = stm.getSymbol(propertynode.getId().getName());
			propertySymbol.setClassname(id.token.getText());
		}
		
		for(int n = 1; n < node.getChildCount() - 1; n++)
		{
			if(node.getChild(n).nodeType == ASTNodeType.FUNCTIONDECL)
			{
				IDNode funcId = ((FunctionDeclarationNode)node.getChild(n)).getID();
				Symbol funcSymbol = stm.addIfNew(funcId.getName(), DijkstraType.FUNC);
				funcSymbol.setClassname(id.token.getText());
			}
		}
		
//		Debug("[SymbolCreator] id.symbol.talbleNumber = " + id.symbol.talbleNumber);
		for(int i = 1; i < node.getChildCount()-1; i++)
		{
			node.getChild(i).accept(this);
		}
		stm.exitScope();
		
		return id.type;
	}

	public DijkstraType visit(ConstructorNode node) 
	{ 
		return node.type;
	}
	
	public DijkstraType visit(ConstantNode node) 
	{ 
		return node.type;
	}
	
	public DijkstraType visit(ExprListNode node) 
	{ 
		visitChildren(node);
		return DijkstraType.UNDEFINED;
	}
	
	public DijkstraType visit(FunctionDeclarationNode node) 
	{ 
		IDNode id = node.getID();
		int returnchildnumber =  node.getcompoundbody().getChildCount()-1;
		ASTNode returnstatementlistnode;
		
		if(returnchildnumber >= 0)
		{
			returnstatementlistnode = node.getcompoundbody().getChild(returnchildnumber);
			if(returnstatementlistnode.nodeType == ASTNodeType.RETURNLIST)
			{
				if(node.returntypeList.size() != returnstatementlistnode.getChild(0).getChild(0).getChildCount())
					{
						throw new DijkstraSymbolException("function  '"
								+ id.getName() + "' return value number is error");
					}
			}
			else
			{
				throw new DijkstraSymbolException("function  '"
						+ id.getName() + "' does not have a return value");
			}
		}else
		{
			throw new DijkstraSymbolException("function  '"
					+ id.getName() + "' does not have a return value");
		}
		
		id.symbol = stm.addIfNew(/*id.token.getText()*/id.getName(), node.type); 
//		id.symbol = stm.getSymbol(id.getName());
		id.symbol.setTypelist(node.returntypeList);
		ArrayList<DijkstraType> paratypelist = node.paratypeList;
		id.symbol.setParaTypelist(paratypelist);
		id.symbol.symbolType = symbolTypeEnum.FUNC;
		id.symbol.talbleNumber = stm.getcurrentTableNumber();
		id.talbleNumber = stm.getcurrentTableNumber();
		Debug("[SymbolCareator] funtion decl function name :" + id.getName() + " paralist " + id.symbol.getParaTypeList() + " returnlist : " + id.symbol.getTypeList());
		Debug("[SymbolCreater] funcdecl table number = " + id.talbleNumber);
		CompoundNode compoundNode  = node.getcompoundbody();	
		stm.enterScope();
		
		compoundNode.symbolTabolNumber = stm.getTables().size()-1;
		if(node.getParameterlistFlag() == true)
		{
			node.getParameterlist().accept(this);
		}

		compoundNode.functionAddressOffset = node.paratypeList.size();
		Debug("[SymbolCreator]node.paratypeList ++++ "+node.paratypeList + " name : " + /*id.token.getText()*/id.getName() + "  functionAddressOffset "+ compoundNode.functionAddressOffset);
		stm.getCurrentSymbolTable().setIdAddressOffest(compoundNode.functionAddressOffset);
		compoundNode.accept(this);

		stm.exitScope();
		return node.type;
	}
	
	public DijkstraType visit(FuntioncallNode node) 
	{ 
		IDNode id = node.getId();
		ArrayList<DijkstraType> symbolParatypeList = new ArrayList<DijkstraType>();
		Symbol classsymbol = stm.getSymbol(id.token.getText()+"CLASS");
		if(null != classsymbol)
		{
			//FOR CONSTRUCTOR
			id.postfix = "CLASS";
			id.symbol = classsymbol;
			Debug("[SymbolCreator] this is a constructor " + stm.getCurrentSymbolTable());
			symbolParatypeList = id.symbol.getParaTypeList();
			Debug("[SymbolCreator] constructor call name :" + id.getName() + " paralist " + symbolParatypeList + " returnlist :" + id.symbol.getTypeList());
			ArrayList<DijkstraType> typelist = new ArrayList<DijkstraType>();
			typelist.add(DijkstraType.OBJECT);
			id.symbol.setTypelist(typelist);
		}
		else
		{
			//Other functions
//			id.symbol = stm.addtoMainIfNew(id.getName());
			id.symbol = stm.getSymbol(id.getName());
			if(id.symbol == null)
			{
				throw new DijkstraSymbolException(" Function    '"
						+ id.token.getText() + "' is not declared");
			}
			Debug("[SymbolCreator] talbe numb " + stm.getcurrentTableNumber() +"  name  = "+ id.getName() +"  symbol = " + id.symbol);
			id.type = DijkstraType.FUNC;
			id.symbol.setType(DijkstraType.FUNC);
			symbolParatypeList = id.symbol.getParaTypeList();
			Debug("[SymbolCreator] function call name :" + id.getName() + " paralist " + symbolParatypeList + " returnlist :" + id.symbol.getTypeList());
		}


		if(node.getChildCount() > 1 && symbolParatypeList.isEmpty() == false)//if there is expression nodes under funtioncall node
		{
			for(int i = 0; i < node.getExprListNode().getChildCount(); i++)
			{
				DijkstraType tempParaType = node.getExprListNode().getChild(i).type;
				if(tempParaType != UNDEFINED && 
						symbolParatypeList.get(i) == UNDEFINED)
				{
					symbolParatypeList.set(i, tempParaType);
					node.getExprListNode().getChild(i).type = tempParaType;
				}
			}
			Debug("symbol careator function call name :" + id.getName() + " paralist " + id.symbol.getParaTypeList());
		}
		
		for(int i = 1; i < node.getChildCount(); i++)
		{
			node.getChild(i).accept(this);
		}

		return DijkstraType.FUNC;
	}
	
	public DijkstraType visit(GuardNode node) 
	{ 
		visitChildren(node);
		return DijkstraType.UNDEFINED;
	}
	
	public DijkstraType visit(IterativeNode node) 
	{ 
		visitChildren(node);
		return DijkstraType.UNDEFINED;
	}
	
	public DijkstraType visit(MethodcallNode node) 
	{ 
		IDNode methodid = node.getID();
		Symbol classsymbol = stm.getSymbol(node.classname);
		if(classsymbol == null)
		{
			throw new DijkstraSymbolException("CLASS   '"
					+ node.getObjectIdNode().token.getText() + "' is not declared");
		}
		int currentTableNum = stm.getcurrentTableNumber();
		stm.enterScope(classsymbol.talbleNumber);
//		methodid.symbol = stm.addIfNew(methodid.getName());
		Symbol methodcallSymbol = stm.getSymbol(node.methodcallName);
		
		if(methodcallSymbol == null)
		{
			throw new DijkstraSymbolException("Methodcall   '"
					+ node.methodcallName + "' is not declared");
		}
		methodid.symbol = methodcallSymbol;
		methodid.symbol.setType(methodcallSymbol.getType());
		Debug("[SymbolCreator] method call symbol name =  "+ methodcallSymbol.getId() + " METHOD NAME = " + node.methodcallName + "  table number " + stm.getcurrentTableNumber());
		methodid.type = DijkstraType.FUNC;
		methodcallSymbol.setType(FUNC);
		for(int i = 1; i < node.getChildCount(); i++)
		{
			node.getChild(i).accept(this);
		}
		stm.enterScope(currentTableNum);
		return DijkstraType.FUNC;
	}
	
	public DijkstraType visit(OutputNode node) 
	{ 
		DijkstraType outputtype = node.getExpression().accept(this);
//				node.getExpression().accept(this);
		visitChildren(node);
		node.type = outputtype;
		Debug("[SymbolCreator] out put node type = " + outputtype + " the node is " + node);
		return outputtype;
	}
	
	public DijkstraType visit(ParameterlistNode node) 
	{ 
		visitChildren(node);
		return DijkstraType.UNDEFINED;
	}
	
	public DijkstraType visit(ParameterNode node) 
	{ 
		DijkstraType paratype;
		IDNode id = node.getID();
		id.symbol = stm.add(/*id.token.getText()*/id.getName(), node.type);
		Debug("[SymbolCreator]paranode IDname =  " + id.getName() + " type =  " + id.symbol.getType());
		paratype = node.getID().accept(this);
		return paratype;
	}
	
	public DijkstraType visit(ProcedurecallNode node) 
	{ 

		IDNode id = node.getID();
//		id.symbol = stm.addIfNew(id.getName(), node.type);
		id.symbol = stm.getSymbol(id.getName());
		id.type = DijkstraType.FUNC;
		ArrayList<DijkstraType> symbolParatypeList = id.symbol.getParaTypeList();

		if(node.getChildCount() > 1)//if there is expression nodes under funtioncall node
		{
			for(int i = 0; i < node.getExprListNode().getChildCount(); i++)
			{
				DijkstraType tempParaType = node.getExprListNode().getChild(i).type;
				if(symbolParatypeList.isEmpty() != false)
				{
					if(tempParaType != UNDEFINED && 
							symbolParatypeList.get(i) == UNDEFINED)
					{
						symbolParatypeList.set(i, tempParaType);
						node.getExprListNode().getChild(i).type = tempParaType;
					}
				}
			}
			Debug("[SymbolCareator] procedure call name :" + id.getName() + " paralist " + id.symbol.getParaTypeList());
		}
		
		for(int i = 1; i < node.getChildCount(); i++)
		{
			node.getChild(i).accept(this);
		}

		return DijkstraType.FUNC;
	}
	
	public DijkstraType visit(ProcedureDeclarationNode node) 
	{ 
		int i;
		IDNode id = node.getID();
		ASTNode compoundnode ;
		
		for(i = 0; i < node.getcompoundbody().getChildCount(); i++)
		{
			compoundnode = node.getcompoundbody().getChild(i);
			if(compoundnode != null)
			{
				if(compoundnode.nodeType == ASTNodeType.RETURNLIST) break;
			}
		}
		
		if(i < node.getcompoundbody().getChildCount())
		{
			for(int n = 0; n < node.getcompoundbody().getChild(i).getChildCount(); n++)
			{
				ReturnStatementNode returnNode = (ReturnStatementNode)node.getcompoundbody().getChild(i).getChild(n);
				if(returnNode.getChildCount() != 0)
				{
					throw new DijkstraSymbolException("function  '"
							+ id.getName() + "' does not have a return value");
				}
			}
		}

//		id.symbol = stm.add(/*id.token.getText()*/id.getName(), node.type);
		
		id.symbol = stm.addIfNew(/*id.token.getText()*/id.getName(), node.type); 
		ArrayList<DijkstraType> paratypelist = node.paratypeList;
		id.symbol.setParaTypelist(paratypelist);
		Debug("[SymbolCareator] procedure decl proc name :" + id.getName() + " paralist " + id.symbol.getParaTypeList());

		CompoundNode compoundNode  = node.getcompoundbody();	
		stm.enterScope();
		
		compoundNode.symbolTabolNumber = stm.getTables().size()-1;
		if(node.getParameterlistFlag() == true)
		{
			node.getParameterlist().accept(this);
		}

		compoundNode.functionAddressOffset = node.paratypeList.size();
		Debug("[SymbolCreator]proc.paratypeList ++++   "+node.paratypeList + "  functionAddressOffset "+ compoundNode.functionAddressOffset);
		stm.getCurrentSymbolTable().setIdAddressOffest(compoundNode.functionAddressOffset);
		compoundNode.accept(this);

		stm.exitScope();
//		visitChildren(node);
		return node.type;
	}
	
	public DijkstraType visit(ProgramNode node) 
	{ 
		for (int i = 0; i < node.getChildCount(); i++) 
		{
			if((node.getChild(i).nodeType ==  PROCEDUREDECL)
				|| (node.getChild(i).nodeType ==   FUNCTIONDECL))
			{
				IDNode id = (IDNode)node.getChild(i).getChild(0);
				id.symbol = stm.addIfNew(/*id.token.getText()*/id.getName(), DijkstraType.FUNC);
			}
			else if(node.getChild(i).nodeType == VARIABLEDECL)
			{
				
				Debug("[SymbolCreator] var decl DijkstraTypet   "+ node.getChild(i).type.toString() + " curtable " + stm.getcurrentTableNumber());
				if(node.getChild(0).type != DijkstraType.CLASS)
				{
					for(int n = 0; n < node.getChild(i).getChildCount(); n++)
					{
						IDNode id = (IDNode)node.getChild(i).getChild(n);
						id.symbol = stm.addIfNew(/*id.token.getText()*/id.getName(), node.type);
					}
				}
			}
			else if(node.getChild(i).nodeType ==  ARRARYDECL)
			{
				
			}
		}
		
		for (int i = 0; i < node.getChildCount(); i++) 
		{
			if((node.getChild(i).nodeType == VARIABLEDECL) 
				|| (node.getChild(i).nodeType ==  PROCEDUREDECL)
				|| (node.getChild(i).nodeType ==   FUNCTIONDECL)
				|| (node.getChild(i).nodeType ==  ARRARYDECL))
				{
					node.getChild(i).accept(this);
				}
		}
		
		for (int i = 0; i < node.getChildCount(); i++) 
		{
			if((node.getChild(i).nodeType != VARIABLEDECL) 
				&& (node.getChild(i).nodeType !=  PROCEDUREDECL)
				&& (node.getChild(i).nodeType !=   FUNCTIONDECL)
				&& (node.getChild(i).nodeType !=  ARRARYDECL))
				{
					node.getChild(i).accept(this);
				}
		}
		return DijkstraType.UNDEFINED;
	}
	
	public DijkstraType visit(PropertyListNode node) 
	{ 
		visitChildren(node);
		return DijkstraType.UNDEFINED;
	}
	
	public DijkstraType visit(PropertyNode node) 
	{ 
		IDNode id = node.getId();
		id.symbol = stm.addIfNew(id.getName(), node.type);
		id.setType(id.symbol.getType());
		Debug("[SymbolCreator] propertyNode symbol name =   "+ id.getName() + "  symbol =   " + id.symbol);
		id.symbol.accessSpec = node.accessSpec;
		id.symbol.symbolType = symbolTypeEnum.PROPERTY;
		node.type = id.symbol.getType();
		return id.symbol.getType();
	}
	
//	public DijkstraType visit(ReturnStatementNode node) 
//	{ 
//		
//		return DijkstraType.UNDEFINED;
//	}
	
	public DijkstraType visit(UnaryExpressionNode node) 
	{ 
		DijkstraType type = node.type;
		visitChildren(node);
		return type;
	}
	
	public DijkstraType visit(variableDeclarationNode node) 
	{ 
		if(node.type == DijkstraType.CLASS)
		{
			IDNode id = node.getId(0);
			id.type = CLASS;
			if(null == stm.getSymbol(id.getName()))
			{
				throw new DijkstraSymbolException("Using the class (name = '"
						+ id.getName() + "') before it is declared");
			}
			id.symbol = stm.addIfNew(id.getName(), CLASS);
			id.symbol.symbolType = symbolTypeEnum.CLASS;
			/****************this part is not right********************/
			for(int i = 1; i < node.getChildCount(); i++)
			{
				IDNode objectid = node.getId(i);
				objectid.type = OBJECT;
				Symbol objectsymbol = stm.addIfNew(/*id.token.getText()*/objectid.getName(), DijkstraType.OBJECT);
				objectid.symbol = objectsymbol;
				objectsymbol.setType(OBJECT);
				objectid.symbol.setClassSymbol(id.symbol);
				objectid.symbol.setClassname(id.token.getText());
				objectid.symbol.symbolType = symbolTypeEnum.OBJECT;
				objectid.symbol.talbleNumber = id.symbol.talbleNumber;
				Debug("[SymbolCreator] object declaration talbe Number = " +objectid.symbol.talbleNumber + " name=  " + objectid.getName()+ " id.symbol.talbleNumber "+ id.symbol.talbleNumber);
				Debug("[SymbolCreator] var decl name = "+ objectsymbol.getId() + " type = " + objectsymbol.getType());
			}
		}
		else
		{
			for(int i = 0; i < node.getChildCount(); i++)
			{
				IDNode id = node.getId(i);
//				id.type = node.type;
				id.symbol = stm.addIfNew(id.getName(), node.type);
				id.setType(node.type);
				id.symbol.setPostfix(id.postfix);
				Debug("[SymbolCreator] vardecl id name = "+ id.getName() + " node.type "+  node.type + " id.type  " + id.type + " symbol.type  " + id.symbol.getType()
						+" table number = " + id.symbol.talbleNumber+ " post fix " + id.symbol.getPostfix() + " id postfix =" +id.postfix);
			}
		}
		return node.type;
	}
	
	public DijkstraType visit(VarlistNode node) 
	{ 
//		visitChildren(node);
		IDNode id;
		for(int i =0; i < node.getChildCount(); i++)
		{
			if(node.getChild(i).nodeType == ARRARYACCESSOR)
			{
				visitChildren(node);
			}
			else if (node.getChild(i).nodeType == CLASSATTIACCESSOR)
			{
				visitChildren(node);
			}
			else
			{
				id = (IDNode)node.getChild(i);
				Symbol objectsymbol = stm.getSymbol(id.token.getText()+"OBJECT");
				if(null != objectsymbol)
				{
					id.postfix = "OBJECT";
					id.symbol = objectsymbol;
					id.type = objectsymbol.getType();
					Debug("[SymbolCreator] varlistnode id =  "+ id.getName()+ " type = "+ objectsymbol.getType());
				}
				else
				{
					id.symbol = stm.addIfNew(/*id.token.getText()*/id.getName(), id.type);
					Debug("[SymbolCreator]  id =  " + id.token.getText()+ "  type "+ id.type);
				}
			}
		}
		return DijkstraType.UNDEFINED;
	}
	
	public DijkstraType visit(VarNode node) 
	{ 
//		DijkstraType type = visitChildren(node);
//		node.setType(type);
		IDNode id;
		if(node.getChildCount() == 1)
		{
			if(node.getChild(0).nodeType == ARRARYACCESSOR)
			{
				id = (IDNode)node.getChild(0).getChild(0);
			}
			else
			{
				id = (IDNode)node.getChild(0);
			}
		}
		else
		{
			id = (IDNode)node.getChild(0);
		}

		id.symbol = stm.addIfNew(/*/*id.token.getText()*/id.getName());
		return id.symbol.getType();
	}
	

	public DijkstraType visit(InputNode node)
	{
		for(int i = 0; i < node.getChildCount(); i++)
		{
			IDNode id = node.getID(i);
			id.symbol = stm.addIfNew(id.getName());
			id.type = id.symbol.getType();
			node.getID(i).accept(this);
		}
		return DijkstraType.UNDEFINED;
	}
	
	public DijkstraType visit(CompoundNode node) 
	{ 
		Debug("[Symbolcreator] compound table number " + stm.getcurrentTableNumber());
		visitChildren(node);
		return UNDEFINED;
	}
	
	public DijkstraType visit(ReturnStatementNode node) 
	{ 
		visitChildren(node);
		return UNDEFINED;
	}
	
	public DijkstraType visit(ReturnStatementlistNode node) 
	{ 
		visitChildren(node);
		return UNDEFINED;
	}
	
	/**
	 * This ID is in an expression. If there is no symbol, then it is an error
	 * because it should have been defined before this.
	 * @see dijkstra.ast.ASTVisitor#visit(dijkstra.ast.ASTNodeFactory.IDNode)
	 */
	public DijkstraType visit(IDNode id)
	{
		Symbol s = stm.getSymbol(id.getName());
		if (s == null) {
			throw new DijkstraSymbolException("Using the identifier '"
					+ id.getName() + "' before it is declared");
		}
		s.setPostfix(id.postfix);
		id.symbol = s;
		id.type = s.getType();
		Debug("[SymbolCreator]  symbol name = "+ id.getName() + " talbenum= " + stm.getcurrentTableNumber()
				+ " type = " + s.getType() + " postfix " + s.getPostfix());

		return id.type;
	}
}
