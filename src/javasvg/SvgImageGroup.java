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
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Alec
 */
public class SvgImageGroup {
    protected ArrayList<SvgImageElement>  elements;
    protected float                       transformX, transformY;
    
    public SvgImageGroup() {
        elements   = new ArrayList<SvgImageElement>();
        transformX = 0;
        transformY = 0;
    }
    
    public void addElement(SvgImageElement element) {
        elements.add(element);
    }
    
    public void draw(Graphics g) {
        Color           fillColor;
        Graphics2D      g2;
        SvgElementStyle style;
        
        g2 = (Graphics2D) g;
        
        //set transform
        g2.translate(transformX, transformY);
        
        for (SvgImageElement element: elements) {
            style = element.style;
            
            if (style != null) {
                fillColor = style.fillColor;

                if (fillColor != null)
                    g2.setColor(fillColor);
            }
            
            g2.fill(element.shape);       
        }
        
        //reset transform
        g2.translate(transformX * -1, transformY *-1);
    }
    
    public void setTransformXML(String transXML) {
        int             start, end;
        String          content;
        StringTokenizer st;
        
        start   = transXML.indexOf("translate(") + 10;
        end     = transXML.indexOf(")\"", start);
        content = transXML.substring(start, end);
        st      = new StringTokenizer(content, ",");
        
        transformX = Float.parseFloat(st.nextToken());
        transformY = Float.parseFloat(st.nextToken()); 
    }
}
