# aligent_challenge
Aligent coding challenge

Content of repository is designed to be imported into Intellij IDEA as a project.
Hoever, for ease of running, instead jar file can be used:

Usage: java -jar AligentChallenge.jar DATETIME DATETIME [CONVERT_TO_UNITS]
 - DATETIME : Date time in following format: YYYY-MM-DDTHH:mm[:SS][OFFSET]
 - OFFSET : Timezone offset in format: +HH:mm:SS
 - CONVERT_TO_UNITS : Additional time unit to calculate, S/M/H/Y -> Seconds/Minutes/Hours/Years
 
e.g. java -jar AligentChallenge.jar 2018-01-01T12:00:00 2018-01-01T19:00:00+04:00:00 H
