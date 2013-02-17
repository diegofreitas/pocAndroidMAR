pocAndroidMAR
=============

Sample project tha apply MVP Pattern, AspectJ and Roboguice Frameworks in the Android platform.


## Configuring the unit tests


1. Criar uma "User Library" com os jar contidos na pasta testlibs está dentro do projeto e adioná-la ao classpath do projeto.
2. Ao executar os testes pela primeira vez ocorrerá um erro. 
                -Abrir "Run Configurations" e selecionar o correspondente ao teste unitario que deu erro.
                -Abrir a aba "Classpath".
                -Remover a lib Android X.X  do "Bootstrap Entries" .
                -Selecionar "User Entries" e "Add External JARs..."   e selecionar  o android.jar correspondente ao da versão da plataforma que está no SDK do android.
                - Executar o teste novamente e ser feliz!
