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

    public int nbMatrixColumns() {
        int i=0;
        int statesCount=1;
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
        return statesCount;
    }

    public void buildTable() {
        //créer une table de commandes sous la forme d'une matrice avec en lignes
        //les indices vers les caractères ASCII et en colonne les états.

        //TODO : que fait-on concernant la casse ?

        int statesCount = nbMatrixColumns();
        globalTable = new int[128][statesCount];

        int newState = 0;
        for (String keyword : keywords) {
            newState = enter(keyword, newState);
        }
    }

    public int jumpTo(int state, char character) {
        if (globalTable[character][state] == 0) {
            return -1;
        } else {
            return globalTable[character][state];
        }
    }

    public int enter(String keyword, int newState) {
        int state = 0;
        int j = 0;

        //On se positionne à l'état qui n'a pas le successeur voulu.
        while(j < keyword.length() && jumpTo(state, keyword.charAt(j)) != -1) {
            state = jumpTo(state, keyword.charAt(j));
            ++j;
        }

        //On créé tous les successeurs consécutivement de l'état trouvé précédemment.
        for (int p = j; p < keyword.length() ; ++p) {
            ++newState;
            globalTable[keyword.charAt(p)][state] = newState;
            state = newState;
        }

        //TODO : renseigner un tableau output qui associe state à keyword
        return newState;
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
