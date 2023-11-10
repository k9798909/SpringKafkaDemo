package com.example.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.consumer.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

}
