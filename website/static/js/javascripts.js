$( document ).ready(function(){

    console.log( 'Document is ready, my lord.' );
    
    email = $('#email').val();
    
    $('#join').click(function(){
	   
	   $.ajax({
		   type: 'POST',
		   url: '/submit',
		   data: { email: email }
	   }).done(function(response){
		  
		  if(response == 'success') {
			  window.location.href('/thankyou');
		  }
		   
	   });
	    
    });

});