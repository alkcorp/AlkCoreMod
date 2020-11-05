package alkcoremod.utils.data;

import alkcoremod.objects.data.AutoMap;

public class StringUtils {

	public static String superscript(String str) {
		str = str.replaceAll("0", "\u2070");
		str = str.replaceAll("1", "\u00B9");
		str = str.replaceAll("2", "\u00B2");
		str = str.replaceAll("3", "\u00B3");
		str = str.replaceAll("4", "\u2074");
		str = str.replaceAll("5", "\u2075");
		str = str.replaceAll("6", "\u2076");
		str = str.replaceAll("7", "\u2077");
		str = str.replaceAll("8", "\u2078");
		str = str.replaceAll("9", "\u2079");
		return str;
	}

	public static String subscript(String str) {
		str = str.replaceAll("0", "\u2080");
		str = str.replaceAll("1", "\u2081");
		str = str.replaceAll("2", "\u2082");
		str = str.replaceAll("3", "\u2083");
		str = str.replaceAll("4", "\u2084");
		str = str.replaceAll("5", "\u2085");
		str = str.replaceAll("6", "\u2086");
		str = str.replaceAll("7", "\u2087");
		str = str.replaceAll("8", "\u2088");
		str = str.replaceAll("9", "\u2089");
		return str;
	}

	public static boolean containsSuperOrSubScript(final String s){
		if (s.contains(StringUtils.superscript("0"))) {
			return true;
		}
		else if (s.contains(StringUtils.superscript("1"))) {
			return true;
		}
		else if (s.contains(StringUtils.superscript("2"))) {
			return true;
		}
		else if (s.contains(StringUtils.superscript("3"))) {
			return true;
		}
		else if (s.contains(StringUtils.superscript("4"))) {
			return true;
		}
		else if (s.contains(StringUtils.superscript("5"))) {
			return true;
		}
		else if (s.contains(StringUtils.superscript("6"))) {
			return true;
		}
		else if (s.contains(StringUtils.superscript("7"))) {
			return true;
		}
		else if (s.contains(StringUtils.superscript("8"))) {
			return true;
		}
		else if (s.contains(StringUtils.superscript("9"))) {
			return true;
		}
		if (s.contains(StringUtils.subscript("0"))) {
			return true;
		}
		else if (s.contains(StringUtils.subscript("1"))) {
			return true;
		}
		else if (s.contains(StringUtils.subscript("2"))) {
			return true;
		}
		else if (s.contains(StringUtils.subscript("3"))) {
			return true;
		}
		else if (s.contains(StringUtils.subscript("4"))) {
			return true;
		}
		else if (s.contains(StringUtils.subscript("5"))) {
			return true;
		}
		else if (s.contains(StringUtils.subscript("6"))) {
			return true;
		}
		else if (s.contains(StringUtils.subscript("7"))) {
			return true;
		}
		else if (s.contains(StringUtils.subscript("8"))) {
			return true;
		}
		else if (s.contains(StringUtils.subscript("9"))) {
			return true;
		}
		return false;
	}

	//Can call this Enum for formatting.
	public static enum TextUtils {
		blue('1'), green('2'), teal('3'), maroon('4'), purple('5'), orange('6'), lightGray('7'), darkGray('8'), lightBlue(
				'9'), black('0'), lime('a'), aqua('b'), red('c'), pink('d'), yellow('e'), white('f');

		private char colourValue;
		private TextUtils(final char value) {
			this.colourValue = value;
		}
		public String colour() {
			return getFormatter() + this.colourValue;
		}
		private String getFormatter() {		
			return "\u00A7"; // Returns §.
		}
	}	

	public static String firstLetterCaps(String data) {
		String firstLetter = data.substring(0,1).toUpperCase();
		String restLetters = data.substring(1).toLowerCase();
		return firstLetter + restLetters;
	}

	public static <V> String getDataStringFromArray(V[] parameterTypes) {
		if (parameterTypes == null || parameterTypes.length == 0) {
			return "empty/null";
		}
		else {
			String aData = "";
			for (V y : parameterTypes) {
				if (y != null) {
					aData += ", "+y.toString();
				}
			}
			return aData;
		}		
	}
	
	
	
	/**
	 * Is this a special regex character for delimination? (.$|()[]{}^?*+\\)
	 * @param aChar - The char to test
	 * @return - Is this a special character?
	 */
	public static boolean isSpecialCharacter(char aChar) {
		if (aChar == '"' || aChar == '.' || aChar == '$' || aChar == '|' || aChar == '(' || aChar == ')' || aChar == '['
				|| aChar == ']' || aChar == '{' || aChar == '}' || aChar == '^' || aChar == '?' || aChar == '*'
				|| aChar == '+' || aChar == '\\') {
			return true;
		}
		return false;
	}
	
	public static boolean isEscaped(String aString) {
		return aString.substring(0, 1).equals("\\");
	}
	
	public static String splitAndUppercase(String aInput, String aDelim) {
		
		if (!isEscaped(aDelim)) {
			boolean isSpecial = false;
			for (int o=0;o<aInput.length();o++) {
				if (isSpecialCharacter(aInput.charAt(o))) {
					isSpecial = true;
				}
			}
			if (isSpecial) {
				aDelim = "\\"+aDelim;
			}
		}		
		
		String[] aSplit = aInput.split(aDelim);
		if (aSplit == null || aSplit.length == 0) {
			return aInput;
		}
		else {
			AutoMap<String> aTemp = new AutoMap<String>();
			for (String s : aSplit) {
				s = s.replace(".", "");
				s = sanitizeString(s);
				s = firstLetterCaps(s);
				aTemp.put(s);
			}
			String aReturn = "";
			for (String s : aTemp) {
				aReturn += s;
			}
			return aReturn;
		}	
	}
	
	public static int characterCount(String aString, char aChar) {
		return characterCount(aString, ""+aChar);
	}
	
	public static int characterCount(String aString, String aChar) {
		int aLength = aString.length();
		int aFound = 0;
		if (aLength == 0 || !aString.contains(aChar)) {
			return 0;
		}
		else {
			for (int index = 0; index < aLength; index++) {
				if (aString.substring(index, index+1).equals(aChar)) {
					aFound++;
				}
			}
			return aFound;
		}
	}

	public static String sanitizeString(final String input) {		
		char[] aKeep = new char[6];
		aKeep[0] = '~';
		aKeep[1] = '$';
		aKeep[2] = '%';
		aKeep[3] = '^';
		aKeep[4] = '&';
		aKeep[5] = '*';
		return sanitizeString(input, aKeep);
	}

	public static String sanitizeStringKeepBrackets(final String input) {
		char[] aKeepBrackets = new char[6];
		aKeepBrackets[0] = '[';
		aKeepBrackets[1] = ']';
		aKeepBrackets[2] = '{';
		aKeepBrackets[3] = '}';
		aKeepBrackets[4] = '(';
		aKeepBrackets[5] = ')';
		return sanitizeString(input, aKeepBrackets); 
	}
	
	public static String sanitizeString(final String input, final char[] aDontRemove) {		
		String output;
		AutoMap<String> aToRemoveMap = new AutoMap<String>();
		aToRemoveMap.put(" ");
		aToRemoveMap.put("-");
		aToRemoveMap.put("_");
		aToRemoveMap.put("~");
		aToRemoveMap.put("?");
		aToRemoveMap.put("!");
		aToRemoveMap.put("@");
		aToRemoveMap.put("#");
		aToRemoveMap.put("$");
		aToRemoveMap.put("%");
		aToRemoveMap.put("^");
		aToRemoveMap.put("&");
		aToRemoveMap.put("*");
		aToRemoveMap.put("(");
		aToRemoveMap.put(")");
		aToRemoveMap.put("{");
		aToRemoveMap.put("}");
		aToRemoveMap.put("[");
		aToRemoveMap.put("]");
		aToRemoveMap.put(" ");		
		for (String s : aToRemoveMap) {
			for (char e : aDontRemove) {
			if (s.charAt(0) == e) {
				aToRemoveMap.remove(s);
			}
			}
		}		
		output = input;
		for (String A : aToRemoveMap) {
			output = output.replace(A, "");
		}		
		return output;
	}
}
