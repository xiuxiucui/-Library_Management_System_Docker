package com.xiuxiucui.librarymanagementsystem.jpa;

import com.xiuxiucui.librarymanagementsystem.library.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String>{
}
