import java.lang.System;

class Sample {
    public static void main(String[] argv) throws java.io.IOException {
	Yylex yy = new Yylex(System.in);
	Yytoken t;
	while ((t = yy.yylex()) != null)
	    System.out.println(t);
    }
}

class Utility {
  public static void _assert(boolean expr) { 
	if (false == expr) throw (new Error("Error: Assertion failed."));
  }
  
  private static final String[] errorMsg = {
    "Error: Unmatched end-of-comment punctuation.",
    "Error: Unmatched start-of-comment punctuation.",
    "Error: Unclosed string.",
    "Error: Illegal character."
  };
  
  public static final int E_ENDCOMMENT = 0; 
  public static final int E_STARTCOMMENT = 1; 
  public static final int E_UNCLOSEDSTR = 2; 
  public static final int E_UNMATCHED = 3; 

  public static void _error(int code)
  {
	System.out.println(errorMsg[code]);
  }
}

class Yytoken {
  Yytoken (int index, String text, int line)
  {
	m_index = index;
	m_text = new String(text);
	m_line = line;
  }

  public int m_index;
  public String m_text;
  public int m_line;

  public String toString() {
      return "Token #"+m_index+": "+m_text+" (line "+m_line+")";
  }
}

%%

%line
%state COMMENT_LINE
%state COMMENT_SECTION

ALPHA=[A-Za-z]
INTEGER=[0-9]*
WHITESPACE=[ \t\r\f\v]

ESCAPE=\\
NEWLINE=\n
NULL_CHAR=\0

SELF=self
SELF_TYPE=SELF_TYPE

ALPHA_NUMERIC=[A-Za-z0-9_]*

TYPE_IDENTIFIER=[A-Z]{ALPHA_NUMERIC}
OBJECT_IDENTIFIER=[a-z]{ALPHA_NUMERIC}

CLASS=[(?i)class]
INHERITS=[(?i)inherits]

IF=[(?i)if]
THEN=[(?i)then)]
ELSE=[(?i)else)]
FI=[(?i)fi)]

WHILE=[(?i)while)]
LOOP=[(?i)loop)]
POOL=[(?i)pool)]

LET=[(?i)let)]
IN=[(?i)in)]

CASE=[(?i)case)]
OF=[(?i))of]
ESAC=[(?i)esac]

NEW=[(?i)new]
ISVOID=[(?i)isvoid)]
NOT=[(?i)not]

TRUE=[t(?i)rue]
FALSE=[f(?i)alse)]

%%

<YYINITIAL> {CLASS} { return (new Yytoken(0,yytext(),yyline)); }
<YYINITIAL> {INHERITS} { return (new Yytoken(1,yytext(),yyline)); }

<YYINITIAL> {IF} { return (new Yytoken(2,yytext(),yyline)); }
<YYINITIAL> {THEN} { return (new Yytoken(3,yytext(),yyline)); }
<YYINITIAL> {ELSE} { return (new Yytoken(4,yytext(),yyline)); }
<YYINITIAL> {FI} { return (new Yytoken(5,yytext(),yyline)); }

<YYINITIAL> {WHILE} { return (new Yytoken(6,yytext(),yyline)); }
<YYINITIAL> {LOOP} { return (new Yytoken(7,yytext(),yyline)); }
<YYINITIAL> {POOL} { return (new Yytoken(8,yytext(),yyline)); }

<YYINITIAL> {LET} { return (new Yytoken(9,yytext(),yyline)); }
<YYINITIAL> {IN} { return (new Yytoken(10,yytext(),yyline)); }

<YYINITIAL> {CASE} { return (new Yytoken(11,yytext(),yyline)); }
<YYINITIAL> {OF} { return (new Yytoken(12,yytext(),yyline)); }
<YYINITIAL> {ESAC} { return (new Yytoken(13,yytext(),yyline)); }

<YYINITIAL> {NEW} { return (new Yytoken(14,yytext(),yyline)); }
<YYINITIAL> {ISVOID} { return (new Yytoken(15,yytext(),yyline)); }
<YYINITIAL> {NOT}  { return (new Yytoken(16,yytext(),yyline)); }

<YYINITIAL> {TYPE_IDENTIFIER} { return (new Yytoken(17,yytext(),yyline)); }
<YYINITIAL> {OBJECT_IDENTIFIER}  { return (new Yytoken(18,yytext(),yyline); }

<YYINITIAL> {INTEGER} { return (new Yytoken(19,yytext(),yyline); }

<YYINITIAL> {NEWLINE} {}
<YYINITIAL> {WHITESPACE} {}

<YYINITIAL> "--" { yybegin(COMMENT_LINE); }
<COMMENT_LINE> . {}
<COMMENT_LINE> {NEWLINE} { yybegin(YYINITIAL); }
<COMMENT_LINE> <<EOF>> { yybegin(YYINITIAL); }





