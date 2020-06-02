package tower.of.ascension.states;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import tower.of.ascension.object.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import tower.of.ascension.object.physics.CollisionHandler;

;

public class Play extends BasicGameState {

    Random r = new Random();

    //Sound Variables
    private Sound enemyHit;
    private Sound playerHit;
    private Sound throwRock;
    private Sound lvlUp;
    private Sound enemyGrunt;

    //Objects Istantiations
    public static PlayerObject Player;
    public static ThrowObject Rock;
    private LinkedList<EnemyObject> enemies;
    private LinkedList<ThrowObject> rocks;

    //Menus Boolean
    private boolean quit = false;
    private boolean debug = false;
    private boolean stats = false;

    //Sprites/Images for Objects
    private Image worldMap;
    private SpriteSheet playerSprite;
    private SpriteSheet enemySprite;
    private SpriteSheet slimeSprite;
    private SpriteSheet bossSprite;
    private Image rock;
    private Image cursor;

    //Map Locales
    private float mapX = 0;
    private float mapY = 0;
    public static float mapBottom;
    public static float mapRight;

    //ThrowObject Variables
    private double dx;
    private double dy;
    private double dir;
    private double dist;

    //Player Variables
    private double fireRate;

    //Enemy Variables
    private int enemiesAlive;
    private int enemiesTrack;
    private float eSpawn;
    private float bSpawn;
    private int bossAlive;
    public static int score;
    public static int wave = 1;
    public static int enemiesKilled;

    //Mouse Variable
    private double mouseX;
    private double mouseY;

    public Play(int state) {
    }

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        worldMap = new Image("res/mainMap.png");

        cursor = new Image("res/cursor.png");
        gc.setMouseCursor(cursor, (int) mouseX, (int) mouseY);

        //Game RESET
        score = 0;
        wave = 1;
        enemiesKilled = 0;

        //Game Sound init
        Menu.menuMusic.setVolume(0.1f);
        enemyHit = new Sound("res/sounds/hit.wav");
        enemyGrunt = new Sound("res/sounds/enemyGrunt.wav");
        playerHit = new Sound("res/sounds/playerGrunt.wav");
        lvlUp = new Sound("res/sounds/lvlup.wav");
        throwRock = new Sound("res/sounds/throw.wav");

        //Collision detection for Mobs
        this.mapBottom = worldMap.getHeight() - 32;
        this.mapRight = worldMap.getWidth() - 32;

        //Rocks init
        rock = new Image("res/trock.png");
        rocks = new LinkedList<>();
        fireRate = 0;

        //Boss & Enemies init
        enemiesAlive = 0;
        bossAlive = 0;
        eSpawn = 5;
        bSpawn = 1;
        enemiesTrack = 0;
        slimeSprite = new SpriteSheet("res/slimeSprite.png", 32, 32);
        enemySprite = new SpriteSheet("res/enemySprite.png", 32, 32);
        bossSprite = new SpriteSheet("res/bossSprite.png", 32, 32);
        enemies = new LinkedList<>();

        //Player init
        playerSprite = new SpriteSheet("res/playerSprite.png", 32, 32);
        Player = new PlayerObject(playerSprite);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        worldMap.draw(mapX, mapY);

        //Enemies Render
        for (EnemyObject e : enemies) {
            if (e.isAlive()) {
                e.buffEnemy(wave);
                e.render(g, mapX, mapY);
                if (debug) {
                    e.debug(g);
                }
            }
        }

        //Rocks Render
        for (ThrowObject t : rocks) {
            if (t.isActive()) {
                t.render(mapX, mapY);
                if (debug) {
                    t.debug(g);
                }
            }
        }

        //Player Render
        g.setColor(Color.black);
        Player.render(g);

        //Player UI
        g.setColor(Color.white);
        g.drawString("HP:", Game.MIDW - 130, 5);
        g.drawString("Score: " + score, Game.MIDW - 50, 22);
        g.drawString(Player.getHealth() + "/" + Player.getMaxHealth(), Game.MIDW - 98, 5);

        //Level tracker
        g.setColor(Color.lightGray);
        g.fillRect(505, 415, 110, 35);
        g.setColor(Color.black);
        g.drawString("Wave:  " + wave, 510, 415);
        g.drawString("Enemies: " + ((enemiesAlive + bossAlive) - enemiesTrack), 510, 430);

        //Stats UI
        if (stats) {
            g.setColor(Color.black);
            g.drawRect(500, 300, 120, 105);
            g.setColor(Color.lightGray);
            g.fillRect(501, 301, 119, 104);
            g.setColor(Color.black);
            g.drawRect(503, 303, 114, 20);
            g.setColor(Color.gray);
            g.fillRect(504, 304, 113, 19);
            g.setColor(Color.black);
            g.drawString("STATS", 535, 305);
            g.drawString("(1)Health+:" + Player.getHpBuff(), 500, 325);
            g.drawString("(2)Attack+:" + Player.getDmgBuff(), 500, 340);
            g.drawString("(3)Speed +:" + Player.getSpeedBuff(), 500, 355);
            g.drawString("(4)Atk_S +:" + Player.getROFBuff(), 500, 370);
            g.drawString(" Points: " + Player.getPoints(), 500, 385);
            if (stats == false) {
                g.clear();
            }
        }

        //Debug 
        if (debug == true) {
            g.setColor(Color.white);

            g.drawString("Mouse Angle: " + dir, 10, 410);
            g.drawString("Pos X: " + mapX + " Pos Y: " + mapY, 10, 425);
            //g.drawString("Rocks: " + rockSize, 10, 325);
            //g.drawString("Dist: " + this.dist, 10, 340);
            g.drawString("Speed: " + (int)Player.getSpeed(), 10, 440);
            g.drawString("| Attack: " + Player.getDamage(), 110, 440);
            g.drawString("| R-O-F: " + (int) Player.getRateOfFire(), 225, 440);
            g.draw(Player.getHitBox());

            if (debug == false) {
                g.clear();
            }
        }

        //Menu
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

        //mouseX = input.getAbsoluteMouseX();
        //mouseY = input.getAbsoluteMouseY();

        //Player Update and Handler
        if (wave % 5 == 1 && enemiesAlive <= 0) {
            Player.resetHealth();
        }
        Player.update();
        if (Player.getHealth() <= 0) {
            sbg.getState(2).init(gc, sbg);
            sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
        }

        //Enemy Updater/Spawner & Enemy - Player Collision
        if (wave % 5 == 0 && bossAlive < bSpawn) {
            enemies.add(new EnemyObject(bossSprite, r.nextInt(worldMap.getWidth()), r.nextInt(worldMap.getHeight()), true));
            bossAlive++;
        } else if (wave % 5 != 0 && enemiesAlive < eSpawn) {
            if (r.nextBoolean()) {
                enemies.add(new EnemyObject(enemySprite, r.nextInt(worldMap.getWidth() - 16), r.nextInt(worldMap.getHeight() - 20), false));
                enemiesAlive++;
            } else {
                enemies.add(new EnemyObject(slimeSprite, r.nextInt(worldMap.getWidth() - 16), r.nextInt(worldMap.getHeight() - 20), false));
                enemiesAlive++;
            }
        }

        if (enemiesTrack >= eSpawn) {
            this.wave++;
            Player.incrementPoints();
            eSpawn += 1.25;
            enemiesTrack = 0;
            enemiesAlive = 0;
        } else if (enemiesTrack >= bSpawn && wave % 5 == 0) {
            this.wave++;
            Player.incrementPoints();
            bSpawn += 0.65;
            bossAlive = 0;
            enemiesTrack = 0;
        }
        Iterator<EnemyObject> e = enemies.iterator();
        while (e.hasNext()) {
            EnemyObject o = e.next();
            if (o.isAlive()) {
                if (o.getHitBox().intersects(Player.getHitBox()) && o.getHitRate() == 0) {
                    Player.takeDamage((int) o.getDamage());
                    this.playerHit.play(1, 0.1f);
                    o.setHitRate(150);
                }
                o.update();
            } else {
                e.remove();
                score += o.getScore();
                enemiesTrack++;
                enemiesKilled++;
                this.enemyGrunt.play(r.nextFloat() + 1, 0.2f);
            }
        }

        //Get Mouse input
        dx = input.getMouseX() - Game.MIDW;
        dy = input.getMouseY() - Game.MIDH;
        dir = Math.atan2(dy, dx);

        //Rocks Updater
        Iterator<ThrowObject> i = rocks.iterator();
        while (i.hasNext()) {
            ThrowObject r = i.next();
            if (r.isActive()) {
                r.update();
                dist = r.getDistance();
                if (CollisionHandler.handle(r, enemies)) {
                }

            } else {
                this.enemyHit.play(0.8f / (float) (Player.getRateOfFire() * 0.01), 0.1f);
                i.remove();
            }
        }
        if (fireRate > 0) {
            fireRate--;
        }

        //Right-Mouse Click
        if (input.isMouseButtonDown(0) && fireRate <= 0) {
            rocks.add(new ThrowObject(rock, (Game.MIDW - (int) mapX), (Game.MIDH - (int) mapY), dir));
            throwRock.play(1.5f / (float) (Player.getRateOfFire() * 0.01), 0.1f);
            fireRate = Player.getRateOfFire();
        }
        //Up
        if (input.isKeyDown(Input.KEY_W)) {
            Player.setMoveUp();
            mapY += Player.getSpeed() * .1f;
            if (mapY > 198) {
                mapY -= Player.getSpeed() * .1f;
            }
        }
        //Left
        if (input.isKeyDown(Input.KEY_A)) {
            Player.setMoveLeft();
            mapX += Player.getSpeed() * .1f;
            if (mapX > 285) {
                mapX -= Player.getSpeed() * .1f;
            }
        }
        //Down
        if (input.isKeyDown(Input.KEY_S)) {
            Player.setMoveDown();
            mapY -= Player.getSpeed() * .1f;
            if (mapY < -683) {
                mapY += Player.getSpeed() * .1f;
            }
        }
        //Right
        if (input.isKeyDown(Input.KEY_D)) {
            Player.setMoveRight();
            mapX -= Player.getSpeed() * .1f;
            if (mapX < -768) {
                mapX += Player.getSpeed() * .1f;
            }
        }

        //Esc + Menu
        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            quit = true;
        }

        if (quit == true) {
            if (input.isKeyDown(Input.KEY_R)) {
                quit = false;
            }
            if (input.isKeyDown(Input.KEY_M)) {
                sbg.getState(1).init(gc, sbg);
                sbg.enterState(0);
                quit = false;
            }
            if (input.isKeyDown(Input.KEY_Q)) {
                System.exit(0);
            }
        }
    }

    @Override
    public void keyPressed(int key, char code) {
        System.out.println("key: " + key);

        //Debug
        if (key == 61) {
            debug = !debug;
        }

        if (key == 45) {
            stats = !stats;
        }
        //Stats + Menu
        if (stats) {
            if (key == 2 && Player.checkPoints()) {
                Player.HPBuff();
                this.lvlUp.play(1, 0.2f);
            }
            if (key == 3 && Player.checkPoints()) {
                Player.DmgBuff();
                this.lvlUp.play(1, 0.2f);
            }
            if (key == 4 && Player.checkPoints()) {
                Player.SpeedBuff();
                this.lvlUp.play(1, 0.2f);
            }
            if (key == 5 && Player.checkPoints()) {
                Player.ROFBuff();
                this.lvlUp.play(1, 0.2f);
            }
        }
    }
}
