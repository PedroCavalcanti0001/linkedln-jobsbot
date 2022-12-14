
# Linkedln-jobsbot

Aplicação que busca novas vagas (a cada x minutos) no Linkedln baseadas em um filtro pré-definido e envia via telegram. O mapeamento das novas vagas é feito através de web-scrapping.


### Motivação

A principal motivação além do aperfeiçoamento das tecnicas utilizadas, foi a necessidade de automatizar a busca por novas vagas, visto que não é nada pratico ficar buscando vagas o dia inteiro, então o bot veio como um facilitador, ele implementa os filtros que eu faria manualmente e me entrega as vagas da maneira como eu gostaria de recebê-las.

## Configurações

### Filtros

É possivel usar a busca booleana do linkedln através do arquivo filtro.yml que é gerado na raiz do projeto, após inicializar a aplicação.

`filtros.yml`
```
keywords:
  - "('JAVA' OR 'java' OR 'Java')"
  - "NOT ('senior' OR 'sr' OR 'Senior' OR 'sênior')"
  - "NOT ('FULLSTACK' OR 'fullstack' OR 'FULL STACK')"
  - "NOT 'Java script'"
```

### Propriedades

É possivel alterar as propriedades do bot e customizar de acordo com as suas necessidades atraves do arquivo propriedades.xml, que também é gerado ao iniciar o bot.

`propriedades.yml`
```
#Corresponde ao tempo em minutos ao qual o bot irá rodar.
taskInterval: 10
#Corresponde ao intervalo em minutos ao qual o bot irá filtrar a busca.
jobsInterval: 180
#Ordem de exibição
#Pode ser por TIME ou RELEVANCE
order: TIME
#Token do bot do discord
botToken: "BOT-TOKEN-AQUI"
#Canal onde o bot irá mandar as mensagens
botChannel: -603392344
#Local onde as vagas serão buscadas
location: "brasil"
#Intervalo para passar como filtro na requisição para o linkedln
# Pode ser DAY, WEEK ou MONTH
moment: DAY
#Define o geoId que é passado como parametro na URL da requisição para o linkedln
geoId: 106057199
#Indica se o bot irá mostrar vagas promovidas ou não
allowPromoted: false
tempo em minutos para remover vagas do cache
removeJobFromDbTime: 120
#Indica se a mensagem 'jobsNotFound' deve ser enviada ou não, quando nenhum job é encontrado.
sendJobsNotFoundMessage: true
#Indica se o bot deve incluir finais de semana
excludeWeekends: true
#Indica se a configuração de intervalo de tempo será usada ou ignorada
allowTimeInterval: true
#Indica o intervalo de tempo no qual o bot irá funcionar
timeInterval: "08:00-18:00"
```

### Mensagens

Através do arquivo mensagens_bot.yml é possivel customizar as mensagens exibilidas no telegram. Assim como os outros arquivos, esse também é gerado após iniciar o bot pela primeira vez.

`mensagens_bot.yml`
```
start:
  - "🔎 Vagas encontradas ({quantidadeVagas}):"
  - ""
end:
  - ""
  - ""
  - "📤 Uma nova busca será realizada as {tempoNovaBusca}"

jobText:
  - ""
  - "💻 Vaga: {vaga}"
  - "🏦 Empresa: {empresa}"
  - "⏱ Postada {strTime}"
  - "📌 Localização: {loc}"
  - "👉 Link: {link}"
  - ""
jobsNotFound:
  - "⚠ Nenhum job encontrado para o filtro atual."
  - ""
  - "📤 Uma nova busca será realizada as {tempoNovaBusca}"
```

## Requisitos

- Java 8
# Instalação

## Obter repositorio
Use o git para clonar o repositorio

```bash
git clone https://github.com/PedroCavalcanti0001/linkedln-jobsbot.git
```
## Gerar JAR

Use o seguinte comando para gerar o arquivo que usaremos para iniciar a aplicação. Após executar o comando, o jar `linkedln-jobsbot-0.0.1-SNAPSHOT.jar` será criado.

```bash
mvn package
```



## Iniciar BOT

O JAR criado anteriormente está na pasta /target dentro do diretório raiz do projeto. Acesse a pasta 
e execute o seguinte comando.

* Após a primeira inicialização é necessário realizar as configurações básicas, como token do bot e chat.

```bash
java -jar linkedln-jobsbot-0.0.1-SNAPSHOT.jar
```

## Autores

- [@PedroCavalcanti0001](https://github.com/PedroCavalcanti0001)
