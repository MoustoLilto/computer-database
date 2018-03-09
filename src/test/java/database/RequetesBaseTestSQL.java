package test.java.database;

public enum RequetesBaseTestSQL {
	CREATION_COMPANY("  create table company (\n" + 
			"    id                        INTEGER IDENTITY PRIMARY KEY,\n" + 
			"    name                      varchar(255),\n" + 
			"  );"),
	CREATION_COMPUTER("  create table computer (\n" + 
			"    id                        INTEGER IDENTITY PRIMARY KEY,\n" + 
			"    name                      varchar(255),\n" + 
			"    introduced                timestamp NULL,\n" + 
			"    discontinued              timestamp NULL,\n" + 
			"    company_id                INTEGER DEFAULT NULL,\n" + 
			"  );"),
	DELETION_COMPANY("DROP TABLE company;"),
	DELETION_COMPUTER("DROP TABLE computer;"),
	ADMIN_USER("CREATE USER admincdb PASSWORD qwerty1234 ADMIN"),
	INSERT_COMPANY_1("insert into company (name) values (  'Apple Inc');"),
	INSERT_COMPANY_2("insert into company (name) values (  'IBM');"),
	INSERT_COMPANY_3("insert into company (name) values (  'Dell');"),
	INSERT_COMPUTER_1("insert into computer (name,introduced,discontinued,company_id) values (  'MacBook Pro 15.4 inch\',NULL,NULL,1);"),
	INSERT_COMPUTER_2("insert into computer (name,introduced,discontinued,company_id) values (  'MacBook retina\',NULL,NULL,1);"),
	INSERT_COMPUTER_3("insert into computer (name,introduced,discontinued,company_id) values (  'Monster Black',NULL,NULL,2);"),
	INSERT_COMPUTER_4("insert into computer (name,introduced,discontinued,company_id) values (  'Asus Boss',NULL,NULL,2);"),
	
	NOMBRE_COMPUTER("SELECT COUNT(*) FROM computer;"),
	NOMBRE_COMPANY("SELECT COUNT(*) FROM company;");
	
	private String requete = "";
	
	RequetesBaseTestSQL(String requete){
		this.requete = requete;
	}
	
	public String toString() {
		return requete;
	}
}
