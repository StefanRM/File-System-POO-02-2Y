package Commands;

import FileSystem.Directory;
import FileSystem.Helpers;

/**
 * 
 * This class implements "deluser" command
 * 
 * @author Maftei Stefan - Radu
 *
 */
public class DelUser extends Commands {

	/**
	 * This function executes the "deluser" command.
	 */
	@Override
	public void execute(Directory root, String argument) {
		if (!Helpers.currentUser.equals("root")) {
			// only root can delete a user
			HandleErrors.printError10(this.getCommandName(argument));
			return;
		}

		if (!Helpers.users.contains(argument)) {
			// if it doesn't exist
			HandleErrors.printError8(this.getCommandName(argument));
			return;
		}

		Helpers.users.remove(argument); // remove user from users' list
		root.changeOwner(argument); // change owner
	}

	/**
	 * It creates a String that contains the whole command.
	 */
	@Override
	public String getCommandName(String argument) {
		return ("deluser" + " " + argument);
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
