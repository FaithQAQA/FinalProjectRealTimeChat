package ca.sheridancollege.maynajal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.maynajal.beans.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

     
    Optional<Users> findByUsername(String username);
    
    Users findBySessions_Id(Long sessionId);    
    public List<Users> findByUsernameContaining(String usernamePart);
}
