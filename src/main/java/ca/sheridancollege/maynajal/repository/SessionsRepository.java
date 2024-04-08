package ca.sheridancollege.maynajal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.maynajal.beans.Session;
import ca.sheridancollege.maynajal.beans.Users;

@Repository
public interface SessionsRepository extends JpaRepository<Session, Long> 
{
    Optional<Session> findByUser(Users user);
    
}
