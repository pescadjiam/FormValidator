package com.pescadinha.formvalidator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements
    FormValidator.IFormValidation{

    private final static String TAG = "MainActivity";

    private EditText nameField, passwordField, emailField;
    private ToggleButton toggleButton;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameField = (EditText) findViewById(R.id.nameField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        emailField = (EditText) findViewById(R.id.emailField);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
    }

    public void onBookClick(View view) {
        new FormValidator(this)
                .setRequiredFields(
                        new FVModelField[]{
                                new FVModelField(nameField),
                                new FVModelField(passwordField),
                                new FVModelField(emailField),
                                new FVModelField(toggleButton),
                                new FVModelField(checkBox)
                        }
                )
                .setEmailField(emailField)
                .validate();
    }

    @Override
    public void onPasswordFieldError() {

    }

    @Override
    public void onEmailValidationError() {
        Log.d(TAG, "Email failed");
    }

    @Override
    public void onRequiredFieldsError() {
        Log.d(TAG, "REQUIRED FIELDS ERROR");
    }

    @Override
    public void onValidationSuccessful() {
        Log.d(TAG, "yew success");
    }
}
