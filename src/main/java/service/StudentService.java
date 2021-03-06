package service;

import model.Course;
import model.PersonalAssignmentAnswer;
import model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isiki on 2016/7/3.
 */
public interface StudentService {
    Student getStudentById(String id);
    ArrayList<Course> getAllCourseById(String id);
    PersonalAssignmentAnswer getAnswer(String aid, String sid);
    List<Student> getAllStudents();
    Student getStudentByName(String name);
}
