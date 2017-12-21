package Commands;

/**
 * 
 * This class is specialized in printing errors.
 * 
 * @author Maftei Stefan - Radu
 *
 */
public class HandleErrors {

	/**
	 * 
	 * Error -1
	 * 
	 * @param commandName
	 *            The whole given command
	 */
	public static void printError1(String commandName) {
		System.out.println("-1: " + commandName + ": Is a directory");
	}

	/**
	 * 
	 * Error -2
	 * 
	 * @param commandName
	 *            The whole given command
	 */
	public static void printError2(String commandName) {
		System.out.println("-2: " + commandName + ": No such directory");
	}

	/**
	 * 
	 * Error -3
	 * 
	 * @param commandName
	 *            The whole given command
	 */
	public static void printError3(String commandName) {
		System.out.println("-3: " + commandName + ": Not a directory");
	}

	/**
	 * 
	 * Error -4
	 * 
	 * @param commandName
	 *            The whole given command
	 */
	public static void printError4(String commandName) {
		System.out.println("-4: " + commandName + ": No rights to read");
	}

	/**
	 * 
	 * Error -5
	 * 
	 * @param commandName
	 *            The whole given command
	 */
	public static void printError5(String commandName) {
		System.out.println("-5: " + commandName + ": No rights to write");
	}

	/**
	 * 
	 * Error -6
	 * 
	 * @param commandName
	 *            The whole given command
	 */
	public static void printError6(String commandName) {
		System.out.println("-6: " + commandName + ": No rights to execute");
	}

	/**
	 * 
	 * Error -7
	 * 
	 * @param commandName
	 *            The whole given command
	 */
	public static void printError7(String commandName) {
		System.out.println("-7: " + commandName + ": File already exists");
	}

	/**
	 * 
	 * Error -8
	 * 
	 * @param commandName
	 *            The whole given command
	 */
	public static void printError8(String commandName) {
		System.out.println("-8: " + commandName + ": User does not exist");
	}

	/**
	 * 
	 * Error -9
	 * 
	 * @param commandName
	 *            The whole given command
	 */
	public static void printError9(String commandName) {
		System.out.println("-9: " + commandName + ": User already exists");
	}

	/**
	 * 
	 * Error -10
	 * 
	 * @param commandName
	 *            The whole given command
	 */
	public static void printError10(String commandName) {
		System.out.println(
				"-10: " + commandName + ": No rights to change user status");
	}

	/**
	 * 
	 * Error -11
	 * 
	 * @param commandName
	 *            The whole given command
	 */
	public static void printError11(String commandName) {
		System.out.println("-11: " + commandName + ": No such file");
	}

	/**
	 * 
	 * Error -12
	 * 
	 * @param commandName
	 *            The whole given command
	 */
	public static void printError12(String commandName) {
		System.out
				.println("-12: " + commandName + ": No such file or directory");
	}

	/**
	 * 
	 * Error -13
	 * 
	 * @param commandName
	 *            The whole given command
	 */
	public static void printError13(String commandName) {
		System.out.println("-13: " + commandName
				+ ": Cannot delete parent or current directory");
	}

	/**
	 * 
	 * Error -14
	 * 
	 * @param commandName
	 *            The whole given command
	 */
	public static void printError14(String commandName) {
		System.out.println("-14: " + commandName + ": Non empty directory");
	}
}
