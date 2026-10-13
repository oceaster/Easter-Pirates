package ace.actually.pirates.easter;

/** Pure progression contract. Not yet connected to Minecraft events or ship factories. */
public final class FleetRules {
    public enum Vessel { VANILLA_BOAT, SHIPPY_VESSEL, LARGE_FLEET_SHIP }
    public enum Acquisition { BUILD, PURCHASE, CAPTURE }
    public enum Crew { HOSTILE_NPC_PIRATES, PLAYER_CREW }
    private FleetRules() {}
    public static boolean allows(Vessel vessel, Acquisition method) {
        if (vessel == null || method == null) throw new IllegalArgumentException("Missing acquisition data");
        return vessel != Vessel.LARGE_FLEET_SHIP || method == Acquisition.CAPTURE;
    }
    /** Player faction never exempts someone from a hostile encounter crew. */
    public static boolean hostileToPlayer(Crew crew, boolean playerIsPirate) {
        if (crew == null) throw new IllegalArgumentException("Missing crew allegiance");
        return crew == Crew.HOSTILE_NPC_PIRATES;
    }
    /** Ownership/atomic transfer belongs to the server adapter, not this eligibility rule. */
    public static boolean canClaim(boolean encounterProvenance, boolean boarded,
                                   int activeHostileDefenders, boolean helmSecured) {
        if (activeHostileDefenders < 0) throw new IllegalArgumentException("Negative defender count");
        return encounterProvenance && boarded && activeHostileDefenders == 0 && helmSecured;
    }
}
