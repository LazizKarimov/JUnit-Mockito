package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

import java.util.Objects;

public class UsersServiceImpl {

    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean authenticate(String login, String password) throws AlreadyAuthenticatedException, EntityNotFoundException {
        User user = usersRepository.findByLogin(login);

        if (user == null) {
            throw new EntityNotFoundException("User is not found.");
        }

        if (user.isAuthenticated()) {
            throw new AlreadyAuthenticatedException("User is already authenticated.");
        }

        if (Objects.equals(user.getPassword(), password)) {
            user.setAuthenticated(true);
            usersRepository.update(user);
            return true;
        }

        return false;
    }
}
