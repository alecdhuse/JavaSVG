/*
 * BSD License
 * 
 * Copyright© 2012, Alec Dhuse All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, 
 * this list of conditions and the following disclaimer in the documentation 
 * and/or other materials provided with the distribution.
 * 
 * Neither Alec Dhuse nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.

 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package javasvg;

import java.awt.Color;
import java.util.StringTokenizer;

/**
 * Holds styling info for an SvgImageElement
 * @author Alec
 */
public class SvgElementStyle {
    protected Color fillColor;
    
    public SvgElementStyle() {
        
    }
    
    /**
     * Parses a String representing a color coded in hex.  
     * 
     * @param  hexString               
     * @return A color object representing the hex string.
     */
    public static Color parseHexString(String hexString) {
        char   currentAlphaDigit;
        int    digitDecimalValue, decimalAlpha;
        Color  returnColor = Color.BLACK;
        String alpha, blue, green, red;

        try {
            if (hexString.length() == 8) {
                alpha             = hexString.substring(0, 2);
                blue              = hexString.substring(2, 4);
                green             = hexString.substring(4, 6);
                red               = hexString.substring(6, 8);
                decimalAlpha      = 0;
                digitDecimalValue = 0;
                returnColor       = Color.decode("#" + red + green + blue);

                for (int i = 1; i >= 0; i--) {
                    currentAlphaDigit = alpha.charAt(i);

                    switch (currentAlphaDigit) {
                        case '0':
                            digitDecimalValue = 0;
                            break;
                        case '1':
                            digitDecimalValue = 1;
                            break;
                        case '2':
                            digitDecimalValue = 2;
                            break;
                        case '3':
                            digitDecimalValue = 3;
                            break;
                        case '4':
                            digitDecimalValue = 4;
                            break;
                        case '5':
                            digitDecimalValue = 5;
                            break;
                        case '6':
                            digitDecimalValue = 6;
                            break;
                        case '7':
                            digitDecimalValue = 7;
                            break;
                        case '8':
                            digitDecimalValue = 8;
                            break;
                        case '9':
                            digitDecimalValue = 9;
                            break;
                        case 'a':
                            digitDecimalValue = 10;
                            break;
                        case 'b':
                            digitDecimalValue = 11;
                            break;
                        case 'c':
                            digitDecimalValue = 12;
                            break;
                        case 'd':
                            digitDecimalValue = 13;
                            break;
                        case 'e':
                            digitDecimalValue = 14;
                            break;
                        case 'f':
                            digitDecimalValue = 15;
                            break;
                    }

                    if (i == 1) {
                        decimalAlpha += (digitDecimalValue * (1));
                    } else if (i == 0) {
                        decimalAlpha += (digitDecimalValue * (16));
                    }
                }

                returnColor = new Color(returnColor.getRed(), returnColor.getGreen(), returnColor.getBlue(), decimalAlpha);
            }
        } catch (Exception e) {
            System.out.println("Error in SvgElementStyle.parseHexString - " + e);
        }

        return returnColor;
    }    
    
    /**
     * Parses information about the style of an Image element.
     * 
     * @param style
     *          Returns a class containing that parsed style information.
     * @return 
     */
    public static SvgElementStyle parseStyle(String style) {
        int             start, end;
        String          property, token, value;
        StringTokenizer st;
        SvgElementStyle elementStyle;
        
        elementStyle = new SvgElementStyle();
        st           = new StringTokenizer(style);
        
        while (st.hasMoreTokens()) {
            token    = st.nextToken();
            start    = token.indexOf(":");
            property = token.substring(0, start);
            value    = token.substring(start);
            
            if (property.equals("fill")) {
                elementStyle.fillColor = SvgElementStyle.parseHexString(value);
            }
        }
        
        
        return elementStyle;
    }    
}
