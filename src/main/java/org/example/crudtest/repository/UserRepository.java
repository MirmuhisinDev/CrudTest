package org.example.crudtest.repository;

import org.example.crudtest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true, value = "select * from users where full_name ilike '%' || :fullName || '%'")

    List<User> searchByFullName(String fullName);

    Optional<User> findByActivationCode(Integer activationCode);

    List<User> findAllByEnabledFalseAndActivationCodeIsNotNull();
    //
    //

    //
    //
    //

}
