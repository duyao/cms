package com.dy.test.DbUnit;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.junit.Assert;
import org.xml.sax.InputSource;

public class AbstractDBUnitTestCase {
	public static IDatabaseConnection connection;
	private File tmpFile;

	public static void init() throws DatabaseUnitException, SQLException {
		connection = new DatabaseConnection(DbUtil.getConnection());
	}

	protected IDataSet createDataSet(String tname) throws Exception {
		InputStream inputStream = AbstractDBUnitTestCase.class.getClassLoader()
				.getResourceAsStream(tname+".xml");
		Assert.assertNotNull("dbUnit基本数据不存在", inputStream);
		return new FlatXmlDataSet(new FlatXmlProducer(new InputSource(
				inputStream)));
	}

	private void writeBackupFile(IDataSet iDataSet) throws Exception {
		tmpFile = File.createTempFile("back", "xml");
		FlatXmlDataSet.write(iDataSet, new FileWriter(tmpFile));
	}

	protected void backupCustomTables(String[] tables) throws Exception {
		QueryDataSet queryDataSet = new QueryDataSet(connection);
		for (String string : tables) {
			queryDataSet.addTable(string);
		}
		writeBackupFile(queryDataSet);
	}

	protected void backupOneTables(String table) throws Exception {
		QueryDataSet queryDataSet = new QueryDataSet(connection);
		queryDataSet.addTable(table);
		writeBackupFile(queryDataSet);
	}

}
