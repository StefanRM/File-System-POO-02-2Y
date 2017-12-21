package Commands;

/**
 * 
 * This class has the "Factory Pattern" implementation. It receives a command
 * type and return the certain command.
 * 
 * @author Maftei Stefan - Radu
 *
 */
public class CommandsFactory {

	/**
	 * 
	 * This function verifies the command type received and if it a valid one,
	 * the certain command is returned.
	 * 
	 * @param commandType
	 *            The command that is needed to be executed
	 * @return The certain command or null, otherwise
	 */
	public Commands getCommand(String commandType) {
		if (commandType == null) {
			return null;
		}

		if (commandType.equals("adduser")) {
			return new AddUser();
		}

		if (commandType.equals("deluser")) {
			return new DelUser();
		}

		if (commandType.equals("chuser")) {
			return new ChUser();
		}

		if (commandType.equals("cd")) {
			return new Cd();
		}

		if (commandType.equals("mkdir")) {
			return new MkDir();
		}

		if (commandType.equals("ls")) {
			return new Ls();
		}

		if (commandType.equals("chmod")) {
			return new ChMod();
		}

		if (commandType.equals("touch")) {
			return new Touch();
		}

		if (commandType.equals("rm")) {
			return new Rm();
		}

		if (commandType.equals("rmdir")) {
			return new RmDir();
		}

		if (commandType.equals("writetofile")) {
			return new WriteToFile();
		}

		if (commandType.equals("cat")) {
			return new Cat();
		}

		return null;
	}
}
