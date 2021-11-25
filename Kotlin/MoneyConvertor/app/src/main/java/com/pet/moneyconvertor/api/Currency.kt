package com.pet.moneyconvertor.api

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


@Root(name="Valute", strict = false)
data class Currency(
    @field:Attribute(name = "ID", required = false)
    var id: String? = null,
    @field:Element(name = "NumCode", required = false)
    var numCode: String? = null,
    @field:Element(name = "CharCode", required = false)
    var charCode: String? = null,
    @field:Element(name = "Nominal", required = false)
    var nominal: String? = null,
    @field:Element(name = "Name", required = false)
    var name: String? = null,
    @field:Element(name = "Value", required = false)
    var value: String? = null
)