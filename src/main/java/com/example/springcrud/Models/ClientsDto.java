package com.example.springcrud.Models;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;

@Data
public class ClientsDto {
    @NotEmpty(message = "name is required")
    private String name;

    @NotEmpty(message = "email is required")
    private String email;

    @NotEmpty(message = "addrss is required")
    private String address;
    
    
  //  @NotEmpty(message)

}
