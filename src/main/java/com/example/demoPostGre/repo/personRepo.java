package com.example.demoPostGre.repo;

import com.example.demoPostGre.model.person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface personRepo extends JpaRepository<person,Long> {
}
