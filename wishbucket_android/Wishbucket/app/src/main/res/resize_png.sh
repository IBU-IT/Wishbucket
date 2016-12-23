#!/usr/bin/env bash

# quit on first error
set -e 

# check for sips
command -v sips >/dev/null 2>&1 || { 
    echo >&2 "sips is not installed.  Aborting."
    exit 1
}

command -v bc >/dev/null 2>&1 || { 
    echo >&2 "bc is not installed.  Aborting."
    exit 1
}

# usage
USAGE="Usage: $0 base_height base_width inputfile"
if [ "$#" -lt 3 ]; then
    echo "Too few number of parameters."
	echo "$USAGE"
	exit 1
fi


base_height=$1
base_width=$2
input_filename=$3

echo "filename: $input_filename"

dirarray=("drawable-mdpi" "drawable-hdpi" "drawable-xhdpi" "drawable-xxhdpi" "drawable-xxxhdpi")
multipliers=(1 1.5 2 3 4)
# create sub directories
for i in ${dirarray[*]}; do
    mkdir -p $i
done

for i in {0..4}; do
    height=$(echo "$base_height * ${multipliers[$i]}" | bc)
    width=$(echo "$base_width * ${multipliers[$i]}" | bc)
    echo "height = $height, width = $width"
    outputname="${dirarray[$i]}/$input_filename"
    sips -z $height $width $input_filename --out $outputname
done
