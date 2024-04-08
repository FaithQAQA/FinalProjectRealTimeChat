package ca.sheridancollege.maynajal.beans;

public class CreateMessageRequest 
{
	 private String content;
	    private Long senderId;
	    private Long recipientId;
	    
	    

	    // Getters and setters
	    public String getContent() {
	        return content;
	    }

	    public void setContent(String content) {
	        this.content = content;
	    }

	    public Long getSenderId() {
	        return senderId;
	    }

	    public void setSenderId(Long senderId) {
	        this.senderId = senderId;
	    }

	    public Long getRecipientId() {
	        return recipientId;
	    }

	    public void setRecipientId(Long recipientId) {
	        this.recipientId = recipientId;
	    }
	    
	    
	    private Long roomId;

	    // Getter method for roomId
	    public Long getRoomId() {
	        return roomId;
	    }

	    // Setter method for roomId
	    public void setRoomId(Long roomId) {
	        this.roomId = roomId;
	    }
}
