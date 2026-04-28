package com.example.libraryapp.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object BookList : Screen("book_list")
    object MyBooks : Screen("my_books")

    object BookDetail : Screen("book_detail/{bookId}") {
        fun createRoute(bookId: Long): String {
            return "book_detail/$bookId"
        }
    }
}