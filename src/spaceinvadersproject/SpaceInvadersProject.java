/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersproject;

import java.awt.Color;
import spaceinvadersproject.GUI.MainMenuFrame;

/**
 *
 * @author Karakayn
 */
public class SpaceInvadersProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainMenuFrame mf = new MainMenuFrame();
        mf.getContentPane().setBackground(Color.BLACK);

        mf.setVisible(true);
    }
    
}
