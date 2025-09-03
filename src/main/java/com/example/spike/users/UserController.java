package com.example.spike.users;

import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@Controller
@ResponseBody
class UserController {

    private final UsersRepository usersRepository;

    UserController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @PostMapping("/helloWorld")
    void helloWorld () {
        LocalDate today = LocalDate.now();
        System.out.println("Hello World!");
        System.out.println(today);
    }

    @GetMapping ("/users/{userId}")
    void getUser (@PathVariable int userId) {
        usersRepository.findById(userId).ifPresent(user -> {
            System.out.println("user:[" + user.name() + "]");
        });
    }

    @PostMapping ("/users/update/{userId}")
    void updateUser (@PathVariable int userId, @RequestBody Map<String, String> data) {
        usersRepository.findById(userId).ifPresent(user -> {
            String name = data.get("name");
            var newUser = this.usersRepository.save(new Users(userId, name));
            System.out.println("user:[" + newUser + "]");
        });
    }
}

interface UsersRepository extends CrudRepository<Users, Integer> {

}

record Users(@Id int id, String name) {}