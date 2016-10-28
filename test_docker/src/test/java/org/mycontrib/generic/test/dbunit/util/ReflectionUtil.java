package org.mycontrib.generic.test.dbunit.util;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

public class ReflectionUtil {

	public static String getPropertyValue(Object bean, String paramName) {
		String res = null;
		try {
			Class c = bean.getClass();
			Method m = c.getDeclaredMethod("get" + firstUpper(paramName), null);
			Object val = m.invoke(bean, null);
			res = String.valueOf(val);
			//System.out.println("val:"+res + "type="+val.getClass().getSimpleName());
			if( val.getClass().getSimpleName().equals("Timestamp") && res.length()>10)
				res=res.substring(0,10);
			if( val.getClass().getSimpleName().equals("Date") ){
				Calendar cal = Calendar.getInstance();
				cal.setTime((Date)val);
				String chMonth = String.valueOf(cal.get(Calendar.MONTH) +1);
				if(chMonth.length()==1)
					chMonth="0"+chMonth;
				res=""+cal.get(Calendar.YEAR) +"-"+chMonth +"-"+ cal.get(Calendar.DAY_OF_MONTH);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static String setPropertyValue(Object bean, String propertyName,Class propertyClass,Object propertyValue) {
		String res = null;
		try {
			Class c = bean.getClass();		
			Class tabOfParamTypes[] = new Class[1];
			tabOfParamTypes[0]=propertyClass;
			Method m = c.getDeclaredMethod("set" + firstUpper(propertyName), tabOfParamTypes);
			Object tabOfValues[] = new Object[1];
			tabOfValues[0]=propertyValue;
			m.invoke(bean, tabOfValues);
		} catch (Exception e) {
			System.err.println("echec setPropertyValue:");
			System.err.println("propertyName:"+propertyName);
			System.err.println("propertyClass:"+propertyClass.getSimpleName());
			System.err.println("propertyValue:"+propertyValue);
			System.err.println("propertyValueType:"+propertyValue.getClass().getSimpleName());
			e.printStackTrace();
		}
		return res;
	}
	
	public static String firstLower(String s){
		if(s==null)return null;
		if(s.length()==1)return s.toLowerCase();
		/*else*/
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}
	
	public static String firstUpper(String s){
		if(s==null)return null;
		if(s.length()==1)return s.toUpperCase();
		/*else*/
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
}
