
package tower.of.ascension.object.physics;

import java.util.LinkedList;
import tower.of.ascension.object.EnemyObject;
import tower.of.ascension.object.ThrowObject;
import static tower.of.ascension.states.Play.Player;

public class CollisionHandler {
    
        public static boolean handle(ThrowObject t, LinkedList<EnemyObject> e) {
        for (int i = 0; i < e.size(); i++) {
            if (t.getHitbox().intersects(e.get(i).getHitBox())) {
                t.setInactive();
                e.get(i).takeDamage(Player.getDamage());
                if (e.get(i).getHealth() <= 0) {
                    e.get(i).setDead();
                }return true;
            }
        }return false;
    }
    
}
