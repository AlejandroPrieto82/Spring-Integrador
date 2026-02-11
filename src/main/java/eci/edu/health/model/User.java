package eci.edu.health.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                   
@NoArgsConstructor      
@AllArgsConstructor     
public class User {
    private String id;
    private String name;
    private String lastName;
    private String email;
}
