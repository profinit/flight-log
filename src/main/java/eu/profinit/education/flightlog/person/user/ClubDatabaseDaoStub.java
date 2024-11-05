package eu.profinit.education.flightlog.person.user;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("stub")
public class ClubDatabaseDaoStub implements ClubDatabaseDao {

    public static final String ROLE_PILOT = "PILOT";

    public static final String ROLE_BACKOFFICE = "BACKOFFICE";

    @Override
    public List<User> getUsers() {
        return List.of(
            new User(1L, "Kamila", "Spoustová", List.of(ROLE_PILOT)),
            new User(2L, "Naděžda", "Pavelková", List.of(ROLE_PILOT)),
            new User(3L, "Silvie", "Hronová", List.of(ROLE_PILOT)),
            new User(9L, "Miloš", "Korbel", List.of(ROLE_PILOT, ROLE_BACKOFFICE)),
            new User(10L, "Petr", "Hrubec", List.of(ROLE_PILOT, ROLE_BACKOFFICE)),
            new User(13L, "Michal", "Vyvlečka", List.of(ROLE_BACKOFFICE))
        );
    }
}