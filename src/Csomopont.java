import java.util.ArrayList;

public class Csomopont {

    public int hany_erteke;                    // Hány értéket vehet fel

    public int erteke;                         // Változó értéke -1 kezdetben, majd később derül ki futás közben hogy mit kap

    public int aktualis_ertek;                 //

    public int szulok_szama;                   // Adott csomópont szüleinek száma

    public ArrayList<Integer> szulok_indexei;  // Szülők indexei

    public Igazsagtabla igazsagtabla;           // Igazságtábla a megfelelő értékekkel

    public Csomopont(){
        erteke = -1;
        szulok_indexei = new ArrayList<>();
        igazsagtabla = new Igazsagtabla(szulok_indexei);
    }
    public void megjelenit(){
        System.out.println("-------------------------------------");
        System.out.println("A csomopontnak: " + hany_erteke + " erteke lehet.");
        System.out.println("A szulei szama " + szulok_szama + " lehet. Ezek sorrendben: ");
        if(szulok_szama!=0)
        for(int i = 0; i<szulok_indexei.size();i++){
            System.out.println("\ta(z) " + szulok_indexei.get(i) + ". indexu szulo.");
        }
        igazsagtabla.tablamegjelenit();
        System.out.println("-------------------------------------");
    }
}
