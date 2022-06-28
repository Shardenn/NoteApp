const express = require('express')
const Note = require('../models/note')

const router = express.Router()

router.post('/notes/create', async(req, res) => {
    //Creating a new note

    const noteData = req.body
    
    try {
        const note = new Note(noteData)
        await note.save()
        res.status(201).send({note})
    } catch (error) {
        res.status(400).send({error})
    }
})

router.put('/notes/edit', async(req, res) => {
    //Editing a note
    try {
        const noteUpdate = req.body
        const note = await Note.editNote(noteUpdate)

        if (!note) {
            return res.status(401).send({error: "Could not update the note."})
        }

        res.status(201).send({note})

    } catch (error) {
        res.status(400).send({error})
    }
})

router.delete('/notes/delete', async(req, res) => {
    try {
        const noteID = req.body._id
        const note = await Note.deleteNote(noteID)
        if (!note) {
            return res.status(401).send({error: "Could not delete your note."})
        }

        res.status(201).send({note})
    } catch (error) {
        res.status(400).send({error})
    }
})

router.get('/notes/', async(req, res) => {
    // Get all notes by user
    try {
        // "Get" don't have body
        const userID = req.query._id
        const notes = await Note.getNotesByID(userID)
        if (!notes) {
            return res.status(401).send({error: "Could not find any notes."})
        }
        res.status(201).send(notes)
    } catch (error) {
        res.status(400).send({error})
    }
})

module.exports = router