# Memory Game in Java Swing and AWT

A desktop card-matching game built with Java Swing and AWT. Match 6 pairs on a 12-card board within 15 turns, or start a new shuffled game.

![Memory Game Screenshot](screenshot.png)

## Requirements

- A JDK with `java` and `javac` available on your PATH.
- A graphical desktop session. Swing needs a display; a headless terminal alone cannot show the game.
- No external Java libraries are required.

## Run locally

```bash
git clone https://github.com/ataturhan21/Memory-Game.git
cd Memory-Game
bash script.sh
```

The launcher compiles the sources and starts the game from the correct directory so the images can be loaded. It preserves your display configuration and does not stop other Java applications.

On Windows, or without Bash, run these commands from the repository root:

```text
javac Main.java
java -Dsun.java2d.xrender=false Main
```

Keep the `images/` directory beside the source files. If `javac` is missing, install a JDK rather than a runtime-only Java installation.

## Play

1. Click a face-down card to reveal it.
2. Click another face-down card to attempt a match.
3. Matched pairs stay revealed; unmatched cards turn back over.
4. Use **New Game** to reset the turn count and shuffle the cards.

## Source guide

| File | Responsibility |
| --- | --- |
| [Main.java](Main.java) | Window, controls, and card grid |
| [MemoryGame.java](MemoryGame.java) | Board state, turn count, and shuffling |
| [GameActions.java](GameActions.java) | Click handling, pair checks, and restart actions |
| [CustomIcon.java](CustomIcon.java) | Card images and per-card state |

## License

See [LICENSE](LICENSE).
