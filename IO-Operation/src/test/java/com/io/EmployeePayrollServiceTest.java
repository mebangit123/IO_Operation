package com.io;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.io.EmployeePayrollService.IOService;

public class EmployeePayrollServiceTest {
	@Test
	public void given3EmployeeWhenWrittenToFileShouldMatchEmployeeEntries() {
		EmployeePayrollData[] arrayOfEmps = {
				new EmployeePayrollData(1, 100000,  "Jeff Bezos"),
				new EmployeePayrollData(1, 200000, "Bill Gates"),
				new EmployeePayrollData(1, 300000, "Mark Zuckerberg")
		};
		EmployeePayrollService employeePayrollService;
		employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
		employeePayrollService.writeData(IOService.FILE_IO);
		employeePayrollService.printData(IOService.FILE_IO);
		long entries = employeePayrollService.countEntries(IOService.FILE_IO);
		Assert.assertEquals(3, entries);
	}
}
