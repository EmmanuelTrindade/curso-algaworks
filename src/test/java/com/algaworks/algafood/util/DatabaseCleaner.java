package com.algaworks.algafood.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCleaner {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DataSource dataSource;

	private Connection connection;

	public void clearTables() {
		try (Connection connection = dataSource.getConnection()) {
			this.connection = connection;
			
			checkTestDatabase();
			tryToClearTables();
			tryToClearSequences();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			this.connection = null;
		}
	}
	
	private void checkTestDatabase() throws SQLException {
		String catalog = connection.getCatalog();

		if (catalog == null || !catalog.endsWith("test")) {
			throw new RuntimeException(
					"Cannot clear database tables because '" + catalog + "' is not a test database (suffix 'test' not found).");
		}
	}

	private void tryToClearSequences() throws SQLException {
		List<String> seqNames = getSeqNames();
		reset(seqNames);
	}
	
	private void tryToClearTables() throws SQLException {
		List<String> tableNames = getTableNames();
		clear(tableNames);
	}

	private List<String> getSeqNames() throws SQLException {
		List<String> seqNames = new ArrayList<>();

		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet rs = metaData.getTables(connection.getCatalog(), null, null, new String[] { "SEQUENCE" });

		while (rs.next()) {
			if(rs.getString("TABLE_NAME").toLowerCase().startsWith("s_")) {
				seqNames.add(rs.getString("TABLE_NAME"));
			}
		}

		return seqNames;
	}
	
	private List<String> getTableNames() throws SQLException {
		List<String> tableNames = new ArrayList<>();

		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet rs = metaData.getTables(connection.getCatalog(), null, null, new String[] { "TABLE" });

		while (rs.next()) {
			tableNames.add(rs.getString("TABLE_NAME"));
		}

		tableNames.remove("flyway_schema_history");

		return tableNames;
	}
	
	private void reset(List<String> seqNames) throws SQLException {
		Statement statement = buildSqlStatementSeq(seqNames);

		logger.debug("Executing SQL");
		statement.executeBatch();
	}
	
	private void clear(List<String> tableNames) throws SQLException {
		Statement statement = buildSqlStatement(tableNames);

		logger.debug("Executing SQL");
		statement.executeBatch();
	}

	private Statement buildSqlStatementSeq(List<String> seqNames) throws SQLException {
		Statement statement = connection.createStatement();

		addSeqSatements(seqNames, statement);
		
		return statement;
	}
	
	private Statement buildSqlStatement(List<String> tableNames) throws SQLException {
		Statement statement = connection.createStatement();

		statement.addBatch(sql("SET session_replication_role = replica"));
		addTruncateSatements(tableNames, statement);
		statement.addBatch(sql("SET session_replication_role = origin"));

		return statement;
	}

	private void addSeqSatements(List<String> seqNames, Statement statement) {
		seqNames.forEach(seqName -> {
			try {
				statement.addBatch(sql("ALTER SEQUENCE " + seqName + " RESTART WITH 1"));
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	private void addTruncateSatements(List<String> tableNames, Statement statement) {
		tableNames.forEach(tableName -> {
			try {
				statement.addBatch(sql("TRUNCATE TABLE " + tableName + " CASCADE"));
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
	}

	private String sql(String sql) {
		logger.debug("Adding SQL: {}", sql);
		return sql;
	}
	
}
