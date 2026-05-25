Battle City level TXT format
============================
Grid size: 26 rows x 26 columns
Source conversion: each cell represents an 8x8 area in the original 208x208 PNG.
Recommended rendering in your 416x416 GamePanel: draw each map cell as 16x16 px.
Your tank can stay 32x32 px, so it occupies 2x2 map cells.

Codes:
0 = empty / road / black background / passable
1 = brick wall / not passable / destroyable
2 = steel wall / not passable / normally not destroyable
3 = bush / forest / grass cover / passable, drawn over tanks
4 = water / not passable
5 = base / eagle / special object; if destroyed, game over
6 = ice / gray slippery road / passable

Notes:
- Player/enemy spawns are NOT drawn in these background PNGs, so I did not encode them here.
  Add them in code separately, for example enemy spawn points at the top and player spawn near the base.
- Files contain only the grid, no header, so LevelLoader can read line-by-line directly.
- In collision logic, 0, 3 and 6 should be passable. 1, 2 and 4 should block tank movement. 5 is special.
