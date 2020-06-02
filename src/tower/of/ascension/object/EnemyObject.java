package tower.of.ascension.object;

import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import tower.of.ascension.object.bars.HealthBar;
import tower.of.ascension.states.Game;

public class EnemyObject extends GameObject {

    //Health Variables
    private HealthBar enemyHealth;
    private double health;
    private double maxHealth;
    private double healthPercent;
    private final float barW = 32;
    private final float barH = 4;
    private float barX;
    private float barY;

    //Combat Variables
    private int hitRate;
    private double damage;
    private boolean alive;
    private int speed;
    private int score;
    private boolean buffed;

    //Random Move-Command
    Random random = new Random();
    private int time;

    //Positioning
    private float x;
    private float y;
    private float mapX;
    private float mapY;
    private int xa;
    private int ya;

    public EnemyObject(SpriteSheet sprite, float posX, float posY, boolean boss) {
        super(sprite, posX, posY, boss);
        this.hitRate = 0;
        this.alive = true;
        this.buffed = false;
        if (boss) {
            this.damage = 25;
            this.speed = 6;
            this.score = 50;
            this.maxHealth = 60;
            this.health = this.maxHealth;

            enemyHealth = new HealthBar((int) this.maxHealth, (int) this.health, this.x, this.y, barW * 2, barH);
        } else {
            this.damage = 10;
            this.speed = 4;
            this.score = 10;
            this.maxHealth = 25;
            this.health = this.maxHealth;
            enemyHealth = new HealthBar((int) this.maxHealth, (int) this.health, this.x, this.y, barW, barH);
        }
    }

    public EnemyObject() {
        this.sprite = null;
        this.alive = false;
        this.maxHealth = 0;
    }

    public void render(Graphics g, float x, float y) {
        this.mapX = x;
        this.mapY = y;
        this.x = this.mapX + pos_X;
        this.y = this.mapY + pos_Y;
        if (this.boss) {
            super.getIdle().draw(this.x, this.y, 64, 64);
            enemyHealth.render(g);
        } else {
            super.getIdle().draw(this.x, this.y);
            enemyHealth.render(g);
        }

    }

    @Override
    public void update() {

        this.randomMove();
        this.chaseMove();
        this.hit();
        this.hitBox.setLocation(x, y);
        enemyHealth.updateHealth((int) health, (int) maxHealth);
        enemyHealth.setLocation(x, y - 5);
        this.speedDecay();

    }

    public void getMaxHealth() {
        //System.out.println("Max: "+this.maxHealth + "HP: " + this.health);
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getMapX() {
        return this.mapX;
    }

    public float getMapY() {
        return this.mapY;
    }

    public double getDamage() {
        return this.damage;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setDead() {
        this.alive = false;
    }

    public double getHealth() {
        return this.health;
    }

    public void takeDamage(int damage) {
        if (this.health <= 0) {
            damage = 0;
        }
        this.health -= damage;
    }

    public int getScore() {
        return this.score;
    }

    public int getHitRate() {
        return this.hitRate;
    }

    public void setHitRate(int hitRate) {
        this.hitRate = hitRate;
    }

    public void hit() {
        if (hitRate > 0) {
            hitRate--;
        }
    }

    // Enemy Chase Move
    public void chaseMove() {
        if ((int) this.x <= Game.MIDW || (int) this.y >= Game.MIDH) {
            super.setMoveDown();
        }
        if ((int) this.x < (Game.MIDW - 16)) {
            moveRight(speed);
        } else if ((int) this.x > (Game.MIDW - 16)) {
            moveLeft(speed);
        }

        if (this.y > (Game.MIDH - 16)) {
            moveUp(speed);
        } else {
            moveDown(speed);
        }

    }

    //Enemy Random Movements
    public void randomMove() {
        time++;
        if (time % (random.nextInt(600) + 400) == 0) {
            xa = random.nextInt(3) - 1;
            ya = random.nextInt(3) - 1;
            if (random.nextInt(4) == 0) {
                xa = 0;
                ya = 0;
            }
        }
        if (ya < 0) {
            moveUp((int) (speed / 2.5));
        } else if (ya > 0) {
            moveDown((int) (speed / 2.5));
        }
        if (xa < 0) {
            moveLeft((int) (speed / 2.5));
        } else if (xa > 0) {
            moveRight((int) (speed / 2.5));
        }
        if (xa == 0 && ya == 0) {
            super.setIdle();
        }
    }

    public void speedDecay() {
        healthPercent = ((double) this.health / (double) this.maxHealth) * 100;
        if (healthPercent < 20) {
            speed = 1;
        }
    }

    public void buffEnemy(int wave) {
        if (buffed) {
        } else {
            double modifier = (wave * .1);
            this.damage += (this.damage * modifier);
            this.maxHealth += (this.maxHealth * modifier) +5 ;
            this.health = this.maxHealth;
            this.score += (int) (this.score * modifier);
            this.speed += (0.4 * modifier);
            buffed = true;
        }
    }

    public void debug(Graphics g) {
        g.setColor(Color.white);
        g.draw(this.hitBox);
        g.drawString("HP : " + (int) this.health + " /" + (int)maxHealth, x, y + 1);
        g.drawString("HP%: " + (int) healthPercent, x, y + 16);
    }

}
