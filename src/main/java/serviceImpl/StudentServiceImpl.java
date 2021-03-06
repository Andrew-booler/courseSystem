package serviceImpl;

import dao.CourseDao;
import dao.SelectionDao;
import dao.StudentDao;
import model.Course;
import model.PersonalAssignmentAnswer;
import model.Student;
import model.TeamAssignmentAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.StudentService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isiki on 2016/7/3.
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private SelectionDao selectionDao;

    @Autowired
    private CourseDao courseDao;

    public Student getStudentById(String id) {
        return studentDao.getStudentById(id);
    }

    public ArrayList<Course> getAllCourseById(String id) {
        ArrayList<String> courseid = selectionDao.getCourseIdByStudentId(id);
        ArrayList<Course> courses = new ArrayList<>();
        for (String cid:courseid ){
            courses.add(courseDao.getCourseById(cid));
        }
        return courses;
    }

    public PersonalAssignmentAnswer getAnswer(String aid, String sid) {
        ArrayList<PersonalAssignmentAnswer> p = studentDao.getAnswer(aid,sid);
        PersonalAssignmentAnswer answer = new PersonalAssignmentAnswer();
        if (p.size()!=0)
            answer = p.get(0);
        return answer;
    }

    public List<Student> getAllStudents(){
        return studentDao.getAllStudents();
    }

    public Student getStudentByName(String name){
        return studentDao.getStudentByName(name);
    }

}
