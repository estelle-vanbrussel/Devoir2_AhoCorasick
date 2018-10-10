public class Main {

    public static void main(String [] args) {
        String[] keywords = {"ART","Carte","CLE","COUP","TABLE"};
        PatternMatchingMachine pmm = new PatternMatchingMachine(keywords);
        for (int i = 0; i < pmm.globalTable.length ; ++i) {
            String ligne = "";
            for (int j = 0; j < pmm.globalTable[0].length ; ++j) {
                ligne += pmm.globalTable[i][j] + " ";
            }
            System.out.println((char)i + " -> " + ligne);
        }
    }
}
