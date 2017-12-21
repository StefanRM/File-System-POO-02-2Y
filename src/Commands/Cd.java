package Commands;

import FileSystem.Directory;
import FileSystem.Helpers;

/**
 * 
 * This class implements "cd" command
 * 
 * @author Maftei Stefan - Radu
 *
 */
public class Cd extends Commands {

	/**
	 * This function executes the "cd" command.
	 */
	@Override
	public void execute(Directory root, String argument) {
		String commandName = getCommandName(argument);

		// get the correct path
		argument = processPathArgument(argument);

		// get the directory from argument
		Directory directory = checkPathGetDirectory(root, argument,
				commandName);

		// if something happened
		if (directory == null) {
			// errors are handled in the checkPathGetDirectory()
			return;
		}

		// if no errors occur we set the new current path
		Helpers.currentPath = createNewPath(directory);
	}

	/**
	 * It creates a String that contains the whole command.
	 */
	@Override
	public String getCommandName(String argument) {
		return ("cd" + " " + argument);
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
