package org.stockws.service;

import java.util.concurrent.CopyOnWriteArrayList;

import org.business.models.User;

import com.google.common.base.Optional;

public interface UserOpsService {

	public User findByUsername(String username);

	public void saveUser(User user);

	public CopyOnWriteArrayList<User> findByUsernameContaining(String term);

	public Optional<User> checkUser(String username, String password);

	public void saveUser(String username, String password, String remoteHost);

}