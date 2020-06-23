[![flauberjp](https://circleci.com/gh/flauberjp/my-git-usage-evidences.svg?style=shield)](https://circleci.com/gh/flauberjp/my-git-usage-evidences/tree/master) <a href="translations/README.pt_br.md"><img align="right" src="https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/240/google/241/flag-brazil_1f1e7-1f1f7.png" width="22"></a> <a href="translations/README.md"><img align="right" src="https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/240/google/241/flag-united-states_1f1fa-1f1f8.png" width="22"></a>
# my-git-usage-evidences

## github user for testing

This project has an [Github account](https://github.com/mygitusageevicencesapp) to support testing, for more information how to use it, please contact @flauberjp.

## Call for Action
Volunteers are welcome! If you would like to join the team, read instructions in [Be a volunteer](CONTRIBUTING.md) to check what is needed in order to start.

## How to use

1. Generate the fat-jar
    * Use your IDE choosing package Maven Lifecycle or use 
    the command line, running the following command in the 
    root of the project, what will generate the jar file _./target/my-git-usage-evidences-1.1-SNAPSHOT-jar-with-dependencies.jar_  
      > mvn package
      * You could skip this step, just [click download this .jar file from our 1st version](https://github.com/flauberjp/my-git-usage-evidences/releases/download/1.0-SNAPSHOT/my-git-usage-evidences-1.0-SNAPSHOT-jar-with-dependencies.jar)

2. Create a directory to store the files of this solution
    * Create this directory _C:\Program Files\my-git-usage-evidences_
    * Copy the jar file that directory

3. Create a project in your remote repository
    * As administrador open the command prompt at _C:\Program Files\my-git-usage-evidences_
    * From that path, run this command: _java -cp my-git-usage-evidences-1.1-SNAPSHOT-jar-with-dependencies.jar io.github.flauberjp.forms.FormForTesting_
    * This solution program starts, then enter your github credentials (username and password), and click the button "_Validar Credenciais_"
    * With the credentials validated, click the "_Criar projeto no repo remoto_" button
    * Access your Github and verify that it contains a repo named _my-git-usage-evidences-repo_. 
    Here we are assuming the value of the text field _Repositório_ was not modified

4. Generate the file _propriedades.txt_
    * Back to the solution program, click "_Salvar Dados em propriedades.txt" button
    * Confirm that arquivo _propriedades.txt_ file was generated at _C:\Program Files\my-git-usage-evidences_

5. Generate the hook
    * Back to the solution program, click "_Gerar hook(arquivo pre-push)_" button
    * Confirm that _pre-push_ file, which is the hook, was generated at _C:\Program Files\my-git-usage-evidences_

6. Apply the hook to a git project
    * Pick one of your git projects (a git project is a folder that has a .git hidden one on its roots)
    * Copy the hook to yours .git\hooks git project folder

7. Testing the solution
    * Open one of yours git project where you just configured used this solution
    * Do any modification, then commit the modification, then push it, what will trigger our solution
    * Access your github repo created on step 3. _my-git-usage-evidences-repo_
    * Check that the commit amount increased by one. Repeat the operation to see this 
    number increase again. And with that your 
    [Github Contributions](https://help.github.com/en/github/setting-up-and-managing-your-github-profile/viewing-contributions-on-your-profile#contributions-calendar) 
    Calendar got marked, an evidence on Github that you just used git locally

## References
- [GitHub API for Java](https://github-api.kohsuke.org/)
- [Reading from config.properties file Maven project](https://stackoverflow.com/questions/35008377/reading-from-config-properties-file-maven-project)
- [How to load an external properties file from a maven java project](https://stackoverflow.com/questions/34712885/how-to-load-an-external-properties-file-from-a-maven-java-project)
- [Can you tell on runtime if you're running java from within a jar?](https://stackoverflow.com/questions/482560/can-you-tell-on-runtime-if-youre-running-java-from-within-a-jar)
- [How to get the real path of Java application at runtime?](https://stackoverflow.com/questions/4032957/how-to-get-the-real-path-of-java-application-at-runtime)
- [File loading by getClass().getResource](https://stackoverflow.com/questions/14089146/file-loading-by-getclass-getresource)
- [JUnit 5 Tutorial: Running Unit Tests With Maven](https://www.petrikainulainen.net/programming/testing/junit-5-tutorial-running-unit-tests-with-maven/)
- [Creating a personal access token for the command line](https://help.github.com/en/github/authenticating-to-github/creating-a-personal-access-token-for-the-command-line)
- [How to get the real path of Java application at runtime?](https://stackoverflow.com/a/43553093/6771132)

## License
The MIT License (MIT)

Prove your coding activity throughout any cv (Gitlab, Bitbucket etc.)  using this Tool. 

