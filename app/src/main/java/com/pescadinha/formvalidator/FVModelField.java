package com.pescadinha.formvalidator;

/**
 * Created by dengun on 29/08/16.
 */
public class FVModelField {

    private Object widget;
    private String emptyString;
    private String initialString;

    public FVModelField(Object widget, String emptyString, String initialString){
        this.widget = widget;
        this.emptyString = emptyString;
        this.initialString = initialString;
    }

    public FVModelField(Object widget){
        this.widget = widget;
        this.emptyString = "";
        this.initialString = "";
    }





    public Object getWidget() {
        return widget;
    }

    public void setWidget(Object widget) {
        this.widget = widget;
    }

    public String getEmptyString() {
        return emptyString;
    }

    public void setEmptyString(String emptyString) {
        this.emptyString = emptyString;
    }

    public String getInitialString() {
        return initialString;
    }

    public void setInitialString(String initialString) {
        this.initialString = initialString;
    }
}
