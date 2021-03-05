package edu.uark.registerapp.commands.activeUsers;

import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import edu.uark.registerapp.models.entities.EmployeeEntity;

@Service
public class ActiveUserDeleteCommand implements VoidCommandInterface {

	@Transactional
	@Override
	public void execute() {
		final Optional<ActiveUserEntity> activeUserEntity =
			this.activeUserRepository.findBySessionKey(this.sessionKey);
        
		if (!activeUserEntity.isPresent()) {
			throw new NotFoundException("ActiveUser");
		}

        else {
            ActiveUserEntity validate = activeUserEntity.get();
            Optional<EmployeeEntity> validateName = this.employeeRepository.findById((validate.getEmployeeId()));
            if(!validateName.isPresent()) {
                throw new NotFoundException("Employee");
            }
            else if(StringUtils.isBlank(validateName.get().getFirstName()) || StringUtils.isBlank(validateName.get().getLastName())) {
                throw new UnprocessableEntityException("empty first/last name");
            }
        }

        this.activeUserRepository.delete(activeUserEntity.get());
	}

	// Properties
	private String sessionKey;

	public String getSessionKey() {
		return this.sessionKey;
	}

	public ActiveUserDeleteCommand setSessionKey(final String sessionKey) {
		this.sessionKey = sessionKey;
		return this;
	}

	@Autowired
	private ActiveUserRepository activeUserRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
}
