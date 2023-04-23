package com.date.tingting.domain.user;

import com.date.tingting.web.responseDto.PartyUserInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT A.userId AS userId, IFNULL(B.isAccepted, '1') AS invitationState FROM User AS A LEFT JOIN PartyGroup AS B ON A.userId = B.guest WHERE A.userId != :userId OR A.isDel != :isDel", nativeQuery = true)
    List<PartyUserInterface> getPartyUserList(@Param("userId") String userId, @Param("isDel") String isDel);

    User findByUuid(String uuid);
    Optional<User> findByUserId(String userId);
    Optional<User> findByUserEmail(String userEmail);
    Optional<User> findByUserEmailAndIsActive(String userEmail, String isActive);
    List<User> findAll();

    void deleteByUserId(String userId);
    void deleteByUserEmail(String userId);

    boolean existsByUserIdAndIsActive(String userId, String isActive);
    boolean existsByUserId(String userId);
    boolean existsByUserEmail(String userEmail);

}
