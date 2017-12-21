package Commands;

import java.util.List;

import FileSystem.CompositeEntity;
import FileSystem.Directory;
import FileSystem.File;
import FileSystem.Helpers;

/**
 * 
 * This class implements "ls" command
 * 
 * @author Maftei Stefan - Radu
 *
 */
public class Ls extends Commands {

	/**
	 * This function executes the "ls" command.
	 */
	@Override
	public void execute(Directory root, String argument) {
		String commandName = getCommandName(argument);

		if (argument.equals("/")) {
			// if the root cannot be read
			if (root.getPermissions().charAt(3) != 'r'
					&& !Helpers.currentUser.equals("root")
					&& (root.getPermissions().charAt(0) != 'r'
							|| !root.getOwner().equals(Helpers.currentUser))) {
				HandleErrors.printError4(commandName);
				return;
			}

			// print level
			root.printLsLevel();
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
			root.printLsLevel();
			return;
		}

		// if everything is okay we analyze the directory's list
		List<CompositeEntity> list = directory.getFiles();

		for (int i = 0; i < list.size(); i++) {
			CompositeEntity entity = list.get(i);
			if (entity.getName().equals(entityToList)) {
				// if it cannot be executed
				if (entity.getPermissions().charAt(5) != 'x'
						&& !Helpers.currentUser.equals("root")
						&& (entity.getPermissions().charAt(2) != 'x' || !entity
								.getOwner().equals(Helpers.currentUser))) {
					HandleErrors.printError6(commandName);
					return;
				}

				// if it cannot be read
				if (entity.getPermissions().charAt(3) != 'r'
						&& !Helpers.currentUser.equals("root")
						&& (entity.getPermissions().charAt(0) != 'r' || !entity
								.getOwner().equals(Helpers.currentUser))) {
					HandleErrors.printError4(commandName);
					return;
				}

				if (entity instanceof File) {
					// listing a file
					entity.printLs();
					return;
				}

				((Directory) entity).printLsLevel(); // certainly a directory
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
		return ("ls" + " " + argument);
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
