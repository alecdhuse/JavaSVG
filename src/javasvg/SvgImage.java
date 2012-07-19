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
import java.awt.Graphics;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Alec
 */
public class SvgImage {
    protected ArrayList<SvgImageGroup>    groups;
    protected boolean                     overflowVisable;
    protected float                       height, width;    
    protected float                       viewBoxX, viewBoxY, viewBoxHeight, viewBoxWidth;
    
    public SvgImage(float height, float width) {
        this.height          = height;
        this.width           = width;
        this.overflowVisable = false;
        this.groups          = new ArrayList<SvgImageGroup>();
    }
    
    /**
     * Adds a group of elements to this Image
     * 
     * @param group 
     */
    public void addGroup(SvgImageGroup group) {
        this.groups.add(group);
    }
    
    /**
     * Draws this SVG image with the provided graphics class.
     * 
     * @param g 
     */
    public void draw(Graphics g) {
        for (SvgImageGroup group: groups) {
            group.draw(g);
        }
    }
    
    /**
     * Parses the g tag of an SVG's XML
     * 
     * @param graphicsXML 
     */
    public static SvgImageGroup parseGroup(String graphicsXML) {
        int             start, end;
        String          gContent, token, transform;
        StringTokenizer st;
        SvgImageGroup   group;
        
        group = new SvgImageGroup();
        
        if (graphicsXML.startsWith("<g") && graphicsXML.endsWith("</g>")) {            
            gContent = graphicsXML.substring(3, graphicsXML.length() - 4);
            st       = new StringTokenizer(gContent, "<");
                                   
            while (st.hasMoreTokens()) {
                token = "<" + st.nextToken().trim();
                
                if (token.startsWith("<path")) {
                    group.addElement(parsePath(token));
                } else if (token.startsWith("<rect")) {
                    group.addElement(parseRectangle(token));
                } else if (token.startsWith("<transform")) {
                    group.setTransformXML(token);
                }
            }
        }
        
        return group;
    }    
    
    /**
     * Adds a path to this SvgImage.
     * 
     * @param pathXMl
     *          The XML of a path
     * 
     * @return Returns the path was parsed.
     */
    public static SvgImageElement parsePath(String pathXMl) {
        float           x1, x2, x3, y1, y2, y3;
        float           x, y;
        GeneralPath     path;
        int             start, end;
        String          coordinates, data, id, style, token;
        StringTokenizer st, coordST;
        SvgElementStyle eleStyle;
        SvgImageElement imageElement;
        
        imageElement = null;
        
        if (pathXMl.startsWith("<path")) {
            x  = 0;
            x1 = 0;
            x2 = 0;
            x3 = 0;
            
            y  = 0;
            y1 = 0;
            y2 = 0;
            y3 = 0;
                    
            //get data
            start    = pathXMl.indexOf("d=\"") + 3;
            end      = pathXMl.indexOf("\"", start);
            data     = pathXMl.substring(start, end);
            
            //get id
            start    = pathXMl.indexOf("id=\"") + 4;
            end      = pathXMl.indexOf("\"", start);
            id       = pathXMl.substring(start, end);            
            
            //get style
            if (pathXMl.indexOf("style=\"") >= 0) {
                start    = pathXMl.indexOf("style=\"") + 7;
                end      = pathXMl.indexOf("\"", start);
                style    = pathXMl.substring(start, end); 
                eleStyle = SvgElementStyle.parseStyle(style);
            } else {
                //no style set fill as black as per the spec               
                eleStyle = new SvgElementStyle();
                eleStyle.fillColor = Color.BLACK;
            }
            
            path         = new GeneralPath();
            st           = new StringTokenizer(data);          
            
            path.moveTo(0, 0);
            
            while (st.hasMoreTokens()) {
                token = st.nextToken();
                
                if (token.equals("c")) {
                    //reletive Bézier curve to
                    coordinates = st.nextToken();
                    coordST     = new StringTokenizer(coordinates, ",");
                    x1          = x + Float.parseFloat(coordST.nextToken());
                    y1          = y + Float.parseFloat(coordST.nextToken());                    
                    
                    coordinates = st.nextToken();
                    coordST     = new StringTokenizer(coordinates, ",");
                    x2          = x + Float.parseFloat(coordST.nextToken());
                    y2          = y + Float.parseFloat(coordST.nextToken());
                    
                    coordinates = st.nextToken();
                    coordST     = new StringTokenizer(coordinates, ",");
                    x3          = x + Float.parseFloat(coordST.nextToken());
                    y3          = y + Float.parseFloat(coordST.nextToken());
                    
                    path.curveTo(x1, y1, x2, y2, x3, y3);
                    
                    //set x1 and y1 to the end point of the curve this may be
                    //used for horizontal and vertical line to
                    x = x3;
                    y = y3;
                } else if (token.equals("C")) {
                    //absolute Bézier curve to
                    coordinates = st.nextToken();
                    coordST     = new StringTokenizer(coordinates, ",");
                    x1          = Float.parseFloat(coordST.nextToken());
                    y1          = Float.parseFloat(coordST.nextToken());                    
                    
                    coordinates = st.nextToken();
                    coordST     = new StringTokenizer(coordinates, ",");
                    x2          = Float.parseFloat(coordST.nextToken());
                    y2          = Float.parseFloat(coordST.nextToken());
                    
                    coordinates = st.nextToken();
                    coordST     = new StringTokenizer(coordinates, ",");
                    x3          = Float.parseFloat(coordST.nextToken());
                    y3          = Float.parseFloat(coordST.nextToken());
                    
                    path.curveTo(x1, y1, x2, y2, x3, y3);
                    
                    //set x1 and y1 to the end point of the curve this may be
                    //used for horizontal and vertical line to
                    x = x3;
                    y = y3;                    
                } else if (token.equals("h")) {
                    //relitive horizontal line to
                    coordinates = st.nextToken();
                    x1          = x + Float.parseFloat(coordinates); 
                    
                    path.lineTo(x1, y);
                    
                    //set the current point for future relitive moves
                    x = x1;
                } else if (token.equals("H")) {
                    //absolute horizontal line to
                    coordinates = st.nextToken();
                    x1          = Float.parseFloat(coordinates); 
                    
                    path.lineTo(x1, y);
                    
                    //set the current point for future relitive moves
                    x = x1;                    
                } else if (token.equals("l")) {  
                    //relitive line to
                    coordinates = st.nextToken();
                    coordST     = new StringTokenizer(coordinates, ",");
                    x1          = x + Float.parseFloat(coordST.nextToken());
                    y1          = y + Float.parseFloat(coordST.nextToken());
                    
                    path.lineTo(x1, y1);
                    
                    //set the current point for future relitive moves
                    x = x1;
                    y = y1;
                } else if (token.equals("L")) {  
                    //absolute line to
                    coordinates = st.nextToken();
                    coordST     = new StringTokenizer(coordinates, ",");
                    x1          = Float.parseFloat(coordST.nextToken());
                    y1          = Float.parseFloat(coordST.nextToken());
                    
                    path.lineTo(x1, y1);
                    
                    //set the current point for future relitive moves
                    x = x1;
                    y = y1;                    
                } else if (token.equals("m")) {
                    //relitive move to
                    coordinates = st.nextToken();
                    coordST     = new StringTokenizer(coordinates, ",");
                    x1          = x + Float.parseFloat(coordST.nextToken());
                    y1          = y + Float.parseFloat(coordST.nextToken());
                    
                    path.moveTo(x1, y1);
                    //set the current point for future relitive moves
                    x = x1;
                    y = y1;     
                } else if (token.equals("M")) {
                    //absolute move to
                    coordinates = st.nextToken();
                    coordST     = new StringTokenizer(coordinates, ",");
                    x1          = Float.parseFloat(coordST.nextToken());
                    y1          = Float.parseFloat(coordST.nextToken());
                    
                    path.moveTo(x1, y1);
                    //set the current point for future relitive moves
                    x = x1;
                    y = y1;                      
                } else if (token.equals("v")) {
                    //relitive vertical line to
                    coordinates = st.nextToken();
                    y1          = y + Float.parseFloat(coordinates);
                    
                    path.lineTo(x, y1);
                    
                    //set the current point for future relitive moves
                    y = y1;         
                } else if (token.equals("V")) {
                    //absolute vertical line to
                    coordinates = st.nextToken();
                    y1          = Float.parseFloat(coordinates);
                    
                    path.lineTo(x, y1);
                    
                    //set the current point for future relitive moves
                    y = y1;                       
                } else if (token.equalsIgnoreCase("z")) {
                    path.closePath();                    
                    imageElement = new SvgImageElement(id, path, eleStyle);
                    break;
                }
            }
        } 
        
        return imageElement;
    }
    
    /**
     * Adds a rectangle with the given XML to this SvgImage.
     * 
     * @param rectangleXML
     *          The XML tag on the rectangle.
     * 
     * @return Returns the parsed rectangle.
     */
    public static SvgImageElement parseRectangle(String rectangleXML) {
        float           x, y, width, height;
        int             start, end;
        Rectangle2D     rect;
        String          id, style;
        SvgElementStyle rectStyle;
        SvgImageElement imageElement;
        
        imageElement = null;
        
        if (rectangleXML.startsWith("<rect")) {
            //read width
            start     = rectangleXML.indexOf("width=\"") + 7;
            end       = rectangleXML.indexOf("\"", start);
            width     = Float.parseFloat(rectangleXML.substring(start, end));
            
            //read height
            start     = rectangleXML.indexOf("height=\"") + 8;
            end       = rectangleXML.indexOf("\"", start);
            height    = Float.parseFloat(rectangleXML.substring(start, end));
            
            //read x
            start     = rectangleXML.indexOf("x=\"") + 3;
            end       = rectangleXML.indexOf("\"", start);
            x         = Float.parseFloat(rectangleXML.substring(start, end));
            
            //read y
            start     = rectangleXML.indexOf("y=\"") + 3;
            end       = rectangleXML.indexOf("\"", start);
            y         = Float.parseFloat(rectangleXML.substring(start, end));
            
            //read id
            start     = rectangleXML.indexOf("id=\"") + 4;
            end       = rectangleXML.indexOf("\"", start);
            id        = rectangleXML.substring(start, end);
            
            //read style
            start     = rectangleXML.indexOf("id=\"") + 4;
            end       = rectangleXML.indexOf("\"", start);
            style     = rectangleXML.substring(start, end);
            rectStyle = SvgElementStyle.parseStyle(style);
            
            rect = new Rectangle2D.Float(x, y, width, height);
            imageElement = new SvgImageElement(id, rect, rectStyle);
        } 
        
        return imageElement;
    }        

    public static SvgImage parseSVG(String svgXML) {
        float         width, height;
        int           start, end;
        String        content, svgContent;
        SvgImage      newImage;
        SvgImageGroup newGroup;
        
        start      = svgXML.indexOf("<svg") + 4;
        end        = svgXML.indexOf("</svg>");
        svgContent = svgXML.substring(start, end);
                
        //read header info
        start   = svgContent.indexOf("width=\"") + 7;
        end     = svgContent.indexOf("\"", start);
        content = svgContent.substring(start, end); 
        width   = Float.parseFloat(content);
        
        start   = svgContent.indexOf("height=\"") + 8;
        end     = svgContent.indexOf("\"", start);
        content = svgContent.substring(start, end); 
        height  = Float.parseFloat(content);        
        
        newImage = new SvgImage(height, width);
        
        //read in groups
        while (svgContent.indexOf("<g",   end) >= 0) {
            start    = svgContent.indexOf("<g",   end);
            end      = svgContent.indexOf("</g>", start) + 4;
            content  = svgContent.substring(start, end); 
            newGroup = parseGroup(content);
            newImage.addGroup(newGroup);
        }
        
        return newImage;
    }
}
