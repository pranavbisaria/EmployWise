package com.employwise.repository;

import com.employwise.model.Employ;
import java.util.List;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployRepository extends CouchbaseRepository<Employ, String> {
    List<Employ> findByManagerRelPath(String managerRelPath);
}
