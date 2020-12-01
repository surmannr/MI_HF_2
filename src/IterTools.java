import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

// Forras: https://stackoverflow.com/questions/9591561/java-cartesian-product-of-a-list-of-lists
public class IterTools {
    public static ArrayList<List<Integer>> generate(ArrayList<List<Integer>> sets) {
        int solutions = 1;
        ArrayList<List<Integer>> result = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>();
        for(int i = 0; i < sets.size(); solutions *= sets.get(i).size(), i++);
        for(int i = 0; i < solutions; i++) {
            int j = 1;

            for(List<Integer> set: sets) {
                temp.add(set.get((i/j)%set.size()));
                j *= set.size();
            }
            result.add((List<Integer>) temp.clone());
            temp.clear();
        }
        return result;
    }
}
