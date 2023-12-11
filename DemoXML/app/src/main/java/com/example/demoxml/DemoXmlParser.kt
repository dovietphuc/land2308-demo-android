package com.example.demoxml

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream

fun parser(inputStream: InputStream): List<Item> {
    val xmlPullParser = Xml.newPullParser()
    xmlPullParser.setInput(inputStream, null)
    xmlPullParser.nextTag()
    return readItems(xmlPullParser)
}

fun readItems(xmlPullParser: XmlPullParser): List<Item> {
    val listItems = ArrayList<Item>()

    xmlPullParser.require(XmlPullParser.START_TAG, null, "feed")
    while(xmlPullParser.next() != XmlPullParser.END_TAG) {
        if(xmlPullParser.eventType != XmlPullParser.START_TAG) {
            continue
        }

        if(xmlPullParser.name.equals("item")) {
            listItems.add(readItem(xmlPullParser))
        } else {
            skip(xmlPullParser)
        }
    }

    return listItems
}

fun readItem(xmlPullParser: XmlPullParser): Item {
    var title = ""
    var description = ""

    xmlPullParser.require(XmlPullParser.START_TAG, null, "item")
    while(xmlPullParser.next() != XmlPullParser.END_TAG) {
        if(xmlPullParser.eventType != XmlPullParser.START_TAG) {
            continue
        }

        if(xmlPullParser.name.equals("title")) {
            title = readTitle(xmlPullParser)
        } else if(xmlPullParser.name.equals("description")) {
            description = readDescription(xmlPullParser)
        } else {
            skip(xmlPullParser)
        }
    }

    return Item(title, description)
}

fun readTitle(xmlPullParser: XmlPullParser): String{
    xmlPullParser.require(XmlPullParser.START_TAG, null, "title")
    val title = readText(xmlPullParser)
    xmlPullParser.require(XmlPullParser.END_TAG, null, "title")
    return title
}

fun readDescription(xmlPullParser: XmlPullParser): String{
    xmlPullParser.require(XmlPullParser.START_TAG, null, "description")
    val description = readText(xmlPullParser)
    xmlPullParser.require(XmlPullParser.END_TAG, null, "description")
    return description
}

fun readText(xmlPullParser: XmlPullParser): String {
    if(xmlPullParser.next() == XmlPullParser.TEXT) {
        val text = xmlPullParser.text
        xmlPullParser.nextTag()
        return text
    }
    return ""
}

fun skip(xmlPullParser: XmlPullParser) {
    if(xmlPullParser.eventType != XmlPullParser.START_TAG) {
        return
    }
    var depth = 1
    while(depth > 0) {
        when(xmlPullParser.next()) {
            XmlPullParser.START_TAG -> depth++
            XmlPullParser.END_TAG -> depth--
        }
    }
}