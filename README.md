# RepoExplorerMVVM

This folder contains the source code for the simple open source GitHub client for android built using MVVM design pattern, reactive programming with LiveData, repository pattern, Room Persistence Library.
Features: Pagination, caching search results and option to add bookmarks.
Code is packaged by feature. "Data" package contains local database model classes( "database" package ), web service ("webservice" package) wich models the structure of GitHub api response and repository ("DataRepository.java") which is used as a single source of truth.
"UI" package contains VIEWS and VIEW MODELS code for each screen in the app ("mainscreen", "savedscreen", "detailsscreen" packages). VIEW MODEL don't hold any references to the VIEW  or the MODEL classes in data package so the code is modular and it is easy to change the screens and add features.
LiveData is used to communicate between app layers. Any changes in the MODEL layer are propagated via LiveData to VIEW MODEL and then to the VIEW layer.  
In Utils class there is Configuretion interface in wich you can configure default search term, results per page and for how long should results be kept in local cache, before trying to update with new ones.

Check it on Google Play Store: https://play.google.com/store/apps/details?id=com.opensource.giantturtle.clientapp

### Getting Started

Check out branch `master` to start.

<a href="https://imgflip.com/gif/2e1ezw"><img src="https://i.imgflip.com/2e1ezw.gif" title="made at imgflip.com"/></a>

<a href="https://imgflip.com/gif/2e1f1g"><img src="https://i.imgflip.com/2e1f1g.gif" title="made at imgflip.com"/></a>

<a href="https://imgflip.com/gif/2e1eye"><img src="https://i.imgflip.com/2e1eye.gif" title="made at imgflip.com"/></a>

<a href="https://imgflip.com/gif/2e1f2m"><img src="https://i.imgflip.com/2e1f2m.gif" title="made at imgflip.com"/></a>




