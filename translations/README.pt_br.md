[![flauberjp](https://circleci.com/gh/flauberjp/my-git-usage-evidences.svg?style=shield)](https://circleci.com/gh/flauberjp/my-git-usage-evidences/tree/master) <a href="translations/README.md"><img align="right" src="https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/240/google/241/flag-united-states_1f1fa-1f1f8.png" width="22"></a> <a href="translations/README.pt_br.md"><img align="right" src="https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/240/google/241/flag-brazil_1f1e7-1f1f7.png" width="22"></a>
 

# my-git-usage-evidences



## github user for testing

Este projeto possui uma [conta](https://github.com/mygitusageevicencesapp) no github para auxiliar nos testes, para mais informções sobre como utiliza-la, pode contatar @flauberjp

## Ajuda Voluntária
Amamos voluntários! Por favor leia as instruções em [Seja Voluntário](CONTRIBUTING.md) para verificar como você pode começar a ajudar.

## Usando a solução, usando alguns passos manuais

1. Gerar o fat-jar
    * Use seu IDE escolhendo o Maven Lifecycle package ou use a linha de comando, executando no raíz do projeto o comando: mvn package
    * O arquivo será gerado em ./target/my-git-usage-evidences-1.1-SNAPSHOT-jar-with-dependencies.jar

2. Criei diretório para armazenar os arquivo da solução
    * Crie o diretório my-git-usage-evidences em C:\Program Files
    * Copie o arquivo my-git-usage-evidences-1.1-SNAPSHOT-jar-with-dependencies.jar para C:\Program Files\my-git-usage-evidences

3. Crie projeto no seu repositório remoto
    * Abra o prompt de comando  como administrador na pasta C:\Program Files\my-git-usage-evidences, criada no passo anterior
    * A partir desse caminho, execute o seguinte comando: java -cp my-git-usage-evidences-1.1-SNAPSHOT-jar-with-dependencies.jar io.github.flauberjp.forms.FormForTesting
    * No formulário que surge, digite suas credenciais do github(username e password), e clique no botão Validar Credenciais
    * As credenciais estando validadas, clique no botão Criar projeto no repo remoto
    * Verifique seu repositório remoto e confirme que um repositório chamado my-git-usage-evidences-repo foi criado (esse nome é customizável, é só alterar o campo Repositório no formulário, a título de exemplo vamos assumir que você escolheu o valor padrão my-git-usage-evidences-repo)

4. Gerar o arquivo propriedades.txt
    * Abra o prompt de comando como administrador na pasta C:\Program Files\my-git-usage-evidences
    * A partir desse caminho, execute o seguinte comando: java -cp my-git-usage-evidences-1.1-SNAPSHOT-jar-with-dependencies.jar io.github.flauberjp.forms.FormForTesting
    * No formulário que surge, digite suas credenciais do github(username e password), e clique no botão Validar Credenciais. 
    * As credenciais estando validadas, clique no botão Salvar Dados em propriedades.txt(Aqui o Repositório deve ainda conter o mesmo nome do repo que você usou no passo anterior, my-git-usage-evidences-repo)
    * Confirme que o arquivo propriedades.txt foi gerado em C:\Program Files\my-git-usage-evidences

5. Gerar o hook
    * Abra o prompt de comando como administrador, vá para a pasta C:\Program Files\my-git-usage-evidences
    * A partir desse caminho, execute o seguinte comando: java -cp my-git-usage-evidences-1.1-SNAPSHOT-jar-with-dependencies.jar io.github.flauberjp.forms.FormForTesting
    * No formulário que surge, clique no botão Gerar hook(arquivo pre-push)
    * Confirme que o arquivo pre-push foi gerado em C:\Program Files\my-git-usage-evidences

6. Usar a solução
    * Escolha um dos seus projeto git (um projeto git é um que contém uma pasta oculta .git no seu raíz)
    * Copie o arquivo C:\Program Files\my-git-usage-evidences\pre-push para a pasta .git\hooks do seu projeto git

7. Testando a solução
    * Abra seu projeto git onde você configurou esta solução
    * Faça uma alteração em qualquer arquivo, commit essa alteração e execute um push
    * Verifique no seu github o repo criado no passo "Crie projeto no seu repositório remoto"
    * Assumindo que o nome do repositório é o nome sugerido por padrão,  my-git-usage-evidences-repo, você pode verificar que o número de commits foi incrementado (repita a operação para ve-lo aumentar mais uma vez se preciso).

## Referências
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

