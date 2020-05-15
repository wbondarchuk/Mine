package com.company.controller;

import com.company.view.MinesweeperView;
import com.company.model.MinesweeperCell;
import com.company.model.MinesweeperModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Controller extends MouseAdapter {

    private MinesweeperModel model;
    private MinesweeperView view;
    private MinesweeperCell PastViewedCell;//Предыдущая просматриваемая ячейка
    private boolean dragging;//Движение зажатой левой кнопки мыши

    public Controller(MinesweeperModel model) {
        this.model = model;
    }

    //Добавление в контроллер Представление
    public void setView(MinesweeperView view) {
        this.view = view;
    }

    //Обработка выбора уровня сложности
    public void selectLevelDifficulty(ItemEvent e) {
        JRadioButtonMenuItem buttonMenuItem = (JRadioButtonMenuItem) e.getSource();
        if (buttonMenuItem.getText().equals("Новичок")) {
            model.setSpecifications(9, 9);
        } else if (buttonMenuItem.getText().equals("Любитель")) {
            model.setSpecifications(16, 16);
        } else if (buttonMenuItem.getText().equals("Профессионал")) {
            model.setSpecifications(16, 31);
        }
        startNewGame();

    }

    //Начало новый игры, вызывается при нажатие на смайлик, если при изменение уровня сложности
    public void startNewGame() {
        model.StartGame();
        view.gameField.FieldInitialization();
        view.menuGame.MenuGameInitialization();
        view.pack();
        view.repaint();
    }

    //Эти методы контроллера отвечают за обработку действий пользователя на поле игры
    //Пользователь нажал на правую кнопку мыши и находился в зоне поля, то выполнить деййствия ...- это обрабатывает mousePressed
    //Пользователь нажал на левую кнопку мыши - это событие делятся на три метода mousePressed,mouseReleased,mouseDragged
    //mousePressed - в любом случае устанавливается флаг dragging=true,который отвечает за то,что нажата левая клавиша мыши и она движется
    //Если мы попали в поле и если ячейка закрыта или вопрос,то ячейки она становится просматриваемой и запоминается,
    // как прошлая просмотренная ячейка(PastViewedCell)
    //mouseDragged-если мы нажали на левую кнопку мыши и движемся, если мы не в зоне поля, следовательно PastViewedCell
    //становится не просматриваемой(PastViewedCell.setViewed(false)) и PastViewedCell=null
    //А если мы в поле и ячейка стала другой,то предыдущая ячейка становится не просматриваемой, а новая просматриваемой
    //mouseReleased-обрабатывает отпускание левой кнопки мыши
    //Если мы в зоне , то открываем ячейку и присваеваем PastViewedCell=null и dragging=false
    @Override
    public void mousePressed(MouseEvent e) {
        if (!model.isFinishGame()) {
            Point position = view.gameField.getPosition(e.getX(), e.getY());
            //Если нажата правая кнопка мыши и игра еще не закончена
            if (position != null) {
                int colum = (int) position.getX();
                int row = (int) position.getY();
                MinesweeperCell cell = model.getCell(row, colum);
                if (e.getButton() == MouseEvent.BUTTON3 && !dragging && (cell.getState() != "opened")) {
                    model.nextCellMark(row, colum);
                    view.repaint();
                }
                //если нажата левая кнопка мыши,игра не закончена и попал в поле
                else if (e.getButton() == MouseEvent.BUTTON1 && (cell.getState() == "closed" || cell.getState() == "question")) {
                    PastViewedCell = cell;
                    cell.setViewed(true);
                    view.repaint();
                }
            }
            //Если нажата левая кнопка мыши,игра не закончена и не попал в поле
            if (e.getButton() == MouseEvent.BUTTON1) {
                dragging = true;
            }

        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        if (!model.isFinishGame() && e.getButton() == MouseEvent.BUTTON1) {
            Point position = view.gameField.getPosition(e.getX(), e.getY());
            if (position != null) {
                int colum = (int) position.getX();
                int row = (int) position.getY();
                MinesweeperCell cell = model.getCell(row, colum);
                if (cell.getState() == "closed" || cell.getState() == "question") {
                    model.openCell(row, colum);
                }
                cell.setViewed(false);
                view.repaint();
            }
            PastViewedCell = null;
            dragging = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragging) {
            Point position = view.gameField.getPosition(e.getX(), e.getY());
            if (position != null) {
                int colum = (int) position.getX();
                int row = (int) position.getY();
                MinesweeperCell cellNow = model.getCell(row, colum);
                if (PastViewedCell != cellNow) {
                    cellNow.setViewed(true);
                    if (PastViewedCell != null)
                        PastViewedCell.setViewed(false);
                    PastViewedCell = cellNow;
                    view.repaint();
                }
                return;
            }
            if (PastViewedCell != null) {
                PastViewedCell.setViewed(false);
                PastViewedCell = null;
                view.repaint();
            }

        }
    }
}
