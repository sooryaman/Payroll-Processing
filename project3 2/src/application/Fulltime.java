

package application;

import java.text.DecimalFormat;


/**
The FullTime class calculates the payments of the full time employees. This class is a sub class of employee.
@author Bhumit Patel
@author Soorya Srivatsa
*/
public class Fulltime extends Employee{

	/**
	This method is an overridden one that is used to calculate the payments of the fulltime employees in the database
	*/
	@Override
	public void calculatePayment() {
		
		final int PAYMENTS_PER_YEAR = 26;
		setPayment(getSalary() / PAYMENTS_PER_YEAR);
		
	}
	
	/**
	This method checks if the object is of type Fulltime
	@param obj The object to be compared
	@return true of the object is of type Fulltime
	*/
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof Fulltime) {
			return true;
		}
		return false;
		
	}
	
	
	/**
	This method converts all employee info into a string
	@return this method returns all the information of the employee in string form
	*/
	@Override
	public String toString() {
		
		DecimalFormat format1 = new DecimalFormat("###,##0.00");
		return "::Payment $" + format1.format(getPayment()) + "::FULL TIME::Annual Salary $" + format1.format(getSalary());
	
	}
	
}
