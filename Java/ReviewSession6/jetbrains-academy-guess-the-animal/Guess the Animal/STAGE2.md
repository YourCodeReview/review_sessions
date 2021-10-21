<h2>Description</h2>

<p>Now the program should ask for two animals and a fact that can help distinguish one animal from the other. The program should only accept statements of a certain template. Based on the entered statement, the program should print facts about the animals.</p>

<h2>Objectives</h2>

<p>The program should greet the user and ask them to enter the names of two animals. Then, it should prompt the user to specify a fact about one of the animals that can help distinguish it from the other one. The program should only accept statements of a certain template: "It can/has/is...". In case of incorrect input, the program should show examples of correct statements.</p>

<p>Then, the program needs to find out from the user if the statement is correct for the second animal. Based on that, the program should generate two facts about both animals. In addition, it should also report to the user what question can help it guess the animal correctly. After that, the program should say goodbye to the user.</p>

<h2>Examples</h2>

<p>The greater-than symbol followed by a space <code class="java">&gt; </code> represents the user input. Note that it's not part of the input.</p>

<p><strong>Example 1</strong></p>

<pre><code class="language-no-highlight">Hello!

Enter the first animal:
&gt; Cat
Enter the second animal:
&gt; Dog
Specify a fact that distinguishes a cat from a dog.
The sentence should be of the format: 'It can/has/is ...'.

&gt; It can climb trees.
Is it correct for a dog?
&gt; No
I have learned the following facts about animals:
- The cat can climb trees.
- The dog can't climb trees.
I can distinguish these animals by asking the question:
- Can it climbs trees?

Thank you and Goodbye!</code></pre>

<p><strong>Example 2</strong></p>

<pre><code class="language-no-highlight">Good afternoon!

Enter the first animal:
&gt; a unicorn
Enter the second animal:
&gt; horse
Specify a fact that distinguishes a unicorn from a horse.
The sentence should be of the format: 'It can/has/is ...'.

&gt; It has a horn
Is it correct for a horse?
&gt; No
I learned the following facts about animals:
 - The unicorn has a horn.
 - The horse doesn't have a horn
I can distinguish these animals by asking the question:
 - Does it have a horn?

Talk to you later!</code></pre>

<p><strong>Example 3</strong></p>

<pre><code class="language-no-highlight">Good evening!

Enter the first animal:
&gt; wolf
Enter the second animal:
&gt; hare
Specify a fact that distinguishes a wolf from a hare.
The sentence should be of the format: 'It can/has/is ...'.

&gt; It is a shy animal
Is it correct for a hare?
&gt; Yes
I learned the following facts about animals:
 - The wolf isn't a shy animal
 - The hare is a shy animal.
I can distinguish these animals by asking the question:
 - Is it a shy animal?

See you later!</code></pre>

<p><strong>Example 4</strong></p>

<pre><code class="language-no-highlight">Good evening!

Enter the first animal:
&gt; cat
Enter the second animal:
&gt; shark
Specify a fact that distinguishes a cat from a shark.
The sentence should be of the format: 'It can/has/is ...'.

&gt; Is it a mammal?
The examples of a statement:
 - It can fly
 - It has horn
 - It is a mammal
Specify a fact that distinguishes a cat from a shark.
The sentence should be of the format: 'It can/has/is ...'.
&gt; It is a mammal?
Is it correct for a shark?
&gt; No
I learned the following facts about animals:
 - The cat is a mammal.
 - The shark isn't a mammal.
I can distinguish these animals by asking the question:
 - Is it a mammal?

See you!</code></pre>
