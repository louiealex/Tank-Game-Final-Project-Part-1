# csc413-TankGame

## Student Name : Alexander M. Louie

# Project Build Info: 
IDE: **IntelliJ Idea 2018.2.3 (Ultimate Edition) IDE** 

Java Version: **Java JDK 10.0.2**.

# **Importing Instructions:**
Open the project in IntelliJ.
1. In IntelliJ, go to "Project Structure -> Modules", and change the following settings.
     - set the Github repo as the Content Root
     - set the src folder as a Source
     - set the src/Resources folder as a Resource

# **To run the Tank Game:**
1. Note the controls and power-ups listed below.
2. Choose 1 of the 2 options.
    - Right Click on the "TankGame" class and select Run "TankGame.main()". The game starts immediately once launched.
    - The Tank Game can also be by launching the included .jar file via the command line located in the JAR folder.
3. Once either player is out of lives, the game will display a message identifying the winning tank. The game window will close automatically after approximately 10 seconds.

Note: The screen resolution is 1280x704. This can be modified by changing the screen width and screen height in the TankGame class file. Make sure the SCREEN_WIDTH is greater than the SCREEN_HEIGHT.

# Controls
Controls for Tank1 (left half of the screen):

1. 'Q' is for shooting the bullet

2. 'W' is for moving the tank forward

3. 'A' is for rotating the tank left

4. 'S' is for moving the tank backward

5. 'D' is for rotating the tank right

Controls for Tank2 (right half of the screen):

1. "Return" or "enter" key (located above the right shift key) is for shooting the bullet

2. up-arrow key is for moving the tank forward

3. left-arrow key is for rotating the tank left

4. down-arrow key is for moving the tank backward

5. right-arrow key is for rotating the tank right



# **Power-ups:**

1. Red Shield (Shield1.gif): Picking this up will allow your tank to get shot by the next bullet without any impact on health. If you have one of these Shields unused, then picking up another Shield will have no effect other than removing it from the map.

2. Health (Pickup.gif): Picking up this power-up will add 20 to the your tank's existing health.
