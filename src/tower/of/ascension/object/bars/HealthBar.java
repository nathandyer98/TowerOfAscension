package tower.of.ascension.object.bars;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class HealthBar {

    private int health;
    private int maxHealth;

    private float posX;
    private float posY;
    private final float width;
    private final float height;
    private float healthPip;


    public HealthBar(int maxHealth, int health, float x, float y, float width, float height) {
        this.health = health;
        this.maxHealth = health;
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.healthPip = (width - 1) / maxHealth;

    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(posX + 1, posY + 1, width - 1, height - 1);

        g.setColor(Color.green);
        g.fillRect(posX + 1, posY + 1, this.health * healthPip, height - 1);

        g.setColor(Color.black);
        g.drawRect(posX, posY, width, height);
        
        //g.drawString("HP: " +health + " /"+maxHealth, posX , posY + 25);
        //g.drawString("PIP: " +healthPip, posX , posY + 40);
    }

    public void update() {

    }

    public void updateHealth(int health, int maxHealth) {
        
        this.health = health;
        this.maxHealth = maxHealth;
        this.healthPip = (width - 1) / (float)maxHealth;

    }

    public void updatePip() {

    }

    public void setLocation(float x, float y) {
        this.posX = x;
        this.posY = y;
    }
}
