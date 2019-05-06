package com.github.nicholasmaven.sugarcoat.wechat;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Wrap {@link com.fasterxml.jackson.dataformat.xml.XmlMapper} to avoid bean conflicts with
 * {@link com.fasterxml.jackson.databind.ObjectMapper}
 *
 * @author mawen
 * @date 2019-03-07 19:26
 */
public class XmlMapperWrapper {
    private XmlMapper mapper;

    public XmlMapperWrapper(XmlMapper mapper) {
        this.mapper = mapper;
    }

    public XmlMapper getMapper() {
        return mapper;
    }
}