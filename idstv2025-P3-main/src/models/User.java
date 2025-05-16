package models;

import java.sql.Date;

public class User {
	
	public int id;
	public String name;
	public String email;
	public String role;
	public String phone;
	public Date create_at;
	public Date update_at;
	
	public User(int id, String name, String email, String role, String phone, Date create, Date update) {
		
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
		this.phone = phone;
		this.create_at = create;
		this.update_at = update;
		
	}

	public User(int int1, String string, String string2, String string3, String string4, String string5,
			String string6) {
	}

}
