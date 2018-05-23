/* The following code was generated by JFlex 1.4.1 on 11/10/12 10:31 PM */

/*
 * 8/19/2009
 *
 * ScalaTokenMaker.java - Scanner for the Scala programming language.
 * 
 * This library is distributed under a modified BSD license.  See the included
 * RSyntaxTextArea.License.txt file for details.
 */
package com.fr.design.gui.syntax.ui.rsyntaxtextarea.modes;

import java.io.*;
import javax.swing.text.Segment;

import com.fr.design.gui.syntax.ui.rsyntaxtextarea.*;


/**
 * Scanner for the Scala programming language.<p>
 *
 * This implementation was created using
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.1; however, the generated file
 * was modified for performance.  Memory allocation needs to be almost
 * completely removed to be competitive with the handwritten lexers (subclasses
 * of <code>AbstractTokenMaker</code>, so this class has been modified so that
 * Strings are never allocated (via yytext()), and the scanner never has to
 * worry about refilling its buffer (needlessly copying chars around).
 * We can achieve this because RText always scans exactly 1 line of tokens at a
 * time, and hands the scanner this line as an array of characters (a Segment
 * really).  Since tokens contain pointers to char arrays instead of Strings
 * holding their contents, there is no need for allocating new memory for
 * Strings.<p>
 *
 * The actual algorithm generated for scanning has, of course, not been
 * modified.<p>
 *
 * If you wish to regenerate this file yourself, keep in mind the following:
 * <ul>
 *   <li>The generated ScalaTokenMaker.java</code> file will contain two
 *       definitions of both <code>zzRefill</code> and <code>yyreset</code>.
 *       You should hand-delete the second of each definition (the ones
 *       generated by the lexer), as these generated methods modify the input
 *       buffer, which we'll never have to do.</li>
 *   <li>You should also change the declaration/definition of zzBuffer to NOT
 *       be initialized.  This is a needless memory allocation for us since we
 *       will be pointing the array somewhere else anyway.</li>
 *   <li>You should NOT call <code>yylex()</code> on the generated scanner
 *       directly; rather, you should use <code>getTokenList</code> as you would
 *       with any other <code>TokenMaker</code> instance.</li>
 * </ul>
 *
 * @author Robert Futrell
 * @version 0.5
 *
 */

public class ScalaTokenMaker extends AbstractJFlexCTokenMaker {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** lexical states */
  public static final int EOL_COMMENT = 3;
  public static final int MULTILINE_STRING_DOUBLE = 1;
  public static final int YYINITIAL = 0;
  public static final int MLC = 2;

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\31\1\24\1\0\1\31\23\0\1\31\1\33\1\25\1\33"+
    "\1\1\1\33\1\33\1\22\2\4\1\30\1\16\1\33\1\16\1\21"+
    "\1\27\1\12\11\3\1\42\1\33\1\0\1\33\1\0\2\33\3\10"+
    "\1\17\1\14\1\17\5\1\1\6\6\1\1\52\7\1\1\4\1\23"+
    "\1\4\1\0\1\5\1\26\1\44\1\45\1\11\1\20\1\15\1\40"+
    "\1\61\1\34\1\41\1\56\1\60\1\7\1\53\1\50\1\47\1\36"+
    "\1\2\1\46\1\37\1\35\1\55\1\57\1\43\1\13\1\51\1\54"+
    "\1\32\1\0\1\32\1\33\uff81\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\2\0\2\2\1\3\1\4\2\2\1\3"+
    "\3\2\1\5\1\6\1\7\2\2\1\10\15\2\1\1"+
    "\1\11\1\12\2\1\1\13\5\1\1\14\3\1\1\2"+
    "\1\3\1\0\2\15\3\2\1\0\3\2\1\16\1\17"+
    "\1\5\2\7\1\20\1\7\1\21\1\22\26\2\1\11"+
    "\1\0\1\23\10\0\1\2\1\15\1\0\4\2\1\24"+
    "\1\2\1\20\1\25\1\26\11\2\1\16\12\2\1\27"+
    "\10\0\21\2\2\0\1\30\2\0\1\31\4\2\1\16"+
    "\6\2\4\0\3\2";

  private static int [] zzUnpackAction() {
    int [] result = new int[184];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\62\0\144\0\226\0\310\0\372\0\u012c\0\310"+
    "\0\u015e\0\u0190\0\u01c2\0\u01f4\0\u0226\0\u0258\0\u028a\0\310"+
    "\0\u02bc\0\u02ee\0\u0320\0\u0352\0\u0384\0\u03b6\0\u03e8\0\u041a"+
    "\0\u044c\0\u047e\0\u04b0\0\u04e2\0\u0514\0\u0546\0\u0578\0\u05aa"+
    "\0\u05dc\0\u060e\0\u0640\0\310\0\u0672\0\u06a4\0\310\0\u06d6"+
    "\0\u0708\0\u073a\0\u076c\0\u079e\0\310\0\u07d0\0\u0802\0\u0834"+
    "\0\u0866\0\310\0\u0898\0\310\0\u08ca\0\u08fc\0\u092e\0\u0960"+
    "\0\u0992\0\u09c4\0\u09f6\0\u0a28\0\372\0\310\0\u0a5a\0\u0a8c"+
    "\0\u0abe\0\u0af0\0\u0b22\0\310\0\310\0\u0b54\0\u0b86\0\u0bb8"+
    "\0\u0bea\0\u0c1c\0\u0c4e\0\u0c80\0\u0cb2\0\u0ce4\0\u0d16\0\u0d48"+
    "\0\u0d7a\0\u0dac\0\u0dde\0\u0e10\0\u0e42\0\u0e74\0\u0ea6\0\u0ed8"+
    "\0\u0f0a\0\u0f3c\0\u0f6e\0\310\0\u0fa0\0\310\0\u0fd2\0\u1004"+
    "\0\u1036\0\u1068\0\u109a\0\u10cc\0\u10fe\0\u1130\0\u1162\0\u1194"+
    "\0\u11c6\0\u11f8\0\u122a\0\u125c\0\u128e\0\u0992\0\u12c0\0\310"+
    "\0\310\0\310\0\u12f2\0\u1324\0\u1356\0\u1388\0\u13ba\0\u13ec"+
    "\0\u141e\0\u1450\0\u1482\0\u14b4\0\u14e6\0\u1518\0\u154a\0\u157c"+
    "\0\u15ae\0\u15e0\0\u1612\0\u1644\0\u1676\0\u16a8\0\310\0\u16da"+
    "\0\u170c\0\u173e\0\u1770\0\u17a2\0\u17d4\0\u1806\0\u1838\0\u186a"+
    "\0\u189c\0\u18ce\0\u1900\0\u1932\0\u1964\0\u1996\0\u19c8\0\u19fa"+
    "\0\u1a2c\0\u1a5e\0\u1a90\0\u1ac2\0\u1af4\0\u1b26\0\u1b58\0\u1b8a"+
    "\0\u1bbc\0\u1bee\0\u1c20\0\u1c52\0\u1c84\0\u1cb6\0\u1ce8\0\u1d1a"+
    "\0\u1d4c\0\u1d7e\0\u1db0\0\u1de2\0\u1e14\0\u1e46\0\u1e78\0\u1eaa"+
    "\0\u1edc\0\u1f0e\0\u1c20\0\u1f40\0\u1cb6\0\u1f72\0\u1fa4\0\u1fd6";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[184];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\5\2\6\1\7\1\10\2\6\1\11\1\6\1\12"+
    "\1\13\2\6\1\14\1\5\1\6\1\15\1\16\1\17"+
    "\1\5\1\20\1\21\1\22\1\23\1\5\1\24\1\10"+
    "\1\5\1\6\1\25\1\26\1\27\1\30\1\31\1\5"+
    "\1\32\1\33\1\6\1\34\1\35\1\36\1\37\1\6"+
    "\1\40\3\6\1\41\2\6\23\42\1\43\1\44\1\45"+
    "\34\42\24\46\1\47\3\46\1\50\3\46\1\51\3\46"+
    "\1\52\2\46\1\53\16\46\24\54\1\55\7\54\1\56"+
    "\3\54\1\57\2\54\1\60\16\54\63\0\3\6\1\0"+
    "\1\61\10\6\1\0\2\6\13\0\6\6\1\0\17\6"+
    "\3\0\1\7\2\0\2\62\2\0\1\7\1\0\2\63"+
    "\1\0\2\64\1\65\16\0\1\64\22\0\3\6\1\0"+
    "\1\61\10\6\1\0\2\6\13\0\6\6\1\0\1\6"+
    "\1\66\15\6\1\0\3\6\1\0\1\61\1\6\1\67"+
    "\6\6\1\0\2\6\13\0\6\6\1\0\1\6\1\70"+
    "\15\6\3\0\1\7\2\0\2\62\2\0\1\7\1\71"+
    "\2\63\1\0\2\64\1\65\16\0\1\64\22\0\3\6"+
    "\1\0\1\61\1\6\1\72\3\6\1\73\2\6\1\0"+
    "\2\6\13\0\6\6\1\0\17\6\1\0\3\6\1\0"+
    "\1\61\7\6\1\74\1\0\2\6\13\0\6\6\1\0"+
    "\4\6\1\75\12\6\3\0\1\65\6\0\1\65\47\0"+
    "\22\17\1\76\1\77\36\17\23\100\1\101\1\100\1\102"+
    "\34\100\26\103\1\0\33\103\27\0\1\104\1\105\62\0"+
    "\1\24\31\0\3\6\1\0\1\61\10\6\1\0\2\6"+
    "\13\0\1\106\5\6\1\0\3\6\1\107\2\6\1\110"+
    "\10\6\1\0\3\6\1\0\1\61\10\6\1\0\2\6"+
    "\13\0\6\6\1\0\1\6\1\111\1\6\1\112\13\6"+
    "\1\0\3\6\1\0\1\61\7\6\1\113\1\0\2\6"+
    "\13\0\6\6\1\0\12\6\1\114\4\6\1\0\3\6"+
    "\1\0\1\61\10\6\1\0\2\6\13\0\5\6\1\115"+
    "\1\0\1\6\1\116\2\6\1\117\12\6\1\0\3\6"+
    "\1\0\1\61\10\6\1\0\2\6\13\0\4\6\1\75"+
    "\1\6\1\0\10\6\1\120\6\6\1\0\3\6\1\0"+
    "\1\61\10\6\1\0\2\6\13\0\1\121\4\6\1\122"+
    "\1\0\17\6\1\0\3\6\1\0\1\61\10\6\1\0"+
    "\2\6\13\0\6\6\1\0\2\6\1\123\14\6\1\0"+
    "\3\6\1\0\1\61\7\6\1\124\1\0\2\6\13\0"+
    "\6\6\1\0\17\6\1\0\3\6\1\0\1\61\10\6"+
    "\1\0\2\6\13\0\6\6\1\0\2\6\1\125\11\6"+
    "\1\126\2\6\1\0\3\6\1\0\1\61\7\6\1\127"+
    "\1\0\2\6\13\0\6\6\1\0\12\6\1\130\4\6"+
    "\1\0\3\6\1\0\1\61\10\6\1\0\2\6\13\0"+
    "\5\6\1\131\1\0\17\6\1\0\3\6\1\0\1\61"+
    "\10\6\1\0\2\6\13\0\6\6\1\0\1\6\1\132"+
    "\15\6\1\0\3\6\1\0\1\61\10\6\1\0\2\6"+
    "\13\0\6\6\1\0\1\6\1\133\15\6\23\42\3\0"+
    "\34\42\24\134\1\0\35\134\25\0\1\135\34\0\24\46"+
    "\1\0\3\46\1\0\3\46\1\0\3\46\1\0\2\46"+
    "\1\0\16\46\27\0\1\136\67\0\1\137\61\0\1\140"+
    "\3\0\1\141\63\0\1\142\16\0\24\54\1\0\7\54"+
    "\1\0\3\54\1\0\2\54\1\0\16\54\35\0\1\143"+
    "\61\0\1\144\3\0\1\145\63\0\1\146\16\0\1\147"+
    "\3\6\1\0\1\61\10\6\1\147\2\6\1\0\7\147"+
    "\1\0\2\147\6\6\1\147\17\6\3\0\1\150\6\0"+
    "\1\150\3\0\1\151\46\0\1\65\6\0\1\65\1\0"+
    "\2\63\1\0\2\64\17\0\1\64\22\0\3\6\1\0"+
    "\1\61\10\6\1\0\2\6\13\0\6\6\1\0\11\6"+
    "\1\152\5\6\1\0\3\6\1\0\1\61\10\6\1\0"+
    "\2\6\13\0\6\6\1\0\1\6\1\153\15\6\1\0"+
    "\3\6\1\0\1\61\10\6\1\0\2\6\13\0\1\6"+
    "\1\154\1\6\1\155\2\6\1\0\17\6\3\0\1\156"+
    "\4\0\3\156\1\0\2\156\1\0\2\156\17\0\1\156"+
    "\3\0\2\156\15\0\3\6\1\0\1\61\10\6\1\0"+
    "\2\6\13\0\3\6\1\155\2\6\1\0\17\6\1\0"+
    "\3\6\1\0\1\61\10\6\1\0\2\6\13\0\1\6"+
    "\1\157\4\6\1\0\17\6\1\0\3\6\1\0\1\61"+
    "\10\6\1\0\2\6\13\0\4\6\1\75\1\6\1\0"+
    "\17\6\24\17\1\0\35\17\23\100\1\101\1\100\1\160"+
    "\60\100\1\0\35\100\25\0\1\161\34\0\26\103\1\162"+
    "\33\103\1\0\3\6\1\0\1\61\10\6\1\0\2\6"+
    "\13\0\5\6\1\163\1\0\3\6\1\164\13\6\1\0"+
    "\3\6\1\0\1\61\10\6\1\0\2\6\13\0\6\6"+
    "\1\0\1\6\1\165\4\6\1\75\3\6\1\155\4\6"+
    "\1\0\3\6\1\0\1\61\10\6\1\0\2\6\13\0"+
    "\2\6\1\155\3\6\1\0\17\6\1\0\3\6\1\0"+
    "\1\61\3\6\1\166\4\6\1\0\2\6\13\0\6\6"+
    "\1\0\17\6\1\0\3\6\1\0\1\61\10\6\1\0"+
    "\2\6\13\0\5\6\1\167\1\0\4\6\1\170\12\6"+
    "\1\0\3\6\1\0\1\61\10\6\1\0\2\6\13\0"+
    "\6\6\1\0\1\6\1\171\15\6\1\0\3\6\1\0"+
    "\1\61\10\6\1\0\2\6\13\0\2\6\1\172\3\6"+
    "\1\0\17\6\1\0\3\6\1\0\1\61\10\6\1\0"+
    "\2\6\13\0\6\6\1\0\5\6\1\173\11\6\1\0"+
    "\3\6\1\0\1\61\1\6\1\72\6\6\1\0\2\6"+
    "\13\0\6\6\1\0\17\6\1\0\3\6\1\0\1\61"+
    "\10\6\1\0\2\6\13\0\6\6\1\0\3\6\1\174"+
    "\13\6\1\0\3\6\1\0\1\61\10\6\1\0\2\6"+
    "\13\0\2\6\1\175\3\6\1\0\17\6\1\0\3\6"+
    "\1\0\1\61\10\6\1\0\2\6\13\0\5\6\1\176"+
    "\1\0\17\6\1\0\3\6\1\0\1\61\10\6\1\0"+
    "\2\6\13\0\1\6\1\177\4\6\1\0\17\6\1\0"+
    "\3\6\1\0\1\61\10\6\1\0\2\6\13\0\3\6"+
    "\1\200\2\6\1\0\17\6\1\0\1\6\1\201\1\6"+
    "\1\0\1\61\10\6\1\0\2\6\13\0\1\6\1\202"+
    "\4\6\1\0\17\6\1\0\3\6\1\0\1\61\10\6"+
    "\1\0\2\6\13\0\6\6\1\0\13\6\1\203\3\6"+
    "\1\0\3\6\1\0\1\61\7\6\1\204\1\0\2\6"+
    "\13\0\6\6\1\0\17\6\1\0\3\6\1\0\1\61"+
    "\10\6\1\0\2\6\13\0\6\6\1\0\1\75\16\6"+
    "\1\0\3\6\1\0\1\61\1\6\1\205\6\6\1\0"+
    "\2\6\13\0\6\6\1\0\17\6\1\0\3\6\1\0"+
    "\1\61\7\6\1\206\1\0\2\6\13\0\6\6\1\0"+
    "\17\6\1\0\3\6\1\0\1\61\10\6\1\0\2\6"+
    "\13\0\1\6\1\154\4\6\1\0\17\6\1\0\3\6"+
    "\1\0\1\61\1\6\1\75\6\6\1\0\2\6\13\0"+
    "\6\6\1\0\3\6\1\75\13\6\25\0\1\207\71\0"+
    "\1\210\62\0\1\211\32\0\1\212\115\0\1\213\53\0"+
    "\1\214\62\0\1\215\32\0\1\216\115\0\1\217\16\0"+
    "\1\147\15\0\1\147\3\0\7\147\1\0\2\147\6\0"+
    "\1\147\22\0\1\150\6\0\1\150\4\0\2\64\17\0"+
    "\1\64\24\0\1\150\6\0\1\150\50\0\3\6\1\0"+
    "\1\61\10\6\1\0\2\6\13\0\6\6\1\0\6\6"+
    "\1\75\10\6\1\0\3\6\1\0\1\61\10\6\1\0"+
    "\2\6\13\0\3\6\1\163\2\6\1\0\17\6\1\0"+
    "\3\6\1\0\1\61\3\6\1\177\4\6\1\0\2\6"+
    "\13\0\6\6\1\0\17\6\1\0\3\6\1\0\1\61"+
    "\7\6\1\75\1\0\2\6\13\0\6\6\1\0\17\6"+
    "\1\0\3\6\1\0\1\61\7\6\1\220\1\0\2\6"+
    "\13\0\6\6\1\0\17\6\1\0\3\6\1\0\1\61"+
    "\10\6\1\0\2\6\13\0\3\6\1\75\2\6\1\0"+
    "\17\6\1\0\3\6\1\0\1\61\10\6\1\0\2\6"+
    "\13\0\6\6\1\0\4\6\1\127\12\6\1\0\3\6"+
    "\1\0\1\61\10\6\1\0\2\6\13\0\5\6\1\221"+
    "\1\0\17\6\1\0\3\6\1\0\1\61\10\6\1\0"+
    "\2\6\13\0\6\6\1\0\15\6\1\222\1\6\1\0"+
    "\3\6\1\0\1\61\10\6\1\0\2\6\13\0\6\6"+
    "\1\0\14\6\1\223\2\6\1\0\3\6\1\0\1\61"+
    "\10\6\1\0\2\6\13\0\1\6\1\224\4\6\1\0"+
    "\17\6\1\0\3\6\1\0\1\61\1\6\1\225\6\6"+
    "\1\0\2\6\13\0\6\6\1\0\17\6\1\0\3\6"+
    "\1\0\1\61\7\6\1\226\1\0\2\6\13\0\6\6"+
    "\1\0\17\6\1\0\3\6\1\0\1\61\10\6\1\0"+
    "\2\6\13\0\6\6\1\0\1\6\1\227\15\6\1\0"+
    "\3\6\1\0\1\61\10\6\1\0\2\6\13\0\6\6"+
    "\1\0\7\6\1\230\7\6\1\0\3\6\1\0\1\61"+
    "\1\6\1\231\6\6\1\0\2\6\13\0\6\6\1\0"+
    "\4\6\1\232\12\6\1\0\3\6\1\0\1\61\1\6"+
    "\1\155\6\6\1\0\2\6\13\0\6\6\1\0\17\6"+
    "\1\0\3\6\1\0\1\61\10\6\1\0\2\6\13\0"+
    "\1\75\5\6\1\0\17\6\1\0\3\6\1\0\1\61"+
    "\10\6\1\0\2\6\13\0\1\6\1\233\4\6\1\0"+
    "\17\6\1\0\3\6\1\0\1\61\10\6\1\0\2\6"+
    "\13\0\6\6\1\0\12\6\1\234\4\6\1\0\3\6"+
    "\1\0\1\61\10\6\1\0\2\6\13\0\6\6\1\0"+
    "\12\6\1\235\4\6\1\0\3\6\1\0\1\61\7\6"+
    "\1\236\1\0\2\6\13\0\6\6\1\0\17\6\1\0"+
    "\3\6\1\0\1\61\10\6\1\0\2\6\13\0\6\6"+
    "\1\0\3\6\1\237\13\6\1\0\3\6\1\0\1\61"+
    "\1\6\1\75\6\6\1\0\2\6\13\0\6\6\1\0"+
    "\17\6\1\0\3\6\1\0\1\61\1\6\1\240\6\6"+
    "\1\0\2\6\13\0\6\6\1\0\17\6\36\0\1\241"+
    "\65\0\1\242\34\0\1\211\65\0\1\243\76\0\1\244"+
    "\65\0\1\245\34\0\1\215\65\0\1\246\41\0\3\6"+
    "\1\0\1\61\10\6\1\0\2\6\13\0\6\6\1\0"+
    "\5\6\1\247\11\6\1\0\3\6\1\0\1\61\10\6"+
    "\1\0\2\6\13\0\1\6\1\75\4\6\1\0\17\6"+
    "\1\0\3\6\1\0\1\61\10\6\1\0\2\6\13\0"+
    "\6\6\1\0\1\6\1\250\15\6\1\0\3\6\1\0"+
    "\1\61\10\6\1\0\2\6\13\0\6\6\1\0\1\6"+
    "\1\251\15\6\1\0\3\6\1\0\1\61\7\6\1\252"+
    "\1\0\2\6\13\0\6\6\1\0\17\6\1\0\3\6"+
    "\1\0\1\61\7\6\1\240\1\0\2\6\13\0\6\6"+
    "\1\0\17\6\1\0\3\6\1\0\1\61\10\6\1\0"+
    "\2\6\13\0\6\6\1\0\3\6\1\75\13\6\1\0"+
    "\3\6\1\0\1\61\1\6\1\253\6\6\1\0\2\6"+
    "\13\0\6\6\1\0\17\6\1\0\3\6\1\0\1\61"+
    "\10\6\1\0\2\6\13\0\6\6\1\0\4\6\1\254"+
    "\12\6\1\0\3\6\1\0\1\61\10\6\1\0\2\6"+
    "\13\0\5\6\1\255\1\0\17\6\1\0\3\6\1\0"+
    "\1\61\10\6\1\0\2\6\13\0\6\6\1\0\3\6"+
    "\1\221\13\6\1\0\3\6\1\0\1\61\10\6\1\0"+
    "\2\6\13\0\6\6\1\0\3\6\1\256\13\6\1\0"+
    "\3\6\1\0\1\61\10\6\1\0\2\6\13\0\5\6"+
    "\1\257\1\0\17\6\1\0\3\6\1\0\1\61\10\6"+
    "\1\0\2\6\13\0\6\6\1\0\3\6\1\260\13\6"+
    "\1\0\3\6\1\0\1\61\3\6\1\221\4\6\1\0"+
    "\2\6\13\0\6\6\1\0\17\6\1\0\3\6\1\0"+
    "\1\61\10\6\1\0\2\6\13\0\6\6\1\0\3\6"+
    "\1\261\13\6\1\0\3\6\1\0\1\61\10\6\1\0"+
    "\1\6\1\75\13\0\6\6\1\0\17\6\37\0\1\211"+
    "\2\0\1\242\46\0\1\262\33\0\3\243\1\263\11\243"+
    "\1\263\2\243\2\263\4\0\1\243\1\263\2\0\1\263"+
    "\6\243\1\263\17\243\37\0\1\215\2\0\1\245\46\0"+
    "\1\264\33\0\3\246\1\265\11\246\1\265\2\246\2\265"+
    "\4\0\1\246\1\265\2\0\1\265\6\246\1\265\17\246"+
    "\1\0\3\6\1\0\1\61\10\6\1\0\1\6\1\163"+
    "\13\0\6\6\1\0\17\6\1\0\3\6\1\0\1\61"+
    "\10\6\1\0\2\6\13\0\6\6\1\0\16\6\1\155"+
    "\1\0\3\6\1\0\1\61\10\6\1\0\2\6\13\0"+
    "\1\6\1\155\4\6\1\0\17\6\1\0\3\6\1\0"+
    "\1\61\3\6\1\266\4\6\1\0\2\6\13\0\6\6"+
    "\1\0\17\6\1\0\3\6\1\0\1\61\1\6\1\152"+
    "\6\6\1\0\2\6\13\0\6\6\1\0\17\6\1\0"+
    "\3\6\1\0\1\61\10\6\1\0\2\6\13\0\6\6"+
    "\1\0\10\6\1\155\6\6\1\0\3\6\1\0\1\61"+
    "\3\6\1\165\4\6\1\0\2\6\13\0\6\6\1\0"+
    "\17\6\1\0\3\6\1\0\1\61\10\6\1\0\2\6"+
    "\13\0\6\6\1\0\1\6\1\236\15\6\1\0\3\6"+
    "\1\0\1\61\10\6\1\0\2\6\13\0\6\6\1\0"+
    "\3\6\1\267\13\6\1\0\3\6\1\0\1\61\10\6"+
    "\1\0\2\6\13\0\6\6\1\0\5\6\1\75\11\6"+
    "\1\0\3\6\1\0\1\61\10\6\1\0\2\6\13\0"+
    "\5\6\1\270\1\0\17\6\27\0\1\243\61\0\1\246"+
    "\33\0\3\6\1\0\1\61\10\6\1\0\2\6\13\0"+
    "\1\6\1\225\4\6\1\0\17\6\1\0\3\6\1\0"+
    "\1\61\7\6\1\163\1\0\2\6\13\0\6\6\1\0"+
    "\17\6\1\0\3\6\1\0\1\61\10\6\1\0\1\6"+
    "\1\155\13\0\6\6\1\0\17\6";

  private static int [] zzUnpackTrans() {
    int [] result = new int[8200];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\1\2\0\1\11\2\1\1\11\7\1\1\11"+
    "\23\1\1\11\2\1\1\11\5\1\1\11\4\1\1\11"+
    "\1\0\1\11\4\1\1\0\4\1\1\11\5\1\2\11"+
    "\26\1\1\11\1\0\1\11\10\0\2\1\1\0\6\1"+
    "\3\11\24\1\1\11\10\0\21\1\2\0\1\1\2\0"+
    "\14\1\4\0\3\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[184];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /* user code: */


	/**
	 * Constructor.  This must be here because JFlex does not generate a
	 * no-parameter constructor.
	 */
	public ScalaTokenMaker() {
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 * @see #addToken(int, int, int)
	 */
	private void addHyperlinkToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so, true);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 */
	private void addToken(int tokenType) {
		addToken(zzStartRead, zzMarkedPos-1, tokenType);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 * @see #addHyperlinkToken(int, int, int)
	 */
	private void addToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so, false);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param array The character array.
	 * @param start The starting offset in the array.
	 * @param end The ending offset in the array.
	 * @param tokenType The token's type.
	 * @param startOffset The offset in the document at which this token
	 *                    occurs.
	 * @param hyperlink Whether this token is a hyperlink.
	 */
	@Override
	public void addToken(char[] array, int start, int end, int tokenType,
						int startOffset, boolean hyperlink) {
		super.addToken(array, start,end, tokenType, startOffset, hyperlink);
		zzStartRead = zzMarkedPos;
	}


	/**
	 * Returns the text to place at the beginning and end of a
	 * line to "comment" it in a this programming language.
	 *
	 * @return The start and end strings to add to a line to "comment"
	 *         it out.
	 */
	@Override
	public String[] getLineCommentStartAndEnd() {
		return new String[] { "//", null };
	}


	/**
	 * Returns the first token in the linked list of tokens generated
	 * from <code>text</code>.  This method must be implemented by
	 * subclasses so they can correctly implement syntax highlighting.
	 *
	 * @param text The text from which to get tokens.
	 * @param initialTokenType The token type we should start with.
	 * @param startOffset The offset into the document at which
	 *        <code>text</code> starts.
	 * @return The first <code>Token</code> in a linked list representing
	 *         the syntax highlighted text.
	 */
	public Token getTokenList(Segment text, int initialTokenType, int startOffset) {

		resetTokenList();
		this.offsetShift = -text.offset + startOffset;

		// Start off in the proper state.
		int state = YYINITIAL;
		switch (initialTokenType) {
			case Token.LITERAL_STRING_DOUBLE_QUOTE:
				state = MULTILINE_STRING_DOUBLE;
				break;
			case Token.COMMENT_MULTILINE:
				state = MLC;
				break;
			default:
				state = YYINITIAL;
		}

		s = text;
		start = text.offset;
		try {
			yyreset(zzReader);
			yybegin(state);
			return yylex();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return new TokenImpl();
		}

	}


	/**
	 * Refills the input buffer.
	 *
	 * @return      <code>true</code> if EOF was reached, otherwise
	 *              <code>false</code>.
	 * @exception   IOException  if any I/O-Error occurs.
	 */
	private boolean zzRefill() throws java.io.IOException {
		return zzCurrentPos>=s.offset+s.count;
	}


	/**
	 * Resets the scanner to read from a new input stream.
	 * Does not close the old reader.
	 *
	 * All internal variables are reset, the old input stream 
	 * <b>cannot</b> be reused (internal buffer is discarded and lost).
	 * Lexical state is set to <tt>YY_INITIAL</tt>.
	 *
	 * @param reader   the new input stream 
	 */
	public final void yyreset(java.io.Reader reader) throws IOException {
		// 's' has been updated.
		zzBuffer = s.array;
		/*
		 * We replaced the line below with the two below it because zzRefill
		 * no longer "refills" the buffer (since the way we do it, it's always
		 * "full" the first time through, since it points to the segment's
		 * array).  So, we assign zzEndRead here.
		 */
		//zzStartRead = zzEndRead = s.offset;
		zzStartRead = s.offset;
		zzEndRead = zzStartRead + s.count - 1;
		zzCurrentPos = zzMarkedPos = s.offset;
		zzLexicalState = YYINITIAL;
		zzReader = reader;
		zzAtEOF  = false;
	}




  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public ScalaTokenMaker(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public ScalaTokenMaker(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 150) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  @Override
public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public com.fr.design.gui.syntax.ui.rsyntaxtextarea.Token yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = zzLexicalState;


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 6: 
          { addNullToken(); return firstToken;
          }
        case 26: break;
        case 15: 
          { addToken(Token.LITERAL_CHAR);
          }
        case 27: break;
        case 21: 
          { start = zzMarkedPos-3; yybegin(MULTILINE_STRING_DOUBLE);
          }
        case 28: break;
        case 19: 
          { yybegin(YYINITIAL); addToken(start,zzStartRead+1, Token.COMMENT_MULTILINE);
          }
        case 29: break;
        case 18: 
          { start = zzMarkedPos-2; yybegin(MLC);
          }
        case 30: break;
        case 8: 
          { addToken(Token.WHITESPACE);
          }
        case 31: break;
        case 20: 
          { addToken(Token.LITERAL_NUMBER_HEXADECIMAL);
          }
        case 32: break;
        case 13: 
          { addToken(Token.LITERAL_NUMBER_FLOAT);
          }
        case 33: break;
        case 14: 
          { addToken(Token.RESERVED_WORD);
          }
        case 34: break;
        case 4: 
          { addToken(Token.SEPARATOR);
          }
        case 35: break;
        case 22: 
          { addToken(Token.LITERAL_BACKQUOTE);
          }
        case 36: break;
        case 9: 
          { /* Skip escaped chars, handles case: '\"""'. */
          }
        case 37: break;
        case 2: 
          { addToken(Token.IDENTIFIER);
          }
        case 38: break;
        case 12: 
          { addToken(start,zzStartRead-1, Token.COMMENT_EOL); addNullToken(); return firstToken;
          }
        case 39: break;
        case 17: 
          { start = zzMarkedPos-2; yybegin(EOL_COMMENT);
          }
        case 40: break;
        case 23: 
          { addToken(start,zzStartRead+2, Token.LITERAL_STRING_DOUBLE_QUOTE); yybegin(YYINITIAL);
          }
        case 41: break;
        case 5: 
          { addToken(Token.ERROR_CHAR); addNullToken(); return firstToken;
          }
        case 42: break;
        case 7: 
          { addToken(Token.ERROR_STRING_DOUBLE); addNullToken(); return firstToken;
          }
        case 43: break;
        case 16: 
          { addToken(Token.LITERAL_STRING_DOUBLE_QUOTE);
          }
        case 44: break;
        case 10: 
          { addToken(start,zzStartRead-1, Token.LITERAL_STRING_DOUBLE_QUOTE); return firstToken;
          }
        case 45: break;
        case 25: 
          { int temp=zzStartRead; addToken(start,zzStartRead-1, Token.COMMENT_EOL); addHyperlinkToken(temp,zzMarkedPos-1, Token.COMMENT_EOL); start = zzMarkedPos;
          }
        case 46: break;
        case 24: 
          { int temp=zzStartRead; addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); addHyperlinkToken(temp,zzMarkedPos-1, Token.COMMENT_MULTILINE); start = zzMarkedPos;
          }
        case 47: break;
        case 3: 
          { addToken(Token.LITERAL_NUMBER_DECIMAL_INT);
          }
        case 48: break;
        case 1: 
          { 
          }
        case 49: break;
        case 11: 
          { addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); return firstToken;
          }
        case 50: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            switch (zzLexicalState) {
            case EOL_COMMENT: {
              addToken(start,zzStartRead-1, Token.COMMENT_EOL); addNullToken(); return firstToken;
            }
            case 185: break;
            case MULTILINE_STRING_DOUBLE: {
              addToken(start,zzStartRead-1, Token.LITERAL_STRING_DOUBLE_QUOTE); return firstToken;
            }
            case 186: break;
            case YYINITIAL: {
              addNullToken(); return firstToken;
            }
            case 187: break;
            case MLC: {
              addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); return firstToken;
            }
            case 188: break;
            default:
            return null;
            }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}