package com.instant.taskone_compose

fun Contact.validateContact(): Boolean {
    return this.name.isNotEmpty() && this.phone.isNotEmpty() && this.address.isNotEmpty()
}