package com.klef.project.repository;
import java.util.*;
import com.klef.project.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    
    @Query("SELECT COUNT(a) > 0 FROM Application a WHERE a.email = :email AND a.id = :id")
    boolean existsByEmailAndJobId(@Param("email") String email, @Param("jobId") Integer i);
    @Query("SELECT a FROM Application a WHERE a.email = :email")
    List<Application> findByEmail(@Param("email")String email);
}

