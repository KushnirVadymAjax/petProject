package com.example.petproject.repository

import com.example.petproject.model.Consumables
import org.springframework.data.mongodb.repository.MongoRepository

interface ConsumablesRepository : MongoRepository<Consumables, String> {
}