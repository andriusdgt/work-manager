package com.andriusdgt.workmanager.repository;

import com.andriusdgt.workmanager.model.Part;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends CrudRepository<Part, Long> {

}
