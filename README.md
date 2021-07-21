# Super*Duper*Drive Cloud Storage
The web application includes three user-facing features:

1. **Simple File Storage:** Upload/download/remove files
2. **Note Management:** Add/update/remove text notes
3. **Password Management:** Save/edit/delete website credentials

## Requirements and Roadmap
There are three layers of the application to be implemented:

1. The back-end with Spring Boot, Spring Security, and MyBatis
2. The front-end with Thymeleaf
3. Application tests with Selenium

### The Back-End
The back-end is about security and connecting the front-end to database data and actions. 

1. Managing user access with Spring Security
 - restrict unauthorized users from accessing pages other than the login and signup pages

2. Handling front-end calls with controllers
 - write controllers that bind application data and functionality to the front-end, using Spring MVC's application model to identify the templates served for different requests and populating the view model with data needed by the template 

3. Making calls to the database with MyBatis mappers
 - implement MyBatis mapper interfaces for each of the model types per database table to support the basic CRUD (Create, Read, Update, Delete) operations 

### The Front-End

1. Login page
 - Everyone should be allowed access to this page, and users can use this page to login to the application. 
 - Show login errors, like invalid username/password, on this page. 

2. Sign Up page
 - Everyone should be allowed access to this page, and potential users can use this page to sign up for a new account. 
 - Validate that the username supplied does not already exist in the application, and show such signup errors on the page when they arise.
 - store the user's password securely

3. Home page

 i. Files
  - The user should be able to upload files and see any files they previously uploaded. 
  - The user should be able to download or delete previously-uploaded files.
  - Any errors related to file actions should be displayed. For example, a user should not be able to upload two files with the same name

 ii. Notes
  - The user should be able to create notes and see a list of the notes they have previously created.
  - The user should be able to edit or delete previously-created notes.

 iii. Credentials
 - The user should be able to store credentials for specific websites and see a list of the credentials they've previously stored. Encrypted passwords are displayed in this list.
 - The user should be able to view/edit or delete individual credentials. When the user views the credential, unencrypted password is seen.

The home page should have a logout button that allows the user to logout of the application and keep their data private.

### Testing

1. Write tests for user signup, login, and unauthorized access restrictions.
![AccessTests](/demoGif/AccessTests.gif)


2. Write tests for note creation, viewing, editing, and deletion.


3. Write tests for credential creation, viewing, editing, and deletion.


