# Kitesurfing project

A tiny backend project made with Spring Boot Framework.

## Dependencies

* [IntelliJ](https://www.jetbrains.com/idea/)
* [Google Chrome](https://www.google.com/chrome/)
* [mongoDB](https://www.mongodb.com/)

# Overview

For database, install the mongoDB and run in the command line ```mongod``` to start the mongoDB server. To import the json file, make sure you are in the ```kitesurfing/src/main/resources``` and use the following command ```mongoimport --db kitesurfing --collection locations --file csvjson.json```.

Starting the aplication, using IntelliJ, Eclipse or Netbeans, just press the run button then, in Chrome, type in the URL bar: ```localhost:8090/api```.

## Main Parts
The main Model is ```Location``` which contains all the information for spots. It is located in the ```domain``` folder. The security is made with username, password and token. The ```User``` class contains the user details.

For communicating with database, I specified the database name in the ```application.properties``` and for all Models, I made a Repository, found in the ```repository``` folder. All those repositories extends
```Java
MongoRepository<Model, idType>
```
used for ```findBy``` methods.

For security, I encrypted the password with ```BCryptPasswordEncoder``` and all the configurations are found in the ```config``` folder. Some endpoints presents ```ModelAndView```, with the view found in the ```resources/templates```

### Endpoints

```Java
/api/login
/api/signup
/api/users/me
/api/spots
/api/spots/{spotId}
/api/spots/countries
/api/spots/favorites
/api/spots/favorites/{spotId}
```
## TO DO

- [ ] /api/forgot-me
- [ ] handle the favorites endpoints for radio buttons
- [ ] more security things

## License

[MIT](https://choosealicense.com/licenses/mit/)