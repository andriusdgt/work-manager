package com.andriusdgt.workmanager.repository;

import com.andriusdgt.workmanager.model.ValidationResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidationResultRepository extends CrudRepository<ValidationResult, Long> {

}
