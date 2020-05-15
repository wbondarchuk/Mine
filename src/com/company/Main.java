package com.company;

import com.company.view.MinesweeperView;
import com.company.controller.Controller;
import com.company.model.MinesweeperModel;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        MinesweeperModel model = new MinesweeperModel();
        Controller controller = new Controller(model);
        JFrame frame = new MinesweeperView(controller, model);
    }
}
