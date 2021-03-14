

package application;

/**
This Profile class is used to get and set the information of each employee including name, department and date hired
It sets and gets these values as necessary and also inherits the functions from the date class to check the validity 
of the date that the employee was hired. 
@author Bhumit Patel
@author Soorya Srivatsa
*/
public class Profile extends Date {

	private String name; //employee’s name in the form “lastname,firstname”
	private String department; //department code: CS, ECE, IT
	private Date dateHired;
	

	/**
	This method gets the value of the private variable name
	@return The name of the employee
	*/
	public String getName() {
		return name;
	}
	
	/**
	This method gets the value of the private variable department
	@return The department of the employee
	*/
	public String getDept() {
		return department;
	}
	
	/**
	This method gets the value of the private object Date
	@return The date that the employee was hired
	*/
	public Date getDate() {
		return dateHired;
	}
	
	/**
	This method sets the value of the private variable name
	@param name This is the name of the employee extracted from the command line
	*/
	public void setName(String name) {
		this.name=name;
	}
	
	/**
	This method sets the value of the private variable department 
	@param department  This is the department of the employee extracted from the command line
	*/
	public void setDept(String department) {
		this.department=department;
	}
	
	/**
	This method sets the value of the private object date
	@param dateHired  This is the date that the employee was hired, extracted from the command line
	*/
	public void setDate(Date dateHired) {
		this.dateHired=dateHired;
	}
	
	
	/**
	This method converts all employee profile information into a string
	@return A string with employee profile details
	*/
	@Override
	public String toString() { 
		return name+ "::" +department+ "::" + dateHired.dateToString();
	}
	
	
	/**
	This method checks if the object is of type profile or not
	@param obj The object that is to be compared 
	@return true if object is of type Profile and false otherwise 
	*/
	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof Profile)) {
			return false;
		}
		Profile profileCompared = (Profile) obj;
		if(!profileCompared.getName().equals(this.name)) {
			return false;
		}
		if(!profileCompared.getDept().equals(this.department)) {
			return false;
		}
		if(profileCompared.getDate().compareTo(this.dateHired) != 0) {
			return false;
		}
		return true;
		
	} //compare name, department and dateHired
	
	
}
