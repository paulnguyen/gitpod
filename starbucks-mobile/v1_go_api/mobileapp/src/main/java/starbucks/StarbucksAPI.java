package starbucks;

import java.text.NumberFormat ;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;


/* 

    REST CLIENT:  

    http://kong.github.io/unirest-java
    https://www.baeldung.com/unirest
    http://stleary.github.io/JSON-java/index.html
    https://www.programcreek.com/java-api-examples/?api=com.mashape.unirest.http.Unirest
    http://javadox.com/com.mashape.unirest/unirest-java/1.4.9/overview-summary.html

*/


class StarbucksAPI {

	private String apiurl = "" ;
    private String apikey = "" ;
    private String register = "" ;

	public StarbucksAPI() {
		Device theDevice = Device.getInstance() ;
       	apiurl = theDevice.getProps("apiurl") ;
        apikey = theDevice.getProps("apikey") ;
        register = theDevice.getProps("register") ;
	}

	public void ping() {
		try {
            String body = 
                Unirest.get( apiurl + "/ping" )
                    .header( "apikey", apikey )
                    .asString()
                    .getBody() ;
            System.err.println( body ) ;
        } catch (Exception e) {
            System.err.println( e ) ;
        }
	}

	public Card newCard() throws Exception {
        try {
            HttpResponse<JsonNode> response = Unirest.post( apiurl + "/cards")
                .header( "apikey", apikey )
                .asJson() ;
            int status_code = response.getStatus() ;
            String status_text = response.getStatusText() ;
            System.err.println( "Status: " + String.valueOf(status_code) + " " + status_text ) ;
            JSONObject json = response.getBody().getObject() ;
            System.err.println( json.toString() ) ;
            Card card = new Card() ;
            card.balance = 0.0 ;
            card.cardId = json.getString( "CardNumber" ) ;
            card.cardCode = json.getString( "CardCode" ) ;
            card.balance = json.getDouble( "Balance" )  ;     
            if ( card.balance == 0.0 )
            	throw new Exception("API Call Failed!") ;
            return card ;
        } catch (Exception e) {
            throw new Exception(e) ;
        }		
	}

	public void getCards() {
		try {
            String body = 
                Unirest.get( apiurl + "/cards" )
                    .header( "apikey", apikey )
                    .asString()
                    .getBody() ;
            System.err.println( body ) ;
        } catch (Exception e) {
            System.err.println( e ) ;
        }
	}

	class Card {
    	public double balance = 0.00f ; // default
    	public String cardId = "000000000" ;
    	public String cardCode = "000" ;
        public String getFormatedBalance() {
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            return formatter.format(balance) ; 
        }
    	public String toString() {
    		return  "Card ID: [" + cardId + "] Code: [" + cardCode + "] Balance: [" + balance + "]"  ;
    	}
	}

	public Card getCard(String cardnum) throws Exception {
        try {
            HttpResponse<JsonNode> response = Unirest.get( apiurl + "/cards/"+cardnum )
                .header( "apikey", apikey )
                .asJson() ;
            int status_code = response.getStatus() ;
            String status_text = response.getStatusText() ;
            System.err.println( "Status: " + String.valueOf(status_code) + " " + status_text ) ;                
            JSONObject json = response.getBody().getObject() ;
            System.err.println( json.toString() ) ;
            Card card = new Card() ;
            card.balance = 0.0 ;
            card.cardId = json.getString( "CardNumber" ) ;
            card.cardCode = json.getString( "CardCode" ) ;
            card.balance = json.getDouble( "Balance" )  ;     
            if ( card.balance == 0.0 )
            	throw new Exception("API Call Failed!") ;
            return card ;
        } catch (Exception e) {
            throw new Exception(e) ;
        }		
	}


	public void activateCard(String num, String code) throws Exception {
        try {
            HttpResponse<JsonNode> response = Unirest.post( apiurl + "/card/activate/" + num + "/" + code )
                .header( "apikey", apikey )
                .asJson() ;
            int status_code = response.getStatus() ;
            String status_text = response.getStatusText() ;
            System.err.println( "Status: " + String.valueOf(status_code) + " " + status_text ) ;
            JSONObject json = response.getBody().getObject() ;
            System.err.println( json.toString() ) ;
            if ( status_code != 200 )
            	throw new Exception( "Card Activation Failed!") ;
        } catch (Exception e) {
            throw new Exception(e) ;
        }		
	}


	public void newOrder(String regid) {

		/* 

		 {
			"Drink": "Latte",
			"Milk":  "Whole",
			"Size":  "Grande"
		 }  

		var DRINK_OPTIONS = [ "Caffe Latte", "Caffe Americano", "Caffe Mocha", "Espresso", "Cappuccino" ];
		var MILK_OPTIONS  = [ "Whole Milk", "2% Milk", "Nonfat Milk", "Almond Milk", "Soy Milk" ];
		var SIZE_OPTIONS  = [ "Short", "Tall", "Grande", "Venti", "Your Own Cup" ];

		*/

	    try {
	    	String drink = "Latte" ;
	    	String milk = "Whole" ;
	    	String size = "Grande" ;
	    	String payload = "{\"Drink\":\""+drink+"\", \"Milk\":\""+milk+"\",\"Size\": \""+size+"\"}" ;
	    	System.err.println( "payload: " + payload ) ;
	        HttpResponse<JsonNode> response = Unirest.post( apiurl + "/order/register/" + regid )
	            .header( "apikey", apikey )
	            .body(payload)
	            .asJson() ;
	        int status_code = response.getStatus() ;
	        String status_text = response.getStatusText() ;
	        System.err.println( "Status: " + String.valueOf(status_code) + " " + status_text ) ;
	        JSONObject json = response.getBody().getObject() ;
	        System.err.println( json.toString() ) ;
	    } catch (Exception e) {
	        System.err.println( e ) ;
	    }	
	}


	public void getOrder( String regid ) {
        try {
            HttpResponse<JsonNode> response = Unirest.get( apiurl + "/order/register/"+regid )
                .header( "apikey", apikey )
                .asJson() ;
            int status_code = response.getStatus() ;
            String status_text = response.getStatusText() ;
            System.err.println( "Status: " + String.valueOf(status_code) + " " + status_text ) ;                
            JSONObject json = response.getBody().getObject() ;
            System.err.println( json.toString() ) ;
        } catch (Exception e) { }		
	}

	public void clearOrder( String regid ) {
        try {
            HttpResponse<JsonNode> response = Unirest.delete( apiurl + "/order/register/"+regid )
                .header( "apikey", apikey )
                .asJson() ;
            int status_code = response.getStatus() ;
            String status_text = response.getStatusText() ;
            System.err.println( "Status: " + String.valueOf(status_code) + " " + status_text ) ;                
            JSONObject json = response.getBody().getObject() ;
            System.err.println( json.toString() ) ;
        } catch (Exception e) { }		
	}


	public Card payForOrder( String regid , String cardnum ) throws Exception {
        try {

            HttpResponse<JsonNode> response = 
                Unirest.post( apiurl + "/order/register/"+regid+"/pay/"+cardnum )
                    .header( "apikey", apikey )
                    .asJson() ;
            JSONObject json = response.getBody().getObject() ;

            int status_code = response.getStatus() ;
            String status_text = response.getStatusText() ;
            System.err.println( json.toString() ) ;

            String status = json.getString( "Status" ) ;

            if ( status.equals("") ) {
	            Card card = new Card() ;
	            card.balance = 0.0 ;
	            card.cardId = json.getString( "CardNumber" ) ;
	            card.cardCode = json.getString( "CardCode" ) ;
	            card.balance = json.getDouble( "Balance" )  ;  
	            return card ;           	
            } else {
            	 System.err.println( status ) ;
            	throw new Exception( status ) ;
            }

        } catch (Exception e) { 
        	throw new Exception( "Runtime Error.") ;
        }		
	}


	public void getOrders() {
		try {
            String body = 
                Unirest.get( apiurl + "/orders" )
                    .header( "apikey", apikey )
                    .asString()
                    .getBody() ;
            System.err.println( body ) ;
        } catch (Exception e) {
            System.err.println( e ) ;
        }
	}

	public void clearOrders() {
        try {
            HttpResponse<JsonNode> response = Unirest.delete( apiurl + "/orders" )
                .header( "apikey", apikey )
                .asJson() ;
            int status_code = response.getStatus() ;
            String status_text = response.getStatusText() ;
            System.err.println( "Status: " + String.valueOf(status_code) + " " + status_text ) ;                
            JSONObject json = response.getBody().getObject() ;
            System.err.println( json.toString() ) ;
        } catch (Exception e) { }		
	}


	public void clearCards() {
        try {
            HttpResponse<JsonNode> response = Unirest.delete( apiurl + "/cards" )
                .header( "apikey", apikey )
                .asJson() ;
            int status_code = response.getStatus() ;
            String status_text = response.getStatusText() ;
            System.err.println( "Status: " + String.valueOf(status_code) + " " + status_text ) ;                
            JSONObject json = response.getBody().getObject() ;
            System.err.println( json.toString() ) ;
        } catch (Exception e) { }		
	}


}




/*

GET 	/ping
		Ping Health Check.

		{
		  "Test": "Starbucks API version 1.0 alive!"
		}		

GET 	/cards 
		Get a list of Starbucks Cards (along with balances).

		[
		  {
		    "CardNumber": "498498082",
		    "CardCode": "425",
		    "Balance": 20,
		    "Activated": false,
		    "Status": ""
		  },
		  {
		    "CardNumber": "627131848",
		    "CardCode": "547",
		    "Balance": 20,
		    "Activated": false,
		    "Status": ""
		  }
		]		

POST 	/cards
		Create a new Starbucks Card.

		{
		  "CardNumber": "498498082",
		  "CardCode": "425",
		  "Balance": 20,
		  "Activated": false,
		  "Status": "New Card."
		}

GET 	/cards/{num}
		Get the details of a specific Starbucks Card.

		{
		  "CardNumber": "627131848",
		  "CardCode": "547",
		  "Balance": 20,
		  "Activated": false,
		  "Status": ""
		}		

POST 	/card/activate/{num}/{code}
		Activate Card 

		{
		  "CardNumber": "627131848",
		  "CardCode": "547",
		  "Balance": 20,
		  "Activated": true,
		  "Status": ""
		}

POST    /order/register/{regid}
        Create a new order. Set order as "active" for register.

        Request:

	    {
	      "Drink": "Latte",
	      "Milk":  "Whole",
	      "Size":  "Grande"
	    }         

	    Response:

		{
		  "Drink": "Latte",
		  "Milk": "Whole",
		  "Size": "Grande",
		  "Total": 2.413125,
		  "Status": "Ready for Payment."
		}	    

GET     /order/register/{regid}
        Request the current state of the "active" Order.

		{
		  "Drink": "Latte",
		  "Milk": "Whole",
		  "Size": "Grande",
		  "Total": 2.413125,
		  "Status": "Ready for Payment."
		}

DELETE  /order/register/{regid}
        Clear the "active" Order.

		{
		  "Status": "Active Order Cleared!"
		}

POST    /order/register/{regid}/pay/{cardnum}
        Process payment for the "active" Order. 

        Response: (with updated card balance)

		{
		  "CardNumber": "627131848",
		  "CardCode": "547",
		  "Balance": 15.17375,
		  "Activated": true,
		  "Status": ""
		}

GET     /orders
        Get a list of all active orders (for all registers)

		{
		  "5012349": {
		    "Drink": "Latte",
		    "Milk": "Whole",
		    "Size": "Grande",
		    "Total": 4.82625,
		    "Status": "Paid with Card: 627131848 Balance: $15.17."
		  }
		}

DELETE 	/cards
		Delete all Cards (Use for Unit Testing Teardown)

		{
		  "Status": "All Cards Cleared!"
		}

DELETE 	/orders
		Delete all Orders (Use for Unit Testing Teardown)

		{
		  "Status": "All Orders Cleared!"
		}

*/