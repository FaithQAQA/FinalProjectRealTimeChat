package ca.sheridancollege.services;

import java.util.List;
import java.util.Optional;

import ca.sheridancollege.maynajal.beans.Room;
import ca.sheridancollege.maynajal.beans.Users;


public interface UsersService {

	List<Users> findAll();
//	List<Users> findByUsernameContaining(String username);
    Optional<Users> findById(Long id);
    Optional<Users> findByUsername(String username);
    Users save(Users student);
    List<Users> findByUsernameContaining(String usernamePart);
    Optional<Users> loginByUsername(String username); // New method to handle login
    Users findBySessionId(Long id);
}
