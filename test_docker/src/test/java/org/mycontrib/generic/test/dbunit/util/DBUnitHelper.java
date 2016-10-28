package org.mycontrib.generic.test.dbunit.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.dbunit.Assertion;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

public class DBUnitHelper extends BasicDBUnitHelper {
	
	
	private IDataSet additionalDataSet=null;
	private IDataSet expectedDataSet=null;
	private IDataSet databaseDataSet=null;
	 
	
	public IDataSet createCompositeDataSet(IDataSet ds1,IDataSet additionalDataSet) throws DataSetException{
		    CompositeDataSet cds = new CompositeDataSet(ds1,additionalDataSet);		 
			return cds; 		    
	}
	
		
	public void extractActualDataSetInDatabase() throws Exception{
		this.databaseDataSet = databaseTester.getConnection().createDataSet();	
	}
	
	public void extractExpectedDataSet(String expectedDataSetFileName) throws Exception{
		// Load expected data from an XML dataset (direct , without addition)
		String expectedDataSetPathName = dataSetDirectory+"/"+expectedDataSetFileName;
		FlatXmlDataSetBuilder fxdsb = new FlatXmlDataSetBuilder();
		this.expectedDataSet = fxdsb.build(new File(expectedDataSetPathName));	
		this.additionalDataSet=null;
	}
	
	public void  extractExpectedDataSetFromAdditionalOne(String additionalDataSetFileName) throws Exception{
		// Load expected data from an XML dataset
		String additionalDataSetPathName = dataSetDirectory+"/"+additionalDataSetFileName;
		this.additionalDataSet = new FlatXmlDataSet(new File(additionalDataSetPathName));
		this.expectedDataSet = createCompositeDataSet(this.initialDataSet,this.additionalDataSet);	
	}
	
	public ITable extractActualTable(String tableName) throws Exception{
		// Fetch database data 
		extractActualDataSetInDatabase();
		ITable actualTable = databaseDataSet.getTable(tableName);
		//System.out.println(actualTable);
		return actualTable;
	}
	
	public ITable extractExpectedTable(String tableName) throws Exception{
		ITable expectedTable = this.expectedDataSet.getTable(tableName);
		//System.out.println(expectedTable);
		return expectedTable;
	}
	
	public ITable extractExpectedTableFromAddition(String tableName,String additionalDataSetFileName) throws Exception{
		extractExpectedDataSetFromAdditionalOne(additionalDataSetFileName);
		return extractExpectedTable(tableName);
	}
	
	public ITable extractExpectedTable(String tableName,String expectedDataSetFileName) throws Exception{
		extractExpectedDataSet(expectedDataSetFileName);
		return extractExpectedTable(tableName);
	}
	
	
	public void assertExpectedRowCount(String tableName,int expectedRowCount){
	   	 try {			
				ITable actualTable = extractActualTable(tableName);
				int n= actualTable.getRowCount();
				System.out.println("row_count="+n);
				TestCase.assertEquals(expectedRowCount, n);  				
			} catch (Exception e) {
				e.printStackTrace();
				TestCase.fail(e.getMessage());
			} 
	   }
	
	public void assertExpectedRowCount(String tableName){
	   	 try {			
				ITable actualTable = extractActualTable(tableName);
				int n= actualTable.getRowCount();
				System.out.println("row_count="+n);
				ITable expectedTable = extractExpectedTable(tableName);
				
				TestCase.assertEquals(expectedTable.getRowCount(), n);  
				
			} catch (Exception e) {
				e.printStackTrace();
				TestCase.fail(e.getMessage());
			} 
	   }
	
	public void assertExpectedTableFromDataSet(String tableName,ITable expectedTable){
	   	 try {			
				ITable actualTable = extractActualTable(tableName);
							
				// filter unnecessary columns of current data by xml definition and  tabOfExcludedColumns
				Column[] tabOfColumns = expectedTable.getTableMetaData().getColumns();
				
				if( additionalDataSet!=null && initialDataSet!=null){
					//commun subset of column in tableName of inititialDataSet and tableName of additionalDataSet
					Column[] tabOfColumnsInit = this.initialDataSet.getTableMetaData(tableName).getColumns();
					Column[] tabOfColumnsAdd = this.additionalDataSet.getTableMetaData(tableName).getColumns();
					List<Column> colColumns = new ArrayList<Column>();
					for(Column c1 : tabOfColumnsInit){
						String c1Name = c1.getColumnName().toLowerCase();
						for(Column c2 : tabOfColumnsAdd){
							String c2Name = c2.getColumnName().toLowerCase();
							if(c1Name.equals(c2Name)){
								colColumns.add(c1); break;
							}
						}
					}
					tabOfColumns=new Column[colColumns.size()];
					for(int i=0;i<colColumns.size();i++)
						tabOfColumns[i]=colColumns.get(i);	
					
					expectedTable = DefaultColumnFilter.includedColumnsTable(expectedTable,tabOfColumns);
				}
				
				actualTable = DefaultColumnFilter.includedColumnsTable(actualTable,tabOfColumns);
				
				
				// Assert actual database table match expected table
				Assertion.assertEquals(expectedTable, actualTable);
			} catch (Exception e) {
				e.printStackTrace();
				TestCase.fail(e.getMessage());
			} 
	   }
	
	public void assertExpectedTable(String tableName,String expectedDataSetFileName){
   	 		try {
   	 			ITable expectedTable = extractExpectedTable(tableName,expectedDataSetFileName);
				assertExpectedTableFromDataSet(tableName,expectedTable);
			} catch (Exception e) {
				e.printStackTrace();
				TestCase.fail(e.getMessage());
			}			
   }
	

	public void assertExpectedTableFromAdditionDataSet(String tableName,String additionalDataSetFileName){
	   	 try {							
	   		   	ITable expectedTable = extractExpectedTableFromAddition(tableName,additionalDataSetFileName);
				assertExpectedTableFromDataSet(tableName,expectedTable);
			} catch (Exception e) {
				e.printStackTrace();
				TestCase.fail(e.getMessage());
			} 
	   }
	
	
	

}
