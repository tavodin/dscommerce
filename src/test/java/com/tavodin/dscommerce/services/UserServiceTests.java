package com.tavodin.dscommerce.services;

import com.tavodin.dscommerce.entities.User;
import com.tavodin.dscommerce.projections.UserDetailsProjection;
import com.tavodin.dscommerce.repositories.UserRepository;
import com.tavodin.dscommerce.tests.UserDetailsFactory;
import com.tavodin.dscommerce.tests.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    private String existingUsername, nonExistingUsername;
    private User user;
    private List<UserDetailsProjection> userDetails;

    @BeforeEach
    void setUp() throws Exception {
        existingUsername = "ana@gmail.com";
        nonExistingUsername = "user@gmail.com";
        user = UserFactory.createdCustomClientUser(1L, existingUsername);
        userDetails = UserDetailsFactory.createCustomClientUser(existingUsername);

        Mockito.when(repository.searchUserAndRolesByEmail(existingUsername)).thenReturn(userDetails);
        Mockito.when(repository.searchUserAndRolesByEmail(nonExistingUsername)).thenReturn(List.of());
    }

    @Test
    public void loadUserByUsernameShouldReturnListOfUserDetailsWhenUserExisting() {
        UserDetails result = service.loadUserByUsername(existingUsername);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getUsername(), existingUsername);
    }

    @Test
    public void loadUserByUsernameShouldReturnUsernameNotFoundExceptionWhenUserDoesNotExinting() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            UserDetails result = service.loadUserByUsername(nonExistingUsername);
        });
    }
}
