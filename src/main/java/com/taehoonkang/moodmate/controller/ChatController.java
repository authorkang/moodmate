package com.taehoonkang.moodmate.controller;

import com.taehoonkang.moodmate.dto.ChatRequestDTO;
import com.taehoonkang.moodmate.dto.ChatResponseDTO;
import com.taehoonkang.moodmate.dto.MemberDTO;
import com.taehoonkang.moodmate.service.ChatService;
import com.taehoonkang.moodmate.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
//@RestController is used when the return value is json, @Controller is used when the return value is html.
@RequiredArgsConstructor
//One of the features of lombok, it automatically generates a constructor for final variables.

public class ChatController {

    private final ChatService chatService;
    private final MemberService memberService;
    //Constructor injection (DI)

    @PostMapping("/api/chat/ask")
    public ChatResponseDTO ask(@RequestBody ChatRequestDTO request, HttpSession session) {
        //PostMapping: When receiving values from json to java, use @RequestBody
        //The body content of json is contained in ChatRequestDTO.
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail == null) {
            return new ChatResponseDTO("Login is required.");
        }
        MemberDTO member = memberService.findByEmail(loginEmail);
        //memberService.findByEmail is expected to bring the memberDTO information corresponding to myEmail from the DB
        if (member == null || member.getOpenaiApikey() == null || member.getOpenaiApikey().isEmpty()) {
            return new ChatResponseDTO("OpenAI API Key is not registered.");
        }

        String zenQuote = chatService.getRandomZenQuote();
        //chatService.getRandomZenQuote is expected to receive a quote from ZenQuote
        String prompt = "State the Quote below in quotation mark and give advice in about 70 words based with the quote.\n" +
                "Give specific advice.\n" +
                "Give advice in a friendly and encouraging tone, as if a friend is talking to a friend.\n" +
                "Quote: " + zenQuote + "\n" +
                "Question: " + request.getRequestText();
        String response = chatService.askChatGPT(prompt, member.getOpenaiApikey());
        //chatService.askChatGPT is expected to receive a response from ChatGPT
        return new ChatResponseDTO(response);
        //@Controller automatically converts the return value to html, @RestController automatically converts the return value to json.
    }
} 