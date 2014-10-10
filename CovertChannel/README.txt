UTEID: jmt2939; dbd453;
FIRSTNAME: Jeremy; Daniel;
LASTNAME: Thompson; Durbin;
CSACCOUNT: jmt2939; majisto;
EMAIL: jthompson2214@gmail.com; majisto@gmail.com;

[Program 1]
[Description]
Our program uses a covert channel to pass information from a high level subject to a lower level one. The way we 
implemented this is by reading in a file (the file to pass along). We then go ahead and set up the instructions 
that are needed to run based on whether or not we need to pass along a 1 or a 0 bit. Then there is a while loop in 
main which grabs a byte at a time and then splits that into bits and calls the instructions based on the 1 or the 0. 
Then whenever the instructions for lyle run it creates an object, writes to it, reads from it, destroys it and then 
executes a run. If it successfully creates the object then a 1 is passed, if not then hal created it and a 0 is passed 
from the read. In lyle's run command we determine based on the temporary read stored in him, whether it was a 1 or a 0 
passed and then adds it to a byte. After enough bits are passed to fill the byte, the byte is written to the out file. 

After testing, we have determined that our process of having the instructions created independently of the bit writing 
process infinitely speeds up the total bandwidth of the channel.   


[Finish]
We finished all of the program. The only odd thing is that with certain online files there is a hidden header file
which appears as negative numbers which ASCII doens't interpret. This isn't a problem with the test files we chose 
to use, but is a strange aspect of certain files.  

[Test Cases]
We have 5 different test files each of which are included in the zip file. They are of differing sizes and we are actually 
able to determine a bandwidth limit to our channel. 

testInputFile 

war_plan

pride_and_prejudice

moby_dick

war_and_peace