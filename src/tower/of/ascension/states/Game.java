package tower.of.ascension.states;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame {

    public static final int SCREENWIDTH = 640;
    public static final int SCREENHEIGHT = 360;
    public static final int MIDW = SCREENWIDTH/2;
    public static final int MIDH = SCREENHEIGHT/2;
    public static final String GAMENAME = "Tower Of Ascension";
    public static final int MENU = 0;
    public static final int PLAY = 1;

    public Game(String gameName) {
        super(GAMENAME);
        this.addState(new Menu(MENU));
        this.addState(new Play(PLAY));
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.getState(MENU).init(gc, this);
        this.getState(PLAY).init(gc, this);
        this.enterState(MENU);
    }

    public static void main(String[] args) {
        AppGameContainer appgc;
        try {
            appgc = new AppGameContainer(new Game(GAMENAME));
            appgc.setDisplayMode(SCREENWIDTH, SCREENHEIGHT, false);
            appgc.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
