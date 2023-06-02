package com.AlleRossi.CS_Project_2023.PacMan;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyChecker extends KeyAdapter {
    GamePanel panel;

    public KeyChecker(GamePanel pan) {

        this.panel = pan;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        panel.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        panel.keyReleased(e);
    }
}
