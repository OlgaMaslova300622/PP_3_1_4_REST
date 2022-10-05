package ru.kata.spring.boot_security.demo.dao;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

    @Query("select distinct u from User u left join fetch u.roles")
    List<User> getAllUsers();
    @Query("select u from User u join fetch u.roles where u.id = :id")
    User getUserById(int id);

    @Query("select u from User u join fetch u.roles where u.login = :login")
    public User getUserByLogin(String login);


    public void deleteById(int id);

}


