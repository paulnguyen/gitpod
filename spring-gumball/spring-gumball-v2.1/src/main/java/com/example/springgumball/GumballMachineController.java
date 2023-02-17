package com.example.springgumball;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.time.*; 

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64.Encoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

import com.example.gumballmachine.GumballMachine ;

@Slf4j
@Controller
@RequestMapping("/")
public class GumballMachineController {

	private static String key = "kwRg54x2Go9iEdl49jFENRM12Mp711QI" ;
    
    private String hmac_sha256(String secretKey, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256") ;
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256") ;
            mac.init(secretKeySpec) ;
            byte[] digest = mac.doFinal(data.getBytes()) ;
            java.util.Base64.Encoder encoder = java.util.Base64.getEncoder() ;
            String hash_string = encoder.encodeToString(digest) ;
            return hash_string ;
        } catch (InvalidKeyException e1) {
            throw new RuntimeException("Invalid key exception while converting to HMAC SHA256") ;
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException("Java Exception Initializing HMAC Crypto Algorithm") ;
        }
    }    

    @GetMapping
    public String getAction( @ModelAttribute("command") GumballCommand command, 
                            Model model) {
      
        GumballModel g = new GumballModel() ;
        g.setModelNumber( "SB102927") ;
        g.setSerialNumber( "2134998871109") ;
        model.addAttribute( "gumball", g ) ;

        GumballMachine gm = new GumballMachine() ;
        String message = gm.toString() ;
    
        String state = gm.getState().getClass().getName() ;
        command.setState( state ) ;

        long ts_long = java.lang.System.currentTimeMillis() ;
        String ts_string = String.valueOf(ts_long) ;
        command.setTimestamp( ts_string ) ;

        String text = state + "/" + ts_string ;
        String hash_string = hmac_sha256( key, text ) ;
        command.setHash( hash_string ) ;

        String server_ip = "" ;
        String host_name = "" ;
        try { 
            InetAddress ip = InetAddress.getLocalHost() ;
            server_ip = ip.getHostAddress() ;
            host_name = ip.getHostName() ;
  
        } catch (Exception e) { }
  
    
        model.addAttribute( "hash", hash_string ) ;
        model.addAttribute( "message", message ) ;
        model.addAttribute( "server",  host_name + "/" + server_ip ) ;

        return "gumball" ;

    }

    @PostMapping
    public String postAction(@Valid @ModelAttribute("command") GumballCommand command,  
                            @RequestParam(value="action", required=true) String action,
                            Errors errors, Model model, HttpServletRequest request) {
    
        log.info( "Action: " + action ) ;
        log.info( "Command: " + command ) ;
    
        String input_hash = command.getHash() ;
        String input_state = command.getState() ;
        String input_timestamp = command.getTimestamp() ;

        String input_text = input_state + "/" + input_timestamp ;
        String calculated_hash = hmac_sha256( key, input_text ) ;

        log.info( "Input Hash: " + input_hash ) ;
        log.info( "Valid Hash: " + calculated_hash ) ;

        // check message integrity
        if ( !input_hash.equals(calculated_hash) ) {
            throw new GumballServerError() ;
        }

        long ts1 = Long.parseLong( input_timestamp ) ;
        long ts2 = java.lang.System.currentTimeMillis() ;
        long diff = ts2 - ts1 ;

        log.info( "Input Timestamp: " + String.valueOf(ts1) ) ;
        log.info( "Current Timestamp: " + String.valueOf(ts2) ) ;
        log.info( "Timestamp Delta: " + String.valueOf(diff) ) ;

        // guard against replay attack
        if ( (diff/1000) > 1000 ) {
            throw new GumballServerError() ;
        }
        
        GumballMachine gm = new GumballMachine() ;
        gm.setState( input_state ) ;

        if ( action.equals("Insert Quarter") ) {
            gm.insertQuarter() ;
        }
        if ( action.equals("Turn Crank") ) {
            command.setMessage("") ;
            gm.turnCrank() ;
        } 
        
        String message = gm.toString() ;

        String state = gm.getState().getClass().getName() ;
        command.setState( state ) ;

        long ts_long = java.lang.System.currentTimeMillis() ;
        String ts_string = String.valueOf(ts_long) ;
        command.setTimestamp( ts_string ) ;

        String text = state + "/" + ts_string ;
        String hash_string = hmac_sha256( key, text ) ;
        command.setHash( hash_string ) ;

        String server_ip = "" ;
        String host_name = "" ;
        try { 
            InetAddress ip = InetAddress.getLocalHost() ;
            server_ip = ip.getHostAddress() ;
            host_name = ip.getHostName() ;  
        } catch (Exception e) { }
          
        model.addAttribute( "hash", hash_string ) ;
        model.addAttribute( "message", message ) ;
        model.addAttribute( "server",  host_name + "/" + server_ip ) ;
     
        if (errors.hasErrors()) {
            return "gumball";
        }

        return "gumball";
    }

}