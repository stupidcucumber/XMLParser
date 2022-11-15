### Structure of the project

``` bash.
├── LICENSE
├── README.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── query.html
├── queryXML.xml
└── src
    └── main
        ├── java
        │   ├── Items
        │   │   ├── Item.java
        │   │   ├── Lecturer.java
        │   │   ├── Practicant.java
        │   │   ├── Scientist.java
        │   │   └── StudyClass.java
        │   ├── Parser
        │   │   ├── DOM
        │   │   │   └── CustomDOMParser.java
        │   │   ├── Parser.java
        │   │   ├── SAX
        │   │   │   ├── CustomSAXParser.java
        │   │   │   └── SAXHandler.java
        │   │   ├── Setting.java
        │   │   └── ToHTMLParser
        │   │       ├── ConverterToHTML.java
        │   │       ├── CustomToHTMLConverter.java
        │   │       ├── ToHTMLConverter.java
        │   │       └── ToXMLParser.java
        │   ├── Windows
        │   │   ├── CloseRequestWindow.java
        │   │   ├── InfoWindow.java
        │   │   ├── PreferencesWindow.java
        │   │   ├── Test.java
        │   │   └── Window.java
        │   ├── com
        │   │   └── example
        │   │       └── xmlparser
        │   │           ├── HelloController.java
        │   │           └── XMLParser.java
        │   └── module-info.java
        └── resources
            ├── com
            │   └── example
            │       └── xmlparser
            │           └── hello-view.fxml
            ├── faculty.xml
            ├── facultyClasses.xsl
            ├── facultyScientists.xsl
            ├── logotype.png
            ├── settings.txt
            └── styles.css
            
```
            
### Project Database
  Database contains information about classes, lecturers and practicants.
