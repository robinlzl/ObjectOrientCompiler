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

package dijkstra.gen;

import java.util.List;
import java.util.Stack;

import org.objectweb.asm.*;

import java.util.ArrayList;

import dijkstra.ast.*;
import dijkstra.ast.ASTNode.ASTNodeType;
import dijkstra.ast.ASTNodeFactory.*;
import dijkstra.lexparse.DijkstraParser;
import dijkstra.utility.*;
import dijkstra.symbol.*;
import dijkstra.symbol.Symbol.symbolTypeEnum;
import static dijkstra.utility.DijkstraType.*;
import static org.objectweb.asm.Opcodes.*;

/**
 * Description
 * @version Feb 21, 2015
 */
public class DijkstraCodeGenerator extends ASTVisitor<byte[]>
{
	private final boolean debug = true;
	private ClassWriter cw = null;
	private MethodVisitor mv = null;
	private FieldVisitor fv=null;
	private ClassWriter newcwfortest = null;
	private ArrayList<ClassWriter> cwlist = new ArrayList<ClassWriter>();
	private final String DEFAULT_PACKAGE = "djkcode";
	private String classPackage;
	private boolean needValue;		// used to indicate whether we need an ID value or address
	final private Stack<Label> guardLabelStack;
	final private Stack<Label> returnLabelStack;
	final private Stack<Label> funEntryLabelStack;
	private Stack<ClassWriter> subClassWiterStack;
	final private ArrayList<Label> funEntryLabelList;
	final private ArrayList<Label> funReturnLabellList;
	public String programName;
	public ArrayList<String> classNameList; 
	final private Stack<inclassDeclororNmalDecl> classDeclorNormalDeclStack;
//	public ArrayList<String> globallist;
	public enum inclassDeclororNmalDecl
	{
		INCLASS, NORMALDECL;
	}
	private SymbolTableManager stm = SymbolTableManager.getInstance();
	
	public DijkstraCodeGenerator()
	{
		classPackage = DEFAULT_PACKAGE;
		guardLabelStack = new Stack<Label>();
		returnLabelStack = new Stack<Label>();
		funEntryLabelStack = new Stack<Label>();
		classDeclorNormalDeclStack = new Stack<inclassDeclororNmalDecl>();
		funEntryLabelList = new ArrayList<Label>();
		funReturnLabellList = new ArrayList<Label>();
		programName = new String();
		classNameList = new ArrayList<String>();
		subClassWiterStack = new Stack<ClassWriter>();
//		globallist = new ArrayList<String>();
		
	}

	/**
	 * Generate the program prolog, then visit the children, then generate
	 * the program end.
	 * @see dijkstra.ast.ASTVisitor#visit(dijkstra.ast.ASTNodeFactory.ProgramNode)
	 */
	public byte[] visit(ClassdeClarationNode classDeclnode) 
	{
		// Start a new class
		cwlist.add(new ClassWriter(ClassWriter.COMPUTE_FRAMES));
		ClassWriter cw = cwlist.get(0);
		FieldVisitor fv;
		MethodVisitor mv;
		String classPath = classPackage + "/" + classDeclnode.className;

		cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, classPackage + "/" + classDeclnode.className, null, "java/lang/Object", null);
		cw.visitSource(classDeclnode.className + ".class", null);
		
		String classDisc = "";
		PropertyListNode propertylistnode = classDeclnode.getProperty();
		ArrayList<DijkstraType> getpropertyTypeList = propertylistnode.getpropertyTypeList();
		Debug("constructor getpropertyTypeList : " + getpropertyTypeList);

		for(int i = 0; i < getpropertyTypeList.size(); i++)
		{
			DijkstraType type = getpropertyTypeList.get(i);
			int access = propertylistnode.accessSpecList.get(i);
			int accessValue = getaccessString(access);
			if(type == DijkstraType.INT)
			{
				classDisc = classDisc + "I";
//				fv = cw.visitField(accessValue, propertylistnode.getProperty(i).getId().token.getText(), "I", null, null);
				fv = cw.visitField(accessValue, propertylistnode.getProperty(i).getId().getName(), "I", null, null);

				fv.visitEnd();
			}
			else if(type == DijkstraType.FLOAT)
			{
				classDisc = classDisc + "F";
//				fv = cw.visitField(accessValue, propertylistnode.getProperty(i).getId().token.getText(), "F", null, null);
				fv = cw.visitField(accessValue, propertylistnode.getProperty(i).getId().getName(), "F", null, null);

				fv.visitEnd();
			}
			else if(type == DijkstraType.BOOLEAN)
			{
				classDisc = classDisc + "Z";
//				fv = cw.visitField(accessValue, propertylistnode.getProperty(i).getId().token.getText(), "Z", null, null);
				fv = cw.visitField(accessValue, propertylistnode.getProperty(i).getId().getName(), "Z", null, null);
				fv.visitEnd();
			}
		}
		
		classDisc = "(" + classDisc + ")V";
		Debug("[CodeGen] constructor classdisc : " + classDisc + "  talbenum = "+ stm.getcurrentTableNumber());
		Symbol classsymbol = stm.getSymbol(classDeclnode.getId().getName());
		classsymbol.setfuntionDisc(classDisc);
		int currentTable = stm.getcurrentTableNumber();
		stm.enterScope(classsymbol.talbleNumber);
		ArrayList<String> globlist = stm.getGlaobalList();
		Debug("[CodeGen] classdeclaration globlist = " + globlist);
		for(int i = 0; i < globlist.size(); i++)
		{
			Symbol symbol = stm.getSymbol(globlist.get(i));
			if(symbol == null)
			{
				continue;
			}
			
			symbol.setClassname(classDeclnode.className);
			
			if(symbol.symbolType == Symbol.symbolTypeEnum.NORMAL)
			{
				if(symbol.getType() == DijkstraType.FLOAT)
				{
					fv = cw.visitField(ACC_PUBLIC + ACC_STATIC, symbol.getId(), "F", null, null);
					fv.visitEnd();
					Debug("[CodeGen]symbol glaobal fv  FLOAT is created = " + symbol.getId()+ " "+ symbol.globalFlag);
				}
				else if(symbol.getType() == DijkstraType.INT)
				{
					fv = cw.visitField(ACC_PUBLIC + ACC_STATIC, symbol.getId(), "I", null, null);
					fv.visitEnd();
					Debug("[CodeGen]symbol glaobal fv INT"+ symbol.getId()+ " is created = " + symbol.globalFlag);
				}
			}
			else if(symbol.symbolType == Symbol.symbolTypeEnum.ARRAY)
			{
				if(symbol.getType() == DijkstraType.FLOAT)
				{
					fv = cw.visitField(ACC_PUBLIC + ACC_STATIC, symbol.getId(), "[F", null, null);
					fv.visitEnd();
					Debug("[CodeGen]array symbol glaobal fv  FLOAT is created = "+ symbol.getId()+ " " + symbol.globalFlag);
				}
				else if(symbol.getType() == DijkstraType.INT)
				{
					fv = cw.visitField(ACC_PUBLIC + ACC_STATIC, symbol.getId(), "[I", null, null);
					fv.visitEnd();
					Debug("[CodeGen]array symbol glaobal fv INT is created = " + symbol.globalFlag);
				}
			}
		}
		
		stm.enterScope(currentTable);
		
		mv = cw.visitMethod(ACC_PUBLIC, "<init>", classDisc, null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		
		for(int i = 0; i < getpropertyTypeList.size(); i++)
		{
			DijkstraType type = getpropertyTypeList.get(i);
			IDNode id = propertylistnode.getProperty(i).getId();
			int address = i + 1;
//			int address = i;
//			mv.visitVarInsn(ALOAD, 0);
			if(type == DijkstraType.INT)
			{
				mv.visitVarInsn(ILOAD, address);
//				mv.visitFieldInsn(PUTFIELD, classPath, id.token.getText(), "I");
				mv.visitFieldInsn(PUTSTATIC, classPath, id.getName(), "I");
			}
			else if(type == DijkstraType.FLOAT)
			{
				mv.visitVarInsn(FLOAD, address);
//				mv.visitFieldInsn(PUTFIELD, classPath, id.token.getText(), "F");
				mv.visitFieldInsn(PUTSTATIC, classPath, id.getName(), "F");
			}
			else if(type == DijkstraType.BOOLEAN)
			{
				mv.visitVarInsn(ILOAD, address);
//				mv.visitFieldInsn(PUTFIELD, classPath, id.token.getText(), "Z");
				mv.visitFieldInsn(PUTSTATIC, classPath, id.getName(), "Z");
			}
		}
		
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		
		if(classDeclnode.getChild(classDeclnode.getChildCount() - 1).nodeType == ASTNodeType.PROPERTYLIST)
		{
			classDeclnode.getChild(classDeclnode.getChildCount() - 1).accept(this);
		}

		Debug("[CodeGen] classsymbol tablenum = " + classsymbol.talbleNumber);
		int currenttablenum = stm.getcurrentTableNumber();
		stm.enterScope(classsymbol.talbleNumber);
		subClassWiterStack.push(cw);
		for(int i = 1; i < classDeclnode.getChildCount() - 1; i++)
		{
			classDeclnode.getChild(i).accept(this);
		}
		subClassWiterStack.pop();
		stm.enterScope(currenttablenum);
		
		cw.visitEnd();
		return cw.toByteArray();
	}

	private int getaccessString(int access) 
	{
		int accessString = ACC_STATIC;
//-------------Manually check the access
//		switch(access)
//		{
//		case 0:
//			accessString = ACC_FINAL + ACC_STATIC;
//			break;
//		case 1:
//			accessString = ACC_PRIVATE + ACC_STATIC;
//			break;
//		case 2:
//			accessString = ACC_PUBLIC + ACC_STATIC;
//			break;
//		default:
//			break;	
//		}
		accessString = ACC_PUBLIC + ACC_STATIC;
		
		return accessString;
	}

	public byte[] visit(ClassAttributeAccessorNode classAttributeAccessor)
	{
		IDNode objectid = classAttributeAccessor.getObjectName();
		IDNode attributeid = classAttributeAccessor.getattributeName();
		int currenttablenumber = stm.getcurrentTableNumber();
		Debug("[codegen] classAttributeAccessor currentable = " +currenttablenumber );
		stm.backtoMainymbolTable();
		Symbol objectsymbol = stm.getSymbol(objectid.getName());
		stm.enterScope(objectsymbol.talbleNumber);
		
		Symbol attrisymbol = stm.getSymbol(attributeid.getName());
		
//		mv.visitVarInsn(ALOAD, objectid.getAddress());
		String classPath = classPackage + "/" + objectsymbol.getClassname();
		if(attrisymbol.getType() == INT)
		{
//			mv.visitFieldInsn(GETSTATIC, classPath, attributeid.token.getText(), "I");
			mv.visitFieldInsn(GETSTATIC, classPath, attributeid.getName(), "I");
		}
		else if(attrisymbol.getType() == DijkstraType.FLOAT)
		{
//			mv.visitFieldInsn(GETSTATIC, classPath, attributeid.token.getText(), "F");
			mv.visitFieldInsn(GETSTATIC, classPath, attributeid.getName(), "F");

		}
		else if(attrisymbol.getType() == DijkstraType.BOOLEAN)
		{
//			mv.visitFieldInsn(GETSTATIC, classPath, attributeid.token.getText(), "I");
			mv.visitFieldInsn(GETSTATIC, classPath, attributeid.getName(), "I");

		}
		else if(attrisymbol.getType() == DijkstraType.FUNC)
		{
			Debug("[CodeGen]   class method   ----------------------------------");
		}
		
		stm.enterScope(currenttablenumber);
		
		return null;
	}
	
	
	/**
	 * Generate the program prolog, then visit the children, then generate
	 * the program end.
	 * @see dijkstra.ast.ASTVisitor#visit(dijkstra.ast.ASTNodeFactory.ProgramNode)
	 */
	public byte[] visit(ProgramNode program) 
	{
		
		// Start the main() method
		mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
		mv.visitCode();

		for(int i = 0; i < program.getChildCount(); i++)
		{
			if((program.getChild(i).nodeType == ASTNodeType.FUNCTIONDECL)
					|| program.getChild(i).nodeType == ASTNodeType.PROCEDUREDECL)
			{
				continue;
			}
			else
			{
				program.getChild(i).accept(this);
			}
		}
		
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		
		for(int i = 0; i < program.getChildCount(); i++)
		{
			if((program.getChild(i).nodeType == ASTNodeType.FUNCTIONDECL) 
					|| program.getChild(i).nodeType == ASTNodeType.PROCEDUREDECL)
			{
				program.getChild(i).accept(this);
			}
			else
			{
				continue;
			}
		}
		return null;
	}

	public byte[] visit(DeclarationNode decl)
	{
		return null;
	}
	
	public byte[] visit(CompoundNode compound)
	{
		visitChildren(compound);
		return null;
	}
	

//	public byte[] visit(FunctionDeclarationNode funcdecl)
//	{
//		int funaddress = funcdecl.getID().symbol.getFunAddress();
//		CompoundNode compoundNode = funcdecl.getcompoundbody();
//
//		mv.visitLabel(funEntryLabelList.get(funcdecl.getID().symbol.getFunAddress()));
//		if(funcdecl.getParameterlistFlag() == true)
//		{
//			ParameterlistNode paralistnode = funcdecl.getParameterlist();
//			paralistnode.accept(this);
//		}
//
//		if(funReturnLabellList.size() != 0)
////		if(returnLabelStack.isEmpty() == false)
//		{
////			returnLabelStack.push(funReturnLabellList.get(funaddress));
//			compoundNode.accept(this);
////			returnLabelStack.pop();
//		}
//		else
//		{
//			Debug("warning: funtion '" + funcdecl.getID().getName() +"' is never used.");
//		}
//		return null;
//	}
	
	public byte[] visit(FunctionDeclarationNode funcdecl)
	{
		ClassWriter funcDeclcw;
		Debug("[CodeGen] talbenum = "+ stm.getcurrentTableNumber());
		CompoundNode compoundNode = funcdecl.getcompoundbody();
		Symbol functionSymbol = stm.getSymbol(funcdecl.getID().getName());
		setFunctionDisc(functionSymbol, funcdecl.nodeType);
		Debug("[CodeGen]functiondecl name : " +funcdecl.getID().getName());
		Debug("[CodeGen]functiondecl disc: " + functionSymbol.functionDiscriptor);
		Debug("[CodeGen]fucntiondecl addr : " + functionSymbol.getAddress());
		if(subClassWiterStack.isEmpty() == false)
		{
			Debug("[CodeGen]   cw not null ");
			funcDeclcw = subClassWiterStack.peek();
		}
		else
		{
			funcDeclcw = cw;
		}
		
//		mv = funcDeclcw.visitMethod(ACC_PUBLIC + ACC_STATIC, funcdecl.getID().getName(), functionSymbol.functionDiscriptor, null, null);
		mv = funcDeclcw.visitMethod(ACC_PUBLIC + ACC_STATIC, funcdecl.getID().token.getText(), functionSymbol.functionDiscriptor, null, null);
//		mv = funcDeclcw.visitMethod(ACC_PUBLIC, funcdecl.getID().token.getText(), functionSymbol.functionDiscriptor, null, null);

		mv.visitCode();
		int currentTablNum = stm.getcurrentTableNumber();
		Debug("[CodeGen]zmy ; compoundNode.symbolTabolNumber : " + compoundNode.symbolTabolNumber);
		stm.enterScope(compoundNode.symbolTabolNumber);
		
		if(funcdecl.ParameterlistFlag == true)
		{
			funcdecl.getParameterlist().accept(this);
		}
		
		int addressOffset = stm.getCurrentSymbolTable().getIdAddressOffest();
		JVMInfo.setAddressOffset(addressOffset);
		Debug("addressOffset : " + addressOffset);

		mv.visitIntInsn(BIPUSH, funcdecl.returntypeList.size());
		mv.visitIntInsn(NEWARRAY, T_FLOAT);
		mv.visitVarInsn(ASTORE, compoundNode.functionAddressOffset);
		
		compoundNode.accept(this);
		Debug("[CodeGen]zmy: currentTablNum : " + currentTablNum);
		stm.enterScope(currentTablNum);
		
//		mv.visitInsn(ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		return null;
		
		
		
//		Debug("[CodeGen]   cw = " + subClassWiterStack.peek() );
//		mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, funcdecl.getID().getName(), functionSymbol.functionDiscriptor, null, null);
//		mv.visitCode();
//		int currentTablNum = stm.getcurrentTableNumber();
//		stm.enterScope(compoundNode.symbolTabolNumber);
//		if(funcdecl.ParameterlistFlag == true)
//		{
//			funcdecl.getParameterlist().accept(this);
//		}
//		
//		int addressOffset = stm.getCurrentSymbolTable().getIdAddressOffest();
//		JVMInfo.setAddressOffset(addressOffset);
//		Debug("addressOffset : " + addressOffset);
//		
//		mv.visitIntInsn(BIPUSH, funcdecl.returntypeList.size());
//		mv.visitIntInsn(NEWARRAY, T_FLOAT);
//		mv.visitVarInsn(ASTORE, compoundNode.functionAddressOffset);
//		
//		compoundNode.accept(this);
//		stm.enterScope(currentTablNum);
//		
////		mv.visitInsn(ARETURN);
//		mv.visitMaxs(0, 0);
//		mv.visitEnd();
//		return null;
	}
	
	public byte[] visit(ProcedureDeclarationNode proceduredecl)
	{
		CompoundNode compoundNode = proceduredecl.getcompoundbody();
		Symbol functionSymbol = stm.getSymbol(proceduredecl.getID().getName());
		setFunctionDisc(functionSymbol, proceduredecl.nodeType);
		Debug("proceduredecl name : " +proceduredecl.getID().getName());
		Debug("proceduredecl disc: " + functionSymbol.functionDiscriptor);
		Debug("proceduredecl addr : " + functionSymbol.getAddress());
		mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, proceduredecl.getID().getName(), functionSymbol.functionDiscriptor, null, null);
		mv.visitCode();
		int currentTableNum = stm.getcurrentTableNumber();
		stm.enterScope(compoundNode.symbolTabolNumber);
		if(proceduredecl.ParameterlistFlag == true)
		{
			proceduredecl.getParameterlist().accept(this);
		}
		
		int addressOffset = stm.getCurrentSymbolTable().getIdAddressOffest();
		JVMInfo.setAddressOffset(addressOffset);
		Debug("addressOffset : " + addressOffset);
		
		compoundNode.accept(this);
		stm.enterScope(currentTableNum);
		
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		return null;
	}
	
	public byte[] visit(ProcedurecallNode proccall)
	{
		int procaddress = proccall.getId().getAddress();

		ExprListNode exprlistnode = proccall.getExprListNode();
		
		Symbol procedureSymbol = stm.getSymbol(proccall.getId().getName());
		setFunctionDisc(procedureSymbol, proccall.nodeType);

		visitChildren(exprlistnode);
		procedureSymbol.functionDiscriptor.replace("F", "V");
		Debug("procedure name : " +proccall.getId().getName());
		Debug("procedure disc: " + procedureSymbol.functionDiscriptor);
		Debug("procedure addr : " + procaddress);
//		mv.visitMethodInsn(INVOKESTATIC, "djkcode/Test", proccall.getId().getName(), procedureSymbol.functionDiscriptor, false);
		mv.visitMethodInsn(INVOKESTATIC, classPackage + "/" + programName, proccall.getId().getName(), procedureSymbol.functionDiscriptor, false);
		
		return null;
	}
	
	
	public byte[] visit(ReturnStatementNode returnnode)
	{
		int retrunArrayAddress = stm.getCurrentSymbolTable().getIdAddressOffest();

		if(returnnode.getChildCount() != 0)
		{
			ExprListNode exprlistNode = (ExprListNode)returnnode.getChild(0);
		
			for(int i = 0; i < exprlistNode.getChildCount(); i++)
			{
				Debug("[CodeGen] "+ i +"  retrunArrayAddress = " + retrunArrayAddress);
				mv.visitVarInsn(ALOAD, retrunArrayAddress);
				mv.visitIntInsn(BIPUSH, i);
				exprlistNode.getChild(i).accept(this);
				if(exprlistNode.getChild(i).type == DijkstraType.FLOAT)
				{
					Debug("[CodeGen] return float "+i);
				}
				else
				{
					Debug("[CodeGen] return int change to float"+i);
					mv.visitInsn(I2F);
				}
				mv.visitInsn(FASTORE);			
			}
			mv.visitVarInsn(ALOAD, retrunArrayAddress);
		
			Debug("[CodeGen]retrunArrayAddress " +  "   "+retrunArrayAddress);
			mv.visitInsn(ARETURN);
		}
		else
		{
			mv.visitInsn(RETURN);
		}
		return null;
	}
	
	public byte[] visit(ReturnStatementlistNode returnlist)
//	public byte[] visit(ReturnStatementNode returnlist)
	{
		int retrunArrayAddress = stm.getCurrentSymbolTable().getIdAddressOffest();
		ReturnStatementNode returnnode = (ReturnStatementNode)returnlist.getChild(0);
		if(returnnode.getChildCount() != 0)
		{
			ExprListNode exprlistNode = (ExprListNode)returnnode.getChild(0);
		
			for(int i = 0; i < exprlistNode.getChildCount(); i++)
			{
				Debug("[CodeGen] "+ i +"  retrunArrayAddress = " + retrunArrayAddress);
				mv.visitVarInsn(ALOAD, retrunArrayAddress);
				mv.visitIntInsn(BIPUSH, i);
				exprlistNode.getChild(i).accept(this);
				if(exprlistNode.getChild(i).type == DijkstraType.FLOAT)
				{
					Debug("[CodeGen] return float "+i);
				}
				else
				{
					Debug("[CodeGen] return int change to float"+i);
					mv.visitInsn(I2F);
				}
				mv.visitInsn(FASTORE);			
			}
			mv.visitVarInsn(ALOAD, retrunArrayAddress);
		
			Debug("[CodeGen]retrunArrayAddress " +  "   "+retrunArrayAddress);
			mv.visitInsn(ARETURN);
		}
		else
		{
			mv.visitInsn(RETURN);
		}
		return null;
	}
	
	public byte[] visit(MethodcallNode methodcallnode)
	{
		int methodaddress = methodcallnode.getID().getAddress();
		String objectname = methodcallnode.classname;
		String methodname = methodcallnode.methodcallName;
		ExprListNode exprlistnode = methodcallnode.getExprListNode();
		
		Symbol objectSymbol = stm.getSymbol(objectname);

		int currenttablenum = stm.getcurrentTableNumber();
		
		stm.enterScope(objectSymbol.talbleNumber);
		
		Symbol methodsymbol = stm.getSymbol(methodname);
		
		setFunctionDisc(methodsymbol, methodcallnode.nodeType);
		visitChildren(exprlistnode);
		Debug("method name : " + methodcallnode.getObjectIdNode().token.getText() + "." + methodcallnode.methodcallName);
		Debug("method disc: " + methodsymbol.functionDiscriptor);
		Debug("method addr : " + methodaddress);
		Debug("method belongs to class : " + objectSymbol.getClassname());
		mv.visitMethodInsn(INVOKESTATIC, classPackage + "/" + objectSymbol.getClassname() , methodcallnode.getMethodIdNode().token.getText(), methodsymbol.functionDiscriptor, false);
		mv.visitVarInsn(ASTORE, methodaddress);
		stm.enterScope(currenttablenum);
		
		for(int n = 0; n < methodsymbol.getTypeList().size(); n++)
		{
			mv.visitVarInsn(ALOAD, methodaddress);
			mv.visitIntInsn(BIPUSH, n);
			mv.visitInsn(FALOAD);
			if(methodsymbol.getTypeList().get(n) == DijkstraType.INT)
			{
				mv.visitInsn(F2I);
			}
			else if(methodsymbol.getTypeList().get(n) == DijkstraType.BOOLEAN)
			{
				final Label debugLabel = new Label();
				mv.visitInsn(F2I);
				mv.visitLabel(debugLabel);
			}
		}
		
		return null;
	}
	
	
	
	
	public byte[] visit(FuntioncallNode funccall)
	{
		int funaddress = funccall.getId().getAddress();

		ExprListNode exprlistnode = funccall.getExprListNode();
		
		Symbol functionSymbol = stm.getSymbol(funccall.getId().getName());
		
		if(functionSymbol.getType() != DijkstraType.CLASS)
		{
			setFunctionDisc(functionSymbol, funccall.nodeType);
			visitChildren(exprlistnode);

			Debug("[CodeGen]function name : " +funccall.getId().getName());
			Debug("[CodeGen]function disc: " + functionSymbol.functionDiscriptor);
			Debug("[CodeGen]fucntion addr : " + funaddress);
			Debug("[CodeGen]function class = " + functionSymbol.getClassname());
			if(functionSymbol.getClassname() != null)
			{
				mv.visitMethodInsn(INVOKESTATIC, classPackage + "/" + functionSymbol.getClassname(), funccall.getId().token.getText(), functionSymbol.functionDiscriptor, false);
				mv.visitVarInsn(ASTORE, funaddress);
			}
			else
			{
				mv.visitMethodInsn(INVOKESTATIC, classPackage + "/" + programName, funccall.getId().token.getText(), functionSymbol.functionDiscriptor, false);
				mv.visitVarInsn(ASTORE, funaddress);
			}
			for(int n = 0; n < functionSymbol.getTypeList().size(); n++)
			{
				mv.visitVarInsn(ALOAD, funaddress);
				mv.visitIntInsn(BIPUSH, n);
				mv.visitInsn(FALOAD);
				if(functionSymbol.getTypeList().get(n) == DijkstraType.INT)
				{
					mv.visitInsn(F2I);
				}
				else if(functionSymbol.getTypeList().get(n) == DijkstraType.BOOLEAN)
				{
					final Label debugLabel = new Label();
					mv.visitInsn(F2I);
					mv.visitLabel(debugLabel);
				}
			}
		}
		else
		{
//			String classPath = classPackage + "/" + this.programName + "$" + classPackage + "/" + funccall.getId().token.getText();
			String classPath = classPackage + "/" + funccall.getId().token.getText();
			Debug("[CodeGen] table num = " + stm.getcurrentTableNumber());
			mv.visitTypeInsn(NEW, classPath);
			mv.visitInsn(DUP);
			visitChildren(exprlistnode);
			Symbol classsymbol = stm.getSymbol(funccall.getId().getName());
			mv.visitMethodInsn(INVOKESPECIAL, classPath, "<init>", classsymbol.functionDiscriptor, false);
		}
		
		return null;
	}

	private void setFunctionDisc(Symbol functionSymbol, ASTNodeType nodetype) {
		String funtionDisc = functionSymbol.functionDiscriptor;
		funtionDisc = "(";
		ArrayList<DijkstraType> paratypelist = functionSymbol.getParaTypeList();
		if(functionSymbol.getParaTypeList().isEmpty() == false)
		{
			for(int n = 0; n < paratypelist.size(); n++)
			{
				if(DijkstraType.INT == paratypelist.get(n))
				{
					funtionDisc = funtionDisc + "I";
				}
				else if( DijkstraType.FLOAT == paratypelist.get(n))
				{
					funtionDisc = funtionDisc + "F";
				}
				else if( DijkstraType.BOOLEAN == paratypelist.get(n))
				{
					funtionDisc = funtionDisc + "Z";
				}
			}
		}
		if(nodetype == ASTNodeType.FUNCCALL 
				|| nodetype == ASTNodeType.FUNCTIONDECL
				|| nodetype == ASTNodeType.METHODCALL)
		{
			funtionDisc = funtionDisc + ")[F";//return a float array
		}
		else if(nodetype == ASTNodeType.PROCEDURECALL || nodetype == ASTNodeType.PROCEDUREDECL)
		{
			funtionDisc = funtionDisc + ")V";
		}
		
		functionSymbol.setfuntionDisc(funtionDisc);
	}
	
	public byte[] visit(ParameterlistNode paralistnode)
	{
//		visitChildren(paralistnode);
//		for(int i = paralistnode.getChildCount() - 1; i >= 0; i--)
//		{
//			paralistnode.getChild(i).accept(this);
//		}
		for(int i = 0; i < paralistnode.getChildCount(); i++)
		{
			ParameterNode paranode = (ParameterNode)paralistnode.getChild(i);
//			paranode.getID().getAddress(i);
			Debug("paraAddress set : " + paranode.getID().getAddress(i) + " i = " + i);
		}
		return null;
	}
	
	public byte[] visit(ParameterNode paranode)
	{
//		visitChildren(paranode);
//		mv.visitVarInsn(ISTORE, paranode.getID().getAddress());
		return null;
	}
	
	public byte[] visit(PropertyListNode propertyListNodes)
	{
//		visitChildren(paranode);
//		mv.visitVarInsn(ISTORE, paranode.getID().getAddress());
		return null;
	}
	
	/**
	 * Generate the program prolog, then visit the children, then generate
	 * the program end.
	 * @see dijkstra.ast.ASTVisitor#visit(dijkstra.ast.ASTNodeFactory.ProgramNode)
	 */
	public byte[] visit(DijkstraTextNode dijkstratextnode) 
	{
		// prolog
		for(int i = 0; i < dijkstratextnode.getChildCount(); i++)
		{
			if(dijkstratextnode.getChild(i).nodeType == ASTNodeType.PROGRAM)
			{
				this.programName = ((ProgramNode)dijkstratextnode.getChild(i)).programName;
			}
			else if(dijkstratextnode.getChild(i).nodeType == ASTNodeType.CLASSDECL)
			{
				this.classNameList.add(((ClassdeClarationNode)dijkstratextnode.getChild(i)).className);
			}
			
		}
		Debug("[CodeGen] program name = " + this.programName + " ClassName = " + this.classNameList);

		cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES); 
		ArrayList<String> globlist = stm.getGlaobalList();
		Debug("[CodeGen] globallist = "+ globlist + " another way =  " + stm.getSymbolTable(0).getSymbols());

		if(this.classNameList.isEmpty() == false || globlist.isEmpty() == false)
		{
			cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, classPackage + "/" + this.programName, null, 
					"java/lang/Object", null);
//			cw.visitSource(classPackage + "/" + programName + ".java", null);
			cw.visitSource(programName + ".class", null);
//			for(int i = 0; i < this.classNameList.size(); i++)
//			{
////				cw.visitInnerClass(this.programName + "$" + this.classNameList.get(i), this.programName, this.classNameList.get(i), ACC_PUBLIC + ACC_STATIC);
//				cw.visitInnerClass(classPackage + "/" + this.programName + "$" + this.classNameList.get(i), classPackage + "/" + this.programName, this.classNameList.get(i), ACC_PUBLIC + ACC_STATIC);
//
//			}
		}
		else
		{
			cw.visit(V1_8, ACC_PUBLIC + ACC_STATIC, classPackage + "/" + programName, null, 
					"java/lang/Object", null);
		}
		
				
		int i;
		Debug("[CodeGen] globlist = " + globlist);
		for(i = 0; i < globlist.size(); i++)
		{
			Symbol symbol = stm.getSymbol(globlist.get(i));
			if(symbol == null)
			{
				continue;
			}
			if(symbol.symbolType == Symbol.symbolTypeEnum.NORMAL)
			{
				if(symbol.getType() == DijkstraType.FLOAT)
				{
					fv = cw.visitField(ACC_STATIC, symbol.getId(), "F", null, null);
					fv.visitEnd();
					Debug("[CodeGen]symbol glaobal fv  FLOAT is created = " + symbol.getId()+ " "+ symbol.globalFlag);
				}
				else if(symbol.getType() == DijkstraType.INT)
				{
					fv = cw.visitField(ACC_STATIC, symbol.getId(), "I", null, null);
					fv.visitEnd();
					Debug("[CodeGen]symbol glaobal fv INT"+ symbol.getId()+ " is created = " + symbol.globalFlag);
				}
			}
			else if(symbol.symbolType == Symbol.symbolTypeEnum.ARRAY)
			{
				if(symbol.getType() == DijkstraType.FLOAT)
				{
					fv = cw.visitField(ACC_STATIC, symbol.getId(), "[F", null, null);
					fv.visitEnd();
					Debug("[CodeGen]array symbol glaobal fv  FLOAT is created = "+ symbol.getId()+ " " + symbol.globalFlag);
				}
				else if(symbol.getType() == DijkstraType.INT)
				{
					fv = cw.visitField(ACC_STATIC, symbol.getId(), "[I", null, null);
					fv.visitEnd();
					Debug("[CodeGen]array symbol glaobal fv INT is created = " + symbol.globalFlag);
				}
			}
		}
//			{
//		stm.exitScope();
//			for(i = 0; i < globallist.size(); i++)
//			{
//				if (globallist.get(i) == symbol.getId())
//				{
//					break;
//				}
//			}
//				
//				if(i == globallist.size())
//				{
//					if(symbol.getType() == DijkstraType.FLOAT)
//					{
//						fv = cw.visitField(0, symbol.getId(), "F", null, null);
//						fv.visitEnd();
//						Debug("symbol glaobal fv  FLOAT is created = " + symbol.globalFlag);
//					}
//					else if(symbol.getType() == DijkstraType.INT)
//					{
//						fv = cw.visitField(0, symbol.getId(), "I", null, null);
//						fv.visitEnd();
//						Debug("symbol glaobal fv INT is created = " + symbol.globalFlag);
//					}
//					globallist.add(symbol.getId());
//					symbol.globalFlag = false;
//				}
//			}
		
		
		mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		
		visitChildren(dijkstratextnode);
//		dijkstratextnode.getChild(0).accept(this);
		
		// Actual end of generation
		cw.visitEnd();
		return cw.toByteArray();
	}
	
	
	/**
	 * Call the runtime input for the ID and then stor the result in the 
	 * @see dijkstra.ast.ASTVisitor#visit(dijkstra.ast.ASTNodeFactory.InputNode)
	 */
	public byte[] visit(InputNode input)
	{
		for(int i = 0; i < input.getChildCount(); i++)
		{
			IDNode id = input.getID(i);
			mv.visitLdcInsn(id.token.getText());	// Name of the variable
			if(id.getType() == BOOLEAN) 
			{
				mv.visitMethodInsn(INVOKESTATIC, "dijkstra/runtime/DijkstraRuntime", "inputBoolean", 
						"(Ljava/lang/String;)Z", false);
				mv.visitVarInsn(ISTORE, id.getAddress());
			}
			else if (id.getType() == DijkstraType.FLOAT)
			{
				mv.visitMethodInsn(INVOKESTATIC, "dijkstra/runtime/DijkstraRuntime", "inputFloat", 
						"(Ljava/lang/String;)F", false);
				mv.visitVarInsn(FSTORE, id.getAddress());
			}
			else
			{
				mv.visitMethodInsn(INVOKESTATIC, "dijkstra/runtime/DijkstraRuntime", "inputInt", 
						"(Ljava/lang/String;)I", false);
				mv.visitVarInsn(ISTORE, id.getAddress());
			} 
			
		}
		return null;
	}
	
	/**
	 * Get the expression onto the stack and then call the runtime print
	 * routine.
	 * @see dijkstra.ast.ASTVisitor#visit(dijkstra.ast.ASTNodeFactory.OutputNode)
	 */
	public byte[] visit(OutputNode output)
	{
		output.getExpression().accept(this);	// TOS = expression value
		Debug("[CodeGen] output type = " + output.type);
		if(output.getExpression().nodeType == ASTNodeType.ARRARYACCESSOR)
		{
			if(output.type == DijkstraType.FLOAT)
			{
				mv.visitInsn(FALOAD);
				mv.visitMethodInsn(INVOKESTATIC, 
						"dijkstra/runtime/DijkstraRuntime", "printFloat", "(F)V", false);
			}
			else if(output.type == DijkstraType.BOOLEAN)
			{
				mv.visitInsn(IALOAD);
				mv.visitMethodInsn(INVOKESTATIC, 
						"dijkstra/runtime/DijkstraRuntime", "printBoolean", "(Z)V", false);
			}
			else
			{
				mv.visitInsn(IALOAD);
				mv.visitMethodInsn(INVOKESTATIC, 
						"dijkstra/runtime/DijkstraRuntime", "printInt", "(I)V", false);
			}
		}
		else if (output.type == DijkstraType.FLOAT)
		{
			mv.visitMethodInsn(INVOKESTATIC, 
				"dijkstra/runtime/DijkstraRuntime", "printFloat", "(F)V", false);
		}
		else if(output.type == DijkstraType.UNDEFINED)
		{
			if(output.getExpression().type == DijkstraType.FUNC)
			{
				FuntioncallNode funcallnode = (FuntioncallNode)output.getExpression();
				Symbol symbol = stm.getSymbol(funcallnode.getId().getName());
				int functioncallAddress = symbol.getAddress();
				ArrayList<DijkstraType> returntypelist = symbol.getTypeList();

				for(int i = 0; i < returntypelist.size(); i++)
				{
//					mv.visitVarInsn(ALOAD, functioncallAddress);
//					mv.visitIntInsn(BIPUSH, i);
//					mv.visitInsn(FALOAD);
					if(returntypelist.get(i) == DijkstraType.FLOAT)
					{
						mv.visitMethodInsn(INVOKESTATIC, 
								"dijkstra/runtime/DijkstraRuntime", "printFloat", "(F)V", false);
					}
					else if(returntypelist.get(i) == DijkstraType.BOOLEAN)
					{
						mv.visitMethodInsn(INVOKESTATIC, 
								"dijkstra/runtime/DijkstraRuntime", "printBoolean", "(Z)V", false);
					}
					else
					{
//						mv.visitInsn(F2I);
						mv.visitMethodInsn(INVOKESTATIC, 
								"dijkstra/runtime/DijkstraRuntime", "printInt", "(I)V", false);
					}
				}
			}
		}
		else if (output.type == BOOLEAN)
		{
			mv.visitMethodInsn(INVOKESTATIC, 
				"dijkstra/runtime/DijkstraRuntime", "printBoolean", "(Z)V", false);
		}
		else  
		{
			mv.visitMethodInsn(INVOKESTATIC, 
				"dijkstra/runtime/DijkstraRuntime", "printInt", "(I)V", false);
		}
		
		return null;
	}
	
	
	public byte[] visit(AssignNode assign)
	{
		int exprnumber = 0;
		
		for(int i = 0; i < assign.getVarlist().getChildCount(); i++)
		{
			DijkstraType type = assign.getVarlist().getChild(i).type;
			IDNode idnode = assign.getVarlist().getId(i);
			Symbol tempsymbol = stm.getSymbol(idnode.getName());
			
			if((assign.getVarlist().getChild(i).nodeType == ASTNodeType.CLASSATTIACCESSOR)
					&& (assign.getExpressionlist().getChild(exprnumber).nodeType != ASTNodeType.CLASSATTIACCESSOR))
			{
//				assign.getVarlist().getChild(i).accept(this);
				ClassAttributeAccessorNode attrAccessorNode = (ClassAttributeAccessorNode)assign.getVarlist().getChild(i);
				mv.visitVarInsn(ALOAD, attrAccessorNode.getObjectName().getAddress());
				assign.getExpressionlist().getChild(exprnumber).accept(this);
				tempsymbol = attrAccessorNode.getattributeName().symbol;
				DijkstraType tmpexprtype = assign.getExpressionlist().getChild(exprnumber).type;
				
				if(tmpexprtype == DijkstraType.INT && tempsymbol.getType() == DijkstraType.FLOAT)
				{
					mv.visitInsn(I2F);
				}
				else if(tmpexprtype == DijkstraType.FLOAT)
				{
					
				}
				else if(tmpexprtype == BOOLEAN)
				{
					
				}
				else
				{
					Debug("[Codegen] assign attribute to variable name : " + assign.getVarlist().getId(i).getName()+ "  add :"+ assign.getVarlist().getId(i).getAddress());
				}
				exprnumber = exprnumber + 1;
			}
			else if((assign.getVarlist().getChild(i).nodeType != ASTNodeType.CLASSATTIACCESSOR)
					&& (assign.getExpressionlist().getChild(exprnumber).nodeType == ASTNodeType.CLASSATTIACCESSOR))
			{
//				assign.getExpressionlist().getChild(exprnumber).accept(this);
				ClassAttributeAccessorNode attrAccessorNode = (ClassAttributeAccessorNode)assign.getExpressionlist().getChild(exprnumber);
				attrAccessorNode.accept(this);
				Symbol attrSymbol = attrAccessorNode.getattributeName().symbol;
				int access = attrSymbol.accessSpec;
				Debug("[Codegen] access " + access + " attrsymbol  "+ 	attrSymbol);			
				
				DijkstraType tmpexprtype = assign.getExpressionlist().getChild(exprnumber).type;
				if(tmpexprtype == DijkstraType.INT && tempsymbol.getType() == DijkstraType.FLOAT)
				{
					mv.visitInsn(I2F);
				}
				else if(tmpexprtype == DijkstraType.FLOAT)
				{
					
				}
				else if(tmpexprtype == BOOLEAN)
				{
					
				}
				else
				{
					Debug("[Codegen] assign attribute to variable name : " + assign.getVarlist().getId(i).getName()+ "  add :"+ assign.getVarlist().getId(i).getAddress());
				}
				exprnumber = exprnumber + 1;
			}
			else if((assign.getVarlist().getChild(i).nodeType == ASTNodeType.CLASSATTIACCESSOR)
					&& (assign.getExpressionlist().getChild(exprnumber).nodeType == ASTNodeType.CLASSATTIACCESSOR))
			{
				
			}
			else if((assign.getVarlist().getChild(i).nodeType == ASTNodeType.ARRARYACCESSOR)
					&& (assign.getExpressionlist().getChild(exprnumber).nodeType != ASTNodeType.ARRARYACCESSOR))
			{
				assign.getVarlist().getChild(i).accept(this);
				assign.getExpressionlist().getChild(exprnumber).accept(this);
				DijkstraType exprtype = assign.getExpressionlist().getChild(exprnumber).type;
				if(type == DijkstraType.FLOAT)
				{
					if(exprtype == INT)
					{
						mv.visitInsn(I2F);
					}
//					mv.visitInsn(FASTORE);
				}
//				else if(assign.getVarlist().getChild(i).type == DijkstraType.BOOLEAN)
//				{
//					mv.visitInsn(BASTORE);
//				}
				else
				{
					if(exprtype == DijkstraType.FLOAT)
					{
						mv.visitInsn(F2I);
					}
//					mv.visitInsn(IASTORE);
				}
				exprnumber = exprnumber + 1;
			}		
			else if((assign.getExpressionlist().getChild(exprnumber).nodeType == ASTNodeType.ARRARYACCESSOR)
					&& (assign.getVarlist().getChild(i).nodeType != ASTNodeType.ARRARYACCESSOR))
			{
//				assign.getVarlist().getChild(i).accept(this);
				assign.getExpressionlist().getChild(i).accept(this);
				if(type == DijkstraType.FLOAT)
				{
					mv.visitInsn(FALOAD);
//					mv.visitVarInsn(FSTORE, assign.getVarlist().getId(i).getAddress());
				}
				else if(type == BOOLEAN)
				{
					mv.visitInsn(BALOAD);
//					mv.visitVarInsn(BASTORE, assign.getVarlist().getId(i).getAddress());
				}
				else
				{
					mv.visitInsn(IALOAD);
//					mv.visitVarInsn(ISTORE, assign.getVarlist().getId(i).getAddress());
				}

				exprnumber = exprnumber + 1;
			}
			else if((assign.getExpressionlist().getChild(exprnumber).nodeType == ASTNodeType.ARRARYACCESSOR)
					&& (assign.getVarlist().getChild(i).nodeType == ASTNodeType.ARRARYACCESSOR))
			{
				ArrayaccessorNode arraynode = (ArrayaccessorNode)assign.getVarlist().getChild(i);
				Symbol arraysymbol = stm.getSymbol(arraynode.getId().getName());
				arraynode.accept(this);
				assign.getExpressionlist().getChild(exprnumber).accept(this);
				DijkstraType exprtype = assign.getExpressionlist().getChild(exprnumber).type;

				if(type == DijkstraType.FLOAT)
				{
					mv.visitInsn(FALOAD);
//					mv.visitInsn(FASTORE);
				}
				else if(type == BOOLEAN)
				{
					mv.visitInsn(BALOAD);
//					mv.visitInsn(BASTORE);
				}
				else
				{
					if(exprtype == DijkstraType.FLOAT)
					{
						mv.visitInsn(F2I);
					}
					mv.visitInsn(IALOAD);
//					mv.visitInsn(IASTORE);
				}

				exprnumber = exprnumber + 1;
			}
			else if(assign.getExpressionlist().getChild(exprnumber).nodeType == ASTNodeType.FUNCCALL)
			{
				FuntioncallNode funcallnode = (FuntioncallNode)assign.getExpressionlist().getChild(exprnumber);
				Symbol symbol = stm.getSymbol(funcallnode.getId().getName());
				int returnnum = symbol.getTypeList().size();
				
				assign.getExpressionlist().getChild(exprnumber).accept(this);
				int returnArrayAddress = funcallnode.getId().getAddress();
				Debug("[CodeGen]returnArrayAddress  :  " + returnArrayAddress);

				for(int n = returnnum; n > 0; n--)
				{
					int varaddress = assign.getVarlist().getId(i).getAddress();
					int addressOffset = stm.getCurrentSymbolTable().getIdAddressOffest();
					Debug("[CodeGen]var : " +assign.getVarlist().getId(i).getName()+ "  address  :  " + varaddress + "  idAddrOffset = " + addressOffset);
					i++;
				}
				exprnumber = exprnumber + 1;
			}
			else
			{
				assign.getExpressionlist().getChild(exprnumber).accept(this);
				DijkstraType tmpexprtype = assign.getExpressionlist().getChild(exprnumber).type;
				Debug("assign name : " + assign.getVarlist().getId(i).getName()+ "  add :"+ assign.getVarlist().getId(i).getAddress() + "  tmpexprtype = " + tmpexprtype + "  tempsymbol.getType() = " + tempsymbol.getType());

				int varaddress = assign.getVarlist().getId(i).getAddress();
				
				Symbol symbol = stm.getSymbol(assign.getVarlist().getId(i).getName());
				if(symbol.globalFlag == true)
				{
					
				}
				else
				{
					if(tmpexprtype == DijkstraType.INT && tempsymbol.getType() == DijkstraType.FLOAT)
					{
						mv.visitInsn(I2F);
					}
					else if(tmpexprtype == DijkstraType.FLOAT)
					{
						
					}
					else if(tmpexprtype == BOOLEAN)
					{
						
					}
					else
					{
						Debug("assign name : " + assign.getVarlist().getId(i).getName()+ "  add :"+ assign.getVarlist().getId(i).getAddress());
					}
					exprnumber = exprnumber + 1;
				}
			}
		}
		
		for(int i = assign.getVarlist().getChildCount()-1; i >= 0 ; i--)
		{
			DijkstraType type = assign.getVarlist().getChild(i).type;
			int varaddress = assign.getVarlist().getId(i).getAddress();
			Symbol symbol = stm.getSymbol(assign.getVarlist().getId(i).getName());
			
			if(assign.getVarlist().getChild(i).nodeType == ASTNodeType.ARRARYACCESSOR)
			{
				if(type == DijkstraType.FLOAT)
				{
					mv.visitInsn(FASTORE);
				}
				else if(assign.getVarlist().getChild(i).type == DijkstraType.BOOLEAN)
				{
					mv.visitInsn(BASTORE);
				}
				else
				{
					mv.visitInsn(IASTORE);
				}
			}
			else if(assign.getVarlist().getChild(i).nodeType == ASTNodeType.CLASSATTIACCESSOR)
			{
				ClassAttributeAccessorNode attrAccessorNode = (ClassAttributeAccessorNode)assign.getVarlist().getChild(i);
				IDNode objectIdNode =  attrAccessorNode.getObjectName();
				IDNode attributeNode = attrAccessorNode.getattributeName();
				String classPath = classPackage + "/" + objectIdNode.symbol.getClassname();
				if(type == DijkstraType.INT)
				{
//					mv.visitFieldInsn(PUTSTATIC, classPath, attributeNode.token.getText(), "I");
					mv.visitFieldInsn(PUTSTATIC, classPath, attributeNode.getName(), "I");

				}
				else if(type == DijkstraType.FLOAT)
				{
//					mv.visitFieldInsn(PUTSTATIC, classPath, attributeNode.token.getText(), "F");
					mv.visitFieldInsn(PUTSTATIC, classPath, attributeNode.getName(), "F");

				}
				else if(type == DijkstraType.BOOLEAN)
				{
//					mv.visitFieldInsn(PUTSTATIC, classPath, attributeNode.token.getText(), "Z");
					mv.visitFieldInsn(PUTSTATIC, classPath, attributeNode.getName(), "Z");

				}
			}
			else if(symbol.symbolType == symbolTypeEnum.PROPERTY)
			{
				IDNode id = (IDNode)assign.getVarlist().getChild(i);
				if(id.getType() == DijkstraType.FLOAT)
				{
//					mv.visitFieldInsn(GETFIELD, classPackage + "/" + symbol.getClassname(), id.token.getText(), "F");
					mv.visitFieldInsn(PUTSTATIC, classPackage + "/" + symbol.getClassname(), id.getName(), "F");
				}
				else if(id.getType() == DijkstraType.BOOLEAN)
				{
//					mv.visitFieldInsn(GETFIELD, classPackage + "/" + symbol.getClassname(), id.token.getText(), "Z");
					mv.visitFieldInsn(PUTSTATIC, classPackage + "/" + symbol.getClassname(), id.getName(), "Z");
				}
				
				else
				{
//					mv.visitFieldInsn(GETFIELD, classPackage + "/" + symbol.getClassname(), id.token.getText(), "I");
					mv.visitFieldInsn(PUTSTATIC, classPackage + "/" + symbol.getClassname(), id.getName(), "I");
				}
			}
			else if(symbol.globalFlag == true)
			{
				Debug(" [CodeGen] assign to a global id = " + symbol.getId() + " talbe number " + stm.getcurrentTableNumber());
				String className;
				if(symbol.getClassname() != null)
				{
					className = symbol.getClassname();
				}
				else 
				{
					className = programName;
				}
				if(symbol.getType() == DijkstraType.FLOAT)
				{
//					mv.visitFieldInsn(PUTSTATIC, classPackage + "/" + programName, symbol.getId(), "F");
					mv.visitFieldInsn(PUTSTATIC, classPackage + "/" + className, symbol.getId(), "F");

				}
				else if(symbol.getType() == DijkstraType.BOOLEAN)
				{
//					mv.visitFieldInsn(PUTSTATIC, classPackage + "/" + programName, symbol.getId(), "Z");
					mv.visitFieldInsn(PUTSTATIC, classPackage + "/" + className, symbol.getId(), "Z");

				}
				else
				{
//					mv.visitFieldInsn(PUTSTATIC, classPackage + "/" + programName, symbol.getId(), "I");
					mv.visitFieldInsn(PUTSTATIC, classPackage + "/" + className, symbol.getId(), "I");

				}
			}
			else
			{
				if(symbol.getType() == DijkstraType.FLOAT)
				{
					mv.visitVarInsn(FSTORE, varaddress);
				}
				else if(symbol.getType() == BOOLEAN)
				{
					mv.visitVarInsn(ISTORE, varaddress);
				}
				else if(symbol.getType() == INT)
				{
					mv.visitVarInsn(ISTORE, varaddress);
				}
				else if(symbol.getType() == OBJECT)
				{
					mv.visitVarInsn(ASTORE, varaddress);
				}
				else
				{
					Debug("assign name : " + assign.getVarlist().getId(i).getName()+ "  add :"+ assign.getVarlist().getId(i).getAddress());
				}
			}
		}
		return null;
	}

	
	
	public byte[] visit(ArrayaccessorNode arrayAccessorNode)
	{
		int arrayHeadAddress = arrayAccessorNode.getId().getAddress();
		Symbol symbol = stm.getSymbol(arrayAccessorNode.getId().getName());
		String arraynome = arrayAccessorNode.getId().getName();
		if(symbol.globalFlag == true && symbol.symbolType == symbolTypeEnum.ARRAY)
		{
			if(arrayAccessorNode.type == DijkstraType.FLOAT)
			{
				mv.visitFieldInsn(GETSTATIC, classPackage + "/" + programName, arraynome, "[F");
			}
			else if(arrayAccessorNode.type == DijkstraType.BOOLEAN)
			{
				mv.visitFieldInsn(GETSTATIC, classPackage + "/" + programName, arraynome, "[Z");
			}
			else
			{
				mv.visitFieldInsn(GETSTATIC, classPackage + "/" + programName, arraynome, "[I");
			}
		}
		else
		{
			if(arrayAccessorNode.type == DijkstraType.FLOAT)
			{
				mv.visitVarInsn(ALOAD, arrayHeadAddress);
			}
			else if(arrayAccessorNode.type == DijkstraType.BOOLEAN)
			{
				mv.visitVarInsn(ALOAD, arrayHeadAddress);
			}
			else
			{
				mv.visitVarInsn(ALOAD, arrayHeadAddress);
			}
		}
		
		arrayAccessorNode.getExpression().accept(this);
		return null;
	}
	
	public byte[] visit(ArrayDeclarationNode arrayDeclrNode)
	{
		int arrayElementAddress = 0;
		arrayDeclrNode.getExpression().accept(this);
		for(int i = 1; i < arrayDeclrNode.getChildCount(); i++)
		{
			IDNode idnode = arrayDeclrNode.getID(i);
			String name = idnode.getName();
			Symbol symbol = stm.getSymbol(arrayDeclrNode.getID(i).getName());
			
			if(symbol.globalFlag == true)
			{
				if(arrayDeclrNode.getID(i).type == DijkstraType.FLOAT)
				{
					mv.visitIntInsn(NEWARRAY, T_FLOAT);
					mv.visitFieldInsn(PUTSTATIC, classPackage + "/" + programName, name, "[F");
				}
				else if(arrayDeclrNode.getID(i).type == DijkstraType.BOOLEAN)
				{
					mv.visitIntInsn(NEWARRAY, T_BOOLEAN);
					mv.visitFieldInsn(PUTSTATIC, classPackage + "/" + programName, name, "[Z");
				}
				else
				{
					mv.visitIntInsn(NEWARRAY, T_INT );
					mv.visitFieldInsn(PUTSTATIC, classPackage + "/" + programName, name, "[I");
				}	
			}
			else
			{
				arrayElementAddress = arrayDeclrNode.getID(i).getAddress();
				if(arrayDeclrNode.getID(i).type == DijkstraType.FLOAT)
				{
					mv.visitIntInsn(NEWARRAY, T_FLOAT);
					mv.visitVarInsn(ASTORE, arrayElementAddress);
				}
				else if(arrayDeclrNode.getID(i).type == DijkstraType.BOOLEAN)
				{
					mv.visitIntInsn(NEWARRAY, T_BOOLEAN);
					mv.visitVarInsn(ASTORE, arrayElementAddress);
				}
				else
				{
					mv.visitIntInsn(NEWARRAY, T_INT );
					mv.visitVarInsn(ASTORE , arrayElementAddress);
				}
			}
		}
		return null;
	}
	
	public byte[] visit(AlternativeNode alternative)
	{
		final Label endLabel = new Label();
		guardLabelStack.push(endLabel);
		visitChildren(alternative);
		guardLabelStack.pop();
		mv.visitIntInsn(BIPUSH, alternative.getLineNumber());
		mv.visitMethodInsn(INVOKESTATIC, "dijkstra/runtime/DijkstraRuntime", 
				"abortNoAlternative", "(I)V", false);
		mv.visitLabel(endLabel);
		return null;
	}
	
	public byte[] visit(IterativeNode iterative)
	{
		final int tempAddress = JVMInfo.getNextAddress();	// store the counter
		final Label iterativeStartLabel = new Label();
		mv.visitLabel(iterativeStartLabel);
		mv.visitInsn(ICONST_0);
		mv.visitVarInsn(ISTORE, tempAddress);
		for(int i = 0; i < iterative.getChildCount(); i++)
		{
			final Label trueGuardLabel = new Label();
			guardLabelStack.push(trueGuardLabel);
			iterative.getChild(i).accept(this);
			final Label nextGruardLabel = new Label();
			mv.visitJumpInsn(GOTO, nextGruardLabel);
			mv.visitLabel(trueGuardLabel);
			mv.visitInsn(ICONST_1);
			mv.visitVarInsn(ISTORE, tempAddress);
			mv.visitLabel(nextGruardLabel);
			guardLabelStack.pop();
		}
		// now test if it is 1
		mv.visitVarInsn(ILOAD, tempAddress);
		mv.visitJumpInsn(IFNE, iterativeStartLabel);
//		visitChildren(iterative);
//		for(int i = 0; i < iterative.getChildCount(); i++)
//		{
//			Label startlabel = new Label();
//			mv.visitLabel(startlabel);
//			guardLabelStack.push(startlabel);
//			iterative.getChild(i).accept(this);
//			guardLabelStack.pop();
//		}
		//		mv.visitIntInsn(BIPUSH, alternative.getLineNumber());
//		mv.visitMethodInsn(INVOKESTATIC, "dijkstra/runtime/DijkstraRuntime", 
//				"abortNoAlternative", "(I)V", false);
		return null;

		
	}
	
	public byte[] visit(GuardNode guard)
	{
		final Label failLabel = new Label();
		guard.getExpression().accept(this);
		
//		if(guard.getExpression().nodeType == ASTNodeType.FUNCCALL)
//		{
//			FuntioncallNode funcallnode = (FuntioncallNode)guard.getExpression();
//			int returnArrayAddress = funcallnode.getId().getAddress();
//			mv.visitVarInsn(ALOAD, returnArrayAddress);
//			mv.visitIntInsn(BIPUSH, 0);
//			mv.visitInsn(FALOAD);
//			mv.visitInsn(F2I);
//		}
		
		mv.visitJumpInsn(IFEQ, failLabel);
		guard.getStatement().accept(this);
		mv.visitJumpInsn(GOTO, guardLabelStack.peek());
		mv.visitLabel(failLabel);
		return null;
	}
	
	
	public byte[] visit(VarlistNode varlistnode)
	{
//		visitChildren(varlistnode);
		return null;
	}
	
	/**
	 * Evaluate the child expression and then negate it (logical or arithmetic). Logical
	 * negation is more difficult because there is no logical negate instruction in the 
	 * JVM instruction set.
	 * @see dijkstra.ast.ASTVisitor#visit(dijkstra.ast.ASTNodeFactory.UnaryExpressionNode)
	 */
	public byte[] visit(UnaryExpressionNode unary)
	{
		visitChildren(unary);	// Evaluate the expression
		// TOS = the child expression
		if (unary.type == INT) 
		{
			mv.visitInsn(INEG);
		}
		else if (unary.type == DijkstraType.FLOAT)
		{
			mv.visitInsn(FNEG);
		}
		else 
		{	// Boolean ~
			final Label l1 = new Label();
			final Label l2 = new Label();
			mv.visitJumpInsn(IFEQ, l1);
			mv.visitInsn(ICONST_0);		// true -> false
			mv.visitJumpInsn(GOTO, l2);
			mv.visitLabel(l1);
			mv.visitInsn(ICONST_1);		// false -> true
			mv.visitLabel(l2);
		}
		return null;
	}
	
	
	
	/**
	 * Evaluate the child expression and then negate it (logical or arithmetic). Logical
	 * negation is more difficult because there is no logical negate instruction in the 
	 * JVM instruction set.
	 * @see dijkstra.ast.ASTVisitor#visit(dijkstra.ast.ASTNodeFactory.UnaryExpressionNode)
	 */
	public byte[] visit(variableDeclarationNode vardeclnode)
	{
//		if(vardeclnode.getChild(0).type == CLASS)
//		{
//			mv.visitTypeInsn(NEW, "test$TestClass");
//			mv.visitInsn(DUP);
//		}
		return null;
	}
	
	/**
	 * Get the values of the left and right children on the stack and then perform
	 * the operation.
	 * @see dijkstra.ast.ASTVisitor#visit(dijkstra.ast.ASTNodeFactory.BinaryExpressionNode)
	 */
	public byte[] visit(BinaryExpressionNode binary)
	{
		DijkstraType cmpTypeFlag = DijkstraType.INT;
		
		Label lab1, lab2;
		DijkstraType type = binary.type;
		Debug("[CodeGen] binary = "+binary);
		DijkstraType expr1Type = binary.getExpr1().type;
		DijkstraType expr2Type = binary.getExpr2().type;
		Debug("[CodeGen] expr1Type = " + expr1Type);
		Debug("[CodeGen] expr2Type = " + expr2Type);
		
		if(binary.getOp() != DijkstraParser.SLASH 
				&& binary.getOp() != DijkstraParser.AND
				&& binary.getOp() != DijkstraParser.OR
				&& binary.getExpr1().nodeType != ASTNodeType.ARRARYACCESSOR
				&& binary.getExpr2().nodeType != ASTNodeType.ARRARYACCESSOR
				)
		{
			if(expr1Type == DijkstraType.INT && expr2Type == DijkstraType.FLOAT)
			{
				cmpTypeFlag = DijkstraType.FLOAT;
				binary.getExpr1().accept(this);
				mv.visitInsn(I2F);
				binary.getExpr2().accept(this);
			}
			else if(expr1Type == DijkstraType.FLOAT && expr2Type == DijkstraType.INT)
			{
				cmpTypeFlag = DijkstraType.FLOAT;
				binary.getExpr1().accept(this);
				binary.getExpr2().accept(this);
				mv.visitInsn(I2F);
			}
			else if(expr1Type == DijkstraType.FUNC || expr1Type == DijkstraType.FUNC)
			{
				if(expr1Type == DijkstraType.FUNC && expr2Type != DijkstraType.FUNC)
				{
					FuntioncallNode funcnode = (FuntioncallNode)binary.getExpr1();
					funcnode.accept(this);
					if(stm.getSymbol(funcnode.funcName).getTypeList().isEmpty() == false)
					{
						expr1Type = stm.getSymbol(funcnode.funcName).getTypeList().get(0);
					}
					
					if(expr1Type == DijkstraType.INT && expr2Type == DijkstraType.FLOAT)
					{
						cmpTypeFlag = DijkstraType.FLOAT;
						mv.visitInsn(I2F);
						binary.getExpr2().accept(this);
					}
					else if(expr1Type == DijkstraType.FLOAT && expr2Type == DijkstraType.INT)
					{
						cmpTypeFlag = DijkstraType.FLOAT;
						binary.getExpr2().accept(this);
						mv.visitInsn(I2F);
					}
					else if(expr1Type == expr2Type && expr1Type == DijkstraType.INT)
					{
						cmpTypeFlag = DijkstraType.INT;
						binary.getExpr2().accept(this);		// Stack = ... left right
					}
					else if(expr1Type == expr2Type && expr1Type == DijkstraType.FLOAT)
					{
						cmpTypeFlag = DijkstraType.FLOAT;
						binary.getExpr2().accept(this);	// Stack = ... left right
					}
				}
				else if(expr2Type == DijkstraType.FUNC && expr1Type != DijkstraType.FUNC)
				{
					FuntioncallNode funcnode = (FuntioncallNode)binary.getExpr2();
					if(stm.getSymbol(funcnode.funcName).getTypeList().isEmpty() == false)
					{
						expr2Type = stm.getSymbol(funcnode.funcName).getTypeList().get(0);
					}
					
					binary.getExpr1().accept(this);
					
					if(expr1Type == DijkstraType.INT && expr2Type == DijkstraType.FLOAT)
					{
						cmpTypeFlag = DijkstraType.FLOAT;
						mv.visitInsn(I2F);
						binary.getExpr2().accept(this);
					}
					else if(expr1Type == DijkstraType.FLOAT && expr2Type == DijkstraType.INT)
					{
						cmpTypeFlag = DijkstraType.FLOAT;
						binary.getExpr2().accept(this);
						mv.visitInsn(I2F);
					}
					else if(expr1Type == expr2Type && expr1Type == DijkstraType.INT)
					{
						cmpTypeFlag = DijkstraType.INT;
						binary.getExpr2().accept(this);		// Stack = ... left right
					}
					else if(expr1Type == expr2Type && expr1Type == DijkstraType.FLOAT)
					{
						cmpTypeFlag = DijkstraType.FLOAT;
						binary.getExpr2().accept(this);	// Stack = ... left right
					}
				}
				else if(expr1Type == DijkstraType.FUNC && expr2Type == DijkstraType.FUNC)
				{
					FuntioncallNode func1node = (FuntioncallNode)binary.getExpr1();
					if(stm.getSymbol(func1node.funcName).getTypeList().isEmpty() == false)
					{
						expr1Type = stm.getSymbol(func1node.funcName).getTypeList().get(0);
					}
					
					FuntioncallNode func2node = (FuntioncallNode)binary.getExpr2();
					if(stm.getSymbol(func2node.funcName).getTypeList().isEmpty() == false)
					{
						expr2Type = stm.getSymbol(func2node.funcName).getTypeList().get(0);
					}
					
					if(expr1Type == DijkstraType.INT && expr2Type == DijkstraType.FLOAT)
					{
						cmpTypeFlag = DijkstraType.FLOAT;
						binary.getExpr1().accept(this);
						mv.visitInsn(I2F);
						binary.getExpr2().accept(this);
					}
					else if(expr1Type == DijkstraType.FLOAT && expr2Type == DijkstraType.INT)
					{
						cmpTypeFlag = DijkstraType.FLOAT;
						binary.getExpr1().accept(this);
						binary.getExpr2().accept(this);
						mv.visitInsn(I2F);
					}
					else if(expr1Type == expr2Type && expr1Type == DijkstraType.INT)
					{
						cmpTypeFlag = DijkstraType.INT;
						binary.getExpr1().accept(this);
						binary.getExpr2().accept(this);		// Stack = ... left right
					}
					else if(expr1Type == expr2Type && expr1Type == DijkstraType.FLOAT)
					{
						cmpTypeFlag = DijkstraType.FLOAT;
						binary.getExpr1().accept(this);
						binary.getExpr2().accept(this);	// Stack = ... left right
					}
				}
			}
			else if(expr1Type == expr2Type && expr1Type == DijkstraType.INT)
			{
				cmpTypeFlag = DijkstraType.INT;
				visitChildren(binary);		// Stack = ... left right
			}
			else if(expr1Type == expr2Type && expr1Type == DijkstraType.FLOAT)
			{
				cmpTypeFlag = DijkstraType.FLOAT;
				visitChildren(binary);		// Stack = ... left right
			}
			else
			{
				cmpTypeFlag = DijkstraType.INT;
				visitChildren(binary);		// Stack = ... left right
			}
		}
		
		if(binary.getExpr1().nodeType == ASTNodeType.ARRARYACCESSOR
				&& binary.getExpr2().nodeType != ASTNodeType.ARRARYACCESSOR)
		{
			binary.getExpr1().accept(this);
			if(expr1Type == DijkstraType.FLOAT)
			{
				mv.visitInsn(FALOAD);
				cmpTypeFlag = DijkstraType.FLOAT;
			}
			else if(expr1Type == DijkstraType.INT && expr2Type == DijkstraType.FLOAT)
			{
				mv.visitInsn(IALOAD);
				mv.visitInsn(I2F);
				cmpTypeFlag = DijkstraType.FLOAT;
			}
			else if(expr1Type == DijkstraType.BOOLEAN)
			{
				mv.visitInsn(IALOAD);
				cmpTypeFlag = DijkstraType.INT;
			}
			else
			{
				mv.visitInsn(IALOAD);
				cmpTypeFlag = DijkstraType.INT;
			}
			
			binary.getExpr2().accept(this);
			if(expr2Type == DijkstraType.INT && cmpTypeFlag == DijkstraType.FLOAT)
			{
				mv.visitInsn(I2F);
			}
		}
		else if(binary.getExpr2().nodeType == ASTNodeType.ARRARYACCESSOR 
				&& binary.getExpr1().nodeType != ASTNodeType.ARRARYACCESSOR)
		{
			binary.getExpr1().accept(this);
			if(expr1Type == DijkstraType.FLOAT)
			{
				cmpTypeFlag = DijkstraType.FLOAT;
			}
			else if(expr1Type == DijkstraType.INT && expr2Type == DijkstraType.FLOAT)
			{
				mv.visitInsn(I2F);
				cmpTypeFlag = DijkstraType.FLOAT;
			}
			else
			{
				cmpTypeFlag = DijkstraType.INT;
			}
			
			binary.getExpr2().accept(this);
			if(expr2Type == DijkstraType.FLOAT)
			{
				mv.visitInsn(FALOAD);
				cmpTypeFlag = DijkstraType.FLOAT;
			}
			else if(expr2Type == DijkstraType.INT && expr1Type == DijkstraType.FLOAT)
			{
				mv.visitInsn(IALOAD);
				mv.visitInsn(I2F);
				cmpTypeFlag = DijkstraType.FLOAT;
			}
			else if(expr2Type == DijkstraType.BOOLEAN)
			{
				mv.visitInsn(IALOAD);
				cmpTypeFlag = DijkstraType.INT;
			}
			else
			{
				mv.visitInsn(IALOAD);
				cmpTypeFlag = DijkstraType.INT;
			}
		}
		else if(binary.getExpr2().nodeType == ASTNodeType.ARRARYACCESSOR 
				&& binary.getExpr1().nodeType == ASTNodeType.ARRARYACCESSOR)
		{
			binary.getExpr1().accept(this);
			if(expr1Type == DijkstraType.FLOAT)
			{
				mv.visitInsn(FALOAD);
				cmpTypeFlag = DijkstraType.FLOAT;
			}
			else if(expr1Type == DijkstraType.INT && expr2Type == DijkstraType.FLOAT)
			{
				mv.visitInsn(IALOAD);
				mv.visitInsn(I2F);
				cmpTypeFlag = DijkstraType.FLOAT;
			}
			else if(expr1Type == DijkstraType.BOOLEAN)
			{
				mv.visitInsn(IALOAD);
				cmpTypeFlag = DijkstraType.INT;
			}
			else
			{
				mv.visitInsn(IALOAD);
				cmpTypeFlag = DijkstraType.INT;
			}
			
			binary.getExpr2().accept(this);
			if(expr2Type == DijkstraType.FLOAT)
			{
				mv.visitInsn(FALOAD);
				cmpTypeFlag = DijkstraType.FLOAT;
			}
			else if(expr2Type == DijkstraType.INT && expr1Type == DijkstraType.FLOAT)
			{
				mv.visitInsn(IALOAD);
				mv.visitInsn(I2F);
				cmpTypeFlag = DijkstraType.FLOAT;
			}
			else if(expr2Type == DijkstraType.BOOLEAN)
			{
				mv.visitInsn(IALOAD);
				cmpTypeFlag = DijkstraType.INT;
			}
			else
			{
				mv.visitInsn(IALOAD);
				cmpTypeFlag = DijkstraType.INT;
			}
		}
		
		
		switch (binary.getOp()) {
			case DijkstraParser.LT:
				if(cmpTypeFlag == DijkstraType.FLOAT)
				{
					lab1 =  new Label();
					mv.visitInsn(FCMPL);
					mv.visitJumpInsn(IFGE, lab1);
					mv.visitInsn(ICONST_1);		// left < right
					lab2 = new Label();
					mv.visitJumpInsn(GOTO, lab2);
					mv.visitLabel(lab1);
					mv.visitInsn(ICONST_0);		// right >= left
					mv.visitLabel(lab2);
				}
				else if(cmpTypeFlag == DijkstraType.INT)
				{
					lab1 =  new Label();
					mv.visitJumpInsn(IF_ICMPGE, lab1);
					mv.visitInsn(ICONST_1);		// left < right
					lab2 = new Label();
					mv.visitJumpInsn(GOTO, lab2);
					mv.visitLabel(lab1);
					mv.visitInsn(ICONST_0);		// right >= left
					mv.visitLabel(lab2);
				}
				break;
			case DijkstraParser.GT:
				if(cmpTypeFlag == DijkstraType.FLOAT)
				{
					lab1 =  new Label();
					mv.visitInsn(FCMPL);
					mv.visitJumpInsn(IFLE, lab1);
					mv.visitInsn(ICONST_1);		// left < right
					lab2 = new Label();
					mv.visitJumpInsn(GOTO, lab2);
					mv.visitLabel(lab1);
					mv.visitInsn(ICONST_0);		// right >= left
					mv.visitLabel(lab2);
				}
				else if(cmpTypeFlag == DijkstraType.INT)
				{
					lab1 =  new Label();
					mv.visitJumpInsn(IF_ICMPLE, lab1);
					mv.visitInsn(ICONST_1);		// left > right
					lab2 = new Label();
					mv.visitJumpInsn(GOTO, lab2);
					mv.visitLabel(lab1);
					mv.visitInsn(ICONST_0);		// right <= left
					mv.visitLabel(lab2);
				}
				break;
			case DijkstraParser.LTE:
				if(cmpTypeFlag == DijkstraType.FLOAT)
				{
					lab1 =  new Label();
					mv.visitInsn(FCMPL);
					mv.visitJumpInsn(IFGT, lab1);
					mv.visitInsn(ICONST_1);		// left < right
					lab2 = new Label();
					mv.visitJumpInsn(GOTO, lab2);
					mv.visitLabel(lab1);
					mv.visitInsn(ICONST_0);		// right >= left
					mv.visitLabel(lab2);
				}
				else if(cmpTypeFlag == DijkstraType.INT)
				{
					lab1 =  new Label();
					mv.visitJumpInsn(IF_ICMPGT, lab1);
					mv.visitInsn(ICONST_1);		// left < right
					lab2 = new Label();
					mv.visitJumpInsn(GOTO, lab2);
					mv.visitLabel(lab1);
					mv.visitInsn(ICONST_0);		// right >= left
					mv.visitLabel(lab2);
				}
				break;
			case DijkstraParser.GTE:
				if(cmpTypeFlag == DijkstraType.FLOAT)
				{
					lab1 =  new Label();
					mv.visitInsn(FCMPL);
					mv.visitJumpInsn(IFLT, lab1);
					mv.visitInsn(ICONST_1);		// left < right
					lab2 = new Label();
					mv.visitJumpInsn(GOTO, lab2);
					mv.visitLabel(lab1);
					mv.visitInsn(ICONST_0);		// right >= left
					mv.visitLabel(lab2);
				}
				else if(cmpTypeFlag == DijkstraType.INT)
				{
					lab1 =  new Label();
					mv.visitJumpInsn(IF_ICMPLT, lab1);
					mv.visitInsn(ICONST_1);		// left < right
					lab2 = new Label();
					mv.visitJumpInsn(GOTO, lab2);
					mv.visitLabel(lab1);
					mv.visitInsn(ICONST_0);		// right >= left
					mv.visitLabel(lab2);
				}
				break;
			case DijkstraParser.EQ:
				if(cmpTypeFlag == DijkstraType.FLOAT)
				{
					lab1 =  new Label();
					mv.visitInsn(FCMPL);
					mv.visitJumpInsn(IFNE, lab1);
					mv.visitInsn(ICONST_1);		// left < right
					lab2 = new Label();
					mv.visitJumpInsn(GOTO, lab2);
					mv.visitLabel(lab1);
					mv.visitInsn(ICONST_0);		// right >= left
					mv.visitLabel(lab2);
				}
				else if(cmpTypeFlag == DijkstraType.INT)
				{
					lab1 =  new Label();
					mv.visitJumpInsn(IF_ICMPNE, lab1);
					mv.visitInsn(ICONST_1);		// left = right
					lab2 = new Label();
					mv.visitJumpInsn(GOTO, lab2);
					mv.visitLabel(lab1);
					mv.visitInsn(ICONST_0);		// left ~= right
					mv.visitLabel(lab2);
				}
				break;
			case DijkstraParser.NEQ:
				if(cmpTypeFlag == DijkstraType.FLOAT)
				{
					lab1 =  new Label();
					mv.visitInsn(FCMPL);
					mv.visitJumpInsn(IFEQ, lab1);
					mv.visitInsn(ICONST_1);		// left < right
					lab2 = new Label();
					mv.visitJumpInsn(GOTO, lab2);
					mv.visitLabel(lab1);
					mv.visitInsn(ICONST_0);		// right >= left
					mv.visitLabel(lab2);
				}
				else if(cmpTypeFlag == DijkstraType.INT)
				{
					lab1 =  new Label();
					mv.visitJumpInsn(IF_ICMPEQ, lab1);
					mv.visitInsn(ICONST_1);		// left != right
					lab2 = new Label();
					mv.visitJumpInsn(GOTO, lab2);
					mv.visitLabel(lab1);
					mv.visitInsn(ICONST_0);		// left = right
					mv.visitLabel(lab2);
				}
				break;
			case DijkstraParser.OR:
				binary.getExpr1().accept(this);
				lab1 =  new Label();
				mv.visitInsn(ICONST_1);		// AT LEAST ONE TRUE
				mv.visitJumpInsn(IF_ICMPEQ, lab1);
				binary.getExpr2().accept(this);
				lab2 =  new Label();				 
				mv.visitInsn(ICONST_1);		// AT LEAST ONE TRUE
				mv.visitJumpInsn(IF_ICMPEQ, lab1);
				mv.visitInsn(ICONST_0);		// RESULT IS FALSE
				mv.visitJumpInsn(GOTO, lab2);
				mv.visitLabel(lab1);				
				mv.visitInsn(ICONST_1);		// RESULT IS TRUE
				mv.visitLabel(lab2);
//				mv.visitInsn(IADD);
//				lab1 =  new Label();
//				mv.visitInsn(ICONST_1);		// AT LEAST ONE TRUE
//				mv.visitJumpInsn(IF_ICMPLT, lab1);
//				mv.visitInsn(ICONST_1);		// left = right
//				lab2 = new Label();
//				mv.visitJumpInsn(GOTO, lab2);
//				mv.visitLabel(lab1);
//				mv.visitInsn(ICONST_0);		// left ~= right
//				mv.visitLabel(lab2);
				break;
			case DijkstraParser.AND:
				binary.getExpr1().accept(this);
				lab1 =  new Label();
				mv.visitInsn(ICONST_0);		
				mv.visitJumpInsn(IF_ICMPEQ, lab1); // IF IS FALSE
				binary.getExpr2().accept(this);
				lab2 =  new Label();				 
				mv.visitInsn(ICONST_0);		// AT LEAST ONE TRUE				
				mv.visitJumpInsn(IF_ICMPEQ, lab1); // IF IS FALSE
				mv.visitInsn(ICONST_1);		// RESULT IS FALSE
				mv.visitJumpInsn(GOTO, lab2);
				mv.visitLabel(lab1);				
				mv.visitInsn(ICONST_0);		// RESULT IS TRUE
				mv.visitLabel(lab2);
				
//				mv.visitInsn(IADD);
//				lab1 =  new Label();
//				mv.visitInsn(ICONST_2);		// NOT BOTH ARE TRUE
//				mv.visitJumpInsn(IF_ICMPLT, lab1);
//				mv.visitInsn(ICONST_1);		// left = right
//				lab2 = new Label();
//				mv.visitJumpInsn(GOTO, lab2);
//				mv.visitLabel(lab1);
//				mv.visitInsn(ICONST_0);		// left ~= right
//				mv.visitLabel(lab2);
				break;
			case DijkstraParser.PLUS:
				if(type == DijkstraType.FLOAT)
				{
					mv.visitInsn(FADD);
				}
				else
				{
					mv.visitInsn(IADD);
				}
				break;
			case DijkstraParser.MINUS:
				if(type == DijkstraType.FLOAT)
				{
					mv.visitInsn(FSUB);
				}
				else
				{
					mv.visitInsn(ISUB);
				}
				break;
			case DijkstraParser.STAR:
				if(type == DijkstraType.FLOAT)
				{
					mv.visitInsn(FMUL);
				}
				else
				{
					mv.visitInsn(IMUL);
				}
				break;
				
			case DijkstraParser.SLASH:
				if(expr1Type != expr2Type)
				{
					binary.getExpr1().accept(this);
					if(binary.getExpr1().type == INT)
					{
						mv.visitInsn(I2F);
					}
					binary.getExpr2().accept(this);
					if(binary.getExpr2().type == INT)
					{
						mv.visitInsn(I2F);
					}
					mv.visitInsn(FDIV);
				}
				else if(expr1Type == expr2Type && expr1Type == INT)
				{
					binary.getExpr1().accept(this);
					binary.getExpr2().accept(this);
					mv.visitInsn(IDIV);
				}
				else if(expr1Type == expr2Type && expr1Type == DijkstraType.FLOAT)
				{
					binary.getExpr1().accept(this);
					binary.getExpr2().accept(this);
					mv.visitInsn(FDIV);
				}
				break;
				
			case DijkstraParser.MOD:
				mv.visitInsn(IREM);				
				break;
				
			case DijkstraParser.DIV:
				mv.visitInsn(IDIV);
				break;
		}
		
		return null;
	}
	
	/**
	 * Simply load the constant value onto the stack.
	 * @see dijkstra.ast.ASTVisitor#visit(dijkstra.ast.ASTNodeFactory.ConstantNode)
	 */
	public byte[] visit(ConstantNode constant)
	{
		if (constant.type == INT) 
		{
			int i = Integer.parseInt(constant.token.getText());
			mv.visitLdcInsn(i);
		}
		else if (constant.type == DijkstraType.FLOAT)
		{
//			Debug(constant.token.getText());
			float f = Float.parseFloat(constant.token.getText());
			mv.visitLdcInsn(f);
		}
		else {
			// boolean
			if (constant.token.getText().equals("true")) {
				mv.visitInsn(ICONST_1);
			} else {
				mv.visitInsn(ICONST_0);
			}
		}
		return null;
	}
	
	/**
	 * Simply load the constant value onto the stack.
	 * @see dijkstra.ast.ASTVisitor#visit(dijkstra.ast.ASTNodeFactory.ConstantNode)
	 */
	public byte[] visit(ExprListNode exprlistnode)
	{
		visitChildren(exprlistnode);
		return null;
	}
	
	/**
	 * When visiting an ID node, we want the value of the ID on the top of stack
	 * @see dijkstra.ast.ASTVisitor#visit(dijkstra.ast.ASTNodeFactory.IDNode)
	 */
	public byte[] visit(IDNode id)
	{
		Symbol symbol = stm.getSymbol(id.getName());
		Debug("[Codegen]idname = " + id.getName() + " Symbol = " + symbol);
		Debug("[Codegen][Codegen]currenttable = " + stm.getcurrentTableNumber() + " Symbol = " + symbol);
		Debug("[Codegen]parent is 2   "+ stm.getTables().size());
		Debug("[Codegen]symbol glaobal = " + symbol.globalFlag);
		Debug ("[Codegen]symbol.symbol type =  "+ symbol.symbolType);
		int i = 0;
		
		if(symbol.symbolType == symbolTypeEnum.PROPERTY)
		{
//			mv.visitVarInsn(ALOAD, 0);
			if(id.getType() == DijkstraType.FLOAT)
			{
//				mv.visitFieldInsn(GETFIELD, classPackage + "/" + symbol.getClassname(), id.token.getText(), "F");
				mv.visitFieldInsn(GETSTATIC, classPackage + "/" + symbol.getClassname(), id.getName(), "F");
			}
			else if(id.getType() == DijkstraType.BOOLEAN)
			{
//				mv.visitFieldInsn(GETFIELD, classPackage + "/" + symbol.getClassname(), id.token.getText(), "Z");
				mv.visitFieldInsn(GETSTATIC, classPackage + "/" + symbol.getClassname(), id.getName(), "Z");
			}
			
			else
			{
//				mv.visitFieldInsn(GETFIELD, classPackage + "/" + symbol.getClassname(), id.token.getText(), "I");
				mv.visitFieldInsn(GETSTATIC, classPackage + "/" + symbol.getClassname(), id.getName(), "I");
			}
		}
		else if(symbol.globalFlag == true)//global varialbes are stored by the name with postfix 
		{
			String className;
			if(symbol.getClassname() != null)
			{
				className = symbol.getClassname();
			}
			else 
			{
				className = programName;
			}
			
			if(id.getType() == DijkstraType.FLOAT)
			{
//				mv.visitFieldInsn(GETSTATIC, classPackage + "/" + programName, id.getName(), "F");
				mv.visitFieldInsn(GETSTATIC, classPackage + "/" + className, id.getName(), "F");
				
			}
			
			else if(id.getType() == DijkstraType.BOOLEAN)
			{
//				mv.visitFieldInsn(GETSTATIC, classPackage + "/" + programName, id.getName(), "Z");
				mv.visitFieldInsn(GETSTATIC, classPackage + "/" + className, id.getName(), "Z");

			}
			else
			{
//				mv.visitFieldInsn(GETSTATIC, classPackage + "/" + programName, id.getName(), "I");
				mv.visitFieldInsn(GETSTATIC, classPackage + "/" + className, id.getName(), "I");

			}
		}
		else
		{
			if(id.getType() == DijkstraType.FLOAT)
			{
				mv.visitVarInsn(FLOAD, id.getAddress());
				Debug("[CodeGen]Float idgen address:"+id.getAddress()+ " name : " + id.token.getText());
			}
			else
			{
				mv.visitVarInsn(ILOAD, id.getAddress());
				Debug("[CodeGen]Int idgen address:"+id.getAddress()+ " name : " + id.token.getText());
			}
		}
		
		return null;
	}
	
	/**
	 * @param classPackage the classPackage to set
	 */
	public void setClassPackage(String classPackage)
	{
		this.classPackage = classPackage;
	}	
	
	//------------------------ Helper methods -----------------------------
	
	public byte[] getByteCode()
	{
		return cw.toByteArray();
	}
	
	/**
	 * Print out a debugging message. If you're not debugging, just set the debugging
	 * variable.
	 * @param msg
	 */
	private void debug(String msg) 
	{
		if (debug) {
			debug(msg, "    ");
		}
	}
	
	private void debug(String msg, String pad)
	{
		Debug("DBG> " + pad + msg);
	}
}
