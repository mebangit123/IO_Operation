package com.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class EmployeeFileServiceIO {
	public static String PAYROLL_FILENAME = "payroll-file.txt";
	public static void writeDatas(List<EmployeePayrollData> employeePayrollList) {
		StringBuffer empBuffer = new StringBuffer();
		employeePayrollList.forEach(employee -> {
			String employeeDataString = employee.toString().concat("\n");
			empBuffer.append(employeeDataString);
		});
		try {
			Files.write(Paths.get(PAYROLL_FILENAME), empBuffer.toString().getBytes());		
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void printData() {
		try {
			Files.lines(new File(PAYROLL_FILENAME).toPath())
				.forEach(System.out::println);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public long countEntries() {
		long entries = 0;
		try {
			entries = Files.lines(new File(PAYROLL_FILENAME).toPath()).count();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return entries;
	}
}
