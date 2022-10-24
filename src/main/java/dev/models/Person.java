package dev.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Person {

    private int person_id;
    @NotEmpty(message = "Name should be not empty")
    @Size(min=3,max=100, message = "Full name size should be between 3 and 100")
   // @UniqueElements(message = "Full name should be unique")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+", message = "Should be (Name Surname)")
    private String name;
    @NotEmpty(message = "Birth should be not empty")
    private String birth;

    public int getPerson_id() {
        return person_id;
    }

    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}