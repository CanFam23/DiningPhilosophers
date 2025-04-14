# DiningPhilosophers

By Nick Clouse, Jack Holy, Jace Claassen

## The Problem

The	dining-philosophers	problem	is	considered	a	classic	synchronization	problem	
neither	because	of	its	practical	importance	nor	because	computer	scientists	dislike	
philosophers	but	because	it	is	an	example	of	a	large	class	of	concurrency-control	
problems.	It	is	a	simple	representation	of the	need	to	allocate	several	resources	
among	several	processes	in	a	deadlock-free	and	starvation-free	manner.  

*Consider	five philosophers	who	spend	their	lives	thinking	and	eating.	The	
philosophers	share	a	circular	table	surrounded	by	five	chairs,	each	belonging	to	
one	philosopher.	In	the	center	of	the	table	is	a	bowl	of	rice,	and	the	table	is	laid	
with	five	single	chopsticks.	When	a	philosopher	thinks,	she	does	not	interact	with	
her	colleagues.	From	time	to	time,	a	philosopher	gets	hungry	and	tries	to	pick	up	
the	two	chopsticks	that	are	closest	to	her	(the	chopsticks	that	are	between	her	and	
her	left	and	right	neighbors).	A	philosopher	may	pick	up	only	one	chopstick	at	a	
time.	Obviously,	she	cannot	pick	up	a	chopstick	that	is	already	in	the	hand	of	a	
neighbor.	When	a	hungry	philosopher	has	both	her	chopsticks	at	the	same	time,	
she	eats	without	releasing	the	chopsticks.	When	she	is	finished	eating,	she	puts	
down	both	chopsticks	and	starts	thinking	again.*

## Our Solution

### Built with
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Apache Ant](https://img.shields.io/badge/Apache%20Ant-A81C7D?style=for-the-badge&logo=apache&logoColor=white)

We implemented what we call the **Star Method**, where the philosophers eat in a star pattern of (1, 3, 5, 2, 4), with only two philosophers eating at any given time. For example, philosophers 1 and 3 eat first, followed by 3 and 5, then 5 and 2, then 2 and 4, and so on. This pattern continues until the eating time is over.

By enforcing this eating order, we effectively eliminate any possible deadlocks or race conditions. Our solution ensures:
1. Only two philosophers are eating at a time.
2. The eating pattern avoids any direct competition for chopsticks, preventing deadlocks.

Each philosopher waits until it's their turn in the current "wave" to eat. Once finished, the philosopher notifies the [`DiningTable`](src/DiningTable.java) class so the next pair can begin eating.

Initially, we were unsure if our solution was truly "correct," but we believe it holds up. Each philosopher picks up the chopsticks to their left and right, one at a time. They do not communicate with each other directly—only with the [`DiningTable`](src/DiningTable.java). We felt this was acceptable, as in a real-life scenario, philosophers would be aware of their surroundings and wait until chopsticks were free before eating.

## Getting started

* Open a terminal
* If Ant isn't installed and you have a mac with homebrew, run
```bash
brew install ant
```
If you don't have a mac with homebrew, sorry.
### Clone the repository
```bash
git clone https://github.com/CanFam23/DiningPhilosophers/
```

## Running the program
Run the program using
```bash
ant run
```
Other helpful commands:
```bash
ant clean # Deletes old compiled files.
ant compile # Compiles Java source files into bin/
```
  
You can also run the program through an IDE by navigating to the [`DiningTable`](src/DiningTable.java) class and running it.
  
**Sample Output**
```bash
$ ant run
Buildfile: path/to/project/DiningPhilosophers/build.xml

init:

compile:
    [javac] Compiling 3 source files to path/to/project/DiningPhilosophers/dist/classes

jar:
      [jar] Building jar: path/to/project/DiningPhilosophers/dist/Dining-Philosopher.jar

run:
     [java] Allowing Philosophers to eat for 5 seconds...
     [java] Philosopher: 1 ate 10 times
     [java] Philosopher: 2 ate 10 times
     [java] Philosopher: 3 ate 10 times
     [java] Philosopher: 4 ate 10 times
     [java] Philosopher: 5 ate 10 times

BUILD SUCCESSFUL
Total time: 6 seconds
```



