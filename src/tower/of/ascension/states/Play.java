package tower.of.ascension.states;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Play extends BasicGameState {

    boolean quit = false;
    Image worldMap;

    Animation me, movingUp, movingDown, movingLeft, movingRight;
    int[] duration = {200, 200};
    float charPosX = 0;
    float charPosY = 0;
    float shiftX = charPosX + Game.MIDW;
    float shiftY = charPosY + Game.MIDH;


    public Play(int state) {
    }

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        worldMap = new Image("res/world.png");
        Image[] walkUp = {new Image("res/buckysBack.png"), new Image("res/buckysBack.png")};
        Image[] walkDown = {new Image("res/buckysFront.png"), new Image("res/buckysFront.png")};
        Image[] walkLeft = {new Image("res/buckysLeft.png"), new Image("res/buckysLeft.png")};
        Image[] walkRight = {new Image("res/buckysRight.png"), new Image("res/buckysRight.png")};

        movingUp = new Animation(walkUp, duration, false);
        movingDown = new Animation(walkDown, duration, false);
        movingLeft = new Animation(walkLeft, duration, false);
        movingRight = new Animation(walkRight, duration, false);
        me = movingDown;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        worldMap.draw(charPosX, charPosY);
        me.draw(shiftX, shiftY);
        g.drawString("X: " + charPosX + "\nY: " + charPosY, 400, 20);

        if (quit == true) {
            g.drawString("Resume (R)", 250, 100);
            g.drawString("Main Menu (M)", 250, 120);
            g.drawString("Quit game (Q)", 250, 140);
            if (quit == false) {
                g.clear();
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();

        //Up
        if (input.isKeyDown(Input.KEY_W)) {
            me = movingUp;
            charPosY += delta * .1f;
            if (charPosY > 180) {
                charPosY -= delta * .1f;
            }
        }
        //Left
        if (input.isKeyDown(Input.KEY_A)) {
            me = movingLeft;
            charPosX += delta * .1f;
            if (charPosX > 320) {
                charPosX -= delta * .1f;
            }
        }
        //Down
        if (input.isKeyDown(Input.KEY_S)) {
            me = movingDown;
            charPosY -= delta * .1f;
            if (charPosY < -581) {
                charPosY += delta * .1f;
            }
        }
        //Right
        if (input.isKeyDown(Input.KEY_D)) {
            me = movingRight;
            charPosX -= delta * .1f;
            if (charPosX < -840) {
                charPosX += delta * .1f;
            }
        }
        //Esc
        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            quit = true;
        }
        //Esc Menu
        if (quit == true) {
            if (input.isKeyDown(Input.KEY_R)) {
                quit = false;
            }
            if (input.isKeyDown(Input.KEY_M)) {
                sbg.enterState(0);
                quit = false;
            }
             if (input.isKeyDown(Input.KEY_Q)) {
                System.exit(0);
            }
        }
    }

}
