
package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
The Company class is what we use to alter the employee array and output its data as described by the user.
It is the class that we use to add, remove and print the contents of the array
@author Bhumit Patel
@author Soorya Srivatsa
*/

public class Company {

	private Employee[] emplist; // this is the array used to store the employees
	private int numEmployee; // this variable stores the number of employees in the array

	/**
	 This method is the default constructor that initializes the Company object when it is created
	 */
	public Company() {
		final int CAPACITY = 4; // setting initial capacity to 4
		this.emplist = new Employee[CAPACITY];
		this.numEmployee = 0; // initial number of employees in the array is 0
	}

	/**
		This method returns the number of employees in the array
	 	@return returns an int that stores number of employees in the array
	 */
	public int getNumEmployee() {
		return numEmployee;
	}

	/**
	  This method is used to find the location of an employee in the list and if
	  found, returns the index at which the employee is found
	  
	  @param employee This method takes an employee and looks for it in the array
	  @return an int index which stores location of employee found, otherwise
	          returns -1 if not found
	 */
	private int find(Employee employee) {

		for (int i = 0; i < numEmployee; i++) {
			if (emplist[i].getProfile().equals(employee.getProfile())) {
				return i;
			}
		}
		return -1;

	}

	/**
	  This method grows the size of the array by 4 if it is full
	 */
	private void grow() {

		final int INCREMENT_CAPACITY = 4;
		Employee[] temp = new Employee[emplist.length + INCREMENT_CAPACITY]; // Creating a temporary array to store the
																				// contents of the employee list
		for (int i = 0; i < emplist.length; i++) {
			temp[i] = emplist[i];
		}
		emplist = temp; // setting emplist to the new, larger array

	}

	/**
	  This method adds an employee to the array and the employee information is
	  extracted from the command line
	  
	  @param employee The employee to be added
	  @return true if add is successful and false otherwise
	 */
	public boolean add(Employee employee) {

		if (find(employee) == -1) {
			if (numEmployee == emplist.length) {
				grow(); // increments the size of the employee list by 4 if not enough space
			}
			emplist[numEmployee] = employee;
			numEmployee++; // increments number of employees in employee list
			return true;
		}
		return false;

	}

	/**
	  This method is used to find an employee and remove it from the array if
	  found.
	  
	  @param employee The employee that is to be removed from the array
	  @return true if removal was successful and false otherwise
	 */
	public boolean remove(Employee employee) {

		int index = find(employee); // index stores the location of the employee found in the database
		if (index == -1) { // index is -1 when the employee hasn't been found
			return false;
		}

		for (int i = index; i < numEmployee - 1; i++) {
			emplist[i] = emplist[i + 1];
		}
		emplist[numEmployee - 1] = null; // deletes the employee at the location it was found at
		numEmployee--; // decrements the number of employees in the database
		return true;

	}

	/**
	  This method looks for the parttime employee from the list and if found, sets
	  the number of hours for that employee
	  
	  @param employee The employee who's hours are to be set
	  @return true if hours are successfully added and false otherwise
	 */
	public boolean setHours(Employee employee) {

		if (!(employee instanceof Parttime)) {
			return false;
		}
		int index = find(employee);
		if (index == -1) {
			return false;
		}
		if (!(emplist[index] instanceof Parttime)) {
			return false;
		}
		Parttime tempEmployee = (Parttime) employee;
		Parttime targetEmployee = (Parttime) emplist[index];
		targetEmployee.setNumHours(tempEmployee.getNumHours());
		return true;

	}

	/**
	  This method is used calculate the payments of all the employees in the array
	 */
	public void processPayments() {

		for (int i = 0; i < numEmployee; i++) {

			emplist[i].calculatePayment();

		}

	}

	/**
	  This method prints the employee array in the order in which it exists

	  @param index The index of the employee in the database to be printed
	  @return A string containing all the employee's information
	 */
	public String print(int index) {

		return emplist[index].getProfile().toString() + emplist[index].toString();
		
	}

	/**
	 This method prints the employee array in the order of department in which the employees work
	 */
	public void printByDepartment() {

		for (int i = 0; i < numEmployee; i++) { // bubble sort algorithm to rearrange the books by department
			for (int j = 0; j < numEmployee - i - 1; j++) {
				if (emplist[j].getProfile().getDept().compareTo(emplist[j + 1].getProfile().getDept()) > 0) {
					Employee temp = emplist[j];
					emplist[j] = emplist[j + 1];
					emplist[j + 1] = temp;
				}
			}
		}
	}

	/**
	 This method prints the employee array in the order of the date on which they were hired
	 */
	public void printByDate() {

		for (int i = 0; i < numEmployee; i++) { // bubble sort algorithm to rearrange the books by date published
			for (int j = 0; j < numEmployee - i - 1; j++) {
				if (emplist[j].getProfile().getDate().compareTo(emplist[j + 1].getProfile().getDate()) == 1) { // compares the dates
					Employee temp = emplist[j];
					emplist[j] = emplist[j + 1];
					emplist[j + 1] = temp;
				}
			}
		}
	}
	
	
	/**
	This method accepts a text file and populates the file with the employee database only if the file is empty. 
	The method handles all invalid inputs by returning different messages for the user 
	
	@param targetFile This is the file in which the database is to be exported 
	@return A string with the status message indicating the status of the export 
	*/
	public String exportDatabase(File targetFile) {

		try {

			Scanner sc = new Scanner(targetFile);

			if (sc.hasNext()) { //checks if the file is empty and returns appropriate status message

				sc.close();
				return "File must be empty!\n";

			}

			PrintWriter pw = new PrintWriter(targetFile);

			for (int i = 0; i < getNumEmployee(); i++) {

				pw.println(print(i)); //prints employee line by line in current order

			}
			pw.close();
			sc.close();
			return "Payroll processing exported!\n"; 

		} catch (FileNotFoundException e) {
			return "File Not Found!\n"; //file that user selected is not found
		} catch (Exception e) {
			return "Export failed. Invalid file selection.\n";
		}

	}
	
}
