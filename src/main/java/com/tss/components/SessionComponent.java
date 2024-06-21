package com.tss.components;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * It's a Spring-managed component that is scoped to the user's session
 * The class is primarily used to store and manage a counter that tracks the number of database queries within a session.
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionComponent {
    private int counter=0;
    
    public int getCounter() {
        return counter;
    }
    
    public void setCounter(int counter) {
        this.counter = counter;
    }
    
    public void increaseCounter() {
        counter++;
    } 
}
