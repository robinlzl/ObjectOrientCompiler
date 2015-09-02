// Generated from Dijkstra.g4 by ANTLR 4.5
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DijkstraParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ASSIGN=1, EQ=2, GT=3, GTE=4, GUARD=5, LBRACE=6, LPAR=7, LT=8, LTE=9, LBRACKET=10, 
		MINUS=11, NEQ=12, PLUS=13, RBRACE=14, RPAR=15, RBRACKET=16, SEMICOLON=17, 
		COLON=18, SLASH=19, STAR=20, MOD=21, DIV=22, TILDE=23, COMMA=24, DOT=25, 
		OR=26, AND=27, BOOLEAN=28, FALSE=29, FI=30, IF=31, INPUT=32, INT=33, FLOAT=34, 
		LOOP=35, PRINT=36, PROGRAM=37, CLASS=38, TRUE=39, DO=40, OD=41, PROC=42, 
		FUN=43, RETURN=44, R=45, W=46, RW=47, ID=48, FLOATCONSTANT=49, INTEGER=50, 
		WS=51, COMMENT=52;
	public static final int
		RULE_dijkstraText = 0, RULE_classdeClaration = 1, RULE_classBody = 2, 
		RULE_program = 3, RULE_declaration = 4, RULE_variableDeclaration = 5, 
		RULE_arrayDeclaration = 6, RULE_precedureDeclaration = 7, RULE_functionDeclaration = 8, 
		RULE_classname = 9, RULE_propertylist = 10, RULE_property = 11, RULE_accessSpec = 12, 
		RULE_parameterlist = 13, RULE_parameter = 14, RULE_type = 15, RULE_typelist = 16, 
		RULE_idlist = 17, RULE_statement = 18, RULE_assignStatement = 19, RULE_inputStatement = 20, 
		RULE_outputStatement = 21, RULE_iterativeStatement = 22, RULE_compoundStatement = 23, 
		RULE_alternativeStatement = 24, RULE_returnStatement = 25, RULE_procedurecall = 26, 
		RULE_methodcall = 27, RULE_compoundbody = 28, RULE_varlist = 29, RULE_var = 30, 
		RULE_guard = 31, RULE_expression = 32, RULE_functioncall = 33, RULE_arrayaccessor = 34, 
		RULE_constructor = 35, RULE_expressionlist = 36;
	public static final String[] ruleNames = {
		"dijkstraText", "classdeClaration", "classBody", "program", "declaration", 
		"variableDeclaration", "arrayDeclaration", "precedureDeclaration", "functionDeclaration", 
		"classname", "propertylist", "property", "accessSpec", "parameterlist", 
		"parameter", "type", "typelist", "idlist", "statement", "assignStatement", 
		"inputStatement", "outputStatement", "iterativeStatement", "compoundStatement", 
		"alternativeStatement", "returnStatement", "procedurecall", "methodcall", 
		"compoundbody", "varlist", "var", "guard", "expression", "functioncall", 
		"arrayaccessor", "constructor", "expressionlist"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'<-'", "'='", "'>'", "'>='", "'::'", "'{'", "'('", "'<'", "'<='", 
		"'['", "'-'", "'~='", "'+'", "'}'", "')'", "']'", "';'", "':'", "'/'", 
		"'*'", "'mod'", "'div'", "'~'", "','", "'.'", "'|'", "'&'", "'boolean'", 
		"'false'", "'fi'", "'if'", "'input'", "'int'", "'float'", "'loop'", "'print'", 
		"'program'", "'class'", "'true'", "'do'", "'od'", "'proc'", "'fun'", "'return'", 
		"'R'", "'W'", "'RW'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "ASSIGN", "EQ", "GT", "GTE", "GUARD", "LBRACE", "LPAR", "LT", "LTE", 
		"LBRACKET", "MINUS", "NEQ", "PLUS", "RBRACE", "RPAR", "RBRACKET", "SEMICOLON", 
		"COLON", "SLASH", "STAR", "MOD", "DIV", "TILDE", "COMMA", "DOT", "OR", 
		"AND", "BOOLEAN", "FALSE", "FI", "IF", "INPUT", "INT", "FLOAT", "LOOP", 
		"PRINT", "PROGRAM", "CLASS", "TRUE", "DO", "OD", "PROC", "FUN", "RETURN", 
		"R", "W", "RW", "ID", "FLOATCONSTANT", "INTEGER", "WS", "COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Dijkstra.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DijkstraParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class DijkstraTextContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(DijkstraParser.EOF, 0); }
		public List<ClassdeClarationContext> classdeClaration() {
			return getRuleContexts(ClassdeClarationContext.class);
		}
		public ClassdeClarationContext classdeClaration(int i) {
			return getRuleContext(ClassdeClarationContext.class,i);
		}
		public ProgramContext program() {
			return getRuleContext(ProgramContext.class,0);
		}
		public DijkstraTextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dijkstraText; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterDijkstraText(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitDijkstraText(this);
		}
	}

	public final DijkstraTextContext dijkstraText() throws RecognitionException {
		DijkstraTextContext _localctx = new DijkstraTextContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_dijkstraText);
		int _la;
		try {
			setState(92);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(75); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(74);
					classdeClaration();
					}
					}
					setState(77); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==CLASS );
				setState(79);
				match(EOF);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				program();
				setState(82);
				match(EOF);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(85); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(84);
					classdeClaration();
					}
					}
					setState(87); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==CLASS );
				setState(89);
				program();
				setState(90);
				match(EOF);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassdeClarationContext extends ParserRuleContext {
		public TerminalNode CLASS() { return getToken(DijkstraParser.CLASS, 0); }
		public TerminalNode ID() { return getToken(DijkstraParser.ID, 0); }
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public TerminalNode LPAR() { return getToken(DijkstraParser.LPAR, 0); }
		public PropertylistContext propertylist() {
			return getRuleContext(PropertylistContext.class,0);
		}
		public TerminalNode RPAR() { return getToken(DijkstraParser.RPAR, 0); }
		public ClassdeClarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classdeClaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterClassdeClaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitClassdeClaration(this);
		}
	}

	public final ClassdeClarationContext classdeClaration() throws RecognitionException {
		ClassdeClarationContext _localctx = new ClassdeClarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_classdeClaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			match(CLASS);
			setState(95);
			match(ID);
			setState(100);
			_la = _input.LA(1);
			if (_la==LPAR) {
				{
				setState(96);
				match(LPAR);
				setState(97);
				propertylist();
				setState(98);
				match(RPAR);
				}
			}

			setState(102);
			classBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassBodyContext extends ParserRuleContext {
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public ClassBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterClassBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitClassBody(this);
		}
	}

	public final ClassBodyContext classBody() throws RecognitionException {
		ClassBodyContext _localctx = new ClassBodyContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_classBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(104);
				declaration();
				}
				}
				setState(107); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << INT) | (1L << FLOAT) | (1L << PROC) | (1L << FUN) | (1L << ID))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode PROGRAM() { return getToken(DijkstraParser.PROGRAM, 0); }
		public TerminalNode ID() { return getToken(DijkstraParser.ID, 0); }
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			match(PROGRAM);
			setState(110);
			match(ID);
			setState(113); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(113);
				switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
				case 1:
					{
					setState(111);
					declaration();
					}
					break;
				case 2:
					{
					setState(112);
					statement();
					}
					break;
				}
				}
				setState(115); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LBRACE) | (1L << BOOLEAN) | (1L << IF) | (1L << INPUT) | (1L << INT) | (1L << FLOAT) | (1L << PRINT) | (1L << DO) | (1L << PROC) | (1L << FUN) | (1L << RETURN) | (1L << ID))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public ArrayDeclarationContext arrayDeclaration() {
			return getRuleContext(ArrayDeclarationContext.class,0);
		}
		public PrecedureDeclarationContext precedureDeclaration() {
			return getRuleContext(PrecedureDeclarationContext.class,0);
		}
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitDeclaration(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_declaration);
		try {
			setState(121);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(117);
				variableDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(118);
				arrayDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(119);
				precedureDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(120);
				functionDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclarationContext extends ParserRuleContext {
		public IdlistContext idlist() {
			return getRuleContext(IdlistContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ClassnameContext classname() {
			return getRuleContext(ClassnameContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(DijkstraParser.SEMICOLON, 0); }
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterVariableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitVariableDeclaration(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_variableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			switch (_input.LA(1)) {
			case BOOLEAN:
			case INT:
			case FLOAT:
				{
				setState(123);
				type();
				}
				break;
			case ID:
				{
				setState(124);
				classname();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(127);
			idlist();
			setState(129);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(128);
				match(SEMICOLON);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayDeclarationContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode LBRACKET() { return getToken(DijkstraParser.LBRACKET, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RBRACKET() { return getToken(DijkstraParser.RBRACKET, 0); }
		public IdlistContext idlist() {
			return getRuleContext(IdlistContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(DijkstraParser.SEMICOLON, 0); }
		public ArrayDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterArrayDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitArrayDeclaration(this);
		}
	}

	public final ArrayDeclarationContext arrayDeclaration() throws RecognitionException {
		ArrayDeclarationContext _localctx = new ArrayDeclarationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_arrayDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			type();
			setState(132);
			match(LBRACKET);
			setState(133);
			expression(0);
			setState(134);
			match(RBRACKET);
			setState(135);
			idlist();
			setState(137);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(136);
				match(SEMICOLON);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrecedureDeclarationContext extends ParserRuleContext {
		public TerminalNode PROC() { return getToken(DijkstraParser.PROC, 0); }
		public TerminalNode ID() { return getToken(DijkstraParser.ID, 0); }
		public TerminalNode LPAR() { return getToken(DijkstraParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(DijkstraParser.RPAR, 0); }
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public ParameterlistContext parameterlist() {
			return getRuleContext(ParameterlistContext.class,0);
		}
		public PrecedureDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_precedureDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterPrecedureDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitPrecedureDeclaration(this);
		}
	}

	public final PrecedureDeclarationContext precedureDeclaration() throws RecognitionException {
		PrecedureDeclarationContext _localctx = new PrecedureDeclarationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_precedureDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			match(PROC);
			setState(140);
			match(ID);
			setState(141);
			match(LPAR);
			setState(143);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << INT) | (1L << FLOAT) | (1L << ID))) != 0)) {
				{
				setState(142);
				parameterlist();
				}
			}

			setState(145);
			match(RPAR);
			setState(146);
			compoundStatement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDeclarationContext extends ParserRuleContext {
		public TerminalNode FUN() { return getToken(DijkstraParser.FUN, 0); }
		public TerminalNode ID() { return getToken(DijkstraParser.ID, 0); }
		public TerminalNode LPAR() { return getToken(DijkstraParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(DijkstraParser.RPAR, 0); }
		public TerminalNode COLON() { return getToken(DijkstraParser.COLON, 0); }
		public TypelistContext typelist() {
			return getRuleContext(TypelistContext.class,0);
		}
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public ParameterlistContext parameterlist() {
			return getRuleContext(ParameterlistContext.class,0);
		}
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterFunctionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitFunctionDeclaration(this);
		}
	}

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_functionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			match(FUN);
			setState(149);
			match(ID);
			setState(150);
			match(LPAR);
			setState(152);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << INT) | (1L << FLOAT) | (1L << ID))) != 0)) {
				{
				setState(151);
				parameterlist();
				}
			}

			setState(154);
			match(RPAR);
			setState(155);
			match(COLON);
			setState(156);
			typelist(0);
			setState(157);
			compoundStatement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassnameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(DijkstraParser.ID, 0); }
		public ClassnameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classname; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterClassname(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitClassname(this);
		}
	}

	public final ClassnameContext classname() throws RecognitionException {
		ClassnameContext _localctx = new ClassnameContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_classname);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertylistContext extends ParserRuleContext {
		public List<PropertyContext> property() {
			return getRuleContexts(PropertyContext.class);
		}
		public PropertyContext property(int i) {
			return getRuleContext(PropertyContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DijkstraParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DijkstraParser.COMMA, i);
		}
		public PropertylistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertylist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterPropertylist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitPropertylist(this);
		}
	}

	public final PropertylistContext propertylist() throws RecognitionException {
		PropertylistContext _localctx = new PropertylistContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_propertylist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			property();
			setState(166);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(162);
				match(COMMA);
				setState(163);
				property();
				}
				}
				setState(168);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(DijkstraParser.ID, 0); }
		public TerminalNode LBRACKET() { return getToken(DijkstraParser.LBRACKET, 0); }
		public AccessSpecContext accessSpec() {
			return getRuleContext(AccessSpecContext.class,0);
		}
		public TerminalNode RBRACKET() { return getToken(DijkstraParser.RBRACKET, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public PropertyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_property; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterProperty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitProperty(this);
		}
	}

	public final PropertyContext property() throws RecognitionException {
		PropertyContext _localctx = new PropertyContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_property);
		try {
			setState(184);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(169);
				match(ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(170);
				match(ID);
				setState(171);
				match(LBRACKET);
				setState(172);
				accessSpec();
				setState(173);
				match(RBRACKET);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(175);
				type();
				setState(176);
				match(ID);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(178);
				type();
				setState(179);
				match(ID);
				setState(180);
				match(LBRACKET);
				setState(181);
				accessSpec();
				setState(182);
				match(RBRACKET);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AccessSpecContext extends ParserRuleContext {
		public TerminalNode R() { return getToken(DijkstraParser.R, 0); }
		public TerminalNode W() { return getToken(DijkstraParser.W, 0); }
		public TerminalNode RW() { return getToken(DijkstraParser.RW, 0); }
		public AccessSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_accessSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterAccessSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitAccessSpec(this);
		}
	}

	public final AccessSpecContext accessSpec() throws RecognitionException {
		AccessSpecContext _localctx = new AccessSpecContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_accessSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(186);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << R) | (1L << W) | (1L << RW))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterlistContext extends ParserRuleContext {
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DijkstraParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DijkstraParser.COMMA, i);
		}
		public ParameterlistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterlist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterParameterlist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitParameterlist(this);
		}
	}

	public final ParameterlistContext parameterlist() throws RecognitionException {
		ParameterlistContext _localctx = new ParameterlistContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_parameterlist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			parameter();
			setState(193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(189);
				match(COMMA);
				setState(190);
				parameter();
				}
				}
				setState(195);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(DijkstraParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ClassnameContext classname() {
			return getRuleContext(ClassnameContext.class,0);
		}
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitParameter(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_parameter);
		try {
			setState(203);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(196);
				match(ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(197);
				type();
				setState(198);
				match(ID);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(200);
				classname();
				setState(201);
				match(ID);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode BOOLEAN() { return getToken(DijkstraParser.BOOLEAN, 0); }
		public TerminalNode INT() { return getToken(DijkstraParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(DijkstraParser.FLOAT, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitType(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << INT) | (1L << FLOAT))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypelistContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypelistContext typelist() {
			return getRuleContext(TypelistContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(DijkstraParser.COMMA, 0); }
		public TypelistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typelist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterTypelist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitTypelist(this);
		}
	}

	public final TypelistContext typelist() throws RecognitionException {
		return typelist(0);
	}

	private TypelistContext typelist(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TypelistContext _localctx = new TypelistContext(_ctx, _parentState);
		TypelistContext _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_typelist, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(208);
			type();
			}
			_ctx.stop = _input.LT(-1);
			setState(215);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TypelistContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_typelist);
					setState(210);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(211);
					match(COMMA);
					setState(212);
					type();
					}
					} 
				}
				setState(217);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class IdlistContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(DijkstraParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(DijkstraParser.ID, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DijkstraParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DijkstraParser.COMMA, i);
		}
		public IdlistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idlist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterIdlist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitIdlist(this);
		}
	}

	public final IdlistContext idlist() throws RecognitionException {
		IdlistContext _localctx = new IdlistContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_idlist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			match(ID);
			setState(223);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(219);
				match(COMMA);
				setState(220);
				match(ID);
				}
				}
				setState(225);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public AssignStatementContext assignStatement() {
			return getRuleContext(AssignStatementContext.class,0);
		}
		public InputStatementContext inputStatement() {
			return getRuleContext(InputStatementContext.class,0);
		}
		public OutputStatementContext outputStatement() {
			return getRuleContext(OutputStatementContext.class,0);
		}
		public AlternativeStatementContext alternativeStatement() {
			return getRuleContext(AlternativeStatementContext.class,0);
		}
		public IterativeStatementContext iterativeStatement() {
			return getRuleContext(IterativeStatementContext.class,0);
		}
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public ProcedurecallContext procedurecall() {
			return getRuleContext(ProcedurecallContext.class,0);
		}
		public MethodcallContext methodcall() {
			return getRuleContext(MethodcallContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_statement);
		try {
			setState(235);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(226);
				assignStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(227);
				inputStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(228);
				outputStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(229);
				alternativeStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(230);
				iterativeStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(231);
				compoundStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(232);
				returnStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(233);
				procedurecall();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(234);
				methodcall();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignStatementContext extends ParserRuleContext {
		public VarlistContext varlist() {
			return getRuleContext(VarlistContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(DijkstraParser.ASSIGN, 0); }
		public ExpressionlistContext expressionlist() {
			return getRuleContext(ExpressionlistContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(DijkstraParser.SEMICOLON, 0); }
		public AssignStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterAssignStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitAssignStatement(this);
		}
	}

	public final AssignStatementContext assignStatement() throws RecognitionException {
		AssignStatementContext _localctx = new AssignStatementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_assignStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			varlist();
			setState(238);
			match(ASSIGN);
			setState(239);
			expressionlist();
			setState(241);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(240);
				match(SEMICOLON);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InputStatementContext extends ParserRuleContext {
		public TerminalNode INPUT() { return getToken(DijkstraParser.INPUT, 0); }
		public IdlistContext idlist() {
			return getRuleContext(IdlistContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(DijkstraParser.SEMICOLON, 0); }
		public InputStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inputStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterInputStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitInputStatement(this);
		}
	}

	public final InputStatementContext inputStatement() throws RecognitionException {
		InputStatementContext _localctx = new InputStatementContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_inputStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(243);
			match(INPUT);
			setState(244);
			idlist();
			setState(246);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(245);
				match(SEMICOLON);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OutputStatementContext extends ParserRuleContext {
		public TerminalNode PRINT() { return getToken(DijkstraParser.PRINT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(DijkstraParser.SEMICOLON, 0); }
		public OutputStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_outputStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterOutputStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitOutputStatement(this);
		}
	}

	public final OutputStatementContext outputStatement() throws RecognitionException {
		OutputStatementContext _localctx = new OutputStatementContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_outputStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(248);
			match(PRINT);
			setState(249);
			expression(0);
			setState(251);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(250);
				match(SEMICOLON);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IterativeStatementContext extends ParserRuleContext {
		public TerminalNode DO() { return getToken(DijkstraParser.DO, 0); }
		public TerminalNode OD() { return getToken(DijkstraParser.OD, 0); }
		public List<GuardContext> guard() {
			return getRuleContexts(GuardContext.class);
		}
		public GuardContext guard(int i) {
			return getRuleContext(GuardContext.class,i);
		}
		public IterativeStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iterativeStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterIterativeStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitIterativeStatement(this);
		}
	}

	public final IterativeStatementContext iterativeStatement() throws RecognitionException {
		IterativeStatementContext _localctx = new IterativeStatementContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_iterativeStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			match(DO);
			setState(255); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(254);
				guard();
				}
				}
				setState(257); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAR) | (1L << MINUS) | (1L << TILDE) | (1L << FALSE) | (1L << TRUE) | (1L << ID) | (1L << FLOATCONSTANT) | (1L << INTEGER))) != 0) );
			setState(259);
			match(OD);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompoundStatementContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(DijkstraParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(DijkstraParser.RBRACE, 0); }
		public List<CompoundbodyContext> compoundbody() {
			return getRuleContexts(CompoundbodyContext.class);
		}
		public CompoundbodyContext compoundbody(int i) {
			return getRuleContext(CompoundbodyContext.class,i);
		}
		public CompoundStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compoundStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterCompoundStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitCompoundStatement(this);
		}
	}

	public final CompoundStatementContext compoundStatement() throws RecognitionException {
		CompoundStatementContext _localctx = new CompoundStatementContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_compoundStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(261);
			match(LBRACE);
			setState(263); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(262);
				compoundbody();
				}
				}
				setState(265); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LBRACE) | (1L << BOOLEAN) | (1L << IF) | (1L << INPUT) | (1L << INT) | (1L << FLOAT) | (1L << PRINT) | (1L << DO) | (1L << RETURN) | (1L << ID))) != 0) );
			setState(267);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AlternativeStatementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(DijkstraParser.IF, 0); }
		public TerminalNode FI() { return getToken(DijkstraParser.FI, 0); }
		public List<GuardContext> guard() {
			return getRuleContexts(GuardContext.class);
		}
		public GuardContext guard(int i) {
			return getRuleContext(GuardContext.class,i);
		}
		public AlternativeStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alternativeStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterAlternativeStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitAlternativeStatement(this);
		}
	}

	public final AlternativeStatementContext alternativeStatement() throws RecognitionException {
		AlternativeStatementContext _localctx = new AlternativeStatementContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_alternativeStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(269);
			match(IF);
			setState(271); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(270);
				guard();
				}
				}
				setState(273); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAR) | (1L << MINUS) | (1L << TILDE) | (1L << FALSE) | (1L << TRUE) | (1L << ID) | (1L << FLOATCONSTANT) | (1L << INTEGER))) != 0) );
			setState(275);
			match(FI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(DijkstraParser.RETURN, 0); }
		public ExpressionlistContext expressionlist() {
			return getRuleContext(ExpressionlistContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(DijkstraParser.SEMICOLON, 0); }
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitReturnStatement(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_returnStatement);
		int _la;
		try {
			setState(283);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(277);
				match(RETURN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(278);
				match(RETURN);
				setState(279);
				expressionlist();
				setState(281);
				_la = _input.LA(1);
				if (_la==SEMICOLON) {
					{
					setState(280);
					match(SEMICOLON);
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProcedurecallContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(DijkstraParser.ID, 0); }
		public TerminalNode LPAR() { return getToken(DijkstraParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(DijkstraParser.RPAR, 0); }
		public ExpressionlistContext expressionlist() {
			return getRuleContext(ExpressionlistContext.class,0);
		}
		public ProcedurecallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_procedurecall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterProcedurecall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitProcedurecall(this);
		}
	}

	public final ProcedurecallContext procedurecall() throws RecognitionException {
		ProcedurecallContext _localctx = new ProcedurecallContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_procedurecall);
		try {
			setState(293);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(285);
				match(ID);
				setState(286);
				match(LPAR);
				setState(287);
				match(RPAR);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(288);
				match(ID);
				setState(289);
				match(LPAR);
				setState(290);
				expressionlist();
				setState(291);
				match(RPAR);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodcallContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(DijkstraParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(DijkstraParser.ID, i);
		}
		public TerminalNode DOT() { return getToken(DijkstraParser.DOT, 0); }
		public TerminalNode LPAR() { return getToken(DijkstraParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(DijkstraParser.RPAR, 0); }
		public ExpressionlistContext expressionlist() {
			return getRuleContext(ExpressionlistContext.class,0);
		}
		public MethodcallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodcall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterMethodcall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitMethodcall(this);
		}
	}

	public final MethodcallContext methodcall() throws RecognitionException {
		MethodcallContext _localctx = new MethodcallContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_methodcall);
		try {
			setState(307);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(295);
				match(ID);
				setState(296);
				match(DOT);
				setState(297);
				match(ID);
				setState(298);
				match(LPAR);
				setState(299);
				match(RPAR);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(300);
				match(ID);
				setState(301);
				match(DOT);
				setState(302);
				match(ID);
				setState(303);
				match(LPAR);
				setState(304);
				expressionlist();
				setState(305);
				match(RPAR);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompoundbodyContext extends ParserRuleContext {
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public ArrayDeclarationContext arrayDeclaration() {
			return getRuleContext(ArrayDeclarationContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public CompoundbodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compoundbody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterCompoundbody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitCompoundbody(this);
		}
	}

	public final CompoundbodyContext compoundbody() throws RecognitionException {
		CompoundbodyContext _localctx = new CompoundbodyContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_compoundbody);
		try {
			setState(312);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(309);
				variableDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(310);
				arrayDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(311);
				statement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarlistContext extends ParserRuleContext {
		public List<VarContext> var() {
			return getRuleContexts(VarContext.class);
		}
		public VarContext var(int i) {
			return getRuleContext(VarContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DijkstraParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DijkstraParser.COMMA, i);
		}
		public VarlistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varlist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterVarlist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitVarlist(this);
		}
	}

	public final VarlistContext varlist() throws RecognitionException {
		VarlistContext _localctx = new VarlistContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_varlist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(314);
			var();
			setState(319);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(315);
				match(COMMA);
				setState(316);
				var();
				}
				}
				setState(321);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(DijkstraParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(DijkstraParser.ID, i);
		}
		public TerminalNode DOT() { return getToken(DijkstraParser.DOT, 0); }
		public ArrayaccessorContext arrayaccessor() {
			return getRuleContext(ArrayaccessorContext.class,0);
		}
		public VarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitVar(this);
		}
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_var);
		try {
			setState(327);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(322);
				match(ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(323);
				match(ID);
				setState(324);
				match(DOT);
				setState(325);
				match(ID);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(326);
				arrayaccessor();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GuardContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode GUARD() { return getToken(DijkstraParser.GUARD, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public GuardContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_guard; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterGuard(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitGuard(this);
		}
	}

	public final GuardContext guard() throws RecognitionException {
		GuardContext _localctx = new GuardContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_guard);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(329);
			expression(0);
			setState(330);
			match(GUARD);
			setState(331);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode TILDE() { return getToken(DijkstraParser.TILDE, 0); }
		public TerminalNode MINUS() { return getToken(DijkstraParser.MINUS, 0); }
		public TerminalNode LPAR() { return getToken(DijkstraParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(DijkstraParser.RPAR, 0); }
		public List<TerminalNode> ID() { return getTokens(DijkstraParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(DijkstraParser.ID, i);
		}
		public TerminalNode INTEGER() { return getToken(DijkstraParser.INTEGER, 0); }
		public TerminalNode FLOATCONSTANT() { return getToken(DijkstraParser.FLOATCONSTANT, 0); }
		public TerminalNode TRUE() { return getToken(DijkstraParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(DijkstraParser.FALSE, 0); }
		public TerminalNode DOT() { return getToken(DijkstraParser.DOT, 0); }
		public FunctioncallContext functioncall() {
			return getRuleContext(FunctioncallContext.class,0);
		}
		public ConstructorContext constructor() {
			return getRuleContext(ConstructorContext.class,0);
		}
		public MethodcallContext methodcall() {
			return getRuleContext(MethodcallContext.class,0);
		}
		public ArrayaccessorContext arrayaccessor() {
			return getRuleContext(ArrayaccessorContext.class,0);
		}
		public TerminalNode STAR() { return getToken(DijkstraParser.STAR, 0); }
		public TerminalNode SLASH() { return getToken(DijkstraParser.SLASH, 0); }
		public TerminalNode MOD() { return getToken(DijkstraParser.MOD, 0); }
		public TerminalNode DIV() { return getToken(DijkstraParser.DIV, 0); }
		public TerminalNode PLUS() { return getToken(DijkstraParser.PLUS, 0); }
		public TerminalNode LT() { return getToken(DijkstraParser.LT, 0); }
		public TerminalNode GT() { return getToken(DijkstraParser.GT, 0); }
		public TerminalNode LTE() { return getToken(DijkstraParser.LTE, 0); }
		public TerminalNode GTE() { return getToken(DijkstraParser.GTE, 0); }
		public TerminalNode EQ() { return getToken(DijkstraParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(DijkstraParser.NEQ, 0); }
		public TerminalNode AND() { return getToken(DijkstraParser.AND, 0); }
		public TerminalNode OR() { return getToken(DijkstraParser.OR, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 64;
		enterRecursionRule(_localctx, 64, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(354);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				{
				setState(334);
				_la = _input.LA(1);
				if ( !(_la==MINUS || _la==TILDE) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(335);
				expression(18);
				}
				break;
			case 2:
				{
				setState(336);
				match(LPAR);
				setState(337);
				expression(0);
				setState(338);
				match(RPAR);
				}
				break;
			case 3:
				{
				setState(340);
				match(ID);
				}
				break;
			case 4:
				{
				setState(341);
				match(INTEGER);
				}
				break;
			case 5:
				{
				setState(342);
				match(FLOATCONSTANT);
				}
				break;
			case 6:
				{
				setState(343);
				match(TRUE);
				}
				break;
			case 7:
				{
				setState(344);
				match(FALSE);
				}
				break;
			case 8:
				{
				{
				setState(345);
				match(TRUE);
				setState(346);
				match(FALSE);
				}
				}
				break;
			case 9:
				{
				setState(347);
				match(ID);
				setState(348);
				match(DOT);
				setState(349);
				match(ID);
				}
				break;
			case 10:
				{
				setState(350);
				functioncall();
				}
				break;
			case 11:
				{
				setState(351);
				constructor();
				}
				break;
			case 12:
				{
				setState(352);
				methodcall();
				}
				break;
			case 13:
				{
				setState(353);
				arrayaccessor();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(376);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(374);
					switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(356);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(357);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SLASH) | (1L << STAR) | (1L << MOD) | (1L << DIV))) != 0)) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(358);
						expression(18);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(359);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(360);
						_la = _input.LA(1);
						if ( !(_la==MINUS || _la==PLUS) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(361);
						expression(17);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(362);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(363);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GT) | (1L << GTE) | (1L << LT) | (1L << LTE))) != 0)) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(364);
						expression(16);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(365);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(366);
						_la = _input.LA(1);
						if ( !(_la==EQ || _la==NEQ) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(367);
						expression(14);
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(368);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(369);
						match(AND);
						setState(370);
						expression(14);
						}
						break;
					case 6:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(371);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(372);
						match(OR);
						setState(373);
						expression(13);
						}
						break;
					}
					} 
				}
				setState(378);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class FunctioncallContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(DijkstraParser.ID, 0); }
		public TerminalNode LPAR() { return getToken(DijkstraParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(DijkstraParser.RPAR, 0); }
		public ExpressionlistContext expressionlist() {
			return getRuleContext(ExpressionlistContext.class,0);
		}
		public FunctioncallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functioncall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterFunctioncall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitFunctioncall(this);
		}
	}

	public final FunctioncallContext functioncall() throws RecognitionException {
		FunctioncallContext _localctx = new FunctioncallContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_functioncall);
		try {
			setState(387);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(379);
				match(ID);
				setState(380);
				match(LPAR);
				setState(381);
				match(RPAR);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(382);
				match(ID);
				setState(383);
				match(LPAR);
				setState(384);
				expressionlist();
				setState(385);
				match(RPAR);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayaccessorContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(DijkstraParser.ID, 0); }
		public TerminalNode LBRACKET() { return getToken(DijkstraParser.LBRACKET, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RBRACKET() { return getToken(DijkstraParser.RBRACKET, 0); }
		public ArrayaccessorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayaccessor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterArrayaccessor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitArrayaccessor(this);
		}
	}

	public final ArrayaccessorContext arrayaccessor() throws RecognitionException {
		ArrayaccessorContext _localctx = new ArrayaccessorContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_arrayaccessor);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(389);
			match(ID);
			setState(390);
			match(LBRACKET);
			setState(391);
			expression(0);
			setState(392);
			match(RBRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructorContext extends ParserRuleContext {
		public ClassnameContext classname() {
			return getRuleContext(ClassnameContext.class,0);
		}
		public TerminalNode LPAR() { return getToken(DijkstraParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(DijkstraParser.RPAR, 0); }
		public ExpressionlistContext expressionlist() {
			return getRuleContext(ExpressionlistContext.class,0);
		}
		public ConstructorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterConstructor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitConstructor(this);
		}
	}

	public final ConstructorContext constructor() throws RecognitionException {
		ConstructorContext _localctx = new ConstructorContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_constructor);
		try {
			setState(403);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(394);
				classname();
				setState(395);
				match(LPAR);
				setState(396);
				match(RPAR);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(398);
				classname();
				setState(399);
				match(LPAR);
				setState(400);
				expressionlist();
				setState(401);
				match(RPAR);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionlistContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DijkstraParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DijkstraParser.COMMA, i);
		}
		public ExpressionlistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionlist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).enterExpressionlist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DijkstraListener ) ((DijkstraListener)listener).exitExpressionlist(this);
		}
	}

	public final ExpressionlistContext expressionlist() throws RecognitionException {
		ExpressionlistContext _localctx = new ExpressionlistContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_expressionlist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(405);
			expression(0);
			setState(410);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(406);
				match(COMMA);
				setState(407);
				expression(0);
				}
				}
				setState(412);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 16:
			return typelist_sempred((TypelistContext)_localctx, predIndex);
		case 32:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean typelist_sempred(TypelistContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 17);
		case 2:
			return precpred(_ctx, 16);
		case 3:
			return precpred(_ctx, 15);
		case 4:
			return precpred(_ctx, 14);
		case 5:
			return precpred(_ctx, 13);
		case 6:
			return precpred(_ctx, 12);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\66\u01a0\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\3\2\6\2N\n\2\r\2\16\2O\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\6\2X\n\2\r\2\16\2Y\3\2\3\2\3\2\5\2_\n\2\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\5\3g\n\3\3\3\3\3\3\4\6\4l\n\4\r\4\16\4m\3\5\3\5\3\5\3\5\6\5t\n"+
		"\5\r\5\16\5u\3\6\3\6\3\6\3\6\5\6|\n\6\3\7\3\7\5\7\u0080\n\7\3\7\3\7\5"+
		"\7\u0084\n\7\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u008c\n\b\3\t\3\t\3\t\3\t\5\t"+
		"\u0092\n\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\5\n\u009b\n\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\13\3\13\3\f\3\f\3\f\7\f\u00a7\n\f\f\f\16\f\u00aa\13\f\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00bb\n\r\3\16\3"+
		"\16\3\17\3\17\3\17\7\17\u00c2\n\17\f\17\16\17\u00c5\13\17\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\5\20\u00ce\n\20\3\21\3\21\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\7\22\u00d8\n\22\f\22\16\22\u00db\13\22\3\23\3\23\3\23\7\23"+
		"\u00e0\n\23\f\23\16\23\u00e3\13\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\5\24\u00ee\n\24\3\25\3\25\3\25\3\25\5\25\u00f4\n\25\3\26\3"+
		"\26\3\26\5\26\u00f9\n\26\3\27\3\27\3\27\5\27\u00fe\n\27\3\30\3\30\6\30"+
		"\u0102\n\30\r\30\16\30\u0103\3\30\3\30\3\31\3\31\6\31\u010a\n\31\r\31"+
		"\16\31\u010b\3\31\3\31\3\32\3\32\6\32\u0112\n\32\r\32\16\32\u0113\3\32"+
		"\3\32\3\33\3\33\3\33\3\33\5\33\u011c\n\33\5\33\u011e\n\33\3\34\3\34\3"+
		"\34\3\34\3\34\3\34\3\34\3\34\5\34\u0128\n\34\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u0136\n\35\3\36\3\36\3\36\5\36"+
		"\u013b\n\36\3\37\3\37\3\37\7\37\u0140\n\37\f\37\16\37\u0143\13\37\3 \3"+
		" \3 \3 \3 \5 \u014a\n \3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\""+
		"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\5\"\u0165\n\"\3\"\3\""+
		"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\7\"\u0179"+
		"\n\"\f\"\16\"\u017c\13\"\3#\3#\3#\3#\3#\3#\3#\3#\5#\u0186\n#\3$\3$\3$"+
		"\3$\3$\3%\3%\3%\3%\3%\3%\3%\3%\3%\5%\u0196\n%\3&\3&\3&\7&\u019b\n&\f&"+
		"\16&\u019e\13&\3&\2\4\"B\'\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$"+
		"&(*,.\60\62\64\668:<>@BDFHJ\2\t\3\2/\61\4\2\36\36#$\4\2\r\r\31\31\3\2"+
		"\25\30\4\2\r\r\17\17\4\2\5\6\n\13\4\2\4\4\16\16\u01bf\2^\3\2\2\2\4`\3"+
		"\2\2\2\6k\3\2\2\2\bo\3\2\2\2\n{\3\2\2\2\f\177\3\2\2\2\16\u0085\3\2\2\2"+
		"\20\u008d\3\2\2\2\22\u0096\3\2\2\2\24\u00a1\3\2\2\2\26\u00a3\3\2\2\2\30"+
		"\u00ba\3\2\2\2\32\u00bc\3\2\2\2\34\u00be\3\2\2\2\36\u00cd\3\2\2\2 \u00cf"+
		"\3\2\2\2\"\u00d1\3\2\2\2$\u00dc\3\2\2\2&\u00ed\3\2\2\2(\u00ef\3\2\2\2"+
		"*\u00f5\3\2\2\2,\u00fa\3\2\2\2.\u00ff\3\2\2\2\60\u0107\3\2\2\2\62\u010f"+
		"\3\2\2\2\64\u011d\3\2\2\2\66\u0127\3\2\2\28\u0135\3\2\2\2:\u013a\3\2\2"+
		"\2<\u013c\3\2\2\2>\u0149\3\2\2\2@\u014b\3\2\2\2B\u0164\3\2\2\2D\u0185"+
		"\3\2\2\2F\u0187\3\2\2\2H\u0195\3\2\2\2J\u0197\3\2\2\2LN\5\4\3\2ML\3\2"+
		"\2\2NO\3\2\2\2OM\3\2\2\2OP\3\2\2\2PQ\3\2\2\2QR\7\2\2\3R_\3\2\2\2ST\5\b"+
		"\5\2TU\7\2\2\3U_\3\2\2\2VX\5\4\3\2WV\3\2\2\2XY\3\2\2\2YW\3\2\2\2YZ\3\2"+
		"\2\2Z[\3\2\2\2[\\\5\b\5\2\\]\7\2\2\3]_\3\2\2\2^M\3\2\2\2^S\3\2\2\2^W\3"+
		"\2\2\2_\3\3\2\2\2`a\7(\2\2af\7\62\2\2bc\7\t\2\2cd\5\26\f\2de\7\21\2\2"+
		"eg\3\2\2\2fb\3\2\2\2fg\3\2\2\2gh\3\2\2\2hi\5\6\4\2i\5\3\2\2\2jl\5\n\6"+
		"\2kj\3\2\2\2lm\3\2\2\2mk\3\2\2\2mn\3\2\2\2n\7\3\2\2\2op\7\'\2\2ps\7\62"+
		"\2\2qt\5\n\6\2rt\5&\24\2sq\3\2\2\2sr\3\2\2\2tu\3\2\2\2us\3\2\2\2uv\3\2"+
		"\2\2v\t\3\2\2\2w|\5\f\7\2x|\5\16\b\2y|\5\20\t\2z|\5\22\n\2{w\3\2\2\2{"+
		"x\3\2\2\2{y\3\2\2\2{z\3\2\2\2|\13\3\2\2\2}\u0080\5 \21\2~\u0080\5\24\13"+
		"\2\177}\3\2\2\2\177~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0083\5$\23\2\u0082"+
		"\u0084\7\23\2\2\u0083\u0082\3\2\2\2\u0083\u0084\3\2\2\2\u0084\r\3\2\2"+
		"\2\u0085\u0086\5 \21\2\u0086\u0087\7\f\2\2\u0087\u0088\5B\"\2\u0088\u0089"+
		"\7\22\2\2\u0089\u008b\5$\23\2\u008a\u008c\7\23\2\2\u008b\u008a\3\2\2\2"+
		"\u008b\u008c\3\2\2\2\u008c\17\3\2\2\2\u008d\u008e\7,\2\2\u008e\u008f\7"+
		"\62\2\2\u008f\u0091\7\t\2\2\u0090\u0092\5\34\17\2\u0091\u0090\3\2\2\2"+
		"\u0091\u0092\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0094\7\21\2\2\u0094\u0095"+
		"\5\60\31\2\u0095\21\3\2\2\2\u0096\u0097\7-\2\2\u0097\u0098\7\62\2\2\u0098"+
		"\u009a\7\t\2\2\u0099\u009b\5\34\17\2\u009a\u0099\3\2\2\2\u009a\u009b\3"+
		"\2\2\2\u009b\u009c\3\2\2\2\u009c\u009d\7\21\2\2\u009d\u009e\7\24\2\2\u009e"+
		"\u009f\5\"\22\2\u009f\u00a0\5\60\31\2\u00a0\23\3\2\2\2\u00a1\u00a2\7\62"+
		"\2\2\u00a2\25\3\2\2\2\u00a3\u00a8\5\30\r\2\u00a4\u00a5\7\32\2\2\u00a5"+
		"\u00a7\5\30\r\2\u00a6\u00a4\3\2\2\2\u00a7\u00aa\3\2\2\2\u00a8\u00a6\3"+
		"\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\27\3\2\2\2\u00aa\u00a8\3\2\2\2\u00ab"+
		"\u00bb\7\62\2\2\u00ac\u00ad\7\62\2\2\u00ad\u00ae\7\f\2\2\u00ae\u00af\5"+
		"\32\16\2\u00af\u00b0\7\22\2\2\u00b0\u00bb\3\2\2\2\u00b1\u00b2\5 \21\2"+
		"\u00b2\u00b3\7\62\2\2\u00b3\u00bb\3\2\2\2\u00b4\u00b5\5 \21\2\u00b5\u00b6"+
		"\7\62\2\2\u00b6\u00b7\7\f\2\2\u00b7\u00b8\5\32\16\2\u00b8\u00b9\7\22\2"+
		"\2\u00b9\u00bb\3\2\2\2\u00ba\u00ab\3\2\2\2\u00ba\u00ac\3\2\2\2\u00ba\u00b1"+
		"\3\2\2\2\u00ba\u00b4\3\2\2\2\u00bb\31\3\2\2\2\u00bc\u00bd\t\2\2\2\u00bd"+
		"\33\3\2\2\2\u00be\u00c3\5\36\20\2\u00bf\u00c0\7\32\2\2\u00c0\u00c2\5\36"+
		"\20\2\u00c1\u00bf\3\2\2\2\u00c2\u00c5\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c3"+
		"\u00c4\3\2\2\2\u00c4\35\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c6\u00ce\7\62\2"+
		"\2\u00c7\u00c8\5 \21\2\u00c8\u00c9\7\62\2\2\u00c9\u00ce\3\2\2\2\u00ca"+
		"\u00cb\5\24\13\2\u00cb\u00cc\7\62\2\2\u00cc\u00ce\3\2\2\2\u00cd\u00c6"+
		"\3\2\2\2\u00cd\u00c7\3\2\2\2\u00cd\u00ca\3\2\2\2\u00ce\37\3\2\2\2\u00cf"+
		"\u00d0\t\3\2\2\u00d0!\3\2\2\2\u00d1\u00d2\b\22\1\2\u00d2\u00d3\5 \21\2"+
		"\u00d3\u00d9\3\2\2\2\u00d4\u00d5\f\3\2\2\u00d5\u00d6\7\32\2\2\u00d6\u00d8"+
		"\5 \21\2\u00d7\u00d4\3\2\2\2\u00d8\u00db\3\2\2\2\u00d9\u00d7\3\2\2\2\u00d9"+
		"\u00da\3\2\2\2\u00da#\3\2\2\2\u00db\u00d9\3\2\2\2\u00dc\u00e1\7\62\2\2"+
		"\u00dd\u00de\7\32\2\2\u00de\u00e0\7\62\2\2\u00df\u00dd\3\2\2\2\u00e0\u00e3"+
		"\3\2\2\2\u00e1\u00df\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2%\3\2\2\2\u00e3"+
		"\u00e1\3\2\2\2\u00e4\u00ee\5(\25\2\u00e5\u00ee\5*\26\2\u00e6\u00ee\5,"+
		"\27\2\u00e7\u00ee\5\62\32\2\u00e8\u00ee\5.\30\2\u00e9\u00ee\5\60\31\2"+
		"\u00ea\u00ee\5\64\33\2\u00eb\u00ee\5\66\34\2\u00ec\u00ee\58\35\2\u00ed"+
		"\u00e4\3\2\2\2\u00ed\u00e5\3\2\2\2\u00ed\u00e6\3\2\2\2\u00ed\u00e7\3\2"+
		"\2\2\u00ed\u00e8\3\2\2\2\u00ed\u00e9\3\2\2\2\u00ed\u00ea\3\2\2\2\u00ed"+
		"\u00eb\3\2\2\2\u00ed\u00ec\3\2\2\2\u00ee\'\3\2\2\2\u00ef\u00f0\5<\37\2"+
		"\u00f0\u00f1\7\3\2\2\u00f1\u00f3\5J&\2\u00f2\u00f4\7\23\2\2\u00f3\u00f2"+
		"\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4)\3\2\2\2\u00f5\u00f6\7\"\2\2\u00f6"+
		"\u00f8\5$\23\2\u00f7\u00f9\7\23\2\2\u00f8\u00f7\3\2\2\2\u00f8\u00f9\3"+
		"\2\2\2\u00f9+\3\2\2\2\u00fa\u00fb\7&\2\2\u00fb\u00fd\5B\"\2\u00fc\u00fe"+
		"\7\23\2\2\u00fd\u00fc\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe-\3\2\2\2\u00ff"+
		"\u0101\7*\2\2\u0100\u0102\5@!\2\u0101\u0100\3\2\2\2\u0102\u0103\3\2\2"+
		"\2\u0103\u0101\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u0105\3\2\2\2\u0105\u0106"+
		"\7+\2\2\u0106/\3\2\2\2\u0107\u0109\7\b\2\2\u0108\u010a\5:\36\2\u0109\u0108"+
		"\3\2\2\2\u010a\u010b\3\2\2\2\u010b\u0109\3\2\2\2\u010b\u010c\3\2\2\2\u010c"+
		"\u010d\3\2\2\2\u010d\u010e\7\20\2\2\u010e\61\3\2\2\2\u010f\u0111\7!\2"+
		"\2\u0110\u0112\5@!\2\u0111\u0110\3\2\2\2\u0112\u0113\3\2\2\2\u0113\u0111"+
		"\3\2\2\2\u0113\u0114\3\2\2\2\u0114\u0115\3\2\2\2\u0115\u0116\7 \2\2\u0116"+
		"\63\3\2\2\2\u0117\u011e\7.\2\2\u0118\u0119\7.\2\2\u0119\u011b\5J&\2\u011a"+
		"\u011c\7\23\2\2\u011b\u011a\3\2\2\2\u011b\u011c\3\2\2\2\u011c\u011e\3"+
		"\2\2\2\u011d\u0117\3\2\2\2\u011d\u0118\3\2\2\2\u011e\65\3\2\2\2\u011f"+
		"\u0120\7\62\2\2\u0120\u0121\7\t\2\2\u0121\u0128\7\21\2\2\u0122\u0123\7"+
		"\62\2\2\u0123\u0124\7\t\2\2\u0124\u0125\5J&\2\u0125\u0126\7\21\2\2\u0126"+
		"\u0128\3\2\2\2\u0127\u011f\3\2\2\2\u0127\u0122\3\2\2\2\u0128\67\3\2\2"+
		"\2\u0129\u012a\7\62\2\2\u012a\u012b\7\33\2\2\u012b\u012c\7\62\2\2\u012c"+
		"\u012d\7\t\2\2\u012d\u0136\7\21\2\2\u012e\u012f\7\62\2\2\u012f\u0130\7"+
		"\33\2\2\u0130\u0131\7\62\2\2\u0131\u0132\7\t\2\2\u0132\u0133\5J&\2\u0133"+
		"\u0134\7\21\2\2\u0134\u0136\3\2\2\2\u0135\u0129\3\2\2\2\u0135\u012e\3"+
		"\2\2\2\u01369\3\2\2\2\u0137\u013b\5\f\7\2\u0138\u013b\5\16\b\2\u0139\u013b"+
		"\5&\24\2\u013a\u0137\3\2\2\2\u013a\u0138\3\2\2\2\u013a\u0139\3\2\2\2\u013b"+
		";\3\2\2\2\u013c\u0141\5> \2\u013d\u013e\7\32\2\2\u013e\u0140\5> \2\u013f"+
		"\u013d\3\2\2\2\u0140\u0143\3\2\2\2\u0141\u013f\3\2\2\2\u0141\u0142\3\2"+
		"\2\2\u0142=\3\2\2\2\u0143\u0141\3\2\2\2\u0144\u014a\7\62\2\2\u0145\u0146"+
		"\7\62\2\2\u0146\u0147\7\33\2\2\u0147\u014a\7\62\2\2\u0148\u014a\5F$\2"+
		"\u0149\u0144\3\2\2\2\u0149\u0145\3\2\2\2\u0149\u0148\3\2\2\2\u014a?\3"+
		"\2\2\2\u014b\u014c\5B\"\2\u014c\u014d\7\7\2\2\u014d\u014e\5&\24\2\u014e"+
		"A\3\2\2\2\u014f\u0150\b\"\1\2\u0150\u0151\t\4\2\2\u0151\u0165\5B\"\24"+
		"\u0152\u0153\7\t\2\2\u0153\u0154\5B\"\2\u0154\u0155\7\21\2\2\u0155\u0165"+
		"\3\2\2\2\u0156\u0165\7\62\2\2\u0157\u0165\7\64\2\2\u0158\u0165\7\63\2"+
		"\2\u0159\u0165\7)\2\2\u015a\u0165\7\37\2\2\u015b\u015c\7)\2\2\u015c\u0165"+
		"\7\37\2\2\u015d\u015e\7\62\2\2\u015e\u015f\7\33\2\2\u015f\u0165\7\62\2"+
		"\2\u0160\u0165\5D#\2\u0161\u0165\5H%\2\u0162\u0165\58\35\2\u0163\u0165"+
		"\5F$\2\u0164\u014f\3\2\2\2\u0164\u0152\3\2\2\2\u0164\u0156\3\2\2\2\u0164"+
		"\u0157\3\2\2\2\u0164\u0158\3\2\2\2\u0164\u0159\3\2\2\2\u0164\u015a\3\2"+
		"\2\2\u0164\u015b\3\2\2\2\u0164\u015d\3\2\2\2\u0164\u0160\3\2\2\2\u0164"+
		"\u0161\3\2\2\2\u0164\u0162\3\2\2\2\u0164\u0163\3\2\2\2\u0165\u017a\3\2"+
		"\2\2\u0166\u0167\f\23\2\2\u0167\u0168\t\5\2\2\u0168\u0179\5B\"\24\u0169"+
		"\u016a\f\22\2\2\u016a\u016b\t\6\2\2\u016b\u0179\5B\"\23\u016c\u016d\f"+
		"\21\2\2\u016d\u016e\t\7\2\2\u016e\u0179\5B\"\22\u016f\u0170\f\20\2\2\u0170"+
		"\u0171\t\b\2\2\u0171\u0179\5B\"\20\u0172\u0173\f\17\2\2\u0173\u0174\7"+
		"\35\2\2\u0174\u0179\5B\"\20\u0175\u0176\f\16\2\2\u0176\u0177\7\34\2\2"+
		"\u0177\u0179\5B\"\17\u0178\u0166\3\2\2\2\u0178\u0169\3\2\2\2\u0178\u016c"+
		"\3\2\2\2\u0178\u016f\3\2\2\2\u0178\u0172\3\2\2\2\u0178\u0175\3\2\2\2\u0179"+
		"\u017c\3\2\2\2\u017a\u0178\3\2\2\2\u017a\u017b\3\2\2\2\u017bC\3\2\2\2"+
		"\u017c\u017a\3\2\2\2\u017d\u017e\7\62\2\2\u017e\u017f\7\t\2\2\u017f\u0186"+
		"\7\21\2\2\u0180\u0181\7\62\2\2\u0181\u0182\7\t\2\2\u0182\u0183\5J&\2\u0183"+
		"\u0184\7\21\2\2\u0184\u0186\3\2\2\2\u0185\u017d\3\2\2\2\u0185\u0180\3"+
		"\2\2\2\u0186E\3\2\2\2\u0187\u0188\7\62\2\2\u0188\u0189\7\f\2\2\u0189\u018a"+
		"\5B\"\2\u018a\u018b\7\22\2\2\u018bG\3\2\2\2\u018c\u018d\5\24\13\2\u018d"+
		"\u018e\7\t\2\2\u018e\u018f\7\21\2\2\u018f\u0196\3\2\2\2\u0190\u0191\5"+
		"\24\13\2\u0191\u0192\7\t\2\2\u0192\u0193\5J&\2\u0193\u0194\7\21\2\2\u0194"+
		"\u0196\3\2\2\2\u0195\u018c\3\2\2\2\u0195\u0190\3\2\2\2\u0196I\3\2\2\2"+
		"\u0197\u019c\5B\"\2\u0198\u0199\7\32\2\2\u0199\u019b\5B\"\2\u019a\u0198"+
		"\3\2\2\2\u019b\u019e\3\2\2\2\u019c\u019a\3\2\2\2\u019c\u019d\3\2\2\2\u019d"+
		"K\3\2\2\2\u019e\u019c\3\2\2\2)OY^fmsu{\177\u0083\u008b\u0091\u009a\u00a8"+
		"\u00ba\u00c3\u00cd\u00d9\u00e1\u00ed\u00f3\u00f8\u00fd\u0103\u010b\u0113"+
		"\u011b\u011d\u0127\u0135\u013a\u0141\u0149\u0164\u0178\u017a\u0185\u0195"+
		"\u019c";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}