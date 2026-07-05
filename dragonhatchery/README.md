# Dragon Hatchery

A Fabric mod for Minecraft **26.2** (Fabric Loader 0.19.3). Hatch the Ender Dragon's egg into
a loyal, flying baby dragon companion.

## How it works
1. Kill the Ender Dragon and grab the Dragon Egg it leaves behind, then place it as a block.
2. Craft an **Ancient Dragonflame Charge**: 1 Dragon's Breath (bottle) + 4 Blaze Powder + 1 Nether Star (shapeless).
3. Right-click the placed Dragon Egg while holding the Charge.
4. The egg is consumed and a tamed **Baby Ender Dragon** hatches, bonded to you. It flies, follows you,
   can be told to sit (sneak + right-click), and can be healed with rotten flesh/chicken.

## ⚠️ Important: why this is a source project, not a pre-built .jar
I don't have the internet access in this environment needed to run the actual Gradle/Fabric
Loom build — that process needs to download the Minecraft 26.2 game jar, Fabric Loader, and
Fabric API from Mojang/Fabric's servers, none of which I can reach from here. So instead of a
possibly-broken binary, I've written out the **complete, ready-to-build project**. Building it
on your own machine (which does have internet) is one command.

Minecraft 26.2 is also very new — it dropped official Mojang mappings and reorganized several
APIs (see https://fabricmc.net/2026/06/15/262.html). I've written this against the current
official documentation for that version, but since it's bleeding-edge, a couple of API names
(flagged with `NOTE:` comments in the code, e.g. in `ModItems.java`) might need a one-line
tweak if Fabric renames something between now and when you build. If a compile error mentions
a missing class/method, it's almost always a quick rename — Fabric's docs at
https://docs.fabricmc.net/develop are the fastest way to check the current name.

## Option A: Build it with zero installs, using GitHub (recommended if you don't want to install Java/Gradle)
This repo includes a `.github/workflows/build.yml` that compiles the mod in the cloud. All you need is a browser and a free GitHub account.

1. Go to https://github.com/new and create a new repository (any name, public or private — either works).
2. On the new repo's page, click **"uploading an existing file"**.
3. Extract this zip on your computer, then drag the *contents* of the `dragonhatchery` folder (not the zip itself) — including the hidden `.github` folder — into the upload box. Most browsers preserve folder structure when you drag a folder in. Commit the files.
4. Click the **Actions** tab. A "Build Dragon Hatchery jar" run should already be in progress (or click "Run workflow" if it isn't). It takes 2-4 minutes.
5. Once it finishes with a green check, click into the run, scroll to **Artifacts**, and download `dragonhatchery-jar` — that's a zip containing the actual `.jar` file.
6. Unzip that and drop the `.jar` into your `.minecraft/mods` folder, alongside Fabric API 26.2.

If the build fails (bleeding-edge Minecraft version, so possible), open the failed step's log — it'll show the exact compile error, and you can paste it back to me here and I'll fix the source.

## Option B: Build it locally
1. Install **Java 25** (required by Minecraft 26.2) and make sure it's on your `PATH`.
2. From this project folder, run:
   - Linux/macOS: `./gradlew build`
   - Windows: `gradlew.bat build`
   - (If you don't have a `gradlew` wrapper script yet, install Gradle 9.5.1+ and run
     `gradle wrapper --gradle-version 9.5.1` once first, or just run `gradle build` directly.)
3. The compiled mod will appear at `build/libs/dragonhatchery-1.0.0.jar`.
4. Also install **Fabric API** for 26.2 into your mods folder — this mod depends on it.
5. Drop both jars into your `.minecraft/mods` folder and launch with the Fabric 26.2 profile.

Before building, double check `gradle.properties` against https://fabricmc.net/develop/ for
the exact current Loom / Fabric API patch versions, since those update frequently.

## Customizing
- Model: `src/client/java/com/dragonhatchery/client/BabyEnderDragonModel.java` is a simple
  placeholder box model — swap in a Blockbench-exported model any time.
- Textures: `src/main/resources/assets/dragonhatchery/textures/` — the included PNGs are
  solid-color placeholders, replace them with real art.
- Recipe: `src/main/resources/data/dragonhatchery/recipe/ancient_dragonflame_charge.json`.
