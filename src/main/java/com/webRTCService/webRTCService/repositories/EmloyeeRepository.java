package com.webRTCService.webRTCService.repositories;

import com.webRTCService.webRTCService.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmloyeeRepository extends JpaRepository<Employee,Long> {

}
