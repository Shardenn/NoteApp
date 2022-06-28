// This is how we direct traffic coming into the server

const express = require('express')
const User = require('../models/User')
const router = express.Router()

router.get('/', async(req, res) => {
    res.status(200).send("welcome")
})

router.post('/createUser', async (req, res) => {
    // Create a new user
    try {
        const user = new User(req.body)
        await user.save()

        res.status(201).send({user})
    } catch (error) {
        res.status(400).send({error})
    }
})

router.post('/userLogin', async(req, res) => {
    try {
        const {email, password} = req.body
        const user = await User.findByCredentials(email, password)

        if (!user) {
            return res.status(401).send({error: "Login failed. Check your credentials."})
        }

        res.status(200).send({"error":null, "message":"200", "user":user})
    } catch (error) {
        res.status(400).send({error})
    }
})

module.exports = router