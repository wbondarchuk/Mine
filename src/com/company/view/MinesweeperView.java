package com.company.view;

import com.company.controller.Controller;
import com.company.model.MinesweeperModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MinesweeperView extends JFrame {

    public static final int INTEND = 5;//Отступ от JFrame
    private Controller controller;
    private MinesweeperModel model;
    public GameField gameField;
    public MenuGame menuGame;
    public Map<String, ImageIcon> hashMapImage;//Хеш-таблица, название изображения-это ключ, значение -это ImageIcon


    public MinesweeperView(Controller controller, MinesweeperModel model) {
        this.controller = controller;
        this.model = model;
        controller.setView(this);
        try {
            LoadImage("assets");
        } catch (IOException e) {
            e.printStackTrace();
        }
        createInterface();
    }

    //Основной метод отвечающий за весь Интерфейс,расположение GameField,MenuGame,MenuBar
    private void createInterface() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(hashMapImage.get("titleBomb.png").getImage());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(INTEND, INTEND));
        panel.setBorder(new EmptyBorder(INTEND, INTEND, INTEND, INTEND));
        gameField = new GameField(model, controller, this);
        menuGame = new MenuGame(model, this, controller);
        panel.add(menuGame, BorderLayout.NORTH);
        panel.add(gameField, BorderLayout.CENTER);
        getContentPane().add(panel);
        setJMenuBar(createMenuBar());
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    //Создаем меню бар для выбора уровня сложности
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu game = new JMenu("Игра");
        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem newbie = (new JRadioButtonMenuItem("Новичок"));
        JRadioButtonMenuItem amateur = new JRadioButtonMenuItem("Любитель");
        JRadioButtonMenuItem professional = new JRadioButtonMenuItem("Профессионал");
        group.add(newbie);
        group.add(amateur);
        group.add(professional);
        game.add(newbie);
        game.add(amateur);
        game.add(professional);
        menuBar.add(game);

        ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                controller.selectLevelDifficulty(e);
            }
        };
        newbie.addItemListener(itemListener);
        amateur.addItemListener(itemListener);
        professional.addItemListener(itemListener);
        newbie.setSelected(true);
        return menuBar;
    }

    //Загружаем все изображения
    private void LoadImage(String path) throws IOException {
        hashMapImage = new HashMap<String, ImageIcon>();
        File dir = new File(path);
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            BufferedImage image = ImageIO.read(files[i]);
            hashMapImage.put(files[i].getName(), new ImageIcon(image));
        }
    }
}
