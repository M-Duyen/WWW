package iuh.fit.on_thck_bai02.controllers;

import iuh.fit.on_thck_bai02.models.Course;
import iuh.fit.on_thck_bai02.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping()
    public String getAllCourses(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/searchByName")
    public String findByName(@RequestParam("name") String name, Model model) {
        List<Course> courses = courseService.findCourseByName(name);
        model.addAttribute("courses", courses);
        return "courses";

    }

    @PreAuthorize("permitAll()")
    @GetMapping("/searchByStartDate")
    public String findByStartDate(@RequestParam("startDate") LocalDate startDate, Model model) {
        List<Course> courses = courseService.findCourseByStartDate(startDate);
        model.addAttribute("courses", courses);
        return "courses";
    }

     @PreAuthorize("permitAll()")
    @GetMapping("/searchByEndDate")
    public String findByEndDate(@RequestParam("endDate") LocalDate endDate, Model model) {
        List<Course> courses = courseService.findCourseByEndDate(endDate);
        model.addAttribute("courses", courses);
        return "courses";
    }

     @PreAuthorize("permitAll()")
    @GetMapping("/searchByStartAndEndDate")
    public String findByEndDate(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate, Model model) {
        List<Course> courses = courseService.findCourseByStartDateAndEndDate(startDate, endDate);
        model.addAttribute("courses", courses);
        return "courses";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("isUpdate", false);
        return "create";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        model.addAttribute("isUpdate", true);
        return "create";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping("/save")
    public String createCourse(@ModelAttribute Course course) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean admin = auth.getAuthorities().stream()
                .anyMatch(user -> user.getAuthority().contains("ADMIN"));
        if(!admin){
            course.setOpen(false);
        }

        courseService.saveCourse(course);
        return "redirect:/course";
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
//    @PostMapping("/edit/{id}")
//    public String updateCourse(@PathVariable("id") long id, @ModelAttribute Course course) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        boolean admin = auth.getAuthorities().stream()
//                .anyMatch(user -> user.getAuthority().contains("ADMIN"));
//        if(!admin){
//            course.setOpen(false);
//        }
//
//        course.setId(id);
//        courseService.saveCourse(course);
//        return "redirect:/course";
//    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable("id") long id) {
        courseService.deleteCourseById(id);
        return "redirect:/course";
    }


}
