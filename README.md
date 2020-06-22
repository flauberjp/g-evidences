[![flauberjp](https://circleci.com/gh/flauberjp/my-git-usage-evidences.svg?style=shield)](https://circleci.com/gh/flauberjp/my-git-usage-evidences/tree/master) <a href="translations/README.pt_br.md"><img align="right" src="https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/240/google/241/flag-brazil_1f1e7-1f1f7.png" width="22"></a> <a href="translations/README.md"><img align="right" src="https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/240/google/241/flag-united-states_1f1fa-1f1f8.png" width="22"></a>
# my-git-usage-evidences

## github user for testing

This project has an [Github account](https://github.com/mygitusageevicencesapp) to support testing, for more information how to use it, please contact @flauberjp.

## Call for Action
Volunteers are welcome! If you would like to join the team, read instructions in [Be a volunteer](CONTRIBUTING.md) to check what is needed in order to start.

## Using the solution, yet using few manuals steops

1. Generate the fat-jar
    * Use your IDE chosing package Maven Lifecycle or use the command line, running the following command in the root of the project: mvn package    
    * A file is generated at ./target/my-git-usage-evidences-1.1-SNAPSHOT-jar-with-dependencies.jar

2. Create a directory to store the files of this solution
    * Create the directory my-git-usage-evidences at C:\Program Files
    * Copy the file my-git-usage-evidences-1.1-SNAPSHOT-jar-with-dependencies.jar to C:\Program Files\my-git-usage-evidences

3. Create a project in your remote repository
    * As administrador open the command prompt at C:\Program Files\my-git-usage-evidences, directory which was created in the previous step
    * From that path, run the this command: java -cp my-git-usage-evidences-1.1-SNAPSHOT-jar-with-dependencies.jar io.github.flauberjp.forms.FormForTesting
    * On the form that shows up, enter your github cridentials (username and password), and click the button Validar Credenciais
    * With the validated credentials, click the Criar projeto no repo remoto button
    * Verify that your github repository and confirm that a repo named my-git-usage-evidences-repo was created (this name is customizable, it is just to edit the value of the text field Repositório in the form, as example, let's assume you used the default value my-git-usage-evidences-repo)

4. Generate the file propriedades.txt
    * As adminstrator open the command prompt on directory C:\Program Files\my-git-usage-evidences
    * On that path, run this command: java -cp my-git-usage-evidences-1.1-SNAPSHOT-jar-with-dependencies.jar io.github.flauberjp.forms.FormForTesting
    * On the form that shows up, type yours github credentials (username and password), then clique Validar Credenciais button. 
    * With the validated credentials, click Salvar Dados em propriedades.txt button ( We are assume the repo name is the default one, my-git-usage-evidences-repo)
    * Confirm that arquivo propriedades.txt file was generated at C:\Program Files\my-git-usage-evidences

5. Generate the hook
    * As administrator, open the command prompt, go to C:\Program Files\my-git-usage-evidences
    * From that path, run this command: java -cp my-git-usage-evidences-1.1-SNAPSHOT-jar-with-dependencies.jar io.github.flauberjp.forms.FormForTesting
    * In the form that shows up, click Gerar hook(arquivo pre-push) button
    * Confirm that pre-push file was generated at C:\Program Files\my-git-usage-evidences

6. Use the solution
    * Pick one of your git projects (a git project is one that has a .git hidden folder on its roots)
    * Copy the file C:\Program Files\my-git-usage-evidences\pre-push to yours .git\hooks git project

7. Testing the solution
    * Open one of yours git project where you just configured used this solution
    * Do any modification, then commit the modification, then push it, what will trigger our solution
    * Verify your github repo created on step "3. Create a project in your remote repository"
    * Assuming its name is the default one,  my-git-usage-evidences-repo, you can check that its commit was amount increased by one (repeat the operation to see this number increase again), and with that your [Github Contributions](https://help.github.com/en/github/setting-up-and-managing-your-github-profile/viewing-contributions-on-your-profile#contributions-calendar) Calendar got marked, an evidence on Github that you just used git locally

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

