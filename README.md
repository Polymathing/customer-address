# Technical Assignment

## Requirements

Write Http endpoint API to manage customers and addresses

#### Customer contains: 
*	Document id
*	Name
*	Age
*	Registration date
*	Last update info  
*	One or more addresses

#### Address contains: 
* Zip code
* Number 
* It can belong to one or more customers

#### Assumptions

* Document id from customer is unique;
* Supports more than one customer by address;
* One customer can have more than one address;
* Zip code must have a mask 99999-999


#### Expected 

We should be able to insert, update, delete and query customers along with their dependencies
We should be able to query customers by zip code

#### Technical Spec

Write a standalone Java Application exposing either rest or graphql API

• Use ORM implementation as persistency
• It must be written in Java8 or higher

#### Delivery
Preferable to post the code in a public/shared repository such github



### Required:
* JDK 17

### Documentation

- OpenAPI    

    - The OpenAPI Specification can be found in `./docs/openAPI_spec_volvo.yaml`. For visualization, the  file can be opened on https://editor.swagger.io/ 
    
    	Go to `File` then `Import file`
    
- Relational Model

    ![relational-model](https://github.com/Polymathing/customer-address-volvo/blob/main/docs/relational-model.png?raw=true)

### Unit Tests:
Unit tests can be found in `./src/test/java/com/example/volvo` 

### Integration Tests:
All integration tests can be found under `./src/integration-test`

### How to run:

- Application

	- `./mvnw spring-boot:run` 
	
- Tests:
    
    - `./mvnw clean test`

    
- Postman:
    
    - Postman collection file can be found in `./docs/volvo_postman_collection.json`, or via link https://www.postman.com/collections/e1620d3245644a62b98d
