package com.crossover.repository;

import com.crossover.entity.ExamProtocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ExamProtocolRepository extends JpaRepository<ExamProtocol, Integer> {}
