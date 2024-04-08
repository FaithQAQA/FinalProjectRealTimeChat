package ca.sheridancollege.maynajal.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Message {

	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String content;

	    @JsonIgnore
	    @ManyToOne
	    private Room room;
	    @JsonIgnore
	    @ManyToOne
	    private Users sender;
	    @JsonIgnore
	    @ManyToOne
	    private Users recipient;
	    
	    private String senderUsername;
	    private String recipientUsername;
	    
	    
	    @Override
	    public String toString() {
	        return "From: " + senderUsername + ", Content: '" + content + "'";
	    }

}
