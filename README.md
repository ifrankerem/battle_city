<div align="center">

# 🚀 Battle City Remake

**A Java remake of the NES classic *Battle City / Tank 1990* — built for a Software Development Methodologies course.**

![Java](https://img.shields.io/badge/language-Java-ED8B00?style=flat-square&logo=openjdk)
![GUI](https://img.shields.io/badge/GUI-Swing-2b9348?style=flat-square)
![Status](https://img.shields.io/badge/status-complete-blue?style=flat-square)
![Stars](https://img.shields.io/github/stars/ifrankerem/battle_city?style=flat-square)

</div>

---

## 📋 Table of Contents

- [About](#about)
- [Features](#features)
- [Controls](#controls)
- [Getting Started](#getting-started)
- [Architecture](#architecture)
- [Course Context](#course-context)
- [License & Note](#license--note)

---

## 📖 About

A faithful Java remake of **Battle City**. Drive a tank across a tile grid,
destroy enemy tanks, grab power-ups and keep the base from being overrun — with
original-style destructible brick walls, animated water and bush overlays, and
a map editor so you can build your own stages.

Originally a term project for **CSE212 — Software Development Methodologies**
at Yeditepe University, kept as a clean object-oriented game codebase.

---

## ✨ Features

- Classic top-down tank gameplay on a tile grid
- Player movement with `WASD`, shooting with `Space`
- Enemy tanks spawning from the top edge with simple AI
- Bullet collisions against walls, tanks and other bullets
- Destructible brick walls, indestructible steel walls
- Player lives and respawn, pause / unpause
- Game-over and level-complete screens
- Power-ups taken from the original game
- Animated water and bush overlays
- High-score saving backed by CSV
- **Built-in custom map editor**

---

## 🎮 Controls

| Key | Action |
|---|---|
| `W` | Move up |
| `A` | Move left |
| `S` | Move down |
| `D` | Move right |
| `Space` | Shoot |

---

## 🚀 Getting Started

**Prerequisites**

- JDK 17 or newer

**Build and run**

```bash
javac -d out $(find . -name "*.java")
java -cp out Main
```

Or open the project in your IDE and run `Main.java`.

---

## 🧱 Architecture

```
battle_city/
├── Main.java      # entry point, window setup
├── engine/        # game loop, collision, state machine
├── entity/        # tanks and bullets
├── gui/           # rendering, HUD, menus
├── level/         # level loading and progression
├── model/         # shared game state
├── powerup/       # power-up types and effects
├── tile/          # tile types and behaviour
├── resources/     # sprites and audio
└── util/          # helpers (CSV high scores, math)
```

The design deliberately splits **model** (what the game state is) from
**engine** (how it changes) from **gui** (how it looks), so the game loop stays
readable as behaviour grows.

---

## 🎓 Course Context

The goal was to rebuild Battle City in Java while practising object-oriented
game development, Swing GUI programming, collision handling, game states, file
I/O and a hand-rolled fixed game loop.

*This is a student-made remake created for educational purposes. It is not an
official Battle City release.*

---

## 👤 Author

**İrfan Kerem Arslan** — [@ifrankerem](https://github.com/ifrankerem)

---

## 📄 License

Student coursework, shared for learning and portfolio purposes.

---

## 🙏 Acknowledgements

- [awesome-readme](https://github.com/matiassingers/awesome-readme) — structure inspiration for this README