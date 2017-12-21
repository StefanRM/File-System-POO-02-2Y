package FileSystem;

import java.util.ArrayList;
import java.util.List;

public class Directory implements CompositeEntity {
	/**
	 * Directory's name
	 */
	private String name;

	/**
	 * Directory's owner
	 */
	private String owner;

	/**
	 * Directory's permissions
	 */
	private String permissions;

	/**
	 * Directory's parent (only directories have parents)
	 */
	private Directory parent;

	/**
	 * Directory's list of components (files and directories, all are entities)
	 */
	private List<CompositeEntity> files = new ArrayList<CompositeEntity>();

	/**
	 * 
	 * Constructor with 2 parameters
	 * 
	 * @param name
	 *            Directory's name
	 * @param owner
	 *            Directory's owner
	 */
	public Directory(String name, String owner) {
		this(name, owner, "rwx---");
	}

	/**
	 * 
	 * Constructor with 3 parameters
	 * 
	 * @param name
	 *            Directory's name
	 * @param owner
	 *            Directory's owner
	 * @param permissions
	 *            Directory's permissions
	 */
	public Directory(String name, String owner, String permissions) {
		this.name = name;
		this.owner = owner;
		this.permissions = permissions;
		this.parent = null;
	}

	/**
	 * Getter for the directory's name
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Getter for the directory's owner
	 */
	@Override
	public String getOwner() {
		return this.owner;
	}

	/**
	 * Getter for directory's permissions
	 */
	@Override
	public String getPermissions() {
		return this.permissions;
	}

	/**
	 * 
	 * Setter for directory's parent
	 * 
	 * @param directory
	 *            Parent directory
	 */
	public void setParent(Directory directory) {
		this.parent = directory;
	}

	/**
	 * 
	 * Getter for the directory's parent
	 * 
	 * @return Directory's parent
	 */
	public Directory getParent() {
		return this.parent;
	}

	/**
	 * 
	 * Getter for the directory's list of components (entities)
	 * 
	 * @return
	 */
	public List<CompositeEntity> getFiles() {
		return this.files;
	}

	/**
	 * 
	 * This functions checks if an entity exists in a directory's list
	 * 
	 * @param list
	 *            A directory list of components
	 * @param entity
	 *            Entity to verify if exists in the list
	 * @return True it entity exists, otherwise False
	 */
	private boolean existsInDirectory(List<CompositeEntity> list,
			CompositeEntity entity) {
		for (CompositeEntity item : list) {
			if (item.getName().equals(entity.getName())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * This function adds an entity to the directory's list
	 * 
	 * @param entity
	 *            Entity to add in directory's list
	 * @return True if it was successfully added, otherwise False
	 */
	public boolean add(CompositeEntity entity) {
		if (existsInDirectory(files, entity)) {
			// already exists on this level
			return false;
		}

		files.add(entity);
		if (entity instanceof Directory) {
			// set parent for the added directory
			((Directory) entity).setParent(this);
		}
		return true;
	}

	/**
	 * This function prints the directory with indentation
	 */
	@Override
	public void print() {
		if (this.parent != null) { // case of root
			System.out.println(Helpers.indent + this.name + " d"
					+ this.permissions + " " + this.owner);
		} else {
			System.out.println(Helpers.indent + this.name + " d"
					+ this.permissions + " " + this.owner);
		}

		Helpers.indent.append("\t"); // add tab for components
		for (int i = 0; i < files.size(); i++) {
			// print components
			CompositeEntity entity = files.get(i);
			entity.print();
		}

		// remove tab after listing components
		Helpers.indent.replace(Helpers.indent.length() - 1,
				Helpers.indent.length(), "");
	}

	/**
	 * This function prints the directory without indentation
	 */
	@Override
	public void printLs() {
		System.out.println(Helpers.indent + this.name + " d" + this.permissions
				+ " " + this.owner);
	}

	/**
	 * This function prints the directory's list of components without
	 * indentation
	 */
	public void printLsLevel() {
		for (int i = 0; i < files.size(); i++) {
			CompositeEntity entity = files.get(i);
			entity.printLs();
		}
	}

	/**
	 * This function changes owner of a user's directories, if it was deleted
	 */
	@Override
	public void changeOwner(String oldOwner) {
		if (Helpers.users.size() > 1) {
			// first user added becomes the new owner
			if (this.getOwner().equals(oldOwner)) {
				this.owner = Helpers.users.get(1);
			}
		} else {
			// root becomes the new owner
			if (this.getOwner().equals(oldOwner)) {
				this.owner = Helpers.users.get(0);
			}
		}

		// change owner in all components if the user is the owner
		for (int i = 0; i < files.size(); i++) {
			CompositeEntity entity = files.get(i);
			entity.changeOwner(oldOwner);
		}
	}

	/**
	 * This function change permissions of a directory
	 */
	@Override
	public void changePermission(String permissions) {
		this.permissions = permissions;
	}

	/**
	 * 
	 * This function removes an entity from a directory's list of components.
	 * 
	 * @param directory
	 *            Directory from which we delete
	 * @param entityName
	 *            Name of the entity to be deleted
	 */
	public void delete(Directory directory, String entityName) {
		for (int i = 0; i < files.size(); i++) {
			CompositeEntity entity = files.get(i);
			if (entity.getName().equals(entityName)) {
				files.remove(i);
				return;
			}
		}
	}

	/**
	 * 
	 * This function checks if a directory is empty.
	 * 
	 * @return True if directory is empty, otherwise False
	 */
	public boolean isEmpty() {
		return files.size() == 0;
	}
}