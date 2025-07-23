package com.taehoonkang.moodmate.controller;
import com.taehoonkang.moodmate.dto.MemberDTO;
import com.taehoonkang.moodmate.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequiredArgsConstructor
// One of the features of lombok, it automatically generates a constructor for final variables.

public class MemberController {

    private final MemberService memberService;
    // Constructor injection (DI)

    @GetMapping("/member/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        // PostMapping: When receiving values from html to java, use @RequestParam (when sending as DTO, use @ModelAttribute)
        // Connection method: html's name = variable name of MemberDTO class
        memberService.save(memberDTO);
        // memberService.save converts memberDTO to entity and adds it to the db for saving
        return "login";
    }

    @GetMapping("/member/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        //HttpSession: Remembers login information until the browser is closed or logged out.
        MemberDTO loginResult = memberService.login(memberDTO);
        // memberService.login is expected to determine whether login is successful
        if (loginResult != null) {
            // login success
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            session.setAttribute("loginRole", loginResult.getRole());
            return "main";
        } else {
            // login failure
            return "login";
        }
    }

    @GetMapping("/member/")
    public String findAll(HttpSession session, Model model) {
        // GetMapping: When sending values from java to html, use Model
        String role = (String) session.getAttribute("loginRole");
        // The attribute of session is of type Object, so it needs to be cast to String
        if (!"ADMIN".equals(role)) {
            return "redirect:/";
            // "index" is not used here because?
            // If you use index, the url remains /member/. If you use redirect:/, it is initialized to the url.
        }
        List<MemberDTO> memberDTOList = memberService.findAll();
        // memberService.findAll is expected to receive all member information in the DB as a list
        model.addAttribute("memberList", memberDTOList);
        // Send memberDTOList to html as the name memberList
        return "list";
    }

    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model) {
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByEmail(myEmail);
        // memberService.findByEmail is expected to bring the memberDTO information corresponding to myEmail from the DB
        model.addAttribute("updateMember", memberDTO);
        return "update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        //memberService.update converts memberDTO to entity and updates it in the db for saving
        return "login";
    }

    @GetMapping("/member/delete/{id}")
    // In list.html, when you click the delete button, it moves to the url /member/delete/{id}
    public String deleteById(@PathVariable Long id, HttpSession session) {
        // GetMapping: When receiving a specific value from url to java, use @PathVariable
        String role = (String) session.getAttribute("loginRole");
        if (!"ADMIN".equals(role)) {
            return "redirect:/";
        }
        memberService.deleteById(id);
        //memberService.deleteById is expected to delete the account corresponding to id in the db
        return "redirect:/member/";
    }

    @GetMapping("/member/logout")
    // In main.html, when you click the logout hyperlink, it moves to the url /member/logout
    public String logout(HttpSession session) {
        session.invalidate();
        // Initialize session information
        return "index";
    }

    @GetMapping("/main")
    public String mainPage(HttpSession session) {
        if (session.getAttribute("loginEmail") == null) {
            return "redirect:/member/login";
        }
        return "main";
    }
}