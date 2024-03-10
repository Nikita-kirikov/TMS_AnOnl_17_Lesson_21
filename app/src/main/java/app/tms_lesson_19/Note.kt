package app.tms_lesson_19

import java.util.UUID

data class Note(
    val header: String,
    val text: String,
    val date: String,
    var important: Boolean = false,
    override val id : String = UUID.randomUUID().toString()
) : NoteType
