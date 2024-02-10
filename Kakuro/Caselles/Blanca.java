package Kakuro.Caselles;

import Kakuro.Caselles.Casella;

public class Blanca extends Casella {
    private int value;
    private boolean info = false;

    public boolean getInfo(){
        return info;
    }

    public int getValue(int b){
        return value;
    }

    public boolean isWhite(){
        return true;
    }

    public void setValor(int[] a){
        info=true;
        value = a[0];
    }
}