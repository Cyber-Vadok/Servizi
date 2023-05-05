package it.unisalento.pas.smartcitywastemanagement.dto;

import org.springframework.data.annotation.Id;

/**
 * classe per quello che voglio trasmettere di quell'entità (user) dato che non sempre volgio salvare tutto di
 * un'entità e nello stesso formato
 */
public class UserDTO {

    @Id
    private String id;
    private String name;
    private String surname;
    private String email;
    private Integer age;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
