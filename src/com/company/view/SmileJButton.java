package com.company.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SmileJButton extends JButton {
    //иконка-состояние(это название состояния,которое отображает иконку на кнопке)
    private Map<String, ImageIcon> NormalStates;//Хеш-таблица(иконка-состояние= картинка для кнопки)
    private Map<String, ImageIcon> PressedStates;//Хеш-таблица(иконка-состояние нажатой кнопки= картинка для кнопки)

    public SmileJButton() {
        super();
        NormalStates = new HashMap<String, ImageIcon>();
        PressedStates = new HashMap<String, ImageIcon>();
        setBorderPainted(false);
        setContentAreaFilled(false);
    }

    //Добавляет иконку-состояние для нормальной кнопки
    public void setNormalState(String state, ImageIcon imageIcon) {
        NormalStates.put(state, imageIcon);
    }

    //Добавляет иконку-состояние для нажатой кнопки
    public void setPressedState(String state, ImageIcon imageIcon) {
        PressedStates.put(state, imageIcon);
    }

    //Устанавливает иконку-состояние для нормальной кнопки
    public void establishNormalState(String state) {
        ImageIcon image = NormalStates.get(state);
        if (image != null)
            setIcon(image);
    }

    //Устанавливает иконку-состояние для нажатой кнопки
    public void establishPressedState(String state) {
        ImageIcon image = PressedStates.get(state);
        if (image != null)
            setPressedIcon(image);
    }

    //Отрисовает иконку-состояние на кнопки, если она зажата или в нормальном состояние
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        ImageIcon image;
        if (getModel().isPressed())
            image = (ImageIcon) getPressedIcon();
        else
            image = (ImageIcon) getIcon();

        g2d.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}
