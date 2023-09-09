---
# Android Login, Signup, and Profile Page with Volley Library and PHP Backend

This is a simple Android application built using Android Studio and Java that demonstrates the implementation of a login, signup, and profile page. The application uses the Volley library for making HTTP requests to a PHP backend server to manage user authentication and store user data in a MySQL database.

## Features

- **User Registration:** Users can create a new account by providing their username, email, and password.

- **User Login:** Registered users can log in using their username and password.

- **Profile Page:** After logging in, users can view and edit their profile information, including their username and email.

- **Backend Database:** User registration and login data is stored in a MySQL database using PHP scripts for backend communication.

## Prerequisites

Before you begin, ensure you have the following:

- Android Studio installed on your development machine.
- A running web server with PHP support and a MySQL database.

## Setup

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/JoKerP00L/Java_login-signup-profile/.git
   ```

2. Open the project in Android Studio.

3. Configure the Backend:
   - You can download the backend files form aboue link:
   ```bash
   https://github.com/JoKerP00L/php-api/api.git
   ```
   - Upload the PHP scripts located in the `backend` folder to your web server.
   - Modify the PHP scripts to connect to your MySQL database and update the database credentials in `config.php`.

4. Update the URLs in the Android code to point to your server by modifying the `URL.java` file in the project.

## Usage

1. Run the Android application on an emulator or physical device.

2. Register a new account by providing a username, email, and password.

3. Log in using your registered username and password.

4. Access the profile page, where you can view and edit your user information.

## Libraries Used

- [Volley](https://developer.android.com/training/volley): Used for making network requests in Android.

## Screenshots

## Signup Screen
![signup_screen](https://github.com/JoKerP00L/react-native-auth_php/assets/95900764/565db636-da7d-4728-94fd-f30e3de8f90b)
## Login Screen
![login_screen](https://github.com/JoKerP00L/react-native-auth_php/assets/95900764/7c10d213-d42a-4884-9221-19f5faf82788)
## Logut Screen
![logout_screen](https://github.com/JoKerP00L/react-native-auth_php/assets/95900764/64d469eb-ce54-4bc0-8fd3-c7b99682277f)
## Fetch-data Screen
![fetchdata_screen](https://github.com/JoKerP00L/react-native-auth_php/assets/95900764/243b9ea5-3d28-4893-acc8-be5d652298f3)

## Troubleshooting

- If you encounter any issues with the application, please check the server logs for any PHP errors and ensure that your server is properly configured.

## Contributing

Feel free to contribute to this project by submitting pull requests or reporting issues. Your contributions are highly appreciated.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Thanks to the Android and PHP communities for providing valuable resources and documentation.
- Inspiration for this project comes from various Android and web development tutorials.

---
