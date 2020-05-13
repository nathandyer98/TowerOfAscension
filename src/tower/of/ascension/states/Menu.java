package tower.of.ascension.states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Menu extends BasicGameState {

    Image playNow;
    Image exitGame;

    String mouse = "No input!";

    public Menu(int state) {
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        playNow = new Image("res/playNow.png");
        exitGame = new Image("res/exitGame.png");

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawString(mouse, 360, 50);
        g.drawString("Tower of Ascension", 100, 50);
        playNow.draw(Game.MIDW - 75, 100, 150, 50);
        exitGame.draw(Game.MIDW - 75, 200, 150, 50);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        int xpos = Mouse.getX();
        int ypos = Mouse.getY();

        mouse = "Mouse position x " + xpos + " y: " + ypos;
        
        //play button
        if ((xpos > (Game.MIDW - 75) && xpos < (Game.MIDW + 75)) && (ypos > 211 && ypos < 261)) {
            if (Mouse.isButtonDown(0)) {
                sbg.enterState(1);
            }
        }
        //exit button
        if ((xpos > (Game.MIDW - 75) && xpos < (Game.MIDW + 75)) && (ypos > 111 && ypos < 161)) {
            if (Mouse.isButtonDown(0)) {
                System.exit(0);
            }
        }
    }

}
