package Commands;

import FileSystem.Directory;
import FileSystem.Helpers;

/**
 * 
 * This class implements "chuser" command
 * 
 * @author Maftei Stefan - Radu
 *
 */
public class ChUser extends Commands {

	/**
	 * This function executes the "chuser" command.
	 */
	@Override
	public void execute(Directory root, String argument) {
		if (argument.equals("root")) { // switching on root
			// update current user and current path
			Helpers.currentUser = argument;
			Helpers.currentPath = "/";
			return;
		}
		if (!Helpers.users.contains(argument)) {
			// if it doesn't exist
			HandleErrors.printError8(this.getCommandName(argument));
			return;
		}

		// otherwise update current user and current path
		Helpers.currentUser = argument;
		Helpers.currentPath = ("/" + argument);
	}

	/**
	 * It creates a String that contains the whole command.
	 */
	@Override
	public String getCommandName(String argument) {
		return ("chuser" + " " + argument);
	}

	/**
	 * This function is abstract in Super Class, but it doesn't have any use
	 * here.
	 */
	@Override
	public void execute(Directory root, String parameter, String argument) {
		// we didn't need it here
	}

	/**
	 * This function is abstract in Super Class, but it doesn't have any use
	 * here.
	 */
	@Override
	String getCommandName(String parameter, String argument) {
		// we didn't need it here
		return null;
	}
}
