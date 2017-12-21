package FileSystem;

/**
 * 
 * This class implements a File in a file system.
 * 
 * @author Maftei Stefan - Radu
 *
 */
public class File implements CompositeEntity {
	/**
	 * Name of the file
	 */
	private String name;

	/**
	 * Owner of the file
	 */
	private String owner;

	/**
	 * Permissions of the file
	 */
	private String permissions;

	/**
	 * Content of the file
	 */
	private String content;

	/**
	 * 
	 * Constructor with 2 parameters.
	 * 
	 * @param name
	 *            File's name
	 * @param owner
	 *            File's owner
	 */
	public File(String name, String owner) {
		this(name, owner, "rwx---");
	}

	/**
	 * 
	 * Constructor with 3 parameters.
	 * 
	 * @param name
	 *            File's name
	 * @param owner
	 *            File's owner
	 * @param permissions
	 *            File's permissions
	 */
	public File(String name, String owner, String permissions) {
		this.name = name;
		this.owner = owner;
		this.permissions = permissions;
		this.content = "";
	}

	/**
	 * 
	 * Getter for the file's content.
	 * 
	 * @return File's content
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 
	 * Setter for the file's content
	 * 
	 * @param content
	 *            File's content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Getter for the file's name
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Getter for the file's owner
	 */
	@Override
	public String getOwner() {
		return this.owner;
	}

	/**
	 * Getter for the file's permissions
	 */
	@Override
	public String getPermissions() {
		return this.permissions;
	}

	/**
	 * This function prints the file with indentation
	 */
	@Override
	public void print() {
		System.out.println(Helpers.indent + this.name + " f" + this.permissions
				+ " " + this.owner);
	}

	/**
	 * This function prints the file without indentation
	 */
	@Override
	public void printLs() {
		System.out.println(
				this.name + " f" + this.permissions + " " + this.owner);
	}

	/**
	 * This function changes owner of a user's file, if it was deleted
	 */
	@Override
	public void changeOwner(String oldOwner) {
		if (Helpers.users.size() > 1) {
			// first user added becomes the new owner
			if (this.getOwner().equals(oldOwner)) {
				this.owner = Helpers.users.get(1);
			}
			return;
		}

		// root becomes the new owner
		if (this.getOwner().equals(oldOwner)) {
			this.owner = Helpers.users.get(0);
		}
	}

	/**
	 * This function change permissions of a file
	 */
	@Override
	public void changePermission(String permissions) {
		this.permissions = permissions;
	}
}