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
  Yytoken (int index, String text, int line, int charBegin, int charEnd)
  {
	m_index = index;
	m_text = new String(text);
	m_line = line;
	m_charBegin = charBegin;
	m_charEnd = charEnd;
  }

  public int m_index;
  public String m_text;
  public int m_line;
  public int m_charBegin;
  public int m_charEnd;

  public String toString() {
      return "Token #"+m_index+": "+m_text+" (line "+m_line+")";
  }
}

%%

%line

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

<YYINITIAL> {NONNEWLINE_WHITE_SPACE_CHAR}+ { }

<YYINITIAL,COMMENT> \n { }

<YYINITIAL> "/*" { yybegin(COMMENT); comment_count = comment_count + 1; }

<COMMENT> "/*" { comment_count = comment_count + 1; }
<COMMENT> "*/" { 
	comment_count = comment_count - 1; 
	Utility._assert(comment_count >= 0);
	if (comment_count == 0) {
    		yybegin(YYINITIAL);
	}
}
<COMMENT> {COMMENT_TEXT} { }

<YYINITIAL> \"{STRING_TEXT}\" {
	String str =  yytext().substring(1,yytext().length() - 1);
	
	Utility._assert(str.length() == yytext().length() - 2);
	return (new Yytoken(40,str,yyline,yychar,yychar + str.length()));
}
<YYINITIAL> \"{STRING_TEXT} {
	String str =  yytext().substring(1,yytext().length());

	Utility._error(Utility.E_UNCLOSEDSTR);
	Utility._assert(str.length() == yytext().length() - 1);
	return (new Yytoken(41,str,yyline,yychar,yychar + str.length()));
} 
<YYINITIAL> {DIGIT}+ { 
	return (new Yytoken(42,yytext(),yyline,yychar,yychar + yytext().length()));
}	
<YYINITIAL> {ALPHA}({ALPHA}|{DIGIT}|_)* {
	return (new Yytoken(43,yytext(),yyline,yychar,yychar + yytext().length()));
}	
<YYINITIAL,COMMENT> . {
        System.out.println("Illegal character: <" + yytext() + ">");
	Utility._error(Utility.E_UNMATCHED);
}







