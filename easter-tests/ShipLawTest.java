import ace.actually.pirates.easter.ShipLaw;
import static ace.actually.pirates.easter.ShipLaw.*;

public class ShipLawTest {
    public static void main(String[] args) {
        // OWNER, FAMILY, GUEST, STRANGER rows; offline/online columns.
        boolean[][] crimes = {{false,false},{false,false},{true,false},{true,true}};
        boolean[] storageCrimes = {false,false,true,true};
        for (Relation relation : Relation.values()) for (boolean online : new boolean[]{false,true}) {
            var pilot = ShipLaw.after(true, relation, online, Action.PILOT);
            boolean pilotCrime = crimes[relation.ordinal()][online ? 1 : 0];
            require(pilot.crime() == pilotCrime && pilot.immediatePiracy() == pilotCrime,
                    "pilot rule for " + relation + " ownerOnline=" + online);
            var theft = ShipLaw.after(true, relation, online, Action.TAKE_FROM_STORAGE);
            require(theft.crime() == storageCrimes[relation.ordinal()] && !theft.immediatePiracy(),
                    "guest piloting permission must not grant storage rights");
            require(!pilot.actionBlocked() && !theft.actionBlocked(), "law never cancels an action");
            for (Action action : Action.values()) {
                var unnamed = ShipLaw.after(false, relation, online, action);
                require(!unnamed.crime() && !unnamed.actionBlocked(), "unnamed ships do not acquire named-ship law");
            }
        }
        var permitted = ShipLaw.after(true, Relation.GUEST, true, Action.PILOT);
        var disconnected = ShipLaw.after(true, Relation.GUEST, false, Action.PILOT);
        require(!permitted.crime() && disconnected.immediatePiracy(),
                "rechecking ongoing piloting after owner logout must change the consequence");
        System.out.println("Named-ship law: owner/family, online-only guests, storage theft and no protection PASS");
    }
    static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
