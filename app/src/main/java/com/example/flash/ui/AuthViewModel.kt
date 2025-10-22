package com.example.flash.ui


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val isSignUpMode: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentUser: com.google.firebase.auth.FirebaseUser? = null,
    val isEmailVerified: Boolean = false
)

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    init {
        val user = auth.currentUser
        _uiState.value = _uiState.value.copy(
            currentUser = user,
            isEmailVerified = user?.isEmailVerified == true
        )
    }

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun toggleAuthMode() {
        _uiState.value = _uiState.value.copy(isSignUpMode = !_uiState.value.isSignUpMode, error = null)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun fillDemoCredentials() {
        _uiState.value = _uiState.value.copy(email = "demo@email.com", password = "password123")
    }

    fun authenticate() {
        val email = _uiState.value.email.trim()
        val password = _uiState.value.password.trim()

        if (email.isEmpty() || password.isEmpty()) {
            _uiState.value = _uiState.value.copy(error = "Email and password cannot be empty.")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        if (_uiState.value.isSignUpMode) {
            signUp(email, password)
        } else {
            signIn(email, password)
        }
    }

    private fun signUp(email: String, password: String) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    _uiState.value = _uiState.value.copy(isLoading = false)

                    if (task.isSuccessful) {
                        auth.currentUser?.sendEmailVerification()
                        _uiState.value = _uiState.value.copy(
                            currentUser = auth.currentUser,
                            isEmailVerified = false
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            error = task.exception?.localizedMessage
                                ?: "Sign up failed. Please try again."
                        )
                        Log.e("AuthError", "Signup error", task.exception)
                    }
                }
        }
    }

    private fun signIn(email: String, password: String) {
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    _uiState.value = _uiState.value.copy(isLoading = false)

                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        _uiState.value = _uiState.value.copy(
                            currentUser = user,
                            isEmailVerified = user?.isEmailVerified == true
                        )
                    } else {
                        val msg = task.exception?.localizedMessage
                            ?: "Login failed. Please check your credentials."
                        _uiState.value = _uiState.value.copy(error = msg)
                        Log.e("AuthError", "SignIn error", task.exception)
                    }
                }
        }
    }

    fun resendVerificationEmail() {
        auth.currentUser?.sendEmailVerification()
    }

    fun signOut() {
        auth.signOut()
        _uiState.value = AuthUiState()
    }
}
