package com.thuggeelya.rent.service.impl;

import com.thuggeelya.rent.dto.AppUserDTO;
import com.thuggeelya.rent.model.AppUser;
import com.thuggeelya.rent.repository.UserRepository;
import com.thuggeelya.rent.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public AppUser save(AppUser user) {
        return repository.saveAndFlush(user);
    }

    @Override
    @CachePut(value = "users")
    public List<AppUser> findAllUsers() {
        return repository.findAll();
    }

    @Override
    @CachePut(value = "user")
    public AppUser findUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Error: User is not found."));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public AppUser updateUserById(Long id, AppUserDTO userDTO) {
        AppUser user = findUserById(id);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(userDTO, user);
        return repository.saveAndFlush(user);
    }
}
