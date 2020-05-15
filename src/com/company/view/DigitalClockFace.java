package com.company.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class DigitalClockFace extends JLabel {
    private static final int MAX_STATE = 999;//Максимальное значение на циферблате
    private static final int MINIMUM_STATE = -99;//Минимальное значение на циферблате
    private Map<String, ImageIcon> ImageClockFace;//хеш-таблица, которая по ключу выдает заданое изображение (пустое,-,0,1....9);
    private int state;//Состояние, то есть число, которое отображается на циферблате

    public DigitalClockFace() {
        super();
        state = 0;
        ImageClockFace = new HashMap<String, ImageIcon>();
    }

    //Загружает картинки(как будет выглядить циферблат)(пустое,-,0,1....9)
    public void LoadImageForClockFace(String symbol, ImageIcon imageIcon) {
        ImageClockFace.put(symbol, imageIcon);
    }

    //Получить состояние
    public int getState() {
        return state;
    }

    //Добавить состояние
    public void setState(int state) {
        if (state <= MAX_STATE && state >= MINIMUM_STATE)
            this.state = state;
    }

    //Псевдоавтомат, который по состояние выдает соответсвующее изображение
    private Image getImageForStateClockFace() {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        String stateString = String.valueOf(state);
        int j = 0;
        if (stateString.length() == 1) {
            j = 2;
            for (int i = 0; i < 2; i++)
                g2d.drawImage(ImageClockFace.get("Empty").getImage(), i * getWidth() / 3, 0, getWidth() / 3, getHeight(), null);
        } else if (stateString.length() == 2) {
            j = 1;
            g2d.drawImage(ImageClockFace.get("Empty").getImage(), 0, 0, getWidth() / 3, getHeight(), null);
        }
        for (int i = 0; i < stateString.length(); i++) {
            g2d.drawImage(ImageClockFace.get(String.valueOf(stateString.charAt(i))).getImage(),
                    j * getWidth() / 3, 0, getWidth() / 3, getHeight(), null);
            j++;
        }

        return (new ImageIcon(image).getImage());
    }

    //Отрисовает состояние циферблата
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.drawImage(getImageForStateClockFace(), 0, 0, null);
    }
}
