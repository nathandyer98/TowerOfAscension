package tower.of.ascension.object;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import tower.of.ascension.object.bars.HealthBar;
import tower.of.ascension.states.Game;

public class PlayerObject extends GameObject {

    private final HealthBar playerHealth;
    private int health;
    private int maxHealth;
    private int damage;
    private float speed;
    private double rateOfFire;

    private int hpBuff;
    private int dmgBuff;
    private int speedBuff;
    private int rOFBuff;
    private int points;

    private final float barW = 200;
    private final float barH = 17;
    private final float barX = Game.MIDW - (barW / 2);
    private final float barY = 5;


    public PlayerObject(SpriteSheet sprite) {
        super(sprite);
        this.maxHealth = 100;
        this.health = this.maxHealth;
        this.damage = 20;
        this.speed = 5;
        this.rateOfFire = 200;
        this.points = 10;
        
        playerHealth = new HealthBar(this.maxHealth, this.health, barX, barY, barW, barH);
    }

    public void render(Graphics g) {
        super.getIdle().draw(super.getPos_X(), super.getPos_Y());
        playerHealth.render(g);
    }

    @Override
    public void update() {
        this.setIdle();
        playerHealth.updateHealth(health, maxHealth);
        this.hitBox.setLocation(this.pos_X, this.pos_Y);
    }

    public void incrementPoints() {
        this.points++;
    }

    public int getPoints() {
        return this.points;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getHealth() {
        return this.health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public double getRateOfFire() {
        return this.rateOfFire;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void resetHealth() {
        this.health = maxHealth;
    }

    public int getHpBuff() {
        return this.hpBuff;
    }

    public int getDmgBuff() {
        return this.dmgBuff;
    }

    public int getSpeedBuff() {
        return this.speedBuff;
    }

    public int getROFBuff() {
        return this.rOFBuff;
    }

    public void HPBuff() {
        if (checkPoints() && this.hpBuff < 99) {
            if (this.hpBuff <= 10) {
                this.maxHealth += 25;
                this.health += 25;
            } else if (this.hpBuff <= 25) {
                this.maxHealth += 40;
                this.health += 40;
            } else if (this.hpBuff <= 40) {
                this.maxHealth += 50;
                this.health += 50;
            } else if (this.hpBuff <= 80) {
                this.maxHealth += 55;
                this.health += 55;
            } else if (this.hpBuff < 99) {
                this.maxHealth += 60;
                this.health += 60;
            }
            this.hpBuff++;
            this.points--;
        }
    }

    public void DmgBuff() {
        if (checkPoints() && this.dmgBuff < 99) {
            if (this.dmgBuff <= 10) {
                this.damage += 3;
            } else if (this.dmgBuff <= 25) {
                this.damage += 4;
            } else if (this.dmgBuff <= 40) {
                this.damage += 7;
            } else if (this.dmgBuff <= 80) {
                this.damage += 9;
            } else if (this.dmgBuff < 99) {
                this.damage += 12;
            }
            this.dmgBuff++;
            this.points--;
        }
    }

    public void SpeedBuff() {
        if (checkPoints() && this.speedBuff < 99) {
            if (this.speedBuff <= 10) {
                this.speed += 0.15;
            } else if (this.speedBuff <= 25) {
                this.speed += 0.12;
            } else if (this.speedBuff <= 40) {
                this.speed += 0.07;
            } else if (this.speedBuff <= 80) {
                this.speed += 0.04;
            } else if (this.speedBuff < 99) {
                this.speed += 0.02;
            }
            this.speedBuff++;
            this.points--;
        }

    }

    public void ROFBuff() {
        if (checkPoints() && this.rOFBuff < 99) {
            if (this.rOFBuff <= 5) {
                this.rateOfFire -= 15;
            } else if (this.rOFBuff <= 12) {
                this.rateOfFire -= 5;
            } else if (this.rOFBuff <= 25) {
                this.rateOfFire -= 2.5;
            } else if (this.rOFBuff <= 50) {
                this.rateOfFire -= 0.75;
            } else if (this.rOFBuff < 99) {
                this.rateOfFire -= 0.35;
            }
            this.rOFBuff++;
            this.points--;
        }
    }

    public boolean checkPoints() {
        return points > 0;
    }

    public void takeDamage(int damage) {
        if (this.health <= 0) {
            damage = 0;
        }
        this.health -= damage;

    }
}
