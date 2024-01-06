# MTCG SWEN-1
- Author if22b161

## Technical steps and design-decisions
### Creation of base classes (User, Battle, Cards) (4h)
First the Bases Classes for the general Battle functionality were created.
During further Development these classes were changed to provide better functionality with the rest of the project.
The User class was changed greatly to implement a more crash safe and consistent functionality via more db calls.
Further variables and functionalities were added to the cards to make further functionalities easier to implement.
### Creation of Server and Router (4h)
The next Step was the creation of a multithreaded that is listening to calls on its sockets.
The biggest success was the creation of the Router class which is able to route specific http calls to different services.
This greatly improved code readability and modularity for easily adding future routes.
### Creating further HTTP Classes (3h)
To make it easier to handle http requests and make according responses, some classes were added. An enum was also copied from the internet to
include all http code standards and make them easier to use and more readable.
### Creating the Database and DBHandlers (DAOs) (22h this is not even a joke)
This step was one of the hardest, because postgres did not seem to be willing to work on my machine. After many hours of troubleschooting and a hardware swap,
a connection could finally be established and work could continue. As for the DB layout. It uses Multiple tables (Users, Cards, Packages) to safely store data.
To prevent sql injections prepared statements are used within the DBHandlers. These were created to provide basic CRUD functionality, but also more specific calls for the Services.
### Creating the Services (12h)
The services are added to the router and their task is to actually handle the calls. Their job is to read the request, do the required tasks (eg. update a user)
and then provide the required response. To provide better readability the Services were split up and use an Interface as base.
### Creating the SessionHandler (5h)
To provide more security and also to handle user-specific tasks, a SessionHandler singleton class was added. It provides a login function and upon
doing it successfully creates an entry within its sessionmap with some user information and a Session-ID (UUID). It also returns it.
These Tokens were also added to the Services to provide more functionality and improve server responses to be more descriptive.
### Adding Records for easier Data handling (3h)
Because the calls seem to send different types of data formations Record Classes have been added to make it easier to retrieve all and only the necessary data for it.
The User class was also greatly altered to provide only functionalities to the Battle class and no longer be used for responses or requests.
### Creating the BattleHandler (6h mostly research)
To make it possible for the Users to actually join a battle lobby a singleton handler was implemented, which uses lock and wait functions to provide thread safe
functionalities.
## Unit Tests
### Card tests (1h)
These unit tests all include all functionalities, from the monster or spell specific interactions up to the correct calculation of battleDamage based on element types.
They were added to make sure that all the requirements provided by the documentation were met. Much like a test-based development circle.
### Battle tests (2h)
These test all the basic Battle functionalities, from taking over enemy cards after winning, up to full testBattles with updated user data.
These were also implemented to test if all the battle requirements where met.
### Router test (2h)
This tests if Services are added correctly and can be called by the wanted http call. Sadly due to time constraints further testing could not be implemented.
### UserService test (3h)
This tests if the login of users works as planned. They were also added to get some practice with Mockito and its included testing functionalities, like mocking database calls.
### HTTP RequestTest and HTTPResponseTest (1h)
Some small tests for checking the correct format and parsing of HTTP requests and responses.
### Started but not implemented tests due to them not working correctly ;-; (5h)

## Git repository
For the first part of the project a local git tree was used.  
[GitHub Freezarite/SWEN2023Proj](https://github.com/Freezarite/SWEN2023Proj)

 