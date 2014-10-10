bitcoin-converter
=================

Bitcoin price converter in Java

This program was written by me as a final project for my Programming Principles 2 class during Fall 2013.  The program utilized
Jsoup, an HTML parser for Java, and searched through the HTML of a popular bitcoin price website http://bitcoinity.org/markets.  
The program would parse through the HTML to find the latest buy price from the three major exchanges at the time (bitstamp, MTGox, 
and BTC-E) and grab the the line containing the dollar value to then convert to a double.  A GUI is set up containing a text 
field for the user to input their total bitcoin and two more text fields that would show the current value of 1 bitcoin and the 
total dollar value of the given bitcoin amount.   
