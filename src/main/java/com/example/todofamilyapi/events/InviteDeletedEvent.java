package com.example.todofamilyapi.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class InviteDeletedEvent extends ApplicationEvent {

    private final Long userId;
    private final String invitedCode;

    public InviteDeletedEvent(Object source, Long userId, String invitedCode) {
        super(source);
        this.userId = userId;
        this.invitedCode = invitedCode;
    }
}
