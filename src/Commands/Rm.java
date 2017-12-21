package Commands;

import java.util.List;

import FileSystem.CompositeEntity;
import FileSystem.Directory;
import FileSystem.Helpers;

/**
 * 
 * This class implements "rm" command
 * 
 * @author Maftei Stefan - Radu
 *
 */
public class Rm extends Commands {

	/**
	 * This function executes the "rm" command without parameter.
	 */
	@Override
	public void execute(Directory root, String argument) {
		String commandName = getCommandName(argument);

		if (argument.equals("/")) {
			// cannot remove a directory
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
			// cannot remove a directory
			HandleErrors.printError1(commandName);
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
						&& (entity.getPermissions().charAt(1) != 'w' && !entity
								.getOwner().equals(Helpers.currentUser))) {
					HandleErrors.printError5(commandName);
					return;
				}

				if (entity instanceof Directory) {
					// we cannot write a directory
					HandleErrors.printError1(commandName);
					return;
				}

				// delete the file
				directory.delete(directory, entity.getName());
				return;
			}
		}

		// no file found
		HandleErrors.printError11(commandName);
	}

	/**
	 * This function executes the "cat" command with parameter.
	 */
	@Override
	public void execute(Directory root, String parameter, String argument) {
		String commandName = getCommandName(parameter, argument);

		if (argument.equals("/")) {
			// root is always part of the current path
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

		if (entityToList.equals("/")
				|| Helpers.currentPath.contains(entityToList)) {
			// cannot delete root
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
						&& (entity.getPermissions().charAt(1) != 'w' && !entity
								.getOwner().equals(Helpers.currentUser))) {
					HandleErrors.printError5(commandName);
					return;
				}

				if (entity instanceof Directory) {
					if (Helpers.currentPath.contains(entity.getName())) {
						// if it is part of current path
						HandleErrors.printError13(commandName);
						return;
					}
				}

				// otherwise delete it with its content
				directory.delete(directory, entity.getName());
				return;
			}
		}

		// not found
		HandleErrors.printError12(commandName);
	}

	/**
	 * It creates a String that contains the whole command.
	 */
	@Override
	String getCommandName(String argument) {
		return ("rm" + " " + argument);
	}

	/**
	 * It creates a String that contains the whole command.
	 */
	@Override
	String getCommandName(String parameter, String argument) {
		return ("rm" + " " + parameter + " " + argument);
	}

}
