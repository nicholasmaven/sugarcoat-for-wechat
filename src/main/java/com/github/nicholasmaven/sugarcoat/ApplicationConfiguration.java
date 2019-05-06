package com.github.nicholasmaven.sugarcoat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.nicholasmaven.sugarcoat.wechat.XmlMapperWrapper;
import org.apache.http.client.HttpClient;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Configuration for bootstrapping
 *
 * @author mawen
 * @date 2019-01-31 11:27
 */
@Configuration
public class ApplicationConfiguration {
    @Bean
    public XmlMapperWrapper xmlMapper() {
        JacksonXmlModule module = new JacksonXmlModule();
        return new XmlMapperWrapper(new XmlMapper(module));
    }

    @Bean
    public ObjectWriter writer(XmlMapperWrapper wrapper) {
        return wrapper.getMapper().writer().withRootName("xml");
    }

    @Bean
    public ObjectMapper mapperComposition(JacksonProperties props) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.CLOSE_CLOSEABLE)
                .enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,
                        DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                .setDateFormat(new SimpleDateFormat(props.getDateFormat() == null ? "yyyy-MM-dd " +
                        "HH:mm:ss" : props.getDateFormat()))
                .setTimeZone(props.getTimeZone() == null ? TimeZone.getTimeZone("GMT+8") :
                        props.getTimeZone())
                .setLocale(Locale.CHINESE);
        return mapper;
    }



}
