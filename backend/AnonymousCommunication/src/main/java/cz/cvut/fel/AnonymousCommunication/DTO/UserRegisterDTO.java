package cz.cvut.fel.AnonymousCommunication.DTO;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
