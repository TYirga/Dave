package com.example.demo.Controllers;

import com.example.demo.Models.Room;
import com.example.demo.Repositories.RoomRepository;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;

    @RequestMapping("/rooms")
    public String roomList(Model model){
        model.addAttribute("rooms", roomRepository.findAll());

        return "list";
    }

    @RequestMapping("/")
    public String index(Model model){

        model.addAttribute("rooms", roomRepository.findAll());
        return "index";
    }

    @RequestMapping("/add")
    public String roomForm(Model model){
        model.addAttribute("room", new Room());
        return "roomForm";
    }

    @PostMapping("/process")
    public String processForm(@Valid Room room, BindingResult result){
        if(result.hasErrors()){
            return "roomForm";
        }
        roomRepository.save(room);
        return "redirect:/rooms";
    }

    @RequestMapping("/detail/{id}")
    public String roomDetail(@PathVariable("id") long id, Model model){
        model.addAttribute("room", roomRepository.findOne(id));
        return "detail";
    }

    @RequestMapping("/rent/{id}")
    public String roomRent(@PathVariable("id") long id, Model model){
        Room room = roomRepository.findOne(id);
        room.setRented(!room.isRented());
        roomRepository.save(room);

        return "redirect:/rooms";
    }

}

