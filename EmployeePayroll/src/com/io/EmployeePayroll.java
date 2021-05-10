package com.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayroll {
	private List<EmployeePayrollData> empPayrollList;

	public EmployeePayroll() {
	}

	public EmployeePayroll(List<EmployeePayrollData> data) {
		this.empPayrollList = data;
	}

	public static void main(String[] args) {
		System.out.println("Java IO - Employee Payroll Service");
		
		List<EmployeePayrollData> datas = new ArrayList<EmployeePayrollData>();
		EmployeePayroll service = new EmployeePayroll(datas);
		service.readData(new Scanner(System.in));

		service.writeData();
	}

	public void readData(Scanner sc) {
		System.out.println("Enter ID");
		int id = sc.nextInt();

		System.out.println("Enter Salary");
		int sal = sc.nextInt();

		System.out.println("Enter Name");
		String name = sc.next();

		empPayrollList.add(new EmployeePayrollData(id, sal, name));
	}

	public void writeData() {
		System.out.println(empPayrollList);
	}
}

class EmployeePayrollData {
	private int id;
	private int salary;
	private String name;

	public EmployeePayrollData(int id, int salary, String name) {
		this.id = id;
		this.salary = salary;
		this.name = name;
	}

	@Override
	public String toString() {
		return "EmployeePayrollData [id=" + id + ", salary=" + salary + ", name=" + name + "]";
	}
}