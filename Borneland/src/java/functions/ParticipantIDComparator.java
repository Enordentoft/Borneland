package functions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Michael
 */
public class ParticipantIDComparator implements java.util.Comparator<String> {

    @Override
    public int compare(String s1, String s2) {
        int getParticipantID1 = Integer.parseInt(s1);
        int getParticipantID2 = Integer.parseInt(s2);

        //compare by ageGroup
        if (getParticipantID1 > getParticipantID2) {
            return 1;
        } else if (getParticipantID1 < getParticipantID2) {
            return -1;
        } else {
            return 0;

        }
    }

}
