package com.brh.digiturbine.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper

class AdHolder {

    @JacksonXmlElementWrapper(localName = "ad", useWrapping = false)
    var ad: List<AdItem> = emptyList()
    var totalCampaignsRequested: String? = null
    var responseTime: String? = null
    var version: String? = null
}