package tower.of.ascension.object;

import org.newdawn.slick.geom.*;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import tower.of.ascension.states.Game;
import tower.of.ascension.states.Play;

public class GameObject {

    protected SpriteSheet sprite;
    protected Shape hitBox;
    protected Image image;

    public boolean moveable;

    private Animation current;
    private Animation idle;
    private Animation movingDown;
    private Animation movingRight;
    private Animation movingUp;
    private Animation movingLeft;

    protected float pos_X;
    protected float pos_Y;

    protected boolean boss;

    //Empty Constructor
    public GameObject() {

    }

    //Enemy GameObject Constructor
    public GameObject(SpriteSheet sprite, float posX, float posY, boolean boss) {
        this.sprite = sprite;
        this.pos_X = posX;
        this.pos_Y = posY;
        this.boss = boss;
        this.idle = new Animation(sprite, 0, 0, 0, 0, true, 60, true);
        this.movingDown = new Animation(sprite, 0, 0, 3, 0, true, 120, true);
        this.movingLeft = new Animation(sprite, 0, 0, 3, 0, true, 120, true);
        this.movingRight = new Animation(sprite, 0, 1, 3, 1, true, 120, true);
        this.movingDown.setPingPong(true);
        this.movingLeft.setPingPong(true);
        this.movingRight.setPingPong(true);
        this.current = idle;
        if (boss) {
            this.hitBox = new Rectangle(this.pos_X, this.pos_Y, 64, 64);
        } else {
            this.hitBox = new Rectangle(this.pos_X, this.pos_Y, 32, 32);
        }
    }

    //Player GameObject Constructor
    public GameObject(SpriteSheet sprite) {
        this.sprite = sprite;
        this.pos_X = (Game.MIDW - 16);
        this.pos_Y = (Game.MIDH - 16);
        this.idle = new Animation(sprite, 1, 0, 1, 0, true, 100, true);
        this.movingDown = new Animation(sprite, 0, 0, 3, 0, true, 100, true);
        this.movingRight = new Animation(sprite, 0, 1, 3, 1, true, 100, true);
        this.movingUp = new Animation(sprite, 0, 2, 3, 2, true, 100, true);
        this.movingLeft = new Animation(sprite, 0, 3, 3, 3, true, 100, true);
        this.current = idle;
        this.hitBox = new Rectangle(this.pos_X, this.pos_Y, sprite.getSprite(0, 0).getWidth(), sprite.getSprite(0, 0).getHeight());
    }

    public void update() {
        this.setIdle();
        this.hitBox.setLocation(this.pos_X, this.pos_Y);
    }

    public void render() {
        //this.current.draw(this.pos_X, this.pos_Y);
    }

    public void moveUp(int speed) {
        this.pos_Y -= speed * .07f;
        if (pos_Y < 0) {
            pos_Y += speed * .07f;
        }
        //this.setMoveDown();
    }

    public void moveLeft(int speed) {
        this.pos_X -= speed * .07f;
        if (pos_X < 0) {
            pos_X += speed * .07f;
        }
        this.setMoveLeft();
    }

    public void moveDown(int speed) {
        this.pos_Y += speed * .05f;
        if (pos_Y > Play.mapBottom - 20) {
            pos_Y -= speed * .07f;
        }
        //this.setMoveDown();
    }

    public void moveRight(int speed) {
        this.pos_X += speed * .07f;
        if (pos_X > Play.mapRight - 16) {
            pos_X -= speed * .07f;
        }
        this.setMoveRight();
    }

    public void setPos_X(float x) {
        this.pos_X = x;
    }

    public void setPos_Y(float y) {
        this.pos_Y = y;
    }

    public float getPos_X() {
        return this.pos_X;
    }

    public float getPos_Y() {
        return this.pos_Y;
    }

    public SpriteSheet getSprite() {
        return this.sprite;
    }

    public Animation getIdle() {
        return this.current;
    }

    public void setIdle() {
        this.current = this.idle;
    }

    public void setMoveUp() {

        this.current = this.movingUp;
    }

    public void setMoveDown() {
        this.current = this.movingDown;
    }

    public void setMoveLeft() {
        this.current = this.movingLeft;
    }

    public void setMoveRight() {
        this.current = this.movingRight;
    }

    public Shape getHitBox() {
        return this.hitBox;
    }

    public Image getImage() {
        return this.image;
    }
}
