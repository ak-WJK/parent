package com.kzb.parents.util;

import android.text.TextUtils;

import com.kzb.baselibrary.log.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/********************
 * 作者：malus
 * 日期：17/2/10
 * 时间：下午3:52
 * 注释：
 ********************/

public class TextHandler extends DefaultHandler {
    int mSize = 0;
    String content;
    int index;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        Log.e("sax", "startDocument");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        Log.e("sax", "startElement" + "  localName:" + localName + "  qName:" + qName + "  attribute:" + attributes.toString());
        String style = attributes.getValue("style");
        if (!TextUtils.isEmpty(style)) {
            if (style.contains("font-size:")) {
                index = content.indexOf("font-size:",index);
                String size = style.split("pt")[0];
                if (!TextUtils.isEmpty(size)) {
                    String[] split = size.split(" ");
                    if (split.length > 0) {
                        size = split[split.length - 1];
                        try {
                            mSize = Integer.parseInt(size);
                        }catch (Exception e){
                            mSize = 0;
                        }
                    } else {
                        split = size.split(":");
                        if (split.length > 0) {
                            size = split[split.length-1];
                            try {
                                mSize = Integer.parseInt(size);
                            }catch (Exception e){
                                mSize = 0;
                            }
                        }else{
                            mSize = 0;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        Log.e("sax", "characters" + "  ch:" + new String(ch) + " start:" + start + "  length:" + length);
        if (mSize > 0) {
            if (content != null) {
                int cStart = content.indexOf(">", index) + 1;
                if (mSize < 6) {
                    content = content.substring(0, cStart) + "<small><small>" + new String(ch) + "</small></small>" + content.substring(cStart + length, content.length());
                } else if (mSize < 12) {
                    content = content.substring(0, cStart) + "<small>" + new String(ch) + "</small>" + content.substring(cStart + length, content.length());
                }
            }
            mSize = 0;
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        Log.e("sax", "endElement" + "  localName:" + localName + "  qName:" + qName);
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        Log.e("sax", "endDocument");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
