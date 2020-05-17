package com.example.demo;

public class Board {
    public enum STATUS {
        RUNNING,
        INVALID,
        WIN_PLAYER1,
        WIN_PLAYER2
    }

    private int nrLines = 30;
    private int nrCols = 30;
    private int[][] board = new int[nrLines][nrCols];
    private STATUS status;
    int id = 0 ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Board() {

        status = STATUS.RUNNING;
    }

    public Board( int id){
        status = STATUS.RUNNING;
        this.id = id;
    }

    private boolean isValid(int x, int y) {
        if (x < 0 || x > nrLines || y < 0 || y > nrCols) {
            return false;
        }

        return true;
    }

    public STATUS applyMove(int player, int x, int y) {
        if (!isValid(x, y)) {
            return STATUS.INVALID;
        }

        if (board[x][y] != 0) {
            return STATUS.INVALID;
        }

        board[x][y] = player;

        /*CHECK-WIN*/
        int maxLen = 1;
        int firstX = x, firstY = y;
        int secondX = x, secondY = y;

        while (isValid(firstX, firstY) && board[firstX][firstY] == player) {
            firstX--;
        }
        firstX++;
        while (isValid(secondX, secondY) && board[secondX][secondY] == player) {
            secondX++;
        }
        secondX--;
        maxLen = Math.max(secondX - firstX + 1, maxLen);

        firstX = x;
        firstY = y;
        secondX = x;
        secondY = y;

        while (isValid(firstX, firstY) && board[firstX][firstY] == player) {
            firstY--;
        }
        firstY++;
        while (isValid(secondX, secondY) && board[secondX][secondY] == player) {
            secondY++;
        }
        secondY--;
        maxLen = Math.max(secondY - firstY + 1, maxLen);

        firstX = x;
        firstY = y;
        secondX = x;
        secondY = y;

        while (isValid(firstX, firstY) && board[firstX][firstY] == player) {
            firstX--;
            firstY--;
        }
        firstX++;
        while (isValid(secondX, secondY) && board[secondX][secondY] == player) {
            secondX++;
            secondY++;
        }
        secondX--;
        maxLen = Math.max(secondX - firstX + 1, maxLen);

        firstX = x;
        firstY = y;
        secondX = x;
        secondY = y;

        while (isValid(firstX, firstY) && board[firstX][firstY] == player) {
            firstX--;
            firstY++;
        }
        firstX++;
        while (isValid(secondX, secondY) && board[secondX][secondY] == player) {
            secondX++;
            secondY--;
        }
        secondX--;
        maxLen = Math.max(secondX - firstX + 1, maxLen);


        if (maxLen >= 5) {
            switch (player) {
                case 1:
                    return STATUS.WIN_PLAYER1;
                case 2:
                    return STATUS.WIN_PLAYER2;
            }
        }
        /*CHECK-WIN*/
        return STATUS.RUNNING;
    }
}
