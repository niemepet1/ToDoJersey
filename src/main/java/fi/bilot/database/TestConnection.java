package fi.bilot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class TestConnection
{

	public static void main(final String[] args)
	{
		try
		{
			System.out.print("Trying to connect to database... ");
			Class.forName("org.postgresql.Driver");
			final Connection c = DriverManager.getConnection(DbContract.HOST + DbContract.DB_NAME, DbContract.USERNAME,
					DbContract.PASSWORD);
			System.out.println("OK!");
		}
		catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}

}
