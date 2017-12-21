package Commands;

import java.util.List;

import FileSystem.CompositeEntity;
import FileSystem.Directory;
import FileSystem.File;
import FileSystem.Helpers;

/**
 * 
 * This class implements "touch" command
 * 
 * @author Maftei Stefan - Radu
 *
 */
public class Touch extends Commands {

	/**
	 * This function executes the "touch" command.
	 */
	@Override
	public void execute(Directory root, String argument) {
		String commandName = getCommandName(argument);
		if (argument.equals("/")) {
			// it's a directory
			HandleErrors.printError1(commandName);
			return;
		}

		// get processed argument and entity from the argument
		String[] result = parseArgumentAndEntityToList(argument);
		argument = result[0];
		String fileToMake = result[1];

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

		// if we can write in the directory
		if (directory.getPermissions().charAt(4) != 'w'
				&& !Helpers.currentUser.equals("root")
				&& (directory.getPermissions().charAt(1) != 'w'
						|| !directory.getOwner().equals(Helpers.currentUser))) {
			HandleErrors.printError5(commandName);
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			CompositeEntity entity = list.get(i);
			if (entity.getName().equals(fileToMake)) {
				if (entity instanceof File) {
					// if it already exists
					HandleErrors.printError7(commandName);
					return;
				}

				// a directory with the same name exists
				HandleErrors.printError1(commandName);
				return;
			}
		}

		// if don't have rights to write in the directory
		if (directory.getPermissions().charAt(4) != 'w'
				&& !Helpers.currentUser.equals("root")
				&& (directory.getPermissions().charAt(1) != 'w'
						|| !directory.getOwner().equals(Helpers.currentUser))) {
			HandleErrors.printError5(commandName);
			return;
		}

		// add the new file
		File file = new File(fileToMake, Helpers.currentUser);
		directory.add(file);
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
	 * It creates a String that contains the whole command.
	 */
	@Override
	String getCommandName(String argument) {
		return ("touch" + " " + argument);
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
