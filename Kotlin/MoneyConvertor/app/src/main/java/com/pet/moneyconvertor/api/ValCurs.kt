package com.pet.moneyconvertor.api

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ValCurs", strict = false)
data class ValCurs(
    @field:ElementList(entry = "Valute", inline = true, required = false)
    var valList: List<Currency>? = null,
    @field:Attribute(name = "Date")
    var date: String? = null,
    @field:Attribute(name = "name")
    var name: String? = null
)