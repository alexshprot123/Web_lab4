package DTO;

public class AddUserDTO {
    public String phone;
    public String name;
    public AddUserDTO(){

    }

    public AddUserDTO(String phone, String name){
        this.name = name;
        this.phone = phone;
    }
}
