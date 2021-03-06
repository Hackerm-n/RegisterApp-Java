package edu.uark.registerapp.commands.employees.helpers;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;

public class EmployeeHelper {
	public static String padEmployeeId(final int employeeId) {
		final String employeeIdAsString = Integer.toString(employeeId);

		return ((employeeIdAsString.length() < EMPLOYEE_ID_MAXIMUM_LENGTH)
				? StringUtils.leftPad(employeeIdAsString, EMPLOYEE_ID_MAXIMUM_LENGTH, "0")
				: employeeIdAsString);
	}

	public static byte[] hashPassword(final String password)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		return new byte[0];
	}

	private static final int EMPLOYEE_ID_MAXIMUM_LENGTH = 5;
}
