package ca.sheridancollege.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sheridancollege.maynajal.beans.Room;
import ca.sheridancollege.maynajal.beans.Users;
import ca.sheridancollege.maynajal.repository.RoomRepository;
import ca.sheridancollege.maynajal.repository.UserRepository;


@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UserRepository usersRepository;
	
	  @Autowired
	    private RoomRepository roomRepository;

	@Override
	public List<Users> findAll() {
		// TODO Auto-generated method stub
		return usersRepository.findAll();
	}

	@Override
	public Optional<Users> findById(Long id) {
		// TODO Auto-generated method stub
		return usersRepository.findById(id);
	}

	

	@Override
	public Users save(Users student) {
		// TODO Auto-generated method stub
		return usersRepository.save(student);
	}

	@Override
	public List<Users> findByUsernameContaining(String usernamePart) {
		// TODO Auto-generated method stub
		return usersRepository.findByUsernameContaining(usernamePart);
	}

	@Override
	public Optional<Users> findByUsername(String username) {
		// TODO Auto-generated method stub
		return usersRepository.findByUsername(username);
	}

	@Override
	public Optional<Users> loginByUsername(String username) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Users findBySessionId(Long id) {
		// TODO Auto-generated method stub
		return usersRepository.findBySessions_Id(id);
	}



	


}
