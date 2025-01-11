![Logo](./docs/logo.png)

# Sheriff Dodge

A Game made as part of HW at Afeka's `Mobile Apps - 25A10357` Class.

## How to use

1. todo

# Overview

Sheriff Dodge is a game built using `Kotlin 2.1.0`, designed for Android devices.

## App Flow

![Overview](./docs/app_overview.png)

### 1. Main Menu

![Main Menu](./docs/main_menu.png) | ![Main Menu](./docs/main_menu_dark.png) 
-|-

- **Start Game** - Starts the `game loop`, with the setting selected by the user.
- **Settings** - shows the settings screen.
- **Scoreboard** - shows the scoreboard screen.
- **Exit** - closes the app

### 2. Settings

![Settings Screen](./docs/settings_screen.png) | ![Settings Screen](./docs/settings_screen_dark.png) 
-|-

The settings screen uses the `SettingsManager` to define `global settings` that every other class uses.

`moving objects` - coins, hearts and obstacles
`TiltDetector` - Uses the device's `accelerometer` to identify tilt.

- **Difficulty** - each difficulty settings changes the amount of `moving objects` in each row. Defaults to `Infinitely Easy` which is the easiest difficulty
- **Speed** - changes the tick speed (delay) of the game, Effecting `moving objects`. Defaults to `500`
- **Motion controls** - toggles the motion game controls. Uses `TiltDetector`. Defaults to `off`

### 3. Game
- todo

### 4. Scoreboard
![scoreboard Screen](./docs/scoreboard_srceen.png) | ![scoreboard Screen](./docs/scoreboard_screen_dark.png) 
-|-

ScoreManager - save the scores in the app `Shared Preference` and loads them into a list using [prefy](https://github.com/paz-lavi/Prefy.git)

Display the `top 10` scores achieved.
- **On Score Click** - The map zooms in to where you achieved the score.
- **Add score** Adds the score to `ScoreManager` with `FusedLocationProvider` based on current location.
- **Location perms** - uses [EasyPermissions](https://github.com/VMadalin/easypermissions-ktx) to handle `location permissions`
- **Maps Platform API** - uses `SupportMap` to display the map, and saves the `API token` token safely with `Gradle Secrets`

## Features

- todo

## Authors

- [@lordYorden](https://github.com/lordYorden)

