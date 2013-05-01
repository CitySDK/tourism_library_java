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
package citysdk.tourism.client.parser.data;

/**
 * Container of an image. It can be either the byte-code (base64) of the image
 * or a URI of the image.
 * 
 * @author Pedro Cruz
 *
 */
public class ImageContent {
	private String content;
	private boolean imgByteCode;
	private boolean imgUri;
	
	public ImageContent(String content) {
		this.content = content;
	}

	public boolean hasImgByteCode() {
		return imgByteCode;
	}

	public void isImgByteCode(boolean imgByteCode) {
		this.imgByteCode = imgByteCode;
	}

	public boolean hasImgUri() {
		return imgUri;
	}

	public void isImgUri(boolean imgUri) {
		this.imgUri = imgUri;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
