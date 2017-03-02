/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersproject.GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Panel who load an image
 * @author Karakayn
 */
public class JImagePanel extends javax.swing.JPanel {

    private BufferedImage picture;
    
    /**
     * Creates new form JImagePanel
     */
    public JImagePanel() {
        initComponents();
        setImage("ressources/Accueil.png");
    }
    
    public JImagePanel(String path) {
        initComponents();
        setImage(path);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMaximumSize(new java.awt.Dimension(800, 100));
        setMinimumSize(new java.awt.Dimension(800, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    public void setImage(String pathAndName)
    {
        try {                
          picture = ImageIO.read(new File(pathAndName));
          JLabel wIcon = new JLabel(new ImageIcon(picture));
        } catch (IOException ex) {
             JLabel wIcon = new JLabel("Pas d'image");
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(picture, 0, 0, this); // see javadoc for more info on the parameters            
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
