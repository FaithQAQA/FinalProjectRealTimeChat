package ca.sheridancollege.maynajal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.maynajal.beans.Message;
import ca.sheridancollege.maynajal.beans.Session;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
