Visualization using Processing. Three classes emcompassed by the .pde file:
-Main setup and loop
-Puff class (instantiated version of a Processing example)
-Button class (for ui etc)

Java classes for the simulation:
-Initializer acts as the middle man and keeps track of the environment and organisms
-Environment class for the surroundings
-Organism class, contains three genes:
--Hunting gene:
----Dominant (HH or Hh) is decent at foraging for food
----Recessive (hh) is much better at finding food
--Water retention:
----Dominant (WW or Ww) is decent at storing water.
----Recessive (ww) can go more days without water
--Coat gene:
----Homozygous Dominant (CC) is a heavy coat
----Heterozygous (Cc) is a medium weight coat
----Homozygous Recessive (cc) is a light coat