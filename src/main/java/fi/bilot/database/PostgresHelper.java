package fi.bilot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


public class PostgresHelper
{
	private Connection conn;
	private String host;
	private String dbName;
	private String user;
	private String pass;

	// we don't like this constructor
	protected PostgresHelper()
	{
	}

	public PostgresHelper(final String host, final String dbName, final String user, final String pass)
	{
		this.host = host;
		this.dbName = dbName;
		this.user = user;
		this.pass = pass;
	}

	public boolean connect() throws SQLException, ClassNotFoundException
	{
		if (host.isEmpty() || dbName.isEmpty() || user.isEmpty() || pass.isEmpty())
		{
			throw new SQLException("Database credentials missing");
		}

		Class.forName("org.postgresql.Driver"); // Note: why are we doing this?
		this.conn = DriverManager.getConnection(this.host + this.dbName, this.user, this.pass);
		return true;
	}

	public ResultSet execQuery(final String query) throws SQLException
	{
		return this.conn.createStatement().executeQuery(query);
	}

	public int insert(final String table, final Map<String, Object> values) throws SQLException
	{
		final StringBuilder columnsBuilder = new StringBuilder();
		final StringBuilder valuesBuilder = new StringBuilder();

		for (final String col : values.keySet())
		{
			columnsBuilder.append(col).append(",");

			if (values.get(col) instanceof String)
			{
				valuesBuilder.append("'").append(values.get(col)).append("', ");
			}
			else
			{
				valuesBuilder.append(values.get(col)).append(",");
			}
		}

		// These are done to remove the last comma:
		columnsBuilder.setLength(columnsBuilder.length() - 1);
		valuesBuilder.setLength(valuesBuilder.length() - 1);

		final String query = String.format("INSERT INTO %s (%s) VALUES (%s)", table, columnsBuilder.toString(),
				valuesBuilder.toString());
		System.out.print("Executing SQL query: '" + query + "'... ");
		final int queryReturnValue = this.conn.createStatement().executeUpdate(query);
		System.out.println("OK");

		return queryReturnValue;
	}
}
