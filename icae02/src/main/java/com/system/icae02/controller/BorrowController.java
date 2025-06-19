package com.system.icae02.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.icae02.model.Borrow;
import com.system.icae02.service.BorrowService;


@RestController
@RequestMapping("/borrows")
public class BorrowController {

    @Autowired
    public BorrowService borrowService;
    
    @PostMapping("/add")
    public ResponseEntity<Borrow> addBorrowEntry(@RequestBody Borrow borrow){
        Borrow saveBorrow = borrowService.addNewEntry(borrow);
        return ResponseEntity.ok(saveBorrow);
    }
}