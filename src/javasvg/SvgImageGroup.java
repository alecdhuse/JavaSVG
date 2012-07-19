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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Alec
 */
public class SvgImageGroup {
    protected AffineTransform             transform;
    protected ArrayList<SvgImageElement>  elements;
    
    public SvgImageGroup() {
        elements   = new ArrayList<SvgImageElement>();
        transform  = new AffineTransform();
    }
    
    public void addElement(SvgImageElement element) {
        elements.add(element);
    }
    
    public void draw(Graphics g) {
        AffineTransform originalTransform;
        Color           fillColor;
        Graphics2D      g2;
        SvgElementStyle style;
        
        g2 = (Graphics2D) g;
        originalTransform = g2.getTransform();
        
        //set transform
        g2.setTransform(transform);
        g2.setStroke(new BasicStroke(1,  BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        for (SvgImageElement element: elements) {
            style = element.style;
            
            if (style != null) {
                fillColor = style.fillColor;

                if (fillColor != null)
                    g2.setColor(fillColor);
            }
            
            g2.fill(element.shape);                  
            //g2.draw(element.shape);      
        }
        
        //reset transform
        g2.setTransform(originalTransform);
    }
    
    /**
     * Sets the transform for this Image Group.
     * @param transXML 
     *          The transform information in XML
     */
    public void setTransformXML(String transXML) {
        int             start, end;
        String          content, trans, type;
        StringTokenizer st;
        
        start   = transXML.indexOf ("\"") + 1;
        end     = transXML.indexOf("\"", start);
        trans   = transXML.substring(start, end);
        
        end     = trans.indexOf("(");
        type    = trans.substring(0, end);
        
        if (type.equalsIgnoreCase("matrix")) {
            double m00, m10, m01, m11, m02, m12;
            
            start   = trans.indexOf("(") + 1;
            end     = trans.indexOf(")", start);
            content = trans.substring(start, end);            
            
            st  = new StringTokenizer(content, ",");   
            m00 = Float.parseFloat(st.nextToken());
            m10 = Float.parseFloat(st.nextToken());
            m01 = Float.parseFloat(st.nextToken());
            m11 = Float.parseFloat(st.nextToken());
            m02 = Float.parseFloat(st.nextToken());
            m12 = Float.parseFloat(st.nextToken());
            
            transform.setTransform(m00, m10, m01, m11, m02, m12);
        } else if (type.equalsIgnoreCase("rotate")) {    
            
        } else if (type.equalsIgnoreCase("scale")) {    
            
        } else if (type.equalsIgnoreCase("translate")) {
            float translateX, translateY;
            
            start   = trans.indexOf("(");
            end     = trans.indexOf(")", start);
            content = trans.substring(start, end);            
            
            st = new StringTokenizer(content, ",");    
            
            translateX = Float.parseFloat(st.nextToken());
            translateY = Float.parseFloat(st.nextToken());     
            transform.translate(translateX, translateY);
        }
        
    }
}
