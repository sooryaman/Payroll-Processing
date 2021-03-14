

package application;

import java.text.DecimalFormat;

/**
The Management class is used to calculate the payments. This class is a sub class of FullTime and thus, Employee.
@author Bhumit Patel
@author Soorya Srivatsa
*/
public class Management extends Fulltime {

	private int managementRole;
	private double compensation;


	/**
	This method sets the value of the managementRole as described by the user
	@param managementRole is the role of the management Employee
	*/
	public void setManagementRole(int managementRole) {
		this.managementRole = managementRole;
	}
	
	/**
	This method calculates the additional compensation of management employees based on their role
	*/
	public void setCompensation() {
		
		final double MANAGER_EXTRA= 5000;
		final double DEPT_HEAD_EXTRA = 9500;
		final double DIRECTOR_EXTRA = 12000;
		final int PAYMENTS_PER_YEAR = 26;
		final int MANAGER = 1;
		final int DEPARTMENT_HEAD = 2;
		
		if(managementRole == MANAGER) {
			
			compensation = MANAGER_EXTRA/ PAYMENTS_PER_YEAR;
			
		}
		else if(managementRole == DEPARTMENT_HEAD) {
			
			compensation = DEPT_HEAD_EXTRA/ PAYMENTS_PER_YEAR;
			
		}
		else{
			
			compensation = DIRECTOR_EXTRA/ PAYMENTS_PER_YEAR;
			
		}
		
	}

    /**
	This method is an overridden one that is used to calculate the payments of the management employees in the database
	*/
	@Override
	public void calculatePayment() {
		
		final int PAYMENTS_PER_YEAR = 26;
		setPayment((getSalary() / PAYMENTS_PER_YEAR) + compensation);
		
	}
	
	/**
	This method checks if the object is of type Management
	@param obj The object to be compared
	@return true of the object is of type Management 
	*/
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof Management) {
			return true;
		}
		return false;
		
	}
	
	
	/**
	This method converts all management employee information into string form
	@return this method returns all the information of the employee in string form
	*/
	@Override
	public String toString() {
		
		DecimalFormat format1 = new DecimalFormat("###,##0.00");
		final int MANAGER = 1;
		final int DEPARTMENT_HEAD = 2;
		
		if (managementRole == MANAGER) {
			
			return  "::Payment $" + format1.format(getPayment()) + "::FULL TIME::Annual Salary $" +
					format1.format(getSalary()) + "::Manager Compensation $" + format1.format(compensation);
		
		}
		else if (managementRole == DEPARTMENT_HEAD) {
			
			return  "::Payment $" + format1.format(getPayment()) + "::FULL TIME::Annual Salary $" +
					format1.format(getSalary()) + "::DepartmentHead Compensation $" + format1.format(compensation);
		
		}
		else {
			
			return  "::Payment $" + format1.format(getPayment()) + "::FULL TIME::Annual Salary $" +
					format1.format(getSalary()) + "::Director Compensation $" + format1.format(compensation);
		
		}
		
	}
	
}
