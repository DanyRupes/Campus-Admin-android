package admin.example.com.campus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import admin.example.com.nav.R;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "LoginActivity";
    private Button btnLogin, btnLinkToSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private EditText loginInputEmail, loginInputPassword;
    private TextInputLayout loginInputLayoutEmail, loginInputLayoutPassword;
    private  ImageView imViewAndroid;
    //private ProgressDialog progress = new ProgressDialog(LoginActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();


        if(auth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening Main activity
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        }

        loginInputLayoutEmail = (TextInputLayout) findViewById(R.id.login_input_layout_email);
        loginInputLayoutPassword = (TextInputLayout) findViewById(R.id.login_input_layout_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        loginInputEmail = (EditText) findViewById(R.id.login_input_email);
        loginInputPassword = (EditText) findViewById(R.id.login_input_password);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLinkToSignUp = (Button) findViewById(R.id.btn_link_signup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        findViewById(R.id.frgtpswd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
            }
        });

        btnLinkToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                finish();
                startActivity(i);
            }
        });
    }

    /**
     * Validating form
     */
    private void submitForm() {
        String email = loginInputEmail.getText().toString().trim();
        String password = loginInputPassword.getText().toString().trim();

        if(!checkEmail()) {
            return;
        }
        if(!checkPassword()) {
            return;
        }
        loginInputLayoutEmail.setErrorEnabled(false);
        loginInputLayoutPassword.setErrorEnabled(false);

        progressBar.setVisibility(View.VISIBLE);
        final ProgressDialog progress = new ProgressDialog(LoginActivity.this);
        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, Log a message to the LogCat. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressBar.setVisibility(View.GONE);

                        if (!task.isSuccessful()) {
                            // there was an error
                             Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();

                        } else {
                            /*
                            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                            progress.setMessage("Logging In :) ");
                            progress.setTitle("Log Status"); // Setting Title
                            progress.show();
                            progress.setCancelable(false);
                            new Thread(new Runnable() {
                                public void run() {
                                    try {
                                        Thread.sleep(100000);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    progress.dismiss();
                                }
                            }).start(); */
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    private boolean checkEmail() {
        String email = loginInputEmail.getText().toString().trim();
        if (email.isEmpty() || !isEmailValid(email)) {

            loginInputLayoutEmail.setErrorEnabled(true);
            loginInputLayoutEmail.setError(getString(R.string.err_msg_email));
            loginInputEmail.setError(getString(R.string.err_msg_required));
            requestFocus(loginInputEmail);
            return false;
        }
        loginInputLayoutEmail.setErrorEnabled(false);
        return true;
    }

    private boolean checkPassword() {

        String password = loginInputPassword.getText().toString().trim();
        if (password.isEmpty() || !isPasswordValid(password)) {

            loginInputLayoutPassword.setError(getString(R.string.err_msg_password));
            loginInputPassword.setError(getString(R.string.err_msg_required));
            requestFocus(loginInputPassword);
            return false;
        }
        loginInputLayoutPassword.setErrorEnabled(false);
        return true;
    }

    private static boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isPasswordValid(String password){
        return (password.length() >= 6);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}