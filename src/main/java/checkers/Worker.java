package checkers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Worker {

    private int[][] checkersBoard;
    private int tr;
    boolean canBeat = false;
    ArrayList<ArrayList<int[][]>> possBoards;

    public Worker(int[][] brd) {
        this.checkersBoard = brd;
    }

    private boolean isPositionEmpty(int positionX, int positionY, int[][] brd) {
        if(brd[positionY][positionX] == 0){
            return true;
        } else {
            return false;
        }
    }

    private int[][] moveAhead(int positionX, int positionY, int[][] brd, int key, int positX, int positY) {
        int[][] board2 = Arrays.stream(brd).map(int[]::clone).toArray(int[][]::new);
        board2[positionY][positionX] = 0;
        board2[positY][positX] = key;
        return board2;
    }


    private int[][] becomeKing(int positionX, int positionY, int[][] brd, int positX, int positY, int key) {
        int[][] board2 = Arrays.stream(brd).map(int[]::clone).toArray(int[][]::new);
        board2[positionY][positionX] = 0;
        board2[positY][positX] = key * 10 + key;
        return board2;
    }


    private int[][] becomeKing(int positionXOfEnemy, int positionYOfEnemy, int positX, int positY, int[][] brd,  int positionX, int positionY,  int key) {
        int[][] board2 = Arrays.stream(brd).map(int[]::clone).toArray(int[][]::new);
        board2[positionYOfEnemy][positionXOfEnemy] = 0;
        board2[positionY][positionX] = 0;
        board2[positY][positX] = key * 10 + key;
        return board2;
    }

    private boolean isThereEnemyPresent(int positionX, int positionY,int[][] brd,  int positX, int positY) {
        int position = brd[positionY][positionX];
        int nPosition = brd[positY][positX];
        boolean isPositionEven = (position % 2 == 0 && position > 0);
        boolean isPositionNEven = (nPosition % 2 == 0 && nPosition > 0);
        if ((isPositionEven) && (!isPositionNEven) && (nPosition != 0) ) {
            return true;
        }
        else if ((!isPositionEven) && (isPositionNEven) && (nPosition != 0)) {
            return true;
        }
        else {
            return false;
        }
    }


    private boolean canBeatE(int[][] brd, int positionX, int positionY) {
        boolean isY =  positionY >= 0 && positionY < 8;
        boolean isX = (positionX >= 0) && (positionX < 8);

        if(isX && isY && isPositionEmpty(positionX, positionY,brd)){
            return true;
        } else {
            return false;
        }
    }

    private int[][] beatAnotherPlayerCheckers(int positionX, int positionY, int positX, int positY, int[][] brd, int oldPositionX, int oldPositionY, int key) {
        int[][] board2 = Arrays.stream(brd).map(int[]::clone).toArray(int[][]::new);
        board2[positionY][positionX] = 0;
        board2[oldPositionY][oldPositionX] = 0;
        board2[positY][positX] = key;
        return board2;
    }

    private void canBeatAnotherPlayer(int keyOfKing, int key) {
        for (int y = 0; y < this.checkersBoard.length; y++) {
            for (int x = 0; x < this.checkersBoard[y].length; x++) {
                if (canBeat == true) {
                    return;
                }
                if (key == this.checkersBoard[y][x]) {
                    int stepAmount;
                    switch (key){
                        case (1) :
                            stepAmount= 2;
                            break;
                        case (2) :
                            stepAmount= 2;
                            break;
                        case (11) :
                            stepAmount = 4;
                            break;
                        default:
                            stepAmount = 4;
                            break;
                    }

                    int[][] moves;
                    if (stepAmount == 2) {
                        if (tr != 1) {
                            int a = x - 1;
                            int c = x + 1;
                            int d = y - 1;
                            moves = new int[][]{{a, d}, {c, d}};
                        } else{
                            int a = x - 1;
                            int b = y + 1;
                            int c = x + 1;
                            moves = new int[][]{{a, b}, {c, b}};
                        }
                    } else {
                        int a = x - 1;
                        int b = y + 1;
                        int c = x + 1;
                        int d = y - 1;
                        moves = new int[][]{{a, b}, {c, b}, {a, d}, {c, d}};
                    }


                    canBeat( x, y, moves, this.checkersBoard );
                } else if (this.checkersBoard[y][x] == keyOfKing) {
                    int stepAmount ;
                    switch (keyOfKing){
                        case (1) :
                            stepAmount = 2;
                            break;
                        case (2) :
                            stepAmount = 2;
                            break;
                        case (11) :
                            stepAmount= 4;
                            break;
                        default:
                            stepAmount= 4;
                            break;
                    }

                    int[][] moves;

                    if (stepAmount == 2) {
                        if (tr != 1) {
                            int a = x - 1;
                            int c = x + 1;
                            int d = y - 1;
                            moves = new int[][]{{a, d}, {c, d}};
                        } else{
                            int a = x - 1;
                            int b = y + 1;
                            int c = x + 1;
                            moves = new int[][]{{a, b}, {c, b}};
                        }
                    } else {
                        int a = x - 1;
                        int b = y + 1;
                        int c = x + 1;
                        int d = y - 1;
                        moves = new int[][]{{a, b}, {c, b}, {a, d}, {c, d}};
                    }
                    canBeat( x, y, moves, this.checkersBoard );
                }

            }
        }
    }

    private void canBeat(int positionX, int positionY, int[][] moves, int[][] brd) {
        for (int i = 0; i < moves.length; i++) {
            int moveX = moves[i][0];
            int moveY = moves[i][1];
            boolean isY =  (moveY >= 0) && (moveY < 8);
            boolean isX = (moveX >= 0) && (moveX < 8);
            if (isY && isX &&  isThereEnemyPresent(positionX, positionY,brd,  moveX, moveY)) {

                int[] position = new int[2];
                if (moveX < positionX) {
                    if (moveY < positionY) {
                        position[1] = --moveY;
                        position[0] = --moveX;
                    }
                    else {
                        position[1] = ++moveY;
                        position[0] = --moveX;
                    }
                } else {
                    if (moveY < positionY) {
                        position[1] = --moveY;
                        position[0] = ++moveX;
                    }
                    else {
                        position[1] = ++moveY;
                        position[0] = ++moveX;
                    }
                }
                moveX = position[0];
                moveY = position[1];
                if (canBeatE(brd, moveX, moveY)) {
                    canBeat = true;
                }
            }
        }
    }

    private ArrayList<int[][]> performMove(int positionX, int positionY, int[][] moves, int[][] brd, int key) {
        ArrayList<int[][]> availableBoards = new ArrayList<>();

        for (int i = 0; i < moves.length; i++) {
            int moveX = moves[i][0];
            int moveY = moves[i][1];
            boolean isY =  (moveY >= 0) && (moveY < 8);
            boolean isX = (moveX >= 0) && (moveX < 8);

            if (isX && isY) {
                if (isPositionEmpty(moveX, moveY,brd)) {
                    int[][] board2;
                    boolean isTrereEnemyKing ;
                    if (tr == -1 && moveY == 0 && key > 0) {
                        isTrereEnemyKing = true;
                    }
                    else if (tr == 1 && moveY == (8 - 1) && key < 0) {
                        isTrereEnemyKing =  true;
                    }
                    else {
                        isTrereEnemyKing = false;
                    }
                    if (isTrereEnemyKing) {
                        if (moves.length == 2) {
                            board2 = becomeKing(positionX, positionY, brd, moveX, moveY, key);
                        }
                        else {
                            board2 = moveAhead(positionX, positionY,brd, key, moveX, moveY);
                        }
                    }
                    else {
                        board2 = moveAhead(positionX, positionY, brd, key, moveX, moveY);
                    }
                    if (!canBeat)
                        availableBoards.add(board2);
                }
                else if (isThereEnemyPresent(positionX, positionY, brd, moveX, moveY)) {
                    int xPositionOld = moveX;
                    int yPositionOld = moveY;
                    int[] position = new int[2];
                    if (moveX < positionX) {
                        if (moveY < positionY) {
                            position[1] = --moveY;
                            position[0] = --moveX;
                        }
                        else {
                            position[1] = ++moveY;
                            position[0] = --moveX;
                        }
                    } else {
                        if (moveY < positionY) {
                            position[1] = --moveY;
                            position[0] = ++moveX;
                        }
                        else {
                            position[1] = ++moveY;
                            position[0] = ++moveX;
                        }
                    }
                    moveX = position[0];
                    moveY = position[1];

                    if (canBeatE(brd, moveX, moveY)) {
                        if (!canBeat) {
                            availableBoards = new ArrayList<>();
                        }
                        int[][] board2;

                        boolean b;
                        if (tr == -1 && moveY == 0 && key > 0) {
                            b = true;
                        }
                        else if (tr == 1 && moveY == (8 - 1) && key < 0) {
                            b = true;
                        }
                        else {
                            b = false;
                        }
                        if (b) {
                            if (moves.length == 2) {

                                board2 = becomeKing(xPositionOld, yPositionOld,  moveX, moveY, brd, positionX, positionY, key);
                            }
                            else {
                                board2 = beatAnotherPlayerCheckers(positionX, positionY , moveX, moveY, brd,  xPositionOld, yPositionOld, key);
                            }
                        } else {

                            board2 = beatAnotherPlayerCheckers(positionX, positionY , moveX, moveY, brd,  xPositionOld, yPositionOld, key);
                        }
                        availableBoards.add(board2);
                    }
                }
            }
        }
        return availableBoards;
    }

    public ArrayList<ArrayList<int[][]>> getAllAvailableBoards(int key) {
        canBeat = false;
        if(key == 1) {
            this.tr = -1;
        } else{
            this.tr = 1;
        }
        int keyOfQueen = key + key * 10 ;
        possBoards = new ArrayList<>();

        canBeatAnotherPlayer(keyOfQueen,key);
        for (int y = 0; y < this.checkersBoard.length; y++) {
            for (int x = 0; x < this.checkersBoard[y].length; x++) {
                if(keyOfQueen == this.checkersBoard[y][x]){
                    int[][] brd1 = Arrays.stream(this.checkersBoard).map(int[]::clone).toArray(int[][]::new);
                    int moves1;

                    switch (keyOfQueen){
                        case (1) :
                            moves1 = 2;
                            break;
                        case (2) :
                            moves1 = 2;
                            break;
                        case (11) :
                            moves1 = 4;
                            break;
                        default:
                            moves1 = 4;
                            break;
                    }

                    int[][] moves2 ;


                    if (moves1 == 2) {
                        if (tr != 1) {
                            int a = x - 1;
                            int c = x + 1;
                            int d = y - 1;
                            moves2 = new int[][]{{a, d}, {c, d}};
                        } else{
                            int a = x - 1;
                            int b = y + 1;
                            int c = x + 1;
                            moves2 = new int[][]{{a, b}, {c, b}};
                        }
                    } else {
                        int a = x - 1;
                        int b = y + 1;
                        int c = x + 1;
                        int d = y - 1;
                        moves2 = new int[][]{{a, b}, {c, b}, {a, d}, {c, d}};
                    }

                    possBoards.add(performMove(x, y, moves2, brd1, keyOfQueen));
                } else if(key == this.checkersBoard[y][x]){
                    int[][] brd2 = Arrays.stream(this.checkersBoard).map(int[]::clone).toArray(int[][]::new);
                    int moves3;

                    switch (key){
                        case (1) :
                            moves3 = 2;
                            break;
                        case (2) :
                            moves3 = 2;
                            break;
                        case (11) :
                            moves3 = 4;
                            break;
                        default:
                            moves3 =4;
                            break;
                    }
                    int[][] moves4;
                    if (moves3 == 2) {
                        if (tr != 1) {
                            int a = x - 1;
                            int c = x + 1;
                            int d = y - 1;
                            moves4 = new int[][]{{a, d}, {c, d}};
                        } else{
                            int a = x - 1;
                            int b = y + 1;
                            int c = x + 1;
                            moves4 = new int[][]{{a, b}, {c, b}};
                        }
                    } else {
                        int a = x - 1;
                        int b = y + 1;
                        int c = x + 1;
                        int d = y - 1;
                        moves4 = new int[][]{{a, b}, {c, b}, {a, d}, {c, d}};
                    }


                    possBoards.add(performMove(x, y, moves4, brd2, key));
                }

            }
        }
        return possBoards;
    }


    public List<int[][]> allAvailableBoards(int br) {

        List<int[][]> arrayList = new ArrayList<>();
        int availableBoardsLen = getAllAvailableBoards(br).size();
        for(int i = 0; i <availableBoardsLen; i++ ){
            arrayList.addAll(getAllAvailableBoards(br).get(i));
        }
        return arrayList;
    }
}