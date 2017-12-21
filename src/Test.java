import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Commands.Commands;
import Commands.CommandsFactory;
import FileSystem.Directory;
import FileSystem.Helpers;

/**
 * 
 * In this class we execute our commands. It is created a root for the file
 * system and for users. Then the execution begins.
 * 
 * @author Maftei Stefan - Radu
 * 
 */
public class Test {

	public static void main(String[] args) throws IOException {
		// each file begins with command "mkdir /" , so we execute it here
		Directory root = new Directory("/", "root", "rwxr-x");
		Helpers.users.add("root");
		Helpers.currentUser = "root";
		Helpers.currentPath = "/";
		CommandsFactory factory = new CommandsFactory();

		// reading from file
		FileReader input = new FileReader(args[0]);
		BufferedReader br = new BufferedReader(input);
		String line;
		br.readLine(); // we already executed the first command in file

		while ((line = br.readLine()) != null) {
			// obtaining command with its parameter and argument
			String[] parts = line.split(" ", 3);
			if (parts.length == 2) { // without parameter
				Commands command = factory.getCommand(parts[0]);
				command.execute(root, parts[1]);
			}
			if (parts.length == 3) { // with parameter
				Commands command = factory.getCommand(parts[0]);
				command.execute(root, parts[1], parts[2]);
			}
		}

		root.print(); // print the hierarchy

		br.close();
	}

}
