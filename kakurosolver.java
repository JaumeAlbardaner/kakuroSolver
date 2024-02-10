import Kakuro.Kakuro;

public class kakurosolver {
    public static void main(String[] args) {
        Kakuro kak = new Kakuro() ;
        //kak.readKakuro("Repository/demo.txt");
        kak.readKakuroargs();
        kak.solve();
        kak.printKakuro();
    }
}