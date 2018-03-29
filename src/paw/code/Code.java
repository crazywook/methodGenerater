package paw.code;

import java.lang.reflect.Field;

import onnet.smartprinterservlet.IcCard;

public class Code {
	
	public static String generatePutter(Class<?> clazz) {
		
		String switchFormat;		
		String switchPrefix = "switch(%s) {\r\n";		
		String caseFormat = 
			"case \"%s\" : \r\n"
			+"\tset%s(value)\r\n"
			+"\tbreak;\r\n";
		
		String switchSuffix = "default:\r\n}";
		
		Field[] fields = clazz.getDeclaredFields();
		
		switchFormat = switchPrefix;
		
		for(Field field : fields) {
			
			String name = field.getName();
			String pascalName = name.substring(0, 1).toUpperCase() + name.substring(1);
			switchFormat += String.format(caseFormat, name, pascalName);
		}
		
		return switchFormat+switchSuffix;
	}
	
	public static String generateEquals(Class<?> clazz) {
		
		String format = "";
		String className = clazz.getSimpleName();
		String formatPrefix = String.format("%s instance = (%s)obj;\r\n"+"return ", className, className); 		
		String returnFormat = "&& this.%s.equals(instance.get%s())\r\n";
		String formatSuffix = ";";
		
		Field[] fields = clazz.getDeclaredFields();
		
		for(Field field : fields) {
			
			String name = field.getName();
			format += String.format( returnFormat, name, upperInit(name) );
		}
		format = format.substring(3, format.length()-2);
		return formatPrefix + format + formatSuffix;
	}
	
	public static String lowerInit(String name) {
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}
	
	public static String upperInit(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}
}
