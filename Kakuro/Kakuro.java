package Kakuro;
import Kakuro.Caselles.Blanca;
import Kakuro.Caselles.Casella;
import Kakuro.Caselles.Negra;

//Necessaris per a llegir el kakuro de fitxer
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Kakuro{
    private Casella[][] grid;
    private boolean complete = false;
    private int m,n;
    //Generador buit
    public Kakuro(){}
    //Generador a partir de graella
    private Kakuro(Casella[][] aux){
        grid =aux;
    }
    public Casella[][] getGrid(){
        return grid;
    }
    public boolean isComplete(){
        return complete;
    }
    public void completed(){
        complete=true;
    }

    public void solve(){
        Kakuro aux =this.emplace_number(0,0);
        grid = aux.getGrid();
        complete=aux.isComplete();
    }
    //TODO: Canviar la estructura de les funcions que passem per un objecte
    //TODO: Si una casella ja te un nombre cal comprovar restriccions
    private Kakuro emplace_number(int i, int j){
        //Si encara no té un valor i és blanca
        if(grid[i][j].isWhite() ){
            //Vectors per a saber quins nombres apareixen
            int[] possibles_h =new int[10];
            int[] possibles_v =new int[10];
            //Comptadors per a sumer el que portem ja
            int sum_h=0, sum_v=0;
            //Comencem buscant el maxim horitz cap esquerra
            int a=j-1;
            while (grid[i][a].isWhite()) {
                int aux= (grid[i][a]).getValue(1);
                possibles_h[aux]++;
                sum_h+=aux;
                --a;
            }
            int max_h=grid[i][a].getValue(1);
            //Fem el mateix però verticalment cap amunt
            a=i-1;
            while (grid[a][j].isWhite()) {
                int aux= (grid[a][j]).getValue(1);
                possibles_v[aux]++;
                sum_v+=aux;
                --a;
            }
            int max_v=grid[a][j].getValue(0);
            //Ens cal acabar de veure quins valors podem posar
            //Comencem mirant dreta
            /*
            a=j+1;
            while (a<grid[i].length && grid[i][a].isWhite()) {
                int aux= (grid[i][a]).getValue(1);
                possibles_h[aux]++;
                sum_h+=aux;
                ++a;
            }
            //Ara mirem avall
            a=i+1;
            while (a<grid.length && grid[a][j].isWhite()) {
                int aux= (grid[a][j]).getValue(1);
                possibles_v[aux]++;
                sum_v+=aux;
                ++a;
            }
            */
            for(a=1;a<10;++a) {
                Kakuro aux = new Kakuro(grid);
                //Si encara no hem posat el nombre ni hem trobat una solucio
                if (possibles_h[a] == 0 && possibles_v[a] == 0) {
                    //Ara si el de la dreta o el d'avall es negre hem de comprovar que es compleixin les restriccions
                    boolean compleix = true;
                    if (j + 1 == grid[i].length || !grid[i][j + 1].isWhite())
                        compleix = (sum_h + a == max_h);
                    if (i + 1 == grid.length || !grid[i + 1][j].isWhite())
                        compleix = compleix && (sum_v + a == max_v);
                    if (compleix && (a + sum_h) <= max_h && (a + sum_v) <= max_v) {//Si no ens petem les restriccions
                        aux.setValue(i, j, a);
                        //Si estem al final del kakuro
                        if (j + 1 == grid[i].length && i + 1 == grid.length) aux.completed();
                        else if (j + 1 == grid[i].length) aux = aux.emplace_number(i + 1, 0);
                        else aux = aux.emplace_number(i, j + 1);
                    }
                }
                if (aux.isComplete()) return aux;
            }
            return this;
        }
        //Si la casella no és blanca o ja te resposta
        else if (j+1==grid[i].length && i + 1 == grid.length) {
            complete = true;
            return this;
        }
        else if(j + 1 == grid[i].length) return this.emplace_number(i+1,0);
        else return this.emplace_number(i, j+1);
    }

    private void setValue(int i, int j, int value){
        int [] valor= new int[1];
        valor[0]=value;
        grid[i][j].setValor(valor);
    }

    public void readKakuro(String file) {
        try{
            File object = new File(file);
            Scanner myReader = new Scanner(object);
            String [] dim = myReader.next().split(",");
            n= Integer.parseInt(dim[0]);
            m= Integer.parseInt(dim[1]);
            grid = new Casella [n][m];
            for(int i=0;i<n;++i){
                String [] line = myReader.next().split(",");
                for(int j=0;j<m;++j){
                    char [] aux = line[j].toCharArray();
                    if(aux[0] =='*') grid[i][j] = new Negra();
                    else if(aux[0] =='?') grid[i][j] = new Blanca();
                    else if(aux[0] == 'C'){
                        grid[i][j] = (Negra) new Negra();
                        int [] valors =new int[2];//Li passem els dos valors
                        int it=1;
                        while((it< aux.length) && (aux[it] != 'F')) ++it;
                        valors[0] = Integer.parseInt(line[j].substring(1,it));
                        if(it<aux.length) valors[1] = Integer.parseInt(line[j].substring(it+1,aux.length));
                        grid[i][j].setValor(valors);
                    }
                    else if(aux[0] =='F'){
                        grid[i][j] = (Negra) new Negra();
                        int [] valors =new int[2];
                        valors[1] = Integer.parseInt(line[j].substring(1,aux.length));
                        grid[i][j].setValor(valors);
                    }
                    else{
                        grid[i][j] = new Blanca();
                        int[] digit = new int[1];
                        digit[0]= Integer.parseInt(line[j]);
                        grid[i][j].setValor(digit);
                    }

                }
            }


        }
        catch  (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();;
        }
    }

    public void readKakuroargs() {
            Scanner myReader = new Scanner(System.in);
            String [] dim = myReader.next().split(",");
            n= Integer.parseInt(dim[0]);
            m= Integer.parseInt(dim[1]);
            grid = new Casella [n][m];
            for(int i=0;i<n;++i){
                String [] line = myReader.next().split(",");
                for(int j=0;j<m;++j){
                    char [] aux = line[j].toCharArray();
                    if(aux[0] =='*') grid[i][j] = new Negra();
                    else if(aux[0] =='?') grid[i][j] = new Blanca();
                    else if(aux[0] == 'C'){
                        grid[i][j] = (Negra) new Negra();
                        int [] valors =new int[2];//Li passem els dos valors
                        int it=1;
                        while((it< aux.length) && (aux[it] != 'F')) ++it;
                        valors[0] = Integer.parseInt(line[j].substring(1,it));
                        if(it<aux.length) valors[1] = Integer.parseInt(line[j].substring(it+1,aux.length));
                        grid[i][j].setValor(valors);
                    }
                    else if(aux[0] =='F'){
                        grid[i][j] = (Negra) new Negra();
                        int [] valors =new int[2];
                        valors[1] = Integer.parseInt(line[j].substring(1,aux.length));
                        grid[i][j].setValor(valors);
                    }
                    else{
                        grid[i][j] = new Blanca();
                        int[] digit = new int[1];
                        digit[0]= Integer.parseInt(line[j]);
                        grid[i][j].setValor(digit);
                    }

                }
            }
    }

    public void printKakuro() {
        if(complete) System.out.println("1");
        else System.out.println("0");
        System.out.println(m+","+n);
        for (Casella[] casellas : grid) {
            for (int j = 0; j < casellas.length; ++j) {
                String impr;
                if (casellas[j].isWhite()) {
                    impr = String.valueOf(casellas[j].getValue(0));
                } else {
                    impr = "";
                    if (!casellas[j].getInfo()) impr = "*"; // Si la cel·la es de tipus buit
                    else { // Si proporciona info
                        if (casellas[j].getValue(0)!= 0) //Afegim el valor de Col
                            impr = "C" + String.valueOf(casellas[j].getValue(0));
                        if (casellas[j].getValue(1) != 0)  //Afegim el valor de Fila
                            impr += "F" + String.valueOf(casellas[j].getValue(1));
                    }
                }
                if (j + 1 < casellas.length) impr += ",";
                System.out.print(impr);
            }
            System.out.println();
        }
    }



}
