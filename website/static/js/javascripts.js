$( document ).ready(function(){

    console.log( 'Document is ready, my lord.' );
    
    $('#join').click(function(){
	   
       email = $('#email').val();
	   $.ajax({
		   type: 'POST',
		   url: '/submit',
		   data: { email: email }
	   }).done(function(response){
		  
		  if(response == 'success') {
			  window.location.href = '/thankyou';
		  }
		   
	   });
	    
    });

});