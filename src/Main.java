import java.util.ArrayList;

public class Main {

    public static void main(String [] args) {
        String[] keywords = {"ART","CARTE","CLE","COUP","TABLE"};
        PatternMatchingMachine pmm = new PatternMatchingMachine(keywords);
        ArrayList<Integer> test = new ArrayList<>(10);
        test.set(0, 50);
        /*for (int i = 0; i < pmm.globalTable.length ; ++i) {
            String ligne = "";
            for (int j = 0; j < pmm.globalTable[0].length ; ++j) {
                ligne += pmm.globalTable[i][j] + " ";
            }
        }*/
    }
}
