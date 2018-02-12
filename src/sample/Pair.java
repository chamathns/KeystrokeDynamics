package sample;

import java.util.ArrayList;

public class Pair {
    private final double margin = 0.7;
    private final int error = 15;
    private static Pair instance = new Pair();

    public static Pair getInstance(){
        return instance;
    }

    public boolean match(String master, String slave){
        int matches = 0;
        int mismatches = 0;
        ArrayList<Long> masterTime = new ArrayList<Long>();
        ArrayList<Long> slaveTime = new ArrayList<Long>();

        ArrayList<String> ms = new ArrayList<String>();
        ArrayList<String> ss = new ArrayList<String>();

        String[] masterPro = master.split("#");
        String[] slavePro = slave.split("#");

        for(int i = 0; i<masterPro.length; i++){
            ms.add(masterPro[i]);
        }
        ms.remove(0);

        for(int i = 0; i<slavePro.length; i++){
            ss.add(slavePro[i]);
        }
        ss.remove(0);
//        username = masterPro[0];
        for(int i = 0; i<ms.size(); i++){
            masterTime.add(Long.parseLong(ms.get(i)));
        }

        for(int i = 0; i<ss.size(); i++){
            slaveTime.add(Long.parseLong(ss.get(i)));
        }

        for(int j = 0; j<masterTime.size();j++ ){
            long diff = Math.abs(masterTime.get(j)-slaveTime.get(j));
            if(diff <= error){
                matches++;
            }
            else{
                mismatches++;
            }
        }
        double similarity = ((double)matches/((double)matches + (double)mismatches));
        System.out.printf("Similarity: %.2f",similarity);

        return similarity >= margin;

    }
}
