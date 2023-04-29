package ru.skillbox.zerone.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.zerone.backend.model.entity.Friendship;
import ru.skillbox.zerone.backend.model.entity.User;
import ru.skillbox.zerone.backend.model.enumerated.FriendshipStatus;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
  Optional<Friendship> findBySrcPersonAndDstPerson(User srcUser, User dstUser);

  Page<Friendship> findAllBySrcPersonAndStatus(User srcUser, FriendshipStatus status, Pageable pageable);

  @Query("""
      SELECT f FROM Friendship f
      WHERE f.srcPerson = :srcUser AND f.status = :status
      AND (LOWER(f.dstPerson.firstName) LIKE LOWER(CONCAT('%', :name, '%'))
      OR LOWER(f.dstPerson.lastName) LIKE LOWER(CONCAT('%', :name, '%')))
      """)
  Page<Friendship> findAllBySrcPersonAndStatusAndDstPersonNameLike(
      User srcUser, FriendshipStatus status, String name, Pageable pageable);

  List<Friendship> findAllBySrcPersonAndDstPersonIdIn(User user, List<Long> userIds);

  List<Friendship> findAllBySrcPersonAndStatus(User srcUser, FriendshipStatus status);

  boolean existsBySrcPersonAndDstPersonAndStatus(User srcUser, User dstUser, FriendshipStatus status);
}
