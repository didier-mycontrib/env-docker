package org.mycontrib.generic.test.dbunit.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;

public class EntityLoaderFromXmlDataSet {
	
	private IDataSet dataSet;
	
	public EntityLoaderFromXmlDataSet(){
		dataSet=null;
	}
	
	public EntityLoaderFromXmlDataSet(IDataSet dataSet){
		this.dataSet=dataSet;
	}
	
	public List<String> loadEntityValuesDifferences(Object e,Class c){
		List<String> listeDiff=new ArrayList<String>();
		try {
			String className=c.getSimpleName();
			String tableName=className;
			if(tableName.startsWith("_"))
				tableName=tableName.substring(1);
			ITable entityTable = this.dataSet.getTable(tableName);
			if(entityTable.getRowCount()>=1){
				Field[] fields = c.getDeclaredFields();
				for(Field f : fields){
					String fName= f.getName();
					 //fName = propertyName (java)
					String cName = getColumnNameFromPropertyNameOfEntity(c,fName);
					//cName = columnName (eventuellement different de fName si @Column dans @Entity)
					try {
						//int index= entityTable.getTableMetaData().getColumnIndex(fName);
						Object expectedValue= entityTable.getValue(0,cName);
						Object value = ReflectionUtil.getPropertyValue(e, fName);
						if(!expectedValue.toString().equals(value.toString())){
							String diffMsg =fName+"  value is =" +value + " , expected value="  + expectedValue;
							listeDiff.add(diffMsg);
						}else{
							//System.out.println(fName+"(java) is ok : value=" +value + " , expected value : "  + expectedValue + " , colomnName=" + cName );
						}
					} catch (Exception ee) {
						//column with name of field not found 
					}
				}
			}
		} catch (Exception e1) {			
			e1.printStackTrace();
		}
		return listeDiff;
	}
	
	public void loadEntityValues(Object e,Class c){
		try {
			String className=c.getSimpleName();
			String tableName=className;
			if(tableName.startsWith("_"))
				tableName=tableName.substring(1);
			ITable entityTable = this.dataSet.getTable(tableName);
			if(entityTable.getRowCount()>=1){
				Field[] fields = c.getDeclaredFields();
				for(Field f : fields){
					String fName= f.getName(); //fName = propertyName (java)
					String cName = getColumnNameFromPropertyNameOfEntity(c,fName);
					//cName = columnName (eventuellement different de fName si @Column dans @Entity)
					try {
						//int index= entityTable.getTableMetaData().getColumnIndex(cName);
						Object value= entityTable.getValue(0,cName);
						ReflectionUtil.setPropertyValue(e, fName,f.getType(), convertAs(value,f.getType()));
					} catch (Exception ee) {
						//column with name of field not found 
					}
				}
			}
		} catch (Exception e1) {			
			e1.printStackTrace();
		}
	}
	
	public static Object convertAs(Object originValue,Class destType){
		Object resValue=null;
		Class originType = originValue.getClass();
		String oTypeName=originType.getSimpleName();
		String dTypeName=destType.getSimpleName();
		if(oTypeName.equals(dTypeName)){
			resValue=originValue;
		}
		else if(dTypeName.equals("String")){
			resValue=String.valueOf(originValue);		
		}
		else if(dTypeName.equals("Double")){
			resValue=Double.parseDouble(String.valueOf(originValue));		
		}
		else if(dTypeName.equals("Integer")){
			resValue=Integer.parseInt(String.valueOf(originValue));		
		}
		else if(dTypeName.equals("Float")){
			resValue=Float.parseFloat(String.valueOf(originValue));		
		}
		else if(dTypeName.equals("Long")){
			resValue=Long.parseLong(String.valueOf(originValue));		
		}
		else if(dTypeName.equals("Boolean")){
			resValue=Boolean.valueOf(String.valueOf(originValue));		
		}
		else if(dTypeName.equals("Date")){
			//au format AAAA-MM-DD
			Date date=null;			
			String sDate = String.valueOf(originValue);	
			if(sDate.length()==10){
				String sYear = sDate.substring(0, 4);
				String sMonth = sDate.substring(5, 7);
				String sDay = sDate.substring(8, 10);
				Calendar cal = Calendar.getInstance();
				cal.set(Integer.parseInt(sYear), Integer.parseInt(sMonth) -1 , Integer.parseInt(sDay));
				date = cal.getTime();
				//System.out.println("*** date="+date);
			}
			return date;
		}
		//System.out.print("convertAs: (originalValue=" + originValue + " ,originalType:"+oTypeName+")--");
		//System.out.println("(resValue=" + resValue + " ,resType:"+dTypeName+")");
		return resValue;
	}
	

	public String getColumnNameFromPropertyNameOfEntity(Class c,String pName){
		String cName=null;
		try {
			Entity anotEntity = (Entity) c.getAnnotation(Entity.class);
			if(anotEntity!=null){
				Field f = c.getDeclaredField(pName);
				Column anotColumn = (Column) f.getAnnotation(Column.class);
				if(anotColumn!=null)
					cName=anotColumn.name();
			}
		} catch (Exception e) {
			//e.printStackTrace();
			System.err.println(e.getMessage());
		}
		if(cName==null) 
			cName=pName; //par Defaut
		return cName;
	}

	
	public IDataSet getDataSet() {
		return dataSet;
	}

	public void setDataSet(IDataSet dataSet) {
		this.dataSet = dataSet;
	}

}
