package org.softwire.training.bookish.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/members")
public class MembersController {

    @RequestMapping("")
    ModelAndView showMemberList() {
        return new ModelAndView("index");
    }
}
