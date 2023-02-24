# Spring Gumball


### Version 3.2

* Added Support for CSRF Protection
* Added Login Controller & Custom Login Page
	* Login Page & CSRF will not work behind a Load Balancer 
	* Need to use Spring Session + Redis
	* Workaround is to Enabled LB Sticky Sessions

Cross Site Request Forgery (CSRF)

* https://docs.spring.io/spring-security/reference/features/exploits/csrf.html
* https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html

Custom Login Form Example

* https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/form.html
* https://codepen.io/khadkamhn/pen/ZGvPLo




