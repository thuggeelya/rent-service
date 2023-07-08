package com.thuggeelya.rent.service.impl;

import com.thuggeelya.rent.model.AppUser;
import com.thuggeelya.rent.repository.UserRepository;
import com.thuggeelya.rent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(AppUser user) {
        repository.saveAndFlush(user);
    }

    @CachePut(value = "users")
    @Override
    public List<AppUser> findAllUsers() {
        return repository.findAll();
    }
}
