# my-git-usage-evidences

## github user for testing

Este projeto possui uma [conta](https://github.com/mygitusageevicencesapp) no github para auxiliar nos testes, para mais informções sobre como utiliza-la, pode contatar @flauberjp

## Ajuda Voluntária
Amamos voluntários! Por favor leia as instruções em [Seja Voluntário](CONTRIBUTING.pt_br.md) para verificar como você pode começar a ajudar.

## Como usar

1. [Faça o download do _fat jar_ da nossa 2a release](https://github.com/flauberjp/my-git-usage-evidences/releases/tag/1.1-SNAPSHOT)

2. Crie um diretório para armazenar os arquivos deste programa
    * Crie o diretório _C:\Program Files\my-git-usage-evidences_
      * Se a língua do seu SO não é inglês, o caminho _C:\Programa Files_ é diferene, 
       por exemplo, quando a língua for português brasileiro, o caminho seria _c:\Arquivos de Programas_
    * Copie o _fat jar_ para esse diretório

3. Inicie o programa
    * Primeiramente, configure o programa java.exe para ser executado como administrador, 
    veja como em [10](https://appuals.com/how-to-run-jar-files-on-windows-10/)
    * Abra o prompt de comando 
    na pasta _C:\Program Files\my-git-usage-evidences_
    * A partir desse caminho, digite o seguinte comando e tecle _Enter_, o que iniciará o programa. 
    Comando: 
      > _java -jar my-git-usage-evidences-jar-with-dependencies.jar_

4. Configure o programa
    * No programa, digite suas credenciais do Github 
    (username e password)
    * Configure o nome do repositório no seu github que será usado
    para registrar o seu uso do git em projetos que não são projetos 
    do tipo Github
    * Selecione os projetos que não são projetos Github que você quer
    monitorar o uso do git neles
      * Para isso, selecione uma pasta que contém a maioria dos seus
      projetos git, clicando no botão "_Selecionar_" e escolhendo a pasta.
      Essa pasta será analisada, e apenas projetos git que não são projetos
      Github serão listados. 
      **Essa analise demora um certo tempo, por favor aguarde**
      * Marque os projetos que você quer monitorar

5. Aplique a configuração
    * Clique no botão "_Aplicar configurações_", e aguarde a mensagem
    de confirmação de que tudo foi configurado corretamente. 

6. Testando a solução
    * Abra seu projeto git onde você acabou de configurar esta solução
    * Faça uma alteração em qualquer arquivo, então realize um commit 
    e em seguida execute um push, o que disparará nossa solução
    * Verifique no seu github o repo criado no passo 3. _my-git-usage-evidences-repo_
    * Verifique que o número de commits foi incrementado. 
    Repita a operação para ve-lo aumentar mais uma vez se preciso.
    E com isso seu [Calendário de contribuições do Github](https://help.github.com/pt/github/setting-up-and-managing-your-github-profile/viewing-contributions-on-your-profile#contributions-calendar) 
    foi marcado, evidenciando no Github de que você acabou de usar o git localmente

## Referências
1. [GitHub API for Java](https://github-api.kohsuke.org/)
2. [Reading from config.properties file Maven project](https://stackoverflow.com/questions/35008377/reading-from-config-properties-file-maven-project)
3. [How to load an external properties file from a maven java project](https://stackoverflow.com/questions/34712885/how-to-load-an-external-properties-file-from-a-maven-java-project)
4. [Can you tell on runtime if you're running java from within a jar?](https://stackoverflow.com/questions/482560/can-you-tell-on-runtime-if-youre-running-java-from-within-a-jar)
5. [How to get the real path of Java application at runtime?](https://stackoverflow.com/questions/4032957/how-to-get-the-real-path-of-java-application-at-runtime)
6. [File loading by getClass().getResource](https://stackoverflow.com/questions/14089146/file-loading-by-getclass-getresource)
7. [JUnit 5 Tutorial: Running Unit Tests With Maven](https://www.petrikainulainen.net/programming/testing/junit-5-tutorial-running-unit-tests-with-maven/)
8. [Creating a personal access token for the command line](https://help.github.com/en/github/authenticating-to-github/creating-a-personal-access-token-for-the-command-line)
9. [How to get the real path of Java application at runtime?](https://stackoverflow.com/a/43553093/6771132)
10. [How to Run .JAR Files on Windows 10](https://appuals.com/how-to-run-jar-files-on-windows-10/)


## License
The MIT License (MIT)

Prove your coding activity throughout any cv (Gitlab, Bitbucket etc.)  using this Tool. 
