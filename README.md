<h1 align="center">Movie Rate</h1>


<!--ts-->
   * [About](#about)
   * [How to use](#how-to-use)
   * [Endpoint's Flow](#endpoints-flow)
   * [All application endpoints](#all-application-endpoints)
   * [Credits](#credits)
<!--te-->

<h1>About</h1>
<p align="center">This repository receives data from an external API to list some films which the user can rate and this will generate a general average of the values ​​of the films. The system has the implementation of JWT and Roles authentication to validate who can access certain endpoints..</p>
  </br>
<h1>How to use</h1>




<h2>Prerequisites</h2>
<p>First, you must have the MySql database installed on your computer, which can be downloaded through this <a href="https://www.mysql.com/downloads/">link</a>.</br>
<p>Then, <a href="https://insomnia.rest/download">download</a> Insomnia to make http requests.</br>
<p>After that, create a local database with name manageremployee and give it the root name and password of your choice, just make sure you change the spring.datasource.password variable in the application.properties to the password you set.</br></br>
  <img src="https://user-images.githubusercontent.com/63808405/210464300-8497e99d-b41d-4d57-8e41-87ba76e3c8fa.png"/>
  <p>Run the app.</br>
  
<h1>Endpoint's Flow</h1>
<p>After doing all that, let's go to the endpoints.</br>
<h3>BASE URL</h3>


```bash
http://localhost:8085/api/v1/
``` 


To create a new admin, use:

```bash
@POST
admin/save
``` 
with the request body

```bash
{
	"fullname":"admin",
	"username":"admin",
	"email":"email@gmail.com",
	"password":"admin"
}
``` 
Done that, wait for this answer.

  <img src="https://user-images.githubusercontent.com/63808405/215793365-194d84fb-cd30-47b3-832d-a8331c083fe0.png"/></br></br>
  
  
Then, we need do login our admin to get the token and pass to other endpoints to use them.
  
  
  
  After that, we can create download our movies from external API using:
  
  
  ```bash
@POST
api/login
``` 
with the request body

```bash
{
	"username":"admin",
	"password":"admin"
}
``` 
  This endpoint is usefull to login our simple user too. After you do this, take the user token, pass in the authentication on insomnia using Bearer, then you can download all movies from external API.
  
```bash
@GET
admin/getMovies
``` 
And all movies will be downloaded.

so, let's create our simple user to rate the movies. Use this endpoints:

```bash
@POST
user/save
``` 

with the request body

```bash
{
	"fullname":"simple-user",
	"username":"simple",
	"email":"email@gmail.com",
	"password":"123"
}
``` 

Wait for this answer.

  <img src="https://user-images.githubusercontent.com/63808405/215793371-2dde0f98-a44d-4901-9945-bc78b0a36345.png"/></br></br>
  
Doing that, you can start to rate the movies. First, login the simple user using the previus endpoint to login. Then, follow the next endpoint to rate 
a movie:

```bash
@POST
movie/rateMovie/{movie-id}
``` 

passing in the request body the note from rate

```bash
{
	"rate":6
}
``` 

When a lot of people rate the same movie, all the scores will be added up and averaged. You can check the averaged following this next endpoint:


```bash
@POST
movie/average/{movie-id}
``` 
 
 remeber to pass the simple user token on header of requisition.
 
 You can also update a rate of movie using this endpoint:
 
 
  
```bash
@PATCH
movie/update/{movie-id}/rate
``` 
 
  ```bash
{
	"rate":85
}
``` 

<h1>Other application endpoints</h1></br>


<h3>DELETE</h3></br>

```bash
Request | Response
:-------: | :------:
user/delete | with token on header of requisition, the user will be delete by token, deleting the current user.

movie/delete/{movie-id} | delete movie
```

<h1>All application endpoints</h1></br>

<h3>POST</h3></br>

```bash
Request | Response
:-------: | ------:
user/save   | create a new simple user

admin/save   | create new admin

movie/rateMovie/{movie-id}   | rate movie

api/login   | login user

```

<h3>PATCH</h3></br>

```bash
Request | Response
:-------: | ------:
movie/update/{movie-id}/rate   | update score of movie

```
<h3>GET</h3></br>

```bash
Request | Response
:-------: | ------:
movie/all | get all movies

movie/average/{movie-id} | get movie averaged

admin/getMovies | download movies from external API

```


<h3>DELETE</h3></br>

```bash
Request | Response
:-------: | ------:
user/delete   | delete current user, need the token on header

movie/delete/{movie-id}  | Delete movie

```


<h1>Credits</h1>

---

<a href="https://www.linkedin.com/in/valter-gabriel">
  <img style="border-radius: 50%;" src="https://user-images.githubusercontent.com/63808405/171045850-84caf881-ee10-4782-9016-ea1682c4731d.jpeg" width="100px;" alt=""/>
  <br />
  <sub><b>Valter Gabriel</b></sub></a> <a href="https://www.linkedin.com/in/valter-gabriel" title="Linkedin">🚀</ a>
 
Made by Valter Gabriel 👋🏽 Get in touch!

[![Linkedin Badge](https://img.shields.io/badge/-Gabriel-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/valter-gabriel/ )](https://www.linkedin.com/in/valter-gabriel/)

