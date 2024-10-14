package com.tavodin.dscommerce.services;

import com.tavodin.dscommerce.dto.UserDTO;
import com.tavodin.dscommerce.entities.User;
import com.tavodin.dscommerce.projections.UserDetailsProjection;
import com.tavodin.dscommerce.repositories.UserRepository;
import com.tavodin.dscommerce.tests.UserDetailsFactory;
import com.tavodin.dscommerce.tests.UserFactory;
import com.tavodin.dscommerce.util.CustomUserUtil;
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
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private CustomUserUtil userUtil;

    private String existingUsername, nonExistingUsername;
    private User user;
    private List<UserDetailsProjection> userDetails;

    @BeforeEach
    void setUp() throws Exception {
        existingUsername = "maria@gmail.com";
        nonExistingUsername = "user@gmail.com";
        user = UserFactory.createdCustomClientUser(1L, existingUsername);
        userDetails = UserDetailsFactory.createCustomClientUser(existingUsername);

        Mockito.when(repository.searchUserAndRolesByEmail(existingUsername)).thenReturn(userDetails);
        Mockito.when(repository.searchUserAndRolesByEmail(nonExistingUsername)).thenReturn(List.of());

        Mockito.when(repository.findByEmail(existingUsername)).thenReturn(Optional.of(user));
        Mockito.when(repository.findByEmail(nonExistingUsername)).thenReturn(Optional.empty());
    }

    @Test
    public void loadUserByUsernameShouldReturnListOfUserDetailsWhenUserExist() {
        UserDetails result = service.loadUserByUsername(existingUsername);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getUsername(), existingUsername);
    }

    @Test
    public void loadUserByUsernameShouldReturnUsernameNotFoundExceptionWhenUserDoesNotExist() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername(nonExistingUsername);
        });
    }

    @Test
    public void authenticatedShouldReturnUserWhenUserExists() {
        Mockito.when(userUtil.getLoggedUserName()).thenReturn(existingUsername);

        User result = service.authenticated();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getName(), existingUsername);
    }

    @Test
    public void authenticatedShouldReturnUsernameNotFoundExceptionWhenUserDoesNotExist() {
        Mockito.doThrow(ClassCastException.class).when(userUtil).getLoggedUserName();

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            service.authenticated();
        });
    }

    @Test
    public void getMeShouldReturnUserDTOWHenUserAuthenticated() {
        UserService spyUserService = Mockito.spy(service);
        Mockito.doReturn(user).when(spyUserService).authenticated();
        //Mockito.doReturn(user).when(service).authenticated();

        UserDTO result = spyUserService.getMe();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getEmail(), existingUsername);
    }

    @Test
    public void getMeShouldReturnUsernameNotFoundExceptionWhenUserNotAuthenticated() {
        UserService spyUserService = Mockito.spy(service);
        Mockito.doThrow(UsernameNotFoundException.class).when(spyUserService).authenticated();

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            UserDTO result = spyUserService.getMe();
        });
    }
}
