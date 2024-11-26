package id.beecolony.routeanalyze.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.beecolony.routeanalyze.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    Optional<User> getByEmail(String email);

    Optional<User> getByEmailAndPass(String email, String pass);
}
