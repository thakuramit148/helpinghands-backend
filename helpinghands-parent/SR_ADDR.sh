#!/bin/sh
if [ $# -eq 0 ]
then
	echo INFO : accepts atleast 1 argument!;	
elif [ $# -eq 2 ]
then
	out=$(sed -i 's/amit/'"$2"'/g' "$1" 2>&1);
	if [ ${#out} -gt 0 ];
	then
		echo $out;
	else	
		echo successfully saved!;
	fi
elif [ $# -eq 3 ]
then
	out=$(sed -i 's/'"$2"'/'"$3"'/g' "$1" 2>&1);
	if [ ${#out} -gt 0 ];
	then
		echo $out;
	else
		echo successfully saved!;
	fi
else
	echo atleast 2 arguments and less than 3 arguments are required, $# arguments are given!;
fi
