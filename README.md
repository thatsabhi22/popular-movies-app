# Popular Movies Stage-2 App
Android Developer Nanodegree Popular Movies Stage 2 App

This is the Stage 2 of the Popular Movies App.  
This app helps users discover popular and recent movies.  
It requests the data from Internet on MoviesDB API and this ensures data is latest and updated.  



## Used libraries and Concepts

- Android Architecture Components 

  - ViewModel 
  - LiveData
  - Repository 
  - Room Database
  
- Retrofit ( A type-safe HTTP client )
- Picasso ( A powerful image downloading and caching library )
- Stetho ( A debugging library for Android applications )
- Scrolling Activity
- Material Design
- JSON Parsing
- RecyclerView with ViewHolder


## How it works
- As the app launches, Home screen with movie posters arranged in a grid appears to the user.
- Select the category (popular, top rated and favorites) of movie list from Home screen settings menu on top-right.
- As user taps a movie, the movie detail screen opens with detailed information such as: original title, plot synopsis, user rating, release date
- You can mark a movie your favorite from the detail screen, it will be added to your favorite list
- This app works in offline mode as well. All your favorite movies can be seen in offline mode.
- Select the 'favorite' category from the menu option on top right at Home Screen in online as well as offline mode.
- On the detail screen you can watch various movie trailers as well as the movie reviews.



## How to use
- This app fetches movies data from www.themoviedb.org which needs API Key.  
- You need to generate your own API Key to use this app.  
- To obtain an API Key, Go to www.themoviedb.org and sign up, you get your API Key generated.  
- Replace the API Key in the strings.xml under values folder, replace the string value with name "api_key".  

## Screens
![Alt text](/Screenshots/1.png?raw=true)
![Alt text](/Screenshots/2.png?raw=true)
![Alt text](/Screenshots/3.png?raw=true)
![Alt text](/Screenshots/4.png?raw=true)
![Alt text](/Screenshots/5.png?raw=true)
![Alt text](/Screenshots/6.png?raw=true)
![Alt text](/Screenshots/7.png?raw=true)
![Alt text](/Screenshots/8.png?raw=true)
![Alt text](/Screenshots/9.png?raw=true)

