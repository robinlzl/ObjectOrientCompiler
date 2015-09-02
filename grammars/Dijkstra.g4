/*******************************************************************************
 * Copyright (c) 2015 Gary F. Pollice
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Used in CS4533/CS544 at Worcester Polytechnic Institute
 *******************************************************************************/
 /*
  * This is the grammar for Toy Dijkstra
  */
grammar Dijkstra;

// tokens {
// 	FLOAT
// }
 
// Parser rules
/* Beginning of the file */
dijkstraText : 			classdeClaration+ EOF
						|program EOF 
						|classdeClaration+ program EOF
						;
	
classdeClaration :		CLASS ID (LPAR propertylist RPAR)? classBody
						;
						
//classBody :				declaration (classBody)? ;
classBody :				declaration+ ;
											
program :				PROGRAM ID (declaration | statement)+ ;

/*Declarations */
declaration :			variableDeclaration
						| arrayDeclaration
						| precedureDeclaration
						| functionDeclaration
						;
						
variableDeclaration :	(type | classname) idlist SEMICOLON? 
						;
					
arrayDeclaration :		type LBRACKET expression RBRACKET idlist SEMICOLON?
						;

precedureDeclaration :	PROC ID LPAR (parameterlist)? RPAR compoundStatement
						; 
						
functionDeclaration : 	FUN ID LPAR (parameterlist)? RPAR COLON typelist compoundStatement
						;

classname :				ID;	


propertylist :			property (COMMA property)*
						;

property :				ID
						| ID LBRACKET accessSpec RBRACKET
						| type ID
						| type ID LBRACKET accessSpec RBRACKET
						;

//property :				property COMMA property
//						| ID
//						| ID LBRACKET accessSpec RBRACKET
//						| type ID
//						| type ID LBRACKET accessSpec RBRACKET
//						;
						
accessSpec :			R | W | RW;


parameterlist :			parameter (COMMA parameter)*;

parameter :				ID
						|type ID
						| classname ID
						;

//parameter :        		parameter COMMA parameter
//						| ID
//						| type ID
//						| classname ID
//						;

/*Some places need only one type. Some places allow a typelist */						
type :					BOOLEAN | INT | FLOAT
						;
						
typelist :				type
						|typelist COMMA type
						;
						
idlist :                ID (COMMA ID)*;
 

/*Statments */						                           
statement :				assignStatement
	 				 	| inputStatement 
	 				 	| outputStatement
	 				 	| alternativeStatement
	 				 	| iterativeStatement
	 				 	| compoundStatement
	 				 	| returnStatement
	 				 	| procedurecall
	 				 	| methodcall
	 				 	;
	 				 	
assignStatement :		varlist ASSIGN expressionlist SEMICOLON? 
						;
						
inputStatement :		INPUT idlist SEMICOLON? 
						;
						
outputStatement :		PRINT expression SEMICOLON? 
						;

iterativeStatement :    DO guard+ OD 
						; 
						
compoundStatement :		LBRACE compoundbody+ RBRACE 
						;

alternativeStatement :	IF guard+ FI 
						;
						
returnStatement:		RETURN
						| RETURN expressionlist SEMICOLON? 
						;
						
procedurecall :			ID LPAR RPAR						
						| ID LPAR expressionlist RPAR
						;
						
methodcall :			ID DOT ID LPAR RPAR
						| ID DOT ID LPAR expressionlist RPAR
						;

compoundbody :			variableDeclaration
						| arrayDeclaration
						| statement
						;
												
//arglist :				argument
//						|arglist COMMA argument
//						;
//						
//argument :				expression
//						;						

varlist :				var (COMMA var)*
						;
						
var :					ID 
						| ID DOT ID
						| arrayaccessor
						;
						
guard :					expression GUARD statement 
						;
 
/* Note that the equality operators are right-associative. This allows for a = b ~= c to be
 * interpreted as a = (b ~= c). Semantic analysis would guarantee that a is a boolean.
 * NOTE also that the placement of the <assoc=right> in different from the description in
 * TDAR. The new way of doing this is described at 
 * https://theantlrguy.atlassian.net/wiki/display/ANTLR4/Left-recursive+rules
 */
expression :			LPAR expression RPAR
						| (TILDE | MINUS) expression
						| expression (STAR | SLASH | MOD | DIV) expression
						| expression (PLUS | MINUS) expression
						| expression (LT | GT | LTE | GTE) expression
						| <assoc=right> expression (EQ | NEQ) expression
//						| expression (OR | AND) expression
						| expression AND expression
						| expression OR expression
						| ID 
						| INTEGER
						| FLOATCONSTANT
						| TRUE
						| FALSE
						| (TRUE FALSE)
						| ID DOT ID
						| functioncall
						| constructor
						| methodcall
						| arrayaccessor
						;

functioncall :			ID LPAR RPAR
						| ID LPAR expressionlist RPAR
						;
						
arrayaccessor :			ID LBRACKET expression RBRACKET;					

constructor :			classname LPAR RPAR
						| classname LPAR expressionlist RPAR
						;

expressionlist: 		expression (COMMA expression)*
						;


//expr:   expr '.' ID                 // suffix operator, precedence 11
//    |   expr '[' expr ']'           // suffix operator, precedence 10
//    |   '-' expr                    // prefix operator, precedence 9
//    |   expr ('*'|'/') expr         // binary operator, precedence 8
//    |   expr ('+'|'-') expr         // binary operator, precedence 7
//    |   expr '&' expr               // binary operator, precedence 6
//    |   expr '?' expr ':' expr      // ternary operator, precedence 5
//    |   expr '='<assoc=right> expr  // binary right-associative op, prec 4
//    |   ID                          // primary
//    |   INT                         // primary
//    |   '(' expr ')'                // primary
//    ;
/* Lexical rules */
 
// Separators and operators
ASSIGN :		'<-' ;
EQ :			'=' ;
GT :			'>' ;
GTE :			'>=' ;
GUARD :			'::' ;
LBRACE :		'{' ;
LPAR :			'(' ;
LT :			'<' ;
LTE :			'<=';
LBRACKET :		'[';
MINUS :			'-' ;
NEQ :			'~=' ;
PLUS :			'+' ;
RBRACE :		'}' ;
RPAR :			')' ;
RBRACKET :		']' ;
SEMICOLON :		';' ;
COLON :			':' ;
SLASH :			'/' ;
STAR :			'*' ;
MOD :			'mod' ;
DIV :			'div' ;
TILDE :			'~' ;
COMMA :         ',' ;
DOT :			'.' ;
OR :            '|' ;
AND :			'&' ;        

// Reserved words
BOOLEAN :		'boolean' ;
FALSE :			'false' ;
FI :			'fi' ;
IF :			'if' ;
INPUT :			'input' ;
INT:			'int' ;
FLOAT :         'float' ;
LOOP :			'loop' ;
PRINT :			'print' ;
PROGRAM :		'program' ;
CLASS :			'class' ;
TRUE :			'true' ;
DO :			'do';
OD :			'od';
PROC :			'proc' ;
FUN :			'fun' ;
RETURN :		'return' ;
R :				'R' ;
W :				'W' ;
RW :			'RW' ;

// The rest
ID : 			LETTER (LETTER|DIGIT|'_'|'?')* ;
FLOATCONSTANT:	DIGIT+ DOT DIGIT+;
INTEGER : 		DIGIT+ ;
WS :			[ \t\r\n]+ -> skip ;
COMMENT :		'#' .*? ('\n'|EOF)->skip;
 
fragment
LETTER :		[A-Za-z] ;
 
fragment
DIGIT :		[0-9] ;