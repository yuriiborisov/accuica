//package code.accessor.implementations.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class ViewController {
//    @GetMapping("/matrix")
//    public String showUserList(@RequestParam("roleId") String roleId, Model model) {
//        model.addAttribute("roleId", roleId);
//        return "matrix";
//    }
//
//    @GetMapping("/privileges")
//    public String showUserList(Model model) {
//        return "privileges";
//    }
//}
