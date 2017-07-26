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

			final String query = "SELECT * FROM todos";

			System.out.print("Executing SQL query '" + query + "'... ");
			final ResultSet rs = client.execQuery(query);
			System.out.println(" OK");

			while (rs.next())
			{
				System.out.printf("%d\t%s\t%s\n", rs.getInt(1), rs.getString(2), rs.getString(3));
			}
		}
		catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}



	}

}
