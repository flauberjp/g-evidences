[![flauberjp](https://circleci.com/gh/flauberjp/my-git-usage-evidences.svg?style=shield)](https://circleci.com/gh/flauberjp/my-git-usage-evidences/tree/master) <a href="README.pt_br.md"><img align="right" src="https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/240/google/241/flag-brazil_1f1e7-1f1f7.png" width="22"></a> <a href="../README.md"><img align="right" src="https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/240/google/241/flag-united-states_1f1fa-1f1f8.png" width="22"></a>
 

# my-git-usage-evidences



## github user for testing

Este projeto possui uma [conta](https://github.com/mygitusageevicencesapp) no github para auxiliar nos testes, para mais informções sobre como utiliza-la, pode contatar @flauberjp

## Ajuda Voluntária
Amamos voluntários! Por favor leia as instruções em [Seja Voluntário](CONTRIBUTING.pt_br.md) para verificar como você pode começar a ajudar.

## Como usar

1. [Faça o download do _fat jar_ da nossa 1a release](https://github.com/flauberjp/my-git-usage-evidences/releases/tag/1.0-SNAPSHOT)

2. Crie um diretório para armazenar os arquivos da solução
    * Crie o diretório _C:\Program Files\my-git-usage-evidences_
    * Copie o _fat jar_ para esse diretório

3. Crie um repositório no seu Github
    * Como administrador abra o prompt de comando 
    na pasta _C:\Program Files\my-git-usage-evidences_
    * A partir desse caminho, execute o seguinte 
    comando: 
      > _java -cp my-git-usage-evidences-jar-with-dependencies io.github.flauberjp.forms.FormForTesting_
    * A programa desta solução é executado, então digite suas credenciais 
    do github (username e password), e clique no botão "_Validar Credenciais_"
    * As credenciais estando validadas, clique no botão "_Criar projeto no repo remoto_"
    * Acesse seu Github e verifique que ele contém um repositório 
    chamado _my-git-usage-evidences-repo_. 
      * Aqui estamos assumindo que o valor do campo "_Repositório_" não foi alterado 

4. Gerar o arquivo _propriedades.txt_
    * De volta ao programa da solução, clique no botão "_Salvar Dados em propriedades.txt_"
    * Confirme que o arquivo _propriedades.txt_ foi gerado 
    em _C:\Program Files\my-git-usage-evidences_

5. Gerar o hook
    * De volta ao programa da solução, clique no botão "_Gerar hook(arquivo pre-commit)_"
    * Confirme que o arquivo _pre-commit_ foi gerado 
    em _C:\Program Files\my-git-usage-evidences_

6. Aplique o hook em um projeto git
    * Escolha um dos seus projetos git
      * Um projeto git é uma pasta que contém uma sub-pasta oculta chamada _.git_
    * Copie o hook para a pasta _.git\hooks_ do seu projeto git

7. Testando a solução
    * Abra seu projeto git onde você acabou de configurar esta solução
    * Faça uma alteração em qualquer arquivo, então realize um commit 
    e em seguida execute um push, o que disparará nossa solução
    * Verifique no seu github o repo criado no passo 3. _my-git-usage-evidences-repo_
    * Verifique que o número de commits foi incrementado. 
    Repita a operação para ve-lo aumentar mais uma vez se preciso.
    E com isso seu [Calendário de contribuições do Github](https://help.github.com/pt/github/setting-up-and-managing-your-github-profile/viewing-contributions-on-your-profile#contributions-calendar) 
    foi marcado, evidenciando no Github de que você acabou de usar o git localmente

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

