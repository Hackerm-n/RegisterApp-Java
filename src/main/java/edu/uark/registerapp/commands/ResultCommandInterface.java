package edu.uark.registerapp.commands;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface ResultCommandInterface<T> {
	T execute() throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
