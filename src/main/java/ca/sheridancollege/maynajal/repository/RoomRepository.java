package ca.sheridancollege.maynajal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.maynajal.beans.Room;
import ca.sheridancollege.maynajal.beans.Users;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>  {

    List<Room> findByUsersContaining(Users user);
}
