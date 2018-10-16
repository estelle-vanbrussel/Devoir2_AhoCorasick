import java.io.FileInputStream;
import java.util.ArrayList;

public class PatternMatchingMachine {

    public String[] keywords;
    public int[][] globalTable;

    public PatternMatchingMachine(String[] keywords) {
        //trier les mots clés
        this.keywords = keywords;
        buildTable();
        buildFailTable();
    }

    public void findPatterns(FileInputStream file) {

    }

    private boolean isInDuplicateTable(String[] duplicateTable, String string) {
        // on vérifie qu'on n'a pas déjà trouvé le caractère dans la colonne
        // ou son équivalent minuscule ou majuscule
        for (String tableString : duplicateTable) {
            if (string.equalsIgnoreCase(tableString) /*||
                    (string-32 == tableCharacter || string+32 == tableCharacter)*/) {
                return true;
            }
        }
        return false;
    }

    public void buildTable() {
        //créer une table de commandes sous la forme d'une matrice avec en lignes
        //les indices vers les caractères ASCII et en colonne les états.

        int i=0;
        int statesCount=0;
        int oldStatesCount;
        String[] duplicateTable = new String[keywords.length];

        do {
            oldStatesCount=statesCount;
            for (int j = 0; j < keywords.length ; ++j) {
                // i = colonne, j = ligne
                if (i < keywords[j].length() && !isInDuplicateTable(duplicateTable, keywords[j].substring(0,i+1))){
                    statesCount++;
                }
                if (i < keywords[j].length()) {
                    duplicateTable[j] = keywords[j].substring(0,i+1);
                }
            }
            System.out.println("nb colonnes : " + statesCount);
            System.out.println("colonne : " + i);
            for (String string : duplicateTable) {

                System.out.println(string);
            }
            i++;
        } while(oldStatesCount<statesCount);

        System.out.println(statesCount);
        globalTable = new int[128][statesCount];

        //A CORRIGER
        //probleme : on ajoute à la colonne i mais il faut ajouter à la colonne correspondant à l'état
        //soit 0 pour les premiers caractères de chaque mot,
        //soit du caractère d'avant du mot en cours,
        //soit ??
        //peut-être changer totalement la structure de cette boucle

        //Algo original :
        //g(state, aj) ---> globalTable[aj][state]
        //fail ---> il n'y a pas aj dans la colonne state
        //procedure enter :
        //1e boucle : on va jusqu'à un état qui n'a pas le successeur voulu
        //2e boucle : à partir de cet état, on créé les nouveaux états
        i=0;
        statesCount=0;
        do {
            oldStatesCount=statesCount;
            for (String keyword: keywords) {
                if(i<keyword.length() && globalTable[(int)keyword.charAt(i)][i]==0){
                    if (keyword.charAt(i) >= 97 && keyword.charAt(i) <= 122) {
                        globalTable[(int)keyword.charAt(i)-32][i] = statesCount+1;
                    }
                    else {
                        globalTable[(int)keyword.charAt(i)][i] = statesCount+1;
                    }
                    statesCount++;
                }
            }
            i++;
        } while(oldStatesCount<statesCount);
        System.out.println(statesCount);
    }

    public void buildFailTable() {

    }

    public String toString() {
        String output = "";
        for (int i = 0; i < this.globalTable.length ; ++i) {
            String ligne = "";
            boolean empty = true;
            for (int j = 0; j < this.globalTable[0].length; ++j) {
                if (this.globalTable[i][j] != 0) {
                    empty = false;
                }
                ligne += this.globalTable[i][j] + " ";

            }
            if (!empty) {
                output += (char)i + " -> " + ligne + "\n";
            }

        }
        return output;
    }

}
