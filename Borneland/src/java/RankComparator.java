/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael
 */
  class RankComparator implements java.util.Comparator<ScoreObject> {

    @Override
    public int compare(ScoreObject o1, ScoreObject o2) {
        int totalScore1 = o1.getTotalScore();
        int totalScore2 = o2.getTotalScore();
        int ageGroup1 = Integer.parseInt(o1.getAgeGroup());
        int ageGroup2 = Integer.parseInt(o2.getAgeGroup());

        //compare by lane
        if (ageGroup1 > ageGroup2) {
            return 1;
        } else if (ageGroup1 < ageGroup2) {
            return -1;
        } else {
            //compare by totalScore
            if (totalScore1 > totalScore2) {
                return -1;
            } else if (totalScore1 < totalScore2) {
                return 1;
            } else {
                return 0;
            }

            }}

        }
