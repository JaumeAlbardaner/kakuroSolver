package Kakuro.Caselles;

import Kakuro.Caselles.Casella;

public class Negra extends Casella {
    private boolean info = false;
    private int[] valor; // Columna, Fila

    public boolean getInfo(){
        return info;
    }

    public int getValue(int a){
        if(a==0)
        return valor[0];
        else return valor[1];
    }

    public boolean isWhite(){
        return false;
    }

    public void setValor(int[] a){
        info=true;
        valor = new int[2];
        valor[0]=a[0];
        valor[1]=a[1];
    }

}