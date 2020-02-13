package com.register.me.model.domain.repository;


public interface CacheRepository {

    void setUsername(String username);

    void setPassword(String password);

    String getUsername();

    String getPassword();

    boolean isSidebarShown();

    boolean isLoggedIn();

    void clear();
}
