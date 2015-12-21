package com.dy.test.DbUnit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.xml.sax.InputSource;

public class AbstractDBUnitTestCase {
	public static IDatabaseConnection connection;
	private File tempFile;

	@BeforeClass
	public static void init() throws DatabaseUnitException, SQLException {
		connection = new DatabaseConnection(DbUtil.getConnection());
	}

	protected IDataSet createDataSet(String tname) throws Exception {
		InputStream inputStream = AbstractDBUnitTestCase.class.getClassLoader()
				.getResourceAsStream(tname+".xml");
		Assert.assertNotNull("dbUnit基本数据不存在", inputStream);
		IDataSet reSet = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(
				inputStream)));
		return reSet;
		
	}
	protected void backupAllTable() throws Exception{
		IDataSet ds = connection.createDataSet();
		writeBackupFile(ds);
	}
	

	private void writeBackupFile(IDataSet ds) throws IOException, DataSetException {
		tempFile = File.createTempFile("back","xml");
		FlatXmlDataSet.write(ds, new FileWriter(tempFile));
	}
	
	protected void backupCustomTable(String[] tname) throws DataSetException, IOException {
		QueryDataSet ds = new QueryDataSet(connection);
		for(String str:tname) {
			ds.addTable(str);
		}
		writeBackupFile(ds);
	}
	
	protected void bakcupOneTable(String tname) throws DataSetException, IOException {
		backupCustomTable(new String[]{tname});
	}
	
	protected void resumeTable() throws FileNotFoundException, DatabaseUnitException, SQLException {
		IDataSet ds = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(tempFile))));
		DatabaseOperation.CLEAN_INSERT.execute(connection, ds);
	}
	
	
	@AfterClass
	public static void destory() {
		try {
			if(connection!=null) connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

}
