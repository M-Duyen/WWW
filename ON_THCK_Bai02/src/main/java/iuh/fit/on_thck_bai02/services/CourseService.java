package iuh.fit.on_thck_bai02.services;

import iuh.fit.on_thck_bai02.models.Course;
import iuh.fit.on_thck_bai02.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository repo;

    public CourseService(CourseRepository repo) {
        this.repo = repo;
    }

    public List<Course> getAllCourses() {
        return repo.findAll();
    }

    public Course getCourseById(long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Course saveCourse(Course course) {
        return repo.save(course);
    }

    public void deleteCourseById(long id) {
        repo.deleteById(id);
    }

    public List<Course> findCourseByName(String name) {
        return repo.findCourseByClassNameContainingIgnoreCase(name);
    }

    public List<Course> findCourseByStartDate(LocalDate date) {
        return repo.findCourseByStartDate(date);
    }

    public List<Course> findCourseByEndDate(LocalDate date) {
        return repo.findCourseByEndDate(date);
    }

    public List<Course> findCourseByStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {
        return repo.findCourseByStartDateAndEndDate(startDate, endDate);
    }
}
