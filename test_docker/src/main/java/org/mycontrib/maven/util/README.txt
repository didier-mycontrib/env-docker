mvn dependency:purge-local-repository permet de balayer (de manière transitive) les artifacts du projet courant au sein du référentiel local (.m2/repository) pour
a) supprimer les anciens fichiers (dont certains ont quelquefois le problème "invalid LOC header (bad signature)"
b) forcer un nouveau téléchargement (reResolve)


=====
avec un peu de temps , il faudrait ecrire un programme utilitaire 
qui balaie certaines branches de .m2/repository 
qui détecte "invalid LOC header (bad signature)" via java.util.zip.ZipFile ou autre 
et qui ne supprime que les fichiers à problème.
