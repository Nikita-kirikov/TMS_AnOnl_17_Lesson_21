package app.tms_lesson_19

import java.util.UUID

data class Group(
    val text: String,
    override val id: String = UUID.randomUUID().toString()
) : NoteType
