package Commands;

import FileSystem.Directory;
import FileSystem.Helpers;

/**
 * 
 * This class implements "adduser" command
 * 
 * @author Maftei Stefan - Radu
 *
 */
public class AddUser extends Commands {

	/**
	 * This function executes the "adduser" command.
	 */
	@Override
	public void execute(Directory root, String argument) {
		if (!Helpers.currentUser.equals("root")) {
			// only root can add a user
			HandleErrors.printError10(this.getCommandName(argument));
			return;
		}

		if (Helpers.users.contains(argument)) {
			// if it already exists
			HandleErrors.printError9(this.getCommandName(argument));
			return;
		}

		// create directory and add user
		Directory userFolder = new Directory(argument, argument);
		root.add(userFolder);
		Helpers.users.add(argument);
	}

	/**
	 * It creates a String that contains the whole command.
	 */
	@Override
	public String getCommandName(String argument) {
		return ("adduser" + " " + argument);
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
