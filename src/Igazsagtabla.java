import java.util.ArrayList;

public class Igazsagtabla {

    public ArrayList<Integer> szulo_indexek;        // Tartalmazza a szülő indexeket

    public ArrayList<Sor> sorok;                    // Sorokat tartalmazó lista

    Igazsagtabla(ArrayList<Integer> _szulo_indexek){
        szulo_indexek = _szulo_indexek;
        sorok = new ArrayList<>();
    }

    public void addSor(Sor uj){
        sorok.add(uj);
    }

    public void tablamegjelenit(){
        System.out.println("Igazsagtabla:");
        for (Sor s : sorok){
            s.sormegjelenit();
        }
    }
}
