# Data layer and backend-ready seam

Type: grilling
Status: open
Blocked by: 02, 03, 06

## Question

How is Training persistence shaped so Room can later be swapped for (or fronted by) a backend? Covers: repository interface location and contract (Flow for the list, suspend for writes), data source split, Room entity/DAO placement, mappers, identity type (Long autoincrement vs UUID — matters for undo-with-same-identity and a future backend), DTO conventions for later. Which parts of `android-data-layer` are accepted or overridden?

Resolve point by point, then rewrite `~/.claude/skills/android-data-layer/SKILL.md` with `/mattpocock-skills:writing-for-agents`. Decide whether this warrants an ADR.
