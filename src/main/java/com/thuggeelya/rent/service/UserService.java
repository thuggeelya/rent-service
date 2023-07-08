package com.thuggeelya.rent.service;

import com.thuggeelya.rent.dto.AppUserDTO;
import com.thuggeelya.rent.model.AppUser;

import java.util.List;

public interface UserService {
    AppUser save(AppUser user);

    List<AppUser> findAllUsers();

    AppUser findUserById(Long id);

    AppUser updateUserById(Long id, AppUserDTO userDTO);
}
