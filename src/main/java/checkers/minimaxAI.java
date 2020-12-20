
package checkers;

import java.util.*;

public class minimaxAI {
    private final int[] mainBoardBGL = new int[]{0, 0, 0, 0};
    private final int[] firstMainBoard = new int[]{0, 0, 0, 0};
    private String color;


    public minimaxAI() {}

    public void setColor(String color) {
        this.color = color;
    }


    public Step generateStep(int[][] position) {
        try {
            int[][] finalBoardmap = minimax(position, 6, true);
            return findTheFirstVarOfStep(position, finalBoardmap, color);
        } catch (Exception exception) {
            Worker helper = new Worker(position);
            Random random = new Random();
            List<int [][]> temp = helper.allAvailableBoards(1);
            int i = random.nextInt(temp.size());
            int[][] finalBoardmap = temp.get(i);
            return findTheFirstVarOfStep(position, finalBoardmap, color);
        }

    }

    public int[][] minimax(int[][] position, int depth, boolean maximizing) {
        Map<int[][], Integer> stepss = new HashMap<>();
        if (depth == 0){
            return position;
        }
        List<int[][]> stepsWeCan = new ArrayList<>();

        Worker helper = new Worker(position);

        List<int[][]> stepsInPositions = new ArrayList<>();
        int keyFirst = 1;
        int keySecond = 2;

        if (true && maximizing) {
            if(helper.allAvailableBoards(keyFirst).size() == 0){
                return null;
            }else{
                stepsWeCan = helper.allAvailableBoards(keyFirst);

            }

            for(int i = 0; i <= stepsWeCan.size(); i++){
                int[][] curB = stepsWeCan.get(i);
                int [][] t = this.minimax(curB, depth - 1, false);

                if (t != null){
                    stepss.put(curB, estimateMax(position, t));
                }
            }
            int maxVal;
            if (stepss.values().size() != 0) {
                maxVal = Collections.max(stepss.values());
            }else{
                return null;
            }
            for(int i = 0; i <= stepsWeCan.size(); i++){
                int[][] firdtB = stepsWeCan.get(i);
                if (stepss.containsKey(firdtB)){
                    Integer v = stepss.get(firdtB);
                    if(v.equals(maxVal)) {
                        stepsInPositions.add(firdtB);
                    }
                }

            }

        } else {
            if(helper.allAvailableBoards(keySecond).size() == 0){
                return null;
            }else{
                stepsWeCan = helper.allAvailableBoards(keySecond);
            }


            for(int i = 0; i <= stepsWeCan.size(); i++){
                int[][] posB = stepsWeCan.get(i);
                int [][] t = this.minimax(posB, depth - 1, true);
                if (t != null)
                    stepss.put(posB, estimateMin(position, t));
            }
            int minVal;
            if (stepss.values().size() != 0) {
                minVal = Collections.min(stepss.values());

            }else{
                return null;
            }

            for(int i = 0; i <= stepsWeCan.size(); i++){
                int[][] posB = stepsWeCan.get(i);
                if (stepss.containsKey(posB)){
                    Integer v = stepss.get(posB);
                    if(v.equals(minVal)) {
                        stepsInPositions.add(posB);
                    }
                }
            }

        }

        return position;
    }

    public int estimateMax(int[][] boardd, int[][] shift) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int val = shift[i][j];
                if (val == 1) {
                    mainBoardBGL[0]++;
                } else if (val == 11) {
                    mainBoardBGL[1]++;
                } else if (val == 2) {
                    mainBoardBGL[2]++;
                } else if (val == 22) {
                    mainBoardBGL[3]++;
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int val = boardd[i][j];
                if (val == 1) {
                    firstMainBoard[0]++;
                } else if (val == 11) {
                    firstMainBoard[1]++;
                } else if (val == 2) {
                    firstMainBoard[2]++;
                } else if (val == 22) {
                    firstMainBoard[3]++;
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int val = boardd[i][j];
                if (val == 1) {
                    firstMainBoard[0]++;
                } else if (val == 11) {
                    firstMainBoard[1]++;
                } else if (val == 2) {
                    firstMainBoard[2]++;
                } else if (val == 22) {
                    firstMainBoard[3]++;
                }
            }
        }
        int res = mainBoardBGL[0] + mainBoardBGL[1] * 3 - mainBoardBGL[2] - mainBoardBGL[3] * 3;
        int frqTOBcKing = firstMainBoard[1] < mainBoardBGL[1] ? 3 : 0;
        res += frqTOBcKing;
        int killedCh = firstMainBoard[2] - mainBoardBGL[2] + (firstMainBoard[3] - mainBoardBGL[3]) * 3;
        res += killedCh;
        int wiilBeKilled = getAmoutOfPossKilled(shift);
        res -= wiilBeKilled;
        return res;
    }

    private int getAmoutOfPossKilled(int[][] board) {
        Worker helper = new Worker(board);
        ArrayList<ArrayList<int[][]>> allPosBoardsNew = helper.getAllAvailableBoards(2);
        int killedCHAM = 0;
        int size = allPosBoardsNew.size();
        for (ArrayList<int[][]> boasrdNN : allPosBoardsNew) {
            int subsize = boasrdNN.size();
            for (int[][] nn : boasrdNN) {

                int[] othersAmCh = new int[]{0, 0, 0, 0};
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        int val = nn[i][j];
                        if (val == 1) {
                            othersAmCh[0]++;
                        } else if (val == 11) {
                            othersAmCh[1]++;
                        } else if (val == 2) {
                            othersAmCh[2]++;
                        } else if (val == 22) {
                            othersAmCh[3]++;
                        }
                    }
                }
                int amOfDead = firstMainBoard[0] - mainBoardBGL[0] + (firstMainBoard[ 1] - mainBoardBGL[ 1]) * 3;
                if (amOfDead > killedCHAM) {
                    killedCHAM = amOfDead;
                }
            }
        }
        int res = killedCHAM * (-1);
        return res;
    }



    public int estimateMin(int[][] boardO, int[][] shift) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int val = shift[i][j];
                if (val == 1) {
                    mainBoardBGL[2]++;
                } else if (val == 11) {
                    mainBoardBGL[3]++;
                } else if (val == 2) {
                    mainBoardBGL[0]++;
                } else if (val == 22) {
                    mainBoardBGL[1]++;
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int val = boardO[i][j];
                if (val == 1) {
                    firstMainBoard[2]++;
                } else if (val == 11) {
                    firstMainBoard[3]++;
                } else if (val == 2) {
                    firstMainBoard[0]++;
                } else if (val == 22) {
                    firstMainBoard[1]++;
                }
            }
        }
        int res = mainBoardBGL[2] + mainBoardBGL[3] * 3 - mainBoardBGL[0] - mainBoardBGL[1] * 3;
        int willBeKing = firstMainBoard[3] < mainBoardBGL[3] ? 3 : 0;
        res += willBeKing;
        int willbeKilled = firstMainBoard[0] - mainBoardBGL[0] + (firstMainBoard[1] - mainBoardBGL[1]) * 3;
        res += willbeKilled;
        return res;
    }

    public static Step findTheFirstVarOfStep(int firstB[][], int lastB[][], String color) {
        if (color.equals("RED")) {
            int chang[][] = new int[8][8];
            int changedTemp[][] = chang;
            int chang1[][] = new int[8][8];
            for (int i = 0; i < lastB.length; i++) {

                for (int j = 0; j < lastB.length; j++) {
                    chang[8 - i - 1][8 - j - 1] = lastB[i][j];
                }
            }
            for (int i = 0; i < firstB.length; i++) {
                for (int j = 0; j < firstB.length; j++) {
                    chang1[8 - i - 1][8 - j - 1] = firstB[i][j];
                }
            }
            int originalTemp[][] = chang1;

            lastB = changedTemp;
            firstB = originalTemp;
        }
        Step.Position fisrtPos = null;
        Step.Position secPos = null;
        int aaf[][] = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                aaf[i][j] =  - firstB[i][j] + lastB[i][j];
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (-1 * 1 == aaf[i][j] ||  -1 * 11 == aaf[i][j]) {
                    fisrtPos = new Step.Position(j, i);
                }
                if (1 == aaf[i][j]  || 11 == aaf[i][j]) {
                    secPos = new Step.Position(j, i);
                }
            }
        }
        return new Step(fisrtPos, secPos);
    }

}