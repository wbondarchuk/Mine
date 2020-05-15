package com.company.model;
//Класс,описывающий состояние ячейки; ее положение; мина она или нет; а так же количество мин вокруг,если она не мина.
public class MinesweeperCell {
    private int row;//В какой строке находится ячейка
    private int colum;//В каком столбце находится ячейка
    private String state;//Состояние ячейки(closed(по умолчанию),flagged,opened,question);
    private boolean mined;//Мина или нет
    private int counter;//Число мин вокруг ячейки,если она не мина
    private boolean viewed;//Просматривают ячейку или нет
    private String[] MarkSquence = {"closed", "flagged", "question"};

    //Инициализация по умолчанию
    public MinesweeperCell(int row, int colum) {
        this.row = row;
        this.colum = colum;
        this.state = "closed";
        this.mined = false;
        this.counter = 0;
        this.viewed = false;
    }

    //Переставляет состояния ячейки при нажатие на правую кнопку мыши
    public void nextMark() {
        for (int index = 0; index < MarkSquence.length; index++) {
            if (state == MarkSquence[index]) {
                state = MarkSquence[(index + 1) % MarkSquence.length];
                break;
            }
        }
    }

    public void open() {
        state = "opened";
    }


    //getter and setter

    public String getState() {
        return state;
    }

    public boolean isMined() {
        return mined;
    }

    public void setMined(boolean mined) {
        this.mined = mined;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getRow() {
        return row;
    }

    public int getColum() {
        return colum;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }
}
