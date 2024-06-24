package dev.jacob.TrialTask.storage.repository;

import dev.jacob.TrialTask.storage.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {

    Point findByName(String name);

}
