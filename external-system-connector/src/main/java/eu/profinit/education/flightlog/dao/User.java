package eu.profinit.education.flightlog.dao;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {

    private Long memberId;
    private String firstName;
    private String lastName;
    private List<String> roles;
}
