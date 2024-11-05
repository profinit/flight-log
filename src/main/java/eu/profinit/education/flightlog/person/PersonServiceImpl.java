package eu.profinit.education.flightlog.person;


import eu.profinit.education.flightlog.person.user.ClubDatabaseDao;
import eu.profinit.education.flightlog.person.user.User;
import eu.profinit.education.flightlog.common.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final ClubDatabaseDao clubDatabaseDao;

    @Override
    public List<PersonTo> getClubMembers() {
        return clubDatabaseDao.getUsers().stream().map(this::convert2To)
            .toList();
    }

    private User getClubMemberById(long memberId) {
        return clubDatabaseDao.getUsers().stream()
            .filter(user -> user.getMemberId() == memberId)
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Club member with ID {} does not exist.", memberId));
    }

    @Override
    public Person getExistingOrCreatePerson(PersonTo personTo) {
        if (personTo == null) {
            return null;
        }
        if (personTo.getMemberId() != null) {
            User userFromBackend = getClubMemberById(personTo.getMemberId());
            Optional<Person> maybeClubMember = personRepository.findByMemberId(userFromBackend.getMemberId());
            if (maybeClubMember.isPresent()) {
                return maybeClubMember.get();
            } else {
                Person clubMember = new Person(Person.Type.CLUB_MEMBER, personTo.getMemberId(), userFromBackend.getFirstName(), userFromBackend.getLastName());
                return personRepository.save(clubMember);
            }
        } else {
            Person guest = createGuestEntity(personTo);
            return personRepository.save(guest);
        }
    }

    private Person createGuestEntity(PersonTo personTo) {
        Address address = null;
        if (personTo.getAddress() != null) {
            address = new Address(personTo.getAddress().getStreet(), personTo.getAddress().getCity(), personTo.getAddress().getPostalCode(), personTo.getAddress().getCountry());
        }
        return new Person(Person.Type.GUEST, personTo.getFirstName(), personTo.getLastName(), address);
    }

    private PersonTo convert2To(User user) {
        return PersonTo.ofClubMember(user.getMemberId(), user.getFirstName(), user.getLastName());
    }

}