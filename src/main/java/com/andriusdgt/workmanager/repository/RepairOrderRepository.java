package com.andriusdgt.workmanager.repository;

import com.andriusdgt.workmanager.model.RepairOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairOrderRepository extends CrudRepository<RepairOrder, Long> {

}
