package ca.sheridancollege.maynajal.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor

public class Users {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String username;
	    private String password;
	    private Boolean loggedIn = false; // Corrected field name to "loggedIn"	
	    private String ImgUrl ="https://t4.ftcdn.net/jpg/00/64/67/63/360_F_64676383_LdbmhiNM6Ypzb3FM4PPuFP9rHe7ri8Ju.jpg";
	    private String bio;
	   
	    @JsonIgnore
	    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	    private List<Session> sessions;
}
