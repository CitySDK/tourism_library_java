/**
 * COPYRIGHT NOTICE: 
 *
 * This file is part of CitySDK WP5 Tourism Java Library.
 *
 * CitySDK WP5 Tourism Java Library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CitySDK WP5 Tourism Java Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with CitySDK WP5 Tourism Java Library. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2013, IST
 */
package citysdk.tourism.client.requests;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import citysdk.tourism.client.exceptions.ServerErrorException;

/**
 * Used to perform HTTP requests.
 * 
 * @author Pedro Cruz
 * 
 */
public class Request {
    /*
     * Gets the response after querying a given URL
     */
    protected static String getResponse(String Url) throws IOException, ServerErrorException {
        if (Url == null) {
            return null;
        }

        URL url = new URL(Url);
        HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
        httpUrl.setRequestMethod("GET");
        httpUrl.setRequestProperty("Accept", "application/json");
        httpUrl.setRequestProperty("Accept-Encoding", "gzip, deflate");
        httpUrl.connect();

        int code = httpUrl.getResponseCode();
        String encoding = httpUrl.getContentEncoding();
        InputStream resultingInputStream = null;

        Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.info("Queried " + Url + " with response code " + code);
        if (code == 200) {
            if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
                resultingInputStream = new GZIPInputStream(httpUrl.getInputStream());
            } else if (encoding != null && encoding.equalsIgnoreCase("deflate")) {
                resultingInputStream = new InflaterInputStream(httpUrl.getInputStream(), new Inflater(true));
            } else {
                resultingInputStream = httpUrl.getInputStream();
            }

            byte[] response = readFully(resultingInputStream);
            httpUrl.disconnect();

            return new String(response, "UTF-8");

        } else {
            String read, message = "";
            logger.warning("Error code " + code);
            try {
                if (httpUrl != null) {
                    if (httpUrl.getErrorStream() != null) {
                        BufferedReader inStream = new BufferedReader(new InputStreamReader(httpUrl.getErrorStream()));
                        while ((read = inStream.readLine()) != null) {
                            message += read;
                        }

                        inStream.close();
                    }
                    httpUrl.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            logger.severe("Error message " + message);
            throw new ServerErrorException(message);
        }
    }

    static byte[] readFully(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int count; (count = in.read(buffer)) != -1;) {
            out.write(buffer, 0, count);
        }
        return out.toByteArray();
    }
}