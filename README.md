# Movie Collection

[![Apache-2.0 License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

Project details and also it's a note for me to see my old concerns. [![ProjectNotes][notes-shield]][notes-url]

This is a demo project for practicing the usage of Spring Data JPA and Thymeleaf. It has the most common features that are used for some CRUD operations.

* Add movie, actor/actress, genre, language
* Delete movie, actor/actress, genre
* Update existing records
* Register/Login and authorization
* Search by movie title, genre, actor/actress name
* Sort the movie records by movie year
* View movie details like actors/actresses' name and their roles, genres, etc.
* User authorization with AdminPanel


## Build and Run on your localhost

1. Use maven to build JAR
  ```sh
    mvn clean install -DskipTests
  ``` 
2. Build image and run with Docker
  ```sh
    docker-compose up --build
  ```
  
 ### OR

1. Download and unzip the source repo, or clone it using Git:
  ```sh
    git clone https://github.com/bariskbayram/spring-movie-collection.git
   ```
2. Create a new PostgreSQL Database,
   ```sh
   database name: movie_collection
   database username: postgres
   database password: postgres
   ```  
3. Run 
  ```sh 
    mvn spring-boot:run
  ```

### Build with and Third-party

* Spring ( SpringDataJPA, SpringBootSecurity )
* Thymeleaf
* Bootstrap
* PostgreSQL
* Guava
* Lombok

[linkedin-shield]: https://img.shields.io/static/v1?label=LINKEDIN&message=BKB&color=<COLOR>
[linkedin-url]: https://www.linkedin.com/in/bariskbayram/
[license-shield]: https://img.shields.io/static/v1?label=LICENCE&message=Apache-2.0&color=<COLOR>
[license-url]: https://github.com/bariskbayram/spring-movie-collection/blob/master/LICENSE
[notes-shield]: https://img.shields.io/static/v1?label=INFO&message=NOTES&color=<COLOR>
[notes-url]: https://github.com/bariskbayram/spring-movie-collection/blob/master/project-notes.adoc
