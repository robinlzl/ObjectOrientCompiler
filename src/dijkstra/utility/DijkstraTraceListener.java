/*******************************************************************************
 * Copyright (c) 2015 Gary F. Pollice
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Used in CS4533/CS544 at Worcester Polytechnic Institute
 *******************************************************************************/

package dijkstra.utility;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import dijkstra.lexparse.*;
import static dijkstra.lexparse.DijkstraParser.*;

/**
 * Example listener for the Toy Dijkstra grammar. This prints out a nicer trace than using
 * setTrace() on the parser.
 * 
 * @version Feb 5, 2015
 */
public class DijkstraTraceListener extends DijkstraBaseListener
{
	private final Recognizer parser;
	private final String indent = "  ";
	private int indentLevel;

	public DijkstraTraceListener(Recognizer parser)
	{
		super();
		this.parser = parser;
		indentLevel = 0;
	}

	@Override
	public void enterDijkstraText(DijkstraParser.DijkstraTextContext ctx)
	{
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void exitDijkstraText(DijkstraParser.DijkstraTextContext ctx)
	{
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void enterProgram(DijkstraParser.ProgramContext ctx)
	{
		
		doPrint(ruleName(ctx) + " '"+ ctx.ID().getText() + "'");
		indentLevel++;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void enterDeclaration(DijkstraParser.DeclarationContext ctx)
	{
//		doPrint(ruleName(ctx) + " " + ctx.type().getText() + " '" 
//				+ ctx.ID().getText() + "'");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void exitDeclaration(DijkstraParser.DeclarationContext ctx)
	{
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void enterType(DijkstraParser.TypeContext ctx)
	{
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void exitType(DijkstraParser.TypeContext ctx)
	{
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void enterStatement(DijkstraParser.StatementContext ctx)
	{
		doPrint(ruleName(ctx));
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void exitStatement(DijkstraParser.StatementContext ctx)
	{
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * ID ASSIGN expression SEMICOLON?
	 * </p>
	 */
	@Override
	public void enterAssignStatement(DijkstraParser.AssignStatementContext ctx)
	{
		System.out.println(indent() + tokenString(ctx.getChild(1)));
		indentLevel++;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void exitAssignStatement(DijkstraParser.AssignStatementContext ctx)
	{
		indentLevel--;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * INPUT ID SEMICOLON?
	 * </p>
	 */
	@Override
	public void enterInputStatement(DijkstraParser.InputStatementContext ctx)
	{
		System.out.println(indent() + ctx.getChild(0).getText() + " " + tokenString(ctx.getChild(1)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void exitInputStatement(DijkstraParser.InputStatementContext ctx)
	{
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * PRINT expression SEMICOLON?
	 * </p>
	 */
	@Override
	public void enterOutputStatement(DijkstraParser.OutputStatementContext ctx)
	{
		System.out.println(indent() + ctx.getChild(0).getText());
		indentLevel++;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void exitOutputStatement(DijkstraParser.OutputStatementContext ctx)
	{
		indentLevel--;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * LOOP expression statement ;
	 * </p>
	 */
	@Override
	public void enterIterativeStatement(DijkstraParser.IterativeStatementContext ctx)
	{
		System.out.println(indent() + ctx.getChild(0).getText());
		indentLevel++;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void exitIterativeStatement(DijkstraParser.IterativeStatementContext ctx)
	{
		indentLevel--;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void enterCompoundStatement(DijkstraParser.CompoundStatementContext ctx)
	{
		System.out.println(indent() + "{ }");
		indentLevel++;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void exitCompoundStatement(DijkstraParser.CompoundStatementContext ctx)
	{
		indentLevel--;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * IF guard+ FI
	 * </p>
	 */
	@Override
	public void enterAlternativeStatement(
			DijkstraParser.AlternativeStatementContext ctx)
	{
		doPrint(indent() + ctx.getChild(0).getText());
		indentLevel++;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void exitAlternativeStatement(
			DijkstraParser.AlternativeStatementContext ctx)
	{
		indentLevel--;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * expression GUARD statement
	 * </p>
	 */
	@Override
	public void enterGuard(DijkstraParser.GuardContext ctx)
	{
		doPrint(indent() + tokenString(ctx.getChild(1)));
		indentLevel++;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void exitGuard(DijkstraParser.GuardContext ctx)
	{
		indentLevel--;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void enterExpression(DijkstraParser.ExpressionContext ctx)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(ruleName(ctx) + " ");
		switch (ctx.getChildCount()) {
			case 1: // terminal node
			case 2: // unaryOperator expression
				sb.append(tokenString(ctx.getChild(0)));
				break;
			case 3: // Binary operator or parenthesized expression
				if (ctx.getChild(0) instanceof  TerminalNode) {
					sb.append(" ()");
				} else {
					sb.append(tokenString(ctx.getChild(1)));
				}
		}
		System.out.println(indent() + sb.toString());
		indentLevel++;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void exitExpression(DijkstraParser.ExpressionContext ctx)
	{
		indentLevel--;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void enterEveryRule(ParserRuleContext ctx)
	{
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void exitEveryRule(ParserRuleContext ctx)
	{
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void visitTerminal(TerminalNode node)
	{
//		Token t = node.getSymbol();
//		doPrint(parser.getTokenNames()[t.getType()] + "'" + t.getText() + "'");
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation does nothing.
	 * </p>
	 */
	@Override
	public void visitErrorNode(ErrorNode node)
	{
	}

	public void doPrint(ParserRuleContext ctx)
	{
		System.out.println(indent() + parser.getRuleNames()[ctx.getRuleIndex()]
				+ " '" + ctx.getStart().getText() + "'");
	}
	
	public void doPrint(String s)
	{
		System.out.println(indent() + s);
	}

	private String indent()
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indentLevel; i++) {
			sb.append(indent);
		}
		return sb.toString();
	}
	
	private String ruleName(ParserRuleContext ctx)
	{
		return "[" + parser.getRuleNames()[ctx.getRuleIndex()] + "]";
	}
	
	private String tokenString(ParseTree pt)
	{
		StringBuilder sb = new StringBuilder();
		Token t = ((TerminalNode)pt).getSymbol();
		if (t.getType() == INTEGER | t.getType() == ID) {
			sb.append(parser.getTokenNames()[t.getType()] + " '" + t.getText() + "'");
		} else {
			sb.append(parser.getTokenNames()[t.getType()]);
		}
		return sb.toString();
	}
}
