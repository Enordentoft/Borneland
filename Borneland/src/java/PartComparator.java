
import java.sql.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Sorts participants by laneID and totalScore
 * @author Michael
 */
class PartComparator implements java.util.Comparator<ScoreObject> {

    @Override
    public int compare(ScoreObject o1, ScoreObject o2) {
        int lane1 = Integer.parseInt(o1.getLaneID());
        int lane2 = Integer.parseInt(o2.getLaneID());
        int totalScore1 = o1.getTotalScore();
        int totalScore2 = o2.getTotalScore();

        //compare by lane
        if (lane1 > lane2) {
            return 1;
        } else if (lane1 < lane2) {
            return -1;
        } else {
            //if same lane compare by totalScore
            if (totalScore1 > totalScore2) {
                return -1;
            } else if (totalScore1 < totalScore2) {
                return 1;
            } else {
                return 0;
            }

            }

        }   

    }
