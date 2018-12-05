package com.stosz.tms.model.aramex;

import java.io.Serializable;

/**
 * transaction -> aramexContent
 */
public class Transaction implements Serializable{

    private String Reference1;

    private String Reference2;

    private String Reference3;

    private String Reference4;

    private String Reference5;

    public String getReference1() {
        return Reference1;
    }

    public void setReference1(String reference1) {
        Reference1 = reference1;
    }

    public String getReference2() {
        return Reference2;
    }

    public void setReference2(String reference2) {
        Reference2 = reference2;
    }

    public String getReference3() {
        return Reference3;
    }

    public void setReference3(String reference3) {
        Reference3 = reference3;
    }

    public String getReference4() {
        return Reference4;
    }

    public void setReference4(String reference4) {
        Reference4 = reference4;
    }

    public String getReference5() {
        return Reference5;
    }

    public void setReference5(String reference5) {
        Reference5 = reference5;
    }
}
