package Commands;

import java.util.List;

import FileSystem.CompositeEntity;
import FileSystem.Directory;
import FileSystem.File;
import FileSystem.Helpers;

/**
 * 
 * This class implements "mkdir" command
 * 
 * @author Maftei Stefan - Radu
 *
 */
public class MkDir extends Commands {

	/**
	 * This function executes the "mkdir" command.
	 */
	@Override
	public void execute(Directory root, String argument) {
		String commandName = getCommandName(argument);
		if (argument.equals("/")) {
			// root already exists
			HandleErrors.printError1(commandName);
			return;
		}

		// get processed argument and entity from the argument
		String[] result = parseArgumentAndEntityToList(argument);
		argument = result[0];
		String dirToMake = result[1];

		// get the correct path to entity's parent
		argument = processPathArgument(argument);

		// get the directory from argument
		Directory directory = checkPathGetDirectory(root, argument,
				commandName);

		if (directory == null) {
			// in case of errors
			// errors are handled in the above function
			return;
		}

		// if everything is okay we analyze the directory's list
		List<CompositeEntity> list = directory.getFiles();

		// if it cannot be written
		if (directory.getPermissions().charAt(4) != 'w'
				&& !Helpers.currentUser.equals("root")
				&& (directory.getPermissions().charAt(1) != 'w'
						|| !directory.getOwner().equals(Helpers.currentUser))) {
			HandleErrors.printError5(commandName);
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			CompositeEntity entity = list.get(i);
			if (entity.getName().equals(dirToMake)) {
				if (entity instanceof File) {
					// a file with the same name
					HandleErrors.printError3(commandName);
					return;
				}

				// directory already exists
				HandleErrors.printError1(commandName);
				return;
			}
		}

		// if it cannot be written
		if (directory.getPermissions().charAt(4) != 'w'
				&& !Helpers.currentUser.equals("root")
				&& (directory.getPermissions().charAt(1) != 'w'
						|| !directory.getOwner().equals(Helpers.currentUser))) {
			HandleErrors.printError5(commandName);
			return;
		}

		// add directory
		Directory dir = new Directory(dirToMake, Helpers.currentUser);
		directory.add(dir);
	}

	/**
	 * It creates a String that contains the whole command.
	 */
	@Override
	public String getCommandName(String argument) {
		return ("mkdir" + " " + argument);
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
