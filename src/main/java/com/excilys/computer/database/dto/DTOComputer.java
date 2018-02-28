package main.java.com.excilys.computer.database.dto;

public class DTOComputer {
	private long id;
	private String name;
	private String introduced;  
	private String discontinued; 
	private long companyID;
	private String companyName;
	
	public DTOComputer(String name, String introduced, String discontinued, long companyID, String companyName) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyID = companyID;
		this.companyName = companyName;
	}
	public DTOComputer(long id, String name, String introduced, String discontinued, long companyID,
			String companyName) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyID = companyID;
		this.companyName = companyName;
	}
	public DTOComputer() {
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
	public String getIntroduced() {
		return introduced;
	}
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	public String getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	public long getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		long goodID = Long.parseLong(companyID);
		this.companyID = goodID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Override
	public String toString() {
		return "DTOComputer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", companyID=" + companyID + ", companyName=" + companyName + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (companyID ^ (companyID >>> 32));
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
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
		DTOComputer other = (DTOComputer) obj;
		if (companyID != other.companyID)
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
