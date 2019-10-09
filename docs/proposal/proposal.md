# School of Computing &mdash; Year 4 Project Proposal Form

> Edit (then commit and push) this document to complete your proposal form.
> Make use of figures / diagrams where appropriate.
>
> Do not rename this file.

## SECTION A

|                     |                     |
|---------------------|---------------------|
|Project Title:       | We live in a Society|
|Student 1 Name:      | Sean McCann         |
|Student 1 ID:        | 16448004            |
|Student 2 Name:      | Kacper Slowikowski  |
|Student 2 ID:        | 16446386            |
|Project Supervisor:  | xxxxxx              |

> Ensure that the Supervisor formally agrees to supervise your project; this is only recognised once the
> Supervisor assigns herself/himself via the project Dashboard.
>
> Project proposals without an assigned
> Supervisor will not be accepted for presentation to the Approval Panel.

## SECTION B

> Guidance: This document is expected to be approximately 3 pages in length, but it can exceed this page limit.
> It is also permissible to carry forward content from this proposal to your later documents (e.g. functional
> specification) as appropriate.
>
> Your proposal must include *at least* the following sections.


### Introduction

> This project is aiming to simulate societies contesting for resources over a map through the use of Genetic Algorithms.  The map will consist of tiles of different terrain quality (e.g Water Tiles, Plain Tiles, Arrid TIles, Fertile TIles). Each tile will either be unclaimed or claimed by an existing society. Any tile that is claimed will host a number of people who are members of the society to which the tile belongs. The goal of the simulation is to examine how societies will change and react over time in order to gain the most amount of territory while maintaining a healthy population.

The techincal area this project covers is simulation of population growth, genetic algorithms and strategy like games. We believe that using this it might prove to be an effective way to demonstrate how genetic algorithms function and how genetic structures, in our case Societies, can adapt and change over time in order to adjust to their environment as well as adapt to the other genetic Structures that compete against them. 

### Outline

> TThis will be a simple interactive simulation of multiple groups of people(Societies) attempting to gain majority control of a world(set of tiles). It will consist of two major components

**Front end**

- Written in Python using Libraries such as (Plotly, Pygame)
- Will contain the UI
  - UI consists of graphical representation of what is happening in the world
  - Contains Various Graphs and breakdown of each individual Society
    - ■■Population increase over time
    - ■■Population aggressiveness over time
    - ■■Population spread over time
    - ■■etc..

**Back end**

- Written in Python
- Will contain the Algorithm which is used to make decisions

The majority of the workload will be inside the Back end component as we want to get the most accurate results for our algorithm. This will mean a lot of tuning and training of any component we create. Below is the following logic we wish to implement.

A Society is made up of people

Each person has some traits

Majority of traits determines society&#39;s strategy

**What is the world made of?**

- Tiles of different types of land
  - Water tiles
  - Fertile tiles
  - Arrid Tiles
  - Plain Tiles

**Water Tile**

- impassible
- Gives bonuses to Societies nearby

**Fertile Tile**

- Passable
- Gives positive bonuses to occupying Society

**Arrid Tiles**

- Passable
- Gives Negative bonuses to occupying Society

**Plain Tiles**

- Passable
- Give no bonuses to occupying Society

**What is a Person made of?**

- Health
  - An int of 100
  - This value decreases as a person goes through &quot;life&quot;
  - This value will change frequently
- Age
  - Value between 0 and average life expectancy or higher. This is  inputted by user at the start.
  - 1 year will roughly equal 1 minute in real life. This speed can be altered by the user
- Gender
  - Male or female
- Aggressiveness index
  - Value between -1 and 1
    - ■■-1 passive
    - ■■1 aggressive
  - The more aggressive a person is the more likely they are to attack if available
- Fertility index
  - Value between -1 and 1
    - ■■1 fertile will be more likely to be selected for reproduction
    - ■■-1 Infertile will be less likely to be selected for reproduction



(Unsure how to tackle just yet)

- Pathfinding Index
  - index value between -1 and 1
    - ■■Will depend on individuals within a tile Age, Aggressiveness, Fertility and Health
    - ■■-1 Society is not willing to try and secure more tiles until it has more pop
    - ■■1 Society is willing to try and secure more land before increasing your pop

**What is a Society?**

- A group of People

**What does the Society do?**

- Claim territory

**How?**

**Expanding territory**

- Claiming unowned territory by staying in it for 1 turn
- Claiming owned territory by battling opposing Society
- Stealing another Society tile and its population.

**How does battling work?**

- One society defends a tile one society attacks a tile. **NOTE** The more aggressive Society will be the first to decide its events

- Create some sort of an attacking or defending modifier / winning probability based on the following factors
  - Number of people attacking / defending
  - Aggressiveness of the attackers / defending
  - Overall health of force attacking / defending

**How does a Society Decide what to do?**

- Based on the following factors Each Society will determine what is the more appropriate move.
  - Societies Aggressiveness - **Attack**
  - Societies Size - **Reproduce**
  - Societies Pathfinding - **Move**

**Why does a Society want to make these moves?**

- The goal of each Society is to have the largest population of healthy individuals across the largest territory. A society can also win by being the last society left on the map. Each decision will alter the Societies time efficiency which will dictate its movements. After the time limit is reached, a winner is decided.


### Background

> Where did the ideas come from?

### Achievements

> What functions will the project provide? Who will the users be?

### Justification

> Why/when/where/how will it be useful?

### Programming language(s)

> List the proposed language(s) to be used.

### Programming tools / Tech stack

> Describe the compiler, database, web server, etc., and any other software tools you plan to use.

### Hardware

> Describe any non-standard hardware components which will be required.

### Learning Challenges

> List the main new things (technologies, languages, tools, etc) that you will have to learn.

### Breakdown of work

> Clearly identify who will undertake which parts of the project.
>
> It must be clear from the explanation of this breakdown of work both that each student is responsible for
> separate, clearly-defined tasks, and that those responsibilities substantially cover all of the work required
> for the project.

#### Student 1

> *Student 1 should complete this section.*

#### Student 2

> *Student 2 should complete this section.*

## Example

> Example: Here's how you can include images in markdown documents...

<!-- Basically, just use HTML! -->

<p align="center">
  <img src="./res/cat.png" width="300px">
</p>

