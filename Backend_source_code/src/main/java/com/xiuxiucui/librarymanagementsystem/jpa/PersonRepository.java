package com.xiuxiucui.librarymanagementsystem.jpa;

import com.xiuxiucui.librarymanagementsystem.library.Book;
import com.xiuxiucui.librarymanagementsystem.library.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String>{
}
