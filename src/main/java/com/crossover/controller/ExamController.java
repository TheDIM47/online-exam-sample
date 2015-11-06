package com.crossover.controller;

import com.crossover.dto.EntryDTO;
import com.crossover.dto.ExamDTO;
import com.crossover.entity.Exam;
import com.crossover.entity.ExamProtocol;
import com.crossover.entity.Question;
import com.crossover.entity.User;
import com.crossover.repository.UserRepository;
import com.crossover.service.ExamService;
import com.crossover.util.Converters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Controller
public class ExamController {
    @Value("${exam.time.minutes}")
    private Integer examTimeMins;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ExamService examSvc;

    @RequestMapping(value = {"/", "/index"})
    public String home(Model model, HttpServletRequest request) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            final int examId = (Integer) request.getSession().getAttribute("examId");
            final Exam exam = examSvc.getExam(examId);

            final List<Question> questions = examSvc.getQuestionsForExam(examId);

            final ExamProtocol protocol = new ExamProtocol();
            protocol.setExam(exam);
            protocol.setUser(getUser());
            protocol.setStart(Calendar.getInstance().getTime());
            protocol.setQuestionCount(questions.size());

            final int protocolId = examSvc.insertExam(protocol);

            model.addAttribute("results", new ExamDTO(protocolId, examId));
            model.addAttribute("examName", String.format("Welcome to \"%s\"!", exam.getName()));
            model.addAttribute("questions", Converters.questionsToDTO(questions));

            request.getSession().setAttribute("examStarted", protocol.getStart().getTime());
            final int remaining = getRemainingTime(request);
            model.addAttribute("examTime", remaining);

            return "index";
        } else {
            return "redirect:/login";
        }
    }

    private int getRemainingTime(HttpServletRequest request) {
        final long start = (long) request.getSession().getAttribute("examStarted");
        final int remaining = (int) ((examTimeMins * 60) - ((Calendar.getInstance().getTimeInMillis() - start) / 1000));
        return remaining;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, @RequestParam Optional<String> error, HttpServletRequest request) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.isAuthenticated();
        logger.info("AUTH: {} [{}]", auth, auth.isAuthenticated());

        final Exam exam = examSvc.getRandomExam();
        request.getSession().setAttribute("examId", exam.getId());

        model.addAttribute("examName", exam.getName());
        model.addAttribute("examDescription", exam.getDescription());
        return "login";
    }

    @RequestMapping(value = "/questions/{id}")
    @ResponseBody
    public List<Question> getQuestionsForExam(@PathVariable("id") Integer examId) {
        return examSvc.getQuestionsForExam(examId);
    }

    @RequestMapping(value = "/answers/{id}")
    @ResponseBody
    public List<EntryDTO> getAnswersForQuestion(@PathVariable("id") Integer questionId) {
        return Converters.answersToDTO(examSvc.getAnswersForQuestion(questionId));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String submitResult(Model model, @Valid @ModelAttribute("results") ExamDTO frm, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            // TODO?
        }
        logger.info("Submit: {}", frm);
        request.getSession().removeAttribute("examId");

        final ExamProtocol protocol = examSvc.getExamProtocol(frm.getId());
        protocol.setFinish(Calendar.getInstance().getTime());

        examSvc.calcGrade(protocol, frm.getExamId(), frm.getAnswers());
        examSvc.updateExamProtocol(protocol);
        logger.info("Submit: {}", protocol);
        return "redirect:/stat/" + protocol.getId();
    }

    @RequestMapping(value = "/stat/{id}", method = RequestMethod.GET)
    public String stat(Model model, @PathVariable("id") Integer protocolId) {
        if (protocolId < 1) {
            return "redirect:/index";
        }
        final ExamProtocol protocol = examSvc.getExamProtocol(protocolId);
        if (protocol == null) {
            return "redirect:/index";
        }
        logger.info("Stat: {}", protocol);
        final Exam exam = examSvc.getExam(protocol.getExam().getId());
        model.addAttribute("title", String.format("Your results for %s", exam.getName()));
        model.addAttribute("start", protocol.getStart());
        model.addAttribute("finish", protocol.getFinish());
        model.addAttribute("questionCount", protocol.getQuestionCount());
        model.addAttribute("grade", protocol.getGrade());
        model.addAttribute("maxGrade", ExamService.MAX_GRADE);
        return "stat";
    }

    @RequestMapping(value = "/time")
    @ResponseBody
    public Integer timer(HttpServletRequest request) {
        return getRemainingTime(request);
    }

    private User getUser() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByUsername(username);
    }

    private static final Logger logger = LoggerFactory.getLogger(ExamController.class);
}
