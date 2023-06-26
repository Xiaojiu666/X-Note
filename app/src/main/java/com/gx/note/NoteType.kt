package com.gx.note

enum class NoteType(noteName: String, val resId: Int) {
    DIARY("写日记", R.mipmap.column),
    ACCOUNT("记账", R.mipmap.ic_note_account),
    TODO("写计划", R.mipmap.todo_list),
}