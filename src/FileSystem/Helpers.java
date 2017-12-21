package FileSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class's purpose is to keep track of some attributes we use in our whole
 * program
 * 
 * @author Maftei Stefan - Radu
 *
 */
public class Helpers {
	/**
	 * For printing with indentation
	 */
	public static StringBuffer indent = new StringBuffer();

	/**
	 * Users' list
	 */
	public static List<String> users = new ArrayList<String>();

	/**
	 * Current user
	 */
	public static String currentUser;

	/**
	 * Current path
	 */
	public static String currentPath;
}