
package application;

import java.text.DecimalFormat;


/**
The Parttime class is a subclass of Employee that calculates the payments for all Parttime Employees in the database
and also handles special cases like overtime.
@author Bhumit Patel
@author Soorya Srivatsa
*/
public class Parttime extends Employee {
	
	private int numHours;


    /**
	This method gets the value of the number of hours that the parttime employee works
	@return number of hours that the parttime employee has worked
	*/
	public int getNumHours() {
		return numHours;
	}
	
	
	/**
	This method sets the value of the private variable numHours
	@param numHours This variable is to be used to calculate part time employee payment
	*/
	public void setNumHours(int numHours) {
		this.numHours = numHours;
	}
	
	/**
	This method is an overridden one that is used to calculate the payments of the parttime employees in the database
	*/
	@Override
	public void calculatePayment() {
	
		final int MAX_HOURS = 80;
		final double OVERTIME_MULTIPLIER = 1.5;
		if(numHours <= MAX_HOURS) {
			setPayment(numHours*getSalary());
			return;
		}
		setPayment((MAX_HOURS + (numHours-MAX_HOURS)*OVERTIME_MULTIPLIER) * getSalary());
	
	}
	
	/**
	This method checks if the object is of type Parttime
	@param obj The object to be compared
	@return true of the object is of type Parttime
	*/
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof Parttime) {
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
		return "::Payment $" + format1.format(getPayment()) + "::PART TIME::Hourly Rate $" +
						format1.format(getSalary()) + "::Hours worked this period: " + numHours;
		
	}
	
}
