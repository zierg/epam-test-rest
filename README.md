## Tasks:

1. Build a web service that offers an endpoint which returns all cities from either the JSON or CSV.
2. Enhance the service to also include a density field, which should be number of people per sq km.
3. Add the capability to sort by name / population / area in descending or ascending manner.
4. Additionally, add a feature to filter the cities by name using a “contains” filter.
5. Create an endpoint to add new cities with name, population and area during runtime.
6. Create an UI which loads the full dataset from the service and shows it in a table with all 4 columns.
7. Improve the UI by highlighting rows of cities having a population over 1 million.

## System requirements:
* Java 17 (tested on Java 24)
* Maven (tested on 3.9.9)

## How to launch: execute the following command from the project directory
```
mvnw clean spring-boot:run
```

## Testing
Detailed information about REST endpoints can be found at http://localhost:8080/swagger-ui/index.html

### Getting cities

The testing URL: `http://localhost:8080/cities`

* To get all cities, simply execute GET request on testing URL
* To add density to cities, specify `add-density` GET request parameter with value = `true`:
```
http://localhost:8080/cities?add-density=true
```
* To use a sorting field, specify `sort-by` GET request parameter with one of these values:
```
name, area, population
```
* Sorting is ascending by default. To make it descending, specify `sort-descending` GET request parameter with value = `true`.
* To get only cities whose names contain a certain string, specify `name-contains` GET request parameter. It's not case sensitive
* It's possible to use more that one parameter in one request. For example:
```
http://localhost:8080/cities?add-density=true&sort-by=population&name-contains=new
```

### Adding a city

The testing URL: `http://localhost:8080/cities/{cityName}`

To add a city, execute PUT request on the testing URL (specify the cityName) 
with the following body structure:
```
{
    "area": 100,  // double value
    "population": 1000000  // integer value
}
```

## Application properties
* `cities.source-file` - a path to the file from the task. Must be a valid path to a valid JSON file. The path is relative to the `resources` folder
* `cities.persist-changes` - can be set to `true` or `false`. If `true`, 
then added cities would be stored in a file specified in `cities.additional-source-file` property. If `false`, then
added cities would be stored in application memory, so they will be lost after application is stopped.
* `cities.additional-source-file` - path to a file to store added cities. **Must** be specified if `cities.persist-changes` is specified.

The property values are stored in `resources/application.properties`. Also they can be changed in the command line like this:
```
mvnw clean spring-boot:run -Dspring-boot.run.arguments="--cities.persist-changes=false"
```

