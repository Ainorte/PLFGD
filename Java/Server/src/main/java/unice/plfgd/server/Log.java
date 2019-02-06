package unice.plfgd.server;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Custom dumb logging class
 */
public class Log {
	public static void init() {
		System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
	}

	/**
	 * Logs a message with the given ANSI colour
	 * @param s ANSI colour
	 * @param m message
	 * @see State
	 */
	public static void log(String s, String m) {
		System.out.println(State.fmt(s, m));
	}

	/**
	 * Logs a non-coloured message
	 *
	 * @param m message
	 */
	public static void log(String m) {
		log(State.WHITE, m);
	}

	/**
	 * Logs an empty line
	 */
	public static void log() {
		log("");
	}

	public static class State {
		static final String RESET = "\u001B[0m";
		public static final String RED = "\u001B[31m";
		public static final String GREEN = "\u001B[32m";
		public static final String SYS = "\u001B[33m[SYS] ";
		public static final String BLUE = "\u001B[34m";
		static final String WHITE = "\u001B[37m";

		public static String fmt(String State, String msg) {
			return State + msg + RESET;
		}
	}
}
