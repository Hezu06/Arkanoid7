# Brick Breaker 36.0 🎮

A modern JavaFX implementation of the classic Arkanoid/Breakout game with enhanced graphics, multiple difficulty levels, and exciting power-ups.

![Java](https://img.shields.io/badge/Java-17+-orange.svg)
![JavaFX](https://img.shields.io/badge/JavaFX-19+-blue.svg)
![License](https://img.shields.io/badge/license-MIT-green.svg)

## 🎯 Features

### Core Gameplay
- **Smooth Physics**: Advanced collision detection with minimum penetration algorithm
- **Multiple Difficulty Levels**:
    - Hard
    - Very Hard
    - Asian (Expert)
- **Dynamic Brick Types**:
    - Normal Bricks (1 hit)
    - Strong Bricks (3 hits with visual degradation)
    - Explosive Bricks (chain reaction explosions)
    - Unbreakable Bricks (indestructible)

### Power-Ups
- **Expand Paddle**: Temporarily increases paddle width
- **Multi-Ball**: Spawns additional balls
- **Fire Ball**: Ball destroys bricks without bouncing
- **Laser**: Shoot laser beams from paddle
- **Immortal**: Temporary barrier at bottom of screen
- **Extra Lives**: Gain additional lives
- **Extra Coins**: Bonus points

### Visual & Audio
- **Particle Effects**: Explosive animations on brick destruction
- **Dynamic Glow Effects**: Pulsing glow on all bricks
- **Multiple Themes**:
    - 4 Background options (Galaxy, Black, Beach, Grass)
    - 3 Ball skins (Default, Basketball, Volleyball)
    - 3 Paddle skins (Default, Grass, Sand)
- **Background Music**: Adjustable volume control
- **Sound Effects**: Click sounds and collision audio

### UI Features
- **High Score System**: Persistent leaderboard (top 10 scores)
- **Lives System**: Visual heart display (max 5 lives)
- **Pause Menu**: Resume, restart, or return to main menu
- **Smooth Transitions**: Fade effects between screens

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 17 or higher
- JavaFX SDK 19 or higher
- JUnit 5 (for running tests)

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/brick-breaker-36.git
cd brick-breaker-36
```

2. **Build the project**
```bash
# Using Maven
mvn clean install

# Using Gradle
gradle build
```

3. **Run the game**
```bash
# Using Maven
mvn javafx:run

# Using Gradle
gradle run

# Or run directly
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.media,javafx.fxml -jar brick-breaker.jar
```

## 🎮 How to Play

### Controls
- **Left Arrow**: Move paddle left
- **Right Arrow**: Move paddle right
- **Space / Up Arrow**: Launch ball
- **Escape**: Pause game

### Objective
Destroy all breakable bricks while keeping the ball(s) in play. Catch power-ups to gain advantages and maximize your score!

### Scoring
- Normal Brick: 50 points
- Strong Brick: 150 points
- Extra Coins Power-up: 500 points

## 📁 Project Structure

```
src/main/java/
├── com/arkanoid/
│   ├── entity/
│   │   ├── brick/          # All brick types
│   │   ├── powerUp/        # Power-up implementations
│   │   ├── Ball.java
│   │   ├── Paddle.java
│   │   └── GameObject.java
│   ├── game/
│   │   ├── GameMain.java   # Main game loop
│   │   └── GameStateManager.java
│   ├── level/
│   │   ├── Level.java
│   │   ├── LevelLoader.java
│   │   └── DifficultySettings.java
│   └── ui/
│       ├── GameMenu.java
│       ├── OptionsScreen.java
│       ├── PausedScreen.java
│       ├── ScoreScreen.java
│       └── HighScoreManager.java
└── UnitTest/
    └── BallTest.java       # Unit tests for collision
```

## 🏗️ Design Patterns

The project implements several design patterns to ensure maintainable and extensible code:

### 1. **Singleton Pattern**
Used for managing global game state and resources that should have only one instance.

**Implementations:**
- `GameStateManager` - Manages score, lives, and game state
- `HighScoreManager` - Handles persistent high score storage
- `SoundBackground` - Controls background music playback
- `SoundEffect` - Manages sound effects

```java
public class GameStateManager {
    private static GameStateManager instance;
    
    private GameStateManager() {
        // Private constructor
    }
    
    public static GameStateManager getInstance() {
        if (instance == null) {
            instance = new GameStateManager();
        }
        return instance;
    }
}
```

### 2. **Factory Pattern**
Creates power-up objects based on game conditions without exposing creation logic.

**Implementation:**
- `PowerUpFactory` - Generates random power-ups with configurable drop rates

```java
public static PowerUp createPowerUp(double x, double y, Level.LevelDifficulty difficulty) {
    double roll = random.nextDouble();
    if (roll < DifficultySettings.getPowerUpChance(difficulty)) {
        PowerUpType type = dropTypes[random.nextInt(dropTypes.length)];
        return switch (type) {
            case EXPAND_PADDLE -> new ExpandPaddle(x, y);
            case MULTI_BALL -> new MultiBall(x, y);
            // ... other types
        };
    }
    return null;
}
```

### 3. **Strategy Pattern**
Different brick types implement varying hit behaviors and special actions.

**Implementation:**
- Abstract `Brick` class defines interface
- Concrete implementations: `NormalBrick`, `StrongBrick`, `ExplosiveBrick`, `UnbreakableBrick`

```java
public abstract class Brick extends GameObject {
    @Override
    public abstract boolean takeHit();
    protected abstract void updateAppearance();
    public List<int[]> triggerSpecialAction() { 
        return new ArrayList<>(); 
    }
}
```

### 4. **Template Method Pattern**
Base classes define the skeleton of algorithms while allowing subclasses to override specific steps.

**Implementation:**
- `GameObject` - Abstract base for all game entities
- `MovableObject` - Extends GameObject for entities that move
- `PowerUp` - Abstract base for all power-up effects

```java
public abstract class GameObject {
    public abstract void update();
    public abstract void render(GraphicsContext gc);
    public abstract boolean takeHit();
    protected Rectangle2D getBounds() { /* default implementation */ }
}
```

### 5. **State Pattern (Implicit)**
Game manages different states (Menu, Playing, Paused, GameOver).

**Implementation:**
- `GameMenu` - Main menu state
- `GameMain` - Playing state
- `PausedScreen` - Paused state
- `ScoreScreen` - Game over/level complete state

## 📋 Coding Conventions

The project follows consistent coding standards for maintainability and readability.

### Naming Conventions

#### Classes
- **PascalCase** for class names
- Descriptive nouns or noun phrases
```java
public class GameStateManager { }
public class ExplosiveBrick { }
public class PowerUpFactory { }
```

#### Methods
- **camelCase** for method names
- Start with verbs describing the action
```java
public void updateAppearance() { }
public boolean checkCollision(GameObject other) { }
private void handleExplosion() { }
```

#### Variables
- **camelCase** for variables
- Descriptive names avoiding single letters (except loops)
```java
private int currentDurability;
private double barrierTimeRemaining;
private List<Brick> bricksToRemove;

// Loop counters can use single letters
for (int i = 0; i < bricks.size(); i++) { }
```

#### Constants
- **UPPER_SNAKE_CASE** for constants
- Use `static final` modifiers
```java
private static final int MAX_LIVES = 5;
private static final double BARRIER_DURATION_SECONDS = 5.0;
private static final String FONT_PATH = "/fonts/GameFont.TTF";
```

### Code Organization

#### Package Structure
```
com.arkanoid
├── entity       # Game objects (Ball, Paddle, Brick, PowerUp)
├── game         # Core game logic and state management
├── level        # Level loading and difficulty settings
└── ui           # User interface screens and components
```

#### Class Structure Order
1. Static variables
2. Instance variables (private fields)
3. Constructors
4. Public methods
5. Protected methods
6. Private methods
7. Getters and setters (at the end)

#### Method Length
- Keep methods focused on a single responsibility
- Prefer methods under 50 lines
- Extract complex logic into helper methods

### Documentation

#### Class Documentation
```java
/**
 * Manages the explosion effect animation when bricks are destroyed.
 * Creates particle effects with customizable properties based on brick type.
 */
public class ExplosionEffect {
    // ...
}
```

#### Method Documentation
```java
/**
 * Handles the chain reaction of explosive brick destruction.
 * 
 * @param affectedCoords The list of [x, y] coordinates to check
 * @param bricksToRemove The global list of bricks to be removed
 * @param processedCoords A set of already processed coordinates
 */
private void handleExplosion(List<int[]> affectedCoords,
                            List<Brick> bricksToRemove,
                            Set<String> processedCoords) {
    // ...
}
```


## 🧪 Testing

The project includes comprehensive unit tests for the Ball collision system:

```bash
# Run tests with Maven
mvn test

# Run tests with Gradle
gradle test
```

### Test Coverage
- Collision detection (overlapping, touching edges, touching corners)
- Bounce physics (walls, bricks, paddle)
- Paddle interaction (center, left, right hits with angle variation)

## ⚙️ Configuration

### Difficulty Settings
Modify `DifficultySettings.java` to adjust:
- Ball speed
- Paddle width
- Power-up drop chance

### Level Design
Create custom levels by editing files in `resources/difficulty/`:
- `N` = Normal Brick
- `S` = Strong Brick
- `X` = Explosive Brick
- `U` = Unbreakable Brick
- `-` = Empty space

Example level file:
```
NNNNNNNNNNNN
SSSSSSSSSSSS
XXXXXXXXXXXX
UUUUUUUUUUUU
```


## 🔮 Future Enhancements

- [ ] Additional levels and level progression
- [ ] Boss battles
- [ ] Multiplayer mode
- [ ] Mobile version
- [ ] More power-up varieties
- [ ] Achievement system
- [ ] Cloud save for high scores

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 👥 Authors

- Nguyễn Trung Hiếu
- Đặng Ngọc Minh
- Lê Viết An
- Trần Đăng Khoa
- Hoàng Việt Anh Đức

## 🙏 Acknowledgments

- Inspired by the classic Arkanoid and Breakout games
- JavaFX community for excellent documentation
- Font assets from various open-source collections

## 📞 Support

For support, email nguyentrunghieu14112006@gmail.com or open an issue in the repository.

---

**Enjoy playing Brick Breaker 36.0!** 🎉