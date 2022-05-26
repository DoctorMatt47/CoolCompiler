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


class Yylex {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

	private int nestedCommentCount = 0;
	private String currentString = "";
	private boolean isCommentSection = false;
	private boolean isString = false;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int STRING = 3;
	private final int COMMENT_SECTION = 2;
	private final int YYINITIAL = 0;
	private final int COMMENT_LINE = 1;
	private final int yy_state_dtrans[] = {
		0,
		47,
		104,
		107
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NO_ANCHOR,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NOT_ACCEPT,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NOT_ACCEPT,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NOT_ACCEPT,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NOT_ACCEPT,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NOT_ACCEPT,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NOT_ACCEPT,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NOT_ACCEPT,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NOT_ACCEPT,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NOT_ACCEPT,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NO_ANCHOR,
		/* 169 */ YY_NO_ANCHOR,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NO_ANCHOR,
		/* 172 */ YY_NO_ANCHOR,
		/* 173 */ YY_NO_ANCHOR,
		/* 174 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"60,56:8,57,54,56,57,55,56:18,57,56,58,56:5,13,14,4,6,11,7,1,5,53:10,10,12,8" +
",9,17,56,2,34,35,36,37,38,39,35,40,41,35:2,42,35,43,44,45,35,46,47,48,35,49" +
",50,35:3,56,59,56:2,51,56,20,52,18,33,25,28,52,24,22,52:2,19,52,23,30,31,52" +
",26,21,27,52,32,29,52:3,15,56,16,3,56,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,175,
"0,1:4,2,1:2,3,4,5,1:3,6,1:4,7,8,1:9,9,10:16,11,1:17,12,13,14,15,8:16,16,17," +
"18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42," +
"43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67," +
"68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,10,88,89,90,91," +
"92,93,94,95,96,97,98,99,100,101,102,103,104")[0];

	private int yy_nxt[][] = unpackFromString(105,61,
"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,127,157:2,66,129,157,159,15" +
"7,161,89,163,93,165,157:2,20:2,158,20,160,67,20,90,128,130,94,162,20:2,164," +
"20,171,18,157,65,21,22,18,22,23,18:2,-1:75,24,-1:53,25,-1:60,26,-1,27,-1:68" +
",28,-1:47,29,-1:74,157,167,131,157:13,131,157:7,167,157:11,-1:25,20:36,-1:2" +
"5,157:6,151,157:15,151,157:13,-1:25,157:36,-1:7,1,48:7,86,48:23,49,48:21,50" +
",49,48,49,48:3,-1:53,65,-1:25,157:3,135,157,30,157:4,31,157:10,31,157:3,30," +
"157:3,135,157:6,-1:25,20:4,70,20:18,70,20:12,-1:25,20:6,174,20:15,174,20:13" +
",-1:45,91,-1:30,85,-1:66,55,-1:47,62:53,63,110,62:5,-1:18,157:4,32,157:18,3" +
"2,157:12,-1:25,20:3,172,20,68,20:4,69,20:10,69,20:3,68,20:3,172,20:6,-1:51," +
"95,-1:20,56,-1:74,157:10,33,157:10,33,157:14,-1:25,20:10,71,20:10,71,20:14," +
"-1:46,98,-1:39,157:9,34,157:20,34,157:5,-1:25,20:9,72,20:20,72,20:5,-1:24,1" +
"01,-1:61,157:11,35,157:20,35,157:3,-1:25,20:11,73,20:20,73,20:3,-1:24,51,-1" +
":61,157:9,36,157:20,36,157:5,-1:25,20:9,74,20:20,74,20:5,-1:7,1,52:3,87,52:" +
"8,92,52:18,53,52:21,54,53,52,53,52:3,-1:18,157:7,37,157:12,37,157:15,-1:25," +
"20:7,75,20:12,75,20:15,-1:7,1,57:31,58,57:21,59,58,57,58,60,88,61,-1:18,157" +
":13,38,157:13,38,157:8,-1:25,20:7,77,20:12,77,20:15,-1:61,64,-1:24,157:7,39" +
",157:12,39,157:15,-1:25,78,20:17,78,20:17,-1:25,40,157:17,40,157:17,-1:25,2" +
"0:13,76,20:13,76,20:8,-1:25,157:5,41,157:19,41,157:10,-1:25,20,80,20:22,80," +
"20:11,-1:25,157,42,157:22,42,157:11,-1:25,20:5,79,20:19,79,20:10,-1:25,157:" +
"3,43,157:25,43,157:6,-1:25,20:3,81,20:25,81,20:6,-1:25,157:7,44,157:12,44,1" +
"57:15,-1:25,20:7,82,20:12,82,20:15,-1:25,157:15,45,157:3,45,157:16,-1:25,20" +
":15,83,20:3,83,20:16,-1:25,157:3,46,157:25,46,157:6,-1:25,20:3,84,20:25,84," +
"20:6,-1:25,157:7,96,157:4,133,157:7,96,157:5,133,157:9,-1:25,20:7,97,20:4,1" +
"38,20:7,97,20:5,138,20:9,-1:25,157:7,99,157:4,102,157:7,99,157:5,102,157:9," +
"-1:25,20:7,100,20:4,103,20:7,100,20:5,103,20:9,-1:25,157:3,105,157:25,105,1" +
"57:6,-1:25,20:3,106,20:25,106,20:6,-1:25,157:12,108,157:13,108,157:9,-1:25," +
"20:3,109,20:25,109,20:6,-1:25,157:14,149,157:16,149,157:4,-1:25,20:2,112,20" +
":13,112,20:19,-1:25,157:3,111,157:25,111,157:6,-1:25,20:12,114,20:13,114,20" +
":9,-1:25,157:2,113,157:13,113,157:19,-1:25,20:12,116,20:13,116,20:9,-1:25,1" +
"57:7,115,157:12,115,157:15,-1:25,20:7,118,20:12,118,20:15,-1:25,157:4,152,1" +
"57:18,152,157:12,-1:25,20:3,120,20:25,120,20:6,-1:25,157:12,117,157:13,117," +
"157:9,-1:25,20,122,20:22,122,20:11,-1:25,157:3,119,157:25,119,157:6,-1:25,2" +
"0:4,124,20:18,124,20:12,-1:25,157:12,153,157:13,153,157:9,-1:25,20:9,126,20" +
":20,126,20:5,-1:25,157:7,154,157:12,154,157:15,-1:25,157,121,157:22,121,157" +
":11,-1:25,157:4,123,157:18,123,157:12,-1:25,157:8,155,157:19,155,157:7,-1:2" +
"5,157:4,156,157:18,156,157:12,-1:25,157:9,125,157:20,125,157:5,-1:25,20,166" +
",132,20:13,132,20:7,166,20:11,-1:25,157,137,157,139,157:20,137,157:4,139,15" +
"7:6,-1:25,20,134,20,136,20:20,134,20:4,136,20:6,-1:25,157:6,141,157:15,141," +
"157:13,-1:25,20:12,140,20:13,140,20:9,-1:25,157:6,143,157:15,143,157:13,-1:" +
"25,20:6,142,20:15,142,20:13,-1:25,157:12,145,157:13,145,157:9,-1:25,20:2,14" +
"4,20:13,144,20:19,-1:25,157:2,147,157:13,147,157:19,-1:25,20:4,146,20:18,14" +
"6,20:12,-1:25,20:12,148,20:13,148,20:9,-1:25,20:4,150,20:18,150,20:12,-1:25" +
",20:6,168,20:15,168,20:13,-1:25,20:14,169,20:16,169,20:4,-1:25,20:8,170,20:" +
"19,170,20:7,-1:25,20:7,173,20:12,173,20:15,-1:7");

	public Yytoken yylex ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

	if (isCommentSection) {
		isCommentSection = false;
		return new Yytoken(-5,"Unclosed comment section",yyline);
	}
	if (isString) {
		isString = false;
		return new Yytoken(-6,"Unclosed string",yyline);
	}
	return null;
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 0:
						{ return new Yytoken(38,yytext(),yyline); }
					case -2:
						break;
					case 1:
						
					case -3:
						break;
					case 2:
						{ return new Yytoken(0,yytext(),yyline); }
					case -4:
						break;
					case 3:
						{ return new Yytoken(1,yytext(),yyline); }
					case -5:
						break;
					case 4:
						{ return new Yytoken(2,yytext(),yyline); }
					case -6:
						break;
					case 5:
						{ return new Yytoken(3,yytext(),yyline); }
					case -7:
						break;
					case 6:
						{ return new Yytoken(4,yytext(),yyline); }
					case -8:
						break;
					case 7:
						{ return new Yytoken(5,yytext(),yyline); }
					case -9:
						break;
					case 8:
						{ return new Yytoken(6,yytext(),yyline); }
					case -10:
						break;
					case 9:
						{ return new Yytoken(7,yytext(),yyline); }
					case -11:
						break;
					case 10:
						{ return new Yytoken(8,yytext(),yyline); }
					case -12:
						break;
					case 11:
						{ return new Yytoken(9,yytext(),yyline); }
					case -13:
						break;
					case 12:
						{ return new Yytoken(10,yytext(),yyline); }
					case -14:
						break;
					case 13:
						{ return new Yytoken(11,yytext(),yyline); }
					case -15:
						break;
					case 14:
						{ return new Yytoken(12,yytext(),yyline); }
					case -16:
						break;
					case 15:
						{ return new Yytoken(13,yytext(),yyline); }
					case -17:
						break;
					case 16:
						{ return new Yytoken(14,yytext(),yyline); }
					case -18:
						break;
					case 17:
						{ return new Yytoken(15,yytext(),yyline); }
					case -19:
						break;
					case 18:
						{ return new Yytoken(-4,"Unexpected token",yyline); }
					case -20:
						break;
					case 19:
						{ return new Yytoken(37,yytext(),yyline); }
					case -21:
						break;
					case 20:
						{ return new Yytoken(36,yytext(),yyline); }
					case -22:
						break;
					case 21:
						{}
					case -23:
						break;
					case 22:
						{}
					case -24:
						break;
					case 23:
						{
	yybegin(STRING);
	currentString = "";
	isString = true;
}
					case -25:
						break;
					case 24:
						{ return new Yytoken(-3,"Unexpected token",yyline); }
					case -26:
						break;
					case 25:
						{ yybegin(COMMENT_LINE); }
					case -27:
						break;
					case 26:
						{ return new Yytoken(18,yytext(),yyline); }
					case -28:
						break;
					case 27:
						{ return new Yytoken(16,yytext(),yyline); }
					case -29:
						break;
					case 28:
						{ return new Yytoken(17,yytext(),yyline); }
					case -30:
						break;
					case 29:
						{ yybegin(COMMENT_SECTION); isCommentSection = true; }
					case -31:
						break;
					case 30:
						{ return new Yytoken(29,yytext(),yyline); }
					case -32:
						break;
					case 31:
						{ return new Yytoken(21,yytext(),yyline); }
					case -33:
						break;
					case 32:
						{ return new Yytoken(24,yytext(),yyline); }
					case -34:
						break;
					case 33:
						{ return new Yytoken(31,yytext(),yyline); }
					case -35:
						break;
					case 34:
						{ return new Yytoken(28,yytext(),yyline); }
					case -36:
						break;
					case 35:
						{ return new Yytoken(33,yytext(),yyline); }
					case -37:
						break;
					case 36:
						{ return new Yytoken(35,yytext(),yyline); }
					case -38:
						break;
					case 37:
						{ return new Yytoken(30,yytext(),yyline); }
					case -39:
						break;
					case 38:
						{ return new Yytoken(26,yytext(),yyline); }
					case -40:
						break;
					case 39:
						{ return new Yytoken(23,yytext(),yyline); }
					case -41:
						break;
					case 40:
						{ return new Yytoken(32,yytext(),yyline); }
					case -42:
						break;
					case 41:
						{ return new Yytoken(22,yytext(),yyline); }
					case -43:
						break;
					case 42:
						{ return new Yytoken(27,yytext(),yyline); }
					case -44:
						break;
					case 43:
						{ return new Yytoken(19,yytext(),yyline); }
					case -45:
						break;
					case 44:
						{ return new Yytoken(25,yytext(),yyline); }
					case -46:
						break;
					case 45:
						{ return new Yytoken(34,yytext(),yyline); }
					case -47:
						break;
					case 46:
						{ return new Yytoken(20,yytext(),yyline); }
					case -48:
						break;
					case 48:
						{}
					case -49:
						break;
					case 49:
						{}
					case -50:
						break;
					case 50:
						{ yybegin(YYINITIAL); }
					case -51:
						break;
					case 51:
						{ yybegin(YYINITIAL); }
					case -52:
						break;
					case 52:
						{}
					case -53:
						break;
					case 53:
						{}
					case -54:
						break;
					case 54:
						{}
					case -55:
						break;
					case 55:
						{
	if (nestedCommentCount == 0) {
		yybegin(YYINITIAL);
		isCommentSection = false;
	}
	else {
		nestedCommentCount--;
	}
}
					case -56:
						break;
					case 56:
						{ nestedCommentCount++; }
					case -57:
						break;
					case 57:
						{ currentString += yytext(); }
					case -58:
						break;
					case 58:
						{ currentString += yytext(); }
					case -59:
						break;
					case 59:
						{ return new Yytoken(-1,"Not escaped newline character in string",yyline); }
					case -60:
						break;
					case 60:
						{ 
	yybegin(YYINITIAL);
	isString = false;
	return new Yytoken(39,currentString,yyline);
}
					case -61:
						break;
					case 61:
						{ return new Yytoken(-2,"Null character in string",yyline); }
					case -62:
						break;
					case 62:
						{
	var secondChar = yytext().charAt(1);
	switch (secondChar) {
		case 'b' -> currentString += '\b';
		case 't' -> currentString += '\t';
		case 'n' -> currentString += '\n';
		case 'f' -> currentString += '\f';
		default -> currentString += secondChar;
	}
}
					case -63:
						break;
					case 63:
						{ currentString += yytext(); }
					case -64:
						break;
					case 64:
						{ currentString += yytext(); }
					case -65:
						break;
					case 65:
						{ return new Yytoken(38,yytext(),yyline); }
					case -66:
						break;
					case 66:
						{ return new Yytoken(37,yytext(),yyline); }
					case -67:
						break;
					case 67:
						{ return new Yytoken(36,yytext(),yyline); }
					case -68:
						break;
					case 68:
						{ return new Yytoken(29,yytext(),yyline); }
					case -69:
						break;
					case 69:
						{ return new Yytoken(21,yytext(),yyline); }
					case -70:
						break;
					case 70:
						{ return new Yytoken(24,yytext(),yyline); }
					case -71:
						break;
					case 71:
						{ return new Yytoken(31,yytext(),yyline); }
					case -72:
						break;
					case 72:
						{ return new Yytoken(28,yytext(),yyline); }
					case -73:
						break;
					case 73:
						{ return new Yytoken(33,yytext(),yyline); }
					case -74:
						break;
					case 74:
						{ return new Yytoken(35,yytext(),yyline); }
					case -75:
						break;
					case 75:
						{ return new Yytoken(30,yytext(),yyline); }
					case -76:
						break;
					case 76:
						{ return new Yytoken(26,yytext(),yyline); }
					case -77:
						break;
					case 77:
						{ return new Yytoken(23,yytext(),yyline); }
					case -78:
						break;
					case 78:
						{ return new Yytoken(32,yytext(),yyline); }
					case -79:
						break;
					case 79:
						{ return new Yytoken(22,yytext(),yyline); }
					case -80:
						break;
					case 80:
						{ return new Yytoken(27,yytext(),yyline); }
					case -81:
						break;
					case 81:
						{ return new Yytoken(19,yytext(),yyline); }
					case -82:
						break;
					case 82:
						{ return new Yytoken(25,yytext(),yyline); }
					case -83:
						break;
					case 83:
						{ return new Yytoken(34,yytext(),yyline); }
					case -84:
						break;
					case 84:
						{ return new Yytoken(20,yytext(),yyline); }
					case -85:
						break;
					case 86:
						{}
					case -86:
						break;
					case 87:
						{}
					case -87:
						break;
					case 88:
						{ currentString += yytext(); }
					case -88:
						break;
					case 89:
						{ return new Yytoken(37,yytext(),yyline); }
					case -89:
						break;
					case 90:
						{ return new Yytoken(36,yytext(),yyline); }
					case -90:
						break;
					case 92:
						{}
					case -91:
						break;
					case 93:
						{ return new Yytoken(37,yytext(),yyline); }
					case -92:
						break;
					case 94:
						{ return new Yytoken(36,yytext(),yyline); }
					case -93:
						break;
					case 96:
						{ return new Yytoken(37,yytext(),yyline); }
					case -94:
						break;
					case 97:
						{ return new Yytoken(36,yytext(),yyline); }
					case -95:
						break;
					case 99:
						{ return new Yytoken(37,yytext(),yyline); }
					case -96:
						break;
					case 100:
						{ return new Yytoken(36,yytext(),yyline); }
					case -97:
						break;
					case 102:
						{ return new Yytoken(37,yytext(),yyline); }
					case -98:
						break;
					case 103:
						{ return new Yytoken(36,yytext(),yyline); }
					case -99:
						break;
					case 105:
						{ return new Yytoken(37,yytext(),yyline); }
					case -100:
						break;
					case 106:
						{ return new Yytoken(36,yytext(),yyline); }
					case -101:
						break;
					case 108:
						{ return new Yytoken(37,yytext(),yyline); }
					case -102:
						break;
					case 109:
						{ return new Yytoken(36,yytext(),yyline); }
					case -103:
						break;
					case 111:
						{ return new Yytoken(37,yytext(),yyline); }
					case -104:
						break;
					case 112:
						{ return new Yytoken(36,yytext(),yyline); }
					case -105:
						break;
					case 113:
						{ return new Yytoken(37,yytext(),yyline); }
					case -106:
						break;
					case 114:
						{ return new Yytoken(36,yytext(),yyline); }
					case -107:
						break;
					case 115:
						{ return new Yytoken(37,yytext(),yyline); }
					case -108:
						break;
					case 116:
						{ return new Yytoken(36,yytext(),yyline); }
					case -109:
						break;
					case 117:
						{ return new Yytoken(37,yytext(),yyline); }
					case -110:
						break;
					case 118:
						{ return new Yytoken(36,yytext(),yyline); }
					case -111:
						break;
					case 119:
						{ return new Yytoken(37,yytext(),yyline); }
					case -112:
						break;
					case 120:
						{ return new Yytoken(36,yytext(),yyline); }
					case -113:
						break;
					case 121:
						{ return new Yytoken(37,yytext(),yyline); }
					case -114:
						break;
					case 122:
						{ return new Yytoken(36,yytext(),yyline); }
					case -115:
						break;
					case 123:
						{ return new Yytoken(37,yytext(),yyline); }
					case -116:
						break;
					case 124:
						{ return new Yytoken(36,yytext(),yyline); }
					case -117:
						break;
					case 125:
						{ return new Yytoken(37,yytext(),yyline); }
					case -118:
						break;
					case 126:
						{ return new Yytoken(36,yytext(),yyline); }
					case -119:
						break;
					case 127:
						{ return new Yytoken(37,yytext(),yyline); }
					case -120:
						break;
					case 128:
						{ return new Yytoken(36,yytext(),yyline); }
					case -121:
						break;
					case 129:
						{ return new Yytoken(37,yytext(),yyline); }
					case -122:
						break;
					case 130:
						{ return new Yytoken(36,yytext(),yyline); }
					case -123:
						break;
					case 131:
						{ return new Yytoken(37,yytext(),yyline); }
					case -124:
						break;
					case 132:
						{ return new Yytoken(36,yytext(),yyline); }
					case -125:
						break;
					case 133:
						{ return new Yytoken(37,yytext(),yyline); }
					case -126:
						break;
					case 134:
						{ return new Yytoken(36,yytext(),yyline); }
					case -127:
						break;
					case 135:
						{ return new Yytoken(37,yytext(),yyline); }
					case -128:
						break;
					case 136:
						{ return new Yytoken(36,yytext(),yyline); }
					case -129:
						break;
					case 137:
						{ return new Yytoken(37,yytext(),yyline); }
					case -130:
						break;
					case 138:
						{ return new Yytoken(36,yytext(),yyline); }
					case -131:
						break;
					case 139:
						{ return new Yytoken(37,yytext(),yyline); }
					case -132:
						break;
					case 140:
						{ return new Yytoken(36,yytext(),yyline); }
					case -133:
						break;
					case 141:
						{ return new Yytoken(37,yytext(),yyline); }
					case -134:
						break;
					case 142:
						{ return new Yytoken(36,yytext(),yyline); }
					case -135:
						break;
					case 143:
						{ return new Yytoken(37,yytext(),yyline); }
					case -136:
						break;
					case 144:
						{ return new Yytoken(36,yytext(),yyline); }
					case -137:
						break;
					case 145:
						{ return new Yytoken(37,yytext(),yyline); }
					case -138:
						break;
					case 146:
						{ return new Yytoken(36,yytext(),yyline); }
					case -139:
						break;
					case 147:
						{ return new Yytoken(37,yytext(),yyline); }
					case -140:
						break;
					case 148:
						{ return new Yytoken(36,yytext(),yyline); }
					case -141:
						break;
					case 149:
						{ return new Yytoken(37,yytext(),yyline); }
					case -142:
						break;
					case 150:
						{ return new Yytoken(36,yytext(),yyline); }
					case -143:
						break;
					case 151:
						{ return new Yytoken(37,yytext(),yyline); }
					case -144:
						break;
					case 152:
						{ return new Yytoken(37,yytext(),yyline); }
					case -145:
						break;
					case 153:
						{ return new Yytoken(37,yytext(),yyline); }
					case -146:
						break;
					case 154:
						{ return new Yytoken(37,yytext(),yyline); }
					case -147:
						break;
					case 155:
						{ return new Yytoken(37,yytext(),yyline); }
					case -148:
						break;
					case 156:
						{ return new Yytoken(37,yytext(),yyline); }
					case -149:
						break;
					case 157:
						{ return new Yytoken(37,yytext(),yyline); }
					case -150:
						break;
					case 158:
						{ return new Yytoken(36,yytext(),yyline); }
					case -151:
						break;
					case 159:
						{ return new Yytoken(37,yytext(),yyline); }
					case -152:
						break;
					case 160:
						{ return new Yytoken(36,yytext(),yyline); }
					case -153:
						break;
					case 161:
						{ return new Yytoken(37,yytext(),yyline); }
					case -154:
						break;
					case 162:
						{ return new Yytoken(36,yytext(),yyline); }
					case -155:
						break;
					case 163:
						{ return new Yytoken(37,yytext(),yyline); }
					case -156:
						break;
					case 164:
						{ return new Yytoken(36,yytext(),yyline); }
					case -157:
						break;
					case 165:
						{ return new Yytoken(37,yytext(),yyline); }
					case -158:
						break;
					case 166:
						{ return new Yytoken(36,yytext(),yyline); }
					case -159:
						break;
					case 167:
						{ return new Yytoken(37,yytext(),yyline); }
					case -160:
						break;
					case 168:
						{ return new Yytoken(36,yytext(),yyline); }
					case -161:
						break;
					case 169:
						{ return new Yytoken(36,yytext(),yyline); }
					case -162:
						break;
					case 170:
						{ return new Yytoken(36,yytext(),yyline); }
					case -163:
						break;
					case 171:
						{ return new Yytoken(36,yytext(),yyline); }
					case -164:
						break;
					case 172:
						{ return new Yytoken(36,yytext(),yyline); }
					case -165:
						break;
					case 173:
						{ return new Yytoken(36,yytext(),yyline); }
					case -166:
						break;
					case 174:
						{ return new Yytoken(36,yytext(),yyline); }
					case -167:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
