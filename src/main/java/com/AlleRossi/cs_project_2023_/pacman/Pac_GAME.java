package com.AlleRossi.cs_project_2023_.pacman;

// run the code here to enjoy new PacMan
// disclaimer: this project is for educational purposes only all rights reserved to the respectful owners of both game and images used in this code
//please read the MIT license on GitHub --> https://github.com/AlleRossi/CS_Project_2023/blob/master/LICENSE

public class Pac_GAME {
    //game starter
    public static MyFrame StartGame() {
        return new MyFrame();
    }

    public static void main(String[] args) {
        MyFrame game = StartGame();
        game.BeginningScreen();


    }
}