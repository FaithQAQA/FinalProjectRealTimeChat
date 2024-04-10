package ca.sheridancollege.maynajal.beans;

public class CreateMessageRequest 
{
	 private String content;
	    private Long senderId;
	    private Long recipientId;
	    
	    

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

	    public Long getRoomId() {
	        return roomId;
	    }

	    public void setRoomId(Long roomId) {
	        this.roomId = roomId;
	    }
}
