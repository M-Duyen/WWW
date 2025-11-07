package iuh.fit.on_thck_bai02.repositories;

import iuh.fit.on_thck_bai02.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findCourseByClassNameContainingIgnoreCase(String name);

    List<Course> findCourseByStartDate(LocalDate date);

    List<Course> findCourseByEndDate(LocalDate date);

    List<Course> findCourseByStartDateAndEndDate(LocalDate startDate, LocalDate endDate);

}