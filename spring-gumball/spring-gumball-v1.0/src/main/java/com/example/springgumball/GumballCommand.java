package com.example.springgumball;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class GumballCommand {

    private String action ;
    private String message ;

/*
    public String getMessage() { return this.message ; }
    public void setMessage(String arg) { this.message = arg ; }
*/
    
}

