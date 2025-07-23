package com.taehoonkang.moodmate.controller;


import com.taehoonkang.moodmate.dto.BoardDTO;
import com.taehoonkang.moodmate.service.BoardService;
import com.taehoonkang.moodmate.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequiredArgsConstructor
// One of the features of lombok, it automatically generates a constructor for final variables.

public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    // Constructor injection (DI)


    @GetMapping("/board/save")
    public String saveForm() {
        return "board-save";
    }

    @PostMapping("/board/save")
    public String save(@ModelAttribute BoardDTO boardDTO, HttpSession session) {
        // PostMapping: When receiving values from html to java, use @RequestParam (when sending as DTO, use @ModelAttribute)
        // Connection method: html's name = variable name of boardDTO class
        String loginEmail = (String) session.getAttribute("loginEmail");
        // The attribute of session is of type Object, so it needs to be cast to String
        if (loginEmail != null) {
            Integer age = memberService.findByEmail(loginEmail).getAge();
            // memberService.findByEmail is expected to bring the age of the corresponding email account
            boardDTO.setWriterAge(age);
        }
        boardService.save(boardDTO);
        //boardService.save converts boardDTO to entity and adds it to the db for saving
        return "board";
    }

    @GetMapping("/board")
    public String boardMain(HttpSession session) {
        if (session.getAttribute("loginEmail") == null) {
            return "redirect:/member/login";
            // "login" is not used here because?
            // If you use login, the url remains /board. If you use redirect:/, it is initialized to the url.
        }
        return "board-main";
    }

    @GetMapping("/board/list")
    public String findAll(Model model) {
        // GetMapping: When sending values from java to html, use Model
        List<BoardDTO> boardDTOList = boardService.findAll();
        // boardService.findAll is expected to receive all journal information in the DB as a list
        model.addAttribute("boardList", boardDTOList);
        // Send boardDTOList to html as the name boardList
        return "board-list";
    }

    @GetMapping("/board/{id}")
    // In board-list.html, when you click the title, it moves to the url /board/delete/{id}
    public String findById(@PathVariable Long id, Model model) {
        // GetMapping: When receiving a specific value from url to java, use @PathVariable
        BoardDTO boardDTO = boardService.findById(id);
        //boardService.findById is expected to search the account corresponding to id in the db
        model.addAttribute("board", boardDTO);
        // Send boardDTO to html as the name board
        return "board-detail";
    }

    @GetMapping("/board/update/{id}")
    // In board-detail.html, when you click the Edit button, it moves to the url /board/update/{id}
    public String updateForm(@PathVariable Long id, Model model) {
        // GetMapping: When receiving a specific value from url to java, use @PathVariable
        BoardDTO boardDTO = boardService.findById(id);
        //boardService.findById is expected to search the account corresponding to id in the db
        model.addAttribute("boardUpdate", boardDTO);
        // Send boardDTO to html as the name boardUpdate
        return "board-update";
    }

    @PostMapping("/board/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        // PostMapping: When receiving values from html to java, use @RequestParam (when sending as DTO, use @ModelAttribute)
        // Connection method: html's name = variable name of boardDTO class
        BoardDTO original = boardService.findById(boardDTO.getId());
        //boardService.findById is expected to search the account corresponding to id in the db
        if (original.getBoardPass().equals(boardDTO.getBoardPass())) {
            BoardDTO board = boardService.update(boardDTO);
            //boardService.update converts boardDTO to entity and updates it in the db for saving
            model.addAttribute("board", board);
            // Send boardDTO to html as the name board
            return "board-detail";
        } else {
            model.addAttribute("error", "Password does not match!");
            // Send the sentence that the password does not match to html as the name error
            model.addAttribute("boardUpdate", original);
            // Send the original record to html as the name boardUpdate
            return "board-update";
        }
    }

    @PostMapping("/board/delete")
    public String delete(@RequestParam Long id, @RequestParam String boardPass, Model model) {
        // PostMapping: When receiving values from html to java, use @RequestParam (when sending as DTO, use @ModelAttribute)
        // Connection method: html's name = variable name of boardDTO class
        boolean result = boardService.deleteWithPass(id, boardPass);
        //boardService.deleteWithPass is expected to delete the Journal corresponding to id in the db if the password entered matches
        if (result) {
            return "redirect:/board";
        } else {
            model.addAttribute("error", "Password does not match!");
            // Send the sentence that the password does not match to html as the name error
            BoardDTO boardDTO = boardService.findById(id);
            //boardService.findById is expected to search the account corresponding to id in the db
            model.addAttribute("boardUpdate", boardDTO);
            // Send the original record to html as the name boardUpdate
            return "board-update";
        }
    }

    @GetMapping("/board/delete/{id}")
    // In board-list.html, when you click the delete button, it moves to the url /board/delete/{id}
    public String deleteByAdmin(@PathVariable Long id, HttpSession session) {
        // GetMapping: When receiving a specific value from url to java, use @PathVariable
        String role = (String) session.getAttribute("loginRole");
        if (!"ADMIN".equals(role)) {
            return "redirect:/board/list";
        }
        boardService.delete(id);
        //boardService.delete is expected to delete the Journal corresponding to id in the db
        return "redirect:/board/list";
    }

}
