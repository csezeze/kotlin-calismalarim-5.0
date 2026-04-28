package com.example.libraryapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.libraryapp.ui.screen.BookDetailScreen
import com.example.libraryapp.ui.screen.BookListScreen
import com.example.libraryapp.ui.screen.LoginScreen
import com.example.libraryapp.ui.screen.MyBooksScreen
import com.example.libraryapp.ui.screen.RegisterScreen
import com.example.libraryapp.ui.viewmodel.AuthViewModel
import com.example.libraryapp.ui.viewmodel.BookViewModel
import com.example.libraryapp.ui.viewmodel.LoanViewModel
import com.example.libraryapp.ui.viewmodel.MyBooksViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    val authViewModel: AuthViewModel = viewModel()
    val bookViewModel: BookViewModel = viewModel()
    val loanViewModel: LoanViewModel = viewModel()
    val myBooksViewModel: MyBooksViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.BookList.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onRegisterClick = {
                    authViewModel.clearMessages()
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                authViewModel = authViewModel,
                onLoginClick = {
                    authViewModel.clearMessages()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.BookList.route) {
            BookListScreen(
                bookViewModel = bookViewModel,
                onBookClick = { bookId ->
                    navController.navigate(Screen.BookDetail.createRoute(bookId))
                },
                onMyBooksClick = {
                    navController.navigate(Screen.MyBooks.route)
                },
                onLogoutClick = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.MyBooks.route) {
            MyBooksScreen(
                myBooksViewModel = myBooksViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.BookDetail.route,
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getLong("bookId")

            val selectedBook = bookViewModel.uiState.value.books.find {
                it.id == bookId
            }

            BookDetailScreen(
                book = selectedBook,
                loanViewModel = loanViewModel,
                onBorrowSuccess = {
                    bookViewModel.loadBooks()
                    myBooksViewModel.loadMyBooks()
                },
                onBackClick = {
                    loanViewModel.clearMessages()
                    navController.popBackStack()
                }
            )
        }
    }
}