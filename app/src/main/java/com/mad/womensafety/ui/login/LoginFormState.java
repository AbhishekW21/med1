package com.mad.womensafety.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class LoginFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    private boolean isDataValid;

    // Constructor for when there are validation errors
    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false; // Mark as invalid if there are errors
    }

    // Constructor for when the form is completely valid
    LoginFormState(boolean isDataValid) {
        this.usernameError = null; // No errors
        this.passwordError = null; // No errors
        this.isDataValid = isDataValid; // Only true when data is valid
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }

    // Optional: You can add setters if you plan to update this object after creation
    void setUsernameError(@Nullable Integer usernameError) {
        this.usernameError = usernameError;
    }

    void setPasswordError(@Nullable Integer passwordError) {
        this.passwordError = passwordError;
    }

    void setDataValid(boolean isDataValid) {
        this.isDataValid = isDataValid;
    }
}
