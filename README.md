Name: CityCycle Canada

Developers: Austin, Valentyna, Thomas, Nicolas, and Kimberley

App Front End: Nicolas, Valentyna   \
                                      Thomas
Node.JS Back End: Kemberley, Austin /

Premise: An informative android application focused on assisting cyclists in finding bike paths, requesting new bike paths, and communicating with other cyclists. Users can post information about certain bike paths, and other users can upvote or downvote the posts in accordance to the posts accuracy.
    The app will be developed using a Node.js backend api, which a front end andorid appliction developed using Kotlin will use.


//////////////---APP    SIDE---//////////////
- Main page displays bike paths outright. Do not need to be logged in to use this page.
    - If location use is permitted, the map centers in on the user.
    - Else the map shows all of Regina.

- Vertical side-bar for Pages (in order)
    - Bikelane Map
    
    - Bikelane Condition Reports
        - Credibility Rating (Logged In)
                - Users vote on credible and uncredible posts
                - At a certain negative score, users will be banned for 1 month
                
    - Forum (Logged In)
        - Users can submit topics and forum posts
        
    - Stolen Bike (Logged In)
        - Report Stolen Bikes
        - See Map / List of Stolen Bikes 
        
    - Request New Bikelane (Logged In)
        - See Map / List of Requested Bike Lanes
            - Upvote / Downvote Lane request
        
    - Login
        - Facebook
        - Google

/////////////////////////////////////////////


//////////////---SERVER SIDE---//////////////
- Restify Application


/////////////////////////////////////////////