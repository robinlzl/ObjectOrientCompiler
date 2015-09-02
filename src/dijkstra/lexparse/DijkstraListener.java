// Generated from Dijkstra.g4 by ANTLR 4.5
package dijkstra.lexparse;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DijkstraParser}.
 */
public interface DijkstraListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#dijkstraText}.
	 * @param ctx the parse tree
	 */
	void enterDijkstraText(DijkstraParser.DijkstraTextContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#dijkstraText}.
	 * @param ctx the parse tree
	 */
	void exitDijkstraText(DijkstraParser.DijkstraTextContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#classdeClaration}.
	 * @param ctx the parse tree
	 */
	void enterClassdeClaration(DijkstraParser.ClassdeClarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#classdeClaration}.
	 * @param ctx the parse tree
	 */
	void exitClassdeClaration(DijkstraParser.ClassdeClarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(DijkstraParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(DijkstraParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(DijkstraParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(DijkstraParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(DijkstraParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(DijkstraParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(DijkstraParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(DijkstraParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#arrayDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterArrayDeclaration(DijkstraParser.ArrayDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#arrayDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitArrayDeclaration(DijkstraParser.ArrayDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#precedureDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterPrecedureDeclaration(DijkstraParser.PrecedureDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#precedureDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitPrecedureDeclaration(DijkstraParser.PrecedureDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(DijkstraParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(DijkstraParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#classname}.
	 * @param ctx the parse tree
	 */
	void enterClassname(DijkstraParser.ClassnameContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#classname}.
	 * @param ctx the parse tree
	 */
	void exitClassname(DijkstraParser.ClassnameContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#propertylist}.
	 * @param ctx the parse tree
	 */
	void enterPropertylist(DijkstraParser.PropertylistContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#propertylist}.
	 * @param ctx the parse tree
	 */
	void exitPropertylist(DijkstraParser.PropertylistContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#property}.
	 * @param ctx the parse tree
	 */
	void enterProperty(DijkstraParser.PropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#property}.
	 * @param ctx the parse tree
	 */
	void exitProperty(DijkstraParser.PropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#accessSpec}.
	 * @param ctx the parse tree
	 */
	void enterAccessSpec(DijkstraParser.AccessSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#accessSpec}.
	 * @param ctx the parse tree
	 */
	void exitAccessSpec(DijkstraParser.AccessSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#parameterlist}.
	 * @param ctx the parse tree
	 */
	void enterParameterlist(DijkstraParser.ParameterlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#parameterlist}.
	 * @param ctx the parse tree
	 */
	void exitParameterlist(DijkstraParser.ParameterlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(DijkstraParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(DijkstraParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(DijkstraParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(DijkstraParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#typelist}.
	 * @param ctx the parse tree
	 */
	void enterTypelist(DijkstraParser.TypelistContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#typelist}.
	 * @param ctx the parse tree
	 */
	void exitTypelist(DijkstraParser.TypelistContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#idlist}.
	 * @param ctx the parse tree
	 */
	void enterIdlist(DijkstraParser.IdlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#idlist}.
	 * @param ctx the parse tree
	 */
	void exitIdlist(DijkstraParser.IdlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(DijkstraParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(DijkstraParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#assignStatement}.
	 * @param ctx the parse tree
	 */
	void enterAssignStatement(DijkstraParser.AssignStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#assignStatement}.
	 * @param ctx the parse tree
	 */
	void exitAssignStatement(DijkstraParser.AssignStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#inputStatement}.
	 * @param ctx the parse tree
	 */
	void enterInputStatement(DijkstraParser.InputStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#inputStatement}.
	 * @param ctx the parse tree
	 */
	void exitInputStatement(DijkstraParser.InputStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#outputStatement}.
	 * @param ctx the parse tree
	 */
	void enterOutputStatement(DijkstraParser.OutputStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#outputStatement}.
	 * @param ctx the parse tree
	 */
	void exitOutputStatement(DijkstraParser.OutputStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#iterativeStatement}.
	 * @param ctx the parse tree
	 */
	void enterIterativeStatement(DijkstraParser.IterativeStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#iterativeStatement}.
	 * @param ctx the parse tree
	 */
	void exitIterativeStatement(DijkstraParser.IterativeStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void enterCompoundStatement(DijkstraParser.CompoundStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void exitCompoundStatement(DijkstraParser.CompoundStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#alternativeStatement}.
	 * @param ctx the parse tree
	 */
	void enterAlternativeStatement(DijkstraParser.AlternativeStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#alternativeStatement}.
	 * @param ctx the parse tree
	 */
	void exitAlternativeStatement(DijkstraParser.AlternativeStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(DijkstraParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(DijkstraParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#procedurecall}.
	 * @param ctx the parse tree
	 */
	void enterProcedurecall(DijkstraParser.ProcedurecallContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#procedurecall}.
	 * @param ctx the parse tree
	 */
	void exitProcedurecall(DijkstraParser.ProcedurecallContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#methodcall}.
	 * @param ctx the parse tree
	 */
	void enterMethodcall(DijkstraParser.MethodcallContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#methodcall}.
	 * @param ctx the parse tree
	 */
	void exitMethodcall(DijkstraParser.MethodcallContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#compoundbody}.
	 * @param ctx the parse tree
	 */
	void enterCompoundbody(DijkstraParser.CompoundbodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#compoundbody}.
	 * @param ctx the parse tree
	 */
	void exitCompoundbody(DijkstraParser.CompoundbodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#varlist}.
	 * @param ctx the parse tree
	 */
	void enterVarlist(DijkstraParser.VarlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#varlist}.
	 * @param ctx the parse tree
	 */
	void exitVarlist(DijkstraParser.VarlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(DijkstraParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(DijkstraParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#guard}.
	 * @param ctx the parse tree
	 */
	void enterGuard(DijkstraParser.GuardContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#guard}.
	 * @param ctx the parse tree
	 */
	void exitGuard(DijkstraParser.GuardContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(DijkstraParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(DijkstraParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#functioncall}.
	 * @param ctx the parse tree
	 */
	void enterFunctioncall(DijkstraParser.FunctioncallContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#functioncall}.
	 * @param ctx the parse tree
	 */
	void exitFunctioncall(DijkstraParser.FunctioncallContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#arrayaccessor}.
	 * @param ctx the parse tree
	 */
	void enterArrayaccessor(DijkstraParser.ArrayaccessorContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#arrayaccessor}.
	 * @param ctx the parse tree
	 */
	void exitArrayaccessor(DijkstraParser.ArrayaccessorContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#constructor}.
	 * @param ctx the parse tree
	 */
	void enterConstructor(DijkstraParser.ConstructorContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#constructor}.
	 * @param ctx the parse tree
	 */
	void exitConstructor(DijkstraParser.ConstructorContext ctx);
	/**
	 * Enter a parse tree produced by {@link DijkstraParser#expressionlist}.
	 * @param ctx the parse tree
	 */
	void enterExpressionlist(DijkstraParser.ExpressionlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link DijkstraParser#expressionlist}.
	 * @param ctx the parse tree
	 */
	void exitExpressionlist(DijkstraParser.ExpressionlistContext ctx);
}