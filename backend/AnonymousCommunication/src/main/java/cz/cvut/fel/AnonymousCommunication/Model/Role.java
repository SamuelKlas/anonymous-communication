package cz.cvut.fel.AnonymousCommunication.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data @Entity @NoArgsConstructor @AllArgsConstructor
public class Role extends AbstractEntity{
    private String name;
}
