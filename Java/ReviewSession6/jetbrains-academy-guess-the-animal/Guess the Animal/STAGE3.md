<h2>Description</h2>

<p>It's time to play! The program prompts the user to think of an animal and then tries to guess what it is. If the computer fails, the program should ask the user what this animal is and what statement can help distinguish it from another one. This knowledge is supposed to be stored in a form of a binary tree and kept in memory only for the duration of the game session.</p>

<h2>Objectives</h2>

<p>The program should ask the user about their favorite animal. This animal name will be the root node in our knowledge tree. When the computer starts the game, it will ask questions starting from the top, that is, the root node. If the computer makes a wrong guess, it should ask the user two questions: first, what animal the user had in mind, and second, what statement can help the computer distinguish the animal it guessed (old) from the animal that the person actually thought of (new). The program should clarify whether that fact is correct for the new animal. After that, the name of the "old" animal in the tree is replaced with the new statement, and two new leaves are added to this node: one with the "old" animal and another with the "new" animal.</p>

<p>Let's look at an example so that you can better visualize the game process.</p>

<p>When the program starts and prompts the user for their favorite animal, the user replies that it is a cat.</p>

<pre><code class="java">       |
    ( Cat )
      | |
   null null</code></pre>

<p>We get a binary tree where the root node is unique and has no children. Such a node is called a <strong>leaf</strong>.</p>

<p>Suppose that then the user has thought of a dog. To the computer's question "Is it a cat?", they answer negatively. The user then enters the name of the intended animal, "dog", and the statement "It can climb trees". The correct answer for a dog is "no". Knowing this, the computer builds a new tree:</p>

<pre><code class="language-no-highlight">    ( Can it climbs trees? )
       |                |
      yes               no
       |                |
    ( Cat )          ( Dog )
      | |              | |
   null null        null null</code></pre>

<p>In the new tree, the root element is the statement, and the node has two children: the names of the animals.</p>

<p>So, if the program does not guess the animal correctly, two items are added to the knowledge tree: the name of the new animal and a fact that distinguishes one animal from the other. After some game time, the knowledge tree could look something like this:</p>

<p><img alt="" src="https://ucarecdn.com/e1566b86-c4ef-41da-99ad-198a0cb6dc16/"></p>

<p>At the end of each session, the program should ask the user if they want to keep playing or quit the game. In this stage, we are not yet saving the knowledge tree to the disk, so when the user decides to finish the game, the built knowledge tree will disappear. Don't worry: in the next step, we will learn how to save it.</p>

<h2>Example</h2>

<p>The greater-than symbol followed by a space <code class="java">&gt; </code> represents the user input. Note that it's not part of the input.</p>

<pre><code class="language-no-highlight">Hi, Early Bird!

I want to learn about animals.
Which animal do you like most?
&gt; cat
Wonderful! I've learned so much about animals!
Let's play a game!
You think of an animal, and I guess it.
Press enter when you're ready.
&gt;
Is it a cat?
&gt; No
I give up. What animal do you have in mind?
&gt; a shark
Specify a fact that distinguishes a cat from a shark.
The sentence should satisfy one of the following templates:
- It can ...
- It has ...
- It is a/an ...

&gt; It is a mammal
Is the statement correct for a shark?
&gt; No
I have learned the following facts about animals:
 - The cat is a mammal.
 - The shark isn't a mammal.
I can distinguish these animals by asking the question:
 - Is it a mammal?
Nice! I've learned so much about animals!

Would you like to play again?
&gt; No

Have a nice day!</code></pre>