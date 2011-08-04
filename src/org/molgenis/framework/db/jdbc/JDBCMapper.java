package org.molgenis.framework.db.jdbc;

import java.util.List;

import org.molgenis.framework.db.DatabaseException;
import org.molgenis.framework.db.Mapper;
import org.molgenis.framework.db.QueryRule;
import org.molgenis.util.CsvReader;
import org.molgenis.util.SpreadsheetWriter;
import org.molgenis.util.Entity;

/**
 * Factory for creating SQL statements
 * 
 * @author Morris Swertz
 * @author Joris Lops
 * 
 */
public interface JDBCMapper<E extends Entity> extends Mapper<E>
{	
	public JDBCDatabase getDatabase();

	public int add(List<E> entities) throws DatabaseException;

	// FIXME: can we merge the two add functions by wrapping list/reader into an
	// iterator of some kind?
	public E create();
	
	public int add(CsvReader reader, SpreadsheetWriter writer) throws DatabaseException;
	
	public int update(List<E> entities) throws DatabaseException;
	
	public int update(CsvReader reader) throws DatabaseException;
	
	public int remove(List<E> entities) throws DatabaseException;
	
	public int count(QueryRule ...rules) throws DatabaseException;

	public List<E> find(QueryRule ...rules) throws DatabaseException;

	public void find(SpreadsheetWriter writer, QueryRule ... rules) throws DatabaseException;
	
	public void find(SpreadsheetWriter writer, List<String> fieldsToExport, QueryRule ... rules) throws DatabaseException;

	public int remove(CsvReader reader) throws DatabaseException;

	public List<E> toList(CsvReader reader, int limit) throws DatabaseException;

	public String getTableFieldName(String field);

	public org.molgenis.framework.db.jdbc.ColumnInfo.Type getFieldType(String field);

//	/**
//	 * Helper method for retrieving keys.
//	 * 
//	 * @param entities
//	 * @param fromIndex
//	 * @param stmt
//	 * @throws DatabaseException
//	 */
//	private void getGeneratedKeys(List<E> entities, Statement stmt, int fromIndex) throws DatabaseException
//	{
//		E entity = null;
//		ResultSet rs_keys = null;
//		int i = 0;
//		try
//		{
//			rs_keys = stmt.getGeneratedKeys();
//			while (rs_keys.next())
//			{
//				entity = entities.get(fromIndex + i);
//				setAutogeneratedKey(rs_keys.getInt(1), entity);
//				entities.set(fromIndex + i, entity); // put it back again...
//				i++;
//
//			}
//		}
//		catch (Exception e)
//		{
//			logger.error("executeKeys(): " + e);
//			e.printStackTrace();
//			throw new DatabaseException(e.getMessage());
//		}
//		finally
//		{
//			try
//			{
//				rs_keys.close();
//			}
//			catch (Exception e)
//			{
//			}
//			rs_keys = null;
//		}
//	}
//
//	/**
//	 * Maps to another mapping strategy for superclasses
//	 */
//	public abstract JDBCMapper getSuperTypeMapper();
//
//	/**
//	 * helper method create a new instance of E
//	 */
//	public abstract E create();
//
//	/**
//	 * Method to build a list for Entity E. This allows the finder to pick a
//	 * more efficient list implementation than the generic lists.
//	 * 
//	 * @param size
//	 *            of the list
//	 * @return list
//	 */
//	public abstract List<E> createList(int size);
//
//	/**
//	 * maps {@link org.molgenis.framework.Database#add(List)}
//	 * 
//	 * @throws DatabaseException
//	 */
//	public abstract int executeAdd(List<E> entities) throws SQLException, DatabaseException;
//
//	/**
//	 * maps {@link org.molgenis.framework.Database#update(List)}
//	 * @throws DatabaseException 
//	 */
//	public abstract int executeUpdate(List<E> entities) throws SQLException, DatabaseException;
//
//	/**
//	 * maps {@link org.molgenis.framework.Database#remove(List)}
//	 */
//	public abstract int executeRemove(List<E> entities) throws SQLException, DatabaseException;
//
//	/**
//	 * maps {@link org.molgenis.framework.Database#find(Class, QueryRule[])}
//	 * 
//	 * @throws DatabaseException
//	 */
//	public abstract String createFindSql(QueryRule... rules) throws DatabaseException;
//
//	/**
//	 * maps {@link org.molgenis.framework.Database#count(Class, QueryRule[])}
//	 * @throws DatabaseException 
//	 * 
//	 * @throws DatabaseException
//	 * 
//	 * @throws SQLException
//	 */
//	public abstract String createCountSql(QueryRule... rules) throws DatabaseException;
//
//	/**
//	 * Translate object field name to table fieldname
//	 */
//	public abstract String getTableFieldName(String fieldName);
//
//	/**
//	 * Retrieve the type of the field
//	 */
//	public abstract Type getFieldType(String fieldName);
//
//	/**
//	 * helper method to set the auto-generated keys
//	 */
//	public abstract void setAutogeneratedKey(int key, E entity);
//
//	/**
//	 * helper method to prepares file for saving.
//	 * 
//	 * @throws IOException
//	 */
//	public abstract void prepareFileAttachements(List<E> entities, File dir) throws IOException;
//
//	/**
//	 * helper method to do some actions after the transaction. For example:
//	 * write files to disk. FIXME make a listener?
//	 * 
//	 * @return true if files were saved (will cause additional update to the
//	 *         database)
//	 * @throws IOException
//	 */
//	public abstract boolean saveFileAttachements(List<E> entities, File dir) throws IOException;
//
//	/**
//	 * helper method for mapping multiplicative references (mref). This function
//	 * is used when retrieving the entity. It should retrieve the mref elements
//	 * and add them to each mref field.
//	 * 
//	 * @param db
//	 * @param entities
//	 * @throws DatabaseException
//	 */
//	public abstract void mapMrefs(List<E> entities) throws DatabaseException;
//
//	/**
//	 * Helper method for storing multiplicative references. This function should
//	 * check wether any mref values have been newly selected or deselected. The
//	 * newly selected elements should be added, the deselected elements should
//	 * be removed (from the entity that holds the mrefs).
//	 * 
//	 * @param db
//	 * @param entities
//	 * @throws DatabaseException
//	 * @throws IOException
//	 */
//	public void storeMrefs(List<E> entities) throws DatabaseException, IOException
//	{
//
//	}
//
//	/**
//	 * Foreign key values may be only given via the 'label'. This function
//	 * allows resolves the underlying references for a list of entities.
//	 * 
//	 * @param db
//	 * @param entities
//	 * @throws DatabaseException
//	 * @throws ParseException
//	 */
//	public void resolveForeignKeys(List<E> entities) throws DatabaseException, ParseException
//	{
//	}
//
//	/**
//	 * Helper method for removing multiplicative references ('mrefs')
//	 * 
//	 * @param db
//	 * @param entities
//	 * @throws SQLException
//	 * @throws IOException
//	 * @throws DatabaseException
//	 */
//	public void removeMrefs(List<E> entities) throws SQLException, IOException, DatabaseException
//	{
//	}
//
//	public int remove(CsvReader reader)
//	{
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	public List<E> toList(CsvReader reader, int limit) throws Exception
//	{
//		final List<E> entities = createList(10);
//		reader.parse(limit, new CsvReaderListener()
//		{
//			public void handleLine(int line_number, Tuple line) throws Exception
//			{
//				E e = create();
//				e.set(line, false); // parse the tuple
//				entities.add(e);
//			}
//		});
//		return entities;
//	}
}
