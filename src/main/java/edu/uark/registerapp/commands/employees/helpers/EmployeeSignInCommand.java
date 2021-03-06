package edu.uark.registerapp.commands.employees.helpers;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays; 

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.*;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.*;
import edu.uark.registerapp.models.repositories.*;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.commands.employees.helpers.EmployeeHelper;


@Service
public class EmployeeSignInCommand implements VoidCommandInterface {
	@Transactional
	@Override
	public void execute() {
		this.validateProperties();

		final Optional<EmployeeEntity> employeeEntity =
			this.employeeRepository.findByEmployeeId(Integer.parseInt(this.employeeSignIn.getEmployeeId()));
		if (!employeeEntity.isPresent()) { // No record with the associated record ID exists in the database.
			throw new NotFoundException("Employee");
		}

        try {
			if(!(Arrays.equals(employeeSignIn.getPassword().getBytes(StandardCharsets.UTF_8), employeeEntity.get().getPassword()))) {
				throw new UnauthorizedException();
			}
			else {
			    Optional<ActiveUserEntity> activeUserEntity = this.activeUserRepository.findByEmployeeId(employeeEntity.get().getId());
                if (activeUserEntity.isPresent()) {
                    activeUserEntity.get().setSessionKey(this.sessionKey);
                    this.activeUserRepository.save(activeUserEntity.get());
                }
                else {
                    ActiveUserEntity save = new ActiveUserEntity();
                    save.setSessionKey(this.sessionKey);
                    save.setClassification(employeeEntity.get().getClassification());
                    save.setName(employeeEntity.get().getFirstName() + " " + employeeEntity.get().getLastName());
                    save.setEmployeeId(employeeEntity.get().getId());
                    this.activeUserRepository.save(save);
                }
			}
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

	// Helper methods
	private void validateProperties() {
		if (StringUtils.isBlank(this.employeeSignIn.getPassword()) || !StringUtils.isNumeric(this.employeeSignIn.getEmployeeId()) || StringUtils.isBlank(this.employeeSignIn.getEmployeeId())) {
			throw new UnprocessableEntityException("bad credentials");
		}
	}

	// Properties
	private String sessionKey;
	public String getSessionKey() {
		return this.sessionKey;
	}
	public EmployeeSignInCommand setSessionKey(final String sessionKey) {
		this.sessionKey = sessionKey;
		return this;
	}

	private EmployeeSignIn employeeSignIn;
	public EmployeeSignIn getEmployeeSignIn() {
		return this.employeeSignIn;
	}
	public EmployeeSignInCommand setEmployeeSignIn(final EmployeeSignIn employeeSignIn) {
		this.employeeSignIn = employeeSignIn;
		return this;
	}
	
	@Autowired
	private EmployeeRepository employeeRepository;

    @Autowired
    private ActiveUserRepository activeUserRepository;
}