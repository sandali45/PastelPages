package com.example.pastelpages.repository;
import com.example.pastelpages.model.ScrapbookPage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<ScrapbookPage, Long> {
}