[![flauberjp](https://circleci.com/gh/flauberjp/my-git-usage-evidences.svg?style=shield)](https://circleci.com/gh/flauberjp/my-git-usage-evidences/tree/master)
# my-git-usage-evidences

O [Calendário de contribuições do Github](https://help.github.com/pt/github/setting-up-and-managing-your-github-profile/viewing-contributions-on-your-profile#contributions-calendar) 
reflete suas várias atividades de contribuição no Github, mas só as do Github,
quem vê-lo parece que você nem esta programando, e isso é um problema!

Muitas vezes nossos projetos estão no Bitbucket, no Gitlab ou mesmo são projetos 
Git versionados localmente apenas, e nada das atividades que você realiza nesses projetos é 
refletido no Github.

Esse programa resolve esse problema refletindo no Calendário de contribuições do Github 
todo commit que você executa nos seus projetos Git configurados por ele.

Veja na imagem abaixo um exemplo. Em (A) você tem o que o Github refletiu de uso por você em um 
determinado mês. Em (B) existe a realidade que o Github não reflete, que é o fato de que
você também realiza diversas atividades diariamente só que em um outro repositório central, por exemplo,
no Bitbucket ou Gitlab ou mesmo em um projeto Git local apenas. (A) + (B) é o resultado desse programa
aqui, ele reflete no Calendário de contribuições do Github o que você fez nos seus outros repositórios 
centrais também.   

![Resultado do uso desse programa](static/exemploGraficoDeUso.png "Resultado do uso desse programa")

## Como usar esse programa

1. [Baixe o instalador](https://github.com/flauberjp/my-git-usage-evidences/releases/tag/1.3-SNAPSHOT)

2. Inicie o programa

3. Configure o programa
    * No programa, digite suas credenciais do Github 
    (username e password)
    * Selecione os projetos que não são projetos Github que você quer
    monitorar o uso do git neles
      * Para isso, selecione uma pasta que contém a maioria dos seus
      projetos git, clicando no botão "_Selecionar_" e escolhendo a pasta.
      Essa pasta será analisada, e apenas projetos git que não são projetos
      Github serão listados. 
      **Essa analise pode demorar um pouco, por favor aguarde**
      * Marque os projetos que você quer monitorar

4. Aplique a configuração
    * Clique no botão "_Aplicar configurações_", e aguarde a mensagem
    de confirmação de que tudo foi configurado corretamente.
    **A aplicação das configurações pode demorar um pouco, por favor aguarde** 

## Testando o programa

Testando a solução
1. Abra um dos seus projetos git que você acabou de configurar esta solução
2. Faça uma alteração em qualquer arquivo, então realize um commit, 
3. que disparará nossa solução
4. Verifique no seu github o repo criado no passo _my-git-usage-evidences-repo_
5. Verifique que o número de commits foi incrementado. 
Repita a operação para vê-lo aumentar mais uma vez se preciso. 
Isso é a evidência de no Github de que você acabou de usar o git localmente.

## Ajuda Voluntária
Amamos voluntários! Venha tornar esse programa ainda mais incrivél! 

Por favor leia as instruções em [Seja Voluntário](CONTRIBUTING.md) para verificar como você pode começar a ajudar.


## License
[MIT](https://pt.wikipedia.org/wiki/Licen%C3%A7a_MIT)


