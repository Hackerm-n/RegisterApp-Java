package edu.uark.registerapp.commands.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Service
public class ActiveEmployeeExistsQuery implements VoidCommandInterface {
	@Override
	public void execute() {
		final boolean employeeEntity =
			this.employeeRepository.existsByIsActive(true);
		if (!employeeEntity) {
			throw new NotFoundException("Employee");
	    }
    }

    public long employeeCount() {
		return employeeRepository.count();
	}

    @Autowired
    private EmployeeRepository employeeRepository;
}
