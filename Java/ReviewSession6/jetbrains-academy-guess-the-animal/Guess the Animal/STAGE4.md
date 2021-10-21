<h2>Description</h2>

<p>Our program is growing and learning so quickly, but just as quickly it forgets everything as soon as we finish the game session. Let's save the precious information: in this stage, you need to implement saving the accumulated knowledge. Now, when the user starts the program, the computer will load the knowledge tree saved in the memory from the previous session.</p>

<h2>Theory</h2>

<p>We will save the knowledge base in one of the following formats: JSON, XML, YAML. For this, we will use the library <a target="_blank" href="https://en.wikipedia.org/wiki/Jackson_(API)" rel="nofollow noopener noreferrer">Jackson</a>.</p>

<p>First of all, we must add the required dependencies to our <em>build.gradle</em> file:</p>

<pre><code class="language-java">dependencies {
    compile group: 'com.fasterxml.jackson.core', name: 'jackson-databind', version: '2.11.2'
    compile group: 'com.fasterxml.jackson.dataformat', name: 'jackson-dataformat-xml', version: '2.11.2'
    compile group: 'com.fasterxml.jackson.dataformat', name: 'jackson-dataformat-yaml', version: '2.11.2'
}</code></pre>

<p>In order to map a Java object to the file, we should create an instance of the corresponding ObjectMapper class. For JSON, it is <code class="language-java">JsonMapper()</code>, for XML — <code class="language-java">XmlMapper()</code>, and for YAML file format it is <code class="language-java">YAMLMapper()</code>.</p>

<p>Consider an example. Say we have a Java class to describe a tree node:</p>

<pre><code class="language-java">public class TreeNode {
    private String data;
    private TreeNode yes;
    private TreeNode no;

    public TreeNode() {
    }
    // Public constructor is required to map java class
    // We skip the public Setters and Getters ...
}
</code></pre>

<p>In our program, we define the tree root as <code class="language-java">TreeNode root</code> and build a knowledge tree during game sessions. Now we would like to save our tree to the disk in JSON format. To accomplish this task, we should define the file name and create an instance of ObjectMapper:</p>

<pre><code class="language-java">    String fileName = "animals.json";
    ObjectMapper objectMapper = new JsonMapper();
</code></pre>

<p>Then, we can save our knowledge tree to JSON file using the statement:</p>

<pre><code class="language-java">    objectMapper
            .writerWithDefaultPrettyPrinter()
            .writeValue(new File(fileName), root);
</code></pre>

<p>To load our knowledge tree, we can use the following command:</p>

<pre><code class="language-java">    root = objectMapper.readValue(new File(fileName), TreeNode.class);
</code></pre>

<p>For both cases, you should try/catch possible exceptions.</p>

<p>To avoid storing null values in the JSON file and skip parsing additional methods that can be presented in our TreeNode class, we can use Jackson Annotations:</p>

<pre><code class="language-java">@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeNode {
    private String data;
    private TreeNode yes;
    private TreeNode no;

    public TreeNode() {
    }

    @JsonIgnore
    public boolean isLeaf() {
        return no == null &amp;&amp; yes == null;
    }

    // public Setters and Getters ...
}
</code></pre>

<p>Here is an example of a JSON file:</p>

<pre><code class="language-json">{
  "data" : "It is a mammal",
  "yes" : {
    "data" : "It is living in the forest",
    "yes" : {
      "data" : "It has a long bushy tail",
      "yes" : {
        "data" : "a fox"
      },
      "no" : {
        "data" : "It is a shy animal",
        "yes" : {
          "data" : "a hare"
        },
        "no" : {
          "data" : "a wolf"
        }
      }
    },
    "no" : {
      "data" : "It can climb trees",
      "yes" : {
        "data" : "a cat"
      },
      "no" : {
        "data" : "a dog"
      }
    }
  },
  "no" : {
    "data" : "a shark"
  }
}</code></pre>

<h2>Objectives</h2>

<p>When starting the program, the user can specify the parameter "-type" with one of the following options: "json", "xml", or "yaml". If the parameter is not specified, the program should select the default format "json".</p>

<p>You should polish the output before saving it to the disk. This will allow you to easily review the file in any text editor and make changes to it if necessary.</p>

<p>At the start, the program must search for the file with the database. If the file isn't found, the computer must ask the user about their favorite animal and create a new knowledge tree.</p>

<h2>Examples</h2>

<p>The greater-than symbol followed by a space <code class="language-java">&gt; </code> represents the user input. Note that it's not part of the input.</p>

<p><strong>Example 1</strong></p>

<pre><code class="language-no-highlight">Good morning!

I want to learn about animals.
Which animal do you like most?
&gt; cat
Wonderful!
I've learned so much about animals!
Let's play a game!
You think of an animal, and I guess it.
Press enter when you're ready.
&gt; Is it a cat?
&gt; No
I give up. What animal do you have in mind?
&gt; a dog
Enter a statement which can help me distinguish a cat from a dog.
&gt; It can climb trees.
Is that fact correct for a dog?
&gt; No
I remember the following facts about animals:
- The cat can climb trees.
- The dog can't climb trees.

I've learned so much about animals!
Would you like to play again?
&gt; No

Have a nice day!</code></pre>

<p><strong>Example 2</strong></p>

<p>If a knowledge base already exists, the program doesn't ask the user about the favorite animal: it offers to play a game instead.</p>

<pre><code class="language-no-highlight">Hi Night Owl!

I know a lot about animals.
Let's play a game!
You think of an animal, and I guess it.
Press enter when you're ready.
&gt;
Can it climb trees?
&gt; Sure!
Come on, yes or no?
&gt; Yeah
Is it a cat?
&gt; Nope
I give up. What animal do you have in mind?
&gt; Lynx
Enter a statement that can help me distinguish a cat from a lynx.
&gt; It has tassels on its ears
Is the fact correct for a lynx?
&gt; yea
I remember the following facts about animals:
- The cat hasn't tassels on its ears.
- The lynx has tassels on its ears.

I've learned so much about animals!
Would you like to play again?
&gt; No

Good night!
</code></pre>