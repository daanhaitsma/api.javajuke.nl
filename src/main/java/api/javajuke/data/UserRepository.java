package api.javajuke.data;

import api.javajuke.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Queries for a user with the specified username of email.
     *
     * @param username the username to search for
     * @param email the email to search for
     * @return an optional user object that contains an user if found
     */
    Optional<User> findByUsernameOrEmail(String username, String email);

    /**
     * Queries for a user with the specified token.
     *
     * @param token the token to search for
     * @return an optional user object that contains an user if found
     */
    Optional<User> findByToken(String token);
}

    