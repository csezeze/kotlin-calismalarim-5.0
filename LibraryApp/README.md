# LibraryApp - Supabase Based Android Library Application

LibraryApp is a Kotlin Android application developed with Jetpack Compose and Supabase.  
The app allows users to register, log in, view a digital book list, borrow books, and see their borrowed books.
---

## Features

## Features

- User registration with Supabase Auth
- User login and logout
- Book list fetched from Supabase database
- Book detail screen
- Borrow book feature
- Borrow duration selection between 1 and 5 days
- Prevents borrowing the same book more than once
- Updates available book copy count after borrowing
- Book cards show Borrow Book, Out of Stock, and Already Borrowed states
- My Borrowed Books screen
- Active and previous borrowings are shown separately
- Borrowed books show readable due dates and remaining time

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

| Login Page | Login Failed | Create Account |
|---|---|---|
| <img src="./screenshots/01-login%20page.png" width="220" /> | <img src="./screenshots/02-login%20failed.png" width="220" /> | <img src="./screenshots/03-create%20account.png" width="220" /> |

---

### Registration and Book List

| Registration Success | Book List Page | Borrow Book |
|---|---|---|
| <img src="./screenshots/04-registration%20success.png" width="220" /> | <img src="./screenshots/05-book%20list%20page.png" width="220" /> | <img src="./screenshots/11-borrow%20book.png" width="220" /> |

---

### Borrowed Books Screens

| Borrowed Books | Empty Borrowed Books | Borrow List 1 |
|---|---|---|
| <img src="./screenshots/06-borrowed%20books.png" width="220" /> | <img src="./screenshots/07-empty%20borrowed%20books%20list.png" width="220" /> | <img src="./screenshots/08-borrow%20list%201.png" width="220" /> |

---

### Borrow Rules 

| Borrow Rule Warning | Same Book Cannot Be Borrowed Twice | Updated Borrow List |
|---|---|---|
| <img src="./screenshots/09-important%20rule%20borrow.png" width="220" /> | <img src="./screenshots/10-same-book-cannot-be-borrowed-twice.png" width="220" /> | <img src="./screenshots/12-borrow%20list%202.png" width="220" /> |

---

### Search Feature

| Search Bar | Search Test |
|---|---|
| <img src="./screenshots/16-search-bar.png" width="220" /> | <img src="./screenshots/17-test-search-bar.png" width="220" /> |

---

### Final Borrowing Status Updates

| Book List Borrow Status | Out of Stock and Already Borrowed | Active and Previous Borrowings |
|---|---|---|
| <img src="./screenshots/13-book-list-borrow-status-overview.png" width="220" /> | <img src="./screenshots/14-book-list-out-of-stock-and-borrowed.png" width="220" /> | <img src="./screenshots/15-my-books-active-previous.png" width="220" /> |
