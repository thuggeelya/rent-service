package com.thuggeelya.rent.service;

import com.thuggeelya.rent.model.AppUser;

import java.util.List;

public interface UserService {
    void save(AppUser user);

    List<AppUser> findAllUsers();
}
