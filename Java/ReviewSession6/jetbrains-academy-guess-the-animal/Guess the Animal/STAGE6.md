<h2>Description</h2>

<p>Your program is already really cool: as a matter of fact, it's too cool to be available only to the English speakers. In this stage, we will make our game international by adding support for different countries and languages.</p>

<p>As an example, we will use a neutral language <a target="_blank" href="https://en.wikipedia.org/wiki/Esperanto" rel="noopener noreferrer nofollow">Esperanto</a>. Resource files for this language are available for download in the attachment for this step. To pass the tests, you need to internationalize the program and add support for this language. We also encourage you to add support for another language of your choice: it could be your native language or a language you are learning.</p>

<h2>Theory</h2>

<p>Program internationalization is a complex process. To be ready to implement it, check out an <a target="_blank" href="https://www.baeldung.com/java-8-localization" rel="noopener noreferrer nofollow">article on localization on baeldung.com</a>. To support different languages, Java uses <a target="_blank" href="https://www.baeldung.com/java-resourcebundle" rel="noopener noreferrer nofollow">resource files</a>, so you want to be confident working with them, too.</p>

<p>To add a resource file to the project, you need to create a subdirectory <em>main/resources</em> in the <em>src </em>directory: this is where we should put the resource files. They are typically text property files (.properties), but they can also be XML files or regular Java classes. If they are Java classes, then we can not only use text strings but also return Java objects specific to the selected language. Let's take a closer look at using Java classes.</p>

<p>To create a Java resource class, you must inherit from the class <code class="language-java">ListResourceBundle</code>. We also need to rewrite the method <code class="language-java">getContents</code> to return a two-dimensional array of objects. Here is an example of a class for the default language:</p>

<pre><code class="language-java">public class App extends ListResourceBundle {
   @Override
   protected Object[][] getContents() {
       return new Object[][]{
               {"hello", “Hello!”},
               {"bye", new String[]{
                       "Bye!",
                       "Bye, bye!",
                       "See you later!",
                       "See you soon!",
                       "Have a nice one!"
               }},
               {"animal.name", (UnaryOperator&lt;String&gt;) name -&gt; {
                    If (name.matches("[aeiou].*") {
                        return "an " + name;
                    } else {
                        return "a " + name;
                    }
               }},
             {"animal.question", (UnaryOperator&lt;String&gt;) animal -&gt; "Is it " + animal + "?"}
       };
   }
}
</code>
</pre>

<p>In this example, we have created an <code class="language-java">App</code> resource class with three keys: <em>hello</em>, <em>bye</em>, and <em>animal.name</em>. The key “hello” is of the type String, the key “bye” is an array of strings, and the key “animal.name” is a <code class="language-java">UnaryOperator</code> object.</p>

<p>Now you can load this class in your program and get resources using the following code:</p>

<pre><code class="language-java">var appResource = ResourceBundle.getBundle("App");

var helloString = appResource.getString("hello"));
var byeStringArray = appResource.getStringArray("bye"));
var animalName = (UnaryOperator) appResource.getObject("animal.name");
</code>
</pre>

<p>For Esperanto, this class should be named <em>App_eo. </em>It should look something like this:</p>

<pre><code class="language-java">public class App_eo extends ListResourceBundle {
   @Override
   protected Object[][] getContents() {
       return new Object[][]{
               {"hello", “Saluton!”},
               {"bye", new String[]{
                       "Ĝis!",
                       "Ĝis revido!",
                       "Estis agrable vidi vin!"
               }},
               {"animal.name", (UnaryOperator&lt;String&gt;) name -&gt; name},
               {"animal.question", (UnaryOperator&lt;String&gt;) animal -&gt;
                    "Ĉu ĝi estas " + animal + "?"}
       };
   }
}
</code>
</pre>

<p>As you can see, we can store grammar rules for a particular language in the resource class. Here are all the rules that you need to add to the resource file to support Esperanto:</p>

<ol>
    <li>Statements must begin with the word <strong>Ĝi</strong>….<br>
        <strong>It is</strong> a mammal = <strong>Ĝi</strong> estas mamulo<br>
        <strong>It can</strong> fly = <strong>Ĝi</strong> povas flugi<br>
        <strong>It has</strong> horns = <strong>Ĝi</strong> havas kornojn</li>
    <li>Negative facts are formed by simply adding <strong>ne</strong> after the word <strong>Ĝi</strong>:<br>
        <strong>It isn’t</strong> a mammal = <strong>Ĝi ne</strong> estas mamulo<br>
        <strong>It can't</strong> fly = <strong>Ĝi ne</strong> povas flugi<br>
        <strong>It doesn't have</strong> horns = <strong>Ĝi ne</strong> havas kornojn</li>
    <li>Questions are formed by simply adding the particle <strong>Ĉu</strong> to the beginning of the statement:<br>
        <strong>Is it</strong> a mammal? = <strong>Ĉu</strong> ĝi estas mamulo?<br>
        <strong>Can it</strong> fly? = <strong>Ĉu</strong> ĝi povas flugi?<br>
        <strong>Does it have</strong> horns? = <strong>Ĉu</strong> ĝi havas kornojn?</li>
    <li>There is no indefinite article in Esperanto.<br>
        <strong>a</strong> cat = kato<br>
        <strong>an</strong> ape = simio</li>
    <li>Esperanto, like English, has a definite article.<br>
        <strong>The</strong> cat = <strong>La</strong> kato<br>
        <strong>The</strong> dog = <strong>La</strong> hundo.</li>
    <li>To ask about an animal, we need to write “<strong>Ĉu ĝi estas</strong>…?”<br>
        <strong>Is it</strong> a cat? = <strong>Ĉu ĝi estas</strong> kato?</li>
</ol>

<p>When you are done with internationalization, you can select the language when you start the program using the "user.language" key of the Java virtual machine. For example, to specify the Esperanto language, you need to run the following command:</p>

<pre><code class="language-no-highlight">java -Duser.language=eo Main</code></pre>

<p>In the IntelliJ development environment, you can specify this key in the “Run / Debug Configurations” in the “VM Options” field.</p>

<h2>Objective</h2>

<p>You need to add support for Esperanto. You can get the translation of phrases in the attached <a target="_blank" href="https://stepik.org/media/attachments/lesson/385218/resources.zip" rel="noopener noreferrer nofollow">resource files</a>, or you can use <a target="_blank" href="https://translate.google.com/#view=home&amp;op=translate&amp;sl=en&amp;tl=eo" rel="noopener noreferrer nofollow">Google Translate </a>to translate from English into Esperanto. You need to implement the correct rules for constructing questions and negative statements. When saving the knowledge tree to the disk, you must add the language prefix to the file name, for example: <code class="language-java">animals_eo.json</code>. In the test directory, you can see the test script in the <code class="language-java">esperanto.script.yaml</code> file.</p>