

package application;

import java.util.Calendar;

/**
The Date class creates date objects with the day, month and year, and it checks if the date is valid with the isValid method.
There are helper methods to check if it is leap year and to compare dates, and a testbed main method to test the isValid method.
@author Bhumit Patel
@author Soorya Srivatsa
*/
public class Date implements Comparable<Date> {

	private int year; // integer to stores the year
	private int month; // integer to stores the month
	private int day; // integer to stores the day

	/**
	 * This is the testbed main method used to test the isValid method. The 15 test
	 * cases are from the test document. The method prints "Date is valid." for a
	 * valid date and "Date is invalid." for an invalid date.
	 * 
	 * @param args is the user input arguments array from the console
	 */
	public static void main(String args[]) {

		
		String[] tests = { "1/1/1899", "1/1/1900", "1/1/2022", "1/1/2021", "5/32/1999", "5/31/1999", "4/31/1999",
				"4/30/1999", "13/1/1999", "0/1/1999", "12/1/1999", "2/30/2000", "2/29/2000", "2/29/2013", "2/28/2013" }; // 15 test cases
		boolean [] results = new boolean[tests.length];

		for (int i = 0; i < tests.length; i++) { // iterate through the test cases
			Date testDate = new Date(tests[i]);
			results[i] = testDate.isValid();
		}

	}

	/**
	 * This is a parameterized constructor that initializes a new Date object. The
	 * method splits a date string into its respective variables (month, day, year)
	 * 
	 * @param date The date that was extracted from the command line
	 */
	public Date(String date) {

		String[] dateArray = date.split("/"); // Populates an empty array by separating the date into day, month year
												// separated by "/"
		month = Integer.parseInt(dateArray[0]); // first element of array contains month
		day = Integer.parseInt(dateArray[1]); //// second element of array contains day
		year = Integer.parseInt(dateArray[2]); // last element of array contains year

	}

	/**
	 * This is a default constructor that initializes a Date object with todays date
	 * as its values This method used the calendar class to find today's date
	 */
	public Date() {

		Calendar calendar = Calendar.getInstance();
		String todaysDate = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DATE) + "/"
				+ calendar.get(Calendar.YEAR); // using calendar class to set today's date
		String[] todayArray = todaysDate.split("/");
		month = Integer.parseInt(todayArray[0]); // converting the string into integers
		day = Integer.parseInt(todayArray[1]); // converting the string into integers
		year = Integer.parseInt(todayArray[2]); // converting the string into integers

	}

	/**
	 * This method is used to check if a user entered date is valid or not. First
	 * the method checks if the days and months don't exceed their limit for the
	 * specific months or years (Leap years) The method also checks if the date
	 * entered is a future date in which case the date is invalid
	 * 
	 * @return true if the given date is valid and false if not
	 */
	public boolean isValid() {

		Date today = new Date();
		final int MIN_YEAR = 1900;
		final int MAX_DAY_JANUARY = 31;
		final int MAX_DAY_APRIL = 30;
		final int MAX_DAY_FEBRUARY = 28;
		

		if ((today.year < year)) {
			return false;
		}
		if ((today.year == year) && (today.month < month)) { // comparing the date with today's date
			return false;
		}
		if ((today.year == year) && (today.month == month) && (today.day < day)) { // comparing the date with today's
																					// date
			return false;
		}
		if (year < MIN_YEAR || month > Calendar.DECEMBER + 1 || month < Calendar.JANUARY + 1) { // making sure the month
																								// and year is within
																								// required range
			return false;
		}
		if ((month == Calendar.JANUARY + 1 || month == Calendar.MARCH + 1 || month == Calendar.MAY + 1
				|| month == Calendar.JULY + 1 || month == Calendar.AUGUST + 1 || month == Calendar.OCTOBER + 1
				|| month == Calendar.DECEMBER + 1) && day > MAX_DAY_JANUARY) {
			return false; // returns false if date has more days than allowed
		} else if ((month == Calendar.APRIL + 1 || month == Calendar.JUNE + 1 || month == Calendar.SEPTEMBER + 1
				|| month == Calendar.NOVEMBER + 1) && day > MAX_DAY_APRIL) {
			return false; // returns false if date has more days than allowed
		} else if ((leap(year) == false && day > MAX_DAY_FEBRUARY && month == Calendar.FEBRUARY + 1)
				|| (leap(year) == true && day > MAX_DAY_FEBRUARY + 1 && month == Calendar.FEBRUARY + 1)) {
			return false; // Allows constraint for leap year so that 29th Feb is a valid date on leap
							// years
		}
		return true; // if all conditions are passed, the date is valid and true is returned

	}

	/**
	 * This method checks if a given year is a leap year or not
	 * 
	 * @param year The year integer that is to be checked
	 * @return true if leap year, false otherwise
	 */
	public boolean leap(int year) {
		final int DIVIDER_1 = 4;
		final int DIVIDER_2 = 100;
		final int DIVIDER_3 = 400;
		boolean leapYear = false;
		if (year % DIVIDER_1 == 0) { // executing algorithm to determine if leap year
			if (year % DIVIDER_2 == 0) {
				if (year % DIVIDER_3 == 0) {
					leapYear = true;
				} else {
					leapYear = false;
				}
			} else {
				leapYear = true;
			}
		} else {
			leapYear = false;
		}
		return leapYear;

	}

	/**
	 * This method returns a Date object in the form of a string
	 * 
	 * @return a string in a date format (mm/dd/yyyy)
	 */
	public String dateToString() {
		return month + "/" + day + "/" + year;
	}

	/**
	 * This method compares two dates and determines which one is the earlier date
	 * 
	 * @param date The date that is being compared to
	 * @return 1 if dates are the same, 0 if date is larger and -1 if date is
	 *         smaller than input
	 */
	@Override
	public int compareTo(Date date) {

		if (year == date.year && month == date.month && day == date.day) {
			return 0; // return 0 if dates are the same
		}
		if (year > date.year) {
			return 1;
		}
		if ((year == date.year) && (month > date.month)) {
			return 1;
		}
		if ((year == date.year) && (month == date.month) && (day > date.day)) {
			return 1;
		}
		return -1;

	}

}