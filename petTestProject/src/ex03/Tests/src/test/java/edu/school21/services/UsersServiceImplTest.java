package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsersServiceImplTest {

    @Mock
    private UsersRepository usersRepository;

    private UsersServiceImpl usersService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        usersService = new UsersServiceImpl(usersRepository);
    }

    @Test
    public void testAuthenticateCorrectLoginPassword() throws AlreadyAuthenticatedException, EntityNotFoundException {
        User user = new User(1L, "testUser", "password");

        when(usersRepository.findByLogin("testUser")).thenReturn(user);

        boolean result = usersService.authenticate("testUser", "password");

        assertTrue(result);
        assertTrue(user.isAuthenticated());
        verify(usersRepository, times(1)).update(user);
    }

    @Test
    public void testAuthenticateIncorrectLogin() {
        when(usersRepository.findByLogin("nonExistentUser")).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> {
            usersService.authenticate("nonExistentUser", "password");
        });

        verify(usersRepository, never()).update(any());
    }

    @Test
    public void testAuthenticateIncorrectPassword() throws AlreadyAuthenticatedException, EntityNotFoundException {
        User user = new User(1L, "testUser", "password");

        when(usersRepository.findByLogin("testUser")).thenReturn(user);

        boolean result = usersService.authenticate("testUser", "wrongPassword");

        assertFalse(result);
        verify(usersRepository, never()).update(any());
    }
}
