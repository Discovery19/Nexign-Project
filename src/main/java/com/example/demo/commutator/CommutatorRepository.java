package com.example.demo.commutator;

import com.example.demo.models.Cdr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommutatorRepository extends JpaRepository<Cdr, Long> {
}
