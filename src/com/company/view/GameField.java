package com.company.view;

import com.company.controller.Controller;
import com.company.model.MinesweeperCell;
import com.company.model.MinesweeperModel;

import javax.swing.*;
import java.awt.*;


public class GameField extends JPanel {

    private static final int CELL_SIZE = 16;
    private MinesweeperModel model;
    private Controller controller;
    private MinesweeperView view;
    private int rowCount;
    private int columCount;


    public GameField(MinesweeperModel model, Controller controller, MinesweeperView view) {
        this.model = model;
        this.controller = controller;
        this.view = view;
        this.addMouseMotionListener(controller);
        this.addMouseListener(controller);
        FieldInitialization();
    }

    //Инициализация поля, используется для того,чтобы начать игру  или при изменение уровня сложности
    public void FieldInitialization() {
        removeAll();
        rowCount = model.getRowCount();
        columCount = model.getColumCount();
        setPreferredSize(new Dimension(CELL_SIZE * columCount, CELL_SIZE * rowCount));
    }

    //Используется для синхронизации Модели и Представления(то есть Представление отрисовает состояние Модели)
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columCount; j++) {
                Image im = getImageForStateCell(i, j);
                g2d.drawImage(im, j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);

            }
        }
    }

    //Псевдоавтомат, который по состоянию ячейки в Модели выдает определеную image
    public Image getImageForStateCell(int row, int colum) {
        MinesweeperCell cell = model.getCell(row, colum);
        if (cell.getState() == "closed") {
            if (model.isGameOver() && cell.isMined()) {
                return view.hashMapImage.get("StateBomb.png").getImage();
            }
            if (model.isFinishGame() && cell.isMined()) {
                return view.hashMapImage.get("StateFlag.png").getImage();
            }
            if (cell.isViewed()) {
                return view.hashMapImage.get("State0.png").getImage();
            }
            return view.hashMapImage.get("StateClosed.png").getImage();
        } else if (cell.getState() == "flagged") {
            if (!cell.isMined() && model.isGameOver()) {
                return view.hashMapImage.get("StateBombMistake.png").getImage();
            }
            return view.hashMapImage.get("StateFlag.png").getImage();
        } else if (cell.getState() == "question") {
            if (cell.isViewed()) {
                return view.hashMapImage.get("StateQuestionViewed.png").getImage();
            }
            return view.hashMapImage.get("StateQuestion.png").getImage();
        } else if (cell.getState() == "opened") {
            if (cell.isMined()) {
                return view.hashMapImage.get("StateBombThis.png").getImage();
            }
            return view.hashMapImage.get("State" + cell.getCounter() + ".png").getImage();
        }

        return null;
    }

    //Псевдоавтомат, который по позиции x и y, выдает номер ячейки в Модели
    public Point getPosition(int x, int y) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            return new Point(x / CELL_SIZE, y / CELL_SIZE);
        }
        return null;
    }
}
