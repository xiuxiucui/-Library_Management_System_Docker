package com.xiuxiucui.librarymanagementsystem.jpa;

import com.xiuxiucui.librarymanagementsystem.library.Person;
import com.xiuxiucui.librarymanagementsystem.library.Records;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordsRepository extends JpaRepository<Records, Integer>{
}
