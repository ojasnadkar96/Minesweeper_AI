# Minesweeper AI

This project was implemented as a part of Artifical Intelligence (CS271P) course at University of California, Irvine

The agent can solve:<br>
80% Beginner worlds (8X8 10 mines)<br>
75% Intermediate worlds (16X16 40 mines)<br>
25% Expert worlds (16X30 99 mines)<br>

## Generating the worlds

Open `WorldGenertor` folder and run the `generateTournament.sh` to generate 1000 worlds of each level.<br>
The worlds are generated in a `Problems` folder which is created once the shell script is run.<br>
<br>

## Testing the agent

Run the `make` command in the `Minesweeper_Java` folder. Open the `bin` folder and type `java -jar mine.jar -f <path of Problems folder>` to run the agent on the generated worlds.<br>
