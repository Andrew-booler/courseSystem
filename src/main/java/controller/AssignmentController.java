package controller;

import dao.CourseDao;
import model.Assignment;
import model.Course;
import model.PersonalAssignmentAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.AssignmentAnswerService;
import service.AssignmentService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import entity.BaseException;
import service.CourseService;
import service.StudentService;
import util.PageResultSet;
import util.UserSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.in;

/**
 * Created by isiki on 2016/7/4.
 */
@Controller
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private AssignmentAnswerService assignmentAnswerService;
    @Autowired
    private CourseService courseService;


    @RequestMapping(value = "t/add_assignment", method = RequestMethod.GET)
    public String addAssignment(HttpServletRequest request, Model model) {
        String courseId = (String)request.getSession().getAttribute("course_id");
        model.addAttribute("course",courseService.searchCourseById(courseId));
        return "assignment/add_assignment";
    }

    @RequestMapping(value = "t/save_assignment", method = RequestMethod.POST)
    public void saveAssignment(HttpServletRequest request,HttpServletResponse response) {
        Assignment assignment = new Assignment();
        String cid = request.getParameter("courseId");
        assignment.setCourseId(cid);
        assignment.setHeading(request.getParameter("heading"));
        assignment.setDescription(request.getParameter("description"));
        String startDateRaw = request.getParameter("startDate");
        String endDateRaw = request.getParameter("endDate");
        String isTeamwork = request.getParameter("isTeamwork");
        boolean tw = isTeamwork==null?false:(isTeamwork.equals("true"));
        assignment.setIsTeamwork(tw);
        assignment.setTotalGrade(5);
        assignment.setAttachmentUrl("about:blank");
        int id = assignmentService.consultAssignmentMaxId(cid)+1;
        assignment.setIdInCourse(id);
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            assignment.setStartTime(dateFormat.parse(startDateRaw));
            assignment.setEndTime(dateFormat.parse(endDateRaw));
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        assignmentService.insertAssignment(assignment);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    //@RequestMapping(value = "t/assignment",method = RequestMethod.GET)
    public String listAssignment(Model model, HttpSession session){
        UserSession user =new UserSession(session);
        List<Assignment> assignment = assignmentService.getAllByCourseId(user.getCourse().getId());
        PageResultSet<Assignment> assignments = new PageResultSet<>();
        assignments.setList(assignment);
        model.addAttribute("assignments",assignments);
        return "assignmentlist";
    }

    //@RequestMapping(value = "t/assignment_detail",method = RequestMethod.GET)
    public String consultAssignment(String assignment_id ,Model model){
        Assignment det=assignmentService.getAssignmentById(assignment_id);
        model.addAttribute("assignment",det);
        if(true==det.getIsTeamwork()) {
            model.addAttribute("teamType", "team");
            model.addAttribute("assignmentAnswers",assignmentAnswerService.getTeamAnswerByAssignment(det.getId()));
        }else{
            model.addAttribute("teamType","personal");
            model.addAttribute("assignmentAnswers",assignmentAnswerService.getPersonalAnswerByAssignment(det.getId()));
        }
        return "assignment_detail";
    }

    //@RequestMapping(value = "t/assignment_detail", method = RequestMethod.POST)
    //@ResponseBody
    public Assignment alterAssignment(Assignment assignment, @ModelAttribute("startDate") String startDate , @ModelAttribute("endDate")String endDate){
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        try {
            assignment.setStartTime(dateFormat.parse(startDate));
            assignment.setEndTime(dateFormat.parse(endDate));
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        return assignmentService.updateAssignment(assignment);
    }

    //@RequestMapping(value = "t/assignment", method = RequestMethod.POST)
    //@ResponseBody
    public List<Assignment> removeAssignment(String assignment_id, HttpSession session){
        assignmentService.removeAssignment(assignment_id);
        UserSession user =new UserSession(session);
        return assignmentService.getAllByCourseId(user.getCourse().getId());
    }


}
