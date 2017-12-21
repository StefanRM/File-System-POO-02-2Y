package Commands;

import java.util.List;

import FileSystem.CompositeEntity;
import FileSystem.Directory;
import FileSystem.Helpers;

/**
 * 
 * This class implements "chmod" command
 * 
 * @author Maftei Stefan - Radu
 *
 */
public class ChMod extends Commands {

	/**
	 * This function is abstract in Super Class, but it doesn't have any use
	 * here.
	 */
	@Override
	public void execute(Directory root, String argument) {
		// we didn't need it here
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
	 * 
	 * This function transforms a digit (0-7) in a String of permissions
	 * 
	 * @param perm
	 *            Number to be converted
	 * @return String of permissions
	 */
	private String calculatePermissions(int perm) {
		String permissions = "";
		int pos = 2; // we have three permissions (0-3 : rwx)
		String permCode = "rwx";
		while (pos >= 0) { // we use base 2 logic
			if (perm % 2 == 1) { // exists permission
				permissions = permCode.charAt(pos) + permissions;
			} else { // no permission
				permissions = "-" + permissions;
			}
			perm /= 2;
			pos--;
		}

		return permissions;
	}

	/**
	 * This function executes the "chmod" command.
	 */
	@Override
	public void execute(Directory root, String parameter, String argument) {
		String commandName = getCommandName(parameter, argument);

		// parameter is a 2-digit number so we convert each digit
		int perm1 = Character.getNumericValue(parameter.charAt(0));
		int perm2 = Character.getNumericValue(parameter.charAt(1));
		String permissions = calculatePermissions(perm1)
				+ calculatePermissions(perm2); // obtain the permission String

		if (argument.equals("/")) {
			// if there are rights to write root
			if (root.getPermissions().charAt(4) != 'w'
					&& !Helpers.currentUser.equals("root")
					&& (root.getPermissions().charAt(1) != 'w'
							|| !root.getOwner().equals(Helpers.currentUser))) {
				HandleErrors.printError5(commandName);
				return;
			}
			root.changePermission(permissions);
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

		// if everything is okay we analyze the directory's list
		List<CompositeEntity> list = directory.getFiles();

		for (int i = 0; i < list.size(); i++) {
			CompositeEntity entity = list.get(i);
			if (entity.getName().equals(entityToList)) {
				// if it cannot be written
				if (entity.getPermissions().charAt(4) != 'w'
						&& !Helpers.currentUser.equals("root")
						&& (entity.getPermissions().charAt(1) != 'w' || !entity
								.getOwner().equals(Helpers.currentUser))) {
					HandleErrors.printError5(commandName);
					return;
				}

				// otherwise it can be written
				entity.changePermission(permissions);
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
	String getCommandName(String parameter, String argument) {
		return ("chmod" + " " + parameter + " " + argument);
	}

}
