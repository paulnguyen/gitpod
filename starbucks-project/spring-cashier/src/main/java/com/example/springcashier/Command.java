package com.example.springcashier;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class Command {

    private String action ;
    private String message ;
    
}
