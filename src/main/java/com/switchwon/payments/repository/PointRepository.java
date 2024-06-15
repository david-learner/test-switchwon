package com.switchwon.payments.repository;

import com.switchwon.payments.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    Point findPointByUserId(Long userId);
}
