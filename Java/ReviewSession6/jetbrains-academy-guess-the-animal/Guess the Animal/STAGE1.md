<h2>Description</h2>

<p>You have probably played some version of this game before: one player thinks of a person, an animal, or something else, and another one tries to guess what it is based on the answers. In this project, we will create a similar text game where a computer tries to guess an animal that the user has thought of. One of the first versions of this game was written by Arthur Luehrmann at Dartmouth College in the 1960s.</p>

<p>In the first stage, we will implement greetings and goodbyes, as well as the formation of a question based on the name of the animal.</p>

<h2>Objectives</h2>

<p>The computer should greet the user based on the time of the day. “Good morning” should be used from 5:00 am to 12:00 pm, “Good afternoon” is best for the period between 12:00 pm and 6:00 pm, and “Good evening” is used after 6 p.m. You can also get creative and use “Hi, Night Owl” for late night or “Hi, Early Bird” for early morning.</p>

<p>After greeting the user, the program should ask for an animal. The animal should be entered in the following format: a/an + name of the animal, for example, “an elephant”. The user may or may not specify the article. If the article is not specified, the program should determine it according to the rules. For simplicity, let's assume that all words beginning with a vowel take the article "an", and those that begin with a consonant take the article "a".</p>

<p>After entering the name of the animal, the program should formulate the question <code class="java">"Is it a/an &lt;animal&gt;?"</code>. The question must use the article specified by the user or determined by the program.</p>

<p>Then, the user should answer this question. The computer must perceive these responses as positive: <em>y, yes, yeah, yep, sure, right, affirmative, correct, indeed, you bet, exactly, you said it</em>. The negative answer could be: <em>n, no, no way, nah, nope, negative, I don't think so, yeah no</em>. The letters can be in any case and there can be a period or an exclamation mark at the end of the statement. In case the user's answer is not clear, the program should ask the user to clarify.</p>

<p>With the clarification question, you don't have to stick to one formal phrase. We are creating a game, so let your fantasy fly! Make the computer ask again in a different way each time. The only condition is that the phrase should contain "yes or no". Check out some example phrases below, but you are free to invent your own phrases:</p>

<blockquote>
    <p><em>"I'm not sure I caught you: was it yes or no?"<br>
        "Funny, I still don't understand, is it yes or no?"<br>
        "Oh, it's too complicated for me: just tell me yes or no."<br>
        "Could you please simply say yes or no?<br>
        "Oh, no, don't try to confuse me: say yes or no."</em></p>
</blockquote>

<p>After accepting the answer, the program should say goodbye to the user. To make our program more lively, let there be several ways to do it. If you need some examples of cool goodbye phrases, watch a Youtube video <a target="_blank" href="https://www.youtube.com/watch?v=DKFA5_E9WY8" rel="nofollow noopener noreferrer">33 ways to say "Bye"</a></p>

<h2>Examples</h2>

<p>The greater-than symbol followed by a space <code class="java">&gt; </code> represents the user input. Note that it's not part of the input.</p>

<p><strong>Example 1</strong></p>

<pre><code class="language-no-highlight">Good morning!

Enter an animal:
&gt; cat
Is it a cat?
&gt; Yeah
You answered: Yes

Have a nice day!</code></pre>

<p><strong>Example 2</strong></p>

<pre><code class="language-no-highlight">Good afternoon!

Enter an animal:
&gt; unicorn
Is it an unicorn?
&gt; Oops..
Come on, yes or no?
&gt; Nope
You answered: No

See you soon!</code></pre>

<p><strong>Example 3</strong></p>

<pre><code class="language-no-highlight">Good evening!

Enter an animal:
&gt; a unicorn
Is it a unicorn?
&gt; Sure!
You answered: Yes

Bye!</code></pre>
