package eu.profinit.education.flightlog.person;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final PersonService personService;

    @GetMapping("/user")
    public List<PersonTo> getClubMembers() {
        return personService.getClubMembers();
    }

}