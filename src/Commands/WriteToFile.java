package Commands;

import java.util.List;

import FileSystem.CompositeEntity;
import FileSystem.Directory;
import FileSystem.File;
import FileSystem.Helpers;

/**
 * 
 * This class implements "writetofile" command
 * 
 * @author Maftei Stefan - Radu
 *
 */
public class WriteToFile extends Commands {

	/**
	 * This function is abstract in Super Class, but it doesn't have any use
	 * here.
	 */
	@Override
	public void execute(Directory root, String argument) {
		// we didn't need it here
	}

	/**
	 * This function executes the "writetofile" command. The order of parameter
	 * and argument is switched.
	 */
	@Override
	public void execute(Directory root, String argument, String content) {
		String commandName = getCommandName(argument, content);

		if (argument.equals("/")) {
			// it is certainly not a file
			HandleErrors.printError1(commandName);
			return;
		}

		// get processed argument and entity from the argument
		String[] result = parseArgumentAndEntityToList(argument);
		argument = result[0];
		String entityToList = result[1];

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

		// cases of entity's, when it is a relative way
		if (entityToList.equals(".")) {
			entityToList = directory.getName();
			directory = directory.getParent();
			if (directory == null) {
				directory = root;
			}
		}

		if (entityToList.equals("..")) {
			directory = directory.getParent();
			if (directory == null) {
				directory = root;
			}
			entityToList = directory.getName();
			directory = directory.getParent();
			if (directory == null) {
				directory = root;
			}
		}

		if (entityToList.equals("/")) {
			// it is certainly not a file
			HandleErrors.printError1(commandName);
			return;
		}

		// if everything is okay we analyze the directory's list
		List<CompositeEntity> list = directory.getFiles();

		for (int i = 0; i < list.size(); i++) {
			CompositeEntity entity = list.get(i);
			if (entity.getName().equals(entityToList)) {
				if (entity instanceof Directory) {
					// it is certainly not a file
					HandleErrors.printError1(commandName);
					return;
				}

				// in case we cannot write
				if (entity.getPermissions().charAt(4) != 'w'
						&& !Helpers.currentUser.equals("root")
						&& (entity.getPermissions().charAt(1) != 'w' || !entity
								.getOwner().equals(Helpers.currentUser))) {
					HandleErrors.printError5(commandName);
					return;
				}

				if ((entity.getPermissions().charAt(1) != 'w'
						|| !entity.getOwner().equals(Helpers.currentUser))) {
					HandleErrors.printError5(commandName);
					return;
				}

				((File) entity).setContent(content); // certainly a file
				return;
			}
		}

		// file not found in list
		HandleErrors.printError11(commandName);
	}

	/**
	 * This function is abstract in Super Class, but it doesn't have any use
	 * here.
	 */
	@Override
	String getCommandName(String argument) {
		// we didn't need it here
		return null;
	}

	/**
	 * It creates a String that contains the whole command.
	 */
	@Override
	String getCommandName(String argument, String content) {
		return ("writetofile" + " " + argument + " " + content);
	}

}
