package com.ecofactorqa.util;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class JsonUtil.
 * This is an utility class for manipulating JSON.
 * The class supports both Java parsing and Java mapping from JSON.
 * 
 * @author $Author: vprasannaa $
 * @version $Rev: 30605 $ $Date: 2014-05-19 05:32:56 -0700 (Mon, 19 May 2014) $
 */
public final class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * Instantiates a new json util.
     */
    private JsonUtil() {

        super();
    }

    /**
     * Parses the object.
     * @param content the content
     * @return the jSON object
     */
    public static JSONObject parseObject(final String content) {

        final JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(content);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * Parses the array.
     * @param content the content
     * @return the jSON array
     */
    public static JSONArray parseArray(final String content) {

        final JSONParser parser = new JSONParser();
        try {
            return (JSONArray) parser.parse(content);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * Write json Java object to file.
     * @param <T> the type of Json object
     * @param jsonData the json data
     * @param fileName the file name
     */
    public static <T> void writeJsonToFile(final T jsonData, final String fileName) {

        final ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(fileName), jsonData);
        } catch (final IOException e) {
            LOGGER.error("Could not write JSON to file.", e);
        }
    }

    /**
     * Reads json from file. Converts the Json to Java type supplied.
     * @param <T> the type of Json object
     * @param fileName the file name
     * @param type the type
     * @return the t
     */
    public static <T> T readJsonFromFile(final String fileName, final Class<T> type) {

        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(fileName), type);
        } catch (final IOException e) {
            LOGGER.error("Could not read from JSON file.", e);
        }
        return null;
    }
}
