package com.safframework.log.parse

import com.alibaba.fastjson.JSON
import com.safframework.log.L
import com.safframework.log.LoggerPrinter
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by tony on 2017/11/24.
 */
class CollectionParse : Parser<Collection<*>> {

    override fun parseString(collection: Collection<*>): String {

        val jsonArray = JSONArray()

        val simpleName = collection.javaClass

        var msg = "%s size = %d" + LoggerPrinter.BR
        msg = String.format(msg, simpleName, collection.size) + "║ "

        collection.map {

            it ->

            try {
                val objStr = JSON.toJSONString(it)
                val jsonObject = JSONObject(objStr)
                jsonArray.put(jsonObject)
            } catch (e: JSONException) {
                L.e("Invalid Json")
            }
        }

        var message = jsonArray.toString(LoggerPrinter.JSON_INDENT)
        message = message.replace("\n".toRegex(), "\n║ ")

        return msg + message
    }
}