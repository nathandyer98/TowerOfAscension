package tower.of.ascension.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameOver extends BasicGameState {

    SpriteSheet gameOverSprite;
    Animation gameOverAnim;

    public GameOver(int state) {
    }

    @Override
    public int getID() {
        return 2;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        gameOverSprite = new SpriteSheet("res/gameOverAnim.png", 640, 480);
        gameOverAnim = new Animation (gameOverSprite, 0,0, 4, 2, true, 60, true);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        
        this.gameOverAnim.draw(0, 0);
        
        g.setColor(Color.white);
        g.drawString("GAME OVER", Game.MIDW - 45, Game.MIDH - 100 + 20);
        g.drawString("Final Score:", Game.MIDW - 50, Game.MIDH - 60+ 20);
        g.drawString("" + Play.score, Game.MIDW - 5, Game.MIDH - 45+ 20);
        g.drawString("Wave Reached:", Game.MIDW - 55, Game.MIDH - 30+ 20);
        g.drawString("" + Play.wave, Game.MIDW - 5, Game.MIDH - 15+ 20);
        g.drawString("Enemies Killed:", Game.MIDW - 60, Game.MIDH+ 20);
        g.drawString("" + Play.enemiesKilled, Game.MIDW - 5, Game.MIDH + 15+ 20);

        g.drawString("(P) Play Again", 100, 380);
        g.drawString("(M) Main Menu", Game.MIDW - 50, 380);
        g.drawString("(Q) Quit Game", 420, 380);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_P)) {
            sbg.getState(1).init(gc, sbg);
            sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());

        }
        if (input.isKeyDown(Input.KEY_M)) {
            sbg.getState(0).init(gc, sbg);
            sbg.enterState(0, new FadeOutTransition(), new FadeInTransition());

        }

        if (input.isKeyDown(Input.KEY_Q)) {
            System.exit(0);

        }
    }
}
