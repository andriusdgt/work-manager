package com.andriusdgt.workmanager.repository;

import com.andriusdgt.workmanager.model.ReplacementOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplacementOrderRepository extends CrudRepository<ReplacementOrder, Long> {

}
