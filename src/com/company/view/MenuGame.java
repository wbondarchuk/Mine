package com.company.view;

import com.company.controller.Controller;
import com.company.model.MinesweeperModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuGame extends JPanel {
    private static final int SMILE_SIZE = 26;//Размеры смайлика
    private static final Point DIGITALCLOCKFACE_SIZE = new Point(39, 23);//Размеры цифирблата
    private MinesweeperModel model;
    private Controller controller;
    private MinesweeperView view;
    private SmileJButton smile;//Cмайлик
    private DigitalClockFace bombCounter;//Счетчик бомб
    private DigitalClockFace timer;//Представление секундомера
    private Timer stopWatch;//Секундомера

    public MenuGame(MinesweeperModel model, MinesweeperView view, Controller controller) {
        this.model = model;
        this.controller = controller;
        this.view = view;
        setLayout(null);
        MenuGameInitialization();
    }

    //Инициализация игрового меню
    public void MenuGameInitialization() {
        removeAll();
        setPreferredSize(new Dimension((view.gameField.getPreferredSize().width), SMILE_SIZE));
        createSmile();
        createBombCounter();
        createViewTimer();
    }

    //Создание смайлика
    private void createSmile() {
        smile = new SmileJButton();
        smile.setPressedState("Game", view.hashMapImage.get("smileSmilingPressed.png"));
        smile.setNormalState("Game", view.hashMapImage.get("smileSmiling.png"));
        smile.setNormalState("Win", view.hashMapImage.get("smileWin.png"));
        smile.setNormalState("GameOver", view.hashMapImage.get("smileGameOver.png"));
        smile.setNormalState("Zero", view.hashMapImage.get("smileZero.png"));
        smile.establishPressedState("Game");
        smile.establishNormalState("Game");
        smile.setBounds(getPreferredSize().width / 2 - SMILE_SIZE / 2, getPreferredSize().height / 2 - SMILE_SIZE / 2,
                SMILE_SIZE, SMILE_SIZE);
        smile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.startNewGame();
            }
        });
        add(smile);
    }

    //Создание счетчика бомб
    private void createBombCounter() {
        bombCounter = new DigitalClockFace();
        bombCounter.LoadImageForClockFace("-", view.hashMapImage.get("-.png"));
        bombCounter.LoadImageForClockFace("Empty", view.hashMapImage.get("EmptyNumber.png"));
        for (int i = 0; i < 10; i++) {
            bombCounter.LoadImageForClockFace(String.valueOf(i), view.hashMapImage.get(i + ".png"));
        }
        bombCounter.setBounds(0, getPreferredSize().height / 2 - DIGITALCLOCKFACE_SIZE.y / 2,
                DIGITALCLOCKFACE_SIZE.x, DIGITALCLOCKFACE_SIZE.y);
        add(bombCounter);
    }

    //Создание секундомера
    private void createViewTimer() {
        timer = new DigitalClockFace();
        timer.LoadImageForClockFace("-", view.hashMapImage.get("-.png"));
        timer.LoadImageForClockFace("Empty", view.hashMapImage.get("EmptyNumber.png"));
        for (int i = 0; i < 10; i++) {
            timer.LoadImageForClockFace(String.valueOf(i), view.hashMapImage.get(i + ".png"));
        }
        timer.setBounds(getPreferredSize().width - DIGITALCLOCKFACE_SIZE.x,
                getPreferredSize().height / 2 - DIGITALCLOCKFACE_SIZE.y / 2, DIGITALCLOCKFACE_SIZE.x, DIGITALCLOCKFACE_SIZE.y);
        stopWatch = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.setState(timer.getState() + 1);
                timer.repaint();
            }
        });
        add(timer);
    }


    //Синхронизация игрового меню с Моделью
    @Override
    protected void paintComponent(Graphics g) {
        bombCounter.setState(model.getMineCount());//Сколько бомб на поле осталось
        if (model.isFirstStep() == false && stopWatch.isRunning() == false) {
            timer.setState(1);
            stopWatch.start();//Если это не первый шаг и секундомер не запущен, то запускаем
        }

        //Если конец игры,то останавливаем секундомер, и в соответсвие с результатом игры изменяем смайлик
        if (model.isFinishGame()) {
            stopWatch.stop();
            if (model.isGameOver())
                smile.establishNormalState("GameOver");
            else
                smile.establishNormalState("Win");
        }
        //В соотвествие с режимомо в игре, изменяем смайлик(Просматриваем ячейки или нет)
        else {
            if (model.isViewedCell())
                smile.establishNormalState("Zero");
            else
                smile.establishNormalState("Game");
        }
    }
}
