package me.ham.circuitbreaker.repository;

import me.ham.circuitbreaker.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResilienceRepository extends JpaRepository<Status, Long> {
}
