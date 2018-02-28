package main.java.com.excilys.computer.database.dto;

public class DTOCompany {
	private long id;
	private String name;
	
	public DTOCompany(String name) {
		this.name = name;
	}
	public DTOCompany(long id, String name) {
		this.id = id;
		this.name = name;
	}
	public DTOCompany() {
	}
	public long getId() {
		return id;
	}
	public void setId(String id) {
		long goodID = Long.parseLong(id);
		this.id = goodID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "DTOCompany [id=" + id + ", name=" + name + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOCompany other = (DTOCompany) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
