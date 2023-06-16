package com.example.demo.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Machine;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

}


