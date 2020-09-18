[![flauberjp](https://circleci.com/gh/flauberjp/g-evidences.svg?style=shield)](https://circleci.com/gh/flauberjp/g-evidences/tree/master)
# g-evidences

O [Calendário de contribuições do Github](https://help.github.com/pt/github/setting-up-and-managing-your-github-profile/viewing-contributions-on-your-profile#contributions-calendar) 
reflete suas várias atividades de contribuição no Github, mas só as do Github,
quem o vê talvez conclua que você não anda codando muito, e isso pode ser um problema!

Muitas vezes nossos projetos estão no Bitbucket, no Gitlab ou mesmo são projetos 
Git versionados localmente apenas, e nada das atividades que você realiza nesses projetos é 
refletido no Github.

Esse programa aqui resolve esse problema refletindo no Calendário de contribuições do Github 
todo commit que você executa nos seus projetos Git configurados por ele, além de refletir 
também o seu histórico de commits realizados até então. 

Veja na imagem abaixo um exemplo. Em (A) você tem o que o Github refletiu de uso por você em um 
determinado mês. Em (B) existe a realidade que o Github não reflete, que é o fato de que
você também realiza diversas atividades diariamente só que em um outro repositório central, por exemplo,
no Bitbucket ou Gitlab ou mesmo em um projeto Git local apenas. (A) + (B) é o resultado do uso desse programa
aqui, ele reflete no Calendário de contribuições do Github tudo que você fez até então nos 
seus outros repositórios centrais, além de que registrará futuros usos também.   

![Resultado do uso desse programa](static/exemploGraficoDeUso.png "Resultado do uso desse programa")

No link seguinte temos um video onde é exibido a recomendação do uso desse programa por dois usuários dessa solução, e na sequência é feita uma demonstração rápida da sua instalação e uso:  [g-evidences](https://youtu.be/ohkm4S6PIL0).

## Como usar esse programa

1. Instale o programa. Para isso use o instalador: [g-evidences-inst-win64.exe](https://github.com/flauberjp/my-git-usage-evidences/releases/download/0.0.1/g-evidences-inst-win64.exe).
    * ATENÇÃO: este programa tem como pre-requisito o Windos 10 e o Java 8.

2. Inicie o programa.

3. Configure o programa. 
    * No programa, digite suas credenciais do Github 
    (username e password).    
    * Selecione seus projetos Git que deseja monitorar o uso do git neles.
      * Para isso, selecione uma pasta que contém a maioria dos seus
      projetos git, clicando no botão "_Selecionar_" e escolhendo a pasta.
      Essa pasta será analisada, e apenas projetos git que não são projetos
      Github serão listados. 
        * Os **projetos Github não são listados** pois o uso do Git neles já é registrado automaticamente pelo próprio Github;
    * Marque a coluna _Configurar?_ os projetos que você quer monitorar o uso futuro.
    * Na coluna _E-mail do seu usuário nesse projeto_ escolha o seu e-mail entre a lista de e-mails
    dos contribuidores naquele projeto. Caso não queira refletir também o seu histórico de 
    commits realizados até então escolha a opção "<DESCONSIDERAR HISTÓRICO>", que é a opção selecionada por padrão.
      * O histórico de commits restaurado é em função da branch atual.

4. Aplique a configuração.
    * Clique no botão "_Aplicar configurações_", e aguarde a mensagem
    de confirmação de que tudo foi configurado corretamente. 

## Testando o programa

1. Abra um dos seus projetos git que você acabou de configurar com esta solução.
2. Faça uma alteração em qualquer arquivo, então realize um commit, que disparará nossa solução.
3. Verifique no seu github que o repo _g-evidences-repo_ teve o número de commits incrementado.
4. Repita os passos anteriores para vê-lo aumentar mais uma vez se preciso, o que é uma 
evidência de que você acabou de usar o git localmente refletida no Github.
5. Para confirmar que o histórico de commits foi restaurado também, consulte o seu 
_Calendário de contribuições do Github_. Caso você não tenha notado uma diferença
esperada, faça logout no Github e se logue novamente, isso fará ele atualizar-se.

## Ajuda Voluntária
Amamos voluntários! Venha tornar esse programa ainda mais incrível! 

Por favor leia as instruções em [Seja Voluntário](CONTRIBUTING.md) para verificar como você pode começar a ajudar.

## License
[MIT](https://pt.wikipedia.org/wiki/Licen%C3%A7a_MIT)


