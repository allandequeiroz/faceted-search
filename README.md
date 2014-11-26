Faceted-Search é um pequeno motor de buscas escrito em java para auxiliar na indexação de conteúdo relacional e criação de ["faceted searches".](http://en.wikipedia.org/wiki/Faceted_search)

# Preparação  
A utilização é bastante simples, a primeira coisa a se fazer é mapear as entidades persistentes, exemplos básicos podem ser vistos no ["facated-search-model"](https://github.com/allandequeiroz/faceted-search/tree/master/faceted-search-model/src/main/java/co/mutt/fun/model).  
A segunda coisa é configurar a conexão com o banco de dados, isso pode ser feito através do persistence.xml ou aproveitar os arquivos hibernate.cfg.xml pré configurados.  

# Empacotamento
Para utilizar dentro de uma aplicação, basta apenas empacotar com o maven "mvn clean package" e incluir o jar gerado no classpath ou para utilizar como uma aplicação standalone, empacote utilizando o profile stantalone "mvn clean package -Pstandalone", nesse caso, ao executar o jar "java -jar faceted-search-jar-with-dependencies.jar" a indexação será iniciada.

# Utilização.
A melhor maneira de entender como um código funciona é lendo o código, nesse caso o melhor é iniciar com a leitura dos modelos de exemplo, seguida da leitura e execução dos testes unitários.
