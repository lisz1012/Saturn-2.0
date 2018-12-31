$(document).ready(function(){
    $('#username').blur(validateUsername);
    $('#password').blur(validatePassword);
    $('#confirmed_password').blur(validateConfirmedPassword);
    $('#firstName').blur(validateFirstName);
    $('#lastName').blur(validateLastName);
    
    $("#username").bind("input propertychange",function(event){
    	validateUsername();
    	enableSignUpButton();
    });
    
    $("#password").bind("input propertychange",function(event){
    	validatePassword();
    	enableSignUpButton();
    });
    
    $("#confirmed_password").bind("input propertychange",function(event){
    	validateConfirmedPassword();
    	enableSignUpButton();
    });
    
    $("#firstName").bind("input propertychange",function(event){
    	validateFirstName();
    	enableSignUpButton();
    });
    
    $("#lastName").bind("input propertychange",function(event){
    	validateLastName();
    	enableSignUpButton();
    });
    
    $('#sign_up-btn').click(register);
    
	function validateUsername() {
		var username = $('#username').val();
		var isValid = /\w{8,16}/.test(username);
		if (isValid) {
			$('#username_msg').html("");
		} else {
			$('#username_msg').html('<font size="2" color="red">Please input a username with 8 - 16 characters or numbers.</font>');
		}
		enableSignUpButton();
	}

	function validatePassword() {
		var password = $('#password').val();
		var isValid = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,26}$/.test(password);
		if (isValid) {
			$('#password_msg').html("");
		} else {
			$('#password_msg').html('<font size="2" color="red">Please input a password with at least 8 characters containing ' +
					'at least 1 letter and 1 number.</font>');
		}
	}
	
	function validateConfirmedPassword () {
		var password = $('#password').val();
		var confirmed_password = $('#confirmed_password').val();
		if (password == confirmed_password) {
			$('#confirmed_password_msg').html("");
		} else {
			$('#confirmed_password_msg').html('<font size="2" color="red">Does not match the password.</font>');
		}
	}
	
	function validateFirstName () {
		var firstName = $('#firstName').val();
		var isValid = /^[A-Za-z]{1,255}$/.test(firstName);
		if (isValid) {
			$('#firstName_msg').html("");
		} else {
			$('#firstName_msg').html('<font size="2" color="red">First name is illegal.</font>');
		}
	}
	
	function validateLastName () {
		var lastName = $('#lastName').val();
		var isValid = /^[A-Za-z]{1,255}$/.test(lastName);
		if (isValid) {
			$('#lastName_msg').html("");
		} else {
			$('#lastName_msg').html('<font size="2" color="red">Last name is illegal.</font>');
		}
	}
	
	function enableSignUpButton () {
		var sholdEnable = /\w{8,16}/.test($('#username').val()) &&
		                  /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,26}$/.test($('#password').val()) &&
		                  $('#password').val() == $('#confirmed_password').val() &&
		                  /^[A-Za-z]{1,255}$/.test($('#firstName').val()) &&
		                  /^[A-Za-z]{1,255}$/.test($('#lastName').val());
		if (sholdEnable) {
			$("#sign_up-btn").removeAttr("disabled");
		} else {
			$("#sign_up-btn").attr("disabled", "disabled");
		}
	}
	
	function register() {
	    var username = $('#username').val();
	    var password = $('#password').val();
	    password = md5(username + md5(password));
	    var firstName = $('#firstName').val();
	    var lastName = $('#lastName').val();
	    
	    var url = './registration';
	    $.post(
	         url, 
	    	 {user_id : username,
	   	      password : password,
	   	      firstName : firstName,
	   	      lastName : lastName,},
	    	 function(data){
	    	    if (data.status == "success") {
	    	    	location.href = "welcome.html";
	    	    } else {
	    	    	location.href = "error.html";
	    	    }
	    	 }
	    );
	  }
});

