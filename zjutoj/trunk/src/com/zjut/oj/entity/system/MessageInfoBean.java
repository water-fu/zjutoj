package com.zjut.oj.entity.system;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.zjut.oj.common.BaseEntity;

public class MessageInfoBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int messageId ;

    private int messageType ;

    private java.lang.String messageTitle ;

    private java.lang.String messageContent ;
    
    private int viewCount;
    
    private Date createDate;
    
    private Set<MessageAttachBean> messageAttchs;
    
    private String deleteAttachIds;

    public void setMessageId(int messageId){
        this.messageId = messageId ;
    }

    public int getMessageId(){
        return this.messageId ;
    }

    public void setMessageType(int messageType){
        this.messageType = messageType ;
    }

    public int getMessageType(){
        return this.messageType ;
    }

    public void setMessageTitle(java.lang.String messageTitle){
        this.messageTitle = messageTitle ;
    }

    public java.lang.String getMessageTitle(){
        return this.messageTitle ;
    }

    public void setMessageContent(java.lang.String messageContent){
        this.messageContent = messageContent ;
    }

    public java.lang.String getMessageContent(){
        return this.messageContent ;
    }

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Set<MessageAttachBean> getMessageAttchs() {
		return messageAttchs;
	}

	public void setMessageAttchs(Set<MessageAttachBean> messageAttchs) {
		this.messageAttchs = messageAttchs;
	}

	public String getDeleteAttachIds() {
		return deleteAttachIds;
	}

	public void setDeleteAttachIds(String deleteAttachIds) {
		this.deleteAttachIds = deleteAttachIds;
	}
}