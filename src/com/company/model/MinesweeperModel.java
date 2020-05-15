package com.company.model;

import java.util.Random;
import java.util.Vector;

public class MinesweeperModel {

    private boolean gameOver;//Конец игры
    private boolean firstStep;//Это первый шаг или нет(если да,то после него инициализируем бомбы на поле)
    private boolean finishGame;//Закончена ли игра или нет
    private MinesweeperCell[][] cellsTable;//Таблица ячеек(то есть поле);
    private int rowCount;//число строк
    private int columCount;//число столбцов
    private int mineCount;//Количество мин

    public MinesweeperModel() {
        rowCount = 9;
        columCount = 9;
    }

    //Устанавливаем свойство игры, площадь игрового поля
    public void setSpecifications(int row, int colum) {
        rowCount = row;
        columCount = colum;
    }

    //Инициализация игры
    public void StartGame() {
        if (rowCount == 9)
            mineCount = 10;
        else if (rowCount == 16)
            if (columCount == 16)
                mineCount = 40;
            else
                mineCount = 99;
        this.gameOver = false;
        this.firstStep = true;
        this.finishGame = false;
        cellsTable = new MinesweeperCell[rowCount][columCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columCount; j++) {
                cellsTable[i][j] = new MinesweeperCell(i, j);
            }
        }
    }

    //Получение ячейки поля
    public MinesweeperCell getCell(int row, int colum) {
        if (row < 0 || colum < 0 || row >= rowCount || colum >= columCount)
            return null;
        return cellsTable[row][colum];
    }

    //Возвращает проигрыш или нет
    public boolean isGameOver() {
        return gameOver;
    }

    //Возвращает закончена игра или нет(Состояние изменяется при проигрыше или выигрыше)
    public boolean isFinishGame() {
        return finishGame;
    }

    //Следующий тип клетки при нажатие на правую кнопку мыши
    public void nextCellMark(int row, int colum) {
        MinesweeperCell cell = getCell(row, colum);
        if (cell != null) {
            if (cell.getState() == "flagged")
                mineCount++;
            cell.nextMark();
            if (cell.getState() == "flagged")
                mineCount--;
        }
    }

    //Мы победили или нет
    public boolean isWin() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columCount; j++) {
                MinesweeperCell cell = getCell(i, j);
                String state = cell.getState();
                if (!cell.isMined() && (state != "opened" && state != "flagged"))
                    return false;
            }
        }
        finishGame = true;
        return true;
    }

    //Просматривуют ячейки на поле  или нет
    public boolean isViewedCell() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columCount; j++) {
                MinesweeperCell cell = getCell(i, j);
                if (cell.isViewed())
                    return true;
            }
        }
        return false;
    }

    //Открывает ячейку на поле при нажатие на левую кнопку мыши
    public void openCell(int row, int colum) {
        MinesweeperCell cell = getCell(row, colum);
        if (cell == null)
            return;
        cell.open();
        if (cell.isMined()) {
            gameOver = true;
            finishGame = true;
            return;
        }

        if (firstStep) {
            firstStep = false;
            generateMines();
        }

        if (countMineAroundCell(cell) == 0) {
            Vector<MinesweeperCell> neighbours = getCellNeighbours(cell);
            for (int i = 0; i < neighbours.size(); i++) {
                MinesweeperCell cellNeighbours = neighbours.get(i);
                if (cellNeighbours.getState() == "closed") {
                    openCell(cellNeighbours.getRow(), cellNeighbours.getColum());
                }
            }
        }

    }

    //Генерирует мины после первого нажатия на левую кнопку мыши
    public void generateMines() {
        for (int i = 0; i < mineCount; i++) {
            while (true) {
                int row = (new Random()).nextInt(rowCount);
                int colum = (new Random()).nextInt(columCount);
                MinesweeperCell cell = getCell(row, colum);
                if (!cell.isMined() && cell.getState() != "opened") {
                    cell.setMined(true);
                    break;
                }
            }
        }
    }

    //Возвращает число мин вокруг  открытой ячейки
    public int countMineAroundCell(MinesweeperCell cell) {
        int count = 0;
        Vector<MinesweeperCell> neighbours = getCellNeighbours(cell);
        for (int i = 0; i < neighbours.size(); i++) {
            if (neighbours.get(i).isMined())
                count++;
        }
        cell.setCounter(count);
        return count;
    }

    //Возвращает вектор соседних ячеек для данной ячейки
    public Vector<MinesweeperCell> getCellNeighbours(MinesweeperCell cell) {
        Vector<MinesweeperCell> neighbours = new Vector<>();
        int row = cell.getRow();
        int colum = cell.getColum();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = colum - 1; j <= colum + 1; j++) {
                if ((i != row || j != colum) && getCell(i, j) != null) {
                    neighbours.add(getCell(i, j));
                }
            }
        }
        return neighbours;
    }

    //Getter

    public int getRowCount() {
        return rowCount;
    }

    public int getColumCount() {
        return columCount;
    }

    public int getMineCount() {
        return mineCount;
    }

    public boolean isFirstStep() {
        return firstStep;
    }
}
