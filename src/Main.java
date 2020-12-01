import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static int Nv; // Csomópontok száma

    static ArrayList<Csomopont> csomopont_lista = new ArrayList<>();    // Csomópontok listája

    static int Ne; // Ekvidencia-változók száma

    static ArrayList<EkvidenciaValtozo> ekv_valtozok = new ArrayList<>(); // Ekvidencia-változók

    static int celvaltozo_index;    // Célváltozó indexe

    public static void main(String[] args){
        //System.out.println("Teszt");
        Scanner sc = new Scanner(System.in);
        String a = sc.nextLine();
        Nv = Integer.parseInt(a);
        //System.out.println("A csomopontok szama: " + Nv);

        for (int i = 0; i<Nv;i++){
            addCsomoPont(sc);
            csomopont_lista.get(i).megjelenit();
        }
        a = sc.nextLine();
        Ne = Integer.parseInt(a);
        //System.out.println("-------------------------------------");
        //System.out.println("Az ekvidencia-valtozok szama: " + Ne);
        for (int i = 0; i<Ne;i++){
            String[] temp = sc.nextLine().split("\t");
            int ind = Integer.parseInt(temp[0]);
            int ert = Integer.parseInt(temp[1]);
            ekv_valtozok.add(new EkvidenciaValtozo(ind,ert));
            //System.out.println("\t Az ekvidencia-valtozo indexe: " + ekv_valtozok.get(i).index + " es erteke: " + ekv_valtozok.get(i).erteke);
        }
        //System.out.println("-------------------------------------");
        a = sc.nextLine();
        celvaltozo_index = Integer.parseInt(a);
        //System.out.println("A celvaltozo indexe: " + celvaltozo_index);
        int celvaltozo_ertekek = csomopont_lista.get(celvaltozo_index).hany_erteke;

        van_erteke();
        ArrayList<Double> szamlalok = new ArrayList<>();
        for(int i = 0; i<celvaltozo_ertekek; i++){
            double ertek = 0;
            ArrayList<List<Integer>> erteklista_permutaciohoz = new ArrayList<>();
            ArrayList<Integer> ertekek_permutaciohoz =  new ArrayList<>();

            for(int ind = 0; ind<Nv;ind++){
                // Ha az inputon az ekvidenciaváltozóra volt értéke egy adott indexen, akkor az kerül be a listába
                if(ind == celvaltozo_index){
                        ertekek_permutaciohoz.add(i);
                } else {
                    if(csomopont_lista.get(ind).erteke != -1){
                        ertekek_permutaciohoz.add(csomopont_lista.get(ind).erteke);
                    }
                    else {
                        for(int j = 0; j<csomopont_lista.get(ind).hany_erteke; j++){
                            ertekek_permutaciohoz.add(j);
                        }
                    }
                }

                erteklista_permutaciohoz.add((List<Integer>) ertekek_permutaciohoz.clone());
                ertekek_permutaciohoz.clear();
            }
            ArrayList<List<Integer>> lists = IterTools.generate(erteklista_permutaciohoz);
            /*for(List<Integer> an : lists){
                for(Integer bn : an){
                    System.out.print(bn + " ");
                }
                System.out.println();
            }
            System.out.println("\n----------");*/
            // Végigmegyek a listán ami a permutációkat tartalmazza és minden egyes permutációhoz kiszámolom az egyes csomópontokhoz tartozó valószínűségi értékeket
            for(int idx = 0; idx<lists.size();idx++){
                ertekbeallitas(lists.get(idx));
                double szorzat = 1;
                for(int cs_ind = 0; cs_ind<csomopont_lista.size();cs_ind++){
                    szorzat *= valoszinuseg(csomopont_lista.get(cs_ind));
                }
                ertek+=szorzat;
            }
            szamlalok.add(ertek);
        }
        double normalizacios_konstans = 0;

        for(double ertek : szamlalok){
            normalizacios_konstans += ertek;
            System.out.println("szamlalol: " + ertek);
        }
        for(double ertek : szamlalok){
            System.out.println(ertek/normalizacios_konstans);
        }
        System.out.println( "norm konst: " + normalizacios_konstans);
    }

    public static void addCsomoPont(Scanner sc){

        int index;

        String[] input = sc.nextLine().split("\t");

        int hany_ertek = Integer.parseInt(input[0]);
        int hany_szulo = Integer.parseInt(input[1]);

        if(hany_szulo == 0){
            Csomopont cs = new Csomopont();
            cs.szulok_szama = hany_szulo;
            cs.hany_erteke = hany_ertek;
            for (int i = 2; i < input.length; i++) {
                String[] temp = input[i].split(",");
                Sor sor = new Sor();
                for (int j = 0; j < temp.length; j++) {
                    double tempnew = Double.parseDouble(temp[j]);
                    sor.addToSorCsomopontErtek(tempnew);
                }
                cs.igazsagtabla.addSor(sor);
            }
            csomopont_lista.add(cs);
        } else {
            index = 2;
            ArrayList<Integer> szulok = new ArrayList<>();
            Csomopont cs = new Csomopont();
            // Szülők indexeinek hozzáadása a listához
            for (int i = 0; i < hany_szulo; i++) {
                cs.szulok_indexei.add(Integer.parseInt(input[index]));
                index++;
            }

            cs.szulok_szama = hany_szulo;
            cs.hany_erteke = hany_ertek;
            //cs.megjelenit();
            for (int i = index; i < input.length; i++) {
                String[] temp = input[i].split(":");
                Sor sor = new Sor();
                String[] szulok_ertekek = temp[0].split(",");
                String[] cs_ertekek = temp[1].split(",");
                for (int j = 0; j < szulok_ertekek.length; j++) {
                    int tempnew = Integer.parseInt(szulok_ertekek[j]);
                    sor.addToSorSzuloErtek(tempnew);
                }
                for (int j = 0; j < cs_ertekek.length; j++) {
                    double tempnew = Double.parseDouble(cs_ertekek[j]);
                    sor.addToSorCsomopontErtek(tempnew);
                }
                cs.igazsagtabla.addSor(sor);
            }
            csomopont_lista.add(cs);
        }
    }

    public static void van_erteke(){
        for(Csomopont cs : csomopont_lista) {
            for (EkvidenciaValtozo v : ekv_valtozok) {
                if (csomopont_lista.indexOf(cs) == v.index) {
                    cs.erteke = v.erteke;
                }
            }
        }
    }
    public static double valoszinuseg(Csomopont cs){
        for(Sor sor : cs.igazsagtabla.sorok){
            if(cs.szulok_szama==0){
                return sor.csomopont_ertekek.get(cs.aktualis_ertek);
            } else {
                ArrayList<Integer> list = new ArrayList<>();
                for(int i = 0; i<cs.szulok_szama; i++){
                    if(sor.szulok_ertekek.get(i) == csomopont_lista.get(cs.szulok_indexei.get(i)).aktualis_ertek) list.add(1);
                }
                if(list.size()==cs.szulok_szama) return sor.csomopont_ertekek.get(cs.aktualis_ertek);
            }

        }
        return 1;
    }
    public static void ertekbeallitas(List<Integer> perm_lista){
        for(int i = 0; i<perm_lista.size();i++){
            csomopont_lista.get(i).aktualis_ertek = perm_lista.get(i);
        }
    }
}
