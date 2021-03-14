
package application;

// The following lines import the java and javafx library classes that we need to execute the program

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
The Controller class is the driver class that reads the input from GUI and interprets the data and implements 
the different classes to make the Payroll Processing program work. The class also has error handling that
lets the user know if a selection is invalid.
@author Bhumit Patel
@author Soorya Srivatsa
*/
public class Controller {

	private Company company = new Company(); //Creating a new company database 


/* 
The following private fields are the GUI fields that we get from the FXML file
*/

	@FXML
	private Button setHoursButton;
	@FXML
	private ToggleGroup employeeType, dept, managementRole;
	@FXML
	private TextField employeeName, annualSalary, hourlyRate, hours;
	@FXML
	private DatePicker date;
	@FXML
	private TextArea messageArea1, messageArea2;
	
	
	/**
	This method disables the options that do not relate to full time employees, like
	Set Hours button, manager type, hourly rate and hours worked 
	@param event This event represents the user clicking the fulltime radio button 
	*/
	@FXML
	void fulltimeButton(ActionEvent event) {

		managementRole.getToggles().forEach(toggle -> {
			Node node = (Node) toggle;
			node.setDisable(true); //disables management role toggle buttons
		});
		annualSalary.setDisable(false); //Enables annualSalary text field
		setHoursButton.setDisable(true); //disables Set Hours button
		hourlyRate.setDisable(true); //disables hourlyRate text field
		hours.setDisable(true);  //disables hours text field

	}
	
	
	
	/**
	This method disables the options that do not relate to part time time employees, like 
	manager type and annual salary
	@param event This event represents the user clicking the Parttime radio button 
	*/
	@FXML
	void parttimeButton(ActionEvent event) {

		managementRole.getToggles().forEach(toggle -> {
			Node node = (Node) toggle;
			node.setDisable(true); //disables managment role toggle buttons
		});
		annualSalary.setDisable(true); //disables annualSalary text field
		setHoursButton.setDisable(false); //enables Set Hours button
		hourlyRate.setDisable(false); //enables hourly rate text field
		hours.setDisable(false); //enables hours text field

	}


	
	/**
	This method disables the options that do not relate to management employees, like 
	Set Hours button, hourly rate and hours worked
	
	@param event This event represents the user clicking the Parttime radio button 
	*/
	@FXML
	void managementButton(ActionEvent event) {

		managementRole.getToggles().forEach(toggle -> {
			Node node = (Node) toggle;
			node.setDisable(false); //enables management role toggle buttons
		});
		annualSalary.setDisable(false); //enables annualSalary text field
		setHoursButton.setDisable(true); //disables Set Hours button
		hourlyRate.setDisable(true); //disables hourly rate text field
		hours.setDisable(true); //disables hours text field

	}


	/**
	This method is used to extract the employee profile information from the user inputs (From GUI). 
	The method handles exceptions including invalid inputs and empty fields
	
	@return The method returns the profile of the employee 
	*/
	private Profile extractProfile() {

		try {

			String name = employeeName.getText(); //gets name from GUI text field
			if (name.isEmpty()) { //Prints error message if name field is empty
				messageArea1.appendText("Name cannot be empty!\n"); 
				return null;
			}
			
			/*
			The following for loop checks if the name has any special characters or digits and prints
			an error message if 
			*/
			for(int i=0;i<name.length();i++) { 
				if(!(Character.isLetter(name.charAt(i))) && !(name.charAt(i)==',')) {
					messageArea1.appendText("Name is invaild!\n");
					return null;
				}
			}

			String department = "";
			
			/*
			The try - catch block is used to handle an NullPointer exception when user doesn't select department
			and prints an error statement
			*/
			try {
				RadioButton deptType = (RadioButton) dept.getSelectedToggle();
				department = deptType.getText();
			} catch (Exception e) {
				messageArea1.appendText("Select a department!\n");
				return null;
			}

			Profile profile = new Profile(); //creating a new profile and setting the name and dept
			profile.setName(name);
			profile.setDept(department);

			if (date.getValue() == null) { //Exception handling for when user does not input a date (NullPointerException)
				messageArea1.appendText("Date cannot be empty!\n");
				return null;
			}

			/*
			The lines below convert the date from the yyyy-mm-dd format to mm/dd/yyy
			*/
			String[] tempString = date.getValue().toString().split("-"); 
			String newDate = tempString[1] + "/" + tempString[2] + "/" + tempString[0];
			Date dateHired = new Date(newDate);

			if (dateHired.isValid()) { //checks the validity of the date and prints error message accordingly
				profile.setDate(dateHired);
			} else {
				messageArea1.appendText(newDate + " is not a valid date!\n");
				return null;
			}

			return profile; //returns the profile with the employee information

		} catch (Exception e) { //catch block prints error incase of any unforseen exception
			messageArea1.appendText("Invalid input!\n");
			return null;
		}

	}


	/**
	This void method is called when the Add button is clicked on the GUI and appropriately adds the employee into the database.
	The method checks the validity of the user input in the fields (other than profile fields) and handles errors accordingly.
	@param event event This event represents the user clicking the Add radio button 
	*/
	@FXML
	void addCommand(ActionEvent event) {

		boolean flag = false;

		try {

			Profile profile = new Profile();
			profile = extractProfile(); //extracts file using the helper method
			if (profile == null) {
				return;
			}

			String type = "";
			/*
			The try - catch block is used to handle an NullPointer exception when user doesn't select employee type
			and prints an error statement
			*/
			try { //Setting string type to the user selection of employee type in the GUI
				RadioButton newEmployeeType = (RadioButton) employeeType.getSelectedToggle(); 
				type = newEmployeeType.getText();
			} catch (Exception e) {
				messageArea1.appendText("Select an employee type!\n");
				return;
			}

			if (type.equals("Fulltime")) {

				if (annualSalary.getText().isEmpty()) { //checks if salary text field is empty and prints message if it is
					messageArea1.appendText("Annual salary cannot be empty!\n");
					return;
				}

				Fulltime newFullTime = new Fulltime(); // creating a new fulltime employee
				double salary = Double.parseDouble(annualSalary.getText());

				if (salary < 0) { //if block makes sure that annual salary is not negative

					messageArea1.appendText("Annual salary cannot be negative!\n");
					return;

				} else {

					newFullTime.setSalary(salary); // setting the annual salary of the fulltime worker
					newFullTime.setProfile(profile);
					flag = company.add(newFullTime); // adding the new full time employee

				}

			} else if (type.equals("Parttime")) {

				if (hourlyRate.getText().isEmpty()) { //checks if hours text field is empty and prints message if it is
					messageArea1.appendText("Hourly rate cannot be empty!\n"); 
					return;
				}

				Parttime newParttime = new Parttime(); //creating a new parttime employee
				double salary = Double.parseDouble(hourlyRate.getText()); //Converting the String to a double
				;

				if (salary < 0) { //if block makes sure that hourly rate is positive

					messageArea1.appendText("Hourly rate cannot be negative!\n");
					return;

				} else {

					newParttime.setSalary(salary); // setting the hourly rate of the parttime worker
					newParttime.setNumHours(0); //setting hours of the parttime employee
					newParttime.setProfile(profile);
					flag = company.add(newParttime); // adding the new part time employee

				}

			} else if (type.equals("Management")) {

				if (annualSalary.getText().isEmpty()) { //checls if salary text field is empty and prints message if it is
					messageArea1.appendText("Annual salary cannot be empty!\n");
					return;
				}

				Management newManagement = new Management(); //creating new management emloyee
				double salary = Double.parseDouble(annualSalary.getText()); //converting the String to double

				String newRole = "";
				
				/*
				The try - catch block is used to handle an NullPointer exception when user doesn't select management
				role and prints an error statement
				*/
				try { //Setting string newRole to the user selection of manager type in the GUI
					RadioButton newManagementRole = (RadioButton) managementRole.getSelectedToggle();
					newRole = newManagementRole.getText();
				} catch (Exception e) {
					messageArea1.appendText("Select a management role!\n");
					return;
				}
				
				/*
				Below, the role of the manager is extracted 
				*/
				int role;
				final int MANAGER = 1;
				final int DEPT_HEAD = 2;
				final int DIRECTOR = 3;

				if (newRole.equals("Manager")) {
					role = MANAGER;
				} else if (newRole.equals("Manager")) {
					role = DEPT_HEAD;
				} else {
					role = DIRECTOR;
				}

				if (salary < 0) { // this if condition checks the validity of salary

					messageArea1.appendText("Annual salary cannot be negative!\n");
					return;

				} else {

					newManagement.setSalary(salary); // setting the annual salary of the management worker
					newManagement.setManagementRole(role); // sets the managers role
					newManagement.setCompensation();
					newManagement.setProfile(profile); // sets managers profile
					flag = company.add(newManagement);

				}
			}

		} catch (Exception e) {
			messageArea1.appendText("Invalid input.\n");
			return;
		}
		
		//The if block checks if the flag is true or false and accordingly prints if the add is successful or not 
		
		if (flag == true) {
			messageArea1.appendText("Employee added.\n");
			return;
		} else {
			messageArea1.appendText("Employee is already in the list.\n");
			return;
		}

	}
	
	/**
	This void method is called when the remove button is clicked on in the GUI and appropriately removes the employee from the database
	if it is found. The method checks the validity of the user input in the fields (other than profile fields) and handles errors accordingly.
	@param event event This event represents the user clicking the Remove  button 
	*/
	@FXML
	void removeCommand(ActionEvent event) {

		try {

			Profile profile = new Profile();
			profile = extractProfile();
			if (profile == null) {
				return;
			}

			if (company.getNumEmployee() == 0) { // if employee is found in the list the it is removed
				messageArea1.appendText("Employee database is empty.\n");
				return;
			}

			Employee removeEmployee = new Employee();
			removeEmployee.setProfile(profile); // sets the profile of the employee to be searched for in the database
			if (company.remove(removeEmployee)) {
				messageArea1.appendText("Employee Removed.\n");
			} else {
				messageArea1.appendText("Employee does not exist.\n");
			}
			return;

		} catch (Exception e) {
			messageArea1.appendText("Invalid input!\n");
			return;
		}

	}
	
	
	/**
	This void method is called when the clear button is clicked and clears all the text fields, message area and toggled radio buttons
	@param event  This event represents the user clicking the Clear button 
	*/
	@FXML
	void clearCommand(ActionEvent event) {

		messageArea1.clear(); //clears the text area in the employee management tab
		employeeName.setText(""); //clears the Name text field
		date.setValue(null);  // clears the DatePicker field
		annualSalary.setText(""); //clears the salary text field
		hourlyRate.setText("");  //clears the hourly rate text field
		hours.setText("");  //clears the hours text field
		
		/*
		The block of code below deselects all the toggled radiobuttons for Employee type, Department and Managemet Role
		*/
		
		employeeType.getToggles().forEach(toggle -> {
			RadioButton temporaryButton = (RadioButton) toggle;
			temporaryButton.setSelected(false);
		});
		managementRole.getToggles().forEach(toggle -> {
			RadioButton temporaryButton = (RadioButton) toggle;
			temporaryButton.setSelected(false);
		});
		dept.getToggles().forEach(toggle -> {
			RadioButton temporaryButton = (RadioButton) toggle;
			temporaryButton.setSelected(false);
		});

	}
	
	
	/**
	This void method is called when the Set Hours button is clicked and sets the hours for Part Time employees. The method
	checks if the employee type is part time and if not, displays the appropriate error message
	@param event This event represents the user clicking the Set Hours button 
	*/
	@FXML
	void setHoursCommand(ActionEvent event) {

		try {

			final int MAX_HOURS = 100;
			Profile profile = new Profile();
			profile = extractProfile();
			if (profile == null) {
				return;
			}

			if (company.getNumEmployee() == 0) { // if employee is found in the list the employee is removed
				messageArea1.appendText("Employee database is empty.\n");
				return;
			}
			if (hours.getText().isEmpty()) { //checks if the text field is empty
				messageArea1.appendText("Number of hours cannot be empty!\n");
				return;
			}
			int numHours = Integer.parseInt(hours.getText()); //converts the hours string to an integer 

			if (numHours < 0) { // checks validity of hours worked
				messageArea1.appendText("Working hours cannot be negative!\n");
				return;
			} else if (numHours > MAX_HOURS) { // checks validity of working hours
				messageArea1.appendText("Invalid Hours: over 100.\n");
				return;
			}

			Parttime setHourEmployee = new Parttime();
			setHourEmployee.setProfile(profile); 
			setHourEmployee.setNumHours(numHours); // set hours of the employee for which the hours is to be set
			company.setHours(setHourEmployee);
			messageArea1.appendText("Working hours set.\n");
			return;

		} catch (Exception e) {
			messageArea1.appendText("Invalid input!\n");
			return;
		}

	}
	
	
	/**
	This void method is called when the Calculate Payment button is clicked and calculates the payment for all the 
	employees in the database based on the hours, annual salary and employee type.
	@param event  This event represents the user clicking the Calculate Payment button 
	*/
	@FXML
	void calculateCommand(ActionEvent event) {

		messageArea2.clear(); //clears the message are 
		if (company.getNumEmployee() == 0) { //checks if the database is empty and prints an error message accordingly
			messageArea2.appendText("Employee database is empty.\n");
			return;
		}

		company.processPayments(); // calculates the payments of the employees in the current list
		messageArea2.appendText("Calculation of employee payments is done.\n");
		return;

	}


    /**
	This method displays all the employees in the database
	@param event This event represents the user clicking the Print Database button in the Print Menu
	*/
	@FXML
	void printCommand(ActionEvent event) {

		messageArea2.clear();
		if (company.getNumEmployee() == 0) { // checks if the database is empty
			messageArea2.appendText("Employee database is empty.\n");
			return;
		}

		messageArea2.appendText("--Printing earning statements for all employees--\n");
		for (int i = 0; i < company.getNumEmployee(); i++) { //iterates through the database
			messageArea2.appendText(company.print(i) + "\n"); //prints the database employee by employee
		}
	
	}


	/**
	This method displays all the employees in the database, ordered by the department they work in
	@param event This event represents the user clicking the Print by Department button in the Print Menu
	*/
	@FXML
	void printByDeptCommand(ActionEvent event) {

		messageArea2.clear();
		if (company.getNumEmployee() == 0) { // if employee is found in the list the it is removed
			messageArea2.appendText("Employee database is empty.\n");
			return;
		}

		messageArea2.appendText("--Printing earning statements by department--\n");
		company.printByDepartment(); //sorts the database by department
		for (int i = 0; i < company.getNumEmployee(); i++) { //iterates through the database
			messageArea2.appendText(company.print(i) + "\n"); //prints the database ordered by department
		}
		return;

	}

	/**
	This method displays all the employees in the database, ordered by the date they were hired
	@param event This event represents the user clicking the Print by Date button in the Print Menu
	*/
	@FXML
	void printByDateCommand(ActionEvent event) {

		messageArea2.clear();
		if (company.getNumEmployee() == 0) { // if employee is found in the list the it is removed
			messageArea2.appendText("Employee database is empty.\n");
			return;
		}

		messageArea2.appendText("--Printing earning statements by date hired--\n");
		company.printByDate(); //sorts the database by date hired
		for (int i = 0; i < company.getNumEmployee(); i++) { //iterates through the database
			messageArea2.appendText(company.print(i) + "\n"); //prints the database in order of date hired
		}
		return;

	}


    /**
	This helper method is called to populate the employee database with data imported from a file on the User's machine
	@param employeeInfo A string array with an employee's information imported from a file
	*/
	public void addEmployees(String[] employeeInfo) {

		/*
		Initializing the strings to the information from the parameter array
		*/
		String empType = employeeInfo[0];
		String name = employeeInfo[1];
		String department = employeeInfo[2];
		String dateHired = employeeInfo[3];
		String salary = employeeInfo[4];
		final int MANAGER_ARRAY_LENGTH = 6;

		int role = 0;
		if (employeeInfo.length == MANAGER_ARRAY_LENGTH) { //if the array is of length 6, the employee type is management
			role = Integer.parseInt(employeeInfo[5]); 
		}

		Profile profile = new Profile(); //creating a new profile to store the information of new employee
		profile.setName(name); //setting the name
		profile.setDept(department); //setting the department
		Date newDate = new Date(dateHired);
		profile.setDate(newDate); //setting the date hired

		if (empType.equals("P")) { // adding a parttime employee

			Parttime newPartTime = new Parttime();
			newPartTime.setSalary(Double.parseDouble(salary)); // sets the hourly salary of the employee

			newPartTime.setNumHours(0);
			newPartTime.setProfile(profile); // setting the profile of the employee

			company.add(newPartTime); // adding the new part time employee

		}

		else if (empType.equals("F")) {

			Fulltime newFullTime = new Fulltime(); // creating a new fulltime employee

			newFullTime.setSalary(Double.parseDouble(salary)); // setting the annual salary of the fulltime worker
			newFullTime.setProfile(profile);

			company.add(newFullTime); // adding new full time employee to the list

		}

		else if (empType.equals("M")) { 

			Management newManagement = new Management(); //creating new management employee

			newManagement.setSalary(Double.parseDouble(salary)); // setting the annual salary of the management worker
			newManagement.setManagementRole(role);
			newManagement.setCompensation();
			newManagement.setProfile(profile); // sets managers profile

			company.add(newManagement);

		}
		return;

	}


    /**
	This void method is called when the import button is clicked and opens the file browser for the user to choose a file to
	import the database from and calls the helper method to add the employees to the database 
	@param event  This event represents the user clicking the Export button 
	@throws FileNotFoundException If the file to import the database from is not found
	*/
	@FXML
	void importCommand(ActionEvent event) throws FileNotFoundException {

		messageArea2.clear();
		FileChooser chooser = new FileChooser(); //initiallizing file chooser
		chooser.setTitle("Open Source File for the Import");
		chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		Stage stage = new Stage();
		File sourceFile = chooser.showOpenDialog(stage); // get the reference of the source file
		

		try {

			Scanner sc = new Scanner(sourceFile);

			if (!sc.hasNext()) { //if file is empty, prints an error message

				messageArea2.appendText("File is empty!\n");
				sc.close();
				return;

			}
			while (sc.hasNext()) {

				String commandLine = sc.nextLine();
				String[] employeeInfo = commandLine.split(","); //populating an array for each employee with information from file
				addEmployees(employeeInfo); //calling helper method to add employee into database

			}
			messageArea2.appendText("Database imported!\n");
			sc.close();
			return;

		} catch (FileNotFoundException e) {
			messageArea2.appendText("File Not Found!\n");
			return;
		} catch (Exception e) {
			messageArea2.appendText("Import failed. Invalid file selection.");
			return;
		}

	}


	/**
	This void method is called when the option button is clicked and opens the file browser for the user to choose a file to
	export the employee database into. 
	@param event This event represents the user clicking the Export button 
	@throws FileNotFoundException If the file to export the database to is not found
	*/
	@FXML
	void exportCommand(ActionEvent event) throws FileNotFoundException {

		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open Target File for the Export");
		chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		Stage stage = new Stage();
		File targetFile = chooser.showSaveDialog(stage); // get the reference of the target file
		// write code to write to the file.

		/*
		Initializing the error messages 
		*/
		String error1 = "File must be empty!\n";
		String error2 = "Export failed. Invalid file selection.\n";
		String error3 = "File Not Found!\n";
		String success = "Payroll processing exported!\n";
		
		/*
		The if - else if block calls the export method from Company and populates the target text file.
		The export database returns status messages based of success or failure and the 
		appropriate messages are appended to the text area 
		*/
		
		if (company.exportDatabase(targetFile).equals(error1)) {
			messageArea2.appendText(error1);
		}
		else if (company.exportDatabase(targetFile).equals(error2)) {
			messageArea2.appendText(error2);
		}
		else if (company.exportDatabase(targetFile).equals(error3)) {
			messageArea2.appendText(error3);
		}
		else if(company.exportDatabase(targetFile).equals(success)) {
			messageArea2.appendText(success);
		}
		return;

	}

}
