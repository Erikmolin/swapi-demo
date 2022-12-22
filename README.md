# README.MD

Hallå hallå - jag har byggt en liten stateless spring-boot service som hämtar alla skepp från swapi,
sorterar dem fallande efter pris, och returnerar en lista.

Man kan bygga projeket med gradle, eller köra den packade jaren jag skickar med; 
Jag har byggt för java8, p.g.a behöver jobba med uråldiga xml-integrationer om dagarna 

för att köra: 
java -jar ./shipsapi-0.0.1-SNAPSHOT.jar

För att se resultat: 
http://localhost:8080/starships

Om man verkligen inte kan få nog av skepp kan man få ännu fler genom att speca
en limit som queryParameter, dvs
http://localhost:8080/starships?limit=11