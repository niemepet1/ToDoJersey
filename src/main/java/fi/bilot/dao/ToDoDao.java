package fi.bilot.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import fi.bilot.database.DbContract;
import fi.bilot.database.PostgresHelper;
import fi.bilot.model.ToDo;


public enum ToDoDao
{
	instance;

	private final Map<Integer, ToDo> contentProvider = new HashMap<>();
	private final PostgresHelper client = new PostgresHelper(DbContract.HOST, DbContract.DB_NAME, DbContract.USERNAME,
			DbContract.PASSWORD);

	private ToDoDao()
	{

		try
		{
			System.out.print("Trying to connect to DB... ");
			if (client.connect())
			{
				System.out.println("DB connected");
			}
		}
		catch (final ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}

	}

	public Map<Integer, ToDo> getModel()
	{

		try
		{
			final ResultSet resultSet = selectAllFromTable(client, "todos");

			while (resultSet.next())
			{
				final ToDo todo = new ToDo(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
				contentProvider.put(resultSet.getInt(1), todo);
			}
		}
		catch (final SQLException e)
		{
			e.printStackTrace();
		}

		return contentProvider;
	}

	private static ResultSet selectAllFromTable(final PostgresHelper client, final String table) throws SQLException
	{
		final String query = "SELECT * FROM " + table;

		System.out.print("Executing SQL query '" + query + "'... ");
		final ResultSet resultSet = client.execQuery(query);
		System.out.println(" OK");

		return resultSet;
	}
}
