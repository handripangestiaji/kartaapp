package com.karta;

import com.nineoldandroids.animation.ObjectAnimator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
 
public class Login extends Activity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.login);         
        
    	((ImageView) findViewById(R.id.btn_signup)).setOnClickListener(btnClick);
    	((ImageView) findViewById(R.id.btn_skip)).setOnClickListener(btnClick);
    	((ImageView) findViewById(R.id.btn_facebook)).setOnClickListener(btnClick);
    	((ImageView) findViewById(R.id.btn_google)).setOnClickListener(btnClick);
    }
    
    public EditText _username;
    public EditText _password;
    
    private View.OnClickListener btnClick = new OnClickListener() {
		
        @Override
		public void onClick(View v) {
                switch(v.getId()){
                    case R.id.btn_signup:{                            
                        ObjectAnimator animation1 = ObjectAnimator.ofFloat(((ImageView) findViewById(R.id.btn_signup)), "alpha", 1f, 0.2f);
                        animation1.setDuration(800);
                        animation1.start();
                        animation1 = ObjectAnimator.ofFloat(((ImageView) findViewById(R.id.btn_signup)), "alpha", 0.2f, 1f);
                        animation1.setDuration(800);
                        animation1.setStartDelay(100);
                        animation1.start();
                        
                        _username = ((EditText) findViewById(R.id.username));
                        _password = ((EditText) findViewById(R.id.password));

                        String username = _username.getText().toString();
                        String password = _password.getText().toString();
                        
                        if(username.equals("admin") && password.equals("admin")){
	                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
	                        Editor editor = pref.edit();
	                        
	                        editor.putBoolean("isLogin", true); 
	                        editor.putString("kartaUsername", username);
	                        
	                        editor.commit();
	                        
	                        Intent i = new Intent(Login.this, Home.class);
	                        startActivity(i); 

	                        finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Username and password not valid", Toast.LENGTH_LONG).show();
                        }
                        
                    	break;
                    }
                    case R.id.btn_facebook:{                                                    
                        ObjectAnimator animation1 = ObjectAnimator.ofFloat(((ImageView) findViewById(R.id.btn_facebook)), "alpha", 1f, 0.2f);
                        animation1.setDuration(800);
                        animation1.start();
                        animation1 = ObjectAnimator.ofFloat(((ImageView) findViewById(R.id.btn_facebook)), "alpha", 0.2f, 1f);
                        animation1.setDuration(800);
                        animation1.setStartDelay(100);
                        animation1.start();
                        
                        Toast.makeText(getApplicationContext(), "Login facebook not working properly", Toast.LENGTH_LONG).show();
                        
                    	break;
                    }
                    case R.id.btn_google:{                                                    
                        ObjectAnimator animation1 = ObjectAnimator.ofFloat(((ImageView) findViewById(R.id.btn_google)), "alpha", 1f, 0.2f);
                        animation1.setDuration(800);
                        animation1.start();
                        animation1 = ObjectAnimator.ofFloat(((ImageView) findViewById(R.id.btn_google)), "alpha", 0.2f, 1f);
                        animation1.setDuration(800);
                        animation1.setStartDelay(100);
                        animation1.start();
                        
                        Toast.makeText(getApplicationContext(), "Login google not working properly", Toast.LENGTH_LONG).show();
                        
                    	break;
                    }
                    case R.id.btn_skip:{                                                    
                        ObjectAnimator animation1 = ObjectAnimator.ofFloat(((ImageView) findViewById(R.id.btn_signup)), "alpha", 1f, 0.2f);
                        animation1.setDuration(800);
                        animation1.start();
                        animation1 = ObjectAnimator.ofFloat(((ImageView) findViewById(R.id.btn_skip)), "alpha", 0.2f, 1f);
                        animation1.setDuration(800);
                        animation1.setStartDelay(100);
                        animation1.start();

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        Editor editor = pref.edit();
                        
                        editor.putBoolean("isLogin", true); 
                        editor.putString("kartaUsername", "anynomous");
                        
                        editor.commit();

                        Intent i = new Intent(Login.this, Home.class);
                        startActivity(i); 

                        finish();
                        
                    	break;
                    }
                }	                
	        }
		}; 
    
}