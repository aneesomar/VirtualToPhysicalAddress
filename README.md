# OS1Assignment Address Converter

### Author

Anees Omar (OMRANE005)

## description

This program translates virtual addresses to physical addresses using a page table aswell as extracting the offset and virtual page number from the virtual address. It reads a file containing 8-byte hexadecimal virtual addresses, converts them, and writes the physical addresses to a new file named "output-OS1".

## Compile: 
```bash
javac OS1Assignment.java 
```

## Run:
```bash
java OS1Assignment input_file_name 
```

## Input File:
should be a binary file containing virtual addresses

## Output File:
The output file (output-OS1) will contain the physical addresses corresponding to the virtual addresses in the input file. 
Each physical address will be written as a long integer in little-endian byte order.

