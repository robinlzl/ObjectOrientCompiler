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

import static org.junit.Assert.*;
import org.junit.*;
import dijkstra.ast.ASTNodeFactory.*;
import static dijkstra.utility.DijkstraType.*;

/**
 * Description
 * @version Feb 10, 2015
 */
public class ASTVisitorTest
{
	private ASTNode root;
	/**
	 * @throws java.lang.Exception
	 */
//	@Before
	public void setUp() throws Exception
	{
		root = ProgramNode.makeProgramNode("test");
		ASTNode child = DeclarationNode.makeDeclarationNode(null);
		root.addChild(child);
		child = DeclarationNode.makeDeclarationNode(null);
		root.addChild(child);
		child = AssignNode.makeAssignNode();
		root.addChild(child);
	}

//	@Test
	public void test()
	{
		assertEquals(UNDEFINED, root.type);
		root.accept(new TestVisitor());
	}

	class TestVisitor extends ASTVisitor<Void>
	{
		@Override
		public Void visit(ProgramNode node)
		{
			System.out.println("ProgramNode");
			visitChildren(node);
			return null;
		}
		
		@Override
		public Void visit(DeclarationNode node)
		{
			System.out.println("DeclarationNode");
			return null;
		}
		
		public Void visit(AssignNode node)
		{
			System.out.println("AssignNode");
			return null;
		}
	}
}
