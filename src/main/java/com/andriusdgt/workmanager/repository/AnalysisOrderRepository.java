package com.andriusdgt.workmanager.repository;

import com.andriusdgt.workmanager.model.AnalysisOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisOrderRepository extends CrudRepository<AnalysisOrder, Long> {

}
