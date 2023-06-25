package com.example.todofamilyapi.events;

import org.springframework.context.ApplicationEvent;

public class InviteDeletedEvent extends ApplicationEvent {

    private final Long userId;
    private final String invitedCode;

    public InviteDeletedEvent(Object source, Long userId, String invitedCode) {
        super(source);
        this.userId = userId;
        this.invitedCode = invitedCode;
    }

    public Long getUserId() {
        return userId;
    }

    public String getInvitedCode() {
        return invitedCode;
    }
}
