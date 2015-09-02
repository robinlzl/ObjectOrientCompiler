

package dijkstra.utility;


import static org.junit.Assert.*;

import org.antlr.v4.runtime.*;
import org.junit.*;

import dijkstra.ast.*;
import dijkstra.lexparse.DijkstraParser;
import dijkstra.semantic.TypeChecker;
import dijkstra.symbol.*;


public class TypeCheckRunner
{

	public DijkstraParser parser;
	public ParserRuleContext tree;
	public ASTCreator creator;
	public ASTNode ast;
	public SymbolCreator symbolCreator;
	public SymbolTableManager stm;
	
	void check(String text)
	{
	
		parser = DijkstraFactory.makeParser(new ANTLRInputStream(text));
		tree = parser.dijkstraText();
		creator = new ASTCreator();
		ast = tree.accept(creator);
		symbolCreator = new SymbolCreator();
		ast.accept(symbolCreator);
		TypeChecker checker = new TypeChecker();
		
		do {
			ast.accept(checker);
		} while (checker.checkAgain());
		
	}
}
