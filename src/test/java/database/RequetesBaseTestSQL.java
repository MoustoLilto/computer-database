package test.java.database;

public enum RequetesBaseTestSQL {
	CREATION_COMPANY("  create table company (\n" + 
			"    id                        bigint not null ,\n" + 
			"    name                      varchar(255),\n" + 
			"    constraint pk_company primary key (id)\n" + 
			"  );"),
	CREATION_COMPUTER("  create table computer (\n" + 
			"    id                        bigint not null ,\n" + 
			"    name                      varchar(255),\n" + 
			"    introduced                timestamp NULL,\n" + 
			"    discontinued              timestamp NULL,\n" + 
			"    company_id                bigint default NULL,\n" + 
			"    constraint pk_computer primary key (id)\n" + 
			"  );"),
	DELETION_COMPANY("DROP TABLE company;"),
	DELETION_COMPUTER("DROP TABLE computer;"),
	ADMIN_USER("CREATE USER admincdb PASSWORD qwerty1234 ADMIN"),
	INSERT_COMPANY_1("insert into company (id,name) values (  1,'Apple Inc');"),
	INSERT_COMPANY_2("insert into company (id,name) values (  2,'IBM');"),
	INSERT_COMPANY_3("insert into company (id,name) values (  3,'Dell');"),
	INSERT_COMPUTER_1("insert into computer (id,name,introduced,discontinued,company_id) values (  1,'MacBook Pro 15.4 inch\',NULL,NULL,1);"),
	INSERT_COMPUTER_2("insert into computer (id,name,introduced,discontinued,company_id) values (  2,'MacBook retina\',NULL,NULL,1);"),
	INSERT_COMPUTER_3("insert into computer (id,name,introduced,discontinued,company_id) values (  3,'Monster Black',NULL,NULL,3);"),
	INSERT_COMPUTER_4("insert into computer (id,name,introduced,discontinued,company_id) values (  4,'Asus Boss',NULL,NULL,2);");
	
	private String requete = "";
	
	RequetesBaseTestSQL(String requete){
		this.requete = requete;
	}
	
	public String toString() {
		return requete;
	}
}
