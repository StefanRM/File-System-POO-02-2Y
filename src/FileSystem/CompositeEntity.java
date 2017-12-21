package FileSystem;

/**
 * 
 * This interface implements "Composite Pattern", used to treat Directory and
 * File in similar way as a single object.
 * 
 * @author Maftei Stefan - Radu
 *
 */
public interface CompositeEntity {

	/**
	 * 
	 * Getter for the entity's name
	 * 
	 * @return Entity's name
	 */
	public String getName();

	/**
	 * 
	 * Getter for the entity's owner
	 * 
	 * @return Entity's owner
	 */
	public String getOwner();

	/**
	 * 
	 * Getter for entity's permissions
	 * 
	 * @return Entity's permissions
	 */
	public String getPermissions();

	/**
	 * 
	 * This function change permissions of an entity
	 * 
	 * @param permissions
	 *            New permissions
	 */
	public void changePermission(String permissions);

	/**
	 * This function prints the entity with indentation
	 */
	public void print();

	/**
	 * This function prints the entity without indentation
	 */
	public void printLs();

	/**
	 * 
	 * This function changes owner of a user's entities, if it was deleted
	 * 
	 * @param oldOwner
	 *            Deleted owner
	 */
	public void changeOwner(String oldOwner);
}