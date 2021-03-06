package com.gracefull.demo;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class EmployeeControler {

    private final EmployeeRepository repository;

    private final EmployeeResourceAssembler assembler;

    public EmployeeControler(EmployeeRepository repository, EmployeeResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/employees")
    Resources<Resource<Employee>> all(){
        List<Resource<Employee>> employees = repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(employees,
                linkTo(methodOn(EmployeeControler.class).all()).withSelfRel());
    }

    @PostMapping("/employees")
    ResponseEntity<?> newEmployee (@RequestBody Employee newEmployee) throws URISyntaxException {
        Resource<Employee> employees = assembler.toResource(repository.save(newEmployee));
        return ResponseEntity
                .created(new URI(employees.getId().expand().getHref()))
                .body(employees);
    }

    @GetMapping("/employees/{id}")
    Resource<Employee> one (@PathVariable Long id){
        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return assembler.toResource(employee);

    }

    @PutMapping("/employees/{id}")
    Employee replaceEmployee (@RequestBody Employee newEmployee, @PathVariable Long id){
        return repository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setRole(newEmployee.getRole());
            return repository.save(employee);
        })
                .orElseGet(() -> {
                   newEmployee.setId(id);
                   return repository.save(newEmployee);
                });
    }

    @DeleteMapping ("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
