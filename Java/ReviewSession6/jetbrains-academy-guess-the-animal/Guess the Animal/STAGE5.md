<h2>Description</h2>

<p>Our program is turning from a simple game into an expert system. In this stage, we will add a menu that will allow you not only to play with the computer but also get different information from the knowledge base.</p>

<h2>Objectives</h2>

<p>As before, the program first greets the user. If a database has not been created yet, the program should ask the user about their favorite animal. This animal will become the basis of the newly created knowledge base. The program should then offer the user to play.</p>

<p>If a knowledge tree already exists, the computer loads it, greets the user as an expert system in animals, and offers the user a menu. The menu has to include at least these five items:</p>

<ol>
    <li>Play the guessing game</li>
    <li>List of all animals</li>
    <li>Search for an animal</li>
    <li>Calculate statistics</li>
    <li>Print the Knowledge Tree</li>
    <li>Exit</li>
</ol>

<h2>Examples</h2>

<p>The greater-than symbol followed by a space <code class="java">&gt; </code> represents the user input. Note that it's not part of the input.</p>

<p><strong>Example 1: the game starts anew</strong></p>

<pre><code class="language-no-highlight">Good evening!

I want to learn about animals.
Which animal do you like most?
&gt; cat

Welcome to the animal expert system!

What do you want to do:

1. Play the guessing game
2. List of all animals
3. Search for an animal
4. Calculate statistics
5. Print the Knowledge Tree
0. Exit
&gt; 1
You think of an animal, and I guess it.
Press enter when you're ready.
&gt;
Is it a cat?
&gt; No
I give up. What animal do you have in mind?
&gt; a shark
Specify a fact that distinguishes a cat from a shark.
The sentence should be of the format: 'It can/has/is ...'.
&gt; It is a mammal
Is this fact correct for the shark?
&gt; No
Wonderful! I've learned so much about animals!
Do you like to play again?
&gt; No
What do you want to do:

1. Play the guessing game
2. List of all animals
3. Search for an animal
4. Calculate statistics
5. Print the Knowledge Tree
0. Exit
&gt; 0
Thank you and goodbye!</code></pre>

<p><strong>Example 2: an already existing knowledge base has been loaded</strong></p>

<pre><code class="language-no-highlight">Good afternoon!

Welcome to the animal expert system!

What do you want to do:

1. Play the guessing game
2. List of all animals
3. Search for an animal
4. Calculate statistics
5. Print the Knowledge Tree
0. Exit</code></pre>

<p><strong>Example 3: the user wants to search for an animal</strong></p>

<p>The search has to accept the animal's name with or without the article. If the animal is found, the program should print all the facts it knows about this animal.</p>

<pre><code class="language-no-highlight">Your choice:
3
Enter the animal:
hare
Facts about the hare:
 - It is a mammal.
 - It is living in the forest.
 - It doesn't have a long bushy tail.
 - It is a shy animal.
</code></pre>

<p>If the animal is not found, print this information for the user:</p>

<pre><code class="language-no-highlight">Your choice:
3
Enter the animal:
rabbit
No facts about the rabbit.
</code></pre>

<p><strong>Example 4: the user wants to see the list of all animals</strong></p>

<p>The program should traverse the knowledge tree and collect all the animal names into a list. The list of animals should be sorted in ascending order without articles.</p>

<pre><code class="language-no-highlight">Your choice:
2
Here are the animals I know:
 - cat
 - dog
 - fox
 - hare
 - shark
 - wolf
</code></pre>

<p><strong>Example 5: the user wants to see the stats</strong></p>

<p>The program should print the root node, the size of the knowledge tree (number of nodes), the number of animals, the number of facts, and the maximum and minimum depth of the tree which correspond to the minimum and maximum number of questions the program gets before it wins or gives up.</p>

<pre><code class="language-no-highlight">&gt; 4
The Knowledge Tree stats

- root node                    It is a mammal
- total number of nodes        11
- total number of animals      6
- total number of statements   5
- height of the tree           4
- minimum depth                1
- average depth                3.0
</code></pre>

<p><strong>Example 6: the user wants to print tree</strong></p>

<p>The program should print the Knowledge Tree.</p>

<pre><code class="language-no-highlight">&gt; 5

 └ Is it a mammal?
  ├ Is it living in the forest?
  │├ Is it a shy animal?
  ││├ a hare
  ││└ a wolf
  │└ Can it climb tree?
  │ ├ a cat
  │ └ a dog
  └ a shark
</code></pre>