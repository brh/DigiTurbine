package com.brh.digiturbine.repository

import com.brh.digiturbine.State
import com.brh.digiturbine.model.AdHolder
import com.brh.digiturbine.network.DTService
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DTRepository @Inject constructor(val dtService: DTService) {

    suspend fun getItemList(): State {
        try {
            val result = dtService.getItems()
            val adHolder = deserializeFromXML(result.string())
            return State.Content(adHolder?.ad)
        }
        catch (e: Throwable){
            return State.Error(e.message)
        }
    }

    /**
     * Couldn't easily get retrofit and SimpleXML to work on the xml returned, so do it
     * by hand with Jackson.  Next step would be to integrate this into retrofit as custom
     * adapter
     */
    fun deserializeFromXML(readContent: String): AdHolder? {
        val xmlMapper = XmlMapper()
        xmlMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        val deserializedData: AdHolder =
            xmlMapper.readValue(readContent, AdHolder::class.java)
        return deserializedData
    }

}