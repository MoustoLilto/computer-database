package main.java.com.excilys.computer.database.mapper;

public class MapperSQL {
	private static MapperSQL mapperSQL = null;
	
	private MapperSQL() {
	}
	
	public static MapperSQL getInstance() {
		if (mapperSQL == null) {
			mapperSQL = new MapperSQL();
		}
		return mapperSQL;
	}
	
	public String toSQL(String field) {
		String attribute = "";
		return attribute;
	}
}
