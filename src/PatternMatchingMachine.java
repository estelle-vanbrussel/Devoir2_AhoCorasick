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

    public void buildTable() {
        //créer une table de commandes sous la forme d'une matrice avec en lignes
        //les indices vers les caractères ASCII et en colonne les états.

        int i=0;
        int statesCount=1;
        int oldStatesCount=0;

        do {
            oldStatesCount=statesCount;
            for (int j = 1; j < keywords.length ; ++j) {
                if(i<keywords[j].length()/* && vérifier qu'on n'a pas déjà vu la lettre dans la même colonne sur tous les mots d'avant*/){

                    statesCount++;
                }
            }
            i++;
        } while(oldStatesCount<statesCount);

        globalTable = new int[128][statesCount];

        i=0;
        statesCount=0;
        oldStatesCount=0;

        do {
            oldStatesCount=statesCount;
            for (String keyword: keywords) {
                if(i<keyword.length() && globalTable[(int)keyword.charAt(i)][i]==0){
                    globalTable[(int)keyword.charAt(i)][i] = statesCount+1;

                    statesCount++;
                }
            }
            i++;
        } while(oldStatesCount<statesCount);
    }

    public void buildFailTable() {

    }

}
