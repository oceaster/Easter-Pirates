# Easter Pirates backlog

## EP-01 — matching Fabric/26.2 dependency baseline (not started)
Depends on a working Easter Skies port. Audit vlib, Eureka, Sails and VS/core version
pins and optional class loading. Acceptance: isolated client/server startup with
all required dependencies, without pretending the three forks alone are enough.

## EP-02 — roaming NPC pirate encounters (Shippy implemented; large fleet pending)
2026-10-13: Navy commit d679ddb implements six bounded loaded-chunk Shippy
encounters, patrol/chase movement and hostile NPC crew. The actual 26.2 isolated
server test passed spawn, movement and defender deaths; this runtime is deployed.
This legacy Pirates module's large physics encounters still depend on EP-01.
Crew-controlled vessels at sea: Shippy small encounters and large physics hulls.
NPC pirates attack all players, including player pirates; recruited player crew is
a distinct allegiance. Acceptance: boarding, steering and combat remain functional
with bounded encounter counts and no protected-spawn intrusion.

## EP-03 — persistent capture and player crew (Shippy implemented; large adapter pending)
2026-10-13: the deployed Navy runtime checks surviving defenders/boarding/helm,
transfers capture once, persists owner/name/UUID and recruits captain-aligned crew.
The actual 26.2 server test passed capture rejection/success, reload and recruitment.
These are Shippy entity adapters; they do not yet operate on VS block ships.
Neutralize active defenders, board and secure helm, then atomically transfer control.
Acceptance: racing claimants cannot both win; ownership/crew survive save/restart;
large vessels are never bought or constructed from scratch by players.

## EP-04 — named-ship law integration (Shippy deployed; VS adapter pending)
2026-10-13: Navy observes real controlling-player input and actual Shippy container
mutations, uses HouseAccess rosters, and invokes the existing bounty functions.
Guest owner logout piracy, deduction once per control episode, actual chest theft
and unblocked access passed in the isolated 26.2 server. See Navy FLEET_RUNTIME.md.
Read Easter Skies EASTER_SHIP_LAW.md. Owner/family may pilot any time; guest may pilot
lawfully only while owner is online. Other piloting is immediate piracy. Storage
withdrawal by anyone outside owner/family is theft, even an invited pilot. Observe
completed actions; never cancel access or add hull protection. Test continuing
control after owner logout and avoid per-tick duplicate penalties.

## Source prototypes (compiled, not wired)
FleetRules and ShipLaw express and test the progression/law truth tables. They do
not register ship names, detect piloting/storage moves, create NPC fleets, transfer
ownership or update the live crime system. easter-tests checks the pure rules only.
