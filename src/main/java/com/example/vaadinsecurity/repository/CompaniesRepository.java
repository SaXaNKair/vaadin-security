package com.example.vaadinsecurity.repository;

import com.example.vaadinsecurity.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompaniesRepository extends JpaRepository<Company, Long> {
}
