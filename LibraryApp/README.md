# LibraryApp - Supabase Based Android Library Application

LibraryApp is a Kotlin Android application developed with Jetpack Compose and Supabase.  
The app allows users to register, log in, view a digital book list, borrow books, and see their borrowed books.
---

## Features

- User registration with Supabase Auth
- User login and logout
- Book list fetched from Supabase database
- Book detail screen
- Borrow book feature
- Prevents borrowing the same book more than once
- Updates available book copy count after borrowing
- My Borrowed Books screen
- Clean and modern Jetpack Compose UI
---

## Technologies Used

- Kotlin
- Android Studio
- Jetpack Compose
- Material 3
- Navigation Compose
- ViewModel
- StateFlow
- Supabase Auth
- Supabase Database
- Supabase PostgREST / RPC
- Kotlin Serialization

---
## Screenshots

### Login and Register Flow

<p align="center">
  <img src="screenshots/01-login%20page.png" width="220" />
  <img src="screenshots/02-login%20failed.png" width="220" />
  <img src="screenshots/03-create%20account.png" width="220" />
</p>

<p align="center">
  <b>Login Page</b> &nbsp;&nbsp;&nbsp;&nbsp;
  <b>Login Failed</b> &nbsp;&nbsp;&nbsp;&nbsp;
  <b>Create Account</b>
</p>

---

### Registration and Book List

<p align="center">
  <img src="screenshots/04-registration%20success.png" width="220" />
  <img src="screenshots/05-book%20list%20page.png" width="220" />
  <img src="screenshots/11-borrow%20book.png" width="220" />
</p>

<p align="center">
  <b>Registration Success</b> &nbsp;&nbsp;&nbsp;&nbsp;
  <b>Book List Page</b> &nbsp;&nbsp;&nbsp;&nbsp;
  <b>Borrow Book</b>
</p>

---

### Borrowed Books Screens

<p align="center">
  <img src="screenshots/06-borrowed%20books.png" width="220" />
  <img src="screenshots/07-empty%20borrowed%20books%20list.png" width="220" />
  <img src="screenshots/08-borrow%20list%201.png" width="220" />
</p>

<p align="center">
  <b>Borrowed Books</b> &nbsp;&nbsp;&nbsp;&nbsp;
  <b>Empty Borrowed List</b> &nbsp;&nbsp;&nbsp;&nbsp;
  <b>Borrow List</b>
</p>

---

### Borrow Rules and Final Result

<p align="center">
  <img src="screenshots/09-important%20rule%20borrow.png" width="220" />
  <img src="screenshots/10-only%20once%20borrow%20same%20book%20fixed.png" width="220" />
  <img src="screenshots/12-borrow%20list%202.png" width="220" />
</p>

<p align="center">
  <b>Borrow Rule</b> &nbsp;&nbsp;&nbsp;&nbsp;
  <b>Only Once Borrow Rule</b> &nbsp;&nbsp;&nbsp;&nbsp;
  <b>Updated Borrow List</b>
</p>
