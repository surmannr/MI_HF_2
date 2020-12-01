import java.util.ArrayList;

public class Sor {
    public ArrayList<Integer> szulok_ertekek;       // Inputon kapott szülők értékei
    public ArrayList<Double> csomopont_ertekek;    // Inputon kapott szülök értékei mellett lévő csomóponti értékek

    public Sor(){
        szulok_ertekek = new ArrayList<>();
        csomopont_ertekek = new ArrayList<>();
    }

    public void addToSorSzuloErtek(int _szulok_ertek){
        szulok_ertekek.add(_szulok_ertek);
    }

    public void addToSorCsomopontErtek(double _csomopont_ertek){
        csomopont_ertekek.add(_csomopont_ertek);
    }
    public void sormegjelenit(){
        for (int i : szulok_ertekek){
            System.out.print(i);
            System.out.print('\t');
        }
        if(szulok_ertekek.size()!=0)System.out.print("|");
        for (double i : csomopont_ertekek){
            System.out.print('\t');
            System.out.print(i);
        }
        System.out.println();
    }
}
