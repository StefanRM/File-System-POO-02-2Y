package Commands;

import java.util.List;

import FileSystem.CompositeEntity;
import FileSystem.Directory;
import FileSystem.File;
import FileSystem.Helpers;

/**
 * 
 * This abstract class represents the logic of all commands. It is abstract
 * because we don't want a general command class, but its logic.
 * 
 * @author Maftei Stefan - Radu
 *
 */
public abstract class Commands {
	/**
	 * 
	 * Executes a command
	 * 
	 * @param root
	 *            Root of file system
	 * @param argument
	 *            Argument of the command
	 */
	public abstract void execute(Directory root, String argument);

	/**
	 * 
	 * Executes a command
	 * 
	 * @param root
	 *            Root of file system
	 * @param parameter
	 *            Parameter of the command
	 * @param argument
	 *            Argument of the command
	 */
	public abstract void execute(Directory root, String parameter,
			String argument);

	/**
	 * 
	 * It creates a String that contains the whole command.
	 * 
	 * @param argument
	 *            Argument of the command
	 * @return The whole command name
	 */
	abstract String getCommandName(String argument);

	/**
	 * 
	 * It creates a String that contains the whole command.
	 * 
	 * @param parameter
	 *            Parameter of the command
	 * @param argument
	 *            Argument of the command
	 * @return The whole command name
	 */
	abstract String getCommandName(String parameter, String argument);

	/**
	 * 
	 * This function checks a path and finds the directory indicated by path.
	 * 
	 * @param root
	 *            Root of the file system
	 * @param path
	 *            Path to a certain directory (absolute path)
	 * @param commandName
	 *            whole command name
	 * @return The directory at the end of the path, or null otherwise
	 */
	protected Directory checkPathGetDirectory(Directory root, String path,
			String commandName) {
		if (path.equals("/")) {
			return root;
		}

		Directory directory = root;
		// split path to analyze all directories in path
		String[] parts = path.split("/");
		for (int i = 1; i < parts.length; i++) {
			// cases when that is not a name of directory, but a indicated way
			if (parts[i].equals("")) {
				continue;
			}
			if (parts[i].equals(".")) {
				continue;
			}
			if (parts[i].equals("..")) {
				directory = directory.getParent();
				if (directory == null) {
					directory = root;
				}
				continue;
			}
			// get the list of files from current directory
			List<CompositeEntity> list = directory.getFiles();

			int j;
			// search in list of files
			for (j = 0; j < list.size(); j++) {
				// get an entity
				CompositeEntity entity = list.get(j);
				if (entity.getName().equals(parts[i])) {
					if (entity instanceof File) {
						// a path should not include a file (only at the end of
						// it)
						HandleErrors.printError3(commandName);
						return null;
					}

					// check permissions
					if (entity.getPermissions().charAt(5) != 'x'
							&& !Helpers.currentUser.equals("root")
							&& (entity.getPermissions().charAt(2) != 'x'
									|| !entity.getOwner()
											.equals(Helpers.currentUser))) {
						HandleErrors.printError6(commandName);
						return null;
					}

					// it is certainly a directory
					directory = ((Directory) entity);
					break;
				}
			}

			if (j == list.size()) {
				// it wasn't found
				HandleErrors.printError2(commandName);
				return null;
			}
		}

		// directory can be null after this operations only if it was root
		// for safety
		if (directory == null) {
			directory = root;
		}

		return directory;
	}

	/**
	 * 
	 * This function creates the path of a directory in the file system.
	 * 
	 * @param directory
	 *            Directory whose path we want to find
	 * @return Directory's absolute path
	 */
	protected String createNewPath(Directory directory) {
		String newPath = "";

		// case of root
		if (directory.getName().equals("/")) {
			newPath = "/";
			return newPath;
		}

		// we iterate from parent to parent of directory
		for (Directory i = directory; i != null; i = i.getParent()) {
			if (!i.getName().equals("/")) { // "/" is already put
				// assembling path
				newPath = ("/" + i.getName() + newPath);
			}
		}

		return newPath;
	}

	/**
	 * 
	 * This function transforms a relative path to absolute path
	 * 
	 * @param argument
	 *            Argument of the command
	 * @return The absolute path made from argument
	 */
	protected String processPathArgument(String argument) {
		if (!argument.startsWith("/")) { // if it is not an absolute path
			// we do not want "//" in the path
			if (!Helpers.currentPath.equals("/")) {
				argument = Helpers.currentPath + "/" + argument;
			} else {
				argument = Helpers.currentPath + argument;
			}
		}

		return argument;
	}

	/**
	 * 
	 * This function splits the argument (path) and the last entity of it.
	 * 
	 * @param argument
	 *            Argument of the command
	 * @return A String array with a processed argument and entity
	 */
	protected String[] parseArgumentAndEntityToList(String argument) {
		String[] result = new String[2];
		String entityToList;
		boolean bool = false;

		// keep in mind if the argument starts with "/"
		if (argument.startsWith("/")) {
			bool = true;
		}

		// we don't need our argument to end with "/"
		if (argument.endsWith("/")) {
			argument = argument.substring(0, argument.length() - 1);
		}
		int lastOcc = argument.lastIndexOf("/"); // position of last "/"
		if (lastOcc == -1) {
			// it isn't any "/"
			entityToList = argument;
			argument = "";
		} else {
			// save entity
			entityToList = argument.substring(lastOcc + 1);

			// eliminate entity from the argument
			for (int i = 0; i <= entityToList.length(); i++) {
				argument = argument.substring(0, lastOcc)
						+ argument.substring(lastOcc + 1);
			}
		}

		// if it started with "/" it should contain it to the end
		if (bool && argument.equals("")) {
			argument = "/";
		}

		// assign solution
		result[0] = argument;
		result[1] = entityToList;

		return result;
	}
}
