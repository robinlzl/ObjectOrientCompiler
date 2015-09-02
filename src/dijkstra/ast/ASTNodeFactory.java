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

import org.antlr.v4.runtime.Token;
import org.objectweb.asm.Label;

import dijkstra.gen.JVMInfo;
import dijkstra.lexparse.DijkstraParser;
import dijkstra.symbol.Symbol;
import dijkstra.utility.DijkstraType;
import static dijkstra.lexparse.DijkstraParser.FALSE;
import static dijkstra.lexparse.DijkstraParser.FLOATCONSTANT;
import static dijkstra.lexparse.DijkstraParser.INTEGER;
import static dijkstra.lexparse.DijkstraParser.TRUE;
import static dijkstra.utility.DijkstraType.*;
import static dijkstra.ast.ASTNode.ASTNodeType.*;

import java.util.List;
import java.util.ArrayList;
/**
 * Node factory for all of the nodes that are in the AST. The specific nodes are defined
 * here. Each one has one or more factory methods.
 * @version Feb 10, 2015
 */
public class ASTNodeFactory
{
	//--------------------- ProgramNode -----------------------
	/**
	 * program -> ProgramNode(ASTNode+, programName)
	 */
	public static class ProgramNode extends ASTNode
	{
		public final String programName;
		
		public ProgramNode(String programName) 
		{ 
			super(); 
			nodeType = PROGRAM;
			this.programName = programName; 
		}
		
		@Override
		protected String extraInfo() { return ", name=" + programName; }
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
		/**
		 * Factory method for the ProgramNode 
		 * @param programName
		 * @return the new node with no parent.
		 */
		public static ProgramNode makeProgramNode(String programName)
		{
			return new ProgramNode(programName);
		}
	}
	
	//--------------------- ProgramNode -----------------------
	/**
	 * program -> ProgramNode(ASTNode+, programName)
	 */
	public static class DijkstraTextNode extends ASTNode
	{
		public DijkstraTextNode() 
		{ 
			super(); 
			nodeType = DIJKSTRA;
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }

		public String getProgramName() 
		{
//			//if there is a program;
//			if(node.getChild(node.getChildCount() - 2).nodeType == PROGRAM)
//			{
//				return ((ProgramNode)node.getChild(getChildCount())).programName;
//			}
//			else
//			{
//				return null;
//			}
//			 
			return ((ProgramNode)this.getChild(0)).programName;
		} 
		
		public ClassdeClarationNode getClassDeclaration(int i) { return (ClassdeClarationNode) this.getChild(i + 1); }

		public static DijkstraTextNode makeDijkstraTextNode()
		{
			return new DijkstraTextNode();
		}
	}
	
	//--------------------- IDNode -----------------------
	/**
	 * ID -> IDNode(token symbol)
	 */
	public static class IDNode extends ASTNode
	{
		public Symbol symbol;
		public String postfix;
		public int talbleNumber;
		public IDNode(Token t)
		{
			super();
			nodeType = ID;
			token = t;
			postfix = "NORMAL";
			symbol = null;
		}
		
		public IDNode(Token t, String funcpostfix)
		{
			super();
			nodeType = ID;
			token = t;
			postfix = funcpostfix;
			symbol = null;
		}
		
		public DijkstraType getType() { return symbol != null ? symbol.getType() : this.type; }
		
		@Override
		public void setType(DijkstraType dt)
		{
			this.type = dt;
			if (symbol != null) { symbol.setType(dt); }
		}
		
		@Override
		public String extraInfo()
		{
			return symbol == null ? "" : ", symbol={" + symbol.toString() + "}";
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public int getAddress()
		{
			int address = symbol.getAddress();
			if (address == Symbol.NO_ADDRESS) {
				symbol.setAddress(JVMInfo.getNextAddress());
			}
			return symbol.getAddress();
		}
		
		public int getAddress(int address)
		{
			int currentaddress = symbol.getAddress();
			if (currentaddress == Symbol.NO_ADDRESS) {
				symbol.setAddress(address);
			}
			return symbol.getAddress();
		}
		public int getAddressForFunction()
		{
			int address = symbol.getAddress();
			
			if (address == Symbol.NO_ADDRESS) {
				symbol.setAddress(JVMInfo.getNextAddress());
			}
			return symbol.getAddress();
		}
		
		public int getFunAddress()
		{
			int funaddress = symbol.getFunAddress();
			if (funaddress == Symbol.NO_ADDRESS) {
				symbol.setAddress(JVMInfo.getNextFunAddress());
			}
			return symbol.getFunAddress();
		}
		
//		public String getName() { return token.getText(); }
//		public void changeName(String changePostfix) { symbol. 
		public String getName() { return token.getText() + postfix; }
//		public String getName(String certainPostfix) { return token.getText() + certainPostfix; }
		
		public static IDNode makeIDNode(Token t) { return new IDNode(t); }
		
		public static IDNode makeIDNode(Token t, String funcPostfix) 
		{ 
			return new IDNode(t, funcPostfix); 
		}
	}
	
	//--------------------- ConstantNode -----------------------
	/**
	 * CONSTANT -> ConstantNode(token)
	 * Constants are TRUE, FALSE, INTEGER tokens
	 */
	public static class ConstantNode extends ASTNode
	{
		public ConstantNode(Token t, int constanttype)
		{
			super();
			nodeType = CONSTANT;
			token = t;
			switch(constanttype)
			{
				case INTEGER:
					type = INT;
					break;
				case FLOATCONSTANT:
					type = FLOAT;
					break;
				case TRUE:
				case FALSE:
					type = BOOLEAN;
					break;
				default:
					break;
			}
			
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static ConstantNode makeConstantNode(Token t, int constanttype) 
		{ 
			return new ConstantNode(t, constanttype); 
		}
	}
	
	//--------------------- DeclarationNode -----------------------
	/**
	 * declaration -> DeclarationNode(IDNode, type)
	 */
	public static class DeclarationNode extends ASTNode
	{
		public DeclarationNode(IDNode id, DijkstraType type)
		{
			super();
			nodeType = DECL;
			this.type = type;
			addChild(id);	// child[0] is the ID
		}
		
		public IDNode getId() { return (IDNode)getChild(0); }
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static DeclarationNode makeDeclarationNode(IDNode id)
		{
			return new DeclarationNode(id, DijkstraType.UNDEFINED);
		}
		
		public static DeclarationNode makeDeclarationNode(IDNode id, DijkstraType type)
		{
			return new DeclarationNode(id, type);
		}
	}

	
	//--------------------- returnstatementnode -----------------------
	/**
	 * declaration -> DeclarationNode(IDNode, type)
	 */
	public static class ReturnStatementlistNode extends ASTNode
	{
		public ReturnStatementlistNode()
		{
			super();
			nodeType = RETURNLIST;
//			this.token = t;
		}
		
		public IDNode getId() { return (IDNode)getChild(0); }
		public ExprListNode getexpression(int i){ return (ExprListNode)getChild(i);}
		public Token gettoken() { return this.token;}
		public ASTNodeType getType(){ return this.nodeType;};
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static ReturnStatementlistNode makeReturnStatementlistNode()
		{
			return new ReturnStatementlistNode();
		}
	}
	
	//--------------------- returnstatementnode -----------------------
		/**
		 * declaration -> DeclarationNode(IDNode, type)
		 */
		public static class ReturnStatementNode extends ASTNode
		{
			public ReturnStatementNode(Token t)
			{
				super();
				nodeType = RETURN;
				this.token = t;
			}
			
			public IDNode getId() { return (IDNode)getChild(0); }
			public ExprListNode getexpression(int i){ return (ExprListNode)getChild(i);}
			public Token gettoken() { return this.token;}
			
			@Override
			public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
			
			public static ReturnStatementNode makeReturnStatementNode(Token t)
			{
				return new ReturnStatementNode(t);
			}
		}	
	
	//--------------------- ClassdeClarationNode -----------------------
	/**
	 * declaration -> ClassdeClarationNode(IDNode, type)
	 */
	public static class ClassdeClarationNode extends ASTNode
	{
		public Symbol symbol;
		public final String className;
		public final DijkstraType returntype = DijkstraType.OBJECT;
		public ClassdeClarationNode(String classname)
		{
			super();
			nodeType = CLASSDECL;
			type = DijkstraType.CLASS;
			this.className = classname;
		}
		
		public IDNode getId() { return (IDNode)getChild(0); }
//		public ASTNode getclassbody(int i) { return getChild(i); }	
//		public ASTNode getclassbody() { return getChild(this.getChildCount()-1); }
		public PropertyListNode getProperty() {return (PropertyListNode)getChild(this.getChildCount()-1);};
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static ClassdeClarationNode makeClassdeClarationNode(String classname)
		{
			return new ClassdeClarationNode(classname);
		}
	}	
	
	//--------------------- ClassdeClarationNode -----------------------
	/**
	 * declaration -> ClassdeClarationNode(IDNode, type)
	 */
	public static class PropertyListNode extends ASTNode
	{
		public ArrayList<DijkstraType> propertyTypeList = new ArrayList<DijkstraType>();
		public ArrayList<Integer> accessSpecList = new ArrayList<Integer>();// 0 = R, 1 = W, 2 = R/W
		
		public PropertyListNode()
		{
			super();
			nodeType = PROPERTYLIST;
			this.type = UNDEFINED;
		}
		
		public IDNode getId() { return (IDNode)getChild(0); }
		public ArrayList<DijkstraType> getpropertyTypeList() { return propertyTypeList;}
		public PropertyNode  getProperty(int i) { return (PropertyNode)this.getChild(i); };
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static PropertyListNode makePropertyNode()
		{
			return new PropertyListNode();
		}
	}	
	
	//--------------------- ClassdeClarationNode -----------------------
	/**
	 * declaration -> ClassdeClarationNode(IDNode, type)
	 */
	public static class PropertyNode extends ASTNode
	{
		public DijkstraType propertyType;
		public Integer accessSpec;// 0 = R, 1 = W, 2 = R/W
		
		public PropertyNode(DijkstraType type, int accessSpec)
		{
			super();
			nodeType = PROPERTY;
			this.type = type;
			this.accessSpec = accessSpec;
		}
		
		public IDNode getId() { return (IDNode)getChild(0); }
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static PropertyNode makePropertyNode(DijkstraType type, int accessSpec)
		{
			return new PropertyNode(type, accessSpec);
		}
	}	
	//--------------------- Procedurecallnode -----------------------
	/**
	 * declaration -> DeclarationNode(IDNode, type)
	 */
	public static class ProcedurecallNode extends ASTNode
	{
		public final String procedureName;
		public ProcedurecallNode(IDNode procedureid)
		{
			super();
			nodeType = PROCEDURECALL;
			this.type = DijkstraType.FUNC;
			this.procedureName = procedureid.getName();
			addChild(procedureid);
		}
		
		public IDNode getId() { return (IDNode)getChild(0); }
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		public IDNode getID() { return (IDNode) getChild(0); }
		
		public ExprListNode getExprListNode() 
		{ 
			if(this.getChildCount() > 1) return (ExprListNode)getChild(1);
			else return null;
		}
		
		public static ProcedurecallNode makeProcedurecallNode(IDNode procedureid)
		{
			return new ProcedurecallNode(procedureid);
		}
	}	
    //--------------------- variableDeclarationNode -----------------------
	/**
	 * declaration -> variableDeclarationNode(IDNode, type)
	 */
	public static class variableDeclarationNode extends ASTNode
	{
		public final String className;
		
		public variableDeclarationNode(IDNode id, DijkstraType type)
		{
			super();
			nodeType = VARIABLEDECL;
			this.className = id.getName();
			this.type = type;
			addChild(id);
		}
		
		public variableDeclarationNode(DijkstraType type)
		{
			super();
			nodeType = VARIABLEDECL;
			this.className = null;
			this.type = type;
		}
		
		public variableDeclarationNode(String classname, DijkstraType type)
		{
			super();
			nodeType = VARIABLEDECL;
			this.className = classname;
			this.type = type;
		}
		public IDNode getId(int i) { return (IDNode)getChild(i); }
		public IDNode getclassId() { return (IDNode)getChild(0); }	//the first IDNode is the class name
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static variableDeclarationNode makevariableDeclarationNode(IDNode id)
		{
			return new variableDeclarationNode(id, DijkstraType.UNDEFINED);
		}
		
		public static variableDeclarationNode makevariableDeclarationNode(IDNode id, DijkstraType type)
		{
			return new variableDeclarationNode(id, type);
		}
		public static variableDeclarationNode makevariableDeclarationNode(DijkstraType type)
		{
			return new variableDeclarationNode(type);
		}
		public static variableDeclarationNode makevariableDeclarationNode(String classname, DijkstraType type)
		{
			return new variableDeclarationNode(classname, type);
		}
	}

	
	
	//--------------------- ArrayDeclarationNode -----------------------
	/**
	 * declaration -> ArrayDeclarationNode(IDNode, type)
	 */
	public static class ArrayDeclarationNode extends ASTNode
	{
		public ArrayDeclarationNode(DijkstraType type)
		{
			super();
			nodeType = ARRARYDECL;
			this.type = type;
		}
		public ASTNode getExpression() { return getChild(0); }
		public IDNode getID(int i) { return (IDNode)getChild(i); }
		public DijkstraType getType() { return this.type; }		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static ArrayDeclarationNode makeArrayDeclarationNode(DijkstraType type)
		{
			return new ArrayDeclarationNode(type);
		}

	}
	
	
	
	//--------------------- ProcedureDeclarationNode -----------------------
	/**
	 * declaration -> ProcedureDeclarationNode(IDNode, type)
	 */
	public static class ProcedureDeclarationNode extends ASTNode
	{
		public final String programName;
		public boolean ParameterlistFlag;
		public ArrayList<DijkstraType> paratypeList = new ArrayList<DijkstraType>();

		public ProcedureDeclarationNode(IDNode procedureid)
		{
			super();
			nodeType = PROCEDUREDECL;
			this.programName = procedureid.getName();
			this.type = DijkstraType.FUNC;
			addChild(procedureid);
		}
		
		public IDNode getID() { return (IDNode)getChild(0); }
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public CompoundNode getcompoundbody() { return (CompoundNode) getChild(1); }

		public boolean setParameterlistFlag(boolean flag) {  return this.ParameterlistFlag = flag;}
		
		public boolean getParameterlistFlag() {  return this.ParameterlistFlag; }
	
		public ParameterlistNode getParameterlist() { return (ParameterlistNode) getChild(2);}

		public static ProcedureDeclarationNode makeProcedureDeclarationNode(IDNode procedureid)
		{
			return new ProcedureDeclarationNode(procedureid);
		}
	}
	
	
	//--------------------- FunctionDeclarationNode -----------------------
	/**
	 * declaration -> FunctionDeclarationNode(IDNode, type)
	 */
	public static class FunctionDeclarationNode extends ASTNode
	{
		public Symbol symbol;
		public final String functionName;
		public ArrayList<DijkstraType> returntypeList = new ArrayList<DijkstraType>();
		public boolean ParameterlistFlag = false;
		public ArrayList<DijkstraType> paratypeList = new ArrayList<DijkstraType>();
		public int address;
		
		int returnchild;
		public FunctionDeclarationNode(IDNode id)
		{
			super();
			nodeType = FUNCTIONDECL;
			this.functionName = id.getName();
			this.type = DijkstraType.FUNC;
			this.address = setFunAddress();
			funEntryLabelList.add(this.address);
			addChild(id);
		}
		
//		public IDNode getId() { return (IDNode)getChild(0); }
		public void settypelist(int index, DijkstraType type) 
		{ 
			this.returntypeList.add(index, type);
		}
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public IDNode getID() { return (IDNode) getChild(0); }
		
		public ParameterlistNode getParameterlist() { return (ParameterlistNode) getChild(2);}
		
		public CompoundNode getcompoundbody() { return (CompoundNode) getChild(1); }

		public ReturnStatementNode getreturnstatement() { return (ReturnStatementNode) getChild(this.returnchild);}
		
		public ReturnStatementlistNode getreturnstatementlist(int i) 
		{ 
			return (ReturnStatementlistNode) this.getcompoundbody().getChild(i);
		}

		
		public void setReturnStatement(int i) { returnchild = i; } 
		
		public boolean setParameterlistFlag(boolean flag) {  return this.ParameterlistFlag = flag;}
		
		public boolean getParameterlistFlag() {  return this.ParameterlistFlag; }
		
		public ArrayList<DijkstraType> getTypelist() 
		{
			return returntypeList;				
		}
		
		public ArrayList<DijkstraType> getParaTypelist() 
		{
			return paratypeList;				
		}
		
		
		public DijkstraType getType() { return this.returntypeList.get(0); }
		
				
		public static FunctionDeclarationNode makeFunctionDeclarationNode(IDNode id)
		{
			return new FunctionDeclarationNode(id);
		}
	}	
	//--------------------- AssignNode -----------------------
	/**
	 * assignStatement -> AssignNode(IDNode Expression, type)
	 */
	public static class AssignNode extends ASTNode
	{

		public AssignNode() { super(); }
		public AssignNode(IDNode id, ASTNode expression)
		{
			super();
			nodeType = ASSIGN;
			addChild(id);			// identifier
			addChild(expression);	// expression or terminal
		}
		
		public AssignNode(ASTNode varlist, ASTNode exprlist)
		{
			super();
			nodeType = ASSIGN;

			addChild(varlist);			// identifier
			addChild(exprlist);	// expression or terminal
		}
		
		public VarlistNode getVarlist() { return (VarlistNode) getChild(0); }
		public ExprListNode getExpressionlist() { return (ExprListNode)getChild(1); }
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static AssignNode makeAssignNode() { return new AssignNode(); }
		public static AssignNode makeAssignNode(ASTNode varlist, ASTNode exprlist) { return new AssignNode(varlist, exprlist); }
		public static AssignNode makeAssignNode(IDNode id, ASTNode expression)
		{
			return new AssignNode(id, expression);
		}
	}
	
	//--------------------- InputNode -----------------------
	/**
	 * inputStatement -> InputNode(IDNode type)
	 */
	public static class InputNode extends ASTNode
	{
		public InputNode(IDNode id)
		{
			super();
			nodeType = INPUT;
			addChild(id);
		}
		public InputNode()
		{
			super();
			nodeType = INPUT;
			this.type = DijkstraType.UNDEFINED;
		}
		
		public IDNode getID(int i) { return (IDNode) getChild(i); }
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static InputNode makeInputNode(IDNode id) { return new InputNode(id); }
		public static InputNode makeInputNode() { return new InputNode(); }
	}
	
	//--------------------- OutputNode -----------------------
	/**
	 * outputStatement -> OutputNode(ASTNode)
	 */
	public static class OutputNode extends ASTNode
	{
		public OutputNode(ASTNode expr)
		{
			super();
			nodeType = OUTPUT;
			type = UNDEFINED;
			addChild(expr);
		}
		
		public ASTNode getExpression() { return getChild(0); }
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static OutputNode makeOutputNode(ASTNode expr) { return new OutputNode(expr); }
	}
	
	//--------------------- IterativeNode -----------------------
	/**
	 * iterativeStatement -> IterativeNode(ASTNode ASTNode)
	 * Description
	 * @version Feb 12, 2015
	 */
	public static class IterativeNode extends ASTNode
	{
		public IterativeNode()
		{
			super();
			nodeType = ITERATIVE;
		}
		
		public ASTNode getExpression() { return getChild(0); }
		public ASTNode getStatement() { return getChild(1); }
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static IterativeNode makeIterativeNode()
		{
			return new IterativeNode();
		}
	}
	
	//--------------------- AlternativeNode -----------------------
	/**
	 * alternativeStatement -> AlternativeNode(GuardNode+)
	 */
	public static class AlternativeNode extends ASTNode
	{
		int lineNumber;
		
		public AlternativeNode() 
		{ 
			super(); 
			nodeType = ALTERNATIVE;
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public int getLineNumber() { return lineNumber; }
		
		public static AlternativeNode makeAlternativeNode(int lineNumber) 
		{ 
			AlternativeNode node = new AlternativeNode();
			node.lineNumber = lineNumber;
			return node;
		}
	}
	
	//--------------------- CompoundNode -----------------------
	/**
	 * program -> CompoundNode(ASTNode+)
	 */
	public static class CompoundNode extends ASTNode
	{
		public int returnnumber;
		public int symbolTabolNumber;
		public int functionAddressOffset;
		public CompoundNode() 
		{ 
			super(); 
			nodeType = COMPOUND;
			
		}
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		public void setreturnnumber(int num) { this.returnnumber = this.returnnumber + num;}
		public ASTNode getDeclaration() { return getChild(0);}
		public ASTNode  getStatement() { return getChild(1); }
		public static CompoundNode makeCompoundNode() { return new CompoundNode(); }
	}
	
	
	
	//--------------------- GuardNode -----------------------
	/**
	 * guard -> GuardNode(ASTNode ASTNode)
	 */
	public static class GuardNode extends ASTNode
	{
		public GuardNode(ASTNode expr, ASTNode stmt)
		{
			super();
			nodeType = GUARD;
			addChild(expr);
			addChild(stmt);
		}
		
		public ASTNode getExpression() { return getChild(0); }
		public ASTNode getStatement() { return getChild(1); }
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static GuardNode makeGuardNode(ASTNode expr, ASTNode stmt) { return new GuardNode(expr, stmt); }
	}

	
	
	
	//--------------------- ParameterlistNode -----------------------
	/**
	 * Varlist -> VarlistNode(ASTNode ASTNode)
	 */	
	public static class ParameterlistNode extends ASTNode
	{
		public ParameterlistNode()
		{
			super();
			nodeType = PARALIST;
		}
		
		public ASTNode getExpression() { return getChild(0); }
		public ASTNode getStatement() { return getChild(1); }
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static ParameterlistNode makeParameterlistNode() 
		{
			return new ParameterlistNode(); 
		}
	}
	
	//--------------------- ParameterlistNode -----------------------
	/**
	 * Varlist -> VarlistNode(ASTNode ASTNode)
	 */	
	public static class ParameterNode extends ASTNode
	{
		public ParameterNode(IDNode id)
		{
			super();
			nodeType = PARAM;
			this.type = UNDEFINED;
			addChild(id);
		}
		
		public ParameterNode(IDNode id, DijkstraType type)
		{
			super();
			nodeType = PARAM;			
			this.type = type;
			addChild(id);
		}

		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public IDNode getID() { return (IDNode) getChild(0); }
		
		public static ParameterNode makeParameterNode(IDNode id) 
		{
			return new ParameterNode(id); 
		}
		
		public static ParameterNode makeParameterNode(IDNode id, DijkstraType type)
		{
			return new ParameterNode(id, type); 
		}
		
	}
	//--------------------- VarlistNode -----------------------
	/**
	 * Varlist -> VarlistNode(ASTNode ASTNode)
	 */	
	public static class VarlistNode extends ASTNode
	{
		public VarlistNode()
		{
			super();
			nodeType = VARLIST;
		}
		
		public IDNode getId(int i) 
		{ 
			ASTNode node = getChild(i);
			IDNode idnode;
			if (node.nodeType == ARRARYACCESSOR)
			{
				ArrayaccessorNode accessornode = (ArrayaccessorNode) node;
				idnode = accessornode.getId();
			}
			else if(node.nodeType == CLASSATTIACCESSOR)
			{
				ClassAttributeAccessorNode classattraccessor = (ClassAttributeAccessorNode) node;
				idnode = classattraccessor.getattributeName();
			}
			else
			{
				idnode = (IDNode)node;
			}
			return idnode;
		} 
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static VarlistNode makeVarlistNode() 
		{
			return new VarlistNode(); 
		}
	}

	//--------------------- ExprListNode -----------------------
	/**
	 * ExprList -> ExprListNode( )
	 */	
	public static class ExprListNode extends ASTNode
	{
		public ExprListNode()
		{
			super();
			nodeType = EXPRLIST;

		}
		
//		public ASTNode getExpression() { return getChild(0); }
//		public ASTNode getStatement() { return getChild(1); }
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static ExprListNode makeExprListNode() 
		{
			return new ExprListNode();
		}
	}
	
	//--------------------- UnaryExpressionNode -----------------------
	/**
	 * op expression -> UnaryExpressionNode(ASTNode, op)
	 */
	public static class UnaryExpressionNode extends ASTNode
	{
		public UnaryExpressionNode(Token op, ASTNode expr)
		{
			super();
			nodeType = UNARYEXPR;
			token = op;
			type = op.getType() == DijkstraParser.TILDE ? BOOLEAN : NUMERIC;
			addChild(expr);
		}
		
		public int getOp() { return token.getType(); }
		public ASTNode getExpression() { return getChild(0); }
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static UnaryExpressionNode makeUnaryExpressionNode(Token op, ASTNode expr)
		{
			return new UnaryExpressionNode(op, expr);
		}
	}
	
	//--------------------- BinaryExpressionNode -----------------------
	/**
	 * expression op expression -> BinaryExpressionNode(ASTNode ASTNode, op)
	 */
	public static class BinaryExpressionNode extends ASTNode
	{
		public BinaryExpressionNode(Token op, ASTNode expr1, ASTNode expr2)
		{
			super();
			nodeType = BINARYEXPR;
			token = op;
			switch (op.getType()) {
				case DijkstraParser.STAR:
				case DijkstraParser.PLUS:
				case DijkstraParser.MINUS:
				case DijkstraParser.SLASH:
				case DijkstraParser.MOD:
				case DijkstraParser.DIV:
//					type = INT;
					type = NUMERIC;
					break;
				case DijkstraParser.LT:
				case DijkstraParser.GT:
				case DijkstraParser.EQ:
				case DijkstraParser.NEQ:
				case DijkstraParser.LTE:
				case DijkstraParser.GTE:
				case DijkstraParser.OR:
				case DijkstraParser.AND:
					type = BOOLEAN;
			}
			addChild(expr1);
			addChild(expr2);
		}
		
		public ASTNode getExpr1() { return getChild(0); }
		public ASTNode getExpr2() { return getChild(1); }
		public int getOp() { return token.getType(); }
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static BinaryExpressionNode makeBinaryExpressionNode(
				Token op, ASTNode expr1, ASTNode expr2)
		{
			return new BinaryExpressionNode(op, expr1, expr2);
		}
	}

	
	//--------------------- FuntioncallNode -----------------------
	/**
	 * Terminal ( expressionlist ) -> FuntioncallNode()
	 */
	public static class FuntioncallNode extends ASTNode
	{
		public final String funcName;
		public Symbol symbol;
//		public ArrayList<DijkstraType> paratypeList = new ArrayList<DijkstraType>();

		public FuntioncallNode(IDNode id, ASTNode exprlist)
		{
			super();
			nodeType = FUNCCALL;
            this.funcName = id.getName();
            this.type = FUNC;
            addChild(id);
			addChild(exprlist);
		}
		
		public FuntioncallNode(IDNode id)
		{
			super();
			nodeType = FUNCCALL;
            this.funcName = id.getName();
            this.type = FUNC;
            addChild(id);
		}
		
		public IDNode getId() { return (IDNode)getChild(0); }
	
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public ExprListNode getExprListNode() 
		{ 
			if(this.getChildCount() > 1) return (ExprListNode)getChild(1);
			else return null;
		}
		
		public static FuntioncallNode makeFuntioncallNode(
				IDNode id, ASTNode exprlist)
		{
			return new FuntioncallNode(id, exprlist);
		}
		
		public int getAddress()
		{
			int address = symbol.getAddress();
			if (address == Symbol.NO_ADDRESS) {
				symbol.setAddress(JVMInfo.getNextFunAddress());
			}
			return symbol.getAddress();
		}
		
		public static FuntioncallNode makeFuntioncallNode(
				IDNode id)
		{
			return new FuntioncallNode(id);
		}
	}	
	
	
	//--------------------- ConstructorNode -----------------------
	/**
	 * ID ( expressionlist ) -> ConstructorNode()
	 */
	public static class ConstructorNode extends ASTNode
	{
		public final String constructorName;
		
		public ConstructorNode(IDNode id, ASTNode exprlist)
		{
			super();
			nodeType = CONSTRUCTORCALL;
            this.constructorName = id.getName();
            addChild(id);
			addChild(exprlist);
		}
		
//		public ASTNode getExpr1() { return getChild(0); }
//		public ASTNode getExpr2() { return getChild(1); }
//		public int getOp() { return token.getType(); }
		public IDNode getID() { return (IDNode) getChild(0); }
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public CompoundNode getcompoundbody() { return (CompoundNode) getChild(1); }
		
		public static ConstructorNode makeConstructorNode(
				IDNode id, ASTNode exprlist)
		{
			return new ConstructorNode(id, exprlist);
		}
	}	
	
	
	//--------------------- MethodcallNode -----------------------
	/**
	 * ID DOT ID ( expressionlist ) -> MethodcallNode()
	 */
	public static class MethodcallNode extends ASTNode
	{
		public final String methodcallName;
		public final String classname;
		
		public MethodcallNode(IDNode methodcallid, IDNode classid)
		{
			super();
			nodeType = METHODCALL;
			this.type = FUNC;
            this.methodcallName = methodcallid.getName();
            this.classname = classid.getName();
            addChild(methodcallid);
			addChild(classid);
		}
		
//		public ASTNode getExpr1() { return getChild(0); }
//		public ASTNode getExpr2() { return getChild(1); }
//		public int getOp() { return token.getType(); }
		
		public ExprListNode getExprListNode() 
		{ 
			if(this.getChildCount() > 2) return (ExprListNode)getChild(2);
			else return null;
		}
		
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public IDNode getID() { return (IDNode) getChild(0); }
		
		public IDNode getObjectIdNode() { return (IDNode) getChild(1); }

		public IDNode getMethodIdNode() { return (IDNode) getChild(0); }

		public static MethodcallNode makeMethodcallNode(
				IDNode methodcallid, IDNode classid)
		{
			return new MethodcallNode(methodcallid, classid);
		}
	}	
		
	//--------------------- VarNode -----------------------
	/**
	 * Var -> VarNode( )
	 */	
	public static class VarNode extends ASTNode
	{
		public VarNode(IDNode classnameidnode, IDNode attributenameidnode)
		{
			super();
			nodeType = VAR;
			type = DijkstraType.CLASS;
			addChild(classnameidnode);
			addChild(attributenameidnode);
		}
		
		public VarNode(IDNode idnode)
		{
			super();
			nodeType = VAR;
			this.type = idnode.type;
			addChild(idnode); 
		}
		
		public VarNode(ArrayaccessorNode arrayaccessornode)
		{
			super();
			nodeType = VAR;
			this.type = arrayaccessornode.type;
			addChild(arrayaccessornode); 
		}
//		public ASTNode getExpression() { return getChild(0); }
		public ASTNode getStatement() { return getChild(1); }
		public ASTNode getID() { return getChild(0); }	
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		public static VarNode makeVarNode(IDNode classnameidnode, IDNode attributenameidnode) 
		{
			
			return new VarNode(classnameidnode, attributenameidnode);
		}
		public static VarNode makeVarNode(IDNode idnode) 
		{
			
			return new VarNode(idnode);
		}
		public static VarNode makeVarNode(ArrayaccessorNode arrayaccessornode) 
		{
			
			return new VarNode(arrayaccessornode);
		}
	}

	
	//--------------------- ClassAttributeAccessorNode -----------------------
	/**
	 * ClassAttributeAccessorNode -> ClassAttributeAccessorNode( )
	 */	
	public static class ClassAttributeAccessorNode extends ASTNode
	{
		public Symbol symbol; 
		
		public ClassAttributeAccessorNode(IDNode attributeName, IDNode className)
		{
			super();
			nodeType = CLASSATTIACCESSOR;
//			this.type = OBJECT;
			addChild(attributeName);			// identifier
			addChild(className);	// expression or terminal
		}
		
		public IDNode getattributeName() { return (IDNode)getChild(1); }
		public IDNode getObjectName() { return (IDNode)getChild(0); }
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		
		@Override
		public void setType(DijkstraType dt)
		{
			type = dt;
			if (symbol != null) { symbol.setType(dt); }
		}

		public static ClassAttributeAccessorNode makeClassAttributeAccessorNode(IDNode className , IDNode attributeName) 
		{
			
			return new ClassAttributeAccessorNode(className , attributeName);
		}

	}
	//--------------------- VarNode -----------------------
	/**
	 * Var -> VarNode( )
	 */	
	public static class ArrayaccessorNode extends ASTNode
	{
		public Symbol symbol;
		
		public ArrayaccessorNode(IDNode id, ASTNode expr)
		{
			super();
			nodeType = ARRARYACCESSOR;
			addChild(id);			// identifier
			addChild(expr);	// expression or terminal
		}
		public IDNode getId() { return (IDNode)getChild(0); }
		
		public ASTNode getExpression() { return getChild(1); }
		
//		public ASTNode getStatement() { return getChild(1); }
		
		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
		public String getName() { return token.getText(); }
		@Override
		public void setType(DijkstraType dt)
		{
			type = dt;
			if (symbol != null) { symbol.setType(dt); }
		}

		public static ArrayaccessorNode makeArrayaccessorNode(IDNode id, ASTNode expr) 
		{
			
			return new ArrayaccessorNode(id, expr);
		}

	}

}
