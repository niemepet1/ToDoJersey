package fi.bilot.database;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Main
{

	public static void main(final String[] args)
	{
		final PostgresHelper client = new PostgresHelper(DbContract.HOST, DbContract.DB_NAME, DbContract.USERNAME,
				DbContract.PASSWORD);

		try
		{
			System.out.print("Trying to connect to DB... ");
			if (client.connect())
			{
				System.out.println("DB connected");
			}

			selectAllFromTable(client, "todos");

			/*
			 * final Map<String, Object> vals = new HashMap<String, Object>(); vals.put("id", 6); vals.put("summary",
			 * "Task inserted from Java code"); vals.put("description", "This is inserted from the executed Java code"); if
			 * (client.insert("todos", vals) == 1) { System.out.println("Record added"); }
			 */

			//selectAllFromTable(client, "todos");
		}
		catch (final ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}

	}

	private static void selectAllFromTable(final PostgresHelper client, final String table) throws SQLException
	{
		final String query = "SELECT * FROM " + table;

		System.out.print("Executing SQL query '" + query + "'... ");
		final ResultSet rs = client.execQuery(query);
		System.out.println(" OK");

		while (rs.next())
		{
			System.out.printf("%d\t%s\t%s\n", rs.getInt(1), rs.getString(2), rs.getString(3));
		}
	}

}
