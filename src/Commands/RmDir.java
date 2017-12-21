package Commands;

import java.util.List;

import FileSystem.CompositeEntity;
import FileSystem.Directory;
import FileSystem.File;
import FileSystem.Helpers;

/**
 * 
 * This class implements "rmdir" command
 * 
 * @author Maftei Stefan - Radu
 *
 */
public class RmDir extends Commands {

	/**
	 * This function executes the "rmdir" command.
	 */
	@Override
	public void execute(Directory root, String argument) {
		String commandName = getCommandName(argument);

		if (argument.equals("/")) {
			// cannot delete root (always part of current path)
			HandleErrors.printError13(commandName);
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
			// root cannot be deleted (always part of current path)
			HandleErrors.printError13(commandName);
			return;
		}

		// if everything is okay we analyze the directory's list
		List<CompositeEntity> list = directory.getFiles();

		// if we cannot write in the directory
		if (directory.getPermissions().charAt(4) != 'w'
				&& !Helpers.currentUser.equals("root")
				&& (directory.getPermissions().charAt(1) != 'w'
						|| !directory.getOwner().equals(Helpers.currentUser))) {
			HandleErrors.printError5(commandName);
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			CompositeEntity entity = list.get(i);
			if (entity.getName().equals(entityToList)) {
				// if we cannot write
				if (entity.getPermissions().charAt(4) != 'w'
						&& !Helpers.currentUser.equals("root")
						&& (entity.getPermissions().charAt(1) != 'w' || !entity
								.getOwner().equals(Helpers.currentUser))) {
					HandleErrors.printError5(commandName);
					return;
				}

				if (entity instanceof File) {
					// it is a file
					HandleErrors.printError3(commandName);
					return;
				}

				// certainly a directory
				if (!((Directory) entity).isEmpty()) {
					// if it's not empty error
					HandleErrors.printError14(commandName);
					return;
				} else {
					// if empty, delete it
					directory.delete(directory, entity.getName());
					return;
				}
			}
		}

		// not found
		HandleErrors.printError2(commandName);
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
		return ("rmdir" + " " + argument);
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
