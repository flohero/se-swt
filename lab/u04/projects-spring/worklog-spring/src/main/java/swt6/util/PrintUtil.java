package swt6.util;

@SuppressWarnings("Duplicates")
public class PrintUtil {
	
	public static void printTitle(String text, int length, char ch) {	
		StringBuilder sb = new StringBuilder(length);
		int n = (length - (text.length() + 2))/2;
		sb.append(String.valueOf(ch).repeat(Math.max(0, n)));
		sb.append(" ").append(text).append(" ");
		sb.append(String.valueOf(ch).repeat(Math.max(0, length - (n + text.length() + 2))));
		
		System.out.println(sb);
	}

	public static void printTitle(String text, int length) {	
	  printTitle(text, length, '=');
	}
	
	public static void printSeparator(int length, char ch) {
		StringBuilder sb = new StringBuilder(length);
		sb.append(String.valueOf(ch).repeat(Math.max(0, length)));
		System.out.println(sb);
	}
	
	public static void printSeparator(int length) {	
		printSeparator(length, '=');
	}

	public static void println() {
		System.out.println();
	}

}
