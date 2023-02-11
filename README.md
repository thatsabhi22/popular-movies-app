# Popular Movies Stage 2 App

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
![1](https://user-images.githubusercontent.com/24782276/218276641-7318598a-7383-461d-b435-b8de7f3658bc.png)
![2](https://user-images.githubusercontent.com/24782276/218276643-59c7c441-0de2-4198-8ecd-4a9454c1ae8e.png)
![3](https://user-images.githubusercontent.com/24782276/218276644-065faf09-77c8-45db-b86b-b83d1e082eba.png)
![4](https://user-images.githubusercontent.com/24782276/218276646-3d78afb0-30a8-48f8-9f1b-cd1acb7c009f.png)
![5](https://user-images.githubusercontent.com/24782276/218276648-1bde3d60-88e5-44dd-8141-f3b1bbda4d35.png)
![6](https://user-images.githubusercontent.com/24782276/218276649-feae2327-3133-41d9-925d-5f1795596a07.png)
![7](https://user-images.githubusercontent.com/24782276/218276651-eb1cf1d3-3d47-4921-abcc-4529dbc48f5b.png)
![8](https://user-images.githubusercontent.com/24782276/218276655-8103e121-9b0f-42f7-bbd2-4b64cffe5947.png)
![9](https://user-images.githubusercontent.com/24782276/218276657-60ff9047-ac54-4933-850a-03321d1b6658.png)

