package DTO;

public class AddDoctorDTO {
    public String name;
    public String spec;

    public AddDoctorDTO(){

    }

    public AddDoctorDTO(String name, String spec){
        this.name = name;
        this.spec = spec;
    }
}
