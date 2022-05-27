import java.lang.System;
import java.io.File;
import java.io.FileInputStream;

class Program {
    public static void main(String[] argv) throws java.io.IOException {
		var file = new File(argv[0]);
		var stream = new FileInputStream(file);

		Yylex yy = new Yylex(stream);
		Yytoken t;
		while ((t = yy.yylex()) != null)
			System.out.println(t);
	}
}

class Yytoken {
  Yytoken (int index, String text, int line)
  {
	this.index = index;
	this.text = text;
	this.line = line;
  }

  public int index;
  public String text;
  public int line;

  public String toString() {
      return "Token #"+index+": "+text+" (line "+line+")";
  }
}

%%

%{
	private int nestedCommentCount = 0;
	private String currentString = "";

	private boolean isCommentSection = false;
	private boolean isString = false;
%}

%line
%char
%state COMMENT_LINE, COMMENT_SECTION, STRING 

ALPHA=[A-Za-z]
INTEGER=[0-9]*
WHITESPACE=[ \t\r\f\v]

BACKSLASH=\\
NEWLINE=\n
NULL_CHAR=\0
QUOTE=\"

OPEN_COMMENT_LINE=\-\-

OPEN_COMMENT_SECTION=\(\*
CLOSE_COMMENT_SECTION=\*\)

SELF=self
SELF_TYPE=SELF_TYPE

ALPHA_NUMERIC=[A-Za-z0-9_]*

TYPE_IDENTIFIER=[A-Z]{ALPHA_NUMERIC}
OBJECT_IDENTIFIER=[a-z]{ALPHA_NUMERIC}

CLASS=[cC][lL][aA][sS][sS]
INHERITS=[iI][nN][hH][eE][rR][iI][tT][sS]

IF=[iI][fF]
THEN=[tT][hH][eE][nN]
ELSE=[eE][lL][sS][eE]
FI=[fF][iI]

WHILE=[wW][hH][iI][lL][eE]
LOOP=[lL][oO][oO][pP]
POOL=[pP][oO][oO][lL]

LET=[lL][eE][tT]
IN=[iI][nN]

CASE=[cC][aA][sS][eE]
OF=[oO][fF]
ESAC=[eE][sS][aA][cC]

NEW=[nN][eE][wW]
ISVOID=[iI][sS][vV][oO][iI][dD]
NOT=[nN][oO][tT]

TRUE=t[rR][uU][eE]
FALSE=f[aA][lL][sS][eE]

%eofval{
	if (isCommentSection) {
		isCommentSection = false;
		return new Yytoken(-5,"Unclosed comment section",yyline);
	}
	if (isString) {
		isString = false;
		return new Yytoken(-6,"Unclosed string",yyline);
	}
	return null;
%eofval}

%%

<YYINITIAL> "." { return new Yytoken(0,yytext(),yyline); }
<YYINITIAL> "@" { return new Yytoken(1,yytext(),yyline); }
<YYINITIAL> "~" { return new Yytoken(2,yytext(),yyline); }
<YYINITIAL> "*" { return new Yytoken(3,yytext(),yyline); }
<YYINITIAL> "/" { return new Yytoken(4,yytext(),yyline); }
<YYINITIAL> "+" { return new Yytoken(5,yytext(),yyline); }
<YYINITIAL> "-" { return new Yytoken(6,yytext(),yyline); }
<YYINITIAL> "<" { return new Yytoken(7,yytext(),yyline); }
<YYINITIAL> "=" { return new Yytoken(8,yytext(),yyline); }
<YYINITIAL> ":" { return new Yytoken(9,yytext(),yyline); }
<YYINITIAL> "," { return new Yytoken(10,yytext(),yyline); }
<YYINITIAL> ";" { return new Yytoken(11,yytext(),yyline); }
<YYINITIAL> "(" { return new Yytoken(12,yytext(),yyline); }
<YYINITIAL> ")" { return new Yytoken(13,yytext(),yyline); }
<YYINITIAL> "{" { return new Yytoken(14,yytext(),yyline); }
<YYINITIAL> "}" { return new Yytoken(15,yytext(),yyline); }

<YYINITIAL> "<=" { return new Yytoken(16,yytext(),yyline); }
<YYINITIAL> "=>" { return new Yytoken(17,yytext(),yyline); }
<YYINITIAL> "<-" { return new Yytoken(18,yytext(),yyline); }

<YYINITIAL> {CLASS} { return new Yytoken(19,yytext(),yyline); }
<YYINITIAL> {INHERITS} { return new Yytoken(20,yytext(),yyline); }

<YYINITIAL> {IF} { return new Yytoken(21,yytext(),yyline); }
<YYINITIAL> {THEN} { return new Yytoken(22,yytext(),yyline); }
<YYINITIAL> {ELSE} { return new Yytoken(23,yytext(),yyline); }
<YYINITIAL> {FI} { return new Yytoken(24,yytext(),yyline); }

<YYINITIAL> {WHILE} { return new Yytoken(25,yytext(),yyline); }
<YYINITIAL> {LOOP} { return new Yytoken(26,yytext(),yyline); }
<YYINITIAL> {POOL} { return new Yytoken(27,yytext(),yyline); }

<YYINITIAL> {LET} { return new Yytoken(28,yytext(),yyline); }
<YYINITIAL> {IN} { return new Yytoken(29,yytext(),yyline); }

<YYINITIAL> {CASE} { return new Yytoken(30,yytext(),yyline); }
<YYINITIAL> {OF} { return new Yytoken(31,yytext(),yyline); }
<YYINITIAL> {ESAC} { return new Yytoken(32,yytext(),yyline); }

<YYINITIAL> {NEW} { return new Yytoken(33,yytext(),yyline); }
<YYINITIAL> {ISVOID} { return new Yytoken(34,yytext(),yyline); }
<YYINITIAL> {NOT}  { return new Yytoken(35,yytext(),yyline); }

<YYINITIAL> {TYPE_IDENTIFIER} { return new Yytoken(36,yytext(),yyline); }
<YYINITIAL> {OBJECT_IDENTIFIER}  { return new Yytoken(37,yytext(),yyline); }

<YYINITIAL> {INTEGER} { return new Yytoken(38,yytext(),yyline); }

<YYINITIAL> {SELF} { return new Yytoken(40,yytext(),yyline); }
<YYINITIAL> {SELF_TYPE} { return new Yytoken(41,yytext(),yyline); }

<YYINITIAL> {OPEN_COMMENT_LINE} { yybegin(COMMENT_LINE); }
<COMMENT_LINE> {NEWLINE} { yybegin(YYINITIAL); }
<COMMENT_LINE> <<EOF>> { yybegin(YYINITIAL); }
<COMMENT_LINE> {WHITESPACE} {}
<COMMENT_LINE> . {}

<YYINITIAL> {OPEN_COMMENT_SECTION} { yybegin(COMMENT_SECTION); isCommentSection = true; }

<COMMENT_SECTION> {CLOSE_COMMENT_SECTION} {
	if (nestedCommentCount == 0) {
		yybegin(YYINITIAL);
		isCommentSection = false;
	}
	else {
		nestedCommentCount--;
	}
}

<COMMENT_SECTION> {OPEN_COMMENT_SECTION} { nestedCommentCount++; }

<COMMENT_SECTION> {WHITESPACE} {}
<COMMENT_SECTION> {NEWLINE} {}
<COMMENT_SECTION> . {}

<YYINITIAL> {QUOTE} {
	yybegin(STRING);
	currentString = "";
	isString = true;
}

<STRING> {BACKSLASH}{NEWLINE} { currentString += yytext(); }
<STRING> {BACKSLASH}\r{NEWLINE} { currentString += yytext(); }
<STRING> {NEWLINE} { return new Yytoken(-1,"Not escaped newline character in string",yyline); }
<STRING> {NULL_CHAR} { return new Yytoken(-2,"Null character in string",yyline); }

<STRING> {BACKSLASH}. {
	var secondChar = yytext().charAt(1);
	switch (secondChar) {
		case 'b' -> currentString += '\b';
		case 't' -> currentString += '\t';
		case 'n' -> currentString += '\n';
		case 'f' -> currentString += '\f';
		default -> currentString += secondChar;
	}
}

<STRING> {QUOTE} { 
	yybegin(YYINITIAL);
	isString = false;
	return new Yytoken(39,currentString,yyline);
}

<STRING> {WHITESPACE} { currentString += yytext(); }
<STRING> . { currentString += yytext(); }

<YYINITIAL> {NEWLINE} {}
<YYINITIAL> {WHITESPACE} {}

<YYINITIAL> "*)" { return new Yytoken(-3,"Unexpected token",yyline); }
<YYINITIAL> . { return new Yytoken(-4,"Unexpected token",yyline); }
