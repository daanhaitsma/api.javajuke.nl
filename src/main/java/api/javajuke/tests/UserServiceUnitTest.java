package api.javajuke.tests;

import api.javajuke.data.UserRepository;
import api.javajuke.data.model.User;
import api.javajuke.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.Assert;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetUser() {
        User dummyUser = new User("niels-schutte@hotmail.com", "FakaDushi", "Gaat je niks aan");
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(dummyUser));

        User user = userService.getUser(1);

        Assert.assertEquals(user.getUsername(), "FakaDushi");
    }
}