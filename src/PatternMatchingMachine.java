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

    private boolean isInDuplicateTable(char[] duplicateTable, char character) {
        // on vérifie qu'on n'a pas déjà trouvé le caractère dans la colonne
        // ou son équivalent minuscule ou majuscule
        for (char tableCharacter : duplicateTable) {
            if (tableCharacter == character ||
                    (character-32 == tableCharacter || character+32 == tableCharacter)) {
                return true;
            }
        }
        return false;
    }

    private char toUpper(char character) {
        int return_value = character-32;
        return (char)return_value;
    }

    public void buildTable() {
        //créer une table de commandes sous la forme d'une matrice avec en lignes
        //les indices vers les caractères ASCII et en colonne les états.

        int i=0;
        int statesCount=0;
        int oldStatesCount;
        char[] duplicateTable;

        do {
            oldStatesCount=statesCount;
            //On réinitialise la table des doublons à chaque itération
            duplicateTable = new char[keywords.length];
            for (int j = 0; j < keywords.length ; ++j) {
                // i = colonne, j = ligne
                if(i<keywords[j].length() && !isInDuplicateTable(duplicateTable, keywords[j].charAt(i))){
                    duplicateTable[j] = keywords[j].charAt(i);
                    statesCount++;
                }
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
