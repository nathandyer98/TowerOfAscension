package tower.of.ascension.states;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Menu extends BasicGameState {

    private Image menuImage;
    private Image menuHTP;
    public static Music menuMusic;

    private boolean info;

    public Menu(int state) {
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        menuImage = new Image("res/menuImage.png");
        menuHTP = new Image("res/howToPlay.png");

        menuMusic = new Music("res/sounds/playMusic.ogg");
        menuMusic.setVolume(0.01f);
        menuMusic.loop();

        info = false;

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        menuImage.draw(0, 0, 640, 480);

        if (info) {
            g.drawImage(menuHTP, 75, 70);
            if (!info) {
                g.clear();
            }
        }

        //g.drawRect(75, 70, 500, 290);
        g.setColor(Color.white);
        g.setLineWidth(1.5f);
        g.drawString("(P) Play Now", 75, 365);
        g.drawString("(H) How-To-Play", 75, 395);
        g.drawString("(Q) Quit Game", 75, 425);

        g.scale(2f, 2f);
        g.drawString("Tower Of Ascension", Game.MIDW - 240, 15);
        g.scale(1f, 1f);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();

        //play button
        if (input.isKeyDown(Input.KEY_P)) {
            sbg.getState(1).init(gc, sbg);
            sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }

        //exit button
        if (input.isKeyDown(Input.KEY_Q)) {
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(int key, char code) {

        //Info Menu
        if (key == 35) {
            info = !info;
        }
    }
}
