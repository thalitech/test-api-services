package com.test.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DbQueryExec {

	public static List<Map<String, String>> execSelect(final String db, final String query) {

		var result = new ArrayList<Map<String, String>>();

		try (var connection = getConnection(db);
				var stmt = connection.createStatement();
				var rs = stmt.executeQuery(query)) {

			if (!query.toLowerCase().startsWith("delete")) {
				var rsmd = rs.getMetaData();

				while (rs.next()) {
					var row = new HashMap<String, String>();
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						row.put(rsmd.getColumnName(i).toLowerCase(), rs.getString(i).toLowerCase());
					}
					result.add(row);
				}
			}
		} catch (Exception e) {
			System.err.printf("Falha ao executar a query=[%s] na conexao=[%s] \n", query, db);
			e.printStackTrace();
		}
		return result;
	}

	private static Connection getConnection(final String dbProp) throws Exception {
		var props = new Properties();
		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db-connection.properties"));

		final String url = props.getProperty(dbProp + ".datasource.url");
		final String username = props.getProperty(dbProp + ".datasource.username");
		final String senha = props.getProperty(dbProp + ".datasource.password");

		return DriverManager.getConnection(url, username, senha);
	}
}
