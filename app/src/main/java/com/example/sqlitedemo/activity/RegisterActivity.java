package com.example.sqlitedemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqlitedemo.InputValidation;
import com.example.sqlitedemo.R;
import com.example.sqlitedemo.db.DatabaseHelper;
import com.example.sqlitedemo.model.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = RegisterActivity.this;

    EditText textInputEditTextName,textInputEditTextEmail,textInputEditTextPassword,textInputEditTextConfirmPassword;

    Button appCompatButtonRegister;

    TextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {
        textInputEditTextName=findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail=findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword=findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword=findViewById(R.id.textInputEditTextConfirmPassword);
        appCompatButtonRegister=findViewById(R.id.appCompatButtonRegister);
        appCompatTextViewLoginLink=findViewById(R.id.appCompatTextViewLoginLink);
    }

    private void  initListeners(){
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);
    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;

            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    private void postDataToSQLite(){
        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            user.setName(textInputEditTextName.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());

            databaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Toast.makeText(getApplicationContext(),getString(R.string.success_message),Toast.LENGTH_SHORT).show();

            emptyInputEditText();


        } else {
            // Snack Bar to show error message that record already exists
            Toast.makeText(getApplicationContext(),getString(R.string.error_valid_email_password),Toast.LENGTH_SHORT).show();
        }
    }
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}
