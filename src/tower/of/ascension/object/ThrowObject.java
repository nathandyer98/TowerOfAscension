package tower.of.ascension.object;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class ThrowObject {

    private final Image image;
    private Shape hitBox;

    private boolean active;

    private double xo, yo;
    private double angle;
    private double nx, ny;
    private double x;
    private double y;
    private double rx;
    private double ry;
    private float mapX;
    private float mapY;

    private double speed, range;

    public ThrowObject(Image image, int x, int y, double dir) {
        this.image = image;
        this.xo = x;
        this.yo = y;
        this.rx = x;
        this.ry = y;
        this.angle = dir;
        this.active = true;

        this.range = 250;
        this.speed = 1.5;
    }

    public ThrowObject() {
        this.image = null;
        this.active = false;
    }

    public void render(float x, float y) {
        this.mapX = x;
        this.mapY = y;
        this.x = this.mapX + xo;
        this.y = this.mapY + yo;
        this.image.draw((float) this.x, (float) this.y);
        this.hitBox = new Circle((float) this.x + 3, (float) this.y + 3, 4);
        //g.draw(this.hitBox);
    }

    public void update() {
        move();
        hitBox.setLocation((float) this.x, (float) this.y);
    }

    public void move() {
        if (active) {
            this.nx = speed * Math.cos(angle);
            this.ny = speed * Math.sin(angle);
            this.xo += this.nx;
            this.yo += this.ny;
            if (getDistance() > this.range) {
                active = false;
            }
        }
    }

    public void debug(Graphics g) {
        g.setColor(Color.white);
        g.draw(hitBox);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setInactive() {
        this.active = false;
    }

    public double getDistance() {
        double dist = 0;
        dist = Math.sqrt(Math.abs((rx - xo) * (rx - xo) + (ry - yo) * (ry - yo)));
        return dist;
    }

    public Shape getHitbox() {
        return this.hitBox;
    }

}
