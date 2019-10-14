# School of Computing &mdash; Year 4 Project Proposal Form

> Edit (then commit and push) this document to complete your proposal form.
> Make use of figures / diagrams where appropriate.
>
> Do not rename this file.

## SECTION A

|                     |                       |
|---------------------|-----------------------|
|Project Title:       | We live in a Society  |
|Student 1 Name:      | Sean McCann           |
|Student 1 ID:        | 16448004              |
|Student 2 Name:      | Kacper Slowikowski    |
|Student 2 ID:        | 16446386              |
|Project Supervisor:  | Dr Alistair Sutherland|

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

**Introduction**

This project is aiming to simulate societies contesting for resources over a map through the use of Genetic Algorithms.  The map will consist of tiles of different terrain quality (e.g Water Tiles, Plain Tiles, Arrid TIles, Fertile TIles). Each tile will either be unclaimed or claimed by an existing society. Any tile that is claimed will host a number of people who are members of the society to which the tile belongs. The goal of the simulation is to examine how societies will change and react over time in order to gain the most amount of territory while maintaining a healthy population. This project will consist of a front-end GUI, a database and an underlying backend to handle the calculations.

**Outline**

This project will consist of two major components. The front end UI and the backend simulation service.

**Front end**

We have decided that our front end will be written using Python libraries such as Plotty and Pygame. These libraries allow us to display certain metrics such as the average spread of a societies territory over the span of the simulation as well as allowing us to have a graphical display of a map on which the societies are competing. This map will consist of all the societies represented by colour and tiles which will represent different plots of land for societies to claim and populate. This map will be updated as the simulation continues. All metrics used will be derived from a PostgreSQL database (postgresDB). This database will be populated by the downstream back end service whenever a society makes a decision for a particular turn. A turn is a representation of a number of years that pass within our simulation. For each turn that passes in our simulation each society will be allowed to make one decision.

**Back end**

We have decided that our back end will be written using Python. In this component we will be hosting all of our logic for the simulation. The majority of the workload will be inside the back end component. This will mean a lot of tuning and training of any component we create. Below is the following logic we wish to implement.

Our program seeks to emulate the growth and spread of a group of people we call a _Society_. Each society will consist of a number of _People._ These people will consist of a number of traits which will determine the behaviour of the society. This closely resemble a democratic system within each society. These are the traits we wish to implement for each person within a _Society._

- Health
  - An integer between 0 and 100
  - This value decreases each turn depending on:
    - The age of the person
    - The tile this person inhabits
    - If they have recently battled
    - Their _Societies_ average _medicine index_.
- Age
  - An Integer value to represent the age of a member of the society
  - It increases each turn by a value decided on by the user on startup.
  - The higher the age of a person the quicker the rate of health decrease is.
- Gender
  - Male or female
  - Will be used when a _Society_ wants to reproduce. A new member of the society is created by combining traits of 2 parent members. This logic will be handled by our genetic algorithm.
- Aggressiveness index
  - A float value between -1 and 1
    - -1 passive
    - 1 aggressive
  - Passive societies will trade with others as opposed to attacking other societies. This will increase the Societies major skills by a fraction of the other Societies major skills.
  - Aggressive societies will attack other societies as opposed to trading with them. This will allow a society to claim land that doesn&#39;t belong to them.
- Fertility index
  - Value between -1 and 1
    - 1 fertile will be more likely to be selected for reproduction
    - -1 Infertile will be less likely to be selected for reproduction
- Pathfinding Index
  - A float value between -1 and 1
    - Will depend on individuals within a tile Age, Aggressiveness, Fertility and Health
    - -1 Society is not willing to try and secure more tiles until it has more pop
    - 1 Society is willing to try and secure more land before increasing your pop

In addition to these traits we will also assign each person of a society an index for 3 major skills. These skills will massively influence all of the above traits and will be the reasons two Societies would be willing to trade with each other.

- Technology Index
  - A float value between 0 and 1
  - Influences a societies attacking battling modifiers
- Agriculture
  - A float Value between 0 and 1
  - Influences a tile&#39;s bonuses and hinderencess to the society
- Medicine
  - A float between 0 and 1
  - Influences a Societies average life expectancy



The world in which these Societies will exist consists of Tiles. There are four types of tiles in this world. Water, Fertile, Arrid and Plain. Each tile will either give a bonus to a society or hinder it.

Water Tile

- Impassible
- All tiles around a water tile are Fertile

Fertile Tile

- Passable
- Increases life expectancy
- Lower health lost per turn

Arrid Tiles

- Passable
- Decreases life expectancy
- Increases health lost per turn

Plain Tiles

- Passable
- Gives no bonuses

The aim for each _Society_ is to claim as much territory as they can over the shortest amount of time. They have to do this while maintaining a healthy population. They can claim land in the following ways

Expanding territory

- Claiming unowned territory by staying in it for 1 turn(_Pathfinding_)
- Claiming owned territory by battling opposing Society(_Battling_)

How does battling work?

- One society defends a tile one society attacks a tile. **NOTE** The more aggressive Society will be the first to decide its events

- Create some sort of an attacking or defending modifier / winning probability based on the following factors
  - Number of people attacking / defending
  - Overall health of force attacking / defending
  - Average Technology Index of attacking / defending society

How does a Society Decide what to do?

- Based on the following factors Each Society will determine what is the more appropriate move.
  - Societies Aggressiveness - **Attack**
  - Societies Size - **Reproduce**
  - Societies Pathfinding - **Move**
  - Societies major skills - **Trade**

Why does a Society want to make these moves?

- The goal of each Society is to have the largest population of healthy individuals across the largest territory. A society can also win by being the last society left on the map. Each decision will alter the Societies time efficiency which will dictate its movements. After the time limit is reached, a winner is decided.



**Background**

The idea behind this project arose firstly from a project suggestion from Alistair Sutherland, that being the &quot;Modelling Simple Societies&quot; project. This project suggested the idea of creating people within a society who possessed different traits and qualities which would influence how they would interact with each other. The purpose of this project was to examine an ever changing society over time. With this idea in mind we thought back to strategy games that we played in the past. Within these games the player would often play as a country or society and the main objective was to compete against other countries in order to gain control over a randomly generated map. Naturally, we came up with the concept of merging these two ideas into an application where societies would evolve over time as well as its people in order to try to gain power over a randomly generated map. We thought that the premise of a collection of unique members of a population controlling a society in order to adapt to external factors would prove to be quite an interesting endeavour in order the explore genetic algorithms and pathfinding as well as provide a visual representation of these theories in action for educational purposes.



**Achievements**

This project will utilize a GUI that will provide a visual representation of the evolving societies on a randomly generated map over time. Our target users will be students of computing and anyone who is interested in genetic algorithms and decision making technologies. Users will be allowed to customise their simulations by altering factors such as the map size, the amount of societies, the average tile quality, a society&#39;s initial population etc. As well as providing a visual representation of these societies over time our application will also provide an array of graphics for the statistics of each society in a simulation. These statistics will include information such as a society&#39;s average aggression over time, a society&#39;s average life expectancy, a society&#39;s population over time, a society&#39;s technology index over time etc.

The goal for this project is to provide an educational platform to anyone interested examining these areas of computer science.

**Justification**

We believe that this project is justified as currently there are not many applications that allow a user to provide their own input and study the workings of genetic algorithms functioning in real time.  We hope to provide enough statistical feedback with graphs as well as providing a clear enough visualisation of our simulation such that a user can clearly view what is happening during the simulation as well as understand why each society is making their decisions in real time. This application would be useful to anyone interested in these fields, whether it is out of a general interest or wanting to study these areas for educational purposes. This program could be downloaded onto any pc or laptop and quickly run anywhere that  the user wishes.

**Programming Languages**

Python, Python libraries, PostgreSQL

**Programming Tools**

PostgreSQL Database, DbBeaver, Atom text editor, Pyunit, Adobe xD

**Hardware**

No non-standard hardware required

**Learning Challenges**

Over the course of this project we will have to gain a better understanding of genetic algorithms as well as research into artificial decision making options in order to decide what will be the best way to programme our societies to make their decisions over time. We will need to learn the pygame and plotly libraries in order to display our desired information. We will also need to research further into GUI design so we can provide the users with an intuitive experience where all necessary information is clearly visible and easy to understand. This a very important aspect to get correct as this program is intended for anyone interested in this topic at any level so we do not want the information to become overbearing on the user.

**Breakdown of work**

**Student 1 -  ** Sean McCann (16448004)

- ● **Modelling Societies Interactions**
- ● **Modelling Society Decision Making**
- ● **Design UI**
- ● **Map Generation**

**Student 2 -** Kacper Slowikowski (16446386)

- ● **Modeling Society&#39;s member traits**
- ● **Modeling genetic algorithm**
- ● **Design UI**
- ● **Generating metrics and graphs from database**

