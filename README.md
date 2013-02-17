pocAndroidMAR
=============

Sample project tha apply MVP Pattern, AspectJ and Roboguice Frameworks in the Android platform.

## Configuring the unit tests


1. Create a new  "User Library" with the jars within testlibs folder and put that library in the classpath of the project.
2. When you run the tests a error will occur. To fix it do the following: 
                -Abrir "Run Configurations" e selecionar o correspondente ao teste unitario que deu erro.
                -Abrir a aba "Classpath".
                -Remover a lib Android X.X  do "Bootstrap Entries" .
                -Selecionar "User Entries" e "Add External JARs..."   e selecionar  o android.jar correspondente ao da versão da plataforma que está no SDK do android.
                - Executar o teste novamente e ser feliz!
