package Visual;

import Constants.VisualConstants;
import Engine.Bullet;
import Engine.GameEntity;
import Engine.Tank;
import Enums.GameModes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

/**
 *
 * @author Liviu
 */
public class VisualPanel extends javax.swing.JPanel {

    private long lastTime, totalTime, frames, totalFrames;
    private final long startTime;
    Animator animator;

    private GameModes gameMode;

    public ArrayList<GameEntity> entityList;

    /**
     * Creates new form VisualPanel
     */

    public VisualPanel() {
        initComponents();
        startTime = System.currentTimeMillis();
        animator = new Animator(this);
    }

    public void setGameMode(GameModes gameMode) {
        this.gameMode = gameMode;
        animator.setGameMode(gameMode);
    }

    @Override
    public void paintComponent(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        //drawing all the stuff
        //Testing
        if (entityList != null) {
            synchronized (entityList) {
                for (GameEntity tankAux : entityList) {
                    if (tankAux instanceof Tank && ((Tank) tankAux).inTheGame()) {
                        ((Tank) tankAux).draw(g);
                    }
                }

                for (GameEntity bullet : entityList) {
                    if (bullet instanceof Bullet) {
                        ((Bullet) bullet).draw(g);
                    }
                }
            }
        }

        long currentTime = System.currentTimeMillis();
        totalTime += lastTime == 0 ? 0 : currentTime - lastTime;
        lastTime = currentTime;
        frames++;

        if (totalTime >= 1000) {
            totalFrames = totalTime > 1000 && frames > 60 ? 60 : frames;
            frames = 0;
            totalTime = 0;
        }

        g.setColor(Color.WHITE);
        g.drawString("FPS: " + totalFrames, 2, 11);
        g.drawString("Time: " + (currentTime - startTime) / 1000 + "s", 2, VisualConstants.ENGINE_HEIGHT - 30);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMaximumSize(new Dimension(Constants.VisualConstants.ENGINE_WIDTH, Constants.VisualConstants.ENGINE_HEIGHT));
        setMinimumSize(new Dimension(Constants.VisualConstants.ENGINE_WIDTH, Constants.VisualConstants.ENGINE_HEIGHT));
        setPreferredSize(new Dimension(Constants.VisualConstants.ENGINE_WIDTH, Constants.VisualConstants.ENGINE_HEIGHT));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
