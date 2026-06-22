# Painter

A grid-based painting toy and algorithm visualizer. This was one of my first projects when I was learning to code — I had a lot of fun building it and kept coming back to add more features.

## What it does

You navigate a cursor around a 60×45 grid, paint cells in different colours, and then run algorithms directly on your drawing. It's part sketchpad, part sandbox for watching code do its thing.

## Features

- **Paint with keys** — move with `H`/`J`/`K`/`L` (VI-style), hold `SPACE` to draw, pick from 9 colours (`1`–`9`)
- **11 save slots** — save and load your paintings with `S` and `X`, cycle slots with `B`
- **Flood fill** — paint connected regions with a single key (`FILL`)
- **BFS maze solver** — finds the shortest path to the nearest cell of a chosen colour and highlights it in green (`MAZE`)
- **Langton's Ant** — the classic 2-colour cellular automaton, plus 5 extended variants (Chaos, Square, Triangle, Convoluted, Symmetrical) each producing wildy different patterns
- **Conway's Game of Life** — runs on your painted grid using parallel streams for performance
- **On-screen HUD** — shows controls, current save slot, and active algorithm

## Design decisions (junior-dev style)

- The grid is a `HashMap<Position, Cell>` — O(1) lookups by coordinate, which was probably overkill for 2700 cells but made me feel very clever
- `Position` has a `transient origin` field that creates a linked-list chain — used by the maze solver to backtrack the shortest path. Not production-grade, but it worked!
- The five Langton's Ant variants share a `AbstractLangtonExtended` template — each subclass just provides a colour array and a direction array. Discovering the Template Method pattern felt like magic
- Flood fill has a `InitFill` factory that returns either a `Fill` or `Refill` depending on whether the starting cell is already painted — I was proud of this one
- All algorithms run on a single background thread so they don't freeze the UI or interfere with keyboard input
- Save/load uses plain Java serialization — simple, no dependencies, good enough

## Built with

- Java 11, Maven
- [SimpleGraphics](https://github.com/academia-de-codigo/simple-graphics) — a 2D graphics library from the bootcamp I was attending (needs to be installed locally, it's not in Maven Central)
- LITIengine (in `pom.xml` but never actually used — I planned big things)

## Setup

SimpleGraphics isn't on Maven Central — you need to build and install it locally first. The `setup.sh` script handles this:

```bash
./setup.sh
```

This clones SimpleGraphics, installs it to your local Maven repository, then builds Painter.

## Running it

```bash
mvn compile exec:java
```

Or package and run:

```bash
mvn package
java -jar target/painer-1.0-SNAPSHOT.jar
```

## Try it in the browser

A live version runs via [CheerpJ](https://cheerpj.com/) on GitHub Pages:

```
https://<your-username>.github.io/painter
```

After enabling Pages (Settings → Pages → deploy from `docs/`), the `docs/` folder serves the app — no install needed.
