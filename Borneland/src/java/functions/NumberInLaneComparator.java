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
public class NumberInLaneComparator implements java.util.Comparator<ScoreObject> {
    
@Override
    public int compare(ScoreObject o1, ScoreObject o2) {
        int NumberInLane1 = Integer.parseInt(o1.getNumberInLane());
        int NumberInLane2 = Integer.parseInt(o2.getNumberInLane());
        int ageGroup1 = Integer.parseInt(o1.getAgeGroup());
        int ageGroup2 = Integer.parseInt(o2.getAgeGroup());

        //compare by ageGroup
        if (ageGroup1 > ageGroup2) {
            return 1;
        } else if (ageGroup1 < ageGroup2) {
            return -1;
        } else {
            //compare by NumberInLane
            if (NumberInLane1 > NumberInLane2) {
                return -1;
            } else if (NumberInLane1 < NumberInLane2) {
                return 1;
            } else {
                return 0;
            }

            }}

        }
