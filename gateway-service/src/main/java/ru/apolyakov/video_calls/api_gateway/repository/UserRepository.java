package ru.apolyakov.video_calls.api_gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.apolyakov.video_calls.api_gateway.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(
            value = "SELECT * FROM users u WHERE u.login = :login",
            nativeQuery = true)
    Optional<User> findByLogin(@Param("login") String login);

    List<Optional<User>> findAllByLoginLikeOrFirstNameLikeOrSecondNameIsLike(String loginLike, String firstNameLike, String SecondNameLike);

    @Query(
            value = "select * from users where first_name LIKE :firstName AND second_name LIKE :secondName",
            nativeQuery = true)
    List<Optional<User>> findUserLikeFirstNameAndLikeSecondName(@Param("firstName") String firstName,
                                                                @Param("secondName") String secondName);


    Optional<User> findById(Long id);
}
