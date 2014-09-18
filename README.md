<img src="https://bugsnag.com/favicon96.png" height="24" width="24"/> bugsnag-eclipse-plugin
======================
Plugin which shows you the bugs from your Bugsnag projects + few useful features

### Update site <img src="https://eclipse.org/artwork/images/v2/logo-800x188.png" height="24" />
http://code.sromku.com/plugins/

> When setting update site -> uncheck: 'Group items by category'

### Options
1. Add,edit bugsnag accounts
2. Show all projects from all accounts
3. Fetch errors of selected project
4. See full stacktrace of the error
5. **Double click** on the error and go the java class to the right line in the editor
6. Customize view columns

### Story
When developing android apps, I found out that fixing bugs isn't the fun part of development time, I only wanted to create more new features instead. But, leaving the bugs on the user side wasn't a good move. Then, I though that seeing the bugs in my IDE and easy accessing the line in code, will help me to resolve them faster and will not free my conscience if I choose to develop new features instead. So, this what pushed me to develop such plugin.

### How to use it
#### Connect accounts
* Go to your Bugsnag account and copy `Auth token`. It looks like this:
	
	<img src="res/auth_token.png"/>
	
* Open the Bugsnag view in eclipse (Window->Show View->Other...)

	<img src="res/open_view.png" height="40"/>

* From drop down menu -> Projects -> Add new...

	<img src="res/add_new_account.png"/>

#### Manage accounts & projects
* Preferences -> Bugsnag -> Press `New...` to add new account with all projects.

	<img src="res/edit_account.png"/>

#### View project errors
* Go to view -> drop down menu -> Projects -> select the project you want.

	<img src="res/select_project.png"/>

* Press `refresh` button and view errors.

	<img src="res/refresh_button.png"/>

	<img src="res/view_errors.png"/>

#### Double click will take you to the java class! :)

#### View error stacktrace
* Select error row and press on `details` button.

	<img src="res/details_button.png"/>

	<img src="res/stacktrace.png"/>

#### Customize columns
* Go to view -> drop down menu -> `Configure columns...`
	
	<img src="res/columns_dialog.png" width="200"/>

### Bugsnag API:
https://bugsnag.com/docs/api

### Contribute
Feel free to pull request and suggest new features.
Opening bugs is also ok :)

### License
Apache License Version 2.0