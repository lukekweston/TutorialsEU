package weston.luke.gmailclone.model

data class Account(
    val icon: Int? = null,
    val username: String,
    val email: String,
    val unreadEmails: Int
)
