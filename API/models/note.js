const mongoose = require('mongoose')

const noteSchema = mongoose.Schema({
    userID: {
        type: String,
        required: true,
        trim: true
    },
    notebookID: {
        type: String,
        required: true,
        trim: true
    },
    noteTitle: {
        type: String,
        required: true,
        trim: true
    },
    noteContent: {
        type: String,
        required: true
    },
    createdOn: {
        type: Date,
        default: Date.now
    },
    updatedOn: {
        type: Date,
        default: Date.now
    },
    deleted: {
        type: Boolean,
        default: false
    }
})

noteSchema.index({"noteTitle": "text"})

noteSchema.statics.editNote = async(note) => {
    // Find the note and update it
    const id = mongoose.Types.ObjectId(note._id)
    const updatedNote = await Note.updateOne({"_id": id},{"noteTitle": note.noteTitle, "noteContent": note.noteContent})
    if (!updatedNote) {
        throw new Error({error: "Sorry, we couldn't update your note."})
    }

    return updatedNote
}

noteSchema.statics.deleteNote = async(noteID) => {
    var id = mongoose.Types.ObjectId(noteID)

    const note = await Note.updateOne({"_id": id}, {"deleted":true})

    if (!note) {
        throw new Error({error: "We could not delete your note."})
    }

    return note
}

noteSchema.statics.getNotesByID = async(userID) => {
    const notes = await Note.find({"deleted":false, "userID":userID})
    return notes
}

const Note = mongoose.model('Note', noteSchema)
module.exports = Note