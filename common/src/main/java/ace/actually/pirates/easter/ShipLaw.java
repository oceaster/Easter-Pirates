package ace.actually.pirates.easter;

/**
 * Consequences of completed actions on registered named ships.
 * This never blocks piloting, boarding, looting or damaging a vessel.
 * The future adapter must map the owner's shared home family/guest roster to Relation.
 */
public final class ShipLaw {
    public enum Relation { OWNER, FAMILY, GUEST, STRANGER }
    public enum Action { PILOT, TAKE_FROM_STORAGE }
    public record Consequence(boolean crime, boolean immediatePiracy) {
        public boolean actionBlocked() { return false; }
    }
    private static final Consequence LAWFUL = new Consequence(false, false);
    private static final Consequence THEFT = new Consequence(true, false);
    private static final Consequence PIRACY = new Consequence(true, true);
    private ShipLaw() {}

    public static Consequence after(boolean namedAndRegistered, Relation relation,
                                    boolean ownerOnline, Action completedAction) {
        if (relation == null || completedAction == null) throw new IllegalArgumentException("Missing law evidence");
        if (!namedAndRegistered) return LAWFUL;
        boolean trusted = relation == Relation.OWNER || relation == Relation.FAMILY;
        if (completedAction == Action.PILOT) {
            boolean invitedPilot = relation == Relation.GUEST && ownerOnline;
            return trusted || invitedPilot ? LAWFUL : PIRACY;
        }
        return trusted ? LAWFUL : THEFT;
    }
}
