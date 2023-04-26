package com.example.springgumball.login;

import java.util.List;

public interface UserService {
    public void saveUser(User user);
    public List<Object> isUserPresent(User user);
}