// Generated from Dijkstra.g4 by ANTLR 4.5
package dijkstra.lexparse;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link DijkstraParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface DijkstraVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#dijkstraText}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDijkstraText(DijkstraParser.DijkstraTextContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#classdeClaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassdeClaration(DijkstraParser.ClassdeClarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(DijkstraParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(DijkstraParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(DijkstraParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(DijkstraParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#arrayDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayDeclaration(DijkstraParser.ArrayDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#precedureDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrecedureDeclaration(DijkstraParser.PrecedureDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(DijkstraParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#classname}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassname(DijkstraParser.ClassnameContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#propertylist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropertylist(DijkstraParser.PropertylistContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#property}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperty(DijkstraParser.PropertyContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#accessSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccessSpec(DijkstraParser.AccessSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#parameterlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterlist(DijkstraParser.ParameterlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(DijkstraParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(DijkstraParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#typelist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypelist(DijkstraParser.TypelistContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#idlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdlist(DijkstraParser.IdlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(DijkstraParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#assignStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStatement(DijkstraParser.AssignStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#inputStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInputStatement(DijkstraParser.InputStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#outputStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutputStatement(DijkstraParser.OutputStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#iterativeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIterativeStatement(DijkstraParser.IterativeStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#compoundStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundStatement(DijkstraParser.CompoundStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#alternativeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlternativeStatement(DijkstraParser.AlternativeStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(DijkstraParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#procedurecall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedurecall(DijkstraParser.ProcedurecallContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#methodcall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodcall(DijkstraParser.MethodcallContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#compoundbody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundbody(DijkstraParser.CompoundbodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#varlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarlist(DijkstraParser.VarlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(DijkstraParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#guard}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGuard(DijkstraParser.GuardContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(DijkstraParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#functioncall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctioncall(DijkstraParser.FunctioncallContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#arrayaccessor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayaccessor(DijkstraParser.ArrayaccessorContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#constructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructor(DijkstraParser.ConstructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link DijkstraParser#expressionlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionlist(DijkstraParser.ExpressionlistContext ctx);
}