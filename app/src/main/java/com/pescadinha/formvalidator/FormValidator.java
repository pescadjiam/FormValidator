package com.pescadinha.formvalidator;

import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dengun on 29/08/16.
 */
public class FormValidator {

    //Interface for form validation
    public interface IFormValidation {
        void onPasswordFieldError();
        void onEmailValidationError();
        void onRequiredFieldsError();
        void onValidationSuccessful();
    }

    private static final String TAG = "FormValidator";

    private IFormValidation iFormValidation;

    //REQUIRED FIELDS VARs
    private FVModelField[] requiredFields;
    private boolean animateError = false;

    //EMAIL VARs
    private EditText emailField;
    private String emailPattern;

    public FormValidator(IFormValidation iFormValidation){
        this.iFormValidation = iFormValidation;
    }

    //REQUIRED FIELDS METHODS
    public FormValidator setRequiredFields(FVModelField[] requiredFields){
        this.requiredFields = requiredFields;
        return this;
    }

    public FormValidator setRequiredFields(FVModelField[] requiredFields, boolean animateError){
        this.requiredFields = requiredFields;
        this.animateError = animateError;
        return this;
    }

    //EMAIL FIELDS METHODS
    public FormValidator setEmailField(EditText emailField) {
        this.emailField = emailField;
        return this;
    }

    public FormValidator setEmailField(EditText emailField, String emailPattern) {
        this.emailField = emailField;
        this.emailPattern = emailPattern;
        return this;
    }

    public FormValidator setPasswordField() {
        return this;
    }

    public void validate() {
        if (iFormValidation == null) {
            try {
                throw new NoSuchMethodException("IFormValidation implementation not found, please implement");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //1st check for required fields
        if(!validateRequiredFields()){
            iFormValidation.onRequiredFieldsError();
            return;
        }

        //2nd check for valid email if exists
        if(!validateEmailField()) {
            iFormValidation.onEmailValidationError();
            return;
        }

        iFormValidation.onValidationSuccessful();

    }

    private boolean validateRequiredFields() {
        boolean isValid = true;

        ArrayList<FVModelField> emptyFields = new ArrayList<>();

        if (requiredFields != null) {
            for (FVModelField field : requiredFields) {
                if(field.getWidget() instanceof EditText) {
                    String widgetText = ((EditText) field.getWidget()).getText().toString();
                    if(widgetText.isEmpty()
                        || widgetText.equals(field.getInitialString())
                        || widgetText.equals(field.getEmptyString())){
                        isValid = false;
                    }
                } else if(field.getWidget() instanceof CheckBox){
                    CheckBox widget = (CheckBox)field.getWidget();
                    if(!widget.isChecked()){
                        isValid = false;
                    }
                } /*else if(field.getWidget() instanceof RadioButton){
                    RadioButton widget = (RadioButton)field.getWidget();
                    if(!widget.isChecked()){
                        isValid = false;
                    }
                }*/ else if (field.getWidget() instanceof ToggleButton) {
                    ToggleButton widget = (ToggleButton)field.getWidget();
                    if(!widget.isChecked()){
                        isValid = false;
                    }
                } else if(field.getWidget() instanceof Button) {
                    String widgetText = ((Button) field.getWidget()).getText().toString();
                    if (widgetText.isEmpty()
                            || widgetText.equals(field.getInitialString())
                            || widgetText.equals(field.getEmptyString())) {
                        isValid = false;
                    }
                }

                if (animateError) {
                    emptyFields.add(field);
                }
            }
        }

        if (animateError) {
            for(FVModelField field : emptyFields){
                Log.d(TAG, field.getWidget().toString());
            }
        }

        return isValid;
    }

    private boolean validateEmailField() {
        Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailField.getText().toString());

        if(matcher.matches()){
            return true;
        }

        return false;
    }
}
