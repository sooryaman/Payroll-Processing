

package application;


/**
The Employee class is the super class for the different types of employees and each object of employee class is a different 
employee that has been added to the employee list. Using inheritance and polymorphism, we can perform different functions based
on the type of employee without having to duplicate code
@author Bhumit Patel
@author Soorya Srivatsa
*/
public class Employee {

	private Profile employeeProfile;
	private double salary;
	private double payment;

	/**
	 * This method returns the value of the private object employeeProfile
	 * 
	 * @return profile of the employee
	 */
	public Profile getProfile() {
		return employeeProfile;
	}

	/**
	 * This method sets the value of the private variable payment
	 * 
	 * @param payment This variable is to be used to set payment
	 */
	public void setPayment(double payment) {
		this.payment = payment;
	}

	/**
	 * This method returns the value of the private variable payment
	 * 
	 * @return a double variable payment
	 */
	public double getPayment() {
		return payment;
	}

	/**
	 * This method sets the value of the private variable salary from user input
	 * 
	 * @param salary The value obtained from user input string
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}

	/**
	 * This method gets the value of the private variable salary
	 * 
	 * @return salary The value obtained private variable salary
	 */
	public double getSalary() {
		return salary;
	}

	/**
	 * This method sets the value of the private object employeeProfile
	 * 
	 * @param employeeProfile this is the obtained information from the user input
	 *                        to be put into an employee profile
	 */
	public void setProfile(Profile employeeProfile) {
		this.employeeProfile = employeeProfile;
	}

	/**
	 * This method is an overloaded on that is used to calculate the payments of the
	 * employees in the database
	 */
	public void calculatePayment() {

	}

	/**
	 * This method converts all employee information to string form
	 * 
	 * @return this method returns all the information of the employee in string
	 *         form
	 */
	@Override
	public String toString() {

		return toString();

	}

	/**
	 * This method checks if the object is of type employee
	 * 
	 * @param obj The object to be compared
	 * @return true of the object is of type employee
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj instanceof Employee) {
			return true;
		}
		return false;

	}
}
