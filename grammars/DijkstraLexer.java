// Generated from Dijkstra.g4 by ANTLR 4.5
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DijkstraLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"ASSIGN", "EQ", "GT", "GTE", "GUARD", "LBRACE", "LPAR", "LT", "LTE", "LBRACKET", 
		"MINUS", "NEQ", "PLUS", "RBRACE", "RPAR", "RBRACKET", "SEMICOLON", "COLON", 
		"SLASH", "STAR", "MOD", "DIV", "TILDE", "COMMA", "DOT", "OR", "AND", "BOOLEAN", 
		"FALSE", "FI", "IF", "INPUT", "INT", "FLOAT", "LOOP", "PRINT", "PROGRAM", 
		"CLASS", "TRUE", "DO", "OD", "PROC", "FUN", "RETURN", "R", "W", "RW", 
		"ID", "FLOATCONSTANT", "INTEGER", "WS", "COMMENT", "LETTER", "DIGIT"
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


	public DijkstraLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Dijkstra.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\66\u013d\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3"+
		"\5\3\5\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f\3"+
		"\f\3\r\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23"+
		"\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\30"+
		"\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3 \3 \3 "+
		"\3!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$\3"+
		"%\3%\3%\3%\3%\3%\3&\3&\3&\3&\3&\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3\'\3(\3"+
		"(\3(\3(\3(\3)\3)\3)\3*\3*\3*\3+\3+\3+\3+\3+\3,\3,\3,\3,\3-\3-\3-\3-\3"+
		"-\3-\3-\3.\3.\3/\3/\3\60\3\60\3\60\3\61\3\61\3\61\3\61\7\61\u0112\n\61"+
		"\f\61\16\61\u0115\13\61\3\62\6\62\u0118\n\62\r\62\16\62\u0119\3\62\3\62"+
		"\6\62\u011e\n\62\r\62\16\62\u011f\3\63\6\63\u0123\n\63\r\63\16\63\u0124"+
		"\3\64\6\64\u0128\n\64\r\64\16\64\u0129\3\64\3\64\3\65\3\65\7\65\u0130"+
		"\n\65\f\65\16\65\u0133\13\65\3\65\5\65\u0136\n\65\3\65\3\65\3\66\3\66"+
		"\3\67\3\67\3\u0131\28\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27"+
		"\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33"+
		"\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63"+
		"e\64g\65i\66k\2m\2\3\2\7\4\2AAaa\5\2\13\f\17\17\"\"\3\3\f\f\4\2C\\c|\3"+
		"\2\62;\u0142\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2"+
		"\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2"+
		"\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2"+
		"\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2"+
		"\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3"+
		"\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2"+
		"\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2"+
		"S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3"+
		"\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\3o\3\2\2"+
		"\2\5r\3\2\2\2\7t\3\2\2\2\tv\3\2\2\2\13y\3\2\2\2\r|\3\2\2\2\17~\3\2\2\2"+
		"\21\u0080\3\2\2\2\23\u0082\3\2\2\2\25\u0085\3\2\2\2\27\u0087\3\2\2\2\31"+
		"\u0089\3\2\2\2\33\u008c\3\2\2\2\35\u008e\3\2\2\2\37\u0090\3\2\2\2!\u0092"+
		"\3\2\2\2#\u0094\3\2\2\2%\u0096\3\2\2\2\'\u0098\3\2\2\2)\u009a\3\2\2\2"+
		"+\u009c\3\2\2\2-\u00a0\3\2\2\2/\u00a4\3\2\2\2\61\u00a6\3\2\2\2\63\u00a8"+
		"\3\2\2\2\65\u00aa\3\2\2\2\67\u00ac\3\2\2\29\u00ae\3\2\2\2;\u00b6\3\2\2"+
		"\2=\u00bc\3\2\2\2?\u00bf\3\2\2\2A\u00c2\3\2\2\2C\u00c8\3\2\2\2E\u00cc"+
		"\3\2\2\2G\u00d2\3\2\2\2I\u00d7\3\2\2\2K\u00dd\3\2\2\2M\u00e5\3\2\2\2O"+
		"\u00eb\3\2\2\2Q\u00f0\3\2\2\2S\u00f3\3\2\2\2U\u00f6\3\2\2\2W\u00fb\3\2"+
		"\2\2Y\u00ff\3\2\2\2[\u0106\3\2\2\2]\u0108\3\2\2\2_\u010a\3\2\2\2a\u010d"+
		"\3\2\2\2c\u0117\3\2\2\2e\u0122\3\2\2\2g\u0127\3\2\2\2i\u012d\3\2\2\2k"+
		"\u0139\3\2\2\2m\u013b\3\2\2\2op\7>\2\2pq\7/\2\2q\4\3\2\2\2rs\7?\2\2s\6"+
		"\3\2\2\2tu\7@\2\2u\b\3\2\2\2vw\7@\2\2wx\7?\2\2x\n\3\2\2\2yz\7<\2\2z{\7"+
		"<\2\2{\f\3\2\2\2|}\7}\2\2}\16\3\2\2\2~\177\7*\2\2\177\20\3\2\2\2\u0080"+
		"\u0081\7>\2\2\u0081\22\3\2\2\2\u0082\u0083\7>\2\2\u0083\u0084\7?\2\2\u0084"+
		"\24\3\2\2\2\u0085\u0086\7]\2\2\u0086\26\3\2\2\2\u0087\u0088\7/\2\2\u0088"+
		"\30\3\2\2\2\u0089\u008a\7\u0080\2\2\u008a\u008b\7?\2\2\u008b\32\3\2\2"+
		"\2\u008c\u008d\7-\2\2\u008d\34\3\2\2\2\u008e\u008f\7\177\2\2\u008f\36"+
		"\3\2\2\2\u0090\u0091\7+\2\2\u0091 \3\2\2\2\u0092\u0093\7_\2\2\u0093\""+
		"\3\2\2\2\u0094\u0095\7=\2\2\u0095$\3\2\2\2\u0096\u0097\7<\2\2\u0097&\3"+
		"\2\2\2\u0098\u0099\7\61\2\2\u0099(\3\2\2\2\u009a\u009b\7,\2\2\u009b*\3"+
		"\2\2\2\u009c\u009d\7o\2\2\u009d\u009e\7q\2\2\u009e\u009f\7f\2\2\u009f"+
		",\3\2\2\2\u00a0\u00a1\7f\2\2\u00a1\u00a2\7k\2\2\u00a2\u00a3\7x\2\2\u00a3"+
		".\3\2\2\2\u00a4\u00a5\7\u0080\2\2\u00a5\60\3\2\2\2\u00a6\u00a7\7.\2\2"+
		"\u00a7\62\3\2\2\2\u00a8\u00a9\7\60\2\2\u00a9\64\3\2\2\2\u00aa\u00ab\7"+
		"~\2\2\u00ab\66\3\2\2\2\u00ac\u00ad\7(\2\2\u00ad8\3\2\2\2\u00ae\u00af\7"+
		"d\2\2\u00af\u00b0\7q\2\2\u00b0\u00b1\7q\2\2\u00b1\u00b2\7n\2\2\u00b2\u00b3"+
		"\7g\2\2\u00b3\u00b4\7c\2\2\u00b4\u00b5\7p\2\2\u00b5:\3\2\2\2\u00b6\u00b7"+
		"\7h\2\2\u00b7\u00b8\7c\2\2\u00b8\u00b9\7n\2\2\u00b9\u00ba\7u\2\2\u00ba"+
		"\u00bb\7g\2\2\u00bb<\3\2\2\2\u00bc\u00bd\7h\2\2\u00bd\u00be\7k\2\2\u00be"+
		">\3\2\2\2\u00bf\u00c0\7k\2\2\u00c0\u00c1\7h\2\2\u00c1@\3\2\2\2\u00c2\u00c3"+
		"\7k\2\2\u00c3\u00c4\7p\2\2\u00c4\u00c5\7r\2\2\u00c5\u00c6\7w\2\2\u00c6"+
		"\u00c7\7v\2\2\u00c7B\3\2\2\2\u00c8\u00c9\7k\2\2\u00c9\u00ca\7p\2\2\u00ca"+
		"\u00cb\7v\2\2\u00cbD\3\2\2\2\u00cc\u00cd\7h\2\2\u00cd\u00ce\7n\2\2\u00ce"+
		"\u00cf\7q\2\2\u00cf\u00d0\7c\2\2\u00d0\u00d1\7v\2\2\u00d1F\3\2\2\2\u00d2"+
		"\u00d3\7n\2\2\u00d3\u00d4\7q\2\2\u00d4\u00d5\7q\2\2\u00d5\u00d6\7r\2\2"+
		"\u00d6H\3\2\2\2\u00d7\u00d8\7r\2\2\u00d8\u00d9\7t\2\2\u00d9\u00da\7k\2"+
		"\2\u00da\u00db\7p\2\2\u00db\u00dc\7v\2\2\u00dcJ\3\2\2\2\u00dd\u00de\7"+
		"r\2\2\u00de\u00df\7t\2\2\u00df\u00e0\7q\2\2\u00e0\u00e1\7i\2\2\u00e1\u00e2"+
		"\7t\2\2\u00e2\u00e3\7c\2\2\u00e3\u00e4\7o\2\2\u00e4L\3\2\2\2\u00e5\u00e6"+
		"\7e\2\2\u00e6\u00e7\7n\2\2\u00e7\u00e8\7c\2\2\u00e8\u00e9\7u\2\2\u00e9"+
		"\u00ea\7u\2\2\u00eaN\3\2\2\2\u00eb\u00ec\7v\2\2\u00ec\u00ed\7t\2\2\u00ed"+
		"\u00ee\7w\2\2\u00ee\u00ef\7g\2\2\u00efP\3\2\2\2\u00f0\u00f1\7f\2\2\u00f1"+
		"\u00f2\7q\2\2\u00f2R\3\2\2\2\u00f3\u00f4\7q\2\2\u00f4\u00f5\7f\2\2\u00f5"+
		"T\3\2\2\2\u00f6\u00f7\7r\2\2\u00f7\u00f8\7t\2\2\u00f8\u00f9\7q\2\2\u00f9"+
		"\u00fa\7e\2\2\u00faV\3\2\2\2\u00fb\u00fc\7h\2\2\u00fc\u00fd\7w\2\2\u00fd"+
		"\u00fe\7p\2\2\u00feX\3\2\2\2\u00ff\u0100\7t\2\2\u0100\u0101\7g\2\2\u0101"+
		"\u0102\7v\2\2\u0102\u0103\7w\2\2\u0103\u0104\7t\2\2\u0104\u0105\7p\2\2"+
		"\u0105Z\3\2\2\2\u0106\u0107\7T\2\2\u0107\\\3\2\2\2\u0108\u0109\7Y\2\2"+
		"\u0109^\3\2\2\2\u010a\u010b\7T\2\2\u010b\u010c\7Y\2\2\u010c`\3\2\2\2\u010d"+
		"\u0113\5k\66\2\u010e\u0112\5k\66\2\u010f\u0112\5m\67\2\u0110\u0112\t\2"+
		"\2\2\u0111\u010e\3\2\2\2\u0111\u010f\3\2\2\2\u0111\u0110\3\2\2\2\u0112"+
		"\u0115\3\2\2\2\u0113\u0111\3\2\2\2\u0113\u0114\3\2\2\2\u0114b\3\2\2\2"+
		"\u0115\u0113\3\2\2\2\u0116\u0118\5m\67\2\u0117\u0116\3\2\2\2\u0118\u0119"+
		"\3\2\2\2\u0119\u0117\3\2\2\2\u0119\u011a\3\2\2\2\u011a\u011b\3\2\2\2\u011b"+
		"\u011d\5\63\32\2\u011c\u011e\5m\67\2\u011d\u011c\3\2\2\2\u011e\u011f\3"+
		"\2\2\2\u011f\u011d\3\2\2\2\u011f\u0120\3\2\2\2\u0120d\3\2\2\2\u0121\u0123"+
		"\5m\67\2\u0122\u0121\3\2\2\2\u0123\u0124\3\2\2\2\u0124\u0122\3\2\2\2\u0124"+
		"\u0125\3\2\2\2\u0125f\3\2\2\2\u0126\u0128\t\3\2\2\u0127\u0126\3\2\2\2"+
		"\u0128\u0129\3\2\2\2\u0129\u0127\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012b"+
		"\3\2\2\2\u012b\u012c\b\64\2\2\u012ch\3\2\2\2\u012d\u0131\7%\2\2\u012e"+
		"\u0130\13\2\2\2\u012f\u012e\3\2\2\2\u0130\u0133\3\2\2\2\u0131\u0132\3"+
		"\2\2\2\u0131\u012f\3\2\2\2\u0132\u0135\3\2\2\2\u0133\u0131\3\2\2\2\u0134"+
		"\u0136\t\4\2\2\u0135\u0134\3\2\2\2\u0136\u0137\3\2\2\2\u0137\u0138\b\65"+
		"\2\2\u0138j\3\2\2\2\u0139\u013a\t\5\2\2\u013al\3\2\2\2\u013b\u013c\t\6"+
		"\2\2\u013cn\3\2\2\2\13\2\u0111\u0113\u0119\u011f\u0124\u0129\u0131\u0135"+
		"\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}