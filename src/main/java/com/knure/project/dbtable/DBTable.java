package com.knure.project.dbtable;

import java.sql.SQLException;
import java.util.Collection;


public interface DBTable<T> {

	public int insert(T item);


	public T delete(int id) throws SQLException;
	

	public void delete(T item, Filter<T> filter);
	

	public T update(int id, T item) throws SQLException;
	

	public <K> Collection<T> filter(K pattern, Filter<T> filter);

	/**
	 * 
	 * @return All objects from table
	 */
	public Collection<T> selectAll();

	public T get(int id) throws SQLException;
	
	public void clear();
	
	public int size();

	@SuppressWarnings("unchecked")
	public int[] insert(T... item);

}
