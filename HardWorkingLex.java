import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

public class HardWorkingLex {
    static Reader fileReader = null;
    static String charClass = "";
    static Character nextChar = '.';
    static int data = 0;
    static int lexLen = 0;
    static char[] lexeme = new char[100];
    static int nextToken;
    static HashMap<String, Integer> hashie = new HashMap<>();
    static ArrayList<String> orderedList = new ArrayList<String>();
    static boolean hex = false;
    static boolean isFloatingPoint = false;
    static String checkInput = "";



    public static void main(String[] args) {
        // I USED ~ AS A SPECIAL SYMBOL TO IDENTIFY KEYWORDS
        // EXAMPLE: ~1 IS A STRING KEYWORD, ~2 IS AN INTEGER KEYWORD, ETC.
        // DETAILED ON 198-212

        initializeHashie();



        try {
            String filePath = new File("").getAbsolutePath();
            fileReader = new FileReader(filePath + "/src/test");

            data = 0;
            while(data != -1) {
                //do something with data...
                data = fileReader.read();
                getChar();
                do {
                    lex();
                } while (nextToken != -1);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }

        }
        for(int i = 0; i< orderedList.size(); i++) {
            System.out.print(orderedList.get(i) + ",");
        }
    }



    public static void initializeHashie() {
        hashie.put("INT_LIT", 10);
        hashie.put("IDENT", 11);
        hashie.put("PRIV_IDENT", 12);
        hashie.put("CHAR", 13);
        hashie.put("STRING_LIT", 14);
        hashie.put("FLOAT", 15);
        hashie.put("STRING_KEYWORD", 16);
        hashie.put("INTEGER_KEYWORD", 17);
        hashie.put("CHARACTER_KEYWORD", 18);
        hashie.put("FLOAT_KEYWORD", 19);
        hashie.put("OID_KEYWORD", 20);
        hashie.put("ASSIGN_OP", 28);
        hashie.put("ADD_OP", 21);
        hashie.put("SUB_OP", 22);
        hashie.put("MULT_OP", 23);
        hashie.put("DIV_OP", 24);
        hashie.put("LEFT_PAREN", 25);
        hashie.put("RIGHT_PAREN", 26);
        hashie.put("MOD_OP", 27);
        hashie.put("EOF", -1);
        hashie.put("AND", 28);
        hashie.put("OR", 29);
        hashie.put("NOT", 30);
        hashie.put("OPEN_BLOCK", 31);
        hashie.put("CLOSE_BLOCK", 32);
        hashie.put("OPEN_ARRAY_IND", 33);
        hashie.put("CLOSE_ARRAY_IND", 34);
        hashie.put("IF", 35);




    }



    public static void addChar() {
        if (lexLen <= 98) {
            lexeme[lexLen++] = nextChar;
        }
        else {
            System.out.println(lexLen);
            throw new RuntimeException("Error - lexeme is too long.");
        }
    }




    public static void getChar() {
            if (data != -1) {
                nextChar = (char) data;
                if (Character.isDigit(nextChar)) {
                    charClass = "DIGIT";
                }
                else if (Character.isLetter(nextChar)) {
                    nextChar = Character.toLowerCase(nextChar);
                    if (nextChar == 'u' || nextChar == 'l' || nextChar == 'i') {
                        charClass = "SUFFIX";
                    }
                    else if (nextChar == 'e' || nextChar == 'f') {
                        charClass = "FLOATSUFFIX";
                    }
                    else {
                        charClass = "LETTER";
                    }

                }
                else {
                    switch(nextChar) {
                        case '$':
                        case '%':
                        case '@':
                            charClass = "PERL";
                            break;
                        case '&':
                            charClass = "AND";
                            break;
                        case '[':
                            charClass = "OPEN_ARRAY_IND";
                            break;
                        case ']':
                            charClass = "CLOSE_ARRAY_IND";
                            break;

                        case '|':
                            charClass = "OR";
                            break;
                        case '!':
                            charClass = "NOT";
                            break;
                        case '\'':
                            charClass = "CHAR";
                            break;
                        case '=':
                            charClass = "ASSIGN_OP";
                            break;
                        case '\\':
                            charClass = "ESCAPE";
                            break;
                        case ' ':
                            charClass = "SPACE";
                            break;
                        case '_':
                            charClass = "UNDERSCORE";
                            break;
                        case '*':
                            charClass = "MULT_OP";
                            break;
                        case '~':
                            charClass = "KEYWORD";
                            break;
                        case '+':
                            charClass = "ADD_OP";
                            break;
                        case '-':
                            charClass = "SUB_OP";
                            break;
                        case '/':
                            charClass ="DIV_OP";
                            break;
                        case '•':
                            charClass = "IF_STMT";
                            break;
                        case '{':
                            charClass = "OPEN_BLOCK";
                            break;
                        case '}':
                            charClass = "CLOSE_BLOCK";
                            break;
                        case '(':
                            charClass = "LEFT_PAREN";
                            break;
                        case ')':
                            charClass = "RIGHT_PAREN";
                            break;
                        case '"':
                            charClass = "QUOTE";
                            break;
                        case '.':
                            charClass = "DIGIT";
                            isFloatingPoint = true;
                            break;
                        default:
                            charClass = "UNKNOWN";
                            break;
                    }

                }
                try {
                    data = fileReader.read();
                } catch( IOException e) {
                    System.out.println(e);
                }

            }
            else {
                charClass = "EOF";
            }
    }


    public static void lex() {
        int currentLexLen = lexLen;
        switch (charClass) {
            case "KEYWORD":
                addChar();
                switch(data) {
                    case 50:
                        nextToken = hashie.get("STRING_KEYWORD");
                        break;
                    case 51:
                        nextToken = hashie.get("INTEGER_KEYWORD");
                        break;
                    case 52:
                        nextToken = hashie.get("CHARACTER_KEYWORD");
                        break;
                    case 53:
                        nextToken = hashie.get("FLOAT_KEYWORD");
                        break;
                    case 54:
                        nextToken = hashie.get("VOID_KEYWORD");
                        break;
                    default:
                        String msg = "INCORRECT KEYWORD";
                        throw new RuntimeException(msg);
                }
                getChar();
                addChar();
                getChar();
                break;
            case "IF_STMT":
                while(!checkInput.equals("if")) {
                    addAndGetChar();
                    checkInput += nextChar;
                }
                checkInput = "";
                while(!checkInput.trim().equals("(")) {
                    addAndGetChar();
                    checkInput += nextChar;
                }
                while(!charClass.equals("RIGHT_PAREN")) {
                    addAndGetChar();
                }
                addChar();
                nextToken = hashie.get("IF");
                break;
            case "AND":
                addChar();
                getChar();
                if (charClass == "AND") {
                    addChar();
                    getChar();
                }
                nextToken = hashie.get("AND");
                break;
            case "OR":
                addChar();
                getChar();
                if (charClass == "OR") {
                    addChar();
                    getChar();
                }
                nextToken = hashie.get("OR");
                break;
            case "NOT":
                addChar();
                getChar();
                nextToken = hashie.get("NOT");
                break;
            case "PERL":
                boolean isPrivate = false;
                addChar();
                getChar();
                if (nextChar == '_') {
                    isPrivate = true;
                }
                while (charClass.equals("LETTER") || charClass.equals("DIGIT") || charClass.equals("UNDERSCORE")) {
                    addChar();
                    getChar();
                }
                if (isPrivate) {
                    nextToken = hashie.get("PRIV_IDENT");
                }
                else {
                    nextToken = hashie.get("IDENT");
                }
                break;
            case "LETTER":
                addChar();
                getChar();
                while (charClass.equals("LETTER") || charClass.equals("DIGIT")) {
                    addChar();
                    getChar();
                }
                nextToken = hashie.get("IDENT");
                break;

            case "CHAR":
                addChar();
                getChar();
                addChar();
                getChar();
                addChar();
                getChar();
                if (charClass != "SPACE" && charClass != "EOF") {
                    System.out.println("INVALID INPUT. CHAR LENGTH TOO LONG.");
                    throw new InputMismatchException();
                }
                nextToken = hashie.get("CHAR");
                break;

            case "QUOTE":
                addChar();
                getChar();
                while (!charClass.equals("QUOTE") ) {
                    if (charClass.equals("ESCAPE")) {
                        getChar();
                    }
                    addChar();
                    getChar();
                }
                addChar();
                getChar();
                nextToken = hashie.get("STRING_LIT");
                break;
            case "EOF":
                nextToken = hashie.get("EOF");
                lexeme[lexLen++] = 'E';
                lexeme[lexLen++] = 'O';
                lexeme[lexLen++] = 'F';
                break;
            case "SPACE":
                getChar();
                break;
            case "SUB_OP":
                addChar();
                getChar();
                if (!charClass.equals("DIGIT")) {
                    break;
                }
            case "DIGIT":
                addChar();
                if (nextChar == '0') {
                    getChar();
                    if (nextChar =='x') {
                        hex = true;
                        while(charClass.equals("SUB_OP") || charClass.equals("DIGIT") || charClass.equals("LETTER") || charClass.equals("SUFFIX")) {
                            addChar();
                            getChar();
                        }
                    }
                    else {
                        getChar();
                        while (charClass.equals("DIGIT") || charClass.equals("SUFFIX") || charClass.equals("FLOAT") || charClass.equals("FLOATSUFFIX") || charClass.equals("ADD_OP") || charClass.equals("SUB_OP")) {
                            if (charClass.equals("FLOAT") || charClass.equals("FLOATSUFFIX")) {
                                isFloatingPoint = true;
                            }
                            addChar();
                            getChar();
                        }
                    }
                }
                else {
                    getChar();
                    while (charClass.equals("DIGIT") || charClass.equals("SUFFIX") || charClass.equals("FLOAT") || charClass.equals("FLOATSUFFIX") || charClass.equals("ADD_OP") || charClass.equals("SUB_OP")) {
                        if (charClass.equals("FLOAT") || charClass.equals("FLOATSUFFIX")) {
                            isFloatingPoint = true;
                        }
                        addChar();
                        getChar();
                    }
                }
                nextToken = hashie.get("INT_LIT");
                break;
            default:
                nextToken = hashie.get(charClass);
                addChar();
                getChar();
                break;
        }
        String strLexeme = "";
        for (int i = currentLexLen; i< lexLen; i++) {
            if (lexeme[i] == '"') {
                strLexeme += "\"";
                continue;
            }
            strLexeme += lexeme[i];
        }
        if (!strLexeme.equals("")) {
            if (isFloatingPoint) {
                nextToken = 15;
                isFloatingPoint = false;
            }
            System.out.println("Next token is: " + nextToken + ", Next lexeme is " + strLexeme);
            orderedList.add(strLexeme);
        }




        }
        public static void addAndGetChar() {
            addChar();
            getChar();
        }



}
