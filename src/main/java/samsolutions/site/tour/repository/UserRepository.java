package samsolutions.site.tour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import samsolutions.site.tour.entities.UserData;

public interface UserRepository extends JpaRepository<UserData, Long> {
    public UserData findByUsername(String username);
}
