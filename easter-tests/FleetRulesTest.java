import ace.actually.pirates.easter.FleetRules;
import static ace.actually.pirates.easter.FleetRules.*;

public class FleetRulesTest {
    public static void main(String[] args) {
        // Expected rows come from Owen's progression, not the implementation's formula.
        boolean[][] allowed = {{true,true,true},{true,true,true},{false,false,true}};
        for (Vessel vessel : Vessel.values()) for (Acquisition method : Acquisition.values()) {
            require(FleetRules.allows(vessel, method) == allowed[vessel.ordinal()][method.ordinal()],
                    vessel + "/" + method);
        }
        require(hostileToPlayer(Crew.HOSTILE_NPC_PIRATES, true), "NPC pirates attack player pirates");
        require(hostileToPlayer(Crew.HOSTILE_NPC_PIRATES, false), "NPC pirates attack other player factions");
        require(!hostileToPlayer(Crew.PLAYER_CREW, true), "recruited crew is a separate allegiance");
        require(!hostileToPlayer(Crew.PLAYER_CREW, false), "recruited crew is not a hostile encounter");
        require(canClaim(true,true,0,true), "successful boarding can claim an encounter hull");
        require(!canClaim(false,true,0,true), "no large-ship crafting bypass");
        require(!canClaim(true,false,0,true), "no remote ownership claim");
        require(!canClaim(true,true,1,true), "one surviving defender prevents premature claim");
        require(!canClaim(true,true,0,false), "helm control is required");
        System.out.println("Fleet acquisition, faction-independent hostility and capture eligibility PASS");
    }
    static void require(boolean result, String message) {
        if (!result) throw new AssertionError(message);
    }
}
