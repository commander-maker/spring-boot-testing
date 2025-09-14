package com.example.springcrud.Controller;



import java.sql.Date;

import javax.naming.Binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springcrud.Models.Client;
import com.example.springcrud.Models.ClientsDto;
import com.example.springcrud.Repositories.ClientRepository;

import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.RequestBody;
//import java.sql.Date;
import org.springframework.web.bind.annotation.RequestParam;


@Controller 
@RequestMapping("/clients")
public class ClientsController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping({"", "/"})
    public String getClients(Model model) {
        var clients = clientRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        model.addAttribute("clients",clients);
        
        return "clients/index";
    }

    @GetMapping("/api")
    @org.springframework.web.bind.annotation.ResponseBody
    public java.util.List<Client> getClientsJson() {
        return clientRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }


    @GetMapping("/create")
    public String createClient(Model model){
        ClientsDto clientsDto = new ClientsDto();
        model.addAttribute("clientsDto", clientsDto);
        return "clients/create"; 
    }
    
    @PostMapping("/create")
    public String createClient (
        @Valid  @ModelAttribute ClientsDto clientsDto,
        BindingResult bindingResult,
        Model model
    ) {

        if(clientRepository.findByEmail(clientsDto.getEmail()) !=null){
            bindingResult.addError(
                new FieldError("clientsDto","email", clientsDto.getEmail(),
                false,null,null, "Email address is already used")
            );
        }

        if(bindingResult.hasErrors()){
            return "clients/create";
        }

        Client client = new Client();
        client.setName(clientsDto.getName());
        client.setEmail(clientsDto.getEmail());
        client.setAddress(clientsDto.getAddress());
        client.setCreatedAt(new Date(System.currentTimeMillis()));
        
        clientRepository.save(client);

       return "redirect:/clients";
    }

    @GetMapping("/edit")
    public String editClient(Model model, @RequestParam int id){
        Client client =clientRepository.findById(id).orElse(null);
        if(client == null){
            return "redirect:/clients";
        }

        ClientsDto clientsDto = new ClientsDto();
        clientsDto.setName(client.getName());
        clientsDto.setAddress(client.getAddress());
        clientsDto.setEmail(client.getEmail());

        model.addAttribute("client", client);
        model.addAttribute("clientsDto", clientsDto);

        return "clients/edit";
    }

    @PostMapping("/edit")
    public String editClient(
        Model model,
        @RequestParam int id,
        @Valid @ModelAttribute ClientsDto clientsDto,
        BindingResult result
    ){
        Client client = clientRepository.findById(id).orElse(null);
        if(client == null){
            return "redirect:/clients";
        }

        model.addAttribute("client", client);
        if(result.hasErrors()){
            return "clients/edit";
        }

        client.setName(clientsDto.getName());
        client.setEmail(clientsDto.getEmail());
        client.setAddress(clientsDto.getAddress());

        try{
            clientRepository.save(client);
        }catch(Exception ex){
            result.addError(
                new FieldError("clientsDto", "email", clientsDto.getEmail()
                ,false,null,null,"Email address is already used")
            );
            return "clients/edit";
        }

        return "redirect:/clients";
    }

   
    
    @GetMapping("/delete")
    public String deleteClient(@RequestParam int id){
        Client client = clientRepository.findById(id).orElse(null);

        if(client !=null){
            clientRepository.delete(client);
        }

        return "redirect:/clients";
    }

    

}
