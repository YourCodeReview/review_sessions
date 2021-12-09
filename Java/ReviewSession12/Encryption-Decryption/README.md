# Encryption-Decryption_JetBrainsAcademy
Program that takes Command line arguments and encrypts or decrypts String.

//user input

User can pass string that he wants to process as a command line argument(String) or as a path to a .txt file location.


//choosing processing algorithms

There are 4 algorithms, that could be chosen to process input data.

Unicode - takes each character and moves it by user defined amount of values in unicode.

Sift - does the same as Unicode but just for latin letters.

Unicode - Encode 

Unicode - Decode

Shift - Encode

Shift - Decode


//
User can pass argument that returns processed data as new .txt file, or just prints proceed data.

//Example

Example 1

java Main -mode enc -in road_to_treasure.txt -out protected.txt -key 5 -alg unicode
This command must get data from the file road_to_treasure.txt, encrypt the data with the key 5, create a file called protected.txt and write ciphertext to it.

Example 2

Input:

java Main -mode enc -key 5 -data "Welcome to hyperskill!" -alg unicode
Output:

\jqhtrj%yt%m~ujwxpnqq&


Example 3

Input:

java Main -key 5 -alg unicode -data "\jqhtrj%yt%m~ujwxpnqq&" -mode dec
Output:

Welcome to hyperskill!



Example 4:

Input:

java Main -key 5 -alg shift -data "Welcome to hyperskill!" -mode enc
Output:

Bjqhtrj yt mdujwxpnqq!



Example 5:

Input:

java Main -key 5 -alg shift -data "Bjqhtrj yt mdujwxpnqq!" -mode dec
Output:

Welcome to hyperskill!
